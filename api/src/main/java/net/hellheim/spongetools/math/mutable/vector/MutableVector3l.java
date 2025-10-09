package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;
import org.spongepowered.math.vector.Vectorl;

public final class MutableVector3l implements Vectorl {
	
	private long x;
	private long y;
	private long z;
	
	public MutableVector3l(final double x, final double y, final double z) {
		this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l(final long x, final long y, final long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public long x() {
		return this.x;
	}
	
	public long y() {
		return this.y;
	}
	
	public long z() {
		return this.z;
	}
	
	public MutableVector3l copy() {
		return MutableVector3l.from(this);
	}
	
	public MutableVector3l x(final MutableVector3l v) {
		return this.x(v.x);
	}
	
	public MutableVector3l x(final Vector3l v) {
		return this.x(v.x());
	}
	
	public MutableVector3l x(final double a) {
		return this.x(GenericMath.floorl(a));
	}
	
	public MutableVector3l x(final long a) {
		this.x = a;
		return this;
	}
	
	public MutableVector3l y(final MutableVector3l v) {
		return this.y(v.y);
	}
	
	public MutableVector3l y(final Vector3l v) {
		return this.y(v.y());
	}
	
	public MutableVector3l y(final double a) {
		return this.y(GenericMath.floorl(a));
	}
	
	public MutableVector3l y(final long a) {
		this.y = a;
		return this;
	}
	
	public MutableVector3l z(final MutableVector3l v) {
		return this.z(v.z);
	}
	
	public MutableVector3l z(final Vector3l v) {
		return this.z(v.z());
	}
	
	public MutableVector3l z(final double a) {
		return this.z(GenericMath.floorl(a));
	}
	
	public MutableVector3l z(final long a) {
		this.z = a;
		return this;
	}
	
	public MutableVector3l set(final MutableVector3l v) {
		return this.set(v.x, v.y, v.z);
	}
	
	public MutableVector3l set(final Vector3l v) {
		return this.set(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l set(final double x, final double y, final double z) {
		return this.set(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l set(final long x, final long y, final long z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public MutableVector3l add(final MutableVector3l v) {
		return this.add(v.x, v.y, v.z);
	}
	
	public MutableVector3l add(final Vector3l v) {
		return this.add(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l add(final double x, final double y, final double z) {
		return this.add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l add(final long x, final long y, final long z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public MutableVector3l sub(final MutableVector3l v) {
		return this.sub(v.x, v.y, v.z);
	}
	
	public MutableVector3l sub(final Vector3l v) {
		return this.sub(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l sub(final double x, final double y, final double z) {
		return this.sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l sub(final long x, final long y, final long z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public MutableVector3l mul(final double a) {
		return this.mul(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector3l mul(final long a) {
		return this.mul(a, a, a);
	}
	
	public MutableVector3l mul(final MutableVector3l v) {
		return this.mul(v.x, v.y, v.z);
	}
	
	public MutableVector3l mul(final Vector3l v) {
		return this.mul(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l mul(final double x, final double y, final double z) {
		return this.mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l mul(final long x, final long y, final long z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	public MutableVector3l div(final double a) {
		return this.div(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector3l div(final long a) {
		return this.div(a, a, a);
	}
	
	public MutableVector3l div(final MutableVector3l v) {
		return this.div(v.x, v.y, v.z);
	}
	
	public MutableVector3l div(final Vector3l v) {
		return this.div(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l div(final double x, final double y, final double z) {
		return this.div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l div(final long x, final long y, final long z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public long dot(final MutableVector3l v) {
		return this.dot(v.x, v.y, v.z);
	}
	
	public long dot(final Vector3l v) {
		return this.dot(v.x(), v.y(), v.z());
	}
	
	public long dot(final double x, final double y, final double z) {
		return this.dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public long dot(final long x, final long y, final long z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	public MutableVector3l project(final MutableVector3l v) {
		return this.project(v.x, v.y, v.z);
	}
	
	public MutableVector3l project(final Vector3l v) {
		return this.project(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l project(final double x, final double y, final double z) {
		return this.project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l project(final long x, final long y, final long z) {
		final long lengthSquared = x * x + y * y + z * z;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final double a = (double) this.dot(x, y, z) / lengthSquared;
		return this.set(a * x, a * y, a * z);
	}
	
	public MutableVector3l cross(final MutableVector3l v) {
		return this.cross(v.x, v.y, v.z);
	}
	
	public MutableVector3l cross(final Vector3l v) {
		return this.cross(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l cross(final double x, final double y, final double z) {
		return this.cross(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l cross(final long x, final long y, final long z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}
	
	public MutableVector3l pow(final double pow) {
		return this.pow(GenericMath.floorl(pow));
	}
	
	@Override
	public MutableVector3l pow(final long pow) {
		return this.pow(pow, pow, pow);
	}
	
	public MutableVector3l pow(final MutableVector3l v) {
		return this.pow(v.x, v.y, v.z);
	}
	
	public MutableVector3l pow(final Vector3l v) {
		return this.pow(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l pow(final double x, final double y, final double z) {
		return this.pow(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l pow(final long x, final long y, final long z) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z));
	}
	
	@Override
	public MutableVector3l abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }
	
	@Override
	public MutableVector3l negate() {
		return this.set(-this.x, -this.y, -this.z);
	}
	
	public MutableVector3l min(final MutableVector3l v) {
		return this.min(v.x, v.y, v.z);
	}
	
	public MutableVector3l min(final Vector3l v) {
		return this.min(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l min(final double x, final double y, final double z) {
		return this.min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l min(final long x, final long y, final long z) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
	}
	
	public MutableVector3l max(final MutableVector3l v) {
		return this.max(v.x, v.y, v.z);
	}
	
	public MutableVector3l max(final Vector3l v) {
		return this.max(v.x(), v.y(), v.z());
	}
	
	public MutableVector3l max(final double x, final double y, final double z) {
		return this.max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public MutableVector3l max(final long x, final long y, final long z) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
	}
	
	public long distanceSquared(final MutableVector3l v) {
		return this.distanceSquared(v.x, v.y, v.z);
	}
	
	public long distanceSquared(final Vector3l v) {
		return this.distanceSquared(v.x(), v.y(), v.z());
	}
	
	public long distanceSquared(final double x, final double y, final double z) {
		return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public long distanceSquared(final long x, final long y, final long z) {
		final long dx = this.x - x;
		final long dy = this.y - y;
		final long dz = this.z - z;
		return dx * dx + dy * dy + dz * dz;
	}
	
	public double distance(final MutableVector3l v) {
		return this.distance(v.x, v.y, v.z);
	}
	
	public double distance(final Vector3l v) {
		return this.distance(v.x(), v.y(), v.z());
	}
	
	public double distance(final double x, final double y, final double z) {
		return this.distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z));
	}
	
	public double distance(final long x, final long y, final long z) {
		return Math.sqrt(this.distanceSquared(x, y, z));
	}
	
	@Override
	public long lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public int minAxis() {
		return this.x < this.y ? (this.x < this.z ? 0 : 2) : (this.y < this.z ? 1 : 2);
	}
	
	@Override
	public int maxAxis() {
		return this.x < this.y ? (this.y < this.z ? 2 : 1) : (this.x < this.z ? 2 : 0);
	}
	
	@Override
	public long[] toArray() {
		return new long[]{this.x, this.y, this.z};
	}
	
	@Override
	public Vector3i toInt() {
		return new Vector3i(this.x, this.y, this.z);
	}
	
	@Override
	public Vector3l toLong() {
		return new Vector3l(this.x, this.y, this.z);
	}
	
	@Override
	public Vector3f toFloat() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	@Override
	public Vector3d toDouble() {
		return new Vector3d((double) this.x, (double) this.y, (double) this.z);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final MutableVector3l that)) {
			return false;
		} else {
			return that.x == this.x
				&& that.y == this.y
				&& that.z == this.z;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Long.hashCode(this.x);
		result = result * 31 + Long.hashCode(this.y);
		result = result * 31 + Long.hashCode(this.z);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	public static MutableVector3l zero() {
		return MutableVector3l.of(0, 0, 0);
	}
	
	public static MutableVector3l unitX() {
		return MutableVector3l.of(1, 0, 0);
	}
	
	public static MutableVector3l unitY() {
		return MutableVector3l.of(0, 1, 0);
	}
	
	public static MutableVector3l unitZ() {
		return MutableVector3l.of(0, 0, 1);
	}
	
	public static MutableVector3l one() {
		return MutableVector3l.of(1, 1, 1);
	}
	
	public static  MutableVector3l from(final double n) {
		return MutableVector3l.from(GenericMath.floorl(n));
	}
	
	public static MutableVector3l from(final long n) {
		return MutableVector3l.of(n, n, n);
	}
	
	public static MutableVector3l from(final MutableVector3l v) {
		return MutableVector3l.of(v.x, v.y, v.z);
	}
	
	public static MutableVector3l from(final Vector3l v) {
		return MutableVector3l.of(v.x(), v.y(), v.z());
	}
	
	public static MutableVector3l of(final double x, final double y, final double z) {
		return new MutableVector3l(x, y, z);
	}
	
	public static MutableVector3l of(final long x, final long y, final long z) {
		return new MutableVector3l(x, y, z);
	}
}
