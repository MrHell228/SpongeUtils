package net.hellheim.spongeutils.source.solid.data;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.CollectionValue;
import org.spongepowered.api.data.value.MapValue;
import org.spongepowered.api.data.value.MergeFunction;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

@SuppressWarnings("unchecked")
public interface TransitiveMutableDataHolderSource<T> extends DataHolderSource {
	
	@Override
	DataHolder.Mutable getAsDataHolder();
	
	default <E> T transform(final Key<? extends Value<E>> key, final Function<E, E> function) {
		this.getAsDataHolder().transform(key, function);
		return (T) this;
	}
	
	default <E> T transform(final Supplier<? extends Key<? extends Value<E>>> key, final Function<E, E> function) {
		this.getAsDataHolder().transform(key, function);
		return (T) this;
	}
	
	default <E> T offer(final Key<? extends Value<E>> key, final E value) {
		this.getAsDataHolder().offer(key, value);
		return (T) this;
	}
	
	default <E> T offer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		this.getAsDataHolder().offer(key, value);
		return (T) this;
	}
	
	default <E> T offer(final Supplier<? extends Key<? extends Value<E>>> key, final Supplier<? extends E> value) {
		this.getAsDataHolder().offer(key, value);
		return (T) this;
	}
	
	default T offer(final Value<?> value) {
		this.getAsDataHolder().offer(value);
		return (T) this;
	}
	
	default <E> T offerSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		this.getAsDataHolder().offerSingle(key, element);
		return (T) this;
	}
	
	default <E> T offerSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		this.getAsDataHolder().offerSingle(key, element);
		return (T) this;
	}
	
	default <K, V> T offerSingle(final Key<? extends MapValue<K, V>> key, final K valueKey, final V value) {
		this.getAsDataHolder().offerSingle(key, valueKey, value);
		return (T) this;
	}
	
	default <K, V> T offerSingle(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final K valueKey, final V value) {
		this.getAsDataHolder().offerSingle(key, valueKey, value);
		return (T) this;
	}
	
	default <K, V> T offerAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		this.getAsDataHolder().offerAll(key, map);
		return (T) this;
	}
	
	default <K, V> T offerAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		this.getAsDataHolder().offerAll(key, map);
		return (T) this;
	}
	
	default T offerAll(final MapValue<?, ?> value) {
		this.getAsDataHolder().offerAll(value);
		return (T) this;
	}
	
	default T offerAll(final CollectionValue<?, ?> value) {
		this.getAsDataHolder().offerAll(value);
		return (T) this;
	}
	
	default <E> T offerAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		this.getAsDataHolder().offerAll(key, elements);
		return (T) this;
	}
	
	default <E> T removeSingle(final Key<? extends CollectionValue<E, ?>> key, final E element) {
		this.getAsDataHolder().removeSingle(key, element);
		return (T) this;
	}
	
	default <E> T removeSingle(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final E element) {
		this.getAsDataHolder().offerSingle(key, element);
		return (T) this;
	}
	
	default <K> T removeKey(final Key<? extends MapValue<K, ?>> key, final K mapKey) {
		this.getAsDataHolder().removeKey(key, mapKey);
		return (T) this;
	}
	
	default <K> T removeKey(final Supplier<? extends Key<? extends MapValue<K, ?>>> key, final K mapKey) {
		this.getAsDataHolder().removeKey(key, mapKey);
		return (T) this;
	}
	
	default T removeAll(final CollectionValue<?, ?> value) {
		this.getAsDataHolder().removeAll(value);
		return (T) this;
	}
	
	default <E> T removeAll(final Key<? extends CollectionValue<E, ?>> key, final Collection<? extends E> elements) {
		this.getAsDataHolder().removeAll(key, elements);
		return (T) this;
	}
	
	default <E> T removeAll(final Supplier<? extends Key<? extends CollectionValue<E, ?>>> key, final Collection<? extends E> elements) {
		this.getAsDataHolder().removeAll(key, elements);
		return (T) this;
	}
	
	default T removeAll(final MapValue<?, ?> value) {
		this.getAsDataHolder().removeAll(value);
		return (T) this;
	}
	
	default <K, V> T removeAll(final Key<? extends MapValue<K, V>> key, final Map<? extends K, ? extends V> map) {
		this.getAsDataHolder().removeAll(key, map);
		return (T) this;
	}
	
	default <K, V> T removeAll(final Supplier<? extends Key<? extends MapValue<K, V>>> key, final Map<? extends K, ? extends V> map) {
		this.getAsDataHolder().removeAll(key, map);
		return (T) this;
	}
	
	default <E> T tryOffer(final Key<? extends Value<E>> key, E value) {
		this.getAsDataHolder().tryOffer(key, value);
		return (T) this;
	}
	
	default <E> T tryOffer(final Supplier<? extends Key<? extends Value<E>>> key, final E value) {
		this.getAsDataHolder().tryOffer(key, value);
		return (T) this;
	}
	
	default <E> T tryOffer(final Value<E> value) throws IllegalArgumentException {
		this.getAsDataHolder().tryOffer(value);
		return (T) this;
	}
	
	default T remove(final Value<?> value) {
		this.getAsDataHolder().remove(value);
		return (T) this;
	}
	
	default T remove(final Key<?> key) {
		this.getAsDataHolder().remove(key);
		return (T) this;
	}
	
	default T remove(final Supplier<? extends Key<?>> key) {
		this.getAsDataHolder().remove(key);
		return (T) this;
	}
	
	default T copyFrom(final ValueContainer that) {
		this.getAsDataHolder().copyFrom(that);
		return (T) this;
	}
	
	default T copyFrom(final ValueContainer that, final MergeFunction function) {
		this.getAsDataHolder().copyFrom(that, function);
		return (T) this;
	}
}
