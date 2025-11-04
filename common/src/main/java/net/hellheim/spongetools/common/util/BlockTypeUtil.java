package net.hellheim.spongetools.common.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.state.StateProperty;

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
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class BlockTypeUtil {
	
	public static final DefaultedRegistryReference<BlockType> NETWORK_BLOCK = BlockTypes.STONE;
	
	public static BlockBehaviour.Properties properties(
		final DefaultedRegistryReference<BlockType> networkBlockKey,
		final TypedKeyMap context, final Object behaviour
	) {
		final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, Converter.asVanilla(networkBlockKey.location())));
		
		((BlockPropertiesBridge) properties).spongetools$bridge$applyData(new AdditionalData(
				networkBlockKey.get(),
				context.require(BlockTypeKeys.DISPLAY_STATE),
				context.getOrElse(BlockTypeKeys.STATE_PROPERTIES, Set.of()),
				context.getOrElse(BlockTypeKeys.DEFAULT_STATE, List.of())
				));
		
		properties.overrideDescription(context.require(BlockTypeKeys.TRANSLATION_KEY));
		
		properties.overrideLootTable(context.get(BlockTypeKeys.LOOT_TABLE)
				.map(Converter::asVanilla)
				.map(key -> ResourceKey.create(Registries.LOOT_TABLE, key))
				.map(Optional::of)
				.orElse(Optional.empty()));
		
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
				(context, behaviour) -> assembler.apply(context, properties(NETWORK_BLOCK, context, behaviour)));
	}
	
	public static final class Archetypes {
		
		public static final BlockArchetype BLOCK = archetype(
				Optional.empty(),
				Block.class,
				Set.of(BlockTypeKeys.TRANSLATION_KEY, BlockTypeKeys.DISPLAY_STATE),
				(block, context) -> {
					context.set(BlockTypeKeys.TRANSLATION_KEY, block.getDescriptionId());
					context.apply(BlockTypeKeys.LOOT_TABLE, block.getLootTable()
							.map(ResourceKey::location)
							.map(Converter::asSponge));
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
		BlockType networkBlock, UnaryOperator<BlockState> display,
		Set<StateProperty<?>> properties, List<StatePropertyValue<?>> defautProperties) {
	}
	
	private BlockTypeUtil() {
	}
}
