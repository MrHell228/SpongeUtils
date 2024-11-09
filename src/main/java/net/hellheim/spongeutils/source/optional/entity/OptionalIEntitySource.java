package net.hellheim.spongeutils.source.optional.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface OptionalIEntitySource<T extends Entity> {
	
	Optional<T> createEntity(World<?, ?> world, Vector3d position);
}
