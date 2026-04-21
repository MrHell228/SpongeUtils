package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3f;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector3l;
import org.spongepowered.math.vector.Vectori;

public final class MutableVector3i implements Vectori {
	
	private int x;
	private int y;
	private int z;
	
	public MutableVector3i(final double x, final double y, final double z) {
		this(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public MutableVector3i copy() {
		return MutableVector3i.from(this);
	}
	
	public MutableVector3i x(final MutableVector3i v) {
		return this.x(v.x);
	}
	
	public MutableVector3i x(final Vector3i v) {
		return this.x(v.x());
	}
	
	public MutableVector3i x(final double a) {
		return this.x(GenericMath.floor(a));
	}
	
	public MutableVector3i x(final int a) {
		this.x = a;
		return this;
	}
	
	public MutableVector3i y(final MutableVector3i v) {
		return this.y(v.y);
	}
	
	public MutableVector3i y(final Vector3i v) {
		return this.y(v.y());
	}
	
	public MutableVector3i y(final double a) {
		return this.y(GenericMath.floor(a));
	}
	
	public MutableVector3i y(final int a) {
		this.y = a;
		return this;
	}
	
	public MutableVector3i z(final MutableVector3i v) {
		return this.z(v.z);
	}
	
	public MutableVector3i z(final Vector3i v) {
		return this.z(v.z());
	}
	
	public MutableVector3i z(final double a) {
		return this.z(GenericMath.floor(a));
	}
	
	public MutableVector3i z(final int a) {
		this.z = a;
		return this;
	}
	
	public MutableVector3i set(final MutableVector3i v) {
		return this.set(v.x, v.y, v.z);
	}
	
	public MutableVector3i set(final Vector3i v) {
		return this.set(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i set(final double x, final double y, final double z) {
		return this.set(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i set(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public MutableVector3i add(final MutableVector3i v) {
		return this.add(v.x, v.y, v.z);
	}
	
	public MutableVector3i add(final Vector3i v) {
		return this.add(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i add(final double x, final double y, final double z) {
		return this.add(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i add(final int x, final int y, final int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public MutableVector3i sub(final MutableVector3i v) {
		return this.sub(v.x, v.y, v.z);
	}
	
	public MutableVector3i sub(final Vector3i v) {
		return this.sub(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i sub(final double x, final double y, final double z) {
		return this.sub(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i sub(final int x, final int y, final int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public MutableVector3i mul(final double a) {
		return this.mul(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector3i mul(final int a) {
		return this.mul(a, a, a);
	}
	
	public MutableVector3i mul(final MutableVector3i v) {
		return this.mul(v.x, v.y, v.z);
	}
	
	public MutableVector3i mul(final Vector3i v) {
		return this.mul(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i mul(final double x, final double y, final double z) {
		return this.mul(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i mul(final int x, final int y, final int z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	public MutableVector3i div(final double a) {
		return this.div(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector3i div(final int a) {
		return this.div(a, a, a);
	}
	
	public MutableVector3i div(final MutableVector3i v) {
		return this.div(v.x, v.y, v.z);
	}
	
	public MutableVector3i div(final Vector3i v) {
		return this.div(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i div(final double x, final double y, final double z) {
		return this.div(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i div(final int x, final int y, final int z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public int dot(final MutableVector3i v) {
		return this.dot(v.x, v.y, v.z);
	}
	
	public int dot(final Vector3i v) {
		return this.dot(v.x(), v.y(), v.z());
	}
	
	public int dot(final double x, final double y, final double z) {
		return this.dot(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public int dot(final int x, final int y, final int z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	public MutableVector3i project(final MutableVector3i v) {
		return this.project(v.x, v.y, v.z);
	}
	
	public MutableVector3i project(final Vector3i v) {
		return this.project(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i project(final double x, final double y, final double z) {
		return this.project(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i project(final int x, final int y, final int z) {
		final int lengthSquared = x * x + y * y + z * z;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = (float) this.dot(x, y, z) / lengthSquared;
		return this.set(a * x, a * y, a * z);
	}
	
	public MutableVector3i cross(final MutableVector3i v) {
		return this.cross(v.x, v.y, v.z);
	}
	
	public MutableVector3i cross(final Vector3i v) {
		return this.cross(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i cross(final double x, final double y, final double z) {
		return this.cross(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i cross(final int x, final int y, final int z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}
	
	public MutableVector3i pow(final double pow) {
		return this.pow(GenericMath.floor(pow));
	}
	
	@Override
	public MutableVector3i pow(final int pow) {
		return this.pow(pow, pow, pow);
	}
	
	public MutableVector3i pow(final MutableVector3i v) {
		return this.pow(v.x, v.y, v.z);
	}
	
	public MutableVector3i pow(final Vector3i v) {
		return this.pow(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i pow(final double x, final double y, final double z) {
		return this.pow(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i pow(final int x, final int y, final int z) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y), Math.pow(this.z, z));
	}
	
	@Override
	public MutableVector3i abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }
	
	@Override
	public MutableVector3i negate() {
		return this.set(-this.x, -this.y, -this.z);
	}
	
	public MutableVector3i min(final MutableVector3i v) {
		return this.min(v.x, v.y, v.z);
	}
	
	public MutableVector3i min(final Vector3i v) {
		return this.min(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i min(final double x, final double y, final double z) {
		return this.min(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i min(final int x, final int y, final int z) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
	}
	
	public MutableVector3i max(final MutableVector3i v) {
		return this.max(v.x, v.y, v.z);
	}
	
	public MutableVector3i max(final Vector3i v) {
		return this.max(v.x(), v.y(), v.z());
	}
	
	public MutableVector3i max(final double x, final double y, final double z) {
		return this.max(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public MutableVector3i max(final int x, final int y, final int z) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
	}
	
	public int distanceSquared(final MutableVector3i v) {
		return this.distanceSquared(v.x, v.y, v.z);
	}
	
	public int distanceSquared(final Vector3i v) {
		return this.distanceSquared(v.x(), v.y(), v.z());
	}
	
	public int distanceSquared(final double x, final double y, final double z) {
		return this.distanceSquared(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public int distanceSquared(final int x, final int y, final int z) {
		final int dx = this.x - x;
		final int dy = this.y - y;
		final int dz = this.z - z;
		return dx * dx + dy * dy + dz * dz;
	}
	
	public float distance(final MutableVector3i v) {
		return this.distance(v.x, v.y, v.z);
	}
	
	public float distance(final Vector3i v) {
		return this.distance(v.x(), v.y(), v.z());
	}
	
	public float distance(final double x, final double y, final double z) {
		return this.distance(GenericMath.floor(x), GenericMath.floor(y), GenericMath.floor(z));
	}
	
	public float distance(final int x, final int y, final int z) {
		return (float) Math.sqrt(this.distanceSquared(x, y, z));
	}
	
	@Override
	public int lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
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
	public int[] toArray() {
		return new int[]{this.x, this.y, this.z};
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
		} else if (!(other instanceof final MutableVector3i that)) {
			return false;
		} else {
			return that.x == this.x
				&& that.y == this.y
				&& that.z == this.z;
		}
	}
	
	@Override
	public int hashCode() {
		int result = Integer.hashCode(this.x);
		result = result * 31 + Integer.hashCode(this.y);
		result = result * 31 + Integer.hashCode(this.z);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
	
	public static MutableVector3i zero() {
		return MutableVector3i.of(0, 0, 0);
	}
	
	public static MutableVector3i unitX() {
		return MutableVector3i.of(1, 0, 0);
	}
	
	public static MutableVector3i unitY() {
		return MutableVector3i.of(0, 1, 0);
	}
	
	public static MutableVector3i unitZ() {
		return MutableVector3i.of(0, 0, 1);
	}
	
	public static MutableVector3i one() {
		return MutableVector3i.of(1, 1, 1);
	}
	
	public static  MutableVector3i from(final double n) {
		return MutableVector3i.from(GenericMath.floor(n));
	}
	
	public static MutableVector3i from(final int n) {
		return MutableVector3i.of(n, n, n);
	}
	
	public static MutableVector3i from(final MutableVector3i v) {
		return MutableVector3i.of(v.x, v.y, v.z);
	}
	
	public static MutableVector3i from(final Vector3i v) {
		return MutableVector3i.of(v.x(), v.y(), v.z());
	}
	
	public static MutableVector3i of(final double x, final double y, final double z) {
		return new MutableVector3i(x, y, z);
	}
	
	public static MutableVector3i of(final int x, final int y, final int z) {
		return new MutableVector3i(x, y, z);
	}
}
