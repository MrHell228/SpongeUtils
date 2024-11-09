package net.hellheim.spongeutils.source.optional.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface OptionalEntityTypeSource<T extends Entity> extends OptionalIEntitySource<T> {
	
	Optional<EntityType<? extends T>> getAsEntityType();
	
	default Optional<T> createEntityOfType(final World<?, ?> world, final Vector3d position) {
		return this.getAsEntityType().map(type -> world.createEntity(type, position));
	}
	
	@Override
	default Optional<T> createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityOfType(world, position);
	}
}
