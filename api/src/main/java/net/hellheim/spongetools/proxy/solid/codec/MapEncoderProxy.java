package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.MapEncoder;

public interface MapEncoderProxy<T> {
	
	MapEncoder<? extends T> mapCodec();
}
