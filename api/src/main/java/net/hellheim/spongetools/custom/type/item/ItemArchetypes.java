package net.hellheim.spongetools.custom.type.item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryScope;
import org.spongepowered.api.registry.RegistryScopes;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.block.BlockArchetypes;

@RegistryScopes(scopes = RegistryScope.GAME)
public final class ItemArchetypes {
	
	/**
	 * Item that can be placed into the world. <br>
	 * Parent Archetype: {@link #DEFAULT}. <br>
	 * <br>
	 * Required Context: <br>
	 * - {@link ItemTypeKeys#BLOCK} <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<ItemTypeArchetype> BLOCK = ItemArchetypes.key("block");
	
	/**
	 * Regular item without any specific behaviour. <br>
	 * <br>
	 * Required Context: <br>
	 * - {@link ItemTypeKeys#TRANSLATION_KEY} <br>
	 * <br>
	 * Supported Context: <br>
	 * - {@link ItemTypeKeys#CONTAINER} <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<ItemTypeArchetype> DEFAULT = ItemArchetypes.key("item");
	
	/**
	 * Item that can cast a bobber. <br>
	 * Parent Archetype: {@link #DEFAULT}. <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<ItemTypeArchetype> FISHING_ROD = ItemArchetypes.key("fishing_rod");
	
	/**
	 * Block item that properly handles {@link BlockArchetypes#SCAFFOLDING scaffolding-like} block placement. <br>
	 * <br>
	 * Parent Archetype: {@link #BLOCK}. <br>
	 */
	public static final DefaultedRegistryReference<ItemTypeArchetype> SCAFFOLDING = ItemArchetypes.key("scaffolding");
	
	private ItemArchetypes() {
	}

    public static Registry<ItemTypeArchetype> registry() {
    	return ItemTypeArchetype.registry().get();
    }

    private static DefaultedRegistryReference<ItemTypeArchetype> key(final String key) {
        return RegistryKey.of(ItemTypeArchetype.registry(), SpongeTools.key(key)).asDefaultedReference(Sponge::game);
    }
}
