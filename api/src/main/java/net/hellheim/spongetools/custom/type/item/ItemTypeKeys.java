package net.hellheim.spongetools.custom.type.item;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.object.TypedKey;

/**
 * Contains {@link TypedKey context keys} used for {@link ItemTypeBuilder} by builtin {@link ItemTypeArchetype}s.
 */
public final class ItemTypeKeys {
	
	/**
	 * @see ItemType#block()
	 */
	public static final TypedKey<BlockType> BLOCK = TypedKey.of(SpongeTools.key("block"), BlockType.class);
	
	/**
	 * @see ItemType#container()
	 */
	public static final TypedKey<ItemType> CONTAINER = TypedKey.of(SpongeTools.key("container"), ItemType.class);
	
	/**
	 * @see ItemType#asComponent()
	 */
	public static final TypedKey<String> TRANSLATION_KEY = TypedKey.of(SpongeTools.key("translation_key"), String.class);
	
	private ItemTypeKeys() {
	}
}
