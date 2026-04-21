package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4d;
import org.spongepowered.math.vector.Vector4f;
import org.spongepowered.math.vector.Vector4i;
import org.spongepowered.math.vector.Vector4l;
import org.spongepowered.math.vector.Vectori;

public final class MutableVector4i implements Vectori {
	
	private int x;
	private int y;
	private int z;
	private int w;
	
	public MutableVector4i(final double x, final double y, final double z, final double w) {
		this(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i(final int x, final int y, final int z, final int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public int z() {
		return this.z;
	}
	
	public int w() {
		return this.w;
	}
	
	public MutableVector4i copy() {
		return MutableVector4i.from(this);
	}
	
	public MutableVector4i x(final MutableVector4i v) {
		return this.x(v.x);
	}
	
	public MutableVector4i x(final Vector4i v) {
		return this.x(v.x());
	}
	
	public MutableVector4i x(final double a) {
		return this.x(GenericMath.floor(a));
	}
	
	public MutableVector4i x(final int a) {
		this.x = a;
		return this;
	}
	
	public MutableVector4i y(final MutableVector4i v) {
		return this.y(v.y);
	}
	
	public MutableVector4i y(final Vector4i v) {
		return this.y(v.y());
	}
	
	public MutableVector4i y(final double a) {
		return this.y(GenericMath.floor(a));
	}
	
	public MutableVector4i y(final int a) {
		this.y = a;
		return this;
	}
	
	public MutableVector4i z(final MutableVector4i v) {
		return this.z(v.z);
	}
	
	public MutableVector4i z(final Vector4i v) {
		return this.z(v.z());
	}
	
	public MutableVector4i z(final double a) {
		return this.z(GenericMath.floor(a));
	}
	
	public MutableVector4i z(final int a) {
		this.z = a;
		return this;
	}
	
	public MutableVector4i w(final MutableVector4i v) {
		return this.w(v.z);
	}
	
	public MutableVector4i w(final Vector4i v) {
		return this.w(v.z());
	}
	
	public MutableVector4i w(final double a) {
		return this.w(GenericMath.floor(a));
	}
	
	public MutableVector4i w(final int a) {
		this.w = a;
		return this;
	}
	
	public MutableVector4i set(final MutableVector4i v) {
		return this.set(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i set(final Vector4i v) {
		return this.set(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i set(final double x, final double y, final double z, final double w) {
		return this.set(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i set(final int x, final int y, final int z, final int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public MutableVector4i add(final MutableVector4i v) {
		return this.add(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i add(final Vector4i v) {
		return this.add(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i add(final double x, final double y, final double z, final double w) {
		return this.add(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i add(final int x, final int y, final int z, final int w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}
	
	public MutableVector4i sub(final MutableVector4i v) {
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i sub(final Vector4i v) {
		return this.sub(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i sub(final double x, final double y, final double z, final double w) {
		return this.sub(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i sub(final int x, final int y, final int z, final int w) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}
	
	public MutableVector4i mul(final double a) {
		return this.mul(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector4i mul(final int a) {
		return this.mul(a, a, a, a);
	}
	
	public MutableVector4i mul(final MutableVector4i v) {
		return this.mul(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i mul(final Vector4i v) {
		return this.mul(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i mul(final double x, final double y, final double z, final double w) {
		return this.mul(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i mul(final int x, final int y, final int z, final int w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}
	
	public MutableVector4i div(final double a) {
		return this.div(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector4i div(final int a) {
		return this.div(a, a, a, a);
	}
	
	public MutableVector4i div(final MutableVector4i v) {
		return this.div(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i div(final Vector4i v) {
		return this.div(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i div(final double x, final double y, final double z, final double w) {
		return this.div(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i div(final int x, final int y, final int z, final int w) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		this.w /= w;
		return this;
	}
	
	public int dot(final MutableVector4i v) {
		return this.dot(v.x, v.y, v.z, v.w);
	}
	
	public int dot(final Vector4i v) {
		return this.dot(v.x(), v.y(), v.z(), v.w());
	}
	
	public int dot(final double x, final double y, final double z, final double w) {
		return this.dot(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public int dot(final int x, final int y, final int z, final int w) {
		return this.x * x + this.y * y + this.z * z + this.w * w;
	}
	
	public MutableVector4i project(final MutableVector4i v) {
		return this.project(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i project(final Vector4i v) {
		return this.project(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i project(final double x, final double y, final double z, final double w) {
		return this.project(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i project(final int x, final int y, final int z, final int w) {
		final int lengthSquared = x * x + y * y + z * z + w * w;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = (float) this.dot(x, y, z, w) / lengthSquared;
		return this.set(a * x, a * y, a * z, a * w);
	}
	
	public MutableVector4i pow(final double pow) {
		return this.pow(GenericMath.floor(pow));
	}
	
	@Override
	public MutableVector4i pow(final int pow) {
		return this.pow(pow, pow, pow, pow);
	}
	
	public MutableVector4i pow(final MutableVector4i v) {
		return this.pow(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i pow(final Vector4i v) {
		return this.pow(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i pow(final double x, final double y, final double z, final double w) {
		return this.pow(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i pow(final int x, final int y, final int z, final int w) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z), Math.pow(this.w, w));
	}
	
	@Override
	public MutableVector4i abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }
	
	@Override
	public MutableVector4i negate() {
		return this.set(-this.x, -this.y, -this.z, -this.w);
	}
	
	public MutableVector4i min(final MutableVector4i v) {
		return this.min(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i min(final Vector4i v) {
		return this.min(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i min(final double x, final double y, final double z, final double w) {
		return this.min(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i min(final int x, final int y, final int z, final int w) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), Math.min(this.w, w));
	}
	
	public MutableVector4i max(final MutableVector4i v) {
		return this.max(v.x, v.y, v.z, v.w);
	}
	
	public MutableVector4i max(final Vector4i v) {
		return this.max(v.x(), v.y(), v.z(), v.w());
	}
	
	public MutableVector4i max(final double x, final double y, final double z, final double w) {
		return this.max(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public MutableVector4i max(final int x, final int y, final int z, final int w) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), Math.max(this.w, w));
	}
	
	public int distanceSquared(final MutableVector4i v) {
		return this.distanceSquared(v.x, v.y, v.z, v.w);
	}
	
	public int distanceSquared(final Vector4i v) {
		return this.distanceSquared(v.x(), v.y(), v.z(), v.w());
	}
	
	public int distanceSquared(final double x, final double y, final double z, final double w) {
		return this.distanceSquared(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public int distanceSquared(final int x, final int y, final int z, final int w) {
		final int dx = this.x - x;
		final int dy = this.y - y;
		final int dz = this.z - z;
		final int dw = this.w - w;
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	public float distance(final MutableVector4i v) {
		return this.distance(v.x, v.y, v.z, v.w);
	}
	
	public float distance(final Vector4i v) {
		return this.distance(v.x(), v.y(), v.z(), v.w());
	}
	
	public float distance(final double x, final double y, final double z, final double w) {
		return this.distance(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z), GenericMath.floor(w));
	}
	
	public float distance(final int x, final int y, final int z, final int w) {
		return (float) Math.sqrt(this.distanceSquared(x, y, z, w));
	}
	
	@Override
	public int lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public int minAxis() {
		int value = this.x;
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
		int value = this.x;
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
	public int[] toArray() {
		return new int[]{this.x, this.y, this.z, this.w};
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
		} else if (!(other instanceof final MutableVector4i that)) {
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
		int result = Integer.hashCode(this.x);
		result = result * 31 + Integer.hashCode(this.y);
		result = result * 31 + Integer.hashCode(this.z);
		result = result * 31 + Integer.hashCode(this.w);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
	}
	
	public static MutableVector4i zero() {
		return MutableVector4i.of(0, 0, 0, 0);
	}
	
	public static MutableVector4i unitX() {
		return MutableVector4i.of(1, 0, 0, 0);
	}
	
	public static MutableVector4i unitY() {
		return MutableVector4i.of(0, 1, 0, 0);
	}
	
	public static MutableVector4i unitZ() {
		return MutableVector4i.of(0, 0, 1, 0);
	}
	
	public static MutableVector4i unitW() {
		return MutableVector4i.of(0, 0, 0, 1);
	}
	
	public static MutableVector4i one() {
		return MutableVector4i.of(1, 1, 1, 1);
	}
	
	public static  MutableVector4i from(final double n) {
		return MutableVector4i.from(GenericMath.floor(n));
	}
	
	public static MutableVector4i from(final int n) {
		return MutableVector4i.of(n, n, n, n);
	}
	
	public static MutableVector4i from(final MutableVector4i v) {
		return MutableVector4i.of(v.x, v.y, v.z, v.w);
	}
	
	public static MutableVector4i from(final Vector4i v) {
		return MutableVector4i.of(v.x(), v.y(), v.z(), v.w());
	}
	
	public static MutableVector4i of(final double x, final double y, final double z, final double w) {
		return new MutableVector4i(x, y, z, w);
	}
	
	public static MutableVector4i of(final int x, final int y, final int z, final int w) {
		return new MutableVector4i(x, y, z, w);
	}
}
