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
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.persistence.DataSerializable;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.persistence.StringDataFormat;
import org.spongepowered.api.data.value.Value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

/**
 * Codecs for SpongeAPI's DataAPI types
 */
public final class DataCodecs {
	
	private static final Map<StringDataFormat, Codec<DataContainer>> CONTAINERS = new HashMap<>();
	
	public static Function<ResourceKey, Key<?>> keyLookup(final DataHolder keyLookupProvider) {
		return keyLookupProvider.getKeys()
				.stream()
				.collect(Collectors.toUnmodifiableMap(Key::key, Function.identity()))
				::get;
	}
	
	public static Codec<?> byKey(final Key<?> key) {
		return TypeCodecs.of(key.elementType());
	}
	
	public static Codec<Key<Value<Object>>> key(final DataHolder keyLookupProvider) {
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
	
	public static Codec<Map<Key<Value<Object>>, Object>> map(final DataHolder keyLookupProvider) {
		return DataCodecs.map(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<Map<Key<Value<Object>>, Object>> map(final Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
		return Codec.dispatchedMap(key(keyLookup), DataCodecs::byKey);
	}
	
	public static Codec<Set<Value<?>>> valueSet(final DataHolder keyLookupProvider) {
		return DataCodecs.valueSet(DataCodecs.keyLookup(keyLookupProvider));
	}
	
	public static Codec<Set<Value<?>>> valueSet(Function<? super ResourceKey, ? extends Key<?>> keyLookup) {
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
			} catch (InvalidDataException e) {
				return DataResult.error(e::toString);
			}
		}, DataSerializable::toContainer);
	}
	
	public static Codec<DataContainer> container(final Supplier<? extends StringDataFormat> format) {
		return DataCodecs.container(format.get());
	}
	
	public static Codec<DataContainer> container(final StringDataFormat format) {
		return DataCodecs.CONTAINERS.computeIfAbsent(format, f -> {
			return Codec.STRING.flatXmap(string -> {
				try {
					return DataResult.success(f.read(string));
				} catch (IOException e) {
					return DataResult.error(e::toString);
				}
			}, container -> {
				try {
					return DataResult.success(f.write(container));
				} catch (IOException e) {
					return DataResult.error(e::toString);
				}
			});
		});
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
