package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.volume.game.UpdatableVolume;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;

public final class BlockStateBehaviours {
	
	public static final BehaviourType<SpawnValidatorBlockStateBehaviour> SPAWN_VALIDATOR = BehaviourType.create();
	
	public static final BehaviourType<MapColorBlockStateBehaviour> MAP_COLOR = BehaviourType.create();
	
	/**
	 * Used by {@link BlockTypes#PISTON} and {@link BlockTypes#STICKY_PISTON}.
	 */
	public static final BehaviourType<PushReactionBlockStateBehaviour> PUSH_REACTION = BehaviourType.create();
	
	public static final BehaviourType<SignalConductorBlockStateBehaviour> SIGNAL_CONDUCTOR = BehaviourType.create();
	
	/**
	 * Signal that will power neighbour blocks.
	 */
	public static final BehaviourType<SignalPowerBlockStateBehaviour> SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Signal that will go through neighbour blocks. <br>
	 * 
	 * In vanilla this behaviour usually filters result of
	 * {@link BlockStateExtension#origin(BlockBehaviourType)}
	 * for {@link #SIGNAL_POWER} behaviour by side. <br>
	 * 
	 * Used by {@link BlockTypes#REPEATER} (horizontally) and other redstone-related blocks (upwards).
	 */
	public static final BehaviourType<SignalPowerBlockStateBehaviour> DIRECT_SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Result of this behaviour is usually used by {@link BlockTypes#COMPARATOR}.
	 */
	public static final BehaviourType<AnalogSignalPowerBlockStateBehaviour> ANALOG_SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Adding this behaviour doesn't make block ticking "naturally". <br>
	 * Ticks must be scheduled through {@link UpdatableVolume#scheduledBlockUpdates()}. <br>
	 * For example, ticks could be scheduled in {@link #PLACE}, {@link #SHAPE_UPDATE} or in {@link #TICK} itself.
	 */
	public static final BehaviourType<TickBlockStateBehaviour> TICK = BehaviourType.create();
	
	public static final BehaviourType<TickBlockStateBehaviour> RANDOM_TICK = BehaviourType.create();
	
	/**
	 * Called when block enters the world.
	 */
	public static final BehaviourType<ReplaceBlockStateBehaviour> PLACE = BehaviourType.create();
	
	/**
	 * Called when block leaves the world.
	 */
	public static final BehaviourType<ReplaceBlockStateBehaviour> REMOVE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed. <br>
	 * This is usually used to update properties that could
	 * considered as "shape" depending on neighbour blocks
	 * and by waterlogged blocks to schedule liquid ticks.
	 * 
	 * @see #NEIGHBOUR_UPDATE
	 */
	public static final BehaviourType<ShapeUpdateBlockStateBehaviour> SHAPE_UPDATE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed. <br>
	 * This is usually used to update properties that rely on redstone signal
	 * 
	 * @see #SHAPE_UPDATE
	 */
	public static final BehaviourType<NeighbourUpdateBlockStateBehaviour> NEIGHBOUR_UPDATE = BehaviourType.create();
	
	private BlockStateBehaviours() {
	}
}
