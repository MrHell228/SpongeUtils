package net.hellheim.spongetools.common.behaviour;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

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

import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviour.Place.Args;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;
import net.hellheim.spongetools.custom.behaviour.util.UseContext.BlockPlace;

public final class BlockStateArgs {
	
	public record Locatable<V extends Volume>(V volume, Vector3i position)
			implements BlockStateBehaviour.Locatable.Args<V> {
		
		@Override
		public Locatable<V> withVolume(final V volume) {
			return new BlockStateArgs.Locatable<>(Objects.requireNonNull(volume, "volume"), this.position);
		}
		
		@Override
		public Locatable<V> withPosition(final Vector3i position) {
			return new BlockStateArgs.Locatable<>(this.volume, Objects.requireNonNull(position, "position"));
		}
	}
	
	public record LocatableEntity<V extends Volume, E>(V volume, Vector3i position, E entity)
			implements BlockStateBehaviour.LocatableEntity.Args<V, E> {
		
		@Override
		public LocatableEntity<V, E> withVolume(final V volume) {
			return new BlockStateArgs.LocatableEntity<>(Objects.requireNonNull(volume, "volume"), this.position, this.entity);
		}
		
		@Override
		public LocatableEntity<V, E> withPosition(final Vector3i position) {
			return new BlockStateArgs.LocatableEntity<>(this.volume, Objects.requireNonNull(position, "position"), this.entity);
		}
		
		@Override
		public LocatableEntity<V, E> withEntity(final E entity) {
			return new BlockStateArgs.LocatableEntity<>(this.volume, this.position, Objects.requireNonNull(entity, "entity"));
		}
	}
	
	public record SignalPower<V extends Volume>(V volume, Vector3i position, Direction direction)
			implements BlockStateBehaviour.SignalPower.Args<V> {
		
		@Override
		public SignalPower<V> withVolume(final V volume) {
			return new SignalPower<>(Objects.requireNonNull(volume, "volume"), this.position, this.direction);
		}
		
		@Override
		public SignalPower<V> withPosition(final Vector3i position) {
			return new SignalPower<>(this.volume, Objects.requireNonNull(position, "position"), this.direction);
		}
		
		@Override
		public SignalPower<V> withDirection(final Direction direction) {
			return new SignalPower<>(this.volume, this.position, Objects.requireNonNull(direction, "direction"));
		}
	}
	
	public record Tick(ServerWorld volume, Vector3i position, RandomProvider.Source random)
			implements BlockStateBehaviour.Tick.Args {
		
		@Override
		public Tick withVolume(final ServerWorld volume) {
			return new Tick(Objects.requireNonNull(volume, "volume"), this.position, this.random);
		}
		
		@Override
		public Tick withPosition(final Vector3i position) {
			return new Tick(this.volume, Objects.requireNonNull(position, "position"), this.random);
		}
		
		@Override
		public Tick withRandom(final RandomProvider.Source random) {
			return new Tick(this.volume, this.position, Objects.requireNonNull(random, "random"));
		}
	}
	
	public record Place(World<?, ?> volume, Vector3i position, BlockState otherState, boolean movedByPiston)
			implements BlockStateBehaviour.Place.Args {
		
		@Override
		public Place withVolume(final World<?, ?> volume) {
			return new Place(Objects.requireNonNull(volume, "volume"), this.position, this.otherState, this.movedByPiston);
		}
		
		@Override
		public Place withPosition(final Vector3i position) {
			return new Place(this.volume, Objects.requireNonNull(position, "position"), this.otherState, this.movedByPiston);
		}
		
		@Override
		public Args withOtherState(final BlockState otherState) {
			return new Place(this.volume, this.position, Objects.requireNonNull(otherState, "otherState"), this.movedByPiston);
		}
		
		@Override
		public Place withMovedByPiston(final boolean movedByPiston) {
			return new Place(this.volume, this.position, this.otherState, movedByPiston);
		}
	}
	
