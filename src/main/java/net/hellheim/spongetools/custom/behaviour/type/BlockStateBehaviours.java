package net.hellheim.spongetools.custom.behaviour.type;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.api.world.volume.game.Region;
import org.spongepowered.api.world.volume.game.UpdatableVolume;

import net.hellheim.spongetools.custom.behaviour.Behaviour.SimpleBoolean;
import net.hellheim.spongetools.custom.behaviour.Behaviour.SimpleObject;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.CloneItem;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.ExplosionHit;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Locatable;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.LocatableEntity;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Replace;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.ReplaceableByBlock;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.ReplaceableByFluid;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.ShapeUpdate;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SignalPower;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SignalUpdate;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Tick;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.UseWithItem;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.UseWithoutItem;

public final class BlockStateBehaviours {
	
	public static final BehaviourType<LocatableEntity<Boolean, PrimitiveGameVolume, EntityType<?>>> SPAWN_VALIDATOR = BehaviourType.create();
	
	public static final BehaviourType<Locatable<MapColorType, PrimitiveGameVolume>> MAP_COLOR = BehaviourType.create();
	
	public static final BehaviourType<Locatable<Boolean, PrimitiveGameVolume>> SIGNAL_CONDUCTOR = BehaviourType.create();
	
	/**
	 * Signal that will power neighbour blocks.
	 */
	public static final BehaviourType<SignalPower> WEAK_SIGNAL = BehaviourType.create();
	
	/**
	 * Signal that will go through neighbour blocks. <br>
	 * 
	 * In vanilla this behaviour usually filters result of
	 * {@link BlockStateExtension#get(BehaviourType)}
	 * for {@link #WEAK_SIGNAL} behaviour by side. <br>
	 * 
	 * Used by {@link BlockTypes#REPEATER} (horizontally) and other redstone-related blocks (upwards).
	 */
	public static final BehaviourType<SignalPower> STRONG_SIGNAL = BehaviourType.create();
	
	/**
	 * Result of this behaviour is usually used by {@link BlockTypes#COMPARATOR}.
	 */
	public static final BehaviourType<Locatable<Integer, World<?, ?>>> ANALOG_SIGNAL = BehaviourType.create();
	
	/**
	 * The strength of the block for destruction. <br>
	 * The bigger this value, the longer it will take for block to be destroyed. <br>
	 * Special value of -1 makes block unbreakable and not movable by piston.
	 */
	public static final BehaviourType<Locatable<Double, PrimitiveGameVolume>> DESTRUCTION_STRENGTH = BehaviourType.create();
	
	/**
	 * The increment of the destruction progress per tick. <br>
	 * Value greater than or equal to 1 makes block break instantly.
	 */
	public static final BehaviourType<LocatableEntity<Double, PrimitiveGameVolume, Player>> DESTRUCTION_INCREMENT = BehaviourType.create();
	
	/**
	 * Adding this behaviour doesn't make block ticking "naturally". <br>
	 * Ticks must be scheduled through {@link UpdatableVolume#scheduledBlockUpdates()}. <br>
	 * For example, ticks could be scheduled in {@link #PLACE}, {@link #SHAPE_UPDATE} or in {@link #BASE_TICK} itself.
	 */
	public static final BehaviourType<Tick> BASE_TICK = BehaviourType.create();
	
	/**
	 * Adding this behaviour doesn't make block randomly ticking "naturally". <br>
	 * 
	 * @see #HAS_RANDOM_TICK
	 */
	public static final BehaviourType<Tick> RANDOM_TICK = BehaviourType.create();
	
	/**
	 * Used to decide whether block should recieve natural random ticks.
	 */
	public static final BehaviourType<SimpleBoolean> HAS_RANDOM_TICK = BehaviourType.create();
	
	/**
	 * Called when {@link Entity}'s {@link AABB} collides with the {@link BlockState}.
	 */
	public static final BehaviourType<LocatableEntity<Void, World<?, ?>, Entity>> ENTITY_INSIDE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} enters the world.
	 */
	public static final BehaviourType<Replace> PLACE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} leaves the world.
	 */
	public static final BehaviourType<Replace> REMOVE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} is interacted with by the explosion.
	 */
	public static final BehaviourType<ExplosionHit> EXPLOSION = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed. <br>
	 * 
	 * This is usually used to update properties that could be
	 * considered as "shape" depending on neighbour blocks and
	 * by waterlogged blocks to schedule liquid ticks.
	 */
	public static final BehaviourType<ShapeUpdate> SHAPE_UPDATE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} is updated through
	 * {@link WorldExtension#updateAt(int, int, int, BlockType)} (mostly by signal-related blocks). <br>
	 * This is usually used to update properties that rely on redstone signal.
	 */
	public static final BehaviourType<SignalUpdate> SIGNAL_UPDATE = BehaviourType.create();
	
	public static final BehaviourType<UseWithItem> USE_WITH_ITEM = BehaviourType.create();
	
	public static final BehaviourType<UseWithoutItem> USE_WITHOUT_ITEM = BehaviourType.create();
	
	public static final BehaviourType<LocatableEntity<Void, World<?, ?>, Player>> ATTACK = BehaviourType.create();
	
	/**
	 * Used by {@link BlockTypes#PISTON} and {@link BlockTypes#STICKY_PISTON}.
	 */
	public static final BehaviourType<SimpleObject<PushReaction>> PUSH_REACTION = BehaviourType.create();
	
	/**
	 * Used by {@link BlockTypes#NOTE_BLOCK}.
	 */
	public static final BehaviourType<SimpleObject<InstrumentType>> INSTRUMENT = BehaviourType.create();
	
	public static final BehaviourType<SimpleBoolean> REQUIRE_TOOL = BehaviourType.create();
	
	public static final BehaviourType<SimpleBoolean> REPLACEABLE = BehaviourType.create();
	
	public static final BehaviourType<ReplaceableByFluid> REPLACEABLE_BY_FLUID = BehaviourType.create();
	
	public static final BehaviourType<ReplaceableByBlock> REPLACEABLE_BY_BLOCK = BehaviourType.create();
	
	/**
	 * Used to decide whether entities will suffocate in the block.
	 */
	public static final BehaviourType<Locatable<Boolean, PrimitiveGameVolume>> SUFFOCATION = BehaviourType.create();
	
	/**
	 * If this behaviour returns true, {@link #SHAPE_UPDATE} would be called
	 * for this block with each neighbour around it when generated in the world.
	 */
	public static final BehaviourType<Locatable<Boolean, PrimitiveGameVolume>> POST_PROCESSING = BehaviourType.create();
	
	public static final BehaviourType<Locatable<Boolean, Region<?>>> CAN_SURVIVE = BehaviourType.create();
	
	/**
	 * The item representation of the block. <br>
	 * Usually it's asked when player uses the middle mouse button. <br>
	 * If block doesn't have an item representation, this behaviour would return {@link ItemStack#empty()}.
	 */
	public static final BehaviourType<CloneItem> CLONE_ITEM = BehaviourType.create();
	
	private BlockStateBehaviours() {
	}
}
