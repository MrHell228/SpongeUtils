package net.hellheim.spongeutils.source.optional.data;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;

public interface OptionalImmutableDataHolderSource<I extends DataHolder.Immutable<I>> extends OptionalDataHolderSource {
	
	@Override
	Optional<? extends DataHolder.Immutable<I>> getAsDataHolder();
	
	default <E> Optional<I> transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().flatMap(h -> h.transform(key, function));
	}
	
	default <E> Optional<I> transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		return this.getAsDataHolder().flatMap(h -> h.transform(key, function));
	}
	
	default <E> Optional<I> with(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().flatMap(h -> h.with(key, value));
	}
	
	default <E> Optional<I> with(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().flatMap(h -> h.with(key, value));
	}
	
	default Optional<I> with(final Value<?> value) {
		return this.getAsDataHolder().flatMap(h -> h.with(value));
	}
	
	default Optional<I> without(final Value<?> value) {
		return this.getAsDataHolder().flatMap(h -> h.without(value));
	}
	
	default Optional<I> without(final Key<?> key) {
		return this.getAsDataHolder().flatMap(h -> h.without(key));
	}
	
	default Optional<I> without(final Supplier<? extends Key<?>> key) {
		return this.getAsDataHolder().flatMap(h -> h.without(key));
	}
	
	default Optional<I> mergeWith(final I that) {
		return this.getAsDataHolder().map(h -> h.mergeWith(that));
	}
	
	default Optional<I> mergeWith(final I that, final MergeFunction function) {
		return this.getAsDataHolder().map(h -> h.mergeWith(that, function));
	}
}
