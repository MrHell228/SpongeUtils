package net.hellheim.spongeutils.source.optional.data;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.CollectionValue;
import org.spongepowered.api.data.value.MapValue;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface OptionalMutableDataHolderSource extends OptionalDataHolderSource, DataHolder.Mutable {
	
	@Override
	Optional<? extends DataHolder.Mutable> getAsDataHolder();
	
	@Override
	default <E> DataTransactionResult transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().map(h -> h.transform(key, function)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		return this.getAsDataHolder().map(h -> h.transform(key, function)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offer(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().map(h -> h.offer(key, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().map(h -> h.offer(key, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offer(final Supplier<? extends Key<? extends Value<E>>> key, final Supplier<? extends E> value) {
		return this.getAsDataHolder().map(h -> h.offer(key, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult offer(final Value<?> value) {
		return this.getAsDataHolder().map(h -> h.offer(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offerSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, element)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offerSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, element)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult offerSingle(final Key<? extends MapValue<K, V>> key, final K valueKey, final V value) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, valueKey, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult offerSingle(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final K valueKey, final V value) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, valueKey, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult offerAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().map(h -> h.offerAll(key, map)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult offerAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().map(h -> h.offerAll(key, map)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult offerAll(final MapValue<?, ?> value) {
		return this.getAsDataHolder().map(h -> h.offerAll(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult offerAll(final CollectionValue<?, ?> value) {
		return this.getAsDataHolder().map(h -> h.offerAll(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult offerAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().map(h -> h.offerAll(key, elements)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult removeSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, element)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult removeSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		return this.getAsDataHolder().map(h -> h.offerSingle(key, element)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K> DataTransactionResult removeKey(final Key<? extends MapValue<K, ?>> key, final K mapKey) {
		return this.getAsDataHolder().map(h -> h.removeKey(key, mapKey)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K> DataTransactionResult removeKey(final Supplier<? extends Key<? extends MapValue<K, ?>>> key, final K mapKey) {
		return this.getAsDataHolder().map(h -> h.removeKey(key, mapKey)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult removeAll(final CollectionValue<?, ?> value) {
		return this.getAsDataHolder().map(h -> h.removeAll(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult removeAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().map(h -> h.removeAll(key, elements)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult removeAll(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().map(h -> h.removeAll(key, elements)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult removeAll(final MapValue<?, ?> value) {
		return this.getAsDataHolder().map(h -> h.removeAll(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult removeAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().map(h -> h.removeAll(key, map)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <K, V> DataTransactionResult removeAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().map(h -> h.removeAll(key, map)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().map(h -> h.tryOffer(key, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().map(h -> h.tryOffer(key, value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Value<E> value) throws IllegalArgumentException {
		return this.getAsDataHolder().map(h -> h.tryOffer(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult remove(final Value<?> value) {
		return this.getAsDataHolder().map(h -> h.remove(value)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult remove(final Key<?> key) {
		return this.getAsDataHolder().map(h -> h.remove(key)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult remove(final Supplier<? extends Key<?>> key) {
		return this.getAsDataHolder().map(h -> h.remove(key)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult undo(final DataTransactionResult result) {
		return this.getAsDataHolder().map(h -> h.undo(result)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult copyFrom(final ValueContainer that) {
		return this.getAsDataHolder().map(h -> h.copyFrom(that)).orElse(DataTransactionResult.failNoData());
	}
	
	@Override
	default DataTransactionResult copyFrom(final ValueContainer that, final MergeFunction function) {
		return this.getAsDataHolder().map(h -> h.copyFrom(that, function)).orElse(DataTransactionResult.failNoData());
	}
}
