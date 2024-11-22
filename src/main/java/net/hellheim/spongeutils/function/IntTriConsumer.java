package net.hellheim.spongeutils.function;

import java.util.function.Consumer;

import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

@FunctionalInterface
public interface IntTriConsumer {
	
	void accept(int i1, int i2, int i3);
	
	static IntTriConsumer fromVector3i(final Consumer<Vector3i> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3i(x, y, z));
	}
	
	static IntTriConsumer fromVector3d(final Consumer<Vector3d> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3d(x, y, z));
	}
}
