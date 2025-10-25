package net.hellheim.spongetools.object;

import java.lang.reflect.Type;
import java.util.Objects;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeToken;

public record TypedKey<T>(ResourceKey key, Type type) implements ResourceKeyed, Comparable<TypedKey<?>> {
	
	public TypedKey(final ResourceKey key, final Type type) {
		this.key = Objects.requireNonNull(key, "key");
		this.type = Objects.requireNonNull(type, "type");
	}
	
	public static <T> TypedKey<T> of(final ResourceKey key, final Class<T> clazz) {
		return new TypedKey<>(key, clazz);
	}
	
	public static <T> TypedKey<T> of(final ResourceKey key, final TypeToken<T> token) {
		return new TypedKey<>(key, token.getType());
	}
	
	@Deprecated(forRemoval = true)
	public static <T> TypedKey<T> of(final ResourceKey key) {
		return new TypedKey<>(key, new TypeToken<T>(){}.getType());
	}
	
	public boolean isInstance(final Object value) {
        return value != null && GenericTypeReflector.erase(this.type).isInstance(value);
	}
	
	public T cast(final Object value) {
		@SuppressWarnings("unchecked")
		final T casted = (T) value;
		return casted;
	}
	
	@Override
	public int compareTo(final TypedKey<?> key) {
		return this.key().compareTo(key.key());
	}
	
	@Override
	public final String toString() {
		return "TypedKey{" +
	            "key=" + this.key.toString() +
	            ", type=" + this.type.toString() +
	            '}';
	}
}
