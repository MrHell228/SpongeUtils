package net.hellheim.spongetools.proxy.solid.data;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataManipulator;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface MutableDataManipulatorProxy extends DataManipulatorProxy, DataManipulator.Mutable {
	
	@Override
	DataManipulator.Mutable getAsDataHolder();
	
	@Override
    default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final Predicate<Key<?>> predicate) {
        return this.getAsDataHolder().copyFrom(valueContainer, predicate);
    }
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final MergeFunction overlap, final Predicate<Key<?>> predicate) {
		return this.getAsDataHolder().copyFrom(valueContainer, overlap, predicate);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final Key<?> first, final Key<?>... more) {
		return this.getAsDataHolder().copyFrom(valueContainer, first, more);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final MergeFunction overlap, final Key<?> first, final Key<?>... more) {
		return this.getAsDataHolder().copyFrom(valueContainer, overlap, first, more);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final Iterable<Key<?>> keys) {
		return this.getAsDataHolder().copyFrom(valueContainer, keys);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final MergeFunction overlap, final Iterable<Key<?>> keys) {
		return this.getAsDataHolder().copyFrom(valueContainer, overlap, keys);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer) {
		return this.getAsDataHolder().copyFrom(valueContainer);
	}
	
	@Override
	default DataManipulator.Mutable copyFrom(final ValueContainer valueContainer, final MergeFunction overlap) {
		return this.getAsDataHolder().copyFrom(valueContainer, overlap);
	}
	
	@Override
	default <E> DataManipulator.Mutable set(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().set(key, value);
	}
	
	@Override
	default <E, V extends Value<E>> DataManipulator.Mutable set(final Supplier<Key<V>> key, final E value) {
		return this.getAsDataHolder().set(key, value);
	}
	
	@Override
	default <E, V extends Value<E>> DataManipulator.Mutable set(final Supplier<Key<V>> key, final Supplier<E> value) {
		return this.getAsDataHolder().set(key, value);
	}
	
	@Override
	default DataManipulator.Mutable set(final Value<?> value) {
		return this.getAsDataHolder().set(value);
	}
	
	@Override
	default DataManipulator.Mutable set(final Value<?>... values) {
		return this.getAsDataHolder().set(values);
	}
	
	@Override
	default DataManipulator.Mutable set(final Iterable<? extends Value<?>> values) {
		return this.getAsDataHolder().set(values);
	}
	
	@Override
	default <E> DataManipulator.Mutable transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default DataManipulator.Mutable remove(final Key<?> key) {
		return this.getAsDataHolder().remove(key);
	}
	
	@Override
	default DataManipulator.Mutable asMutable() {
		return this.getAsDataHolder().asMutable();
	}
	
	@Override
	default DataManipulator.Mutable copy() {
		return this.getAsDataHolder().copy();
	}
}
