package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.volume.game.PrimitiveGameVolume;
import org.spongepowered.math.vector.Vector3i;

public interface SignalPowerBlockStateBehaviour
		extends BlockStateBehaviour<SignalPowerBlockStateBehaviour.Callback> {
	
	int apply(PrimitiveGameVolume volume, Vector3i position, Direction direction);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Integer> {
		
		int apply(
			BlockStateExtension state, SignalPowerBlockStateBehaviour origin,
			PrimitiveGameVolume volume, Vector3i position, Direction direction
		);
	}
}
