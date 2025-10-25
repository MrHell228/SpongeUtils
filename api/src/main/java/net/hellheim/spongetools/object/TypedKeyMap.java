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
	
	static TypedKeyMap.Impl.Mutable create() {
		return new TypedKeyMap.Impl.Mutable();
	}
	
	Set<TypedKey<?>> typedKeySet();
	
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
		 * @param optValue The optional value
		 */
		default <T> void trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
			optValue.ifPresent(value -> this.set(key, value));
		}
		
		/**
		 * If provided {@link Optional} is present, sets its value for the specified {@link TypedKey}. <br>
		 * If the {@link Optional} is empty, removes provided {@link TypedKey} for this {@link TypedKeyMap}.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param optValue The optionalValue
		 */
		default <T> void apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
			optValue.ifPresentOrElse(value -> this.set(key, value), () -> this.remove(key));
		}
		
		interface Proxy extends TypedKeyMap.Mutable, TypedKeyMap.Proxy {
			
			@Override
			TypedKeyMap.Mutable context();
			
			@Override
			default <T> Optional<T> set(final TypedKey<T> key, final T value) {
				return this.context().set(key, value);
			}
			
			@Override
			default <T> Optional<T> remove(final TypedKey<? extends T> key) {
				return this.context().remove(key);
			}
			
			@Override
			default <T> void trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().trySet(key, optValue);
			}
			
			@Override
			default <T> void apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().apply(key, optValue);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	interface Operator<B extends Operator<B>> extends TypedKeyMap {
		
		/**
		 * Sets value for the provided {@link TypedKey} for this {@link TypedKeyMap}.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param value The value
		 * @return This operator, for chaining
		 */
		<T> B set(TypedKey<T> key, T value);
		
		/**
		 * Removes provided {@link TypedKey} for this {@link TypedKeyMap}. <br>
		 * 
		 * @param <T> The type of key
		 * @param key The key
		 * @return This operator, for chaining
		 */
		B remove(TypedKey<?> key);
		
		/**
		 * Sets value for the specified {@link TypedKey} for this {@link TypedKeyMap}
		 * only if provided {@link Optional} is present.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param optValue The optional value
		 * @return This operator, for chaining
		 */
		<T> B trySet(TypedKey<T> key, Optional<? extends T> optValue);
		
		/**
		 * If provided {@link Optional} is present, sets its value for the specified {@link TypedKey}. <br>
		 * If the {@link Optional} is empty, removes provided {@link TypedKey} for this {@link TypedKeyMap}.
		 * 
		 * @param <T> The type of key and value
		 * @param key The key
		 * @param optValue The optionalValue
		 * @return This operator, for chaining
		 */
		<T> B apply(TypedKey<T> key, Optional<? extends T> optValue);
		
		interface MutableProxy<B extends Operator<B>> extends TypedKeyMap.Operator<B>, TypedKeyMap.Proxy {
			
			@Override
			TypedKeyMap.Mutable context();
			
			@Override
			default <T> B set(final TypedKey<T> key, final T value) {
				this.context().set(key, value);
				return (B) this;
			}
			
			@Override
			default B remove(final TypedKey<?> key) {
				this.context().remove(key);
				return (B) this;
			}
			
			@Override
			default <T> B trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().trySet(key, optValue);
				return (B) this;
			}
			
			@Override
			default <T> B apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().apply(key, optValue);
				return (B) this;
			}
		}
		
		interface OperatorProxy<B extends Operator<B>> extends TypedKeyMap.Operator<B>, TypedKeyMap.Proxy {
			
			@Override
			TypedKeyMap.Operator<?> context();
			
			@Override
			default <T> B set(final TypedKey<T> key, final T value) {
				this.context().set(key, value);
				return (B) this;
			}
			
			@Override
			default B remove(final TypedKey<?> key) {
				this.context().remove(key);
				return (B) this;
			}
			
			@Override
			default <T> B trySet(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().trySet(key, optValue);
				return (B) this;
			}
			
			@Override
			default <T> B apply(final TypedKey<T> key, final Optional<? extends T> optValue) {
				this.context().apply(key, optValue);
				return (B) this;
			}
		}
	}
	
	interface Proxy extends TypedKeyMap {
		
		/**
		 * Returns the underlying {@link TypedKeyMap} for this {@link TypedKeyMap.Proxy}.
		 * 
		 * @return The underlying {@link TypedKeyMap}.
		 */
		TypedKeyMap context();
		
		@Override
		default Set<TypedKey<?>> typedKeySet() {
			return this.context().typedKeySet();
		}
		
		@Override
		default boolean has(final TypedKey<?> key) {
			return this.context().has(key);
		}
		
		@Override
		default <T> Optional<T> get(final TypedKey<? extends T> key) {
			return this.context().get(key);
		}
		
		@Override
		default <T> T require(final TypedKey<? extends T> key) {
			return this.context().require(key);
		}
		
		@Override
		default <T> T getOrElse(final TypedKey<? extends T> key, final T defaultValue) {
			return this.context().getOrElse(key, defaultValue);
		}
		
		@Override
		default <T> T getOrNull(final TypedKey<? extends T> key) {
			return this.context().getOrNull(key);
		}
	}
	
	class Impl implements TypedKeyMap {
		
		private static final TypedKeyMap EMPTY = new Impl(Map.of());
		
		protected final Map<TypedKey<?>, Object> values;
		
		protected Impl(final Map<TypedKey<?>, Object> values) {
			this.values = values;
		}
		
		public Impl.Mutable asMutable() {
			return new Impl.Mutable(this.values);
		}
		
		public Impl.Mutable asMutableCopy() {
			return new Impl.Mutable(this.values);
		}
		
		public Impl asImmutable() {
			return this;
		}
		
		@Override
		public Set<TypedKey<?>> typedKeySet() {
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
				if (this.typedKeySet().size() != that.typedKeySet().size()) {
					return false;
				}
				
				for (final TypedKey<?> key : that.typedKeySet()) {
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
			
			protected Mutable(final Map<TypedKey<?>, Object> values) {
				super(new HashMap<>(values));
			}
			
			@Override
			public Impl.Mutable asMutable() {
				return this;
			}
			
			@Override
			public Impl asImmutable() {
				return new Impl(Map.copyOf(this.values));
			}
			
			@Override
			public <T> Optional<T> set(final TypedKey<T> key, final T value) {
				return Optional.ofNullable(key.cast(this.values.put(key, Objects.requireNonNull(value, "value"))));
			}
			
			@Override
			public <T> Optional<T> remove(final TypedKey<? extends T> key) {
				return Optional.ofNullable(key.cast(this.values.remove(key)));
			}
			
			public void clear() {
				this.values.clear();
			}
		}
	}
}
