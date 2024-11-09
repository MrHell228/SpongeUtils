package net.hellheim.spongeutils.source.optional.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface OptionalEntityArchetypeSource<T extends Entity> extends OptionalEntityTypeSource<T> {
	
	Optional<EntityArchetype> getAsEntityArchetype();
	
	@SuppressWarnings("unchecked")
	@Override
	default Optional<EntityType<? extends T>> getAsEntityType() {
		return this.getAsEntityArchetype().map(archetype -> (EntityType<T>) archetype.type());
	}
	
	default Optional<T> createEntityOfArchetype(final World<?, ?> world, final Vector3d position) {
		final Optional<T> opt = this.createEntityOfType(world, position);
		opt.ifPresent(entity -> entity.setRawData(this.getAsEntityArchetype().get().entityData()));
		return opt;
	}
	
	@Override
	default Optional<T> createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityOfArchetype(world, position);
	}
}
