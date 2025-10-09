package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4d;
import org.spongepowered.math.vector.Vector4f;
import org.spongepowered.math.vector.Vector4i;
import org.spongepowered.math.vector.Vector4l;
import org.spongepowered.math.vector.Vectorl;

public final class MutableVector4l implements Vectorl {
	
	private long x;
	private long y;
	private long z;
	private long w;
	
	public MutableVector4l(final double x, final double y, final double z, final double w) {
		this(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l(final long x, final long y, final long z, final long w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public long w() {
		return this.w;
	}
	
	public MutableVector4l copy() {
		return MutableVector4l.from(this);
	}
	
	public MutableVector4l x(final MutableVector4l v) {
		return this.x(v.x);
	}
	
	public MutableVector4l x(final Vector4l v) {
		return this.x(v.x());
	}
	
	public MutableVector4l x(final double a) {
		return this.x(GenericMath.floorl(a));
	}
	
	public MutableVector4l x(final long a) {
		this.x = a;
		return this;
	}
	
	public MutableVector4l y(final MutableVector4l v) {
		return this.y(v.y);
	}
	
	public MutableVector4l y(final Vector4l v) {
		return this.y(v.y());
	}
	
	public MutableVector4l y(final double a) {
		return this.y(GenericMath.floorl(a));
	}
	
	public MutableVector4l y(final long a) {
		this.y = a;
		return this;
	}
	
	public MutableVector4l z(final MutableVector4l v) {
		return this.z(v.z);
	}
	
	public MutableVector4l z(final Vector4l v) {
		return this.z(v.z());
	}
	
	public MutableVector4l z(final double a) {
		return this.z(GenericMath.floorl(a));
	}
	
	public MutableVector4l z(final long a) {
		this.z = a;
		return this;
	}
	
	public MutableVector4l w(final MutableVector4l v) {
		return this.w(v.z);
	}
	
	public MutableVector4l w(final Vector4l v) {
		return this.w(v.z());
	}
	
	public MutableVector4l w(final double a) {
		return this.w(GenericMath.floorl(a));
	}
	
	public MutableVector4l w(final long a) {
		this.w = a;
		return this;
	}
	
	public MutableVector4l set(final MutableVector4l v) {
		return this.set(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l set(final Vector4l v) {
		return this.set(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l set(final double x, final double y, final double z, final double w) {
		return this.set(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l set(final long x, final long y, final long z, final long w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public MutableVector4l add(final MutableVector4l v) {
		return this.add(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l add(final Vector4l v) {
		return this.add(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l add(final double x, final double y, final double z, final double w) {
		return this.add(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l add(final long x, final long y, final long z, final long w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}
	
	public MutableVector4l sub(final MutableVector4l v) {
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l sub(final Vector4l v) {
		return this.sub(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l sub(final double x, final double y, final double z, final double w) {
		return this.sub(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l sub(final long x, final long y, final long z, final long w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}
	
	public MutableVector4l mul(final double a) {
		return this.mul(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector4l mul(final long a) {
		return this.mul(a, a, a, a);
	}
	
	public MutableVector4l mul(final MutableVector4l v) {
		return this.mul(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l mul(final Vector4l v) {
		return this.mul(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l mul(final double x, final double y, final double z, final double w) {
		return this.mul(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l mul(final long x, final long y, final long z, final long w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}
	
	public MutableVector4l div(final double a) {
		return this.div(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector4l div(final long a) {
		return this.div(a, a, a, a);
	}
	
	public MutableVector4l div(final MutableVector4l v) {
		return this.div(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l div(final Vector4l v) {
		return this.div(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l div(final double x, final double y, final double z, final double w) {
		return this.div(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l div(final long x, final long y, final long z, final long w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;
		return this;
	}
	
	public long dot(final MutableVector4l v) {
		return this.dot(v.x, v.y, v.z, v.w);
	}
	
	public long dot(final Vector4l v) {
		return this.dot(v.x(), v.y(), v.z(), v.w());
	}
	
	public long dot(final double x, final double y, final double z, final double w) {
		return this.dot(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public long dot(final long x, final long y, final long z, final long w) {
		return this.x * x + this.y * y + this.z * z + this.w * w;
	}
	
	public MutableVector4l project(final MutableVector4l v) {
		return this.project(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l project(final Vector4l v) {
		return this.project(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l project(final double x, final double y, final double z, final double w) {
		return this.project(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l project(final long x, final long y, final long z, final long w) {
		final long lengthSquared = x * x + y * y + z * z + w * w;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final double a = (double) this.dot(x, y, z, w) / lengthSquared;
		return this.set(a * x, a * y, a * z, a * w);
	}
	
	public MutableVector4l pow(final double pow) {
		return this.pow(GenericMath.floorl(pow));
	}
	
	@Override
	public MutableVector4l pow(final long pow) {
		return this.pow(pow, pow, pow, pow);
	}
	
	public MutableVector4l pow(final MutableVector4l v) {
		return this.pow(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l pow(final Vector4l v) {
		return this.pow(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l pow(final double x, final double y, final double z, final double w) {
		return this.pow(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l pow(final long x, final long y, final long z, final long w) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z), Math.pow(this.w, w));
	}
	
	@Override
	public MutableVector4l abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }
	
	@Override
	public MutableVector4l negate() {
		return this.set(-this.x, -this.y, -this.z, -this.w);
	}
	
	public MutableVector4l min(final MutableVector4l v) {
		return this.min(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l min(final Vector4l v) {
		return this.min(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l min(final double x, final double y, final double z, final double w) {
		return this.min(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l min(final long x, final long y, final long z, final long w) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
	}
	
	public MutableVector4l max(final MutableVector4l v) {
		return this.max(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4l max(final Vector4l v) {
		return this.max(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4l max(final double x, final double y, final double z, final double w) {
		return this.max(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public MutableVector4l max(final long x, final long y, final long z, final long w) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
	}
	
	public long distanceSquared(final MutableVector4l v) {
		return this.distanceSquared(v.x, v.y, v.z, v.w);
	}
	
	public long distanceSquared(final Vector4l v) {
		return this.distanceSquared(v.x(), v.y(), v.z(), v.w());
	}
	
	public long distanceSquared(final double x, final double y, final double z, final double w) {
		return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public long distanceSquared(final long x, final long y, final long z, final long w) {
		final long dx = this.x - x;
		final long dy = this.y - y;
		final long dz = this.z - z;
		final long dw = this.w - w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public double distance(final MutableVector4l v) {
		return this.distance(v.x, v.y, v.z, v.w);
	}
	
	public double distance(final Vector4l v) {
		return this.distance(v.x(), v.y(), v.z(), v.w());
	}
	
	public double distance(final double x, final double y, final double z, final double w) {
		return this.distance(GenericMath.floorl(x), GenericMath.floorl(y), GenericMath.floorl(z), GenericMath.floorl(w));
	}
	
	public double distance(final long x, final long y, final long z, final long w) {
		return Math.sqrt(this.distanceSquared(x, y, z, w));
	}
	
	@Override
	public long lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public int minAxis() {
		long value = this.x;
		int axis = 0;
		if (this.y < value) {
			value = this.y;
			axis = 1;
		}
		if (this.z < value) {
			value = this.z;
			axis = 2;
		}
		if (this.w < value) {
			axis = 3;
		}
		return axis;
	}
	
	@Override
	public int maxAxis() {
		long value = this.x;
		int axis = 0;
		if (this.y > value) {
			value = this.y;
			axis = 1;
		}
		if (this.z > value) {
			value = this.z;
			axis = 2;
		}
		if (this.w > value) {
			axis = 3;
		}
		return axis;
	}
	
	@Override
	public long[] toArray() {
		return new long[]{this.x, this.y, this.z, this.w};
	}
	
	@Override
	public Vector4i toInt() {
		return new Vector4i(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4l toLong() {
		return new Vector4l(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4f toFloat() {
		return new Vector4f(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public Vector4d toDouble() {
		return new Vector4d((double) this.x, (double) this.y, (double) this.z, (double) this.w);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final MutableVector4l that)) {
			return false;
		} else {
			return that.x == this.x
				&& that.y == this.y
				&& that.z == this.z
				&& that.w == this.w;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Long.hashCode(this.x);
		result = result * 31 + Long.hashCode(this.y);
		result = result * 31 + Long.hashCode(this.z);
		result = result * 31 + Long.hashCode(this.w);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
	}
	
	public static MutableVector4l zero() {
		return MutableVector4l.of(0, 0, 0, 0);
	}
	
	public static MutableVector4l unitX() {
		return MutableVector4l.of(1, 0, 0, 0);
	}
	
	public static MutableVector4l unitY() {
		return MutableVector4l.of(0, 1, 0, 0);
	}
	
	public static MutableVector4l unitZ() {
		return MutableVector4l.of(0, 0, 1, 0);
	}
	
	public static MutableVector4l unitW() {
		return MutableVector4l.of(0, 0, 0, 1);
	}
	
	public static MutableVector4l one() {
		return MutableVector4l.of(1, 1, 1, 1);
	}
	
	public static  MutableVector4l from(final double n) {
		return MutableVector4l.from(GenericMath.floorl(n));
	}
	
	public static MutableVector4l from(final long n) {
		return MutableVector4l.of(n, n, n, n);
	}
	
	public static MutableVector4l from(final MutableVector4l v) {
		return MutableVector4l.of(v.x, v.y, v.z, v.w);
	}
	
	public static MutableVector4l from(final Vector4l v) {
		return MutableVector4l.of(v.x(), v.y(), v.z(), v.w());
	}
	
	public static MutableVector4l of(final double x, final double y, final double z, final double w) {
		return new MutableVector4l(x, y, z, w);
	}
	
	public static MutableVector4l of(final long x, final long y, final long z, final long w) {
		return new MutableVector4l(x, y, z, w);
	}
}
