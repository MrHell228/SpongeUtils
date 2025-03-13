package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.MapDecoder;

public interface MapDecoderProxy<T> {
	
	MapDecoder<? extends T> mapCodec();
}
