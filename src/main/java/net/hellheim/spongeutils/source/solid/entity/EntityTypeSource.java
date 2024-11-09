package net.hellheim.spongeutils.source.solid.entity;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface EntityTypeSource<T extends Entity> extends IEntitySource<T> {
	
	EntityType<? extends T> getAsEntityType();
	
	default T createEntityOfType(final World<?, ?> world, final Vector3d position) {
		return world.createEntity(this.getAsEntityType(), position);
	}
	
	@Override
	default T createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityOfType(world, position);
	}
}
