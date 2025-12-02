package net.hellheim.spongetools.common.event.listener;

import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;

import net.hellheim.spongetools.bridge.BlockStateBaseBridge;
import net.hellheim.spongetools.common.behaviour.BehaviourManagerImpl;
import net.hellheim.spongetools.common.event.RegisterBehaviourDataEventImpl;
import net.hellheim.spongetools.common.event.RegisterBlockStateBehaviourEventImpl;
import net.hellheim.spongetools.common.util.BucketUtil;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviours;
import net.hellheim.spongetools.event.RegisterBehaviourDataEvent;
import net.hellheim.spongetools.mixin.world.level.block.state.BlockBehaviourAccessor;
import net.hellheim.spongetools.mixin.world.level.block.state.BlockBehaviour_PropertiesAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class BehaviourEventListener {
	
	@Listener
	public void fireBehaviourEvents(final StartingEngineEvent<Server> e) {
		final var manager = (BehaviourManagerImpl) BehaviourManager.get();
		Sponge.eventManager().post(new RegisterBehaviourDataEventImpl(e.cause(), e.game(), manager));
		
		final var event = new RegisterBlockStateBehaviourEventImpl(e.cause(), e.game(), manager);
		Sponge.eventManager().post(event);
		event.steps.forEach((state, step) -> {
			if (!step.callbacks.callbacks().isEmpty()) {
				((BlockStateBaseBridge) state).spongetools$bridge$set(step.callbacks);
			}
		});
	}
	
	@Listener
	public void registerBehaviourData(final RegisterBehaviourDataEvent event) {
		this.blockStateData(event);
		this.blockTypeData(event);
	}
	
	private void blockStateData(final RegisterBehaviourDataEvent event) {
		event.group(BlockState.class)
				.register(BlockStateBehaviours.SPAWN_VALIDATOR,
						(state) -> (volume, position, entity) -> state.isValidSpawn(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(entity)
								))
				.register(BlockStateBehaviours.MAP_COLOR,
						(state) -> (volume, position) -> Converter.asSponge(state.getMapColor(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								)))
				.register(BlockStateBehaviours.SIGNAL_CONDUCTOR,
						(state) -> (volume, position) -> state.isRedstoneConductor(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.DIRECT_SIGNAL,
						(state) -> (volume, position, direction) -> state.getSignal(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(direction).getOpposite()
								))
				.register(BlockStateBehaviours.INDIRECT_SIGNAL,
						(state) -> (volume, position, direction) -> state.getDirectSignal(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(direction).getOpposite()
								))
				.register(BlockStateBehaviours.ANALOG_SIGNAL,
						(state) -> (volume, position) -> state.getAnalogOutputSignal(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.DESTRUCTION_RESISTANCE,
						(state) -> (volume, position) -> (double) state.getDestroySpeed(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.DESTRUCTION_INCREMENT,
						(state) -> (volume, position, entity) -> (double) state.getDestroyProgress(
								Converter.asVanilla(entity),
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.BASE_TICK,
						(state) -> (volume, position, random) -> state.tick(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(random)
								))
				.register(BlockStateBehaviours.RANDOM_TICK,
						(state) -> (volume, position, random) -> state.randomTick(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(random)
								))
				.register(BlockStateBehaviours.HAS_RANDOM_TICK,
						(state) -> () -> state.isRandomlyTicking())
				.register(BlockStateBehaviours.ENTITY_INSIDE,
						(state) -> (volume, position, entity) -> {
							state.entityInside(
									Converter.asVanilla(volume),
									Converter.asVanilla(position),
									Converter.asVanilla(entity)
									);
							return null;
						})
				.register(BlockStateBehaviours.PLACE,
						(state) -> (volume, position, otherState, movedByPiston) -> state.onPlace(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(otherState),
								movedByPiston
								))
				.register(BlockStateBehaviours.REMOVE,
						(state) -> (volume, position, otherState, movedByPiston) -> state.onRemove(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(otherState),
								movedByPiston
								))
				.register(BlockStateBehaviours.EXPLOSION,
						(state) -> (volume, position, explosion, drop) -> state.onExplosionHit(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(explosion),
								(item, pos) -> drop.accept(Converter.asSponge(item), Converter.asSponge(pos))
								))
				.register(BlockStateBehaviours.SHAPE_UPDATE,
						(state) -> (region, volume, position, direction, nPos, nState, random) -> Converter.asSponge(state.updateShape(
								Converter.asVanilla(region),
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								Converter.asVanilla(direction).getOpposite(),
								Converter.asVanilla(nPos),
								Converter.asVanilla(nState),
								Converter.asVanilla(random)
								)))
				.register(BlockStateBehaviours.SIGNAL_UPDATE,
						(state) -> (volume, pos, notifier, orientation, movedByPiston) -> state.handleNeighborChanged(
								Converter.asVanilla(volume),
								Converter.asVanilla(pos),
								Converter.asVanilla(notifier),
								Converter.asVanilla(orientation.orElse(null)),
								movedByPiston
								))
				.register(BlockStateBehaviours.USE_WITH_ITEM,
						(state) -> (volume, entity, hit, hand, item) -> Converter.asSponge(state.useItemOn(
								Converter.asVanilla(item.asMutable()),
								Converter.asVanilla(volume),
								Converter.asVanilla(entity),
								Converter.asVanilla(hand),
								Converter.asVanilla(hit)
								)))
				.register(BlockStateBehaviours.USE_WITHOUT_ITEM,
						(state) -> (volume, entity, hit) -> Converter.asSponge(state.useWithoutItem(
								Converter.asVanilla(volume),
								Converter.asVanilla(entity),
								Converter.asVanilla(hit)
								)))
				.register(BlockStateBehaviours.ATTACK,
						(state) -> (volume, position, entity) -> {
							state.attack(
									Converter.asVanilla(volume),
									Converter.asVanilla(position),
									Converter.asVanilla(entity)
									);
							return null;
						})
				.register(BlockStateBehaviours.PUSH_REACTION,
						(state) -> () -> Converter.asSponge(state.getPistonPushReaction()))
				.register(BlockStateBehaviours.INSTRUMENT,
						(state) -> () -> Converter.asSponge(state.instrument()))
				.register(BlockStateBehaviours.FLUID,
						(state) -> () -> Converter.asSponge(state.getFluidState()))
				.register(BlockStateBehaviours.REQUIRE_TOOL,
						(state) -> () -> state.requiresCorrectToolForDrops())
				.register(BlockStateBehaviours.REPLACEABLE,
						(state) -> () -> state.canBeReplaced())
				.register(BlockStateBehaviours.REPLACEABLE_BY_FLUID,
						(state) -> (fluid) -> state.canBeReplaced(
								Converter.asVanilla(fluid)
								))
				.register(BlockStateBehaviours.REPLACEABLE_BY_BLOCK,
						(state) -> (context) -> state.canBeReplaced(
								Converter.asVanilla(context)
								))
				.register(BlockStateBehaviours.SUFFOCATION,
						(state) -> (volume, position) -> state.isSuffocating(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.CAN_SURVIVE,
						(state) -> (volume, position) -> state.canSurvive(
								Converter.asVanilla(volume),
								Converter.asVanilla(position)
								))
				.register(BlockStateBehaviours.CLONE_ITEM,
						(state) -> (volume, position, data) -> Converter.asSponge(state.getCloneItemStack(
								Converter.asVanilla(volume),
								Converter.asVanilla(position),
								data
								)))
				.register(BlockStateBehaviours.BUCKET_PICKUP_ITEM,
						(state) -> (volume, position, entity) -> Converter.asSponge(BucketUtil.bucketPickup(state.getBlock()).pickupBlock(
										Converter.asVanilla(entity.orElse(null)),
										Converter.asVanilla(volume),
										Converter.asVanilla(position),
										state
										)))
				.register(BlockStateBehaviours.BUCKET_PICKUP_SOUND,
						(state) -> () -> BucketUtil.bucketPickup(state.getBlock()).getPickupSound().map(Converter::asSponge))
								;
	}
	
	private void blockTypeData(final RegisterBehaviourDataEvent event) {
		event.group(Block.class).callbacks(BlockState.class)
				.register(BlockStateBehaviours.SPAWN_VALIDATOR, 
						(block) -> (state, origin, args) -> properties(block).accessor$isValidSpawn().test(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.entity())
								))
				// Neo adds extension
				.register(BlockStateBehaviours.MAP_COLOR,
						(block) -> (state, origin, args) -> Converter.asSponge(properties(block).accessor$mapColor().apply(
								state
								)))
				.register(BlockStateBehaviours.SIGNAL_CONDUCTOR,
						(block) -> (state, origin, args) -> properties(block).accessor$isRedstoneConductor().test(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position())
								))
				.register(BlockStateBehaviours.DIRECT_SIGNAL,
						(block) -> (state, origin, args) -> group(block).invoker$getSignal(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.direction().opposite())
								))
				.register(BlockStateBehaviours.INDIRECT_SIGNAL,
						(block) -> (state, origin, args) -> group(block).invoker$getDirectSignal(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.direction().opposite())
								))
				.register(BlockStateBehaviours.ANALOG_SIGNAL,
						(block) -> (state, origin, args) -> group(block).invoker$getAnalogOutputSignal(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position())
								))
				.registerResult(BlockStateBehaviours.DESTRUCTION_RESISTANCE,
						(block) -> (double) block.defaultDestroyTime())
				.register(BlockStateBehaviours.DESTRUCTION_INCREMENT,
						(block) -> (state, origin, args) -> (double) group(block).invoker$getDestroyProgress(
								state,
								Converter.asVanilla(args.entity()),
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position())
								))
				.registerAction(BlockStateBehaviours.BASE_TICK,
						(block) -> (state, origin, args) -> group(block).invoker$tick(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.random())
								))
				.registerAction(BlockStateBehaviours.RANDOM_TICK,
						(block) -> (state, origin, args) -> group(block).invoker$randomTick(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.random())
								))
				.register(BlockStateBehaviours.HAS_RANDOM_TICK,
						(block) -> (state, origin, args) -> group(block).invoker$isRandomlyTicking(state))
				.registerAction(BlockStateBehaviours.ENTITY_INSIDE,
						(block) -> (state, origin, args) -> group(block).invoker$entityInside(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.entity())
								))
				.registerAction(BlockStateBehaviours.PLACE,
						(block) -> (state, origin, args) -> group(block).invoker$onPlace(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.otherState()),
								args.movedByPiston()
								))
				.registerAction(BlockStateBehaviours.REMOVE,
						(block) -> (state, origin, args) -> group(block).invoker$onRemove(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.otherState()),
								args.movedByPiston()
								))
				.registerAction(BlockStateBehaviours.EXPLOSION,
						(block) -> (state, origin, args) -> group(block).invoker$onExplosionHit(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.explosion()),
								(item, pos) -> args.drop().accept(Converter.asSponge(item), Converter.asSponge(pos))
								))
				.register(BlockStateBehaviours.SHAPE_UPDATE,
						(block) -> (state, origin, args) -> Converter.asSponge(group(block).invoker$updateShape(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.updates()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.direction().opposite()),
								Converter.asVanilla(args.neighbourPosition()),
								Converter.asVanilla(args.neighbourState()),
								Converter.asVanilla(args.random())
								)))
				.registerAction(BlockStateBehaviours.SIGNAL_UPDATE,
						(block) -> (state, origin, args) -> group(block).invoker$neighborChanged(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.notifier()),
								Converter.asVanilla(args.orientation().orElse(null)),
								args.movedByPiston()
								))
				.register(BlockStateBehaviours.USE_WITH_ITEM,
						(block) -> (state, origin, args) -> Converter.asSponge(group(block).invoker$useItemOn(
								Converter.asVanilla(args.item()),
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.hit()).getBlockPos(),
								Converter.asVanilla(args.entity()),
								Converter.asVanilla(args.hand()),
								Converter.asVanilla(args.hit())
								)))
				.register(BlockStateBehaviours.USE_WITHOUT_ITEM,
						(block) -> (state, origin, args) -> Converter.asSponge(group(block).invoker$useWithoutItem(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.hit()).getBlockPos(),
								Converter.asVanilla(args.entity()),
								Converter.asVanilla(args.hit())
								)))
				.registerAction(BlockStateBehaviours.ATTACK,
						(block) -> (state, origin, args) -> group(block).invoker$attack(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								Converter.asVanilla(args.entity())
								))
				// Neo adds extension
				.registerResult(BlockStateBehaviours.PUSH_REACTION,
						(block) -> Converter.asSponge(properties(block).accessor$pushReaction()))
				.registerResult(BlockStateBehaviours.INSTRUMENT,
						(block) -> Converter.asSponge(properties(block).accessor$instrument()))
				.register(BlockStateBehaviours.FLUID,
						(block) -> (state, origin, args) -> Converter.asSponge(group(block).invoker$getFluidState(
								state
								)))
				.registerResult(BlockStateBehaviours.REQUIRE_TOOL,
						(block) -> properties(block).accessor$requiresCorrectToolForDrops())
				.registerResult(BlockStateBehaviours.REPLACEABLE,
						(block) -> properties(block).accessor$replaceable())
				.register(BlockStateBehaviours.REPLACEABLE_BY_FLUID,
						(block) -> (state, origin, args) -> group(block).invoker$canBeReplaced(
								state,
								Converter.asVanilla(args.fluid())
								))
				.register(BlockStateBehaviours.REPLACEABLE_BY_BLOCK,
						(block) -> (state, origin, args) -> group(block).invoker$canBeReplaced(
								state,
								Converter.asVanilla(args.context())
								))
				.register(BlockStateBehaviours.SUFFOCATION,
						(block) -> (state, origin, args) -> properties(block).accessor$isSuffocating().test(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position())
								))
				.register(BlockStateBehaviours.CAN_SURVIVE,
						(block) -> (state, origin, args) -> group(block).invoker$canSurvive(
								state,
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position())
								))
				// Neo adds extension
				.register(BlockStateBehaviours.CLONE_ITEM,
						(block) -> (state, origin, args) -> Converter.asSponge(group(block).invoker$getCloneItemStack(
								Converter.asVanilla(args.volume()),
								Converter.asVanilla(args.position()),
								state,
								args.data()
								)))
				.register(BlockStateBehaviours.BUCKET_PICKUP_ITEM,
						(block) -> (state, origin, args) -> Converter.asSponge(BucketUtil.bucketPickup(block).pickupBlock(
										Converter.asVanilla(args.entity().orElse(null)),
										Converter.asVanilla(args.volume()),
										Converter.asVanilla(args.position()),
										state
										)))

				.register(BlockStateBehaviours.BUCKET_PICKUP_SOUND,
						(block) -> (state, origin, args) -> BucketUtil.bucketPickup(block).getPickupSound().map(Converter::asSponge))
						;
	}
	
	private static BlockBehaviourAccessor behaviour(final Block block) {
		return (BlockBehaviourAccessor) block;
	}
	
	private static BlockBehaviour_PropertiesAccessor properties(final Block block) {
		return ((BlockBehaviour_PropertiesAccessor) block.properties());
	}
}
