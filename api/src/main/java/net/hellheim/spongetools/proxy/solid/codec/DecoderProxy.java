package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.Decoder;

public interface DecoderProxy<T> {
	
	Decoder<? extends T> codec();
}
