package net.hellheim.spongetools.common.util;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.common.item.util.ItemStackUtil;

import net.hellheim.spongetools.bridge.ItemPropertiesBridge;
import net.hellheim.spongetools.custom.type.item.ItemTypeArchetype;
import net.hellheim.spongetools.custom.type.item.ItemTypeKeys;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;

public final class ItemTypeUtil {
	
	private static final DefaultedRegistryReference<ItemType> NETWORK_ITEM = ItemTypes.RABBIT_FOOT;
	
	public static DataComponentPatch componentPatch(final ItemType baseType, final ValueContainer data) {
		final ItemStack stack = ItemStack.of(baseType);
		stack.copyFrom(data);
		return ItemStackUtil.toNative(stack).getComponentsPatch();
	}
	
	public static Item.Properties properties(
		final DefaultedRegistryReference<ItemType> networkItemKey,
		final ValueContainer data, final TypedKeyMap context, final Object behaviour
	) {
		final Item.Properties properties = new Item.Properties()
				.setId(ResourceKey.create(Registries.ITEM, Converter.asVanilla(networkItemKey.location())));
		
		final ItemType networkItem = networkItemKey.get();
		((ItemPropertiesBridge) properties).spongetools$bridge$applyData(new AdditionalData(
				networkItem,
				context.require(ItemTypeKeys.TRANSLATION_KEY),
				componentPatch(networkItem, data)
				));
		
		context.get(ItemTypeKeys.CONTAINER).ifPresent(container ->
				properties.craftRemainder(Converter.asVanilla(container)));
		
		return properties;
	}
	
	public static <I extends Item> ItemTypeArchetype archetype(
		final Optional<ItemTypeArchetype> parent,
		final Class<I> baseClass,
		final Set<TypedKey<?>> requiredKeys,
		final BiConsumer<I, TypedKeyMap.Mutable> contextExtractor,
		final BiFunction<TypedKeyMap, Item.Properties, I> assembler
	) {
		return ItemTypeArchetype.of(parent, baseClass, requiredKeys, contextExtractor,
				(data, context, behaviour) -> assembler.apply(context, properties(NETWORK_ITEM, data, context, behaviour)));
	}
	
	public static <I extends Item> ItemTypeArchetype simpleArchetype(
		final Optional<ItemTypeArchetype> parent,
		final Class<I> baseClass,
		final Function<Item.Properties, I> assembler
	) {
		return archetype(parent, baseClass, Set.of(),
				(item, context) -> {},
				(context, properties) -> assembler.apply(properties));
	}
	
	public static final class Archetypes {
		
		public static final ItemTypeArchetype DEFAULT = archetype(
				Optional.empty(),
				Item.class,
				Set.of(ItemTypeKeys.TRANSLATION_KEY),
				(item, context) -> {
					final var remainder = item.getCraftingRemainder();
					if (!remainder.isEmpty()) {
						context.set(ItemTypeKeys.CONTAINER, Converter.asSponge(remainder.getItem()));
					}
					
					context.set(ItemTypeKeys.TRANSLATION_KEY, item.getDescriptionId());
				},
				(context, properties) -> new Item(properties));
		
		public static final ItemTypeArchetype BLOCK = archetype(
				Optional.of(DEFAULT),
				BlockItem.class,
				Set.of(ItemTypeKeys.BLOCK),
				(item, context) -> context.set(ItemTypeKeys.BLOCK, (BlockType) item.getBlock()),
				(context, properties) -> new BlockItem((Block) context.require(ItemTypeKeys.BLOCK), properties)
				);
		
		public static final ItemTypeArchetype FISHING_ROD = simpleArchetype(Optional.of(DEFAULT), FishingRodItem.class, FishingRodItem::new);
		
		public static final ItemTypeArchetype SCAFFOLDING = archetype(
				Optional.of(BLOCK),
				ScaffoldingBlockItem.class,
				Set.of(),
				(item, context) -> {},
				(context, properties) -> {
					final Block scaffolding = (Block) context.require(ItemTypeKeys.BLOCK);
					if (!(scaffolding instanceof ScaffoldingBlock)) {
						throw new IllegalStateException(
								"Scaffolding-like block must be provided for scaffolding-like item, but found " + scaffolding);
					}
					return new ScaffoldingBlockItem(scaffolding, properties);
				});
		
		private Archetypes() {
		}
	}
	
	public record AdditionalData(ItemType networkType, String translationKey, DataComponentPatch patch) {
	}
	
	private ItemTypeUtil() {
	}
}
