package net.hellheim.spongetools.codec;

import java.util.Objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.ExtraCodecs;

public class LateBoundIdMapper<I, V> {
	
	protected final BiMap<I, V> idToValue = HashBiMap.create();
	
	public Codec<V> codec(final Codec<I> idCodec) {
		BiMap<V, I> bimap = this.idToValue.inverse();
		return ExtraCodecs.idResolver(idCodec, this.idToValue::get, bimap::get);
	}
	
	public LateBoundIdMapper<I, V> put(final I id, final V value) {
		Objects.requireNonNull(value, () -> "Value for " + id + " is null");
		this.idToValue.put(id, value);
		return this;
	}
}
