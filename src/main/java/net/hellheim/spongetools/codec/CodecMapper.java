package net.hellheim.spongetools.codec;

import java.lang.reflect.Type;

import com.mojang.serialization.Codec;

public interface CodecMapper<R> {
	
	interface M1<R> extends CodecMapper<R> {
		
		Codec<? extends R> codec(Codec<?> c1);
		
		default Codec<? extends R> codec(final Type t1) {
			return this.codec(TypeCodecs.of(t1));
		}
	}
	
	interface M2<R> extends CodecMapper<R> {
		
		Codec<? extends R> codec(Codec<?> c1, Codec<?> c2);
		
		default Codec<? extends R> codec(final Type t1, final Type t2) {
			return this.codec(TypeCodecs.of(t1), TypeCodecs.of(t2));
		}
	}
	
	interface M3<R> extends CodecMapper<R> {
		
		Codec<? extends R> codec(Codec<?> c1, Codec<?> c2, Codec<?> c3);
		
		default Codec<? extends R> codec(final Type t1, final Type t2, final Type t3) {
			return this.codec(TypeCodecs.of(t1), TypeCodecs.of(t2), TypeCodecs.of(t3));
		}
	}
}
