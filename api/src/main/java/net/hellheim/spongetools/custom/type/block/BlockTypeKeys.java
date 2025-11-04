package net.hellheim.spongetools.custom.type.block;

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.state.StateProperty;

import io.leangen.geantyref.TypeToken;
import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;

public final class BlockTypeKeys {
	
	/**
	 * The properties of default {@link BlockState}.
	 * 
	 * @see BlockType#defaultState()
	 */
	public static final TypedKey<List<StatePropertyValue<?>>> DEFAULT_STATE = TypedKey.of(SpongeTools.key("default_state"), new TypeToken<List<StatePropertyValue<?>>>() {});
	
	/**
	 * Defines how the client will see the {@link BlockState}s of the block.
	 */
	public static final TypedKey<UnaryOperator<BlockState>> DISPLAY_STATE = TypedKey.of(SpongeTools.key("display_state"), new TypeToken<UnaryOperator<BlockState>>() {});
	
	/**
	 * Block drops.
	 */
	public static final TypedKey<ResourceKey> LOOT_TABLE = TypedKey.of(SpongeTools.key("loot_table"), ResourceKey.class);
	
	/**
	 * The set of used properties to populate valid {@link BlockState}s.
	 * 
	 * @see BlockType#validStates()
	 */
	public static final TypedKey<Set<StateProperty<?>>> STATE_PROPERTIES = TypedKey.of(SpongeTools.key("state_properties"), new TypeToken<Set<StateProperty<?>>>() {});
	
	/**
	 * @see BlockType#asComponent()
	 */
	public static final TypedKey<String> TRANSLATION_KEY = TypedKey.of(SpongeTools.key("translation_key"), String.class);
	
	private BlockTypeKeys() {
	}
}
