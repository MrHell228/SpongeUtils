package net.hellheim.spongetools.custom.type.block;

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.data.type.ToolRule;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.state.StateProperty;

import io.leangen.geantyref.TypeToken;
import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;

public final class BlockTypeKeys {
	
	/**
	 * @see Keys#BURNABLE
	 */
	public static final TypedKey<Boolean> BURNABLE = TypedKey.of(SpongeTools.key("burnable"), Boolean.class);
	
	/**
	 * The properties of default {@link BlockState}.
	 * 
	 * @see BlockType#defaultState()
	 */
	public static final TypedKey<List<StatePropertyValue<?>>> DEFAULT_STATE = TypedKey.of(SpongeTools.key("default_state"), new TypeToken<List<StatePropertyValue<?>>>() {});
	
	/**
	 * The strength of the block for destruction. <br>
	 * The bigger this value, the longer it will take for block to be destroyed. <br>
	 * Special value of -1 makes block unbreakable and not movable by piston.
	 */
	public static final TypedKey<Double> DESTRUCTION_RESISTANCE = TypedKey.of(SpongeTools.key("destruction_resistance"), Double.class);
	
	/**
	 * Defines how the client will see the {@link BlockState}s of the block.
	 */
	public static final TypedKey<UnaryOperator<BlockState>> DISPLAY_STATE = TypedKey.of(SpongeTools.key("display_state"), new TypeToken<UnaryOperator<BlockState>>() {});
	
	/**
	 * The strength of the block for explosion. <br>
	 * The bigger this value, the less likely the block will be affected by explosions.
	 */
	public static final TypedKey<Double> EXPLOSION_RESISTANCE = TypedKey.of(SpongeTools.key("explosion_resistance"), Double.class);
	
	/**
	 * Affects the slipperiness of the block. <br>
	 * The closer value to 1, the more slippery the block is. <br>
	 * Defautls to 0.6.
	 */
	public static final TypedKey<Double> FRICTION_FACTOR = TypedKey.of(SpongeTools.key("friction_factor"), Double.class);
	
	/**
	 * Defines the {@link InstrumentType} to use for {@link BlockTypes#NOTE_BLOCK}.
	 */
	public static final TypedKey<InstrumentType> INSTRUMENT = TypedKey.of(SpongeTools.key("instrument"), InstrumentType.class);
	
	/**
	 * Affects the jump strength on the block. <br>
	 * The closer value to 0, the lower the jump is. <bt>
	 * Defaults to 1.
	 */
	public static final TypedKey<Double> JUMP_FACTOR = TypedKey.of(SpongeTools.key("jump_factor"), Double.class);
	
	/**
	 * Block drops.
	 */
	public static final TypedKey<ResourceKey> LOOT_TABLE = TypedKey.of(SpongeTools.key("loot_table"), ResourceKey.class);
	
	/**
	 * Defines the color the block is represented by on a map.
	 */
	public static final TypedKey<MapColorType> MAP_COLOR = TypedKey.of(SpongeTools.key("map_color"), MapColorType.class);
	
	/**
	 * Defines how the block reactors to piston push.
	 */
	public static final TypedKey<PushReaction> PUSH_REACTION = TypedKey.of(SpongeTools.key("push_reaction"), PushReaction.class);
	
	/**
	 * Defines whether the block requires correct {@link ToolRule} for efficient breaking and loot drops.
	 */
	public static final TypedKey<Boolean> REQUIRE_TOOL = TypedKey.of(SpongeTools.key("require_tool"), Boolean.class);
	
	/**
	 * @see BlockType#soundGroup()
	 */
	public static final TypedKey<BlockSoundGroup> SOUND_GROUP = TypedKey.of(SpongeTools.key("sound_group"), BlockSoundGroup.class);
	
	/**
	 * Affects movement speed on the block. <br>
	 * The closer value to 0, the lower the speed is. <br>
	 * Defaults to 1.
	 */
	public static final TypedKey<Double> SPEED_FACTOR = TypedKey.of(SpongeTools.key("speed_factor"), Double.class);
	
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
