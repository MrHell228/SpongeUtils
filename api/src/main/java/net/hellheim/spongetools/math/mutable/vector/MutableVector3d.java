package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;
import org.spongepowered.math.vector.Vectord;

public final class MutableVector3d implements Vectord {
	
	private double x;
	private double y;
	private double z;
	
	public MutableVector3d(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public MutableVector3d copy() {
		return MutableVector3d.from(this);
	}
	
	public MutableVector3d x(final MutableVector3d v) {
		return this.x(v.x);
	}
	
	public MutableVector3d x(final Vector3d v) {
		return this.x(v.x());
	}
	
	public MutableVector3d x(final double a) {
		this.x = a;
		return this;
	}
	
	public MutableVector3d y(final MutableVector3d v) {
		return this.y(v.y);
	}
	
	public MutableVector3d y(final Vector3d v) {
		return this.y(v.y());
	}
	
	public MutableVector3d y(final double a) {
		this.y = a;
		return this;
	}
	
	public MutableVector3d z(final MutableVector3d v) {
		return this.z(v.z);
	}
	
	public MutableVector3d z(final Vector3d v) {
		return this.z(v.z());
	}
	
	public MutableVector3d z(final double a) {
		this.z = a;
		return this;
	}
	
	public MutableVector3d set(final MutableVector3d v) {
		return this.set(v.x, v.y, v.z);
	}
	
	public MutableVector3d set(final Vector3d v) {
		return this.set(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public MutableVector3d add(final MutableVector3d v) {
		return this.add(v.x, v.y, v.z);
	}
	
	public MutableVector3d add(final Vector3d v) {
		return this.add(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public MutableVector3d sub(final MutableVector3d v) {
		return this.sub(v.x, v.y, v.z);
	}
	
	public MutableVector3d sub(final Vector3d v) {
		return this.sub(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d sub(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	@Override
	public MutableVector3d mul(final double a) {
		return this.mul(a, a, a);
	}
	
	public MutableVector3d mul(final MutableVector3d v) {
		return this.mul(v.x, v.y, v.z);
	}
	
	public MutableVector3d mul(final Vector3d v) {
		return this.mul(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d mul(final double x, final double y, final double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	@Override
	public MutableVector3d div(final double a) {
		return this.div(a, a, a);
	}
	
	public MutableVector3d div(final MutableVector3d v) {
		return this.div(v.x, v.y, v.z);
	}
	
	public MutableVector3d div(final Vector3d v) {
		return this.div(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d div(final double x, final double y, final double z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public double dot(final MutableVector3d v) {
		return this.dot(v.x, v.y, v.z);
	}
	
	public double dot(final Vector3d v) {
		return this.dot(v.x(), v.y(), v.z());
	}
	
	public double dot(final double x, final double y, final double z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	public MutableVector3d project(final MutableVector3d v) {
		return this.project(v.x, v.y, v.z);
	}
	
	public MutableVector3d project(final Vector3d v) {
		return this.project(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d project(final double x, final double y, final double z) {
		final double lengthSquared = x * x + y * y + z * z;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final double a = this.dot(x, y, z) / lengthSquared;
		return this.set(a * x, a * y, a * z);
	}
	
	public MutableVector3d cross(final MutableVector3d v) {
		return this.cross(v.x, v.y, v.z);
	}
	
	public MutableVector3d cross(final Vector3d v) {
		return this.cross(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d cross(final double x, final double y, final double z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}
	
	@Override
	public MutableVector3d pow(final double pow) {
		return this.pow(pow, pow, pow);
	}
	
	public MutableVector3d pow(final MutableVector3d v) {
		return this.pow(v.x, v.y, v.z);
	}
	
	public MutableVector3d pow(final Vector3d v) {
		return this.pow(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d pow(final double x, final double y, final double z) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z));
	}
	
	@Override
	public MutableVector3d ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
	}
	
	@Override
	public MutableVector3d floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z));
	}
	
	@Override
	public MutableVector3d round() {
		return this.set(Math.round(this.x), Math.round(this.y), Math.round(this.z));
	}
	
	@Override
	public MutableVector3d abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }
	
	@Override
	public MutableVector3d negate() {
		return this.set(-this.x, -this.y, -this.z);
	}
	
	public MutableVector3d min(final MutableVector3d v) {
		return this.min(v.x, v.y, v.z);
	}
	
	public MutableVector3d min(final Vector3d v) {
		return this.min(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d min(final double x, final double y, final double z) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
	}
	
	public MutableVector3d max(final MutableVector3d v) {
		return this.max(v.x, v.y, v.z);
	}
	
	public MutableVector3d max(final Vector3d v) {
		return this.max(v.x(), v.y(), v.z());
	}
	
	public MutableVector3d max(final double x, final double y, final double z) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
	}
	
	public double distanceSquared(final MutableVector3d v) {
		return this.distanceSquared(v.x, v.y, v.z);
	}
	
	public double distanceSquared(final Vector3d v) {
		return this.distanceSquared(v.x(), v.y(), v.z());
	}
	
	public double distanceSquared(final double x, final double y, final double z) {
		final double dx = this.x - x;
		final double dy = this.y - y;
		final double dz = this.z - z;
		return dx * dx + dy * dy + dz * dz;
	}
	
	public double distance(final MutableVector3d v) {
		return this.distance(v.x, v.y, v.z);
	}
	
	public double distance(final Vector3d v) {
		return this.distance(v.x(), v.y(), v.z());
	}
	
	public double distance(final double x, final double y, final double z) {
		return Math.sqrt(this.distanceSquared(x, y, z));
	}
	
	@Override
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector3d normalize() {
		final double length = this.length();
		if (Math.abs(length) < GenericMath.FLT_EPSILON) {
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return this.set(this.x / length, this.y / length, this.z / length);
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
	public double[] toArray() {
		return new double[]{this.x, this.y, this.z};
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
		} else if (!(other instanceof final MutableVector3d that)) {
			return false;
		} else {
			return Double.compare(that.x, this.x) == 0
				&& Double.compare(that.y, this.y) == 0
				&& Double.compare(that.z, this.z) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Double.hashCode(this.x);
		result = result * 31 + Double.hashCode(this.y);
		result = result * 31 + Double.hashCode(this.z);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	public static MutableVector3d zero() {
		return MutableVector3d.of(0, 0, 0);
	}
	
	public static MutableVector3d unitX() {
		return MutableVector3d.of(1, 0, 0);
	}
	
	public static MutableVector3d unitY() {
		return MutableVector3d.of(0, 1, 0);
	}
	
	public static MutableVector3d unitZ() {
		return MutableVector3d.of(0, 0, 1);
	}
	
	public static MutableVector3d one() {
		return MutableVector3d.of(1, 1, 1);
	}
	
	public static  MutableVector3d from(final double n) {
		return MutableVector3d.of(n, n, n);
	}
	
	public static MutableVector3d from(final MutableVector3d v) {
		return MutableVector3d.of(v.x, v.y, v.z);
	}
	
	public static MutableVector3d from(final Vector3d v) {
		return MutableVector3d.of(v.x(), v.y(), v.z());
	}
	
	public static MutableVector3d of(final double x, final double y, final double z) {
		return new MutableVector3d(x, y, z);
	}
}
