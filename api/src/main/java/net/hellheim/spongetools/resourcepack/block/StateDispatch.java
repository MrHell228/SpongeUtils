package net.hellheim.spongetools.resourcepack.block;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.hellheim.spongetools.function.QuadFunction;
import net.hellheim.spongetools.function.QuinFunction;
import net.hellheim.spongetools.function.TriFunction;

public class StateDispatch {
	
	public static final Codec<StateDispatch> CODEC = Codec
			.unboundedMap(StateSelector.CODEC, Variant.LIST_CODEC)
			.xmap(StateDispatch::new, StateDispatch::values)
			.validate(StateDispatch::validate);
	
	private final Set<StateProperty<?>> properties;
	private final Map<StateSelector, List<Variant>> values;
	
	protected StateDispatch(final Map<StateSelector, List<Variant>> values) {
		this(values, values.keySet().stream()
				.flatMap(selector -> selector.properties().stream())
				.collect(Collectors.toSet()));
	}
	
	protected StateDispatch(final Map<StateSelector, List<Variant>> values, final Set<StateProperty<?>> properties) {
		this.values = Map.copyOf(values);
		this.properties = Set.copyOf(properties);
	}
	
	public static StateDispatch of(final Variant... variants) {
		return StateDispatch.raw().add(StateSelector.empty(), variants).build();
	}
	
	public static StateDispatch of(final Collection<Variant> variants) {
		return StateDispatch.raw().add(StateSelector.empty(), variants).build();
	}
	
	public static Builder<?> raw() {
		return new Builder<>();
	}
	
	public static <
		T1 extends Comparable<T1>>
	P1.Builder<T1> of(
		final StateProperty<T1> p1
	) {
		return new P1.Builder<>(p1);
	}
	
