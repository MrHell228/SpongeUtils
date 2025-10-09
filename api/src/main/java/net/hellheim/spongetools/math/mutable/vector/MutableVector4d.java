package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4d;
import org.spongepowered.math.vector.Vector4f;
import org.spongepowered.math.vector.Vector4i;
import org.spongepowered.math.vector.Vector4l;
import org.spongepowered.math.vector.Vectord;

public final class MutableVector4d implements Vectord {
	
	private double x;
	private double y;
	private double z;
	private double w;
	
	public MutableVector4d(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public double x() {
		return this.x;
	}
	
	public double y() {
		return this.y;
	}
	
	public double z() {
		return this.z;
	}
	
	public double w() {
		return this.w;
	}
	
	public MutableVector4d copy() {
		return MutableVector4d.from(this);
	}
	
	public MutableVector4d x(final MutableVector4d v) {
		return this.x(v.x);
	}
	
	public MutableVector4d x(final Vector4d v) {
		return this.x(v.x());
	}
	
	public MutableVector4d x(final double a) {
		this.x = a;
		return this;
	}
	
	public MutableVector4d y(final MutableVector4d v) {
		return this.y(v.y);
	}
	
	public MutableVector4d y(final Vector4d v) {
		return this.y(v.y());
	}
	
	public MutableVector4d y(final double a) {
		this.y = a;
		return this;
	}
	
	public MutableVector4d z(final MutableVector4d v) {
		return this.z(v.z);
	}
	
	public MutableVector4d z(final Vector4d v) {
		return this.z(v.z());
	}
	
	public MutableVector4d z(final double a) {
		this.z = a;
		return this;
	}
	
	public MutableVector4d w(final MutableVector4d v) {
		return this.w(v.z);
	}
	
	public MutableVector4d w(final Vector4d v) {
		return this.w(v.z());
	}
	
	public MutableVector4d w(final double a) {
		this.w = a;
		return this;
	}
	
	public MutableVector4d set(final MutableVector4d v) {
		return this.set(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d set(final Vector4d v) {
		return this.set(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d set(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public MutableVector4d add(final MutableVector4d v) {
		return this.add(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d add(final Vector4d v) {
		return this.add(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d add(final double x, final double y, final double z, final double w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}
	
	public MutableVector4d sub(final MutableVector4d v) {
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d sub(final Vector4d v) {
		return this.sub(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d sub(final double x, final double y, final double z, final double w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}
	
	@Override
	public MutableVector4d mul(final double a) {
		return this.mul(a, a, a, a);
	}
	
	public MutableVector4d mul(final MutableVector4d v) {
		return this.mul(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d mul(final Vector4d v) {
		return this.mul(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d mul(final double x, final double y, final double z, final double w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}
	
	@Override
	public MutableVector4d div(final double a) {
		return this.div(a, a, a, a);
	}
	
	public MutableVector4d div(final MutableVector4d v) {
		return this.div(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d div(final Vector4d v) {
		return this.div(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d div(final double x, final double y, final double z, final double w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;
		return this;
	}
	
	public double dot(final MutableVector4d v) {
		return this.dot(v.x, v.y, v.z, v.w);
	}
	
	public double dot(final Vector4d v) {
		return this.dot(v.x(), v.y(), v.z(), v.w());
	}
	
	public double dot(final double x, final double y, final double z, final double w) {
		return this.x * x + this.y * y + this.z * z + this.w * w;
	}
	
	public MutableVector4d project(final MutableVector4d v) {
		return this.project(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d project(final Vector4d v) {
		return this.project(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d project(final double x, final double y, final double z, final double w) {
		final double lengthSquared = x * x + y * y + z * z + w * w;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final double a = this.dot(x, y, z, w) / lengthSquared;
		return this.set(a * x, a * y, a * z, a * w);
	}
	
	@Override
	public MutableVector4d pow(final double pow) {
		return this.pow(pow, pow, pow, pow);
	}
	
	public MutableVector4d pow(final MutableVector4d v) {
		return this.pow(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d pow(final Vector4d v) {
		return this.pow(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d pow(final double x, final double y, final double z, final double w) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z), Math.pow(this.w, w));
	}
	
	@Override
	public MutableVector4d ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z), Math.ceil(this.w));
	}
	
	@Override
	public MutableVector4d floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z), GenericMath.floor(this.w));
	}
	
	@Override
	public MutableVector4d round() {
		return this.set(Math.round(this.x), Math.round(this.y), Math.round(this.z), Math.round(this.w));
	}
	
	@Override
	public MutableVector4d abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }
	
	@Override
	public MutableVector4d negate() {
		return this.set(-this.x, -this.y, -this.z, -this.w);
	}
	
	public MutableVector4d min(final MutableVector4d v) {
		return this.min(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d min(final Vector4d v) {
		return this.min(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d min(final double x, final double y, final double z, final double w) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
	}
	
	public MutableVector4d max(final MutableVector4d v) {
		return this.max(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4d max(final Vector4d v) {
		return this.max(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4d max(final double x, final double y, final double z, final double w) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
	}
	
	public double distanceSquared(final MutableVector4d v) {
		return this.distanceSquared(v.x, v.y, v.z, v.w);
	}
	
	public double distanceSquared(final Vector4d v) {
		return this.distanceSquared(v.x(), v.y(), v.z(), v.w());
	}
	
	public double distanceSquared(final double x, final double y, final double z, final double w) {
		final double dx = this.x - x;
		final double dy = this.y - y;
		final double dz = this.z - z;
		final double dw = this.w - w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public double distance(final MutableVector4d v) {
		return this.distance(v.x, v.y, v.z, v.w);
	}
	
	public double distance(final Vector4d v) {
		return this.distance(v.x(), v.y(), v.z(), v.w());
	}
	
	public double distance(final double x, final double y, final double z, final double w) {
		return Math.sqrt(this.distanceSquared(x, y, z, w));
	}
	
	@Override
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector4d normalize() {
		final double length = this.length();
		if (Math.abs(length) < GenericMath.FLT_EPSILON) {
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return this.set(this.x / length, this.y / length, this.z / length, this.w / length);
	}
	
	@Override
	public int minAxis() {
		double value = this.x;
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
		double value = this.x;
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
	public double[] toArray() {
		return new double[]{this.x, this.y, this.z, this.w};
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
		} else if (!(other instanceof final MutableVector4d that)) {
			return false;
		} else {
			return Double.compare(that.x, this.x) == 0
				&& Double.compare(that.y, this.y) == 0
				&& Double.compare(that.z, this.z) == 0
				&& Double.compare(that.w, this.w) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Double.hashCode(this.x);
		result = result * 31 + Double.hashCode(this.y);
		result = result * 31 + Double.hashCode(this.z);
		result = result * 31 + Double.hashCode(this.w);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
	}
	
	public static MutableVector4d zero() {
		return MutableVector4d.of(0, 0, 0, 0);
	}
	
	public static MutableVector4d unitX() {
		return MutableVector4d.of(1, 0, 0, 0);
	}
	
	public static MutableVector4d unitY() {
		return MutableVector4d.of(0, 1, 0, 0);
	}
	
	public static MutableVector4d unitZ() {
		return MutableVector4d.of(0, 0, 1, 0);
	}
	
	public static MutableVector4d unitW() {
		return MutableVector4d.of(0, 0, 0, 1);
	}
	
	public static MutableVector4d one() {
		return MutableVector4d.of(1, 1, 1, 1);
	}
	
	public static  MutableVector4d from(final double n) {
		return MutableVector4d.of(n, n, n, n);
	}
	
	public static MutableVector4d from(final MutableVector4d v) {
		return MutableVector4d.of(v.x, v.y, v.z, v.w);
	}
	
	public static MutableVector4d from(final Vector4d v) {
		return MutableVector4d.of(v.x(), v.y(), v.z(), v.w());
	}
	
	public static MutableVector4d of(final double x, final double y, final double z, final double w) {
		return new MutableVector4d(x, y, z, w);
	}
}
