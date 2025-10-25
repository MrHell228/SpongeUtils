package net.hellheim.spongetools.object;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.value.CollectionValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;

import com.google.common.collect.Streams;

@SuppressWarnings("unchecked")
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
	default <V> B add(final Value<V> value) {
		return (B) this.add(value.key(), value.get());
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
	
	default <V, C extends Collection<V>> B supplyAll(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final Supplier<? extends V>... elements) {
		return this.addAll(key, Arrays.stream(elements).map(Supplier::get));
	}
	
	default <V, C extends Collection<V>> B supplyAll(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final Supplier<? extends Iterable<? extends V>> elements) {
		return this.addAll(key, elements.get());
	}
	
	default <V, C extends Collection<V>> B supplyAll(final Key<? extends CollectionValue<V, C>> key, final Supplier<? extends V>... elements) {
		return this.addAll(key, Arrays.stream(elements).map(Supplier::get));
	}
	
	default <V, C extends Collection<V>> B supplyAll(final Key<? extends CollectionValue<V, C>> key, final Supplier<? extends Iterable<? extends V>> elements) {
		return this.addAll(key, elements.get());
	}
	
	default <V, C extends Collection<V>> B addAll(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final V... elements) {
		return this.addAll(key.get(), elements);
	}
	
	default <V, C extends Collection<V>> B addAll(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final Iterable<? extends V> elements) {
		return this.addAll(key.get(), elements);
	}
	
	default <V, C extends Collection<V>> B addAll(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final Stream<? extends V> elements) {
		return this.addAll(key.get(), elements);
	}
	
	default <V, C extends Collection<V>> B addAll(final Key<? extends CollectionValue<V, C>> key, final V... elements) {
		return this.addAll(key, Arrays.stream(elements));
	}
	
	default <V, C extends Collection<V>> B addAll(final Key<? extends CollectionValue<V, C>> key, final Iterable<? extends V> elements) {
		return this.addAll(key, Streams.stream(elements));
	}
	
	<V, C extends Collection<V>> B addAll(final Key<? extends CollectionValue<V, C>> key, final Stream<? extends V> elements);
	
	default <V, C extends Collection<V>> B supplySingle(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final Supplier<? extends V> element) {
		return this.addSingle(key.get(), element.get());
	}
	
	default <V, C extends Collection<V>> B supplySingle(final Key<? extends CollectionValue<V, C>> key, final Supplier<? extends V> element) {
		return this.addSingle(key, element.get());
	}
	
	default <V, C extends Collection<V>> B addSingle(final Supplier<? extends Key<? extends CollectionValue<V, C>>> key, final V element) {
		return this.addSingle(key.get(), element);
	}
	
	/**
	 * Adds the given element to currently present for the given {@link Key}.
	 * TODO doc
	 * 
	 * @param key     The key to assign the element with
	 * @param element The element to assign with the key
	 * @param <V>     The type of the element
	 * @return The builder, for chaining
	 */
	<V, C extends Collection<V>> B addSingle(Key<? extends CollectionValue<V, C>> key, V element);
	
	B reset();
	
	public interface Proxy<B extends DataOperator<B>> extends DataOperator<B> {
		
		DataOperator<?> getAsData();
		
		@Override
		default <V> B add(final Key<? extends Value<V>> key, final V value) {
			this.getAsData().add(key, value);
			return (B) this;
		}
		
		@Override
		default <V, C extends Collection<V>> B addAll(
			final Key<? extends CollectionValue<V, C>> key, final Stream<? extends V> elements
		) {
			this.getAsData().addAll(key, elements);
			return (B) this;
		}
		
		@Override
		default <V, C extends Collection<V>> B addSingle(
			final Key<? extends CollectionValue<V, C>> key, final V element
		) {
			this.getAsData().addSingle(key, element);
			return (B) this;
		}
	}
}
