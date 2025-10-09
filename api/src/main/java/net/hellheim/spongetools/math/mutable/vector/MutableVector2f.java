package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vectorf;

public final class MutableVector2f implements Vectorf {
	
	private float x;
	private float y;
	
	public MutableVector2f(final double x, final double y) {
		this((float) x, (float) y);
	}
	
	public MutableVector2f(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}
	
	public MutableVector2f copy() {
		return MutableVector2f.from(this);
	}
	
	public MutableVector2f x(final MutableVector2f v) {
		return this.x(v.x);
	}
	
	public MutableVector2f x(final Vector2f v) {
		return this.x(v.x());
	}
	
	public MutableVector2f x(final double a) {
		return this.x((float) a);
	}
	
	public MutableVector2f x(final float a) {
		this.x = a;
		return this;
	}
	
	public MutableVector2f y(final MutableVector2f v) {
		return this.y(v.y);
	}
	
	public MutableVector2f y(final Vector2f v) {
		return this.y(v.y());
	}
	
	public MutableVector2f y(final double a) {
		return this.y((float) a);
	}
	
	public MutableVector2f y(final float a) {
		this.y = a;
		return this;
	}
	
	public MutableVector2f set(final MutableVector2f v) {
		return this.set(v.x, v.y);
	}
	
	public MutableVector2f set(final Vector2f v) {
		return this.set(v.x(), v.y());
	}
	
	public MutableVector2f set(final double x, final double y) {
		return this.set((float) x, (float) y);
	}
	
