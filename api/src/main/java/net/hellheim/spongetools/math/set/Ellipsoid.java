package net.hellheim.spongetools.math.set;

import java.util.random.RandomGenerator;

import com.google.common.base.Preconditions;

import net.hellheim.spongetools.math.mutable.vector.MutableVector3i;
import net.hellheim.spongetools.object.Streamable;

public class Ellipsoid implements CountableVectorSet.Vec3i {
	
	private final int xRad;
	private final int yRad;
	private final int zRad;
	
	private final int xRad2;
	private final int yRad2;
	private final double xMul;
	
	protected Ellipsoid(final int xRadius, final int yRadius, final int zRadius) {
		Preconditions.checkArgument(xRadius > 0, "xRadius must be positive");
		Preconditions.checkArgument(yRadius > 0, "yRadius must be positive");
		Preconditions.checkArgument(zRadius > 0, "zRadius must be positive");
		this.xRad = xRadius;
		this.yRad = yRadius;
		this.zRad = zRadius;
		
		this.xRad2 = xRadius * xRadius;
		this.yRad2 = yRadius * yRadius;
		this.xMul = ((double) this.yRad2) / this.xRad2;
	}

	@Override
	public Streamable<MutableVector3i> all() {
		return null;
	}

	@Override
	public Streamable<MutableVector3i> random(RandomGenerator random) {
		// TODO Auto-generated method stub
		return null;
	}
}
