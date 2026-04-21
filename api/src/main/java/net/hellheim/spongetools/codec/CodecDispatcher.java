package net.hellheim.spongetools.codec;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.registry.DuplicateRegistrationException;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class CodecDispatcher<I, V> {
	
	private final LateBoundIdMapper<I, Entry<V>> idMapper = new LateBoundIdMapper<>();
	private final Map<Type, Entry<V>> typeMapper = new HashMap<>();
	
	@SafeVarargs
	public final <E extends V> void put(
		final I id, final MapCodec<? extends E> codec, final Class<? extends E>... types
	) {
		final Entry<V> entry = new Entry<>(codec);
		this.idMapper.put(id, entry);
		
		for (final Class<? extends E> type : types) {
			final var oldCodec = this.typeMapper.put(type, entry);
			if (oldCodec != null) {
				throw new DuplicateRegistrationException("Codec already registered for type: " + type);
			}
		}
	}
	
	public Codec<V> codec(final Codec<I> idCodec) {
		return this.idMapper.codec(idCodec)
				.dispatch(v -> this.typeMapper.get(v.getClass()), Entry::codec);
	}
	
	private record Entry<V>(MapCodec<? extends V> codec) {
	}
}
