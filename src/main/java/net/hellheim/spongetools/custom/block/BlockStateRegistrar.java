package net.hellheim.spongetools.custom.block;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.spongepowered.api.block.BlockState;

public final class BlockStateRegistrar {
	
	private static final Set<BlockState> OCCUPIED_STATES = new HashSet<>();
	
	public static boolean isOccupied(final BlockState state) {
		return BlockStateRegistrar.OCCUPIED_STATES.contains(Objects.requireNonNull(state, "state"));
	}
	
	public static boolean isAvailable(final BlockState state) {
		return !BlockStateRegistrar.isOccupied(state);
	}
	
	public static void register(final BlockState state) {
		if (BlockStateRegistrar.isOccupied(state)) {
			throw new IllegalArgumentException("State already occupied: " + state.asString());
		}
		
		BlockStateRegistrar.OCCUPIED_STATES.add(state);
	}
	
	private BlockStateRegistrar() {
	}
}
