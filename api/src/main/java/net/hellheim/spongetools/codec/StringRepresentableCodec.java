package net.hellheim.spongetools.codec;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import org.spongepowered.api.data.type.StringRepresentable;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import net.hellheim.spongetools.codec.list.ExtraCodecs;

public class StringRepresentableCodec<S extends StringRepresentable> implements Codec<S> {
	
	private final Supplier<S[]> memoized;
	private final Codec<S> codec;
	
	public StringRepresentableCodec(
		final Supplier<S[]> values, final Function<String, S> nameLookup, ToIntFunction<S> indexLookup
	) {
		this.memoized = Suppliers.memoize(values::get);
		this.codec = ExtraCodecs.orCompressed(
				Codec.stringResolver(StringRepresentable::serializationString, nameLookup),
				ExtraCodecs.idResolver(indexLookup,
						id -> id >= 0 && id < this.memoized.get().length
						? this.memoized.get()[id] : null, -1)
				);
	}
	
	public static <S extends StringRepresentable> StringRepresentableCodec<S> fromValues(final S[] values) {
		return StringRepresentableCodec.fromValues(() -> values);
	}
	
	public static <S extends StringRepresentable> StringRepresentableCodec<S> fromValues(final Supplier<S[]> values) {
		final Supplier<S[]> memoized = Suppliers.memoize(values::get);
        final Supplier<List<S>> memoizedList = Suppliers.memoize(() -> List.of(memoized.get()));
        
        final Function<String, S> nameLookup = StringRepresentableCodec.nameLookup(memoized, Function.identity());
        final ToIntFunction<S> indexLookup = str -> memoizedList.get().indexOf(str);
        
        return new StringRepresentableCodec<>(memoized, nameLookup, indexLookup);
    }
	
	public static <S extends StringRepresentable> Function<String, S> nameLookup(
		final Supplier<S[]> values, final Function<String, String> keyFunction
	) {
		final Supplier<S[]> memoized = Suppliers.memoize(values::get);
		return key -> {
			for (S s : memoized.get()) {
				if (keyFunction.apply(s.serializationString()).equals(key)) {
					return s;
				}
			}
			
			return null;
		};
	}
	
	@Override
	public <T> DataResult<Pair<S, T>> decode(DynamicOps<T> ops, T value) {
		return this.codec.decode(ops, value);
	}
	
	@Override
	public <T> DataResult<T> encode(S input, DynamicOps<T> ops, T prefix) {
		return this.codec.encode(input, ops, prefix);
	}
}
