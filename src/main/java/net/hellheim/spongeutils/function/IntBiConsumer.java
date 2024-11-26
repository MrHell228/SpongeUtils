package net.hellheim.spongeutils.function;

import java.util.function.Consumer;

import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;

@FunctionalInterface
public interface IntBiConsumer {
	
	void accept(int i1, int i2);
	
	static IntBiConsumer fromVector2i(final Consumer<Vector2i> consumer) {
		return (x, y) -> consumer.accept(new Vector2i(x, y));
	}
	
	static IntBiConsumer fromVector2l(final Consumer<Vector2l> consumer) {
		return (x, y) -> consumer.accept(new Vector2l(x, y));
	}
	
	static IntBiConsumer fromVector2f(final Consumer<Vector2f> consumer) {
		return (x, y) -> consumer.accept(new Vector2f(x, y));
	}
	
	static IntBiConsumer fromVector2d(final Consumer<Vector2d> consumer) {
		return (x, y) -> consumer.accept(new Vector2d(x, y));
	}
}
