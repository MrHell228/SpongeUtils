package net.hellheim.spongetools.custom.behaviour.type;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.volume.game.UpdatableVolume;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.AnalogSignalPower;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Attack;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.MapColor;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.PistonPushReaction;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Replace;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.ShapeUpdate;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SignalConductor;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SignalPower;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SignalUpdate;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.SpawnValidator;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Tick;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.UseWithItem;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.UseWithoutItem;

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
	public static final BehaviourType<SignalPower> WEAK_SIGNAL_POWER = BehaviourType.create();
	
	/**
	 * Signal that will go through neighbour blocks. <br>
	 * 
	 * In vanilla this behaviour usually filters result of
	 * {@link BlockStateExtension#get(BehaviourType)}
	 * for {@link #WEAK_SIGNAL_POWER} behaviour by side. <br>
	 * 
	 * Used by {@link BlockTypes#REPEATER} (horizontally) and other redstone-related blocks (upwards).
	 */
	public static final BehaviourType<SignalPower> STRONG_SIGNAL_POWER = BehaviourType.create();
	
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
	
	public static final BehaviourType<Attack> ATTACK = BehaviourType.create();
	
	private BlockStateBehaviours() {
	}
}
