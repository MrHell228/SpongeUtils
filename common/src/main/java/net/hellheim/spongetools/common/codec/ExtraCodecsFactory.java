package net.hellheim.spongetools.common.codec;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.util.ExtraCodecs;

public final class ExtraCodecsFactory implements net.hellheim.spongetools.codec.list.ExtraCodecs.Factory {
	
	@Override
	public Codec<JsonElement> json() {
		return ExtraCodecs.JSON;
	}
	
	@Override
	public Codec<Object> java() {
		return ExtraCodecs.JAVA;
	}
	
	@Override
	public Codec<Integer> rgb() {
		return ExtraCodecs.RGB_COLOR_CODEC;
	}
	
	@Override
	public Codec<Integer> argb() {
		return ExtraCodecs.ARGB_COLOR_CODEC;
	}
	
	@Override
	public Codec<Integer> unsignedByte() {
		return ExtraCodecs.UNSIGNED_BYTE;
	}
	
	@Override
	public Codec<Integer> nonNegativeInt() {
		return ExtraCodecs.NON_NEGATIVE_INT;
	}
	
	@Override
	public Codec<Integer> positiveInt() {
		return ExtraCodecs.POSITIVE_INT;
	}
	
	@Override
	public Codec<Float> nonNegativeFloat() {
		return ExtraCodecs.NON_NEGATIVE_FLOAT;
	}
	
	@Override
	public Codec<Float> positiveFloat() {
		return ExtraCodecs.POSITIVE_FLOAT;
	}
	
	@Override
	public Codec<String> escapedString() {
		return ExtraCodecs.ESCAPED_STRING;
	}
	
	@Override
	public Codec<String> nonEmptyString() {
		return ExtraCodecs.NON_EMPTY_STRING;
	}
	
	@Override
	public Codec<String> playerName() {
		return ExtraCodecs.PLAYER_NAME;
	}
	
	@Override
	public Codec<Pattern> pattern() {
		return ExtraCodecs.PATTERN;
	}

	@Override
	public <T> Codec<T> converter(final DynamicOps<T> ops) {
		return ExtraCodecs.converter(ops);
	}

	@Override
	public <P, I> Codec<I> interval(
		final Codec<P> codec, final String minFieldName, final String maxFieldName,
		final BiFunction<P, P, DataResult<I>> factory, final Function<I, P> minGetter, final Function<I, P> maxGetter
	) {
		return ExtraCodecs.intervalCodec(codec, minFieldName, maxFieldName, factory, minGetter, maxGetter);
	}

	@Override
	public <E> Codec<E> orCompressed(final Codec<E> first, final Codec<E> second) {
		return ExtraCodecs.orCompressed(first, second);
	}

	@Override
	public <E> MapCodec<E> orCompressed(final MapCodec<E> first, final MapCodec<E> second) {
		return ExtraCodecs.orCompressed(first, second);
	}

	@Override
	public <E> Codec<E> overrideLifecycle(
		final Codec<E> codec,
		final Function<E, Lifecycle> applyLifecycle,
		final Function<E, Lifecycle> coApplyLifecycle
	) {
		return ExtraCodecs.overrideLifecycle(codec, applyLifecycle, coApplyLifecycle);
	}

	@Override
	public <E> Codec<E> overrideLifecycle(final Codec<E> codec, final Function<E, Lifecycle> lifecycleGetter) {
		return ExtraCodecs.overrideLifecycle(codec, lifecycleGetter);
	}

	@Override
	public <E> Codec<List<E>> compactList(final Codec<E> elementCodec) {
		return ExtraCodecs.compactListCodec(elementCodec);
	}

	@Override
	public <E> Codec<List<E>> compactList(final Codec<E> elementCodec, final Codec<List<E>> listCodec) {
		return ExtraCodecs.compactListCodec(elementCodec, listCodec);
	}

	@Override
	public <T> Codec<Object2BooleanMap<T>> object2BooleanMap(final Codec<T> codec) {
		return ExtraCodecs.object2BooleanMap(codec);
	}
	
	@Override
	public <K, V> Codec<Map<K, V>> strictUnboundedMap(Codec<K> key, Codec<V> value) {
		return ExtraCodecs.strictUnboundedMap(key, value);
	}

	@Override
	public Codec<Integer> intRange(final int min, final int max) {
		return ExtraCodecs.intRange(min, max);
	}

	@Override
	public Codec<Float> floatRange(final float min, final float max) {
		return ExtraCodecs.floatRange(min, max);
	}

	@Override
	public MapCodec<OptionalLong> asOptionalLong(final MapCodec<Optional<Long>> codec) {
		return ExtraCodecs.asOptionalLong(codec);
	}

	@Override
	public <E> MapCodec<E> retrieveContext(final Function<DynamicOps<?>, DataResult<E>> retriever) {
		return ExtraCodecs.retrieveContext(retriever);
	}

	@Override
	public <A> Codec<A> catchDecoderException(final Codec<A> codec) {
		return ExtraCodecs.catchDecoderException(codec);
	}

	@Override
	public Codec<TemporalAccessor> temporal(final DateTimeFormatter formatter) {
		return ExtraCodecs.temporalCodec(formatter);
	}
}