	public MutableVector2f set(final float x, final float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public MutableVector2f add(final MutableVector2f v) {
		return this.add(v.x, v.y);
	}
	
	public MutableVector2f add(final Vector2f v) {
		return this.add(v.x(), v.y());
	}
	
	public MutableVector2f add(final double x, final double y) {
		return this.add((float) x, (float) y);
	}
	
	public MutableVector2f add(final float x, final float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public MutableVector2f sub(final MutableVector2f v) {
		return this.sub(v.x, v.y);
	}
	
	public MutableVector2f sub(final Vector2f v) {
		return this.sub(v.x(), v.y());
	}
	
	public MutableVector2f sub(final double x, final double y) {
		return this.sub((float) x, (float) y);
	}
	
	public MutableVector2f sub(final float x, final float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public MutableVector2f mul(final double a) {
		return this.mul((float) a);
	}
	
	@Override
	public MutableVector2f mul(final float a) {
		return this.mul(a, a);
	}
	
	public MutableVector2f mul(final MutableVector2f v) {
		return this.mul(v.x, v.y);
	}
	
	public MutableVector2f mul(final Vector2f v) {
		return this.mul(v.x(), v.y());
	}
	
	public MutableVector2f mul(final double x, final double y) {
		return this.mul((float) x, (float) y);
	}
	
	public MutableVector2f mul(final float x, final float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public MutableVector2f div(final double a) {
		return this.div((float) a);
	}
	
	@Override
	public MutableVector2f div(final float a) {
		return this.div(a, a);
	}
	
	public MutableVector2f div(final MutableVector2f v) {
		return this.div(v.x, v.y);
	}
	
	public MutableVector2f div(final Vector2f v) {
		return this.div(v.x(), v.y());
	}
	
	public MutableVector2f div(final double x, final double y) {
		return this.div((float) x, (float) y);
	}
	
	public MutableVector2f div(final float x, final float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public float dot(final MutableVector2f v) {
		return this.dot(v.x, v.y);
	}
	
	public float dot(final Vector2f v) {
		return this.dot(v.x(), v.y());
	}
	
	public float dot(final double x, final double y) {
		return this.dot((float) x, (float) y);
	}
	
	public float dot(final float x, final float y) {
		return this.x * x + this.y * y;
	}
	
	public MutableVector2f project(final MutableVector2f v) {
		return this.project(v.x, v.y);
	}
	
	public MutableVector2f project(final Vector2f v) {
		return this.project(v.x(), v.y());
	}
	
	public MutableVector2f project(final double x, final double y) {
		return this.project((float) x, (float) y);
	}
	
	public MutableVector2f project(final float x, final float y) {
		final float lengthSquared = x * x + y * y;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = this.dot(x, y) / lengthSquared;
		return this.set(a * x, a * y);
	}
	
	public MutableVector2f pow(final double pow) {
		return this.pow((float) pow);
	}
	
	@Override
	public MutableVector2f pow(final float pow) {
		return this.pow(pow, pow);
	}
	
	public MutableVector2f pow(final MutableVector2f v) {
		return this.pow(v.x, v.y);
	}
	
	public MutableVector2f pow(final Vector2f v) {
		return this.pow(v.x(), v.y());
	}
	
	public MutableVector2f pow(final double x, final double y) {
		return this.pow((float) x, (float) y);
	}
	
	public MutableVector2f pow(final float x, final float y) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y));
	}
	
	@Override
	public MutableVector2f ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y));
	}
	
	@Override
	public MutableVector2f floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y));
	}
	
	@Override
	public MutableVector2f round() {
		return this.set(Math.round(this.x), Math.round(this.y));
	}
	
	@Override
	public MutableVector2f abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y));
    }
	
	@Override
	public MutableVector2f negate() {
		return this.set(-this.x, -this.y);
	}
	
	public MutableVector2f min(final MutableVector2f v) {
		return this.min(v.x, v.y);
	}
	
	public MutableVector2f min(final Vector2f v) {
		return this.min(v.x(), v.y());
	}
	
	public MutableVector2f min(final double x, final double y) {
		return this.min((float) x, (float) y);
	}
	
	public MutableVector2f min(final float x, final float y) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public MutableVector2f max(final MutableVector2f v) {
		return this.max(v.x, v.y);
	}
	
	public MutableVector2f max(final Vector2f v) {
		return this.max(v.x(), v.y());
	}
	
	public MutableVector2f max(final double x, final double y) {
		return this.max((float) x, (float) y);
	}
	
	public MutableVector2f max(final float x, final float y) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public float distanceSquared(final MutableVector2f v) {
		return this.distanceSquared(v.x, v.y);
	}
	
	public float distanceSquared(final Vector2f v) {
		return this.distanceSquared(v.x(), v.y());
	}
	
	public float distanceSquared(final double x, final double y) {
		return this.distanceSquared((float) x, (float) y);
	}
	
	public float distanceSquared(final float x, final float y) {
		final float dx = this.x - x;
		final float dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public float distance(final MutableVector2f v) {
		return this.distance(v.x, v.y);
	}
	
	public float distance(final Vector2f v) {
		return this.distance(v.x(), v.y());
	}
	
	public float distance(final double x, final double y) {
		return this.distance((float) x, (float) y);
	}
	
	public float distance(final float x, final float y) {
		return (float) Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector2f normalize() {
		final float length = this.length();
		if (Math.abs(length) < GenericMath.FLT_EPSILON) {
			throw new ArithmeticException("Cannot normalize the zero vector");
		}
		return this.set(this.x / length, this.y / length);
	}
	
	@Override
	public int minAxis() {
		return this.x < this.y ? 0 : 1;
	}
	
	@Override
	public int maxAxis() {
		return this.x > this.y ? 0 : 1;
	}
	
	@Override
	public float[] toArray() {
		return new float[]{this.x, this.y};
	}
	
	@Override
	public Vector2i toInt() {
		return new Vector2i(this.x, this.y);
	}
	
	@Override
	public Vector2l toLong() {
		return new Vector2l(this.x, this.y);
	}
	
	@Override
	public Vector2f toFloat() {
		return new Vector2f(this.x, this.y);
	}
	
	@Override
	public Vector2d toDouble() {
		return new Vector2d((double) this.x, (double) this.y);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final MutableVector2f that)) {
			return false;
		} else {
			return Float.compare(that.x, this.x) == 0
				&& Float.compare(that.y, this.y) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		return Float.hashCode(this.x) * 31 + Float.hashCode(this.y);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static MutableVector2f zero() {
		return MutableVector2f.of(0, 0);
	}
	
	public static MutableVector2f unitX() {
		return MutableVector2f.of(1, 0);
	}
	
	public static MutableVector2f unitY() {
		return MutableVector2f.of(0, 1);
	}
	
	public static MutableVector2f one() {
		return MutableVector2f.of(1, 1);
	}
	
	public static  MutableVector2f from(final double n) {
		return MutableVector2f.from((float) n);
	}
	
	public static MutableVector2f from(final float n) {
		return MutableVector2f.of(n, n);
	}
	
	public static MutableVector2f from(final MutableVector2f v) {
		return MutableVector2f.of(v.x, v.y);
	}
	
	public static MutableVector2f from(final Vector2f v) {
		return MutableVector2f.of(v.x(), v.y());
	}
	
	public static MutableVector2f of(final double x, final double y) {
		return new MutableVector2f(x, y);
	}
	
	public static MutableVector2f of(final float x, final float y) {
		return new MutableVector2f(x, y);
	}
}
