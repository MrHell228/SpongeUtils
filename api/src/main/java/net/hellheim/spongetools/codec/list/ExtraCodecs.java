package net.hellheim.spongetools.codec.list;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

import org.apache.commons.lang3.LocaleUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.kyori.adventure.util.Index;

/**
 * Codecs that are not specific to SpongeAPI
 */
public final class ExtraCodecs {
	
	public static final Codec<JsonElement> JSON = ExtraCodecs.factory().json();
	public static final Codec<Object> JAVA = ExtraCodecs.factory().java();
	
	public static final Codec<Integer> RGB = ExtraCodecs.factory().rgb();
	public static final Codec<Integer> ARGB = ExtraCodecs.factory().argb();
	
	public static final Codec<Integer> UNSIGNED_BYTE = ExtraCodecs.factory().unsignedByte();
	public static final Codec<Integer> NON_NEGATIVE_INT = ExtraCodecs.factory().nonNegativeInt();
	public static final Codec<Integer> POSITIVE_INT = ExtraCodecs.factory().positiveInt();
	public static final Codec<Float> NON_NEGATIVE_FLOAT = ExtraCodecs.factory().nonNegativeFloat();
	public static final Codec<Float> POSITIVE_FLOAT = ExtraCodecs.factory().positiveFloat();
	
	public static final Codec<String> ESCAPED_STRING = ExtraCodecs.factory().escapedString();
	public static final Codec<String> NON_EMPTY_STRING = ExtraCodecs.factory().nonEmptyString();
	public static final Codec<String> PLAYER_NAME = ExtraCodecs.factory().playerName();
	public static final Codec<Pattern> PATTERN = ExtraCodecs.factory().pattern();
	
	public static final Codec<TimeZone> TIME_ZONE = Codec.STRING.comapFlatMap(str -> {
		for (final String id : TimeZone.getAvailableIDs()) {
			if (id.equals(str)) {
				return DataResult.success(TimeZone.getTimeZone(str));
			}
		}
		return DataResult.error(() -> "Unknown timezone: " + str);
	}, TimeZone::getID);
	
	public static final Codec<Locale> LOCALE = Codec.STRING.comapFlatMap(str -> {
		try {
			return DataResult.success(LocaleUtils.toLocale(str));
		} catch (final IllegalArgumentException e) {
			return DataResult.error(e::toString);
		}
	}, Locale::toString);
	
	public static <T> Codec<T> converter(final DynamicOps<T> ops) {
		return ExtraCodecs.factory().converter(ops);
	}
	
	public static <P, I> Codec<I> interval(
		final Codec<P> codec, final String minFieldName, final String maxFieldName,
		final BiFunction<P, P, DataResult<I>> factory, final Function<I, P> minGetter, final Function<I, P> maxGetter
	) {
		return ExtraCodecs.factory().interval(codec, minFieldName, maxFieldName, factory, minGetter, maxGetter);
	}
	
	public static <E> Codec<E> idResolver(
		final ToIntFunction<? super E> encoder,
		final IntFunction<? extends @Nullable E> decoder,
		final int notFoundValue
	) {
		return Codec.INT.flatXmap(
                id -> Optional.ofNullable(decoder.apply(id))
                        .map(DataResult::success)
                        .orElseGet(() -> DataResult.error(() -> "Unknown element id: " + id)),
                value -> {
                    int i = encoder.applyAsInt(value);
                    return i == notFoundValue ? DataResult.error(() -> "Element with unknown id: " + value) : DataResult.success(i);
                }
            );
	}
	
	public static <I, E> Codec<E> idResolver(
		final Codec<I> idCodec,
		final Function<? super I, ? extends @Nullable E> idToValue,
		final Function<? super E, ? extends @Nullable I> valueToId
	) {
		return idCodec.flatXmap(id -> {
			E e = idToValue.apply(id);
			return e == null ? DataResult.error(() -> "Unknown element id: " + id) : DataResult.success(e);
		}, value -> {
			I i = valueToId.apply(value);
			return i == null ? DataResult.error(() -> "Element with unknown id: " + value) : DataResult.success(i);
		});
	}
	
	public static <I, E> Codec<E> idResolver(final Codec<I> idCodec, final Index<I, E> index) {
		return ExtraCodecs.idResolver(idCodec, index::value, index::key);
	}
	
	public static <E> Codec<E> orCompressed(final Codec<E> first, final Codec<E> second) {
		return ExtraCodecs.factory().orCompressed(first, second);
	}
	
	public static <E> MapCodec<E> orCompressed(final MapCodec<E> first, final MapCodec<E> second) {
		return ExtraCodecs.factory().orCompressed(first, second);
	}
	
	public static <E> Codec<E> overrideLifecycle(
		final Codec<E> codec,
		final Function<E, Lifecycle> applyLifecycle,
		final Function<E, Lifecycle> coApplyLifecycle
	) {
		return ExtraCodecs.factory().overrideLifecycle(codec, applyLifecycle, coApplyLifecycle);
	}
	
	public static <E> Codec<E> overrideLifecycle(
		final Codec<E> codec, final Function<E, Lifecycle> lifecycleGetter
	) {
		return ExtraCodecs.factory().overrideLifecycle(codec, lifecycleGetter);
	}
	
	public static <E> Codec<List<E>> compactList(final Codec<E> elementCodec) {
		return ExtraCodecs.factory().compactList(elementCodec);
	}
	
	public static <E> Codec<List<E>> compactList(final Codec<E> elementCodec, final Codec<List<E>> listCodec) {
		return ExtraCodecs.factory().compactList(elementCodec, listCodec);
	}
	
