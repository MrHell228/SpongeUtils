package net.hellheim.spongeutils.function;

import java.util.function.Consumer;

import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;

@FunctionalInterface
public interface IntTriConsumer {
	
	void accept(int i1, int i2, int i3);
	
	static IntTriConsumer fromVector3i(final Consumer<Vector3i> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3i(x, y, z));
	}
	
	static IntTriConsumer fromVector3l(final Consumer<Vector3l> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3l(x, y, z));
	}
	
	static IntTriConsumer fromVector3f(final Consumer<Vector3f> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3f(x, y, z));
	}
	
	static IntTriConsumer fromVector3d(final Consumer<Vector3d> consumer) {
		return (x, y, z) -> consumer.accept(new Vector3d(x, y, z));
	}
}
