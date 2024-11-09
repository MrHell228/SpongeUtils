package net.hellheim.spongeutils.source.solid.data;

import java.util.Collection;
import java.util.Map;
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

public interface MutableDataHolderSource extends DataHolderSource, DataHolder.Mutable {
	
	@Override
	DataHolder.Mutable getAsDataHolder();
	
	@Override
	default <E> DataTransactionResult transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default <E> DataTransactionResult transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		return this.getAsDataHolder().transform(key, function);
	}
	
	@Override
	default <E> DataTransactionResult offer(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().offer(key, value);
	}
	
	@Override
	default <E> DataTransactionResult offer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().offer(key, value);
	}
	
	@Override
	default <E> DataTransactionResult offer(final Supplier<? extends Key<? extends Value<E>>> key, final Supplier<? extends E> value) {
		return this.getAsDataHolder().offer(key, value);
	}
	
	@Override
	default DataTransactionResult offer(final Value<?> value) {
		return this.getAsDataHolder().offer(value);
	}
	
	@Override
	default <E> DataTransactionResult offerSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		return this.getAsDataHolder().offerSingle(key, element);
	}
	
	@Override
	default <E> DataTransactionResult offerSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		return this.getAsDataHolder().offerSingle(key, element);
	}
	
	@Override
	default <K, V> DataTransactionResult offerSingle(final Key<? extends MapValue<K, V>> key, final K valueKey, final V value) {
		return this.getAsDataHolder().offerSingle(key, valueKey, value);
	}
	
	@Override
	default <K, V> DataTransactionResult offerSingle(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final K valueKey, final V value) {
		return this.getAsDataHolder().offerSingle(key, valueKey, value);
	}
	
	@Override
	default <K, V> DataTransactionResult offerAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().offerAll(key, map);
	}
	
	@Override
	default <K, V> DataTransactionResult offerAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().offerAll(key, map);
	}
	
	@Override
	default DataTransactionResult offerAll(final MapValue<?, ?> value) {
		return this.getAsDataHolder().offerAll(value);
	}
	
	@Override
	default DataTransactionResult offerAll(final CollectionValue<?, ?> value) {
		return this.getAsDataHolder().offerAll(value);
	}
	
	@Override
	default <E> DataTransactionResult offerAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().offerAll(key, elements);
	}
	
	@Override
	default <E> DataTransactionResult removeSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		return this.getAsDataHolder().offerSingle(key, element);
	}
	
	@Override
	default <E> DataTransactionResult removeSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		return this.getAsDataHolder().offerSingle(key, element);
	}
	
	@Override
	default <K> DataTransactionResult removeKey(final Key<? extends MapValue<K, ?>> key, final K mapKey) {
		return this.getAsDataHolder().removeKey(key, mapKey);
	}
	
	@Override
	default <K> DataTransactionResult removeKey(final Supplier<? extends Key<? extends MapValue<K, ?>>> key, final K mapKey) {
		return this.getAsDataHolder().removeKey(key, mapKey);
	}
	
	@Override
	default DataTransactionResult removeAll(final CollectionValue<?, ?> value) {
		return this.getAsDataHolder().removeAll(value);
	}
	
	@Override
	default <E> DataTransactionResult removeAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().removeAll(key, elements);
	}
	
	@Override
	default <E> DataTransactionResult removeAll(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final Collection<? extends E> elements) {
		return this.getAsDataHolder().removeAll(key, elements);
	}
	
	@Override
	default DataTransactionResult removeAll(final MapValue<?, ?> value) {
		return this.getAsDataHolder().removeAll(value);
	}
	
	@Override
	default <K, V> DataTransactionResult removeAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().removeAll(key, map);
	}
	
	@Override
	default <K, V> DataTransactionResult removeAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		return this.getAsDataHolder().removeAll(key, map);
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Key<? extends Value<E>> key, final E value) {
		return this.getAsDataHolder().tryOffer(key, value);
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		return this.getAsDataHolder().tryOffer(key, value);
	}
	
	@Override
	default <E> DataTransactionResult tryOffer(final Value<E> value) throws IllegalArgumentException {
		return this.getAsDataHolder().tryOffer(value);
	}
	
	@Override
	default DataTransactionResult remove(final Value<?> value) {
		return this.getAsDataHolder().remove(value);
	}
	
	@Override
	default DataTransactionResult remove(final Key<?> key) {
		return this.getAsDataHolder().remove(key);
	}
	
	@Override
	default DataTransactionResult remove(final Supplier<? extends Key<?>> key) {
		return this.getAsDataHolder().remove(key);
	}
	
	@Override
	default DataTransactionResult undo(final DataTransactionResult result) {
		return this.getAsDataHolder().undo(result);
	}
	
	@Override
	default DataTransactionResult copyFrom(final ValueContainer that) {
		return this.getAsDataHolder().copyFrom(that);
	}
	
	@Override
	default DataTransactionResult copyFrom(final ValueContainer that, final MergeFunction function) {
		return this.getAsDataHolder().copyFrom(that, function);
	}
}
