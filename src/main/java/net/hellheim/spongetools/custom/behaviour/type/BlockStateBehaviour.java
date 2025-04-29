package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.api.world.volume.game.Region;
import org.spongepowered.api.world.volume.game.UpdatableVolume;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;

@FunctionalInterface
public interface BlockStateBehaviour<R, A extends BehaviourArgs> extends Behaviour<R, A> {
	
	@FunctionalInterface
	interface SpawnValidator extends BlockStateBehaviour<Boolean, SpawnValidator.Args> {
		
		@Override
		default Boolean call(final Args args) {
			return this.call(args.volume(), args.position(), args.entity());
		}
		
		boolean call(PrimitiveGameVolume volume, Vector3i position, EntityType<?> entity);
		
		interface Args extends
				BehaviourArgs.Volumed<PrimitiveGameVolume, Args>,
				BehaviourArgs.Positional<Args> {
			
			EntityType<?> entity();
			
			Args withEntity(EntityType<?> entity);
		}
	}
	
	@FunctionalInterface
	interface MapColor extends BlockStateBehaviour<MapColorType, MapColor.Args> {
		
		@Override
		default MapColorType call(final Args args) {
			return this.call(args.volume(), args.position());
		}
		
		MapColorType call(PrimitiveGameVolume volume, Vector3i position);
		
		interface Args extends
				BehaviourArgs.Volumed<PrimitiveGameVolume, Args>,
				BehaviourArgs.Positional<Args> {
		}
	}
	
	@FunctionalInterface
	interface PistonPushReaction extends BlockStateBehaviour<PushReaction, BehaviourArgs> {
		
		@Override
		default PushReaction call(final BehaviourArgs args) {
			return this.call();
		}
		
		PushReaction call();
	}
	
	@FunctionalInterface
	interface SignalConductor extends BlockStateBehaviour<Boolean, SignalConductor.Args> {
		
		@Override
		default Boolean call(final Args args) {
			return this.call(args.volume(), args.position());
		}
		
		boolean call(PrimitiveGameVolume volume, Vector3i position);
		
		interface Args extends
				BehaviourArgs.Volumed<PrimitiveGameVolume, Args>,
				BehaviourArgs.Positional<Args> {
		}
	}
	
	@FunctionalInterface
	interface SignalPower extends BlockStateBehaviour<Integer, SignalPower.Args> {
		
		@Override
		default Integer call(final Args args) {
			return this.call(args.volume(), args.position(), args.direction());
		}
		
		int call(PrimitiveGameVolume volume, Vector3i position, Direction direction);
		
		interface Args extends
				BehaviourArgs.Volumed<PrimitiveGameVolume, Args>,
				BehaviourArgs.Positional<Args>,
				BehaviourArgs.Directional<Args> {
		}
	}
	
	@FunctionalInterface
	interface AnalogSignalPower extends BlockStateBehaviour<Integer, AnalogSignalPower.Args> {
		
		@Override
		default Integer call(final Args args) {
			return this.call(args.volume(), args.position());
		}
		
		int call(World<?, ?> volume, Vector3i position);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.Positional<Args> {
		}
	}
	
