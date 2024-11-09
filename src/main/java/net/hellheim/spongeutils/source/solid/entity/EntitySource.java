package net.hellheim.spongeutils.source.solid.entity;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface EntitySource<T extends Entity> extends EntityArchetypeSource<T> {
	
	T getAsEntity();
	
	@Override
	default EntityArchetype getAsEntityArchetype() {
		return this.getAsEntity().createArchetype();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	default EntityType<? extends T> getAsEntityType() {
		return (EntityType<T>) this.getAsEntity().type();
	}
	
	default T createEntityCopy(final World<?, ?> world) {
		final T entity = this.createEntityOfType(world, this.getAsEntity().position());
		entity.copyFrom(this.getAsEntity());
		return entity;
	}
	
	default T createEntityCopy(final World<?, ?> world, final Vector3d position) {
		final T entity = this.createEntityCopy(world);
		entity.setPosition(position);
		return entity;
	}
	
	
	@Override
	default T createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityCopy(world, position);
	}
}