	public record Remove(ServerWorld volume, Vector3i position, boolean movedByPiston)
			implements BlockStateBehaviour.Remove.Args {
		
		@Override
		public Remove withVolume(final ServerWorld volume) {
			return new Remove(Objects.requireNonNull(volume, "volume"), this.position, this.movedByPiston);
		}
		
		@Override
		public Remove withPosition(final Vector3i position) {
			return new Remove(this.volume, Objects.requireNonNull(position, "position"), this.movedByPiston);
		}
		
		@Override
		public Remove withMovedByPiston(final boolean movedByPiston) {
			return new Remove(this.volume, this.position, movedByPiston);
		}
	}
	
	public record ExplosionHit(ServerWorld volume, Vector3i position, Explosion explosion, BiConsumer<ItemStack, Vector3i> drop)
			implements BlockStateBehaviour.ExplosionHit.Args {
		
		@Override
		public ExplosionHit withVolume(final ServerWorld volume) {
			return new ExplosionHit(Objects.requireNonNull(volume, "volume"), this.position, this.explosion, this.drop);
		}
		
		@Override
		public ExplosionHit withPosition(final Vector3i position) {
			return new ExplosionHit(this.volume, Objects.requireNonNull(position, "position"), this.explosion, this.drop);
		}
		
		@Override
		public ExplosionHit withExplosion(final Explosion explosion) {
			return new ExplosionHit(this.volume, this.position, Objects.requireNonNull(explosion, "explosion"), this.drop);
		}
		
		@Override
		public ExplosionHit withDrop(final BiConsumer<ItemStack, Vector3i> drop) {
			return new ExplosionHit(this.volume, this.position, this.explosion, drop);
		}
	}
	
	public record ShapeUpdate(
			Region<?> volume, UpdatableVolume updates, Vector3i position,
			Direction direction, Vector3i neighbourPosition, BlockState neighbourState,
			RandomProvider.Source random
			) implements BlockStateBehaviour.ShapeUpdate.Args {
		
		@Override
		public ShapeUpdate withVolume(final Region<?> volume) {
			return new ShapeUpdate(Objects.requireNonNull(volume, "volume"), this.updates, this.position, this.direction, this.neighbourPosition, this.neighbourState, this.random);
		}
		
		@Override
		public ShapeUpdate withUpdates(final UpdatableVolume updates) {
			return new ShapeUpdate(this.volume, Objects.requireNonNull(updates, "updates"), this.position, this.direction, this.neighbourPosition, this.neighbourState, this.random);
		}
		
		@Override
		public ShapeUpdate withPosition(final Vector3i position) {
			return new ShapeUpdate(this.volume, this.updates, Objects.requireNonNull(position, "position"), this.direction, this.neighbourPosition, this.neighbourState, this.random);
		}
		
		@Override
		public ShapeUpdate withDirection(final Direction direction) {
			return new ShapeUpdate(this.volume, this.updates, this.position, Objects.requireNonNull(direction, "direction"), this.neighbourPosition, this.neighbourState, this.random);
		}
		
		@Override
		public ShapeUpdate withNeighbourPosition(final Vector3i neighbourPosition) {
			return new ShapeUpdate(this.volume, this.updates, this.position, this.direction, Objects.requireNonNull(neighbourPosition, "neighbourPosition"), this.neighbourState, this.random);
		}
		
		@Override
		public ShapeUpdate withNeighbourState(final BlockState neighbourState) {
			return new ShapeUpdate(this.volume, this.updates, this.position, this.direction, this.neighbourPosition, Objects.requireNonNull(neighbourState, "neighbourState"), this.random);
		}
		
		@Override
		public ShapeUpdate withRandom(final RandomProvider.Source random) {
			return new ShapeUpdate(this.volume, this.updates, this.position, this.direction, this.neighbourPosition, this.neighbourState, Objects.requireNonNull(random, "random"));
		}
	}
	
