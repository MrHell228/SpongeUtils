package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.MapCodec;

public interface MapCodecProxy<T> extends MapEncoderProxy<T>, MapDecoderProxy<T> {
	
	@Override
	MapCodec<? extends T> mapCodec();
}
