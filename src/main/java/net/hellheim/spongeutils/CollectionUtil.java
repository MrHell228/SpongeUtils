package net.hellheim.spongeutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.hellheim.spongeutils.function.TriConsumer;

public final class CollectionUtil {
	
	@SafeVarargs
	public static <T, E extends Collection<T>> E addAll(final E collection, final Supplier<T>... suppliers) {
		for (final Supplier<T> supplier : suppliers) {
			collection.add(supplier.get());
		}
		return collection;
	}
	
	@SafeVarargs
	public static <T> HashSet<T> newHashSet(final Supplier<T>... suppliers) {
		return addAll(new HashSet<>(suppliers.length), suppliers);
	}
	
	public static <K, V, F> void mergeMaps(Map<K, V> origin, Map<K, F> from, BiConsumer<V, F> merger, Supplier<V> emptyValueSupplier) {
		mergeMaps(origin, from, merger, f -> emptyValueSupplier.get());
	}
	
	public static <K, V, F> void mergeMaps(Map<K, V> origin, Map<K, F> from, BiConsumer<V, F> merger, Function<F, V> emptyValueFunction) {
		mergeMaps(origin, from, (v, f, k) -> merger.accept(v, f), (k, f) -> emptyValueFunction.apply(f));
	}
	
	public static <K, V, F> void mergeMaps(Map<K, V> origin, Map<K, F> from, BiConsumer<V, F> merger, BiFunction<K, F, V> emptyValueFunction) {
		mergeMaps(origin, from, (v, f, k) -> merger.accept(v, f), emptyValueFunction);
	}
	
	public static <K, V, F> void mergeMaps(Map<K, V> origin, Map<K, F> from, TriConsumer<V, F, K> merger, Supplier<V> emptyValueSupplier) {
		mergeMaps(origin, from, merger, (k, f) -> emptyValueSupplier.get());
	}
	
	public static <K, V, F> void mergeMaps(Map<K, V> origin, Map<K, F> from, TriConsumer<V, F, K> merger, BiFunction<K, F, V> emptyValueFunction) {
		for (Entry<K, F> e : from.entrySet()) {
			K key = e.getKey();
			F v = e.getValue();
			V value = origin.get(key);
			if (value == null) {
				value = emptyValueFunction.apply(key, v);
				origin.put(key, value);
			}
			merger.accept(value, v, key);
		}
	}
	
	public static <K, V, F> void mergeFilledMaps(Map<K, V> origin, Map<K, F> from, BiConsumer<V, F> merger) {
		for (Entry<K, F> e : from.entrySet()) {
			merger.accept(origin.get(e.getKey()), e.getValue());
		}
	}
	
	public static <T> boolean containsAny(final Collection<T> col, final Collection<? extends T> from) {
		for (T t : from) {
			if (col.contains(t)) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean containsNone(final Collection<T> col, final Collection<? extends T> from) {
		return !containsAny(col, from);
	}
	
	public static <T> void forEachNew(final Collection<T> col, final Consumer<? super T> action) {
		new ArrayList<>(col).forEach(action);
	}
	
	public static <T> Iterable<T> iterable(final Stream<T> stream) {
		return stream::iterator;
	}
	
	private CollectionUtil() {
	}
}
