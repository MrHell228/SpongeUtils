package net.hellheim.spongeutils.source.optional.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongeutils.EntityUtil;

public interface OptionalEntitySource<T extends Entity> extends OptionalEntityArchetypeSource<T> {
	
	Optional<T> getAsEntity();
	
	@Override
	default Optional<EntityArchetype> getAsEntityArchetype() {
		return this.getAsEntity().map(Entity::createArchetype);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	default Optional<EntityType<? extends T>> getAsEntityType() {
		return this.getAsEntity().map(entity -> (EntityType<T>) entity.type());
	}
	
	default Optional<T> createEntityCopy(final World<?, ?> world) {
		return this.getAsEntity().map($this -> {
			final T entity = this.createEntityOfType(world, $this.position()).get();
			entity.copyFrom($this);
			return entity;
		});
	}
	
	default Optional<T> createEntityCopy(final World<?, ?> world, final Vector3d position) {
		final Optional<T> opt = this.createEntityCopy(world);
		opt.ifPresent(entity -> entity.setPosition(position));
		return opt;
	}
	
	
	@Override
	default Optional<T> createEntity(final World<?, ?> world, final Vector3d position) {
		return this.createEntityCopy(world, position);
	}
	
	
	
	default void kill() {
		this.getAsEntity().ifPresent(entity -> EntityUtil.kill(entity));
	}
	
	default Optional<Vector3i> blockPosition() {
		return this.getAsEntity().map(entity -> entity.position().toInt());
	}
	
	default Optional<Vector3d> position() {
		return this.getAsEntity().map(entity -> entity.position());
	}
	
	default boolean setPosition(Vector3d position) {
		return this.getAsEntity().map(entity -> entity.setPosition(position)).orElse(false);
	}
	
	default boolean setLocation(ServerLocation location) {
		return this.getAsEntity().map(entity -> entity.setLocation(location)).orElse(false);
	}
}
