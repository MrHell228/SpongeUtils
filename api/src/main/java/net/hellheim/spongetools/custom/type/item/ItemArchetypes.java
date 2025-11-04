package net.hellheim.spongetools.custom.type.item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryScope;
import org.spongepowered.api.registry.RegistryScopes;

import net.hellheim.spongetools.SpongeTools;

@RegistryScopes(scopes = RegistryScope.GAME)
public final class ItemArchetypes {
	
	/**
	 * Item that can be placed into the world. <br>
	 * Parent Archetype: {@link #ITEM}. <br>
	 * <br>
	 * Required Context: <br>
	 * - {@link ItemTypeKeys#BLOCK} <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<ItemArchetype> BLOCK = ItemArchetypes.key("block");
	
	/**
	 * Item that can cast a bobber. <br>
	 * Parent Archetype: {@link #ITEM}. <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<ItemArchetype> FISHING_ROD = ItemArchetypes.key("fishing_rod");
	
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
	public static final DefaultedRegistryReference<ItemArchetype> ITEM = ItemArchetypes.key("item");
	
	private ItemArchetypes() {
	}

    public static Registry<ItemArchetype> registry() {
    	return ItemArchetype.registry().get();
    }

    private static DefaultedRegistryReference<ItemArchetype> key(final String key) {
        return RegistryKey.of(ItemArchetype.registry(), SpongeTools.key(key)).asDefaultedReference(Sponge::game);
    }
}
