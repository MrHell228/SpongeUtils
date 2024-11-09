package net.hellheim.spongeutils.source.solid.data;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;

public interface ImmutableDataHolderSource<I extends DataHolder.Immutable<I>> extends DataHolderSource, DataHolder.Immutable<I> {
	
	@Override
	DataHolder.Immutable<I> getAsDataHolder();
	
	@Override
	default <E> Optional<I> transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default <E> Optional<I> transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default <E> Optional<I> with(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().with(key, value);
	}
	
	@Override
	default <E> Optional<I> with(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().with(key, value);
	}
	
	@Override
	default Optional<I> with(final Value<?> value) {
		return this.getAsDataHolder().with(value);
	}
	
	@Override
	default Optional<I> without(final Value<?> value) {
		return this.getAsDataHolder().without(value);
	}
	
	@Override
	default Optional<I> without(final Key<?> key) {
		return this.getAsDataHolder().without(key);
	}
	
	@Override
	default Optional<I> without(final Supplier<? extends Key<?>> key) {
		return this.getAsDataHolder().without(key);
	}
	
	@Override
	default I mergeWith(final I that) {
		return this.getAsDataHolder().mergeWith(that);
	}
	
	@Override
	default I mergeWith(final I that, final MergeFunction function) {
		return this.getAsDataHolder().mergeWith(that, function);
	}
}
