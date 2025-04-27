package net.hellheim.spongetools.custom.behaviour.type;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.volume.game.UpdatableVolume;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.block.state.AnalogSignalPowerBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.BlockStateExtension;
import net.hellheim.spongetools.custom.behaviour.block.state.MapColorBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.PushReactionBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.ReplaceBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.ShapeUpdateBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.SignalConductorBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.SignalPowerBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.SignalUpdateBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.SpawnValidatorBlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.block.state.TickBlockStateBehaviour;

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
	 * Called when {@link BlockState} enters the world.
	 */
	public static final BehaviourType<ReplaceBlockStateBehaviour> PLACE = BehaviourType.create();
	
	/**
	 * Called when {@link BlockState} leaves the world.
	 */
	public static final BehaviourType<ReplaceBlockStateBehaviour> REMOVE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed. <br>
	 * This is usually used to update properties that could be
	 * considered as "shape" depending on neighbour blocks and
	 * by waterlogged blocks to schedule liquid ticks.
	 * 
	 * @see #SIGNAL_UPDATE
	 */
	public static final BehaviourType<ShapeUpdateBlockStateBehaviour> SHAPE_UPDATE = BehaviourType.create();
	
	/**
	 * Called when neighbour {@link BlockState}s are changed (mostly due to signal changes). <br>
	 * This is usually used to update properties that rely on redstone signal
	 * 
	 * @see #SHAPE_UPDATE
	 */
	public static final BehaviourType<SignalUpdateBlockStateBehaviour> SIGNAL_UPDATE = BehaviourType.create();
	
	private BlockStateBehaviours() {
	}
}
