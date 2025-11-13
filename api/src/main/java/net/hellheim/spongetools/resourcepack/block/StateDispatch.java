package net.hellheim.spongetools.resourcepack.block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.CopyableBuilder;

import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.hellheim.spongetools.function.QuadFunction;
import net.hellheim.spongetools.function.QuinFunction;
import net.hellheim.spongetools.function.TriFunction;

public class StateDispatch<V> {
	
	public static final Codec<StateDispatch<Variant>> CODEC = StateDispatch.codec(Variant.CODEC);
	
	private final Set<StateProperty<?>> properties;
	private final Map<StateSelector, V> values;
	
	protected StateDispatch(final Map<StateSelector, V> values) {
		this(values, values.keySet().stream()
				.flatMap(selector -> selector.properties().stream())
				.collect(Collectors.toSet()));
	}
	
	protected StateDispatch(final Map<StateSelector, V> values, final Set<StateProperty<?>> properties) {
		this.values = Map.copyOf(values);
		this.properties = Set.copyOf(properties);
	}
	
	public static <V> Codec<StateDispatch<V>> codec(final Codec<V> valueCodec) {
		return Codec
				.unboundedMap(StateSelector.CODEC, valueCodec)
				.xmap(StateDispatch::new, StateDispatch::values)
				.validate(StateDispatch::validate);
	}
	
	public static <V> StateDispatch<V> of(final V value) {
		return StateDispatch.<V>raw().add(StateSelector.empty(), value).build();
	}
	
	public static <V> Builder<V, ?> raw() {
		return new Builder<>();
	}
	
	public static <
		V,
		T1 extends Comparable<T1>>
	P1.Builder<V, T1> of(
		final StateProperty<T1> p1
	) {
		return new P1.Builder<>(p1);
	}
	
