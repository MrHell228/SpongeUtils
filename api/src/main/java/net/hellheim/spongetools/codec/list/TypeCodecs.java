package net.hellheim.spongetools.codec.list;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.registry.DuplicateRegistrationException;
import org.spongepowered.api.util.weighted.RandomObjectTable;
import org.spongepowered.api.util.weighted.TableEntry;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.CodecMapper;

public final class TypeCodecs {
	
	private static final Map<Class<?>, Predicate<Class<?>>> CLASS_MAPPER = new HashMap<>();
	
	private static final Map<Class<?>, Codec<?>> PLAIN = new HashMap<>();
	private static final Map<Class<?>, CodecMapper.M1<?>> M1 = new HashMap<>();
	private static final Map<Class<?>, CodecMapper.M2<?>> M2 = new HashMap<>();
	private static final Map<Class<?>, CodecMapper.M3<?>> M3 = new HashMap<>();
	
	private static final Map<Type, Codec<?>> COMPOSITE = new HashMap<>();
	
	static {
		// Basic Java types
		TypeCodecs.register(Boolean.class, Codec.BOOL);
		TypeCodecs.register(Byte.class, Codec.BYTE);
		TypeCodecs.register(Short.class, Codec.SHORT);
		TypeCodecs.register(Integer.class, Codec.INT);
		TypeCodecs.register(Long.class, Codec.LONG);
		TypeCodecs.register(Float.class, Codec.FLOAT);
		TypeCodecs.register(Double.class, Codec.DOUBLE);
		TypeCodecs.register(String.class, Codec.STRING);
		
		// Collections
		TypeCodecs.register(List.class, c -> c.listOf(), true);
		TypeCodecs.register(Set.class, c -> ExtraCodecs.setOf(c), true);
		TypeCodecs.register(Map.class, (c1, c2) -> Codec.unboundedMap(c1, c2), true);
		
		TypeCodecs.register(RandomObjectTable.class, c -> SpongeCodecs.randomObjectTable(c), true);
		TypeCodecs.register(TableEntry.class, c -> SpongeCodecs.tableEntry(c), true);
	}
	
	/**
	 * Given the type T, attempts to assemble Codec<T> based on registered codec providers.
	 * 
	 * @param type The type for codec
	 * @return The created codec
	 * @throws IllegalArgumentException If cannot make codec for provided type
	 */
	public static Codec<?> of(final Type type) {
		final Codec<?> codec = COMPOSITE.get(type);
		if (codec != null)  {
			return codec;
		}
		
		final Class<?> clazz = TypeCodecs.extractClass(type);
		final Codec<?> plainCodec = TypeCodecs.plain(clazz);
		if (plainCodec != null) {
			return plainCodec;
		}
		
		for (final Map.Entry<Class<?>, Predicate<Class<?>>> entry : CLASS_MAPPER.entrySet()) {
			if (entry.getKey() != clazz && entry.getValue().test(clazz)) {
				return TypeCodecs.of(entry.getKey());
			}
		}
		
		final Codec<?> newCodec;
		if (!(type instanceof final ParameterizedType parametrized)) {
			throw TypeCodecs.error("No plain codec found for type: " + type);
		} else {
			final Type[] args = parametrized.getActualTypeArguments();
			final int amount = args.length;
			if (args.length == 1) {
				newCodec = TypeCodecs.m1(clazz).codec(args[0]);
			} else if (args.length == 2) {
				newCodec = TypeCodecs.m2(clazz).codec(args[0], args[1]);
			} else if (args.length == 3) {
				newCodec = TypeCodecs.m3(clazz).codec(args[0], args[1], args[2]);
			} else {
				throw TypeCodecs.error("Type has too many type arguments: " + parametrized + " (" + amount + ")");
			}
		}
		
		COMPOSITE.put(type, newCodec);
		return newCodec;
	}
	
	private static @Nullable Codec<?> plain(final Class<?> type) {
		return PLAIN.get(type);
	}
	
	private static CodecMapper.M1<?> m1(final Class<?> type) {
		final CodecMapper.M1<?> mapper = TypeCodecs.M1.get(type);
		if (mapper == null) {
			throw TypeCodecs.error("No codec mapper found for 1-parametrized type: " + type);
		}
		return mapper;
	}
	
