package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.math.vector.Vector3i;

@FunctionalInterface
public interface MapColorBlockStateBehaviour
		extends BlockStateBehaviour<MapColorBlockStateBehaviour.Callback> {
	
	MapColorType apply(PrimitiveGameVolume volume, Vector3i position);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<MapColorType> {
		
		MapColorType apply(
			BlockStateExtension state, MapColorBlockStateBehaviour origin,
			PrimitiveGameVolume volume, Vector3i position
		);
	}
}