	public static <C extends Collection<?>> Codec<C> nonEmptyCollection(final Codec<C> codec) {
		return codec.validate(c -> c.isEmpty()
				? DataResult.error(() -> "Collection must have contents")
				: DataResult.success(c));
	}
	
	public static <C extends Collection<?>> Codec<C> sizeLimitedCollection(final Codec<C> codec, final int maxSize) {
		return codec.validate(c -> c.size() > maxSize
				? DataResult.error(() -> "Collection is too long: " + c.size() + ", expected range [0-" + maxSize + "]")
				: DataResult.success(c));
	}
	
	public static <M extends Map<?, ?>> Codec<M> nonEmptyMap(final Codec<M> codec) {
		return codec.validate(m -> m.isEmpty()
				? DataResult.error(() -> "Map must have contents")
				: DataResult.success(m));
	}
	
	public static <K, V> Codec<Map<K, V>> sizeLimitedMap(final Codec<Map<K, V>> codec, final int maxSize) {
		return codec.validate(m -> m.size() > maxSize
				? DataResult.error(() -> "Map is too long: " + m.size() + ", expected range [0-" + maxSize + "]")
				: DataResult.success(m));
	}
	
	public static <T> Codec<Object2BooleanMap<T>> object2BooleanMap(final Codec<T> codec) {
		return ExtraCodecs.factory().object2BooleanMap(codec);
	}
	
	public static <K, V> Codec<Map<K, V>> strictUnboundedMap(final Codec<K> key, final Codec<V> value) {
		return ExtraCodecs.factory().strictUnboundedMap(key, value);
	}
	
	public static Codec<Integer> intRange(final int min, final int max) {
		return ExtraCodecs.factory().intRange(min, max);
	}
	
	public static Codec<Float> floatRange(final float min, final float max) {
		return ExtraCodecs.factory().floatRange(min, max);
	}
	
	public static MapCodec<OptionalLong> asOptionalLong(final MapCodec<Optional<Long>> codec) {
		return ExtraCodecs.factory().asOptionalLong(codec);
	}
	
	public static <E> MapCodec<E> retrieveContext(final Function<DynamicOps<?>, DataResult<E>> retriever) {
		return ExtraCodecs.factory().retrieveContext(retriever);
	}
	
	public static <A> Codec<A> catchDecoderException(final Codec<A> codec) {
		return ExtraCodecs.factory().catchDecoderException(codec);
	}
	
	public static Codec<TemporalAccessor> temporal(final DateTimeFormatter formatter) {
		return ExtraCodecs.factory().temporal(formatter);
	}
	
	public static <T> Codec<Set<T>> setOf(final Codec<T> codec) {
		return codec.listOf().xmap(HashSet::new, ArrayList::new);
	}
	
	public static <F, T> Codec<T> casted(final Codec<F> codec) {
		return codec.flatXmap(
				from -> {
					try {
						@SuppressWarnings("unchecked")
						final T to = (T) from;
						return DataResult.success(to);
					} catch (final ClassCastException e) {
						return DataResult.error(e::toString);
					}
				},
				to -> {
					try {
						@SuppressWarnings("unchecked")
						final F from = (F) to;
						return DataResult.success(from);
					} catch (final ClassCastException e) {
						return DataResult.error(e::toString);
					}
				});
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	public static interface Factory {
		
		Codec<JsonElement> json();
		
		Codec<Object> java();
		
		Codec<Integer> rgb();
		
		Codec<Integer> argb();
		
		Codec<Integer> unsignedByte();
		
		Codec<Integer> nonNegativeInt();
		
		Codec<Integer> positiveInt();
		
		Codec<Float> nonNegativeFloat();
		
		Codec<Float> positiveFloat();
		
		Codec<String> escapedString();
		
		Codec<String> nonEmptyString();
		
		Codec<String> playerName();
		
		Codec<Pattern> pattern();
		
		<T> Codec<T> converter(DynamicOps<T> ops);
		
		<P, I> Codec<I> interval(
			Codec<P> codec, String minFieldName, String maxFieldName,
			BiFunction<P, P, DataResult<I>> factory, Function<I, P> minGetter, Function<I, P> maxGetter
		);
		
		<E> Codec<E> orCompressed(Codec<E> first, Codec<E> second);
		
		<E> MapCodec<E> orCompressed(MapCodec<E> first, MapCodec<E> second);
		
		<E> Codec<E> overrideLifecycle(
			Codec<E> codec, Function<E, Lifecycle> applyLifecycle, Function<E, Lifecycle> coApplyLifecycle
		);
		
		<E> Codec<E> overrideLifecycle(Codec<E> codec, Function<E, Lifecycle> lifecycleGetter);
		
		<E> Codec<List<E>> compactList(Codec<E> elementCodec);
		
		<E> Codec<List<E>> compactList(Codec<E> elementCodec, Codec<List<E>> listCodec);
		
		<T> Codec<Object2BooleanMap<T>> object2BooleanMap(Codec<T> codec);
		
		<K, V> Codec<Map<K, V>> strictUnboundedMap(Codec<K> key, Codec<V> value);
		
		Codec<Integer> intRange(int min, int max);
		
		Codec<Float> floatRange(float min, float max);
		
		MapCodec<OptionalLong> asOptionalLong(MapCodec<Optional<Long>> codec);
		
		<E> MapCodec<E> retrieveContext(Function<DynamicOps<?>, DataResult<E>> retriever);
		
		<A> Codec<A> catchDecoderException(Codec<A> codec);
		
		Codec<TemporalAccessor> temporal(DateTimeFormatter formatter);
	}
	
	private ExtraCodecs() {
	}
}
