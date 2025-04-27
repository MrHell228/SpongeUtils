package net.hellheim.spongetools.custom.behaviour.world;

import java.util.Objects;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

public interface WorldExtension<W extends World<?, ?>> {
	
	static <W extends World<?, ?>> WorldExtension<W> getFor(final W world) {
		@SuppressWarnings("unchecked")
		final WorldExtension<W> extension = (WorldExtension<W>) world;
		return extension;
	}
	
	default W owner() {
		@SuppressWarnings("unchecked")
		final W world = (W) this;
		return world;
	}
	
	void updateAt(int x , int y, int z, BlockType notifier);
	
	default void updateAt(final Vector3i position, final BlockType notifier) {
		Objects.requireNonNull(position, "position");
		this.updateAt(position.x(), position.y(), position.z(), notifier);
	}
	
	void updateAround(int x, int y, int z, BlockType notifier);
	
	default void updateAround(final Vector3i position, final BlockType notifier) {
		Objects.requireNonNull(position, "position");
		this.updateAround(position.x(), position.y(), position.z(), notifier);
	}
	
	void updateAroundExcept(int x, int y, int z, BlockType notifier, Direction direction);
	
	default void updateAroundExcept(final Vector3i position, final BlockType notifier, final Direction direction) {
		Objects.requireNonNull(position, "position");
		this.updateAroundExcept(position.x(), position.y(), position.z(), notifier, direction);
	}
}
