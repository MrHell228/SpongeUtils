package net.hellheim.spongetools.custom.type.item;

import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.RegistryType;

import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.util.TranslationUtil;

/**
 * {@link CustomTypeBuilder} for {@link ItemType}.
 */
public interface ItemTypeBuilder
		extends CustomTypeBuilder.WithData<ItemType, ItemStackLike, ItemArchetype, ItemTypeBuilder> {
	
	/**
	 * Creates the new {@link ItemTypeBuilder}.
	 * 
	 * @return The new builder
	 */
	static ItemTypeBuilder create() {
		return Sponge.game().builderProvider().provide(ItemTypeBuilder.class);
	}
	
	/**
	 * Returns the mandatory registry dependencies that must be used when registering custom type. <br>
	 * These registries include:
	 * <ul>
	 * <li> {@link ItemArchetype#registry()}
	 * <li> {@link LoreProvider#registry()}
	 * <li> {@link LoreProcessor#registry()}
	 * <li> {@link CustomItemAction#registry()}
	 * </ul>
	 * 
	 * @return The registry types
	 * @see #dependencies(RegistryType...)
	 */
	static RegistryType<?>[] dependencies() {
		return new RegistryType[] {
				ItemArchetype.registry(),
				LoreProvider.registry(),
				LoreProcessor.registry(),
				CustomItemAction.registry()
				};
	}
	
	/**
	 * Returns the registry dependencies from {@link #dependencies()}, and the additional given ones.
	 * 
	 * @param registryTypes The additional registry dependencies
	 * @return The registry types
	 */
	static RegistryType<?>[] dependencies(final RegistryType<?>... registryTypes) {
		return ArrayUtils.addAll(ItemTypeBuilder.dependencies(),  registryTypes);
	}
	
	/**
	 * Sets the {@link ItemTypeKeys#TRANSLATION_KEY} context, {@link Keys#ITEM_NAME} and {@link Keys#MODEL} data.
	 */
	default ItemTypeBuilder id(final ResourceKey id) {
		final var component = TranslationUtil.item(id);
		this.set(ItemTypeKeys.TRANSLATION_KEY, component.key());
		this.add(Keys.ITEM_NAME, component);
		this.add(Keys.MODEL, id);
		return this;
	}
	
	/**
	 * Sets the {@link ItemTypeKeys#CONTAINER} context key.
	 */
	default ItemTypeBuilder container(final Supplier<ItemType> item) {
		return this.container(item.get());
	}
	
	/**
	 * Sets the {@link ItemTypeKeys#CONTAINER} context key.
	 */
	default ItemTypeBuilder container(final ItemType item) {
		return this.set(ItemTypeKeys.CONTAINER, item);
	}
	
	/**
	 * Sets the {@link ItemTypeKeys#BLOCK} context key.
	 */
	default ItemTypeBuilder block(final Supplier<BlockType> block) {
		return this.block(block.get());
	}
	
	/**
	 * Sets the {@link ItemTypeKeys#BLOCK} context key.
	 */
	default ItemTypeBuilder block(final BlockType block) {
		return this.set(ItemTypeKeys.BLOCK, block);
	}
	
	// TODO projectile() method to support impl ProjectileItem
}
