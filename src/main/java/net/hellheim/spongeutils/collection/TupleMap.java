package net.hellheim.spongeutils.collection;

import java.util.HashMap;
import java.util.function.Function;

import org.spongepowered.api.util.Tuple;

import net.hellheim.spongeutils.function.TriFunction;

@SuppressWarnings("serial")
public class TupleMap<L, R, V> extends HashMap<Tuple<L, R>, V> {
	
	public V put(final L l, final R r, final V v) {
		return this.put(Tuple.of(l, r), v);
	}
	
	public V get(final L l, final R r) {
		return this.get(Tuple.of(l, r));
	}
	
	public boolean containsKey(final L l, final R r) {
		return this.containsKey(Tuple.of(l, r));
	}
	
	public V compute(final L l, final R r, final Function<? super V, ? extends V> remappingFunction) {
		return this.compute(Tuple.of(l, r), (key, value) -> remappingFunction.apply(value));
	}
	
	public V compute(final L l, final R r, final TriFunction<? super L, ? super R, ? super V, ? extends V> remappingFunction) {
		return this.compute(Tuple.of(l, r), (key, value) -> remappingFunction.apply(key.first(), key.second(), value));
	}
}
