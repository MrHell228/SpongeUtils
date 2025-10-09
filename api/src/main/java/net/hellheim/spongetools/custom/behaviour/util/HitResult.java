package net.hellheim.spongetools.custom.behaviour.util;

import java.util.Objects;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.blockray.RayTraceResult;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

/**
 * Similar to {@link RayTraceResult} but reflects
 * vanilla stuff because they can't be mapped 1 to 1.
 */
public interface HitResult {
	
	static EntityHitResult entity(final RayTraceResult<Entity> rayTrace) {
		return HitResult.entity(rayTrace.selectedObject(), rayTrace.hitPosition());
	}
	
	static EntityHitResult entity(final Entity entity) {
		return HitResult.entity(entity, entity.position());
	}
	
	static EntityHitResult entity(final Entity entity, final Vector3d hitPosition) {
		Objects.requireNonNull(hitPosition, "hitPosition");
		return HitResult.entity(entity, hitPosition.x(), hitPosition.y(), hitPosition.z());
	}
	
	static EntityHitResult entity(final Entity entity, final double hitX, final double hitY, final double hitZ) {
		return Sponge.game().factoryProvider().provide(Factory.class).entity(entity, hitX, hitY, hitZ);
	}
	
	static BlockHitResult.Builder block() {
		return Sponge.game().builderProvider().provide(BlockHitResult.Builder.class);
	}
	
	/**
	 * Returns the position the ray ended at.
	 * 
	 * @return The position the ray ended at
	 */
	Vector3d hitPosition();
	
	/**
	 * Returns whether this hit result is considered as missed. <br>
	 * 
	 * Usually this returns true if this result is {@link BlockHitResult} and
	 * its {@link BlockHitResult.Builder#miss(boolean)} flag is set to true.
	 * 
	 * @return True if this hit result is considered as missed
	 */
	boolean miss();
	
	interface EntityHitResult extends HitResult {
		
		/**
		 * Returns the entity hit by the ray.
		 * 
		 * @return The entity hit by the ray
		 */
		Entity entity();
	}
	
	interface BlockHitResult extends HitResult {
		
		/**
		 * Returns the block position hit by the ray.
		 * 
		 * @return The block position hit by the ray
		 */
		Vector3i blockPosition();
		
		/**
		 * Returns the direction the ray hit the block from.
		 * 
		 * @return The direction the ray hit the block from
		 */
		Direction direction();
		
		/**
		 * Returns whether the ray ended inside the block it hit.
		 * 
		 * @return True if the ray ended inside the block it hit
		 */
		boolean inside();
		
		/**
		 * Returns whether the ray hit the world border.
		 * 
		 * @return True if the ray hit the world border
		 */
		boolean worldBorder();
		
		default Builder toBuilder() {
			return HitResult.block().from(this);
		}
		
		interface Builder extends
				org.spongepowered.api.util.Builder<BlockHitResult, Builder>,
				CopyableBuilder<BlockHitResult, Builder> {
			
			Builder hitPosition(double x, double y, double z);
			
			default Builder hitPosition(final Vector3d position) {
				Objects.requireNonNull(position, "position");
				return this.hitPosition(position.x(), position.y(), position.z());
			}
			
			Builder blockPosition(int x, int y, int z);
			
			default Builder blockPosition(final Vector3i position) {
				Objects.requireNonNull(position, "position");
				return this.blockPosition(position.x(), position.y(), position.z());
			}
			
			Builder direction(Direction direction);
			
			Builder miss(boolean miss);
			
			Builder inside(boolean inside);
			
			Builder worldBorder(boolean worldBorder);
		}
	}
	
	interface Factory {
		
		EntityHitResult entity(Entity entity, double hitX, double hitY, double hitZ);
	}
}
