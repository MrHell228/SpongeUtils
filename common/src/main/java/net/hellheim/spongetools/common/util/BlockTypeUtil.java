package net.hellheim.spongetools.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.state.BooleanStateProperties;
import org.spongepowered.api.state.IntegerStateProperty;
import org.spongepowered.api.state.StateProperty;

import com.google.common.base.Suppliers;

import net.hellheim.spongetools.bridge.BlockPropertiesBridge;
import net.hellheim.spongetools.common.factory.StatePropertyValueFactory;
import net.hellheim.spongetools.custom.type.block.BlockTypeArchetype;
import net.hellheim.spongetools.custom.type.block.BlockTypeKeys;
import net.hellheim.spongetools.mixin.world.level.block.ScaffoldingBlockAccessor;
import net.hellheim.spongetools.mixin.world.level.block.state.BlockBehaviour_PropertiesAccessor;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public final class BlockTypeUtil {
	
	public static final DefaultedRegistryReference<BlockType> NETWORK_BLOCK = BlockTypes.STONE;
	public static final Supplier<BlockState> DEFAULT_STATE = Suppliers.memoize(() -> NETWORK_BLOCK.get().defaultState());
	
	public static final Predicate<StateProperty<?>> SCAFFOLDING_DISTANCE_PROPERTY_FILTER =
			p -> p.name().equals("distance") && p instanceof IntegerStateProperty;
	
	public static BlockBehaviour.Properties properties(
		final DefaultedRegistryReference<BlockType> networkBlockKey,
		final ValueContainer data, final TypedKeyMap context, final Object behaviour
	) {
		final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Converter.asVanilla(networkBlockKey.location())))
				.overrideDescription(context.require(BlockTypeKeys.TRANSLATION_KEY))
				.overrideLootTable(data.get(BlockTypeKeys.LOOT_TABLE)
						.map(Converter::asVanilla)
						.map(key -> ResourceKey.create(Registries.LOOT_TABLE, key))
						.map(Optional::of)
						.orElse(Optional.empty()));
		
		final BlockBehaviour_PropertiesAccessor accessor = (BlockBehaviour_PropertiesAccessor) properties;
		
		properties.sound(data.get(BlockTypeKeys.SOUND_GROUP)
				.map(SoundType.class::cast)
				.orElse(SoundType.EMPTY));
		data.get(BlockTypeKeys.MAP_COLOR).map(Converter::asVanilla).ifPresent(properties::mapColor);
		data.get(Keys.REPRESENTED_INSTRUMENT).map(NoteBlockInstrument.class::cast).ifPresent(properties::instrument);
		
		data.get(Keys.DESTROY_SPEED).map(Double::floatValue).ifPresent(properties::strength);
		data.get(Keys.BLAST_RESISTANCE).map(Double::floatValue).ifPresent(properties::explosionResistance);
		data.get(BlockTypeKeys.REQUIRE_TOOL).filter(Boolean::booleanValue).ifPresent($true -> properties.requiresCorrectToolForDrops());
		
		data.get(BlockTypeKeys.SPEED_FACTOR).map(Double::floatValue).ifPresent(properties::speedFactor);
		data.get(BlockTypeKeys.JUMP_FACTOR).map(Double::floatValue).ifPresent(properties::jumpFactor);
		data.get(BlockTypeKeys.FRICTION_FACTOR).map(Double::floatValue).ifPresent(properties::friction);
		
		data.get(Keys.BURNABLE).filter(Boolean::booleanValue).ifPresent($true -> properties.ignitedByLava());
		data.get(Keys.PUSH_REACTION).map(PushReaction.class::cast).ifPresent(properties::pushReaction);
		
		data.get(BlockTypeKeys.HAS_COLLISION).ifPresent(accessor::accessor$hasCollision);
		data.get(BlockTypeKeys.HAS_DYNAMIC_SHAPE).ifPresent(accessor::accessor$dynamicShape);
		data.get(BlockTypeKeys.HAS_OCCLUSION).ifPresent(accessor::accessor$canOcclude);
		
		((BlockPropertiesBridge) properties).spongetools$bridge$applyData(new AdditionalData(
				networkBlockKey.get(),
				context.getOrElse(BlockTypeKeys.STATE_PROPERTIES, Set.of()),
				context.getOrElse(BlockTypeKeys.DEFAULT_STATE, List.of())
				));
		
		return properties;
	}
	
	public static <I extends Block> BlockTypeArchetype archetype(
		final Optional<BlockTypeArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final BiFunction<TypedKeyMap, BlockBehaviour.Properties, I> assembler
	) {
		return BlockTypeArchetype.of(parent, baseClass, requiredKeys, contextExtractor,
				(data, context, behaviour) -> assembler.apply(context, properties(NETWORK_BLOCK, data, context, behaviour)));
	}
	
	public static final class Archetypes {
		
		public static final BlockTypeArchetype DEFAULT = BlockTypeUtil.archetype(
				Optional.empty(),
				Block.class,
				Set.of(BlockTypeKeys.TRANSLATION_KEY),
				(block, context) -> {
					context.set(BlockTypeKeys.TRANSLATION_KEY, block.getDescriptionId());
					
					context.set(BlockTypeKeys.STATE_PROPERTIES, block.defaultBlockState().getProperties().stream()
							.map(Converter::asSponge)
							.collect(Collectors.toSet()));
					context.set(BlockTypeKeys.DEFAULT_STATE, block.defaultBlockState().getValues()
							.map(e -> StatePropertyValueFactory.ofRawVanilla(e.property(), e.value()))
							.map(Converter::asSponge)
							.collect(Collectors.toList()));
				},
				(context, properties) -> new Block(properties));
		
		public static final BlockTypeArchetype SCAFFOLDING = BlockTypeUtil.archetype(
				Optional.of(Archetypes.DEFAULT),
				ScaffoldingBlock.class,
				Set.of(BlockTypeKeys.STATE_PROPERTIES),
				(block, context) -> {},
				(context, properties) -> {
					final var stateProperties = new HashSet<>(context.require(BlockTypeKeys.STATE_PROPERTIES));
					if (stateProperties.size() >= 3
							&& stateProperties.remove(BooleanStateProperties.property_WATERLOGGED())
							&& stateProperties.remove(BooleanStateProperties.property_BOTTOM())
							&& stateProperties.stream().anyMatch(BlockTypeUtil.SCAFFOLDING_DISTANCE_PROPERTY_FILTER)) {
						return ScaffoldingBlockAccessor.invoker$init(properties);
					} else {
						throw new IllegalStateException(
								"State properties for scaffolding-like block must contain at least 3 elements: "
								+ "BooleanStateProperties.WATERLOGGED, "
								+ "BooleanStateProperties.BOTTOM "
								+ "and IntegerStateProperty named \"distance\". "
								+ "Provided properties are: "
								+ context.require(BlockTypeKeys.STATE_PROPERTIES).stream()
										.map(StateProperty::name)
										.collect(Collectors.joining(", ")));
					}
				});
		
		private Archetypes() {
		}
	}
	
	public record AdditionalData(
		BlockType networkType, Set<StateProperty<?>> properties, List<StatePropertyValue<?>> defaultProperties) {
	}
	
	private BlockTypeUtil() {
	}
}
