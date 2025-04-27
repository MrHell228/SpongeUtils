package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.math.vector.Vector3i;

@FunctionalInterface
public interface SpawnValidatorBlockStateBehaviour
		extends BlockStateBehaviour<SpawnValidatorBlockStateBehaviour.Callback> {
	
	boolean apply(PrimitiveGameVolume volume, Vector3i position, EntityType<?> entity);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Boolean> {
		
		boolean apply(
			BlockStateExtension state, SpawnValidatorBlockStateBehaviour origin,
			PrimitiveGameVolume volume, Vector3i position, EntityType<?> entity
		);
	}
}
