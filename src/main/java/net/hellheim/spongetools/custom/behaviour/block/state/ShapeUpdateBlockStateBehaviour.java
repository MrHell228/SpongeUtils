package net.hellheim.spongetools.custom.behaviour.block.state;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.volume.game.Region;
import org.spongepowered.api.world.volume.game.UpdatableVolume;
import org.spongepowered.math.vector.Vector3i;

@FunctionalInterface
public interface ShapeUpdateBlockStateBehaviour
		extends BlockStateBehaviour<ShapeUpdateBlockStateBehaviour.Callback> {
	
	BlockState apply(
		Region<?> region, UpdatableVolume volume, Vector3i position,
		Direction direction, Vector3i neighbourPosition, BlockState neighbourState,
		RandomProvider.Source random
	);
	
	@FunctionalInterface
	interface Callback extends BlockStateBehaviour.Callback<BlockState> {
		
		BlockState apply(
			BlockStateExtension state, ShapeUpdateBlockStateBehaviour origin,
			Region<?> region, UpdatableVolume volume, Vector3i position,
			Direction direction, Vector3i neighbourPosition, BlockState neighbourState,
			RandomProvider.Source random
		);
	}
}
