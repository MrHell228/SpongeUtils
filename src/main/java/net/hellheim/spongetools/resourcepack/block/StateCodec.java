package net.hellheim.spongetools.resourcepack.block;


import java.util.Iterator;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.state.StateContainer;
import org.spongepowered.api.state.StateProperty;

import com.google.common.base.Splitter;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import net.hellheim.spongetools.resourcepack.block.StateSelector.Builder;

interface StateCodec<A> extends Codec<A> {
	
	Codec<StatePropertyValue<?>> STATE_PROPERTY_VALUE = new StateCodec<>() {
		@Override
		public <T> DataResult<T> encode(
			final StatePropertyValue<?> input, final DynamicOps<T> ops, final T prefix
		) {
			return DataResult.success(ops.createString(input.serializationString()));
		}
		
		@Override
		public <T> DataResult<Pair<StatePropertyValue<?>, T>> read(
			final StateOps<T, ?> ops, final T input
		) {
			return ops.getStringValue(input).flatMap(fullProperty ->
					StateCodec.decodePropertyValue(ops, fullProperty)
							.map(value -> Pair.of(value, ops.empty())));
		}
	};
	
	Codec<StateSelector> STATE_SELECTOR = new StateCodec<>() {
		@Override
		public <T> DataResult<T> encode(
			final StateSelector input, final DynamicOps<T> ops, final T prefix
		) {
			return DataResult.success(ops.createString(input.serializationString()));
		}
		
		@Override
		public <T> DataResult<Pair<StateSelector, T>> read(
			final StateOps<T, ?> ops, final T input
		) {
			return ops.getStringValue(input).flatMap(selector ->
					StateCodec.decodeSelector(ops, selector)
							.map(value -> Pair.of(value, ops.empty())));
		}
	};
	
	Splitter COMMA_SPLITTER = Splitter.on(',');
	Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);
	
	<T> DataResult<Pair<A, T>> read(StateOps<T, ?> ops, T input);
	
	@Override
	default <T> DataResult<Pair<A, T>> decode(final DynamicOps<T> ops, final T input) {
		return ops instanceof final StateOps<T, ?> stateOps
				? this.read(stateOps, input)
				: DataResult.error(() -> "Not a StateOps");
	}
	
	static DataResult<StateSelector> decodeSelector(final StateContainer<?> container, final String selector) {
		final Builder builder = StateSelector.builder();
		for (final String property : COMMA_SPLITTER.split(selector)) {
			final var value = StateCodec.decodePropertyValue(container, property);
			if (value.isError()) {
				return DataResult.error(value.error().get().messageSupplier(), builder.build());
			}
			
			builder.add(value.result().get());
		}
		
		return DataResult.success(builder.build());
	}
	
	static DataResult<StatePropertyValue<?>> decodePropertyValue(
			final StateContainer<?> container, final String fullProperty
		) {
			final Iterator<String> propertyData = EQUAL_SPLITTER.split(fullProperty).iterator();
			
			final String propertyString = propertyData.next();
			if (propertyString.isEmpty()) {
				return DataResult.error(() -> "Empty state property");
			}
			
			final @Nullable StateProperty<?> property = container.findStateProperty(propertyString).orElse(null);
			if (property == null) {
				return DataResult.error(() -> String.format(
						"Unknown state property: '%s'",
						propertyString
						));
			}
			
			if (!propertyData.hasNext()) {
				return DataResult.error(() -> String.format(
						"State property without value: %s",
						propertyString
						));
			}
			
			final String valueString = propertyData.next();
			if (valueString.isEmpty()) {
				return DataResult.error(() -> "Empty state property value");
			}
			
			final @Nullable Comparable<?> value = property.parseValue(valueString).orElse(null);
			if (value == null) {
				return DataResult.error(() -> String.format(
						"Unknown value: '%s' for state property: '%s' %s",
						valueString, propertyString, property.possibleValues()
						));
			}
			
			return DataResult.success(StatePropertyValue.ofRaw(property, value));
		}
}
