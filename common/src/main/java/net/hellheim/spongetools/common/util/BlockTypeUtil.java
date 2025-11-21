package net.hellheim.spongetools.common.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.state.StateProperty;

import com.google.common.base.Suppliers;

import net.hellheim.spongetools.bridge.BlockPropertiesBridge;
import net.hellheim.spongetools.common.factory.StatePropertyValueFactory;
import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.block.BlockTypeKeys;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public final class BlockTypeUtil {
	
	public static final DefaultedRegistryReference<BlockType> NETWORK_BLOCK = BlockTypes.STONE;
	public static final Supplier<BlockState> DEFAULT_STATE = Suppliers.memoize(() -> NETWORK_BLOCK.get().defaultState());
	
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
		
		((BlockPropertiesBridge) properties).spongetools$bridge$applyData(new AdditionalData(
				networkBlockKey.get(),
				context.getOrElse(BlockTypeKeys.STATE_PROPERTIES, Set.of()),
				context.getOrElse(BlockTypeKeys.DEFAULT_STATE, List.of())
				));
		
		return properties;
	}
	
	public static <I extends Block> BlockArchetype archetype(
		final Optional<BlockArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final BiFunction<TypedKeyMap, BlockBehaviour.Properties, I> assembler
	) {
		return BlockArchetype.of(parent, baseClass, requiredKeys, contextExtractor,
				(data, context, behaviour) -> assembler.apply(context, properties(NETWORK_BLOCK, data, context, behaviour)));
	}
	
	public static final class Archetypes {
		
		public static final BlockArchetype DEFAULT = BlockTypeUtil.archetype(
				Optional.empty(),
				Block.class,
				Set.of(BlockTypeKeys.TRANSLATION_KEY),
				(block, context) -> {
					context.set(BlockTypeKeys.TRANSLATION_KEY, block.getDescriptionId());
					
					context.set(BlockTypeKeys.STATE_PROPERTIES, block.defaultBlockState().getProperties().stream()
							.map(Converter::asSponge)
							.collect(Collectors.toSet()));
					context.set(BlockTypeKeys.DEFAULT_STATE, block.defaultBlockState().getValues().entrySet().stream()
							.map(e -> StatePropertyValueFactory.ofRawVanilla(e.getKey(), e.getValue()))
							.map(Converter::asSponge)
							.collect(Collectors.toList()));
				},
				(context, properties) -> new Block(properties));
		
		private Archetypes() {
		}
	}
	
	public static final record AdditionalData(
		BlockType networkBlock, Set<StateProperty<?>> properties, List<StatePropertyValue<?>> defautProperties) {
	}
	
	private BlockTypeUtil() {
	}
}
