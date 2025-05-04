package net.hellheim.spongetools.custom.behaviour;

import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.volume.Volume;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.util.HitResult;

public interface BehaviourArgs {
	
	interface Randomized<A extends Randomized<A>> extends BehaviourArgs {
		
		RandomProvider.Source random();
		
		A withRandom(RandomProvider.Source random);
		
		default A withRandom(final RandomProvider randomProvider) {
			Objects.requireNonNull(randomProvider, "randomProvider");
			return this.withRandom(randomProvider.random());
		}
	}
	
	interface Directional<A extends Directional<A>> extends BehaviourArgs {
		
		Direction direction();
		
		A withDirection(Direction direction);
	}
	
	interface Positional<A extends Positional<A>> extends BehaviourArgs {
		
		Vector3i position();
		
		A withPosition(Vector3i position);
		
		default A withPosition(final Vector3d position) {
			Objects.requireNonNull(position, "position");
			return this.withPosition(position.toInt());
		}
		
		default A withPosition(final int x, final int y, final int z) {
			return this.withPosition(new Vector3i(x, y, z));
		}
		
		default A withPosition(final double x, final double y, final double z) {
			return this.withPosition(new Vector3i(x, y, z));
		}
	}
	
	interface Volumed<V extends Volume, A extends Volumed<V, A>> extends BehaviourArgs {
		
		V volume();
		
		A withVolume(V volume);
	}
	
	interface EntitySource<E, A extends EntitySource<E, A>> extends BehaviourArgs {
		
		E entity();
		
		A withEntity(E entity);
		
		default A withEntity(final Supplier<? extends E> entitySupplier) {
			return this.withEntity(Objects.requireNonNull(entitySupplier, "entitySupplier").get());
		}
	}
	
	interface RayTraced<H extends HitResult, A extends RayTraced<H, A>> extends BehaviourArgs {
		
		H hit();
		
		A withHit(H hit);
	}
	
	interface Contextual<C, A extends Contextual<C, A>> extends BehaviourArgs {
		
		C context();
		
		A withContext(C context);
	}
}
