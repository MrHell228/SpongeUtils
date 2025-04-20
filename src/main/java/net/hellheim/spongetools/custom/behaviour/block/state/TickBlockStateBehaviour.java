package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3i;

public interface TickBlockStateBehaviour
		extends BlockStateBehaviour<TickBlockStateBehaviour.Callback> {
	
	void apply(ServerWorld world, Vector3i position, RandomProvider.Source random);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<Void> {
		
		void apply(
			BlockStateExtension state, TickBlockStateBehaviour origin,
			ServerWorld world, Vector3i position, RandomProvider.Source random
		);
	}
}
