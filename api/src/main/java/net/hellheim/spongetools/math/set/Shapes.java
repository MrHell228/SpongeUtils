package net.hellheim.spongetools.math.set;

import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector3i;

public final class Shapes {
	
	public static Manhattan3 manhattan3(final Vector3i depth) {
		return Shapes.manhattan3(depth.x(), depth.y(), depth.z());
	}
	
	public static Manhattan3 manhattan3(final int depth) {
		return Shapes.manhattan3(depth, depth, depth);
	}
	
	public static Manhattan3 manhattan3(final int xDepth, final int yDepth, final int zDepth) {
		return new Manhattan3(xDepth, yDepth, zDepth);
	}
	
	public static Manhattan2 manhattan2(final Vector2i depth) {
		return Shapes.manhattan2(depth.x(), depth.y());
	}
	
	public static Manhattan2 manhattan2(final int depth) {
		return Shapes.manhattan2(depth, depth);
	}
	
	public static Manhattan2 manhattan2(final int xDepth, final int yDepth) {
		return new Manhattan2(xDepth, yDepth);
	}
	
	private Shapes() {
	}
}
