package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

@FunctionalInterface
public interface AnalogSignalPowerBlockStateBehaviour
		extends BlockStateBehaviour<AnalogSignalPowerBlockStateBehaviour.Callback> {
	
	int apply(World<?, ?> world, Vector3i position);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Integer> {
		
		int apply(
			BlockStateExtension state, AnalogSignalPowerBlockStateBehaviour origin,
			World<?, ?> world, Vector3i position
		);
	}
}
