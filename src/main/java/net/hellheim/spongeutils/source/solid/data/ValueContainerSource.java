package net.hellheim.spongeutils.source.solid.data;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface ValueContainerSource extends ValueContainer {
	
	ValueContainer getAsDataHolder();
	
	@Override
	default <E> Optional<E> get(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder().get(key);
	}
	
	@Override
	default OptionalInt getInt(final Key<? extends Value<Integer>> key) {
		return this.getAsDataHolder().getInt(key);
	}
	
	@Override
	default OptionalDouble getDouble(final Key<? extends Value<Double>> key) {
		return this.getAsDataHolder().getDouble(key);
	}
	
	@Override
	default OptionalLong getLong(final Key<? extends Value<Long>> key) {
		return this.getAsDataHolder().getLong(key);
	}
	
	@Override
	default <E> E require(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder().require(key);
	}
	
	@Override
	default <E> E getOrNull(final Key<? extends Value<E>> key) {
		return this.getAsDataHolder().getOrNull(key);
	}
	
	@Override
	default <E> E getOrElse(final Key<? extends Value<E>> key, E defaultValue) {
		return this.getAsDataHolder().getOrElse(key, defaultValue);
	}
	
	@Override
	default <E, V extends Value<E>> Optional<V> getValue(final Key<V> key) {
		return this.getAsDataHolder().getValue(key);
	}
	
	@Override
	default <E, V extends Value<E>> V requireValue(final Key<V> key) {
		return this.getAsDataHolder().requireValue(key);
	}
	
	@Override
	default boolean supports(final Key<?> key) {
		return this.getAsDataHolder().supports(key);
	}
	
	@Override
	default boolean supports(final Value<?> value) {
		return this.getAsDataHolder().supports(value);
	}
	
	@Override
	default Set<Key<?>> getKeys() {
		return this.getAsDataHolder().getKeys();
	}
	
	@Override
	default Set<Value.Immutable<?>> getValues() {
		return this.getAsDataHolder().getValues();
	}
}