	private static CodecMapper.M2<?> m2(final Class<?> type) {
		final CodecMapper.M2<?> mapper = TypeCodecs.M2.get(type);
		if (mapper == null) {
			throw TypeCodecs.error("No codec mapper found for 2-parametrized type: " + type);
		}
		return mapper;
	}
	
	private static CodecMapper.M3<?> m3(final Class<?> type) {
		final CodecMapper.M3<?> mapper = TypeCodecs.M3.get(type);
		if (mapper == null) {
			throw TypeCodecs.error("No codec mapper found for 3-parametrized type: " + type);
		}
		return mapper;
	}
	
	private static Class<?> extractClass(final Type type) {
		if (type instanceof final Class<?> clazz) {
			return clazz;
		} else if (type instanceof final ParameterizedType parametrized) {
			return TypeCodecs.extractClass(parametrized.getRawType());
		} else if (type instanceof final WildcardType wildcard) {
			throw TypeCodecs.error("Cannot extract class from wildcard type: " + wildcard);
		} else if (type instanceof final GenericArrayType array) {
			throw TypeCodecs.error("Cannot extract class from array type: " + array);
		} else if (type instanceof final TypeVariable<?> variable) {
			throw TypeCodecs.error("Cannot extract class from variable type: " + variable);
		} else {
			throw TypeCodecs.error("Cannot extract Class from Type: " + type);
		}
	}
	
	// Registering
	
	public static void registerClass(final Class<?> root) {
		TypeCodecs.registerClass(root, root::isAssignableFrom);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> void registerClass(final Class<E> root, final Predicate<Class<? extends E>> condition) {
		CLASS_MAPPER.compute(root, ($, oldCondition) -> oldCondition == null
					? type -> condition.test((Class<E>) type)
					: type -> oldCondition.test(type) || condition.test((Class<E>) type)
		);
	}
	
	public static <E> void register(
		final Class<E> type, final Codec<? extends E> codec
	) {
		TypeCodecs.register(type, codec, false);
	}
	
	public static <E> void register(
		final Class<E> type, final Codec<? extends E> codec, final boolean withInherited
	) {
		TypeCodecs.register(PLAIN, type, codec, withInherited);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M1<E> mapper
	) {
		TypeCodecs.register(type, mapper, false);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M1<E> mapper, final boolean withInherited
	) {
		TypeCodecs.assertParameters(1, type);
		TypeCodecs.register(M1, type, mapper, withInherited);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M2<E> mapper
	) {
		TypeCodecs.register(type, mapper, false);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M2<E> mapper, final boolean withInherited
	) {
		TypeCodecs.assertParameters(2, type);
		TypeCodecs.register(M2, type, mapper, withInherited);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M3<E> mapper
	) {
		TypeCodecs.register(type, mapper, false);
	}
	
	public static <E> void register(
		final Class<E> type, final CodecMapper.M3<E> mapper, final boolean withInherited
	) {
		TypeCodecs.assertParameters(3, type);
		TypeCodecs.register(M3, type, mapper, withInherited);
	}
	
	private static <M> void register(
		final Map<Class<?>, M> map, final Class<?> type, final M mapper, final boolean withInherited
	) {
		final M oldMapper = map.put(type, mapper);
		if (oldMapper != null) {
			throw new DuplicateRegistrationException(String.format(
					"Codec provider for type %s already registered (Old: %s, New: %s).",
					type, oldMapper, mapper));
		}
		
		if (withInherited) {
			TypeCodecs.registerClass(type);
		}
	}
	
	private static void assertParameters(final int amount, final Class<?>... types) {
		for (final Class<?> type : types) {
			final int length = type.getTypeParameters().length;
			if (length != amount) {
				throw TypeCodecs.error(
						String.format("Type [%s] has %s type parameters, but %s expected", type, length, amount));
			}
		}
	}
	
	private static IllegalArgumentException error(String message) {
		return new IllegalArgumentException(message);
	}
	
	private TypeCodecs() {
	}
}
