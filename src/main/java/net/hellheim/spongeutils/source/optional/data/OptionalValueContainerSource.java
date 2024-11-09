package net.hellheim.spongeutils.source.optional.data;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface OptionalValueContainerSource extends ValueContainer {
	
	Optional<? extends ValueContainer> getAsDataHolder();
	
	@Override
	default <E> Optional<E> get(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder().flatMap(h -> h.get(key));
	}
	
	@Override
	default OptionalInt getInt(final Key<? extends Value<Integer>> key) {
		return this.getAsDataHolder().map(h -> h.getInt(key)).orElse(OptionalInt.empty());
	}
	
	@Override
	default OptionalDouble getDouble(final Key<? extends Value<Double>> key) {
		return this.getAsDataHolder().map(h -> h.getDouble(key)).orElse(OptionalDouble.empty());
	}
	
	@Override
	default OptionalLong getLong(final Key<? extends Value<Long>> key) {
		return this.getAsDataHolder().map(h -> h.getLong(key)).orElse(OptionalLong.empty());
	}
	
	@Override
	default <E> E require(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder()
				.orElseThrow(() -> new IllegalStateException("DataHolder is not present"))
				.require(key);
	}
	
	@Override
	default <E> E getOrNull(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder().map(h -> h.getOrNull(key)).orElse(null);
	}
	
	@Override
	default <E> E getOrElse(final Key<? extends Value<E>> key, E defaultValue) {
		return this.getAsDataHolder().map(h -> h.getOrElse(key, defaultValue)).orElse(defaultValue);
	}
	
	@Override
	default <E, V extends Value<E>> Optional<V> getValue(final Key<V> key) {
		return this.getAsDataHolder().map(h -> h.getValue(key)).orElse(Optional.empty());
	}
	
	@Override
	default <E, V extends Value<E>> V requireValue(final Key<V> key) {
		return this.getAsDataHolder()
				.orElseThrow(() -> new IllegalStateException("DataHolder is not present"))
				.requireValue(key);
	}
	
	@Override
	default boolean supports(final Key<?> key) {
		return this.getAsDataHolder().map(h -> h.supports(key)).orElse(false);
	}
	
	@Override
	default boolean supports(final Value<?> value) {
		return this.getAsDataHolder().map(h ->  h.supports(value)).orElse(false);
	}
	
	@Override
	default Set<Key<?>> getKeys() {
		return this.getAsDataHolder().map(h -> h.getKeys()).orElse(Set.of());
	}
	
	@Override
	default Set<Value.Immutable<?>> getValues() {
		return this.getAsDataHolder().map(h -> h.getValues()).orElse(Set.of());
	}
}
