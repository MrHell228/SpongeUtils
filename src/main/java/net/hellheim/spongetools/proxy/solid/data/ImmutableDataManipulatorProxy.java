package net.hellheim.spongetools.proxy.solid.data;

import java.util.function.Function;

import org.spongepowered.api.data.DataManipulator;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;

public interface ImmutableDataManipulatorProxy extends DataManipulatorProxy, DataManipulator.Immutable {
	
	@Override
	DataManipulator.Immutable getAsDataHolder();
	
	@Override
	default <E> Immutable with(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().with(key, value);
    }
	
	@Override
	default Immutable without(final Key<?> key) {
		return this.getAsDataHolder().without(key);
    }
	
	@Override
	default <E> Immutable with(final Value<E> value) {
		return this.getAsDataHolder().with(value);
	}
	
	@Override
	default <E> Immutable transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default Immutable copy() {
		return this.getAsDataHolder().copy();
	}
	
	@Override
	default Immutable asImmutable() {
		return this.getAsDataHolder().asImmutable();
	}
}
