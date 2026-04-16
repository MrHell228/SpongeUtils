package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.api.world.volume.Volume;
import org.spongepowered.api.world.volume.game.Region;
import org.spongepowered.api.world.volume.game.UpdatableVolume;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;

@FunctionalInterface
public interface BlockStateBehaviour<R, A extends BehaviourArgs> extends Behaviour<R, A> {
	
	@FunctionalInterface
	interface Locatable<R, V extends Volume> extends BlockStateBehaviour<R, Locatable.Args<V>> {
		
		@Override
		default R call(final Args<V> args) {
			return this.call(args.volume(), args.position());
		}
		
		R call(V volume, Vector3i position);
		
		interface Args<V extends Volume> extends
				BehaviourArgs.Volumed<V, Args<V>>,
				BehaviourArgs.Positional<Args<V>> {
		}
	}
	
	@FunctionalInterface
	interface LocatableEntity<R, V extends Volume, E> extends BlockStateBehaviour<R, LocatableEntity.Args<V, E>> {
		
		@Override
		default R call(final Args<V, E> args) {
			return this.call(args.volume(), args.position(), args.entity());
		}
		
		R call(V volume, Vector3i position, E entity);
		
		interface Args<V extends Volume, E> extends
				BehaviourArgs.Volumed<V, Args<V, E>>,
				BehaviourArgs.Positional<Args<V, E>>,
				BehaviourArgs.EntitySource<E, Args<V, E>>{
		}
	}
	
	@FunctionalInterface
	interface SignalPower<V extends Volume> extends BlockStateBehaviour<Integer, SignalPower.Args<V>> {
		
		@Override
		default Integer call(final Args<V> args) {
			return this.call(args.volume(), args.position(), args.direction());
		}
		
		int call(V volume, Vector3i position, Direction direction);
		
		interface Args<V extends Volume> extends
				BehaviourArgs.Volumed<V, Args<V>>,
				BehaviourArgs.Positional<Args<V>>,
				BehaviourArgs.Directional<Args<V>> {
		}
	}
	
	@FunctionalInterface
	interface Tick extends BlockStateBehaviour<Void, Tick.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.random());
			return null;
		}
		
		void call(ServerWorld volume, Vector3i position, RandomProvider.Source random);
		
		interface Args extends
				BehaviourArgs.Volumed<ServerWorld, Args>,
				BehaviourArgs.Positional<Args>,
				BehaviourArgs.Randomized<Args> {
		}
	}
	
	@FunctionalInterface
	interface Place extends BlockStateBehaviour<Void, Place.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.otherState(), args.movedByPiston());
			return null;
		}
		
		void call(World<?, ?> volume, Vector3i position, BlockState otherState, boolean movedByPiston);
		
		interface Args extends
				BehaviourArgs.Volumed<World<?, ?>, Args>,
				BehaviourArgs.Positional<Args> {
			
			BlockState otherState();
			
			Args withOtherState(BlockState otherState);
			
			boolean movedByPiston();
			
			Args withMovedByPiston(boolean movedByPiston);
		}
	}
	
	@FunctionalInterface
	interface Remove extends BlockStateBehaviour<Void, Remove.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.movedByPiston());
			return null;
		}
		
		void call(ServerWorld volume, Vector3i position, boolean movedByPiston);
		
		interface Args extends
				BehaviourArgs.Volumed<ServerWorld, Args>,
				BehaviourArgs.Positional<Args> {
			
			boolean movedByPiston();
			
			Args withMovedByPiston(boolean movedByPiston);
		}
	}
	
	@FunctionalInterface
	interface ExplosionHit extends BlockStateBehaviour<Void, ExplosionHit.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.volume(), args.position(), args.explosion(), args.drop());
			return null;
		}
		
		void call(ServerWorld volume, Vector3i position, Explosion explosion, BiConsumer<ItemStack, Vector3i> drop);
		
		interface Args extends
				BehaviourArgs.Volumed<ServerWorld, Args>,
				BehaviourArgs.Positional<Args> {
			
			Explosion explosion();
			
			BiConsumer<ItemStack, Vector3i> drop();
			
			Args withExplosion(Explosion explosion);
			
			Args withDrop(BiConsumer<ItemStack, Vector3i> drop);
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
			return this.call(args.volume(), args.entity(), args.hit(), args.hand(), args.item());
		}
		
		InteractionResult call(World<?, ?> volume, Player entity, HitResult.BlockHitResult hit, HandType hand, ItemStackLike item);
		
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
	interface ReplaceableByFluid extends BlockStateBehaviour<Boolean, ReplaceableByFluid.Args> {
		
		@Override
		default Boolean call(final Args args) {
			return this.call(args.fluid());
		}
		
		default boolean call(final Supplier<? extends FluidType> fluidSupplier) {
			return this.call(Objects.requireNonNull(fluidSupplier, "fluidSupplier").get());
		}
		
		boolean call(FluidType fluid);
		
		interface Args extends BehaviourArgs {
			
			FluidType fluid();
			
			Args withFluid(FluidType fluid);
			
			default Args withFluid(final Supplier<? extends FluidType> fluidSupplier) {
				return this.withFluid(Objects.requireNonNull(fluidSupplier, "fluidSupplier").get());
			}
		}
	}
	
	@FunctionalInterface
	interface ReplaceableByBlock extends BlockStateBehaviour<Boolean, ReplaceableByBlock.Args> {
		
		@Override
		default Boolean call(final Args args) {
			return this.call(args.context());
		}
		
		boolean call(UseContext.BlockPlace context);
		
		interface Args extends
				BehaviourArgs.Contextual<UseContext.BlockPlace, Args> {
		}
	}
	
	@FunctionalInterface
	interface CloneItem extends BlockStateBehaviour<ItemStack, CloneItem.Args> {
		
		@Override
		default ItemStack call(final Args args) {
			return this.call(args.volume(), args.position(), args.data());
		}
		
		ItemStack call(Region<?> volume, Vector3i position, boolean data);
		
		interface Args extends
				BehaviourArgs.Volumed<Region<?>, Args>,
				BehaviourArgs.Positional<Args> {
			
			boolean data();
			
			Args withData(boolean data);
			
			default Args withData() {
				return this.withData(true);
			}
			
			default Args withoutData() {
				return this.withData(false);
			}
		}
	}
}
