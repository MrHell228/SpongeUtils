package net.hellheim.spongetools.codec.list;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.persistence.DataFormats;
import org.spongepowered.api.data.persistence.DataSerializable;
import org.spongepowered.api.data.persistence.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.persistence.StringDataFormat;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

/**
 * Codecs for SpongeAPI's DataAPI types
 */
public final class DataCodecs {
	
	private static final Map<StringDataFormat, Codec<DataContainer>> CONTAINERS = new HashMap<>();
	
	public static Function<ResourceKey, Key<?>> keyLookup(final ValueContainer keyLookupProvider) {
		return keyLookupProvider.getKeys()
				.stream()
				.collect(Collectors.toUnmodifiableMap(Key::key, Function.identity()))
				::get;
	}
	
	public static Codec<?> byKey(final Key<?> key) {
		return TypeCodecs.of(key.elementType());
	}
	
	public static Codec<Key<Value<Object>>> key(final ValueContainer keyLookupProvider) {
		return DataCodecs.key(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<Key<Value<Object>>> key(final Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
		return SpongeCodecs.SPONGE_RESOURCE_KEY.comapFlatMap(key -> {
			@SuppressWarnings("unchecked")
			final Key<Value<Object>> dataKey = (Key<Value<Object>>) keyLookup.apply(key);
			return dataKey == null
					? DataResult.error(() ->
						String.format("No data Key found for provided ResourceKey(%s) & DataHolder", key.asString()))
					: DataResult.success(dataKey);
		}, Key::key);
	}
	
	public static Codec<Map<Key<Value<Object>>, Object>> map(final ValueContainer keyLookupProvider) {
		return DataCodecs.map(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<Map<Key<Value<Object>>, Object>> map(final Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
		return Codec.dispatchedMap(key(keyLookup), DataCodecs::byKey);
	}
	
	public static Codec<Set<? extends Value<?>>> valueSet(final ValueContainer keyLookupProvider) {
		return DataCodecs.valueSet(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<Set<? extends Value<?>>> valueSet(final Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
		return DataCodecs.map(keyLookup).xmap(
				map -> map.entrySet().stream()
						.map(DataCodecs::entryToValue)
						.collect(Collectors.toSet())
				,
				set -> set.stream().collect(Collectors.toMap(value -> {
						@SuppressWarnings("unchecked")
						final Key<Value<Object>> key = (Key<Value<Object>>) value.key();
						return key;
						}, Value::get))
				);
	}
	
	public static <T extends DataSerializable> Codec<T> serializable(
		final Class<T> type, final Supplier<? extends StringDataFormat> format
	) {
		return DataCodecs.serializable(type, format.get());
	}
	
	public static <T extends DataSerializable> Codec<T> serializable(
		final Class<T> type, final StringDataFormat format
	) {
		return DataCodecs.container(format).comapFlatMap(container -> {
			final Optional<DataBuilder<T>> builder = Sponge.dataManager().builder(type);
			if (builder.isEmpty()) {
				return DataResult.error(() -> "No DataBuilder found for type: " + type);
			}
			
			try {
				final Optional<T> value = builder.get().build(container);
				if (value.isEmpty()) {
					return DataResult.error(() -> "Failed to build value from container for type: " + type);
				}
				
				return DataResult.success(value.get());
			} catch (final InvalidDataException e) {
				return DataResult.error(e::toString);
			}
		}, DataSerializable::toContainer);
	}
	
	public static Codec<DataContainer> container(final Supplier<? extends StringDataFormat> format) {
		return DataCodecs.container(format.get());
	}
	
	public static Codec<DataContainer> container(final StringDataFormat format) {
		return DataCodecs.CONTAINERS.computeIfAbsent(format, f -> DataCodecs.container(Codec.STRING, format));
	}
	
	public static Codec<DataContainer> container(final Codec<String> stringCodec, final Supplier<? extends StringDataFormat> format) {
		return DataCodecs.container(stringCodec, format.get());
	}
	
	public static Codec<DataContainer> container(final Codec<String> stringCodec, final StringDataFormat format) {
		return DataCodecs.CONTAINERS.computeIfAbsent(format, f -> {
			return stringCodec.flatXmap(string -> {
				try {
					return DataResult.success(f.read(string));
				} catch (final IOException e) {
					return DataResult.error(e::toString);
				}
			}, container -> {
				try {
					return DataResult.success(f.write(container));
				} catch (final IOException e) {
					return DataResult.error(e::toString);
				}
			});
		});
	}
	
	private static final Gson GSON = new GsonBuilder().create();
	public static <T> DataContainer toContainer(final Codec<? super T> codec, final T value) {
		final DataResult<JsonElement> encoded = codec.encodeStart(JsonOps.INSTANCE, value);
		final JsonElement json = encoded.getOrThrow(IllegalArgumentException::new);
		final String str = GSON.toJson(json);
		try {
			return DataFormats.JSON.get().read(str);
		} catch (final IOException | InvalidDataException e) {
			// Should never happen
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T fromContainer(final Codec<T> codec, final DataView container) throws InvalidDataException {
		try {
			final String str = DataFormats.JSON.get().write(container);
			final JsonElement json = JsonParser.parseString(str);
			final DataResult<T> decoded = codec.decode(JsonOps.INSTANCE, json).map(Pair::getFirst);
			return decoded.getOrThrow(InvalidDataException::new);
		} catch (final IOException | JsonParseException e) {
			throw new InvalidDataException(e);
		}
	}
	
	public static <T extends DataSerializable> DataBuilder<T> dataBuilder(final Codec<T> codec) {
		return new DataBuilder<>() {
			@Override
			public Optional<T> build(final DataView container) throws InvalidDataException {
				return Optional.of(DataCodecs.fromContainer(codec, container));
			}
		};
	}
	
	private static <E, V extends Value<E>> Value.Immutable<E> entryToValue(
		final Map.Entry<? extends Key<? extends V>, Object> entry
	) {
		@SuppressWarnings("unchecked")
		final E value = (E) entry.getValue();
		return Value.immutableOf(entry.getKey(), value);
	}
	
	private DataCodecs() {
	}
}
