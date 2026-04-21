package net.hellheim.spongetools.proxy.solid.data;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;

public interface ImmutableDataHolderProxy<I extends DataHolder.Immutable<I>> extends DataHolderProxy, DataHolder.Immutable<I> {
	
	@Override
	DataHolder.Immutable<I> getAsData();
	
	@Override
	default <E> Optional<I> transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsData().transform(key, function);
	}
	
	@Override
	default <E> Optional<I> transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		return this.getAsData().transform(key, function);
	}
	
	@Override
	default <E> Optional<I> with(final Key<? extends Value<E>> key, final E value) {
		return this.getAsData().with(key, value);
	}
	
	@Override
	default <E> Optional<I> with(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsData().with(key, value);
	}
	
	@Override
	default Optional<I> with(final Value<?> value) {
		return this.getAsData().with(value);
	}
	
	@Override
	default Optional<I> without(final Value<?> value) {
		return this.getAsData().without(value);
	}
	
	@Override
	default Optional<I> without(final Key<?> key) {
		return this.getAsData().without(key);
	}
	
	@Override
	default Optional<I> without(final Supplier<? extends Key<?>> key) {
		return this.getAsData().without(key);
	}
	
	@Override
	default I mergeWith(final I that) {
		return this.getAsData().mergeWith(that);
	}
	
	@Override
	default I mergeWith(final I that, final MergeFunction function) {
		return this.getAsData().mergeWith(that, function);
	}
}
