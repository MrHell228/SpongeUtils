package net.hellheim.spongetools.codec;

import java.lang.reflect.Type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.codec.list.TypeCodecs;

public interface MapCodecMapper<R> {
	
	interface M1<R> extends MapCodecMapper<R> {
		
		MapCodec<? extends R> mapCodec(Codec<?> c1);
		
		default MapCodec<? extends R> mapCodec(final Type t1) {
			return this.mapCodec(TypeCodecs.of(t1));
		}
	}
	
	interface M2<R> extends MapCodecMapper<R> {
		
		MapCodec<? extends R> mapCodec(Codec<?> c1, Codec<?> c2);
		
		default MapCodec<? extends R> mapCodec(final Type t1, final Type t2) {
			return this.mapCodec(TypeCodecs.of(t1), TypeCodecs.of(t2));
		}
	}
	
	interface M3<R> extends MapCodecMapper<R> {
		
		MapCodec<? extends R> mapCodec(Codec<?> c1, Codec<?> c2, Codec<?> c3);
		
		default MapCodec<? extends R> mapCodec(final Type t1, final Type t2, final Type t3) {
			return this.mapCodec(TypeCodecs.of(t1), TypeCodecs.of(t2), TypeCodecs.of(t3));
		}
	}
}
