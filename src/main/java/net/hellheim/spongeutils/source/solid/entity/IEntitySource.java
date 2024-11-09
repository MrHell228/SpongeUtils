package net.hellheim.spongeutils.source.solid.entity;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;

public interface IEntitySource<T extends Entity> {
	
	T createEntity(World<?, ?> world, Vector3d position);
}
