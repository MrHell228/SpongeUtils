package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

public interface ReplaceBlockStateBehaviour
		extends BlockStateBehaviour<ReplaceBlockStateBehaviour.Callback> {
	
	void apply(World<?, ?> world, Vector3i position, BlockState otherState, boolean movedByPiston);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Void> {
		
		void apply(
			BlockStateExtension state, ReplaceBlockStateBehaviour origin,
			World<?, ?> world, Vector3i position, BlockState otherState, boolean movedByPiston
		);
	}
}
