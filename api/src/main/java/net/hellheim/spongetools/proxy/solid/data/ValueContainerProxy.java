package net.hellheim.spongetools.proxy.solid.data;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface ValueContainerProxy extends ValueContainer {
	
	ValueContainer getAsData();
	
	@Override
	default <E> Optional<E> get(final Key<? extends Value<E>> key) {
		return this.getAsData().get(key);
	}
	
	@Override
	default OptionalInt getInt(final Key<? extends Value<Integer>> key) {
		return this.getAsData().getInt(key);
	}
	
	@Override
	default OptionalDouble getDouble(final Key<? extends Value<Double>> key) {
		return this.getAsData().getDouble(key);
	}
	
	@Override
	default OptionalLong getLong(final Key<? extends Value<Long>> key) {
		return this.getAsData().getLong(key);
	}
	
	@Override
	default <E> E require(final Key<? extends Value<E>> key) {
		return this.getAsData().require(key);
	}
	
	@Override
	default <E> E getOrNull(final Key<? extends Value<E>> key) {
		return this.getAsData().getOrNull(key);
	}
	
	@Override
	default <E> E getOrElse(final Key<? extends Value<E>> key, E defaultValue) {
		return this.getAsData().getOrElse(key, defaultValue);
	}
	
	@Override
	default <E, V extends Value<E>> Optional<V> getValue(final Key<V> key) {
		return this.getAsData().getValue(key);
	}
	
	@Override
	default <E, V extends Value<E>> V requireValue(final Key<V> key) {
		return this.getAsData().requireValue(key);
	}
	
	@Override
	default boolean supports(final Key<?> key) {
		return this.getAsData().supports(key);
	}
	
	@Override
	default boolean supports(final Value<?> value) {
		return this.getAsData().supports(value);
	}
	
	@Override
	default Set<Key<?>> getKeys() {
		return this.getAsData().getKeys();
	}
	
	@Override
	default Set<Value.Immutable<?>> getValues() {
		return this.getAsData().getValues();
	}
}