	public record SignalUpdate(
			World<?, ?> volume, Vector3i position, BlockType notifier,
			Optional<SignalOrientation> orientation, boolean movedByPiston
			) implements BlockStateBehaviour.SignalUpdate.Args {
		
		@Override
		public SignalUpdate withVolume(final World<?, ?> volume) {
			return new SignalUpdate(Objects.requireNonNull(volume, "volume"), this.position, this.notifier, this.orientation, this.movedByPiston);
		}
		
		@Override
		public SignalUpdate withPosition(final Vector3i position) {
			return new SignalUpdate(this.volume, Objects.requireNonNull(position, "position"), this.notifier, this.orientation, this.movedByPiston);
		}
		
		@Override
		public SignalUpdate withNotifier(final BlockType notifier) {
			return new SignalUpdate(this.volume, this.position, Objects.requireNonNull(notifier, "notifier"), this.orientation, this.movedByPiston);
		}
		
		@Override
		public SignalUpdate withOrientation(final Optional<SignalOrientation> orientation) {
			return new SignalUpdate(this.volume, this.position, this.notifier, Objects.requireNonNull(orientation, "orientation"), this.movedByPiston);
		}
		
		@Override
		public SignalUpdate withMovedByPiston(final boolean movedByPiston) {
			return new SignalUpdate(this.volume, this.position, this.notifier, this.orientation, movedByPiston);
		}
	}
	
	public record UseWithItem(
			World<?, ?> volume, Player entity, HitResult.BlockHitResult hit, HandType hand, ItemStack item
			) implements BlockStateBehaviour.UseWithItem.Args {
		
		@Override
		public UseWithItem withVolume(final World<?, ?> volume) {
			return new UseWithItem(Objects.requireNonNull(volume, "volume"), this.entity, this.hit, this.hand, this.item);
		}
		
		@Override
		public UseWithItem withEntity(final Player entity) {
			return new UseWithItem(this.volume, Objects.requireNonNull(entity, "entity"), this.hit, this.hand, this.item);
		}
		
		@Override
		public UseWithItem withHit(final HitResult.BlockHitResult hit) {
			return new UseWithItem(this.volume, this.entity, Objects.requireNonNull(hit, "hit"), this.hand, this.item);
		}
		
		@Override
		public UseWithItem withHand(final HandType hand) {
			return new UseWithItem(this.volume, this.entity, this.hit, Objects.requireNonNull(hand, "hand"), this.item);
		}
		
		@Override
		public UseWithItem withItem(final ItemStackLike item) {
			return new UseWithItem(this.volume, this.entity, this.hit, this.hand, Objects.requireNonNull(item, "item").asMutable());
		}
	}
	
	public record UseWithoutItem(World<?, ?> volume, Player entity, HitResult.BlockHitResult hit)
			implements BlockStateBehaviour.UseWithoutItem.Args {
		
		@Override
		public UseWithoutItem withVolume(final World<?, ?> volume) {
			return new UseWithoutItem(Objects.requireNonNull(volume, "volume"), this.entity, this.hit);
		}
		
		@Override
		public UseWithoutItem withEntity(final Player entity) {
			return new UseWithoutItem(this.volume, Objects.requireNonNull(entity, "entity"), this.hit);
		}
		
		@Override
		public UseWithoutItem withHit(final HitResult.BlockHitResult hit) {
			return new UseWithoutItem(this.volume, this.entity, Objects.requireNonNull(hit, "hit"));
		}
	}
	
	public record ReplaceableByFluid(FluidType fluid)
			implements BlockStateBehaviour.ReplaceableByFluid.Args {
		
		@Override
		public ReplaceableByFluid withFluid(final FluidType fluid) {
			return new ReplaceableByFluid(Objects.requireNonNull(fluid, "fluid"));
		}
	}
	
	public record ReplaceableByBlock(UseContext.BlockPlace context)
			implements BlockStateBehaviour.ReplaceableByBlock.Args {
		
		@Override
		public ReplaceableByBlock withContext(final BlockPlace context) {
			return new ReplaceableByBlock(Objects.requireNonNull(context, "context"));
		}
	}
	
	public record CloneItem(Region<?> volume, Vector3i position, boolean data)
			implements BlockStateBehaviour.CloneItem.Args {
		
		@Override
		public CloneItem withVolume(final Region<?> volume) {
			return new CloneItem(Objects.requireNonNull(volume, "volume"), this.position, this.data);
		}
		
		@Override
		public CloneItem withPosition(final Vector3i position) {
			return new CloneItem(this.volume, Objects.requireNonNull(position, "position"), this.data);
		}
		
		@Override
		public CloneItem withData(final boolean data) {
			return new CloneItem(this.volume, this.position, Objects.requireNonNull(data, "data"));
		}
	}
	
	private BlockStateArgs() {
	}
}
