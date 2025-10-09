package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;
import org.spongepowered.math.vector.Vectorf;

public final class MutableVector3f implements Vectorf {
	
	private float x;
	private float y;
	private float z;
	
	public MutableVector3f(final double x, final double y, final double z) {
		this((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}
	
	public float z() {
		return this.z;
	}
	
	public MutableVector3f copy() {
		return MutableVector3f.from(this);
	}
	
	public MutableVector3f x(final MutableVector3f v) {
		return this.x(v.x);
	}
	
	public MutableVector3f x(final Vector3f v) {
		return this.x(v.x());
	}
	
	public MutableVector3f x(final double a) {
		return this.x((float) a);
	}
	
	public MutableVector3f x(final float a) {
		this.x = a;
		return this;
	}
	
	public MutableVector3f y(final MutableVector3f v) {
		return this.y(v.y);
	}
	
	public MutableVector3f y(final Vector3f v) {
		return this.y(v.y());
	}
	
	public MutableVector3f y(final double a) {
		return this.y((float) a);
	}
	
	public MutableVector3f y(final float a) {
		this.y = a;
		return this;
	}
	
	public MutableVector3f z(final MutableVector3f v) {
		return this.z(v.z);
	}
	
	public MutableVector3f z(final Vector3f v) {
		return this.z(v.z());
	}
	
	public MutableVector3f z(final double a) {
		return this.z((float) a);
	}
	
	public MutableVector3f z(final float a) {
		this.z = a;
		return this;
	}
	
	public MutableVector3f set(final MutableVector3f v) {
		return this.set(v.x, v.y, v.z);
	}
	
	public MutableVector3f set(final Vector3f v) {
		return this.set(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f set(final double x, final double y, final double z) {
		return this.set((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f set(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public MutableVector3f add(final MutableVector3f v) {
		return this.add(v.x, v.y, v.z);
	}
	
	public MutableVector3f add(final Vector3f v) {
		return this.add(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f add(final double x, final double y, final double z) {
		return this.add((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f add(final float x, final float y, final float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public MutableVector3f sub(final MutableVector3f v) {
		return this.sub(v.x, v.y, v.z);
	}
	
	public MutableVector3f sub(final Vector3f v) {
		return this.sub(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f sub(final double x, final double y, final double z) {
		return this.sub((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f sub(final float x, final float y, final float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public MutableVector3f mul(final double a) {
		return this.mul((float) a);
	}
	
	@Override
	public MutableVector3f mul(final float a) {
		return this.mul(a, a, a);
	}
	
	public MutableVector3f mul(final MutableVector3f v) {
		return this.mul(v.x, v.y, v.z);
	}
	
	public MutableVector3f mul(final Vector3f v) {
		return this.mul(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f mul(final double x, final double y, final double z) {
		return this.mul((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f mul(final float x, final float y, final float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	public MutableVector3f div(final double a) {
		return this.div((float) a);
	}
	
	@Override
	public MutableVector3f div(final float a) {
		return this.div(a, a, a);
	}
	
	public MutableVector3f div(final MutableVector3f v) {
		return this.div(v.x, v.y, v.z);
	}
	
	public MutableVector3f div(final Vector3f v) {
		return this.div(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f div(final double x, final double y, final double z) {
		return this.div((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f div(final float x, final float y, final float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public float dot(final MutableVector3f v) {
		return this.dot(v.x, v.y, v.z);
	}
	
	public float dot(final Vector3f v) {
		return this.dot(v.x(), v.y(), v.z());
	}
	
	public float dot(final double x, final double y, final double z) {
		return this.dot((float) x, (float) y, (float) z);
	}
	
	public float dot(final float x, final float y, final float z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	public MutableVector3f project(final MutableVector3f v) {
		return this.project(v.x, v.y, v.z);
	}
	
	public MutableVector3f project(final Vector3f v) {
		return this.project(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f project(final double x, final double y, final double z) {
		return this.project((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f project(final float x, final float y, final float z) {
		final float lengthSquared = x * x + y * y + z * z;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = this.dot(x, y, z) / lengthSquared;
		return this.set(a * x, a * y, a * z);
	}
	
	public MutableVector3f cross(final MutableVector3f v) {
		return this.cross(v.x, v.y, v.z);
	}
	
	public MutableVector3f cross(final Vector3f v) {
		return this.cross(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f cross(final double x, final double y, final double z) {
		return this.cross((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f cross(final float x, final float y, final float z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}
	
	public MutableVector3f pow(final double pow) {
		return this.pow((float) pow);
	}
	
	@Override
	public MutableVector3f pow(final float pow) {
		return this.pow(pow, pow, pow);
	}
	
	public MutableVector3f pow(final MutableVector3f v) {
		return this.pow(v.x, v.y, v.z);
	}
	
	public MutableVector3f pow(final Vector3f v) {
		return this.pow(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f pow(final double x, final double y, final double z) {
		return this.pow((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f pow(final float x, final float y, final float z) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z));
	}
	
	@Override
	public MutableVector3f ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z));
	}
	
	@Override
	public MutableVector3f floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z));
	}
	
	@Override
	public MutableVector3f round() {
		return this.set(Math.round(this.x), Math.round(this.y), Math.round(this.z));
	}
	
	@Override
	public MutableVector3f abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }
	
	@Override
	public MutableVector3f negate() {
		return this.set(-this.x, -this.y, -this.z);
	}
	
	public MutableVector3f min(final MutableVector3f v) {
		return this.min(v.x, v.y, v.z);
	}
	
	public MutableVector3f min(final Vector3f v) {
		return this.min(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f min(final double x, final double y, final double z) {
		return this.min((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f min(final float x, final float y, final float z) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
	}
	
	public MutableVector3f max(final MutableVector3f v) {
		return this.max(v.x, v.y, v.z);
	}
	
	public MutableVector3f max(final Vector3f v) {
		return this.max(v.x(), v.y(), v.z());
	}
	
	public MutableVector3f max(final double x, final double y, final double z) {
		return this.max((float) x, (float) y, (float) z);
	}
	
	public MutableVector3f max(final float x, final float y, final float z) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
	}
	
	public float distanceSquared(final MutableVector3f v) {
		return this.distanceSquared(v.x, v.y, v.z);
	}
	
	public float distanceSquared(final Vector3f v) {
		return this.distanceSquared(v.x(), v.y(), v.z());
	}
	
	public float distanceSquared(final double x, final double y, final double z) {
		return this.distanceSquared((float) x, (float) y, (float) z);
	}
	
	public float distanceSquared(final float x, final float y, final float z) {
		final float dx = this.x - x;
		final float dy = this.y - y;
		final float dz = this.z - z;
		return dx * dx + dy * dy + dz * dz;
	}
	
	public float distance(final MutableVector3f v) {
		return this.distance(v.x, v.y, v.z);
	}
	
	public float distance(final Vector3f v) {
		return this.distance(v.x(), v.y(), v.z());
	}
	
	public float distance(final double x, final double y, final double z) {
		return this.distance((float) x, (float) y, (float) z);
	}
	
	public float distance(final float x, final float y, final float z) {
		return (float) Math.sqrt(this.distanceSquared(x, y, z));
	}
	
	@Override
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector3f normalize() {
		final float length = this.length();
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
	public float[] toArray() {
		return new float[]{this.x, this.y, this.z};
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
		} else if (!(other instanceof final MutableVector3f that)) {
			return false;
		} else {
			return Float.compare(that.x, this.x) == 0
				&& Float.compare(that.y, this.y) == 0
				&& Float.compare(that.z, this.z) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Float.hashCode(this.x);
		result = result * 31 + Float.hashCode(this.y);
		result = result * 31 + Float.hashCode(this.z);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	public static MutableVector3f zero() {
		return MutableVector3f.of(0, 0, 0);
	}
	
	public static MutableVector3f unitX() {
		return MutableVector3f.of(1, 0, 0);
	}
	
	public static MutableVector3f unitY() {
		return MutableVector3f.of(0, 1, 0);
	}
	
	public static MutableVector3f unitZ() {
		return MutableVector3f.of(0, 0, 1);
	}
	
	public static MutableVector3f one() {
		return MutableVector3f.of(1, 1, 1);
	}
	
	public static  MutableVector3f from(final double n) {
		return MutableVector3f.from((float) n);
	}
	
	public static MutableVector3f from(final float n) {
		return MutableVector3f.of(n, n, n);
	}
	
	public static MutableVector3f from(final MutableVector3f v) {
		return MutableVector3f.of(v.x, v.y, v.z);
	}
	
	public static MutableVector3f from(final Vector3f v) {
		return MutableVector3f.of(v.x(), v.y(), v.z());
	}
	
	public static MutableVector3f of(final double x, final double y, final double z) {
		return new MutableVector3f(x, y, z);
	}
	
	public static MutableVector3f of(final float x, final float y, final float z) {
		return new MutableVector3f(x, y, z);
	}
}
