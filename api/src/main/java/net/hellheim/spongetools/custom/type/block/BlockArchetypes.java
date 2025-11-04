package net.hellheim.spongetools.custom.type.block;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryKey;

import net.hellheim.spongetools.SpongeTools;

public final class BlockArchetypes {
	
	/**
	 * Regular block. <br>
	 * <br>
	 * Required Context: <br>
	 * - {@link BlockTypeKeys#DISPLAY_STATE} <br>
	 * - {@link BlockTypeKeys#TRANSLATION_KEY} <br>
	 * <br>
	 * Supported Context: <br>
	 * - {@link BlockTypeKeys#DEFAULT_STATE} <br>
	 * - {@link BlockTypeKeys#LOOT_TABLE} <br>
	 * - {@link BlockTypeKeys#STATE_PROPERTIES} <br>
	 */
	public static final DefaultedRegistryReference<BlockArchetype> BLOCK = BlockArchetypes.key("block");
	
	/**
	 * Block that behaves like {@link BlockTypes#SCAFFOLDING}. <br>
	 * Parent Archetype: {@link #BLOCK}. <br>
	 * <br>
	 * Required Context: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<BlockArchetype> SCAFFOLDING = BlockArchetypes.key("scaffolding");
	
	private BlockArchetypes() {
	}
	
	public static Registry<BlockArchetype> registry() {
    	return BlockArchetype.registry().get();
    }

    private static DefaultedRegistryReference<BlockArchetype> key(final String key) {
        return RegistryKey.of(BlockArchetype.registry(), SpongeTools.key(key)).asDefaultedReference(Sponge::game);
    }
}
