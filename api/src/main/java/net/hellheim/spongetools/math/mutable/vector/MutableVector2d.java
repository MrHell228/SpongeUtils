package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vectord;

public final class MutableVector2d implements Vectord {
	
	private double x;
	private double y;
	
	public MutableVector2d(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return this.x;
	}
	
	public double y() {
		return this.y;
	}
	
	public MutableVector2d copy() {
		return MutableVector2d.from(this);
	}
	
	public MutableVector2d x(final MutableVector2d v) {
		return this.x(v.x);
	}
	
	public MutableVector2d x(final Vector2d v) {
		return this.x(v.x());
	}
	
	public MutableVector2d x(final double a) {
		this.x = a;
		return this;
	}
	
	public MutableVector2d y(final MutableVector2d v) {
		return this.y(v.y);
	}
	
	public MutableVector2d y(final Vector2d v) {
		return this.y(v.y());
	}
	
	public MutableVector2d y(final double a) {
		this.y = a;
		return this;
	}
	
	public MutableVector2d set(final MutableVector2d v) {
		return this.set(v.x, v.y);
	}
	
	public MutableVector2d set(final Vector2d v) {
		return this.set(v.x(), v.y());
	}
	
	public MutableVector2d set(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public MutableVector2d add(final MutableVector2d v) {
		return this.add(v.x, v.y);
	}
	
	public MutableVector2d add(final Vector2d v) {
		return this.add(v.x(), v.y());
	}
	
	public MutableVector2d add(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public MutableVector2d sub(final MutableVector2d v) {
		return this.sub(v.x, v.y);
	}
	
	public MutableVector2d sub(final Vector2d v) {
		return this.sub(v.x(), v.y());
	}
	
	public MutableVector2d sub(final double x, final double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	@Override
	public MutableVector2d mul(final double a) {
		return this.mul(a, a);
	}
	
	public MutableVector2d mul(final MutableVector2d v) {
		return this.mul(v.x, v.y);
	}
	
	public MutableVector2d mul(final Vector2d v) {
		return this.mul(v.x(), v.y());
	}
	
	public MutableVector2d mul(final double x, final double y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	@Override
	public MutableVector2d div(final double a) {
		return this.div(a, a);
	}
	
	public MutableVector2d div(final MutableVector2d v) {
		return this.div(v.x, v.y);
	}
	
	public MutableVector2d div(final Vector2d v) {
		return this.div(v.x(), v.y());
	}
	
	public MutableVector2d div(final double x, final double y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public double dot(final MutableVector2d v) {
		return this.dot(v.x, v.y);
	}
	
	public double dot(final Vector2d v) {
		return this.dot(v.x(), v.y());
	}
	
	public double dot(final double x, final double y) {
		return this.x * x + this.y * y;
	}
	
	public MutableVector2d project(final MutableVector2d v) {
		return this.project(v.x, v.y);
	}
	
	public MutableVector2d project(final Vector2d v) {
		return this.project(v.x(), v.y());
	}
	
	public MutableVector2d project(final double x, final double y) {
		final double lengthSquared = x * x + y * y;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final double a = this.dot(x, y) / lengthSquared;
		return this.set(a * x, a * y);
	}
	
	@Override
	public MutableVector2d pow(final double pow) {
		return this.pow(pow, pow);
	}
	
	public MutableVector2d pow(final MutableVector2d v) {
		return this.pow(v.x, v.y);
	}
	
	public MutableVector2d pow(final Vector2d v) {
		return this.pow(v.x(), v.y());
	}
	
	public MutableVector2d pow(final double x, final double y) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y));
	}
	
	@Override
	public MutableVector2d ceil() {
		return this.set(Math.ceil(this.x), Math.ceil(this.y));
	}
	
	@Override
	public MutableVector2d floor() {
		return this.set(GenericMath.floor(this.x), GenericMath.floor(this.y));
	}
	
	@Override
	public MutableVector2d round() {
		return this.set(Math.round(this.x), Math.round(this.y));
	}
	
	@Override
	public MutableVector2d abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y));
    }
	
	@Override
	public MutableVector2d negate() {
		return this.set(-this.x, -this.y);
	}
	
	public MutableVector2d min(final MutableVector2d v) {
		return this.min(v.x, v.y);
	}
	
	public MutableVector2d min(final Vector2d v) {
		return this.min(v.x(), v.y());
	}
	
	public MutableVector2d min(final double x, final double y) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public MutableVector2d max(final MutableVector2d v) {
		return this.max(v.x, v.y);
	}
	
	public MutableVector2d max(final Vector2d v) {
		return this.max(v.x(), v.y());
	}
	
	public MutableVector2d max(final double x, final double y) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public double distanceSquared(final MutableVector2d v) {
		return this.distanceSquared(v.x, v.y);
	}
	
	public double distanceSquared(final Vector2d v) {
		return this.distanceSquared(v.x(), v.y());
	}
	
	public double distanceSquared(final double x, final double y) {
		final double dx = this.x - x;
		final double dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public double distance(final MutableVector2d v) {
		return this.distance(v.x, v.y);
	}
	
	public double distance(final Vector2d v) {
		return this.distance(v.x(), v.y());
	}
	
	public double distance(final double x, final double y) {
		return Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	@Override
	public MutableVector2d normalize() {
		final double length = this.length();
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
	public double[] toArray() {
		return new double[]{this.x, this.y};
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
		return new Vector2d(this.x, this.y);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final MutableVector2d that)) {
			return false;
		} else {
			return Double.compare(that.x, this.x) == 0
				&& Double.compare(that.y, this.y) == 0;
		}
	}
	
	@Override
	public int hashCode() {
		return Double.hashCode(this.x) * 31 + Double.hashCode(this.y);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static MutableVector2d zero() {
		return MutableVector2d.of(0, 0);
	}
	
	public static MutableVector2d unitX() {
		return MutableVector2d.of(1, 0);
	}
	
	public static MutableVector2d unitY() {
		return MutableVector2d.of(0, 1);
	}
	
	public static MutableVector2d one() {
		return MutableVector2d.of(1, 1);
	}
	
	public static  MutableVector2d from(final double n) {
		return MutableVector2d.of(n, n);
	}
	
	public static MutableVector2d from(final MutableVector2d v) {
		return MutableVector2d.of(v.x, v.y);
	}
	
	public static MutableVector2d from(final Vector2d v) {
		return MutableVector2d.of(v.x(), v.y());
	}
	
	public static MutableVector2d of(final double x, final double y) {
		return new MutableVector2d(x, y);
	}
}
