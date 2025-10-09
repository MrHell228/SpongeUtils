package net.hellheim.spongetools.resourcepack.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;

public interface StateCondition extends StatePredicate {
	
	Codec<StateCondition> CODEC = StateCodec.STATE_CONDITION;
	
	Codec<List<StateCondition>> LIST_CODEC = StateCondition.CODEC.listOf();
	
	static StateCondition alwaysTrue() {
		return CompositeCondition.ALWAYS_TRUE;
	}
	
	static StateCondition alwaysFalse() {
		return CompositeCondition.ALWAYS_FALSE;
	}
	
	static StateCondition always(final boolean value) {
		return value ? StateCondition.alwaysTrue() : StateCondition.alwaysFalse();
	}
	
	static AndCondition.Builder and() {
		return new AndCondition.Builder();
	}
	
	static StateCondition and(final Consumer<AndCondition.Builder> configurator) {
		final AndCondition.Builder builder = StateCondition.and();
		Objects.requireNonNull(configurator, "configurator").accept(builder);
		return builder.build();
	}
	
	static StateCondition and(final StateCondition... conditions) {
		return StateCondition.and().add(conditions).build();
	}
	
	static StateCondition and(final Iterable<? extends StateCondition> conditions) {
		return StateCondition.and().add(conditions).build();
	}
	
	static StateCondition and(final Stream<? extends StateCondition> conditions) {
		return StateCondition.and().add(conditions).build();
	}
	
	static OrCondition.Builder or() {
		return new OrCondition.Builder();
	}
	
	static StateCondition or(final Consumer<OrCondition.Builder> configurator) {
		final OrCondition.Builder builder = StateCondition.or();
		Objects.requireNonNull(configurator, "configurator").accept(builder);
		return builder.build();
	}
	
	static StateCondition or(final StateCondition... conditions) {
		return StateCondition.or().add(conditions).build();
	}
	
	static StateCondition or(final Iterable<? extends StateCondition> conditions) {
		return StateCondition.or().add(conditions).build();
	}
	
	static StateCondition or(final Stream<? extends StateCondition> conditions) {
		return StateCondition.or().add(conditions).build();
	}
	
	static <T extends Comparable<T>> StateCondition property(
		final StateProperty<T> property, final boolean inverse, final Collection<? extends T> values
	) {
		return PropertyCondition.of(property, inverse, Set.copyOf(values));
	}
	
	@SafeVarargs
	static <T extends Comparable<T>> StateCondition property(
		final StateProperty<T> property, final boolean inverse, final T... values
	) {
		return StateCondition.property(property, inverse, Set.of(values));
	}
	
	@SafeVarargs
	static <T extends Comparable<T>> StateCondition is(
		final StateProperty<T> property, final T... values
	) {
		return StateCondition.property(property, false, values);
	}
	
	static <T extends Comparable<T>> StateCondition is(
		final StateProperty<T> property, final Collection<? extends T> values
	) {
		return StateCondition.property(property, false, values);
	}
	
	static <T extends Comparable<T>> StateCondition is(
		final StatePropertyValue<T> value
	) {
		return StateCondition.is(value.property(), value.value());
	}
	
	static StateCondition is(final State<?> state) {
		return StateCondition.and(StatePropertyValue.allOf(state).map(StateCondition::is));
	}
	
	@SafeVarargs
	static <T extends Comparable<T>> StateCondition not(
		final StateProperty<T> property, final T... values
	) {
		return StateCondition.property(property, true, values);
	}
	
	static <T extends Comparable<T>> StateCondition not(
		final StateProperty<T> property, final Collection<? extends T> values
	) {
		return StateCondition.property(property, true, values);
	}
	
	static <T extends Comparable<T>> StateCondition not(
		final StatePropertyValue<T> value
	) {
		return StateCondition.not(value.property(), value.value());
	}
	
	static StateCondition not(final State<?> state) {
		return StateCondition.or(StatePropertyValue.allOf(state).map(StateCondition::not));
	}
	
	Set<StateProperty<?>> properties();
	
	StateCondition negate();
	
	abstract class CompositeCondition implements StateCondition {
		
		private static final AndCondition ALWAYS_TRUE = new AndCondition(List.of(), Set.of());
		private static final OrCondition ALWAYS_FALSE = new OrCondition(List.of(), Set.of());
		
		private final List<StateCondition> conditions;
		private final Set<StateProperty<?>> properties;
		
		private CompositeCondition(
			final List<StateCondition> conditions, final Set<StateProperty<?>> properties
		) {
			this.conditions = List.copyOf(conditions);
			this.properties = Set.copyOf(properties);
		}
		
		protected abstract StateCondition negate0();
		
