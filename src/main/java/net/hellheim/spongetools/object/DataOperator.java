package net.hellheim.spongetools.object;

import java.util.function.Supplier;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

public interface DataOperator<B extends DataOperator<B>> {
	
	/**
	 * Adds all the {@link Value}s from the {@link ValueContainer} to the builder.
	 *
	 * @param container The container to add
	 * @return This builder, for chaining
	 */
	default B supplyFrom(final Supplier<? extends ValueContainer> container) {
		return this.addFrom(container.get());
	}
	
	/**
	 * Adds all the {@link Value}s from the {@link ValueContainer} to the builder.
	 *
	 * @param container The container to add
	 * @return This builder, for chaining
	 */
	default B addFrom(final ValueContainer container) {
		return this.addAll(container.getValues());
	}
	
	/**
	 * Adds all the {@link Value}s to the builder.
	 *
	 * @param values The values to add
	 * @return This builder, for chaining
	 */
	default B supplyAll(final Supplier<? extends Iterable<? extends Value<?>>> values) {
		return this.addAll(values.get());
	}
	
	/**
	 * Adds all the {@link Value}s to the builder.
	 *
	 * @param values The values to add
	 * @return This builder, for chaining
	 */
	@SuppressWarnings("unchecked")
	default B addAll(final Iterable<? extends Value<?>> values) {
		values.forEach(this::add);
		return (B) this;
	}
	
	/**
	 * Adds the given {@link Value} to the builder.
	 *
	 * @param value The value to add
	 * @return This builder, for chaining
	 */
	default B supply(final Supplier<? extends Value<?>> value) {
		return this.add(value.get());
	}
	
	/**
	 * Adds the given {@link Value} to the builder.
	 *
	 * @param value The value to add
	 * @return This builder, for chaining
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	default B add(final Value<?> value) {
		return (B) this.add((Key) value.key(), value.get());
	}
	
	/**
	 * Adds the given {@link Key} with the given value.
	 *
	 * @param key   The key to assign the value with
	 * @param value The value to assign with the key
	 * @param <V>   The type of the value
	 * @return This builder, for chaining
	 */
	default <V> B supply(final Supplier<? extends Key<? extends Value<V>>> key, final Supplier<? extends V> value) {
		return this.add(key.get(), value.get());
	}
	
	/**
	 * Adds the given {@link Key} with the given value.
	 *
	 * @param key   The key to assign the value with
	 * @param value The value to assign with the key
	 * @param <V>   The type of the value
	 * @return This builder, for chaining
	 */
	default <V> B supply(final Key<? extends Value<V>> key, final Supplier<? extends V> value) {
		return this.add(key, value.get());
	}
	
	/**
	 * Adds the given {@link Key} with the given value.
	 *
	 * @param key   The key to assign the value with
	 * @param value The value to assign with the key
	 * @param <V>   The type of the value
	 * @return This builder, for chaining
	 */
	default <V> B add(final Supplier<? extends Key<? extends Value<V>>> key, final V value) {
		return this.add(key.get(), value);
	}
	
	/**
	 * Adds the given {@link Key} with the given value.
	 *
	 * @param key   The key to assign the value with
	 * @param value The value to assign with the key
	 * @param <V>   The type of the value
	 * @return This builder, for chaining
	 */
	<V> B add(Key<? extends Value<V>> key, V value);
	
	B reset();
}
