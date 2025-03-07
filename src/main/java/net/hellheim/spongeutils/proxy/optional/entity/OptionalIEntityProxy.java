package net.hellheim.spongeutils.proxy.optional.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface OptionalIEntityProxy<T extends Entity> {
	
	Optional<T> createEntity(World<?, ?> world, Vector3d position);
}
