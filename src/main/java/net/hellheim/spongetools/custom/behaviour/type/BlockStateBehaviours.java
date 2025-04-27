package net.hellheim.spongetools.custom.behaviour.type;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.volume.game.UpdatableVolume;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.*;

public final class BlockStateBehaviours {
	
	public static final BehaviourType<SpawnValidator> SPAWN_VALIDATOR = BehaviourType.create();
	
	public static final BehaviourType<MapColor> MAP_COLOR = BehaviourType.create();
	
	/**
	 * Used by {@link BlockTypes#PISTON} and {@link BlockTypes#STICKY_PISTON}.
	 */
	public static final BehaviourType<PistonPushReaction> PUSH_REACTION = BehaviourType.create();
	
	public static final BehaviourType<SignalConductor> SIGNAL_CONDUCTOR = BehaviourType.create();
	
	/**
	 * Signal that will power neighbour blocks.
	 */
	public static final BehaviourType<SignalPower> SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Signal that will go through neighbour blocks. <br>
	 * 
	 * In vanilla this behaviour usually filters result of
	 * {@link BlockStateExtension#origin(BlockBehaviourType)}
	 * for {@link #SIGNAL_POWER} behaviour by side. <br>
	 * 
	 * Used by {@link BlockTypes#REPEATER} (horizontally) and other redstone-related blocks (upwards).
	 */
	public static final BehaviourType<SignalPower> DIRECT_SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Result of this behaviour is usually used by {@link BlockTypes#COMPARATOR}.
	 */
	public static final BehaviourType<AnalogSignalPower> ANALOG_SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Adding this behaviour doesn't make block ticking "naturally". <br>
	 * Ticks must be scheduled through {@link UpdatableVolume#scheduledBlockUpdates()}. <br>
	 * For example, ticks could be scheduled in {@link #PLACE}, {@link #SHAPE_UPDATE} or in {@link #TICK} itself.
	 */
	public static final BehaviourType<Tick> TICK = BehaviourType.create();
	
	public static final BehaviourType<Tick> RANDOM_TICK = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} enters the world.
	 */
	public static final BehaviourType<Replace> PLACE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} leaves the world.
	 */
	public static final BehaviourType<Replace> REMOVE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed. <br>
	 * This is usually used to update properties that could be
	 * considered as "shape" depending on neighbour blocks and
	 * by waterlogged blocks to schedule liquid ticks.
	 * 
	 * @see #SIGNAL_UPDATE
	 */
	public static final BehaviourType<ShapeUpdate> SHAPE_UPDATE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed (mostly due to signal changes). <br>
	 * This is usually used to update properties that rely on redstone signal
	 * 
	 * @see #SHAPE_UPDATE
	 */
	public static final BehaviourType<SignalUpdate> SIGNAL_UPDATE = BehaviourType.create();
	
	private BlockStateBehaviours() {
	}
}
