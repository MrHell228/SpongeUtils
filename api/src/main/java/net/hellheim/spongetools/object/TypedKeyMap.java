package net.hellheim.spongetools.object;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface TypedKeyMap {
	
	static TypedKeyMap empty() {
		return TypedKeyMap.Impl.EMPTY;
	}
	
	static TypedKeyMap.Mutable create() {
		return new TypedKeyMap.Impl.Mutable();
	}
	
	Set<TypedKey<?>> keySet();
	
	/**
	 * Returns whether this {@link TypedKeyMap} contains value for provided {@link TypedKey}.
	 * 
	 * @param key The key
	 * @return True if this {@link TypedKeyMap} contains value for provided key
	 */
	boolean has(TypedKey<?> key);
	
	/**
	 * Returns the value assosiated with provided {@link TypedKey} for this {@link TypedKeyMap},
	 * or {@link Optional#empty()} if there is no value for key.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The value, if available
	 */
	<T> Optional<T> get(TypedKey<? extends T> key);
	
	/**
	 * Returns the value assosiated with provided {@link TypedKey} for this {@link TypedKeyMap}. <br>
	 * <br>
	 * If there is no value for key, {@link NoSuchElementException} will be thrown.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The value
	 * @throws NoSuchElementException If there is no value for provided key
	 */
	<T> T require(TypedKey<? extends T> key);
	
	/**
	 * Returns the value assosiated with provided {@link TypedKey} for this {@link TypedKeyMap},
	 * or <code>defaultValue</code> if there is no value for key.
	 * 
	 * @param <T> The type of key and default value
	 * @param key The key
	 * @param defaultValue The default value
	 * @return The value, or default if not set
	 */
	<T> T getOrElse(TypedKey<? extends T> key, T defaultValue);
	
	/**
	 * Returns the value assosiated with provided {@link TypedKey} for this {@link TypedKeyMap},
	 * or <code>null</code> if there is no value for key.
	 * 
	 * @param <T> The type of key
	 * @param key The key
	 * @return The value, or null if not set
	 */
	<T> T getOrNull(TypedKey<? extends T> key);
	
	interface Mutable extends TypedKeyMap {
		
		/**
		 * Sets value for the provided {@link TypedKey} for this {@link TypedKeyMap}. <br>
		 * Returns the previously assosiated value, or {@link Optional#empty()} if there was no value for key.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param value The value
		 * @return Previously assotiated value for key, if present
		 */
		<T> Optional<T> set(TypedKey<T> key, T value);
		
		/**
		 * Removes provided {@link TypedKey} for this {@link TypedKeyMap}. <br>
		 * Returns the previously assosiated value, or {@link Optional#empty()} if there was no value for key.
		 * 
		 * @param <T> The type of key
		 * @param key The key
		 * @return Previously assotiated value for key, if present
		 */
		<T> Optional<T> remove(TypedKey<? extends T> key);
		
		/**
		 * Sets value for the specified {@link TypedKey} for this {@link TypedKeyMap}
		 * only if provided {@link Optional} is present.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param optionalValue The optional value
		 */
		default <T> void trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
			optValue.ifPresent(value -> this.set(key, value));
		}
		
		/**
		 * If provided {@link Optional} is present, sets its value for the specified {@link TypedKey}. <br>
		 * If the {@link Optional} is empty, removes provided {@link TypedKey} for this {@link TypedKeyMap}.
		 * 
		 * @param <T>
		 * @param key
		 * @param optValue
		 */
		default <T> void apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
			optValue.ifPresentOrElse(value -> this.set(key, value), () -> this.remove(key));
		}
	}
	
	interface Proxy extends TypedKeyMap {
		
		/**
		 * Returns the underlying {@link TypedKeyMap} for this {@link TypedKeyMap.Proxy}.
		 * 
		 * @return The underlying {@link TypedKeyMap}.
		 */
		TypedKeyMap data();
		
		@Override
		default Set<TypedKey<?>> keySet() {
			return this.data().keySet();
		}
		
		@Override
		default boolean has(final TypedKey<?> key) {
			return this.data().has(key);
		}
		
		@Override
		default <T> Optional<T> get(final TypedKey<? extends T> key) {
			return this.data().get(key);
		}
		
		@Override
		default <T> T require(final TypedKey<? extends T> key) {
			return this.data().require(key);
		}
		
		@Override
		default <T> T getOrElse(final TypedKey<? extends T> key, final T defaultValue) {
			return this.data().getOrElse(key, defaultValue);
		}
		
		@Override
		default <T> T getOrNull(final TypedKey<? extends T> key) {
			return this.data().getOrNull(key);
		}
		
		interface Mutable extends Proxy, TypedKeyMap.Mutable {
			
			@Override
			TypedKeyMap.Mutable data();
			
			@Override
			default <T> Optional<T> set(final TypedKey<T> key, final T value) {
				return this.data().set(key, value);
			}
			
			@Override
			default <T> Optional<T> remove(final TypedKey<? extends T> key) {
				return this.data().remove(key);
			}
			
			@Override
			default <T> void trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.data().trySet(key, optValue);
			}
			
			@Override
			default <T> void apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.data().apply(key, optValue);
			}
		}
	}
	
	class Impl implements TypedKeyMap {
		
		private static final TypedKeyMap EMPTY = new Impl(Map.of());
		
		protected final Map<TypedKey<?>, Object> values;
		
		protected Impl(final Map<TypedKey<?>, Object> emptyMap) {
			this.values = emptyMap;
		}
		
		@Override
		public Set<TypedKey<?>> keySet() {
			return this.values.keySet();
		}
		
		@Override
		public boolean has(final TypedKey<?> key) {
			return this.values.containsKey(key);
		}
		
		@Override
		public <T> Optional<T> get(final TypedKey<? extends T> key) {
			return Optional.ofNullable(key.cast(this.values.get(key)));
		}
		
		@Override
		public <T> T require(final TypedKey<? extends T> key) {
			final @Nullable Object value = this.values.get(key);
			if (value == null) {
				throw new NoSuchElementException("Could not retrieve value for key " + key.toString());
			}
			return key.cast(value);
		}
		
		@Override
		public <T> T getOrElse(final TypedKey<? extends T> key, final T defaultValue) {
			return key.cast(this.values.getOrDefault(key, defaultValue));
		}
		
		@Override
		public <T> T getOrNull(final TypedKey<? extends T> key) {
			return key.cast(this.values.get(key));
		}
		
		@Override
		public int hashCode() {
			return this.values.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			} else if (obj instanceof final TypedKeyMap that) {
				if (this.keySet().size() != that.keySet().size()) {
					return false;
				}
				
				for (final TypedKey<?> key : that.keySet()) {
					if (!this.has(key) || !Objects.equals(this.require(key), that.require(key))) {
						return false;
					}
				}
				
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			final StringJoiner joiner = new StringJoiner(", ");
	        for (Map.Entry<TypedKey<?>, Object> entry : this.values.entrySet()) {
	            joiner.add("\"" + entry.getKey().toString() + "\"=" + entry.getValue().toString());
	        }
	        return "Context[" + joiner.toString() + "]";
		}
		
		public static class Mutable extends Impl implements TypedKeyMap.Mutable {
			
			protected Mutable() {
				super(new HashMap<>());
			}
			
			@Override
			public <T> Optional<T> set(final TypedKey<T> key, final T value) {
				return Optional.ofNullable(key.cast(this.values.put(key, Objects.requireNonNull(value, "value"))));
			}
			
			@Override
			public <T> Optional<T> remove(final TypedKey<? extends T> key) {
				return Optional.ofNullable(key.cast(this.values.remove(key)));
			}
		}
	}
}
