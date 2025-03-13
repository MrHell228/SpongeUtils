package net.hellheim.spongetools.proxy.solid.codec;

import com.mojang.serialization.Codec;

public interface CodecProxy<T> extends EncoderProxy<T>, DecoderProxy<T> {
	
	@Override
	Codec<? extends T> codec();
}
