package net.hellheim.spongetools.custom.behaviour.block.state;

import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.custom.behaviour.world.SignalOrientation;

@FunctionalInterface
public interface SignalUpdateBlockStateBehaviour
		extends BlockStateBehaviour<SignalUpdateBlockStateBehaviour.Callback> {
	
	void apply(
		World<?, ?> world, Vector3i position, BlockType notifier,
		Optional<SignalOrientation> orientation, boolean movedByPiston
	);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Void> {
		
		void apply(
			BlockStateExtension state, SignalUpdateBlockStateBehaviour origin,
			World<?, ?> world, Vector3i position, BlockType notifier,
			Optional<SignalOrientation> orientation, boolean movedByPiston
		);
	}
}
