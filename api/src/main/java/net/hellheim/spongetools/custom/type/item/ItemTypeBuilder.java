package net.hellheim.spongetools.custom.type.item;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.util.Builder;
import org.spongepowered.api.util.CopyableBuilder;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.util.TranslationUtil;

/**
 * Builder for custom {@link ItemType}. It's represented by some specific concepts: <br>
 * - {@link ItemArchetype} - the core of the type; <br> 
 * - Data (applied through {@link Key}s) - applied to {@link ItemStackLike}s created from the built type; <br>
 * - Context (applied through {@link TypedKey}s) - used to construct the type itself; <br>
 * - Behaviour (applied through {@link BehaviourType}s) - TODO <br>
 * <br>
 * <b>Note:</b> No data involving server-scoped registries should be used while building the type. <br>
 * It should not be an issue as most of mentioned data is usually applied per-stack (e.g. enchantments). <br>
 * <b>Note:</b> All registries that the built type depends on must be passed to {@link RegisterRegistryValueEvent}. <br>
 * Mandatory dependencies can be retrieved via {@link #dependencies()} or {@link #dependencies(RegistryType...)}.
 */
public interface ItemTypeBuilder extends
		Builder<ItemType, ItemTypeBuilder>,
		CopyableBuilder<ItemType, ItemTypeBuilder>,
		DataOperator<ItemTypeBuilder>,
		TypedKeyMap.Operator<ItemTypeBuilder> {
	
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
	 * </ul>
	 * 
	 * @return The registry types
	 * @see #dependencies(RegistryType...)
	 */
	static RegistryType<?>[] dependencies() {
		return new RegistryType[] {
				ItemArchetype.registry(),
				LoreProvider.registry(),
				LoreProcessor.registry()
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
		this.set(ItemTypeKeys.CONTAINER, item);
		return this;
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
		this.set(ItemTypeKeys.BLOCK, block);
		return this;
	}
	
	/**
	 * Sets the {@link ItemArchetype} of the {@link ItemType}. <br>
	 * Defaults to {@link ItemArchetypes#PLAIN}.
	 * 
	 * @param archetype The item archetype
	 * @return This builder, for chaining
	 */
	default ItemTypeBuilder archetype(final Supplier<? extends ItemArchetype> archetype) {
		return this.archetype(Objects.requireNonNull(archetype, "archetype").get());
	}
	
	/**
	 * Sets the {@link ItemArchetype} of the {@link ItemType}. <br>
	 * Defaults to {@link ItemArchetypes#PLAIN}.
	 * 
	 * @param archetype The item archetype
	 * @return This builder, for chaining
	 */
	ItemTypeBuilder archetype(ItemArchetype archetype);
	
	// TODO projectile() method to support impl ProjectileItem
	
	@Override
	ItemTypeBuilder reset();
}