	public static <
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>>
	P2.Builder<T1, T2> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2
	) {
		return new P2.Builder<>(p1, p2);
	}
	
	public static <
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>>
	P3.Builder<T1, T2, T3> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3
	) {
		return new P3.Builder<>(p1, p2, p3);
	}
	
	public static <
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>,
		T4 extends Comparable<T4>>
	P4.Builder<T1, T2, T3, T4> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3,
		final StateProperty<T4> p4
	) {
		return new P4.Builder<>(p1, p2, p3, p4);
	}
	
	public static <
		T1 extends Comparable<T1>,
		T2 extends Comparable<T2>,
		T3 extends Comparable<T3>,
		T4 extends Comparable<T4>,
		T5 extends Comparable<T5>>
	P5.Builder<T1, T2, T3, T4, T5> of(
		final StateProperty<T1> p1,
		final StateProperty<T2> p2,
		final StateProperty<T3> p3,
		final StateProperty<T4> p4,
		final StateProperty<T5> p5
	) {
		return new P5.Builder<>(p1, p2, p3, p4, p5);
	}
	
	private static DataResult<StateDispatch> validate(final StateDispatch dispatch) {
		return StateDispatch.validate(dispatch.values, dispatch.properties)
				.<DataResult<StateDispatch>>map(msg -> DataResult.error(() -> msg))
				.orElse(DataResult.success(dispatch));
	}
	
	private static Optional<String> validate(
		final Map<StateSelector, List<Variant>> values,
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
	
	public Map<StateSelector, List<Variant>> values() {
		return this.values;
	}
	
	public Builder<?> toBuilder() {
		return StateDispatch.raw().from(this);
	}
	
	public static final class P1<
			T1 extends Comparable<T1>>
			extends StateDispatch {
		
		private final StateProperty<T1> p1;
		
		private P1(
			final Map<StateSelector, List<Variant>> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1
		) {
			super(values, properties);
			this.p1 = p1;
		}
		
		@Override
		public Builder<T1> toBuilder() {
			return StateDispatch.of(this.p1);
		}
		
		public static final class Builder<
				T1 extends Comparable<T1>>
				extends StateDispatch.Builder<Builder<T1>> {
			
			private final StateProperty<T1> p1;
			
			private Builder(
				final StateProperty<T1> p1
			) {
				this.p1 = Objects.requireNonNull(p1, "property");
			}
			
			public Builder<T1> add(final T1 v1, final Variant... variants) {
				return this.add(StateSelector.builder().add(this.p1, v1), variants);
			}
			
			public Builder<T1> add(final T1 v1, final Collection<Variant> variants) {
				return this.add(StateSelector.builder().add(this.p1, v1), variants);
			}
			
			public Builder<T1> generate(final Function<? super T1, Variant> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.add(v1, generator.apply(v1)));
				return this;
			}
			
			public Builder<T1> generateList(final Function<? super T1, ? extends Collection<Variant>> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.add(v1, generator.apply(v1)));
				return this;
			}
			
			@Override
			public P1<T1> build() {
				this.validate();
				return new P1<>(this.values, this.properties, this.p1);
			}
		}
	}
	
	public static final class P2<
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>>
			extends StateDispatch {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		
		private P2(
			final Map<StateSelector, List<Variant>> values,
			final Set<StateProperty<?>> properties,
			final StateProperty<T1> p1,
			final StateProperty<T2> p2
		) {
			super(values, properties);
			this.p1 = p1;
			this.p2 = p2;
		}
		
		@Override
		public Builder<T1, T2> toBuilder() {
			return StateDispatch.of(this.p1, this.p2);
		}
		
		public static final class Builder<
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>>
				extends StateDispatch.Builder<Builder<T1, T2>> {
			
			private final StateProperty<T1> p1;
			private final StateProperty<T2> p2;
			
			private Builder(
				final StateProperty<T1> p1,
				final StateProperty<T2> p2
			) {
				this.p1 = Objects.requireNonNull(p1, "property1");
				this.p2 = Objects.requireNonNull(p2, "property2");
			}
			
			public Builder<T1, T2> add(final T1 v1, final T2 v2, final Variant... variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2), variants);
			}
			
			public Builder<T1, T2> add(final T1 v1, final T2 v2, final Collection<Variant> variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2), variants);
			}
			
			public Builder<T1, T2> generate(final BiFunction<? super T1, ? super T2, Variant> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.add(v1, v2, generator.apply(v1, v2))));
				return this;
			}
			
			public Builder<T1, T2> generateList(final BiFunction<? super T1, ? super T2, ? extends Collection<Variant>> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.add(v1, v2, generator.apply(v1, v2))));
				return this;
			}
			
			@Override
			public P2<T1, T2> build() {
				this.validate();
				return new P2<>(this.values, this.properties, this.p1, this.p2);
			}
		}
	}
	
	public static final class P3<
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>>
			extends StateDispatch {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		
		private P3(
			final Map<StateSelector, List<Variant>> values,
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
		public Builder<T1, T2, T3> toBuilder() {
			return StateDispatch.of(this.p1, this.p2, this.p3);
		}
		
		public static final class Builder<
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>>
				extends StateDispatch.Builder<Builder<T1, T2, T3>> {
			
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
			
			public Builder<T1, T2, T3> add(final T1 v1, final T2 v2, final T3 v3, final Variant... variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3), variants);
			}
			
			public Builder<T1, T2, T3> add(final T1 v1, final T2 v2, final T3 v3, final Collection<Variant> variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3), variants);
			}
			
			public Builder<T1, T2, T3> generate(final TriFunction<? super T1, ? super T2, ? super T3, Variant> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.add(v1, v2, v3, generator.apply(v1, v2, v3)))));
				return this;
			}
			
			public Builder<T1, T2, T3> generateList(final TriFunction<? super T1, ? super T2, ? super T3, ? extends Collection<Variant>> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.add(v1, v2, v3, generator.apply(v1, v2, v3)))));
				return this;
			}
			
			@Override
			public P3<T1, T2, T3> build() {
				this.validate();
				return new P3<>(this.values, this.properties, this.p1, this.p2, this.p3);
			}
		}
	}
	
	public static final class P4<
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>,
			T4 extends Comparable<T4>>
			extends StateDispatch {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		private final StateProperty<T4> p4;
		
		private P4(
			final Map<StateSelector, List<Variant>> values,
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
		public Builder<T1, T2, T3, T4> toBuilder() {
			return StateDispatch.of(this.p1, this.p2, this.p3, this.p4);
		}
		
		public static final class Builder<
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>,
				T4 extends Comparable<T4>>
				extends StateDispatch.Builder<Builder<T1, T2, T3, T4>> {
			
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
			
			public Builder<T1, T2, T3, T4> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final Variant... variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4), variants);
			}
			
			public Builder<T1, T2, T3, T4> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final Collection<Variant> variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4), variants);
			}
			
			public Builder<T1, T2, T3, T4> generate(final QuadFunction<? super T1, ? super T2, ? super T3, ? super T4, Variant> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.p4.possibleValues().forEach(v4 ->
												this.add(v1, v2, v3, v4, generator.apply(v1, v2, v3, v4))))));
				return this;
			}
			
			public Builder<T1, T2, T3, T4> generateList(final QuadFunction<? super T1, ? super T2, ? super T3, ? super T4, ? extends Collection<Variant>> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.p4.possibleValues().forEach(v4 ->
												this.add(v1, v2, v3, v4, generator.apply(v1, v2, v3, v4))))));
				return this;
			}
			
			@Override
			public P4<T1, T2, T3, T4> build() {
				this.validate();
				return new P4<>(this.values, this.properties, this.p1, this.p2, this.p3, this.p4);
			}
		}
	}
	
	public static final class P5<
			T1 extends Comparable<T1>,
			T2 extends Comparable<T2>,
			T3 extends Comparable<T3>,
			T4 extends Comparable<T4>,
			T5 extends Comparable<T5>>
			extends StateDispatch {
		
		private final StateProperty<T1> p1;
		private final StateProperty<T2> p2;
		private final StateProperty<T3> p3;
		private final StateProperty<T4> p4;
		private final StateProperty<T5> p5;
		
		private P5(
			final Map<StateSelector, List<Variant>> values,
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
		public Builder<T1, T2, T3, T4, T5> toBuilder() {
			return StateDispatch.of(this.p1, this.p2, this.p3, this.p4, this.p5);
		}
		
		public static final class Builder<
				T1 extends Comparable<T1>,
				T2 extends Comparable<T2>,
				T3 extends Comparable<T3>,
				T4 extends Comparable<T4>,
				T5 extends Comparable<T5>>
				extends StateDispatch.Builder<Builder<T1, T2, T3, T4, T5>> {
			
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
			
			public Builder<T1, T2, T3, T4, T5> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5, final Variant... variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4).add(this.p5, v5), variants);
			}
			
			public Builder<T1, T2, T3, T4, T5> add(final T1 v1, final T2 v2, final T3 v3, final T4 v4, final T5 v5, final Collection<Variant> variants) {
				return this.add(StateSelector.builder().add(this.p1, v1).add(this.p2, v2).add(this.p3, v3).add(this.p4, v4), variants);
			}
			
			public Builder<T1, T2, T3, T4, T5> generate(final QuinFunction<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, Variant> generator) {
				Objects.requireNonNull(generator, "generator");
				this.p1.possibleValues().forEach(v1 ->
						this.p2.possibleValues().forEach(v2 ->
								this.p3.possibleValues().forEach(v3 ->
										this.p4.possibleValues().forEach(v4 ->
												this.p5.possibleValues().forEach(v5 ->
														this.add(v1, v2, v3, v4, v5, generator.apply(v1, v2, v3, v4, v5)))))));
				return this;
			}
			
			public Builder<T1, T2, T3, T4, T5> generateList(final QuinFunction<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends Collection<Variant>> generator) {
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
			public P5<T1, T2, T3, T4, T5> build() {
				this.validate();
				return new P5<>(this.values, this.properties, this.p1, this.p2, this.p3, this.p4, this.p5);
			}
		}
	}
	
	public static class Builder<B extends Builder<B>> implements
			org.spongepowered.api.util.Builder<StateDispatch, B>,
			CopyableBuilder<StateDispatch, B> {
		
		protected final Map<StateSelector, List<Variant>> values = new HashMap<>();
		protected final Set<StateProperty<?>> properties = new HashSet<>();
		private final Set<String> propertyNames = new HashSet<>();
		
		protected Builder() {
			this.reset();
		}
		
		@SuppressWarnings("unchecked")
		private B cast() {
			return (B) this;
		}
		
		public B addAll(final Map<StateSelector, ? extends Collection<Variant>> values) {
			Objects.requireNonNull(values, "values").forEach(this::add);
			return this.cast();
		}
		
		public B add(final State<?> state, final Variant... variants) {
			return this.add(StateSelector.of(state), variants);
		}
		
		public B add(final State<?> state, final Collection<Variant> variants) {
			return this.add(StateSelector.of(state), variants);
		}
		
		public B add(final StateSelector.Builder selector, final Variant... variants) {
			return this.add(selector.build(), variants);
		}
		
		public B add(final StateSelector.Builder selector, final Collection<Variant> variants) {
			return this.add(selector.build(), variants);
		}
		
		public B add(final StateSelector selector, final Variant... variants) {
			return this.add(selector, List.of(Objects.requireNonNull(variants, "variants")));
		}
		
		public B add(final StateSelector selector, final Collection<Variant> variants) {
			this.validateInput(selector, variants);
			if (this.values.containsKey(selector)) {
				this.values.put(selector, List.copyOf(variants));
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
			
			this.putEntry(selector, List.copyOf(variants));
			return this.cast();
		}
		
		public B expand(final StateSelector selector, final Variant... variants) {
			return this.expand(selector, List.of(variants));
		}
		
		public B expand(final StateSelector selector, final Collection<Variant> variants) {
			this.validateInput(selector, variants);
			if (this.values.containsKey(selector)) {
				this.values.put(selector, List.copyOf(variants));
				return this.cast();
			}
			
			final var values = this.values.entrySet().iterator();
			final Map<StateSelector, List<Variant>> valuesToAdd = new HashMap<>();
			while (values.hasNext()) {
				final var entry = values.next();
				final StateSelector oldSelector = entry.getKey();
				final List<Variant> oldVariants = entry.getValue();
				
				if (oldSelector.test(selector)) {
					values.remove();
					final Stream<StateProperty<?>> newProperties = selector.properties().stream()
							.filter(property -> !oldSelector.properties().contains(property));
					
					StateSelector.populate(newProperties::iterator)
							.map(oldSelector::with)
							.forEach(newSelector -> valuesToAdd.put(newSelector, oldVariants));
					
					valuesToAdd.put(selector, List.copyOf(variants));
					
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
		public B from(final StateDispatch dispatch) {
			return this.reset().addAll(Objects.requireNonNull(dispatch, "dispatch").values());
		}
		
		@Override
		public StateDispatch build() {
			this.validate();
			return new StateDispatch(this.values, this.properties);
		}
		
		private void validateInput(final StateSelector selector, final Collection<Variant> variants) {
			Objects.requireNonNull(selector, "selector");
			Variant.validate(variants);
		}
		
		private RuntimeException overlap(final StateSelector given, final StateSelector current) {
			return new IllegalArgumentException(String.format(
					"Given selector (%s) overlaps with the selector in this dispatch (%s)",
					given, current
					));
		}
		
		private void putEntry(final StateSelector selector, final List<Variant> variants) {
			this.values.put(selector, List.copyOf(variants));
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
