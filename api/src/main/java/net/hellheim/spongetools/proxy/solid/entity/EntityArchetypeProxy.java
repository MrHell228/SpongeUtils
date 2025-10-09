package net.hellheim.spongetools.proxy.solid.entity;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface EntityArchetypeProxy<T extends Entity> extends EntityTypeProxy<T> {
	
	EntityArchetype getAsEntityArchetype();
	
	@SuppressWarnings("unchecked")
	@Override
	default EntityType<? extends T> getAsEntityType() {
		return (EntityType<T>) this.getAsEntityArchetype().type();
	}
	
	default T createEntityOfArchetype(final World<?, ?> world, final Vector3d position) {
		final T entity = this.createEntityOfType(world, position);
		entity.setRawData(this.getAsEntityArchetype().entityData());
		return entity;
	}
	
	@Override
	default T createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityOfArchetype(world, position);
	}
}