	public static <
		V,
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>>
	P2.Builder<V, T1, T2> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2
	) {
		return new P2.Builder<>(p1, p2);
	}
	
	public static <
		V,
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>>
	P3.Builder<V, T1, T2, T3> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3
	) {
		return new P3.Builder<>(p1, p2, p3);
	}
	
	public static <
		V,
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>,
		T4 extends Comparable<T4>>
	P4.Builder<V, T1, T2, T3, T4> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3,
		final StateProperty<T4> p4
	) {
		return new P4.Builder<>(p1, p2, p3, p4);
	}
	
	public static <
		V,
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>,
		T4 extends Comparable<T4>,
		T5 extends Comparable<T5>>
	P5.Builder<V, T1, T2, T3, T4, T5> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3,
		final StateProperty<T4> p4,
		final StateProperty<T5> p5
	) {
		return new P5.Builder<>(p1, p2, p3, p4, p5);
	}
	
	private static <V> DataResult<StateDispatch<V>> validate(final StateDispatch<V> dispatch) {
		return StateDispatch.validate(dispatch.values, dispatch.properties)
				.<DataResult<StateDispatch<V>>>map(msg -> DataResult.error(() -> msg))
				.orElse(DataResult.success(dispatch));
	}
	
	private static <V> Optional<String> validate(
		final Map<StateSelector, V> values,
		final Set<StateProperty<?>> properties
	) {
		if (values.isEmpty()) {
			return Optional.of("Dispatch must contain at least one selector");
		}
		
		final List<StateSelector> missingSelectors = StateSelector.populate(properties)
				.filter(fullSelector -> {
					for (final StateSelector selector : values.keySet()) {
						if (selector.test(fullSelector)) {
							return false;
						}
					}
					return true;
				})
				.toList();
		
		if (!missingSelectors.isEmpty()) {
			String message = "Missing selectors for property sets:";
			for (final StateSelector selector : missingSelectors) {
				message += "\n\t\t" + selector.serializationString();
			}
			return Optional.of(message);
		}
		
		return Optional.empty();
	}
	
	public Set<StateProperty<?>> properties() {
		return this.properties;
	}
	
	public Set<StateSelector> selectors() {
		return this.values.keySet();
	}
	
	public Map<StateSelector, V> values() {
		return this.values;
	}
	
	public Builder<V, ?> toBuilder() {
		return StateDispatch.<V>raw().from(this);
	}
	
	public V getForAnyState(final Iterable<? extends State<?>> states) {
		return this.getFor(s -> s.matchAnyState(states), () -> Streams.stream(states)
				.map(State::toString).collect(Collectors.joining("; ", "States[", "]")));
	}
	
	public V getForAnyState(final State<?>... states) {
		return this.getFor(s -> s.matchAnyState(states), () -> Arrays.stream(states)
				.map(State::toString).collect(Collectors.joining("; ", "States[", "]")));
	}
	
	public V getForAnySelector(final Iterable<? extends StateSelector> selectors) {
		return this.getFor(s -> s.matchAnySelector(selectors), () -> Streams.stream(selectors)
				.map(StateSelector::toString).collect(Collectors.joining("; ", "Selectors[", "]")));
	}
	
	public V getForAnySelector(final StateSelector... selectors) {
		return this.getFor(s -> s.matchAnySelector(selectors), () -> Arrays.stream(selectors)
				.map(StateSelector::toString).collect(Collectors.joining("; ", "Selectors[", "]")));
	}
	
	public V getFor(final State<?> state) {
		return this.getFor(s -> s.test(state), state::toString);
	}
	
	public V getFor(final StateSelector selector) {
		return this.getFor(s -> s.test(selector), selector::toString);
	}
	
	private V getFor(final Predicate<StateSelector> selectorPredicate, final Supplier<String> name) {
		for (final var entry : this.values.entrySet()) {
			if (selectorPredicate.test(entry.getKey())) {
				return entry.getValue();
			}
		}
		
		throw new IllegalArgumentException(String.format(
				"Provided argument (%s) does not match any selector in the dispatch (%s)",
				name.get(), this.toString()));
	}
	
	@Override
	public int hashCode() {
		return this.values.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof StateDispatch<?> dispatch)) {
			return false;
		} else {
			return this.values.equals(dispatch.values);
		}
	}
	
	@Override
	public String toString() {
		return this.values.entrySet().stream()
				.map(entry -> entry.getKey().serializationString() + ": " + entry.getValue().toString())
				.collect(Collectors.joining("; ", "StateDispatch[", "]"));
	}
	
	public static final class P1<
			V,
			T1 extends Comparable<T1>>
			extends StateDispatch<V> {
		
		private final StateProperty<T1> p1;
		
		private P1(
			final Map<StateSelector, V> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1
		) {
			super(values, properties);
			this.p1 = p1;
		}
		
		@Override
		public Builder<V, T1> toBuilder() {
			return StateDispatch.<V, T1>of(this.p1).from(this);
		}
		
		public static final class Builder<
				V,
				T1 extends Comparable<T1>>
				extends StateDispatch.Builder<V, Builder<V, T1>> {
			
			private final StateProperty<T1> p1;
			
			private Builder(
				final StateProperty<T1> p1
			) {
				this.p1 = Objects.requireNonNull(p1, "property");
			}
			
			public Builder<V, T1> add(final T1 v1, final V value) {
				return this.add(StateSelector.builder().add(this.p1, v1), value);
			}
			
			public Builder<V, T1> generate(final Function<? super T1, V> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.add(v1, generator.apply(v1)));
				return this;
			}
			
			@Override
			public P1<V, T1> build() {
				this.validate();
				return new P1<>(this.values, this.properties, this.p1);
			}
		}
	}
	
	public static final class P2<
			V,
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>>
			extends StateDispatch<V> {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		
		private P2(
			final Map<StateSelector, V> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1,
			final StateProperty<T2> p2
		) {
			super(values, properties);
			this.p1 = p1;
			this.p2 = p2;
		}
		
		@Override
		public Builder<V, T1, T2> toBuilder() {
			return StateDispatch.<V, T1, T2>of(this.p1, this.p2).from(this);
		}
		
		public static final class Builder<
				V,
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>>
				extends StateDispatch.Builder<V, Builder<V, T1, T2>> {
			
			private final StateProperty<T1> p1;
			private final StateProperty<T2> p2;
			
			private Builder(
				final StateProperty<T1> p1,
				final StateProperty<T2> p2
			) {
				this.p1 = Objects.requireNonNull(p1, "property1");
				this.p2 = Objects.requireNonNull(p2, "property2");
			}
			
			public Builder<V, T1, T2> add(final T1 v1, final T2 v2, final V value) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2), value);
			}
			
			public Builder<V, T1, T2> generate(final BiFunction<? super T1, ? super T2, V> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.add(v1, v2, generator.apply(v1, v2))));
				return this;
			}
			
			@Override
			public P2<V, T1, T2> build() {
				this.validate();
				return new P2<>(this.values, this.properties, this.p1, this.p2);
			}
		}
	}
	
	public static final class P3<
			V,
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>>
			extends StateDispatch<V> {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		
		private P3(
			final Map<StateSelector, V> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1,
			final StateProperty<T2> p2,
			final StateProperty<T3> p3
		) {
			super(values, properties);
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}
		
		@Override
		public Builder<V, T1, T2, T3> toBuilder() {
			return StateDispatch.<V, T1, T2, T3>of(this.p1, this.p2, this.p3).from(this);
		}
		
		public static final class Builder<
				V,
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>>
				extends StateDispatch.Builder<V, Builder<V, T1, T2, T3>> {
			
			private final StateProperty<T1> p1;
			private final StateProperty<T2> p2;
			private final StateProperty<T3> p3;
			
			private Builder(
				final StateProperty<T1> p1,
				final StateProperty<T2> p2,
				final StateProperty<T3> p3
			) {
				this.p1 = Objects.requireNonNull(p1, "property1");
				this.p2 = Objects.requireNonNull(p2, "property2");
				this.p3 = Objects.requireNonNull(p3, "property3");
			}
			
			public Builder<V, T1, T2, T3> add(final T1 v1, final T2 v2, final T3 v3, final V value) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3), value);
			}
			
			public Builder<V, T1, T2, T3> generate(final TriFunction<? super T1, ? super T2, ? super T3, V> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.add(v1, v2, v3, generator.apply(v1, v2, v3)))));
				return this;
			}
			
			@Override
			public P3<V, T1, T2, T3> build() {
				this.validate();
				return new P3<>(this.values, this.properties, this.p1, this.p2, this.p3);
			}
		}
	}
	
	public static final class P4<
			V,
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>,
			T4 extends Comparable<T4>>
			extends StateDispatch<V> {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		private final StateProperty<T4> p4;
		
		private P4(
			final Map<StateSelector, V> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1,
			final StateProperty<T2> p2,
			final StateProperty<T3> p3,
			final StateProperty<T4> p4
		) {
			super(values, properties);
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.p4 = p4;
		}
		
		@Override
		public Builder<V, T1, T2, T3, T4> toBuilder() {
			return StateDispatch.<V, T1, T2, T3, T4>of(this.p1, this.p2, this.p3, this.p4).from(this);
		}
		
		public static final class Builder<
				V,
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>,
				T4 extends Comparable<T4>>
				extends StateDispatch.Builder<V, Builder<V, T1, T2, T3, T4>> {
			
			private final StateProperty<T1> p1;
			private final StateProperty<T2> p2;
			private final StateProperty<T3> p3;
			private final StateProperty<T4> p4;
			
			private Builder(
				final StateProperty<T1> p1,
				final StateProperty<T2> p2,
				final StateProperty<T3> p3,
				final StateProperty<T4> p4
			) {
				this.p1 = Objects.requireNonNull(p1, "property1");
				this.p2 = Objects.requireNonNull(p2, "property2");
				this.p3 = Objects.requireNonNull(p3, "property3");
				this.p4 = Objects.requireNonNull(p4, "property4");
			}
			
			public Builder<V, T1, T2, T3, T4> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final V value) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4), value);
			}
			
			public Builder<V, T1, T2, T3, T4> generate(final QuadFunction<? super T1, ? super T2, ? super T3, ? super T4, V> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.p4.possibleValues().forEach(v4 ->
												this.add(v1, v2, v3, v4, generator.apply(v1, v2, v3, v4))))));
				return this;
			}
			
			@Override
			public P4<V, T1, T2, T3, T4> build() {
				this.validate();
				return new P4<>(this.values, this.properties, this.p1, this.p2, this.p3, this.p4);
			}
		}
	}
	
	public static final class P5<
			V,
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>,
			T4 extends Comparable<T4>,
			T5 extends Comparable<T5>>
			extends StateDispatch<V> {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		private final StateProperty<T4> p4;
		private final StateProperty<T5> p5;
		
		private P5(
			final Map<StateSelector, V> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1,
			final StateProperty<T2> p2,
			final StateProperty<T3> p3,
			final StateProperty<T4> p4,
			final StateProperty<T5> p5
		) {
			super(values, properties);
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.p4 = p4;
			this.p5 = p5;
		}
		
		@Override
		public Builder<V, T1, T2, T3, T4, T5> toBuilder() {
			return StateDispatch.<V, T1, T2, T3, T4, T5>of(this.p1, this.p2, this.p3, this.p4, this.p5).from(this);
		}
		
		public static final class Builder<
				V,
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>,
				T4 extends Comparable<T4>,
				T5 extends Comparable<T5>>
				extends StateDispatch.Builder<V, Builder<V, T1, T2, T3, T4, T5>> {
			
			private final StateProperty<T1> p1;
			private final StateProperty<T2> p2;
			private final StateProperty<T3> p3;
			private final StateProperty<T4> p4;
			private final StateProperty<T5> p5;
			
			private Builder(
				final StateProperty<T1> p1,
				final StateProperty<T2> p2,
				final StateProperty<T3> p3,
				final StateProperty<T4> p4,
				final StateProperty<T5> p5
			) {
				this.p1 = Objects.requireNonNull(p1, "property1");
				this.p2 = Objects.requireNonNull(p2, "property2");
				this.p3 = Objects.requireNonNull(p3, "property3");
				this.p4 = Objects.requireNonNull(p4, "property4");
				this.p5 = Objects.requireNonNull(p5, "property5");
			}
			
			public Builder<V, T1, T2, T3, T4, T5> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5, final V value) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4).add(this.p5, v5), value);
			}
			
			public Builder<V, T1, T2, T3, T4, T5> generate(final QuinFunction<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, V> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.p4.possibleValues().forEach(v4 ->
												this.p5.possibleValues().forEach(v5 ->
														this.add(v1, v2, v3, v4, v5, generator.apply(v1, v2, v3, v4, v5)))))));
				return this;
			}
			
			@Override
			public P5<V, T1, T2, T3, T4, T5> build() {
				this.validate();
				return new P5<>(this.values, this.properties, this.p1, this.p2, this.p3, this.p4, this.p5);
			}
		}
	}
	
	public static class Builder<V, B extends Builder<V, B>> implements
			org.spongepowered.api.util.Builder<StateDispatch<V>, B>,
			CopyableBuilder<StateDispatch<V>, B> {
		
		protected final Map<StateSelector, V> values = new HashMap<>();
		protected final Set<StateProperty<?>> properties = new HashSet<>();
		private final Set<String> propertyNames = new HashSet<>();
		
		protected Builder() {
			this.reset();
		}
		
		@SuppressWarnings("unchecked")
		private B cast() {
			return (B) this;
		}
		
		public B addAll(final Map<StateSelector, V> values) {
			Objects.requireNonNull(values, "values").forEach(this::add);
			return this.cast();
		}
		
		public B add(final State<?> state, final V value) {
			return this.add(StateSelector.of(state), value);
		}
		
		public B add(final StateSelector.Builder selector, final V value) {
			return this.add(selector.build(), value);
		}
		
		public B add(final StateSelector selector, final V value) {
			this.validateInput(selector, value);
			if (this.values.containsKey(selector)) {
				this.values.put(selector, value);
				return this.cast();
			}
			
			for (final StateProperty<?> property : selector.properties()) {
				final String propertyName = property.name();
				if (this.propertyNames.contains(propertyName) && !this.properties.contains(property)) {
					throw new IllegalArgumentException(String.format(
							"Given selector has property with name '%s', " +
							"but this dispatch already contains another property with the same name",
							propertyName
							));
				}
			}
			
			for (final StateSelector current : this.values.keySet()) {
				if (current.overlaps(selector)) {
					throw this.overlap(selector, current);
				}
			}
			
			this.putEntry(selector, value);
			return this.cast();
		}
		
		public B expand(final State<?> state, final V value) {
			return this.expand(StateSelector.of(state), value);
		}
		
		public B expand(final StateSelector.Builder selector, final V value) {
			return this.expand(selector.build(), value);
		}
		
		public B expand(final StateSelector selector, final V value) {
			this.validateInput(selector, value);
			if (this.values.containsKey(selector)) {
				this.values.put(selector, value);
				return this.cast();
			}
			
			final var values = this.values.entrySet().iterator();
			final Map<StateSelector, V> valuesToAdd = new HashMap<>();
			while (values.hasNext()) {
				final var entry = values.next();
				final StateSelector oldSelector = entry.getKey();
				final V oldValue = entry.getValue();
				
				if (oldSelector.test(selector)) {
					values.remove();
					final Stream<StateProperty<?>> newProperties = selector.properties().stream()
							.filter(property -> !oldSelector.properties().contains(property));
					
					StateSelector.populate(newProperties::iterator)
							.map(oldSelector::with)
							.forEach(newSelector -> valuesToAdd.put(newSelector, oldValue));
					
					valuesToAdd.put(selector, value);
					
				} else if (oldSelector.overlaps(selector)) {
					throw this.overlap(selector, oldSelector);
				}
			}
			
			valuesToAdd.forEach(this::putEntry);
			return this.cast();
		}
		
		@Override
		public B reset() {
			this.values.clear();
			this.properties.clear();
			this.propertyNames.clear();
			return this.cast();
		}
		
		@Override
		public B from(final StateDispatch<V> dispatch) {
			return this.reset().addAll(Objects.requireNonNull(dispatch, "dispatch").values());
		}
		
		@Override
		public StateDispatch<V> build() {
			this.validate();
			return new StateDispatch<V>(this.values, this.properties);
		}
		
		private void validateInput(final StateSelector selector, final V value) {
			Objects.requireNonNull(selector, "selector");
		}
		
		private RuntimeException overlap(final StateSelector given, final StateSelector current) {
			return new IllegalArgumentException(String.format(
					"Given selector (%s) overlaps with the selector in this dispatch (%s)",
					given.serializationString(), current.serializationString()
					));
		}
		
		private void putEntry(final StateSelector selector, final V value) {
			this.values.put(selector, value);
			this.properties.addAll(selector.properties());
			selector.properties().forEach(property -> this.propertyNames.add(property.name()));
		}
		
		protected void validate() {
			StateDispatch.validate(this.values, this.properties).ifPresent(msg -> {
				throw new IllegalStateException(msg);
			});
		}
	}
}
