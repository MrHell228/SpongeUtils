package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.math.vector.Vector3i;

public interface SignalConductorBlockStateBehaviour
		extends BlockStateBehaviour<SignalConductorBlockStateBehaviour.Callback> {
	
	boolean apply(PrimitiveGameVolume volume, Vector3i position);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Boolean> {
		
		boolean apply(
			BlockStateExtension state, SignalConductorBlockStateBehaviour origin,
			PrimitiveGameVolume volume, Vector3i position
		);
	}
}