		public List<StateCondition> conditions() {
			return this.conditions;
		}
		
		@Override
		public Set<StateProperty<?>> properties() {
			return this.properties;
		}
		
		@Override
		public StateCondition negate() {
			if (this == StateCondition.alwaysFalse()) {
				return StateCondition.alwaysTrue();
			} else if (this == StateCondition.alwaysTrue()) {
				return StateCondition.alwaysFalse();
			} else {
				return this.negate0();
			}
		}
		
		public static abstract class Builder<B extends Builder<B>>
				implements org.spongepowered.api.util.Builder<StateCondition, B> {
			
			private final boolean bias;
			protected final List<StateCondition> conditions = new ArrayList<>();
			protected boolean always;
			
			private Builder(final boolean bias) {
				this.bias = bias;
				this.reset();
			}
			
			@SuppressWarnings("unchecked")
			private B cast() {
				return (B) this;
			}
			
			protected abstract void tryAdd(StateCondition condition);
			
			protected abstract <T extends Comparable<T>> StateCondition merge(
				PropertyCondition<T> first, PropertyCondition<T> second
			);
			
			protected abstract CompositeCondition build0(Set<StateProperty<?>> properties);
			
			public B add(final StateCondition... conditions) {
				return this.add(Arrays.stream(conditions));
			}
			
			public B add(final Iterable<? extends StateCondition> conditions) {
				return this.add(Streams.stream(conditions));
			}
			
			public B add(final Stream<? extends StateCondition> conditions) {
				Objects.requireNonNull(conditions, "conditions").forEach(condition -> {
					this.addSingle(Objects.requireNonNull(condition, "condition"));
				});
				
				return this.cast();
			}
			
			private void addSingle(final StateCondition condition) {
				if (this.always) {
					return;
				} else if (condition instanceof final PropertyCondition<?> property) {
					this.merge(property);
				} else {
					this.tryAdd(condition);
				}
			}
			
			private <T extends Comparable<T>> void merge(final PropertyCondition<T> condition) {
				@SuppressWarnings("unchecked")
				final @Nullable PropertyCondition<T> current = this.conditions.stream()
						.filter(PropertyCondition.class::isInstance)
						.map(PropertyCondition.class::cast)
						.filter(c -> c.property().equals(condition.property()))
						.map(c -> (PropertyCondition<T>) c)
						.findAny()
						.orElse(null);
				
				if (current == null) {
					this.tryAdd(condition);
				} else {
					this.conditions.remove(current);
					this.tryAdd(this.merge(current, condition));
				}
			}
			
			@Override
			public B reset() {
				this.conditions.clear();
				this.always = false;
				return this.cast();
			}
			
			@Override
			public StateCondition build() {
				if (this.conditions.isEmpty()) {
					return StateCondition.always(this.bias);
				} else if (this.conditions.size() == 1) {
					return this.conditions.get(0);
				}
				
				final Set<StateProperty<?>> properties = this.conditions.stream()
						.flatMap(condition -> condition.properties().stream())
						.collect(Collectors.toSet());
				final CompositeCondition condition = this.build0(properties);
				
				final Iterator<StateSelector> selectors = StateSelector.populate(properties).iterator();
				int positive = 0, negative = 0;
				while (selectors.hasNext()) {
					final StateSelector selector = selectors.next();
					if (condition.test(selector)) {
						++positive;
					} else {
						++negative;
					}
				}
				
				if (positive == 0) {
					return StateCondition.alwaysFalse();
				} else if (negative == 0) {
					return StateCondition.alwaysTrue();
				} else {
					return condition;
				}
			}
		}
	}
	
	final class AndCondition extends CompositeCondition {
		
		private AndCondition(
			final List<StateCondition> conditions, final Set<StateProperty<?>> properties
		) {
			super(conditions, properties);
		}
		
		@Override
		public boolean test(
			final Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup
		) {
			for (final StateCondition condition : this.conditions()) {
				if (!condition.test(propertyLookup)) {
					return false;
				}
			}
			
			return true;
		}
		
		@Override
		protected StateCondition negate0() {
			return new OrCondition(
					this.conditions().stream().map(StateCondition::negate).toList(),
					this.properties());
		}
		
		public static final class Builder extends CompositeCondition.Builder<Builder> {
			
			private Builder() {
				super(true);
			}
			
			@Override
			protected void tryAdd(final StateCondition condition) {
				if (condition instanceof final AndCondition and) {
					this.add(and.conditions());
				} else if (condition == StateCondition.alwaysFalse()) {
					this.conditions.clear();
					this.always = true;
				} else if (condition != StateCondition.alwaysTrue()) {
					this.conditions.add(condition);
				}
			}
			
