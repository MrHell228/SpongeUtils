package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.Encoder;

public interface EncoderProxy<T> {
	
	Encoder<? extends T> codec();
}