	@FunctionalInterface
	interface Tick extends BlockStateBehaviour<Void, Tick.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.random());
			return null;
		}
		
		void call(ServerWorld world, Vector3i position, RandomProvider.Source random);
		
		interface Args extends
				BehaviourArgs.Volumed<ServerWorld, Args>,
				BehaviourArgs.Positional<Args>,
				BehaviourArgs.Randomized<Args> {
		}
	}
	
	@FunctionalInterface
	interface Replace extends BlockStateBehaviour<Void, Replace.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.otherState(), args.movedByPiston());
			return null;
		}
		
		void call(World<?, ?> world, Vector3i position, BlockState otherState, boolean movedByPiston);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.Positional<Args> {
			
			BlockState otherState();
			
			boolean movedByPiston();
			
			Args withOtherState(BlockState otherState);
			
			Args withMovedByPiston(boolean movedByPiston);
		}
	}
	
	@FunctionalInterface
	interface ShapeUpdate extends BlockStateBehaviour<BlockState, ShapeUpdate.Args> {
		
		@Override
		default BlockState call(final Args args) {
			return this.call(args.volume(), args.updates(), args.position(), args.direction(), args.neighbourPosition(), args.neighbourState(), args.random());
		}
		
		BlockState call(
			Region<?> volume, UpdatableVolume updates, Vector3i position,
			Direction direction, Vector3i neighbourPosition, BlockState neighbourState,
			RandomProvider.Source random
		);
		
		interface Args extends
				BehaviourArgs.Volumed<Region<?>, Args>,
				BehaviourArgs.Positional<Args>,
				BehaviourArgs.Directional<Args>,
				BehaviourArgs.Randomized<Args> {
			
			UpdatableVolume updates();
			
			Vector3i neighbourPosition();
			
			BlockState neighbourState();
			
			Args withUpdates(UpdatableVolume updates);
			
			Args withNeighbourPosition(Vector3i neighbourPosition);
			
			Args withNeighbourState(BlockState neighbourState);
		}
	}
	
	@FunctionalInterface
	interface SignalUpdate extends BlockStateBehaviour<Void, SignalUpdate.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.notifier(), args.orientation(), args.movedByPiston());
			return null;
		}
		
		void call(
			World<?, ?> volume, Vector3i position, BlockType notifier,
			Optional<SignalOrientation> orientation, boolean movedByPiston
		);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.Positional<Args> {
			
			BlockType notifier();
			
			Optional<SignalOrientation> orientation();
			
			boolean movedByPiston();
			
			Args withNotifier(BlockType notifier);
			
			default Args withNotifier(Supplier<? extends BlockType> notifierSupplier) {
				return this.withNotifier(Objects.requireNonNull(notifierSupplier, "notifierSupplier").get());
			}
			
			Args withOrientation(Optional<SignalOrientation> orientation);
			
			default Args withOrientation(final SignalOrientation orientation) {
				return this.withOrientation(Optional.of(Objects.requireNonNull(orientation, "orientation")));
			}
			
			default Args withoutOrientation() {
				return this.withOrientation(Optional.empty());
			}
			
			Args withMovedByPiston(boolean movedByPiston);
		}
	}
	
	@FunctionalInterface
	interface UseWithItem extends BlockStateBehaviour<InteractionResult, UseWithItem.Args> {
		
		@Override
		default InteractionResult call(final Args args) {
			return this.call(args.item(), args.volume(), args.entity(), args.hand(), args.hit());
		}
		
		InteractionResult call(ItemStackLike item, World<?, ?> volume, Player entity, HandType hand, HitResult.BlockHitResult hit);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.EntitySource<Player, Args>,
				BehaviourArgs.RayTraced<HitResult.BlockHitResult, Args> {
			
			HandType hand();
			
			ItemStack item();
			
			Args withHand(HandType hand);
			
			default Args withHand(Supplier<? extends HandType> handSupplier) {
				return this.withHand(Objects.requireNonNull(handSupplier, "handSupplier").get());
			}
			
			Args withItem(ItemStackLike item);
		}
	}
	
	@FunctionalInterface
	interface UseWithoutItem extends BlockStateBehaviour<InteractionResult, UseWithoutItem.Args> {
		
		@Override
		default InteractionResult call(final Args args) {
			return this.call(args.volume(), args.entity(), args.hit());
		}
		
		InteractionResult call(World<?, ?> volume, Player entity, HitResult.BlockHitResult hit);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.EntitySource<Player, Args>,
				BehaviourArgs.RayTraced<HitResult.BlockHitResult, Args> {
		}
	}
	
	@FunctionalInterface
	interface Attack extends BlockStateBehaviour<Void, Attack.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.entity());
			return null;
		}
		
		void call(World<?, ?> volume, Vector3i position, Player entity);
		
		interface Args extends 
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.Positional<Args>,
				BehaviourArgs.EntitySource<Player, Args> {
		}
	}
}
