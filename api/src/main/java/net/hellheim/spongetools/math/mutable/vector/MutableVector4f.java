package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4d;
import org.spongepowered.math.vector.Vector4f;
import org.spongepowered.math.vector.Vector4i;
import org.spongepowered.math.vector.Vector4l;
import org.spongepowered.math.vector.Vectorf;

public final class MutableVector4f implements Vectorf {
	
	private float x;
	private float y;
	private float z;
	private float w;
	
	public MutableVector4f(final double x, final double y, final double z, final double w) {
		this((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	
	public float w() {
		return this.w;
	}
	
	public MutableVector4f copy() {
		return MutableVector4f.from(this);
	}
	
	public MutableVector4f x(final MutableVector4f v) {
		return this.x(v.x);
	}
	
	public MutableVector4f x(final Vector4f v) {
		return this.x(v.x());
	}
	
	public MutableVector4f x(final double a) {
		return this.x((float) a);
	}
	
	public MutableVector4f x(final float a) {
		this.x = a;
		return this;
	}
	
	public MutableVector4f y(final MutableVector4f v) {
		return this.y(v.y);
	}
	
	public MutableVector4f y(final Vector4f v) {
		return this.y(v.y());
	}
	
	public MutableVector4f y(final double a) {
		return this.y((float) a);
	}
	
	public MutableVector4f y(final float a) {
		this.y = a;
		return this;
	}
	
	public MutableVector4f z(final MutableVector4f v) {
		return this.z(v.z);
	}
	
	public MutableVector4f z(final Vector4f v) {
		return this.z(v.z());
	}
	
	public MutableVector4f z(final double a) {
		return this.z((float) a);
	}
	
	public MutableVector4f z(final float a) {
		this.z = a;
		return this;
	}
	
	public MutableVector4f w(final MutableVector4f v) {
		return this.w(v.z);
	}
	
	public MutableVector4f w(final Vector4f v) {
		return this.w(v.z());
	}
	
	public MutableVector4f w(final double a) {
		return this.w((float) a);
	}
	
	public MutableVector4f w(final float a) {
		this.w = a;
		return this;
	}
	
	public MutableVector4f set(final MutableVector4f v) {
		return this.set(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f set(final Vector4f v) {
		return this.set(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f set(final double x, final double y, final double z, final double w) {
		return this.set((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f set(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public MutableVector4f add(final MutableVector4f v) {
		return this.add(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f add(final Vector4f v) {
		return this.add(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f add(final double x, final double y, final double z, final double w) {
		return this.add((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f add(final float x, final float y, final float z, final float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}
	
	public MutableVector4f sub(final MutableVector4f v) {
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f sub(final Vector4f v) {
		return this.sub(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f sub(final double x, final double y, final double z, final double w) {
		return this.sub((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f sub(final float x, final float y, final float z, final float w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}
	
	public MutableVector4f mul(final double a) {
		return this.mul((float) a);
	}
	
	@Override
	public MutableVector4f mul(final float a) {
		return this.mul(a, a, a, a);
	}
	
	public MutableVector4f mul(final MutableVector4f v) {
		return this.mul(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f mul(final Vector4f v) {
		return this.mul(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f mul(final double x, final double y, final double z, final double w) {
		return this.mul((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f mul(final float x, final float y, final float z, final float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}
	
	public MutableVector4f div(final double a) {
		return this.div((float) a);
	}
	
	@Override
	public MutableVector4f div(final float a) {
		return this.div(a, a, a, a);
	}
	
	public MutableVector4f div(final MutableVector4f v) {
		return this.div(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f div(final Vector4f v) {
		return this.div(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f div(final double x, final double y, final double z, final double w) {
		return this.div((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f div(final float x, final float y, final float z, final float w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;
		return this;
	}
	
	public float dot(final MutableVector4f v) {
		return this.dot(v.x, v.y, v.z, v.w);
	}
	
	public float dot(final Vector4f v) {
		return this.dot(v.x(), v.y(), v.z(), v.w());
	}
	
	public float dot(final double x, final double y, final double z, final double w) {
		return this.dot((float) x, (float) y, (float) z, (float) w);
	}
	
	public float dot(final float x, final float y, final float z, final float w) {
		return this.x * x + this.y * y + this.z * z + this.w * w;
	}
	
	public MutableVector4f project(final MutableVector4f v) {
		return this.project(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f project(final Vector4f v) {
		return this.project(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f project(final double x, final double y, final double z, final double w) {
		return this.project((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f project(final float x, final float y, final float z, final float w) {
		final float lengthSquared = x * x + y * y + z * z + w * w;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = this.dot(x, y, z, w) / lengthSquared;
		return this.set(a * x, a * y, a * z, a * w);
	}
	
	public MutableVector4f pow(final double pow) {
		return this.pow((float) pow);
	}
	
	@Override
	public MutableVector4f pow(final float pow) {
		return this.pow(pow, pow, pow, pow);
	}
	
	public MutableVector4f pow(final MutableVector4f v) {
		return this.pow(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f pow(final Vector4f v) {
		return this.pow(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f pow(final double x, final double y, final double z, final double w) {
		return this.pow((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f pow(final float x, final float y, final float z, final float w) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z), Math.pow(this.w, w));
	}
	
	@Override
	public MutableVector4f ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z), Math.ceil(this.w));
	}
	
	@Override
	public MutableVector4f floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y), GenericMath.floor(this.z), GenericMath.floor(this.w));
	}
	
	@Override
	public MutableVector4f round() {
		return this.set(Math.round(this.x), Math.round(this.y), Math.round(this.z), Math.round(this.w));
	}
	
	@Override
	public MutableVector4f abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }
	
	@Override
	public MutableVector4f negate() {
		return this.set(-this.x, -this.y, -this.z, -this.w);
	}
	
	public MutableVector4f min(final MutableVector4f v) {
		return this.min(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f min(final Vector4f v) {
		return this.min(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f min(final double x, final double y, final double z, final double w) {
		return this.min((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f min(final float x, final float y, final float z, final float w) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
	}
	
	public MutableVector4f max(final MutableVector4f v) {
		return this.max(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4f max(final Vector4f v) {
		return this.max(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4f max(final double x, final double y, final double z, final double w) {
		return this.max((float) x, (float) y, (float) z, (float) w);
	}
	
	public MutableVector4f max(final float x, final float y, final float z, final float w) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
	}
	
	public float distanceSquared(final MutableVector4f v) {
		return this.distanceSquared(v.x, v.y, v.z, v.w);
	}
	
	public float distanceSquared(final Vector4f v) {
		return this.distanceSquared(v.x(), v.y(), v.z(), v.w());
	}
	
	public float distanceSquared(final double x, final double y, final double z, final double w) {
		return this.distanceSquared((float) x, (float) y, (float) z, (float) w);
	}
	
	public float distanceSquared(final float x, final float y, final float z, final float w) {
		final float dx = this.x - x;
		final float dy = this.y - y;
		final float dz = this.z - z;
		final float dw = this.w - w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public float distance(final MutableVector4f v) {
		return this.distance(v.x, v.y, v.z, v.w);
	}
	
	public float distance(final Vector4f v) {
		return this.distance(v.x(), v.y(), v.z(), v.w());
	}
	
	public float distance(final double x, final double y, final double z, final double w) {
		return this.distance((float) x, (float) y, (float) z, (float) w);
	}
	
	public float distance(final float x, final float y, final float z, final float w) {
		return (float) Math.sqrt(this.distanceSquared(x, y, z, w));
	}
	
	@Override
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector4f normalize() {
		final float length = this.length();
		if (Math.abs(length) < GenericMath.FLT_EPSILON) {
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return this.set(this.x / length, this.y / length, this.z / length, this.w / length);
	}
	
	@Override
	public int minAxis() {
		float value = this.x;
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
		float value = this.x;
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
	public float[] toArray() {
		return new float[]{this.x, this.y, this.z, this.w};
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
		} else if (!(other instanceof final MutableVector4f that)) {
			return false;
		} else {
			return Float.compare(that.x, this.x) == 0
				&& Float.compare(that.y, this.y) == 0
				&& Float.compare(that.z, this.z) == 0
				&& Float.compare(that.w, this.w) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Float.hashCode(this.x);
		result = result * 31 + Float.hashCode(this.y);
		result = result * 31 + Float.hashCode(this.z);
		result = result * 31 + Float.hashCode(this.w);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
	}
	
	public static MutableVector4f zero() {
		return MutableVector4f.of(0, 0, 0, 0);
	}
	
	public static MutableVector4f unitX() {
		return MutableVector4f.of(1, 0, 0, 0);
	}
	
	public static MutableVector4f unitY() {
		return MutableVector4f.of(0, 1, 0, 0);
	}
	
	public static MutableVector4f unitZ() {
		return MutableVector4f.of(0, 0, 1, 0);
	}
	
	public static MutableVector4f unitW() {
		return MutableVector4f.of(0, 0, 0, 1);
	}
	
	public static MutableVector4f one() {
		return MutableVector4f.of(1, 1, 1, 1);
	}
	
	public static  MutableVector4f from(final double n) {
		return MutableVector4f.from((float) n);
	}
	
	public static MutableVector4f from(final float n) {
		return MutableVector4f.of(n, n, n, n);
	}
	
	public static MutableVector4f from(final MutableVector4f v) {
		return MutableVector4f.of(v.x, v.y, v.z, v.w);
	}
	
	public static MutableVector4f from(final Vector4f v) {
		return MutableVector4f.of(v.x(), v.y(), v.z(), v.w());
	}
	
	public static MutableVector4f of(final double x, final double y, final double z, final double w) {
		return new MutableVector4f(x, y, z, w);
	}
	
	public static MutableVector4f of(final float x, final float y, final float z, final float w) {
		return new MutableVector4f(x, y, z, w);
	}
}
