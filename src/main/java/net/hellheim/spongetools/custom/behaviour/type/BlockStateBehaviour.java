package net.hellheim.spongetools.custom.behaviour.type;

import java.util.Objects;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.block.state.BlockStateExtension;

public interface BlockStateBehaviour<R, A extends BehaviourArgs> extends Behaviour<BlockStateExtension, R, A> {
	
	interface SpawnValidator extends BlockStateBehaviour<Boolean, SpawnValidator.Args> {
		
		default boolean call(final PrimitiveGameVolume volume, final Vector3i position, final EntityType<?> entity) {
			return this.call(new Args(volume, position, entity));
		}
		
		record Args(PrimitiveGameVolume volume, Vector3i position, EntityType<?> entity) implements BehaviourArgs {
			
			public Args(final PrimitiveGameVolume volume, final Vector3i position, final EntityType<?> entity) {
				this.volume = Objects.requireNonNull(volume, "volume");
				this.position = Objects.requireNonNull(position, "position");
				this.entity = Objects.requireNonNull(entity, "entity");
			}
			
			public Args withVolume(final PrimitiveGameVolume volume) {
				return new Args(volume, this.position, this.entity);
			}
			
			public Args withPosition(final Vector3i position) {
				return new Args(this.volume, position, this.entity);
			}
			
			public Args withEntity(final EntityType<?> entity) {
				return new Args(this.volume, this.position, entity);
			}
		}
	}
	
	interface SignalPower extends BlockStateBehaviour<Integer, SignalPower.Args> {
		
		default int call(final PrimitiveGameVolume volume, final Vector3i position, final Direction direction) {
			return this.call(new Args(volume, position, direction));
		}
		
		record Args(PrimitiveGameVolume volume, Vector3i position, Direction direction) implements BehaviourArgs {
			
			public Args(final PrimitiveGameVolume volume, final Vector3i position, final Direction direction) {
				this.volume = Objects.requireNonNull(volume, "volume");
				this.position = Objects.requireNonNull(position, "position");
				this.direction = Objects.requireNonNull(direction, "direction");
			}
			
			public Args withVolume(final PrimitiveGameVolume volume) {
				return new Args(volume, this.position, this.direction);
			}
			
			public Args withPosition(final Vector3i position) {
				return new Args(this.volume, position, this.direction);
			}
			
			public Args withDirection(final Direction direction) {
				return new Args(this.volume, this.position, direction);
			}
		}
	}
	
	interface Tick extends BlockStateBehaviour<Void, Tick.Args> {
		
		default void call(final ServerWorld world, final Vector3i position, final RandomProvider.Source random) {
			this.call(new Args(world, position, random));
		}
		
		record Args(ServerWorld world, Vector3i position, RandomProvider.Source random) implements BehaviourArgs {
			
			public Args(final ServerWorld world, final Vector3i position, final RandomProvider.Source random) {
				this.world = Objects.requireNonNull(world, "world");
				this.position = Objects.requireNonNull(position, "position");
				this.random = Objects.requireNonNull(random, "random");
			}
			
			public Args withWorld(final ServerWorld world) {
				return new Args(world, this.position, this.random);
			}
			
			public Args withPosition(final Vector3i position) {
				return new Args(this.world, position, this.random);
			}
			
			public Args withRandom(final RandomProvider.Source random) {
				return new Args(this.world, this.position, random);
			}
		}
	}
}
