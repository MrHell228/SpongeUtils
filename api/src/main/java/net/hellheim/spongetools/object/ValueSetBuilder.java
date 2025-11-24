package net.hellheim.spongetools.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.data.DataManipulator;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.CollectionValue;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.SetValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.WeightedCollectionValue;
import org.spongepowered.api.util.weighted.WeightedTable;

import io.leangen.geantyref.GenericTypeReflector;

public class ValueSetBuilder implements DataOperator<ValueSetBuilder> {
	
	protected final Map<Key<?>, Value<?>> values = new HashMap<>();
	
	@Override
	public <V> ValueSetBuilder add(final Key<? extends Value<V>> key, final V value) {
		this.values.put(key, Value.immutableOf(key, value));
		return this;
	}
	
	@Override
	public <V, C extends Collection<V>> ValueSetBuilder addAll(final Key<? extends CollectionValue<V, C>> key, final Stream<? extends V> stream) {
		final Iterator<? extends V> iterator = stream.iterator();
		if (!iterator.hasNext()) {
			return this;
		}
		
		@SuppressWarnings("unchecked")
		final @Nullable CollectionValue<V, C> elements = (CollectionValue<V, C>) this.values.get(key);
		final C newElements = createCollection(key);
		if (elements != null) {
			newElements.addAll(elements.all());
		}
		iterator.forEachRemaining(newElements::add);
		return this.add(key, newElements);
	}
	
	@Override
	public <V, C extends Collection<V>> ValueSetBuilder addSingle(final Key<? extends CollectionValue<V, C>> key, final V element) {
		@SuppressWarnings("unchecked")
		final @Nullable CollectionValue<V, C> elements = (CollectionValue<V, C>) this.values.get(key);
		final C newElements = createCollection(key);
		if (elements != null) {
			newElements.addAll(elements.all());
		}
		newElements.add(element);
		return this.add(key, newElements);
	}
	
	@Override
	public ValueSetBuilder remove(final Key<?> key) {
		this.values.remove(key);
		return this;
	}
	
	@Override
	public ValueSetBuilder reset() {
		this.values.clear();
		return this;
	}
	
	public DataManipulator.Mutable asMutableManipulator() {
		return DataManipulator.mutableOf(this.values.values());
	}
	
	public DataManipulator.Immutable asImmutableManipulator() {
		return DataManipulator.immutableOf(this.values.values());
	}
	
	@SuppressWarnings("unchecked")
	private static <V, C extends Collection<V>> C createCollection(final Key<? extends CollectionValue<V, C>> key) {
		final Class<?> rawType = GenericTypeReflector.erase(key.valueType());
		if (ListValue.class.isAssignableFrom(rawType)) {
			return (C) new ArrayList<>();
		} else if (SetValue.class.isAssignableFrom(rawType)) {
			return (C) new HashSet<>();
		} else if (WeightedCollectionValue.class.isAssignableFrom(rawType)) {
			return (C) new WeightedTable<>();
		}
		
		throw new IllegalArgumentException("Unknown CollectionValue type: " + rawType);
	}
}
