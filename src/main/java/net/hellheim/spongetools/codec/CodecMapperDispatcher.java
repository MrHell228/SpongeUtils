package net.hellheim.spongetools.codec;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.registry.DuplicateRegistrationException;

import com.mojang.serialization.Codec;

public final class CodecMapperDispatcher<I, V> {
	
	private final LateBoundIdMapper<I, MapCodecMapper.M1<? extends V>> idMapper = new LateBoundIdMapper<>();
	private final Map<Type, MapCodecMapper.M1<? extends V>> typeMapper = new HashMap<>();
	
	@SafeVarargs
	public final <E extends V> void put(
		final I id, final MapCodecMapper.M1<? extends E> mapper, final Class<? extends E>... types
	) {
		this.idMapper.put(id, mapper);
		
		for (final Class<? extends E> type : types) {
			final var oldCodec = this.typeMapper.put(type, mapper);
			if (oldCodec != null) {
				throw new DuplicateRegistrationException("Codec already registered for type: " + type);
			}
		}
	}
	
	public Codec<V> codec(final Codec<I> idCodec, final Codec<?> elementCodec) {
		return this.idMapper.codec(idCodec)
				.dispatch(v -> this.typeMapper.get(v.getClass()), e -> e.mapCodec(elementCodec));
	}
	
	public <E extends V> Codec<E> castedCodec(final Codec<I> idCodec, final Codec<?> elementCodec) {
		return ExtraCodecs.casted(this.codec(idCodec, elementCodec));
	}
}
