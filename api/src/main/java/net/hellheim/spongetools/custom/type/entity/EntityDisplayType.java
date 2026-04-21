package net.hellheim.spongetools.custom.type.entity;

import java.util.Objects;
import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.world.volume.archetype.entity.EntityArchetypeEntry;
import org.spongepowered.math.vector.Vector3d;

public interface EntityDisplayType {
	
	static Simple simple(final EntityArchetypeEntry entity) {
		return EntityDisplayType.simple(entity.position(), entity.archetype());
	}
	
	static Simple simple(final EntityArchetype entity) {
		return EntityDisplayType.simple(Vector3d.ZERO, entity);
	}
	
	static Simple simple(final Vector3d shift, final EntityArchetype entity) {
		return new Simple(shift, entity);
	}
	
	/**
	 * Creates the display for the given owner.
	 * 
	 * @param owner
	 * @return The display
	 */
	Optional<Entity> create(Entity owner);
	
	record Simple(Vector3d shift, EntityArchetype entity) implements EntityDisplayType {
		
		public Simple(final Vector3d shift, final EntityArchetype entity) {
			this.entity = Objects.requireNonNull(entity, "entity");
			this.shift = Objects.requireNonNull(shift, "shift");
		}

		@Override
		public Optional<Entity> create(final Entity owner) {
			return this.entity.apply(owner.serverLocation().add(this.shift));
		}
	}
}
