package net.hellheim.spongetools.custom.type.block;

import java.util.List;
import java.util.Set;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.type.ToolRule;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.map.color.MapColorType;
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
	 * The set of used properties to populate valid {@link BlockState}s.
	 * 
	 * @see BlockType#validStates()
	 */
	public static final TypedKey<Set<StateProperty<?>>> STATE_PROPERTIES = TypedKey.of(SpongeTools.key("state_properties"), new TypeToken<Set<StateProperty<?>>>() {});
	
	/**
	 * @see BlockType#asComponent()
	 */
	public static final TypedKey<String> TRANSLATION_KEY = TypedKey.of(SpongeTools.key("translation_key"), String.class);
	
	/**
	 * Affects the slipperiness of the block. <br>
	 * The closer value to 1, the more slippery the block is. <br>
	 * Defautls to 0.6.
	 */
	public static final Key<Value<Double>> FRICTION_FACTOR = SpongeTools.Keys.FRICTION_FACTOR;
	
	/**
	 * TODO doc
	 */
	public static final Key<Value<Boolean>> HAS_COLLISION = SpongeTools.Keys.HAS_COLLISION;
	
	/**
	 * TODO doc
	 */
	public static final Key<Value<Boolean>> HAS_DYNAMIC_SHAPE = SpongeTools.Keys.HAS_DYNAMIC_SHAPE;
	
	/**
	 * TODO doc
	 */
	public static final Key<Value<Boolean>> HAS_OCCLUSION = SpongeTools.Keys.HAS_OCCLUSION;
	
	/**
	 * Affects the jump strength on the block. <br>
	 * The closer value to 0, the lower the jump is. <bt>
	 * Defaults to 1.
	 */
	public static final Key<Value<Double>> JUMP_FACTOR = SpongeTools.Keys.JUMP_FACTOR;
	
	/**
	 * Block drops.
	 */
	public static final Key<Value<ResourceKey>> LOOT_TABLE = SpongeTools.Keys.LOOT_TABLE_KEY;
	
	/**
	 * Defines the color the block is represented by on a map.
	 */
	public static final Key<Value<MapColorType>> MAP_COLOR = SpongeTools.Keys.MAP_COLOR_TYPE;
	
	/**
	 * Defines whether the block requires correct {@link ToolRule} for efficient breaking and loot drops.
	 */
	public static final Key<Value<Boolean>> REQUIRE_TOOL = SpongeTools.Keys.REQUIRE_TOOL;
	
	/**
	 * @see BlockType#soundGroup()
	 */
	public static final Key<Value<BlockSoundGroup>> SOUND_GROUP = SpongeTools.Keys.BLOCK_SOUND_GROUP;
	
	/**
	 * Affects movement speed on the block. <br>
	 * The closer value to 0, the lower the speed is. <br>
	 * Defaults to 1.
	 */
	public static final Key<Value<Double>> SPEED_FACTOR = SpongeTools.Keys.SPEED_FACTOR;
	
	private BlockTypeKeys() {
	}
}
