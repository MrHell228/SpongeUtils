package net.hellheim.spongetools.object;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.data.DataManipulator;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;

public class ValueSetBuilder implements DataHolderBuilder<ValueSetBuilder> {
	
	protected final Set<Value<?>> values = new HashSet<>();
	
	@Override
	public <V> ValueSetBuilder add(final Key<? extends Value<V>> key, final V value) {
		this.values.add(Value.immutableOf(key, value));
		return this;
	}
	
	@Override
	public ValueSetBuilder reset() {
		this.values.clear();
		return this;
	}
	
	public DataManipulator.Mutable asMutableManipulator() {
		return DataManipulator.mutableOf(this.values);
	}
	
	public DataManipulator.Immutable asImmutableManipulator() {
		return DataManipulator.immutableOf(this.values);
	}
}
