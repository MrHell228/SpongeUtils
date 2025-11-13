package net.hellheim.spongetools.resourcepack.block;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateProperty;
import org.spongepowered.api.util.CopyableBuilder;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

public final class StateSelector implements StringRepresentable, StatePredicate {
	
	public static final Codec<StateSelector> CODEC = StateCodec.STATE_SELECTOR;
	
	private static final StateSelector EMPTY = new StateSelector(Map.of());
	
	private static final Comparator<StatePropertyValue<?>> COMPARE_BY_NAME =
			Comparator.comparing(value -> value.property().name());
	
	private final Map<StateProperty<?>, StatePropertyValue<?>> values;
	
	private StateSelector(final Map<StateProperty<?>, StatePropertyValue<?>> values) {
		this.values = Map.copyOf(values);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static StateSelector empty() {
		return StateSelector.EMPTY;
	}
	
	public static StateSelector of(final State<?> state) {
		final Builder builder = StateSelector.builder();
		Objects.requireNonNull(state, "state").statePropertyMap().forEach((property, value) ->
				builder.add(StatePropertyValue.ofRaw(property, value)));
		return builder.build();
	}
	
	public static Stream<StateSelector> populate(final StateProperty<?>... properties) {
		return StateSelector.populate(List.of(properties));
	}
	
	public static Stream<StateSelector> populate(final Iterable<? extends StateProperty<?>> properties) {
		Objects.requireNonNull(properties, "properties");
		Stream<StateSelector> selectors = Stream.of(StateSelector.empty());
		for (final StateProperty<?> property : properties) {
			selectors = selectors.flatMap(selector ->
					property.possibleValues().stream().map(value ->
							selector.with(StatePropertyValue.ofRaw(property, value))));
		}
		return selectors;
	}
	
	public Set<StateProperty<?>> properties() {
		return this.values.keySet();
	}
	
	public Collection<StatePropertyValue<?>> values() {
		return this.values.values();
	}
	
	public <T extends Comparable<T>> Optional<T> get(final StateProperty<T> property) {
		@SuppressWarnings("unchecked")
		final @Nullable StatePropertyValue<T> value = (StatePropertyValue<T>) this.values.get(property);
		return value == null ? Optional.empty() : Optional.of(value.propertyValue());
	}
	
	public boolean overlaps(final StateSelector selector) {
		final var commonProperties = Sets.intersection(this.properties(), selector.properties());
		for (final StateProperty<?> property : commonProperties) {
			final var thisValue = this.values.get(property).propertyValue();
			final var thatValue = selector.values.get(property).propertyValue();
			if (!Objects.equals(thisValue, thatValue)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean test(final Function<StateProperty<?>, Optional<? extends Comparable<?>>> propertyLookup) {
		for (final StatePropertyValue<?> value : this.values()) {
			if (propertyLookup.apply(value.property()).map(v -> !value.propertyValue().equals(v)).orElse(true)) {
				return false;
			}
		}
		
		return true;
	}
	
	public Builder toBuilder() {
		return StateSelector.builder().from(this);
	}
	
	public <T extends Comparable<T>> StateSelector with(
		final StateProperty<T> property, final T value
	) {
		return this.toBuilder().add(property, value).build();
	}
	
	public <T extends Comparable<T>> StateSelector with(
		final StateProperty<T> property, final Supplier<? extends T> valueSupplier
	) {
		return this.toBuilder().add(property, valueSupplier).build();
	}
	
	public StateSelector with(final StatePropertyValue<?> value) {
		return this.toBuilder().add(value).build();
	}
	
	public StateSelector with(final StateSelector selector) {
		return this.toBuilder().addAll(selector).build();
	}
	
	@Override
	public String serializationString() {
		return this.values().stream()
				.sorted(StateSelector.COMPARE_BY_NAME)
				.map(StatePropertyValue::serializationString)
				.collect(Collectors.joining(","));
	}
	
	@Override
	public int hashCode() {
		return this.values.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			return this.values.equals(((StateSelector) obj).values);
		}
	}
	
	@Override
	public String toString() {
		return "StateSelector[" + this.serializationString() + "]";
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<StateSelector, Builder>,
			CopyableBuilder<StateSelector, Builder> {
		
		private final Map<StateProperty<?>, StatePropertyValue<?>> values = new HashMap<>();
		private final Set<String> propertyNames = new HashSet<>();
		
		public <T extends Comparable<T>> Builder add(final StateProperty<T> property, final T value) {
			return this.add(StatePropertyValue.of(property, value));
		}
		
		public <T extends Comparable<T>> Builder add(final StateProperty<T> property, final Supplier<? extends T> valueSupplier) {
			return this.add(StatePropertyValue.of(property, valueSupplier));
		}
		
		public Builder add(final StatePropertyValue<?> value) {
			final StateProperty<?> property = value.property();
			final String propertyName = property.name();
			if (this.propertyNames.contains(propertyName) && !this.values.containsKey(property)) {
				throw new IllegalArgumentException(String.format(
						"Given selector has property with name '%s', " +
						"but this selector already contains another property with the same name",
						propertyName
						));
			}
			
			this.values.put(property, value);
			this.propertyNames.add(propertyName);
			return this;
		}
		
		public Builder addAll(final StatePropertyValue<?>... values) {
			for (final StatePropertyValue<?> value : Objects.requireNonNull(values, "values")) {
				this.add(value);
			}
			return this;
		}
		
		public Builder addAll(final Iterable<? extends StatePropertyValue<?>> values) {
			for (final StatePropertyValue<?> value : Objects.requireNonNull(values, "values")) {
				this.add(value);
			}
			return this;
		}
		
		public Builder addAll(final StateSelector selector) {
			return this.addAll(Objects.requireNonNull(selector, "selector").values());
		}
		
		@Override
		public Builder from(final StateSelector selector) {
			return this.reset().addAll(selector);
		}
		
		@Override
		public Builder reset() {
			this.values.clear();
			this.propertyNames.clear();
			return this;
		}
		
		@Override
		public StateSelector build() {
			return this.values.isEmpty() ? StateSelector.empty() : new StateSelector(this.values);
		}
	}
}
