package net.hellheim.spongetools.resourcepack.block;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.state.StateContainer;
import org.spongepowered.api.state.StateProperty;

import com.google.common.base.Splitter;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

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
	
	// TODO TEST THIS TEST THIS TEST THIS
	Codec<StateCondition> STATE_CONDITION = new StateCodec<StateCondition>() {

		@Override
		public <T> DataResult<T> encode(
			final StateCondition input, final DynamicOps<T> ops, final T prefix
		) {
			return switch (input) {
				case final StateCondition.OrCondition condition ->
						StateCondition.LIST_CODEC.encode(condition.conditions(), ops, prefix)
								.map(conditions -> ops.set(ops.emptyMap(), "OR", conditions));
				case final StateCondition.AndCondition condition -> {
					
					yield StateCondition.LIST_CODEC.encode(condition.conditions(), ops, prefix);
				}
				case final StateCondition.PropertyCondition<?> condition -> {
					yield ops.mergeToMap(prefix,
							ops.createString(condition.property().name()),
							ops.createString(condition.valuesString()));
				}
				default -> DataResult.error(() -> "Unknown StateCondition: " + input);
			};
		}

		@Override
		public <T> DataResult<Pair<StateCondition, T>> read(
			final StateOps<T, ?> ops, final T input
		) {
			return ops.getMap(input).flatMap(map -> {
				final long amount = map.entries().count();
				if (amount == 0) {
					return DataResult.error(() -> "No conditions found");
				} else if (amount == 1) {
					final @Nullable T or = map.get("OR");
					if (or != null) {
						return StateCondition.LIST_CODEC.decode(ops, or)
								.map(pair -> Pair.of(StateCondition.or(pair.getFirst()), pair.getSecond()));
					}
					
					final @Nullable T and = map.get("AND");
					if (and != null) {
						return StateCondition.LIST_CODEC.decode(ops, and)
								.map(pair -> Pair.of(StateCondition.and(pair.getFirst()), pair.getSecond()));
					}
					
					return StateCodec.decodePropertyCondition(ops, map.entries().findAny().get())
							.map(condition -> Pair.of(condition, ops.empty()));
				} else {
					final List<? extends StateCondition> conditions = map.entries()
							.map(entry -> StateCodec.decodePropertyCondition(ops, entry))
							.filter(DataResult::isSuccess)
							.map(DataResult::getOrThrow)
							.toList();
					
					return conditions.isEmpty()
							? DataResult.error(() -> "No conditions found")
							: DataResult.success(Pair.of(StateCondition.and(conditions), ops.empty()));
				}
			});
		}
	};
	
	Codec<BlockDefinition.MultiVariant> MULTI_VARIANT = new StateCodec<>() {
		
		@Override
		public <T> DataResult<T> encode(
			final BlockDefinition.MultiVariant input, final DynamicOps<T> ops, final T prefix
		) {
			return StateDispatch.CODEC.encode(input.dispatch(), ops, prefix)
					.map(dispatch -> ops.set(ops.emptyMap(), StateCodec.KEY_VARIANT, dispatch));
		}
		
		@Override
		public <T> DataResult<Pair<BlockDefinition.MultiVariant, T>> read(
			final StateOps<T, ?> ops, final T input
		) {
			if (!(ops.getAsStateContainer() instanceof final BlockType block)) {
				return DataResult.error(() -> "StateOps container not a BlockType");
			}
			
			return ops.get(input, StateCodec.KEY_VARIANT)
					.flatMap(dispatch -> StateDispatch.CODEC.decode(ops, dispatch))
					.map(pair -> Pair.of(BlockDefinition.variant(block).dispatch(pair.getFirst()).build(), pair.getSecond()));
		}
	};
	
	Codec<BlockDefinition.MultiPart> MULTI_PART = new StateCodec<>() {
		
		@Override
		public <T> DataResult<T> encode(
			final BlockDefinition.MultiPart input, final DynamicOps<T> ops, final T prefix
		) {
			return StatePart.LIST_CODEC.encode(input.parts(), ops, prefix)
					.map(parts -> ops.set(ops.emptyMap(), StateCodec.KEY_PART, parts));
		}
		
		@Override
		public <T> DataResult<Pair<BlockDefinition.MultiPart, T>> read(
			final StateOps<T, ?> ops, final T input
		) {
			if (!(ops.getAsStateContainer() instanceof final BlockType block)) {
				return DataResult.error(() -> "StateOps container not a BlockType");
			}
			
			return ops.get(input, StateCodec.KEY_PART)
					.flatMap(parts -> StatePart.LIST_CODEC.decode(ops, parts))
					.map(pair -> Pair.of(BlockDefinition.part(block).add(pair.getFirst()).build(), pair.getSecond()));
		}
	};
	
	Codec<BlockDefinition> BLOCK_DEFINITION = new Codec<>() {
		
		@Override
		public <T> DataResult<T> encode(
			final BlockDefinition input, final DynamicOps<T> ops, final T prefix
		) {
			return switch (input) {
				case BlockDefinition.MultiVariant definition ->
						BlockDefinition.MultiVariant.CODEC.encode(definition, ops, prefix);
				case BlockDefinition.MultiPart definition ->
						BlockDefinition.MultiPart.CODEC.encode(definition, ops, prefix);
				default -> DataResult.error(() -> "Unknown BlockDefinition: " + input);
			};
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> DataResult<Pair<BlockDefinition, T>> decode(
			final DynamicOps<T> ops, final T input
		) {
			final @Nullable T variant = ops.get(input, StateCodec.KEY_VARIANT).result().orElse(null);
			if (variant != null) {
				return (DataResult<Pair<BlockDefinition, T>>) (Object)
						BlockDefinition.MultiVariant.CODEC.decode(ops, variant);
			}
			
			final @Nullable T part = ops.get(input, StateCodec.KEY_PART).result().orElse(null);
			if (part != null) {
				return (DataResult<Pair<BlockDefinition, T>>) (Object)
						BlockDefinition.MultiPart.CODEC.decode(ops, variant);
			}
			
			return DataResult.error(() -> "Unknown BlockDefinition");
		}
	};
	
	String KEY_VARIANT = "variants";
	String KEY_PART = "multipart";
	
	Splitter COMMA_SPLITTER = Splitter.on(',');
	Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);
	Splitter PIPE_SPLITTER = Splitter.on('|').omitEmptyStrings();
	
	<T> DataResult<Pair<A, T>> read(StateOps<T, ?> ops, T input);
	
	@Override
	default <T> DataResult<Pair<A, T>> decode(final DynamicOps<T> ops, final T input) {
		return ops instanceof final StateOps<T, ?> stateOps
				? this.read(stateOps, input)
				: DataResult.error(() -> "Not a StateOps");
	}
	
	static DataResult<StateSelector> decodeSelector(final StateContainer<?> container, final String selector) {
		final StateSelector.Builder builder = StateSelector.builder();
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
					"State property without value: '%s'",
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
	
	static <T> DataResult<StateCondition> decodePropertyCondition(
		final StateOps<T, ?> ops, final Pair<T, T> entry
	) {
		final DataResult<String> key = ops.getStringValue(entry.getFirst());
		if (key.isError()) {
			return DataResult.error(key.error().get().messageSupplier());
		}
		
		final String propertyName = key.getOrThrow();
		final @Nullable StateProperty<?> property = ops.findStateProperty(propertyName).orElse(null);
		if (property == null) {
			return DataResult.error(() -> String.format(
					"Unknown state property '%' on container '%'",
					propertyName, ops.getAsStateContainer()
					));
		}
		
		final DataResult<String> value = ops.getStringValue(entry.getSecond());
		if (value.isError()) {
			return DataResult.error(value.error().get().messageSupplier());
		}
		
		String values = value.getOrThrow();
		final boolean negate = !values.isEmpty() && values.charAt(0) == '!';
		if (negate) {
			values = values.substring(1);
		}
		
		if (values.isEmpty()) {
			return DataResult.error(() -> String.format(
					"Empty property values for property '%s' on container '%'",
					propertyName, ops.getAsStateContainer()
					));
		}
		
		return StateCodec.decodePropertyConditionValues(ops, property, negate, values);
	}
	
	static <T extends Comparable<T>> DataResult<StateCondition> decodePropertyConditionValues(
		final StateOps<?, ?> ops,
		final StateProperty<T> property, final boolean negate, final String valueNames
	) {
		final Set<T> values = new HashSet<>();
		for (final String valueName : StateCodec.PIPE_SPLITTER.split(valueNames)) {
			final @Nullable T value = property.parseValue(valueName).orElse(null);
			if (value == null) {
				return DataResult.error(() -> String.format(
						"Unknown value '%' for property '%' in '%' on container '%'",
						valueName, property.name(), valueNames, ops.getAsStateContainer()
						),
						StateCondition.property(property, negate, values));
			}
			
			values.add(value);
		}
		
		return DataResult.success(StateCondition.property(property, negate, values));
	}
}