			@Override
			protected <T extends Comparable<T>> @Nullable StateCondition merge(
				final PropertyCondition<T> first, final PropertyCondition<T> second
			) {
				final StateProperty<T> property = first.property();
				final boolean inverse = first.inverse() && second.inverse();
				final Set<T> values = first.inverse() == second.inverse()
						? first.inverse()
								? Sets.union(first.values(), second.values())
								: Sets.intersection(first.values(), second.values())
						: first.inverse()
								? Sets.difference(second.values(), first.values())
								: Sets.difference(first.values(), second.values());
				
				return StateCondition.property(property, inverse, values);
			};
			
			@Override
			protected CompositeCondition build0(final Set<StateProperty<?>> properties) {
				return new AndCondition(this.conditions, properties);
			}
		}
	}
	
	final class OrCondition extends CompositeCondition {
		
		private OrCondition(
			final List<StateCondition> conditions, final Set<StateProperty<?>> properties
		) {
			super(conditions, properties);
		}
		
		@Override
		public boolean test(
			final Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup
		) {
			for (final StateCondition condition : this.conditions()) {
				if (condition.test(propertyLookup)) {
					return true;
				}
			}
			
			return false;
		}
		
		@Override
		protected StateCondition negate0() {
			return new AndCondition(
					this.conditions().stream().map(StateCondition::negate).toList(),
					this.properties());
		}
		
		public static final class Builder extends CompositeCondition.Builder<Builder> {
			
			private Builder() {
				super(false);
			}
			
			@Override
			protected void tryAdd(final StateCondition condition) {
				if (condition instanceof final OrCondition or) {
					this.add(or.conditions());
				} else if (condition == StateCondition.alwaysTrue()) {
					this.conditions.clear();
					this.always = true;
				} else if (condition != StateCondition.alwaysFalse()) {
					this.conditions.add(condition);
				}
			}
			
			@Override
			protected <T extends Comparable<T>> StateCondition merge(
				final PropertyCondition<T> first, final PropertyCondition<T> second
			) {
				final StateProperty<T> property = first.property();
				final boolean inverse = !first.inverse() && !second.inverse();
				final Set<T> values = first.inverse() == second.inverse()
						? first.inverse()
								? Sets.intersection(first.values(), second.values())
								: Sets.union(first.values(), second.values())
						: first.inverse()
								? Sets.difference(first.values(), second.values())
								: Sets.difference(second.values(), first.values());
				
				return StateCondition.property(property, inverse, values);
			};
			
			@Override
			protected CompositeCondition build0(final Set<StateProperty<?>> properties) {
				return new OrCondition(this.conditions, properties);
			}
		}
	}
	
	final class PropertyCondition<T extends Comparable<T>> implements StateCondition {
		
		private final StateProperty<T> property;
		private final boolean inverse;
		private final Set<T> values;
		
		private PropertyCondition(
			final StateProperty<T> property, final boolean inverse, final Set<T> values
		) {
			this.property = property;
			this.inverse = inverse;
			this.values = ImmutableSortedSet.copyOf(values);
		}
		
		private static <T extends Comparable<T>> StateCondition of(
			final StateProperty<T> property, final boolean inverse, final Set<T> values
		) {
			Objects.requireNonNull(property, "property");
			Objects.requireNonNull(values, "values");
			if (values.isEmpty()) {
				return StateCondition.always(inverse);
			}
			
			final Set<T> possibleValues = Set.copyOf(property.possibleValues());
			final Set<T> illegalValues = Sets.difference(values, possibleValues);
			if (!illegalValues.isEmpty()) {
				throw new IllegalArgumentException(String.format(
						"Property '%s' does not support values '%'",
						property.name(), illegalValues
						));
			}
			
			if (values.size() == possibleValues.size()) {
				return StateCondition.always(!inverse);
			}
			
			return new PropertyCondition<>(property, inverse, values);
		}
		
		public StateProperty<T> property() {
			return this.property;
		}
		
		public boolean inverse() {
			return this.inverse;
		}
		
		public Set<T> values() {
			return this.values;
		}
		
		@Override
		public boolean test(
			final Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup
		) {
			return propertyLookup.apply(this.property)
					.map(value -> this.inverse ^ this.values.contains(value))
					.orElse(false);
		}
		
		@Override
		public Set<StateProperty<?>> properties() {
			return Set.of(this.property);
		}
		
		@Override
		public StateCondition.PropertyCondition<T> negate() {
			return new PropertyCondition<>(this.property, !this.inverse, this.values);
		}
		
		public String valuesString() {
			final String values = this.values.stream()
					.map(value -> StatePropertyValue.of(this.property, value).valueName())
					.collect(Collectors.joining("|"));
			return this.inverse ? "!" + values : values;
		}
	}
}
