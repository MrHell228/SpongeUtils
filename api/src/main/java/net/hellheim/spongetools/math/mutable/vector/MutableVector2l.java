package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vectorl;

public final class MutableVector2l implements Vectorl {
	
	private long x;
	private long y;
	
	public MutableVector2l(final double x, final double y) {
		this(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l(final long x, final long y) {
		this.x = x;
		this.y = y;
	}
	
	public long x() {
		return this.x;
	}
	
	public long y() {
		return this.y;
	}
	
	public MutableVector2l copy() {
		return MutableVector2l.from(this);
	}
	
	public MutableVector2l x(final MutableVector2l v) {
		return this.x(v.x);
	}
	
	public MutableVector2l x(final Vector2l v) {
		return this.x(v.x());
	}
	
	public MutableVector2l x(final double a) {
		return this.x(GenericMath.floorl(a));
	}
	
	public MutableVector2l x(final long a) {
		this.x = a;
		return this;
	}
	
	public MutableVector2l y(final MutableVector2l v) {
		return this.y(v.y);
	}
	
	public MutableVector2l y(final Vector2l v) {
		return this.y(v.y());
	}
	
	public MutableVector2l y(final double a) {
		return this.y(GenericMath.floorl(a));
	}
	
	public MutableVector2l y(final long a) {
		this.y = a;
		return this;
	}
	
	public MutableVector2l set(final MutableVector2l v) {
		return this.set(v.x, v.y);
	}
	
	public MutableVector2l set(final Vector2l v) {
		return this.set(v.x(), v.y());
	}
	
	public MutableVector2l set(final double x, final double y) {
		return this.set(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l set(final long x, final long y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public MutableVector2l add(final MutableVector2l v) {
		return this.add(v.x, v.y);
	}
	
	public MutableVector2l add(final Vector2l v) {
		return this.add(v.x(), v.y());
	}
	
	public MutableVector2l add(final double x, final double y) {
		return this.add(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l add(final long x, final long y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public MutableVector2l sub(final MutableVector2l v) {
		return this.sub(v.x, v.y);
	}
	
	public MutableVector2l sub(final Vector2l v) {
		return this.sub(v.x(), v.y());
	}
	
	public MutableVector2l sub(final double x, final double y) {
		return this.sub(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l sub(final long x, final long y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public MutableVector2l mul(final double a) {
		return this.mul(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector2l mul(final long a) {
		return this.mul(a, a);
	}
	
	public MutableVector2l mul(final MutableVector2l v) {
		return this.mul(v.x, v.y);
	}
	
	public MutableVector2l mul(final Vector2l v) {
		return this.mul(v.x(), v.y());
	}
	
	public MutableVector2l mul(final double x, final double y) {
		return this.mul(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l mul(final long x, final long y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public MutableVector2l div(final double a) {
		return this.div(GenericMath.floorl(a));
	}
	
	@Override
	public MutableVector2l div(final long a) {
		return this.div(a, a);
	}
	
	public MutableVector2l div(final MutableVector2l v) {
		return this.div(v.x, v.y);
	}
	
	public MutableVector2l div(final Vector2l v) {
		return this.div(v.x(), v.y());
	}
	
	public MutableVector2l div(final double x, final double y) {
		return this.div(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l div(final long x, final long y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public long dot(final MutableVector2l v) {
		return this.dot(v.x, v.y);
	}
	
	public long dot(final Vector2l v) {
		return this.dot(v.x(), v.y());
	}
	
	public long dot(final double x, final double y) {
		return this.dot(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public long dot(final long x, final long y) {
		return this.x * x + this.y * y;
	}
	
	public MutableVector2l project(final MutableVector2l v) {
		return this.project(v.x, v.y);
	}
	
	public MutableVector2l project(final Vector2l v) {
		return this.project(v.x(), v.y());
	}
	
	public MutableVector2l project(final double x, final double y) {
		return this.project(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l project(final long x, final long y) {
		final long lengthSquared = x * x + y * y;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = (float) this.dot(x, y) / lengthSquared;
		return this.set(a * x, a * y);
	}
	
	public MutableVector2l pow(final double pow) {
		return this.pow(GenericMath.floorl(pow));
	}
	
	@Override
	public MutableVector2l pow(final long pow) {
		return this.pow(pow, pow);
	}
	
	public MutableVector2l pow(final MutableVector2l v) {
		return this.pow(v.x, v.y);
	}
	
	public MutableVector2l pow(final Vector2l v) {
		return this.pow(v.x(), v.y());
	}
	
	public MutableVector2l pow(final double x, final double y) {
		return this.pow(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l pow(final long x, final long y) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y));
	}
	
	@Override
	public MutableVector2l abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y));
    }
	
	@Override
	public MutableVector2l negate() {
		return this.set(-this.x, -this.y);
	}
	
	public MutableVector2l min(final MutableVector2l v) {
		return this.min(v.x, v.y);
	}
	
	public MutableVector2l min(final Vector2l v) {
		return this.min(v.x(), v.y());
	}
	
	public MutableVector2l min(final double x, final double y) {
		return this.min(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l min(final long x, final long y) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public MutableVector2l max(final MutableVector2l v) {
		return this.max(v.x, v.y);
	}
	
	public MutableVector2l max(final Vector2l v) {
		return this.max(v.x(), v.y());
	}
	
	public MutableVector2l max(final double x, final double y) {
		return this.max(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public MutableVector2l max(final long x, final long y) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public long distanceSquared(final MutableVector2l v) {
		return this.distanceSquared(v.x, v.y);
	}
	
	public long distanceSquared(final Vector2l v) {
		return this.distanceSquared(v.x(), v.y());
	}
	
	public long distanceSquared(final double x, final double y) {
		return this.distanceSquared(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public long distanceSquared(final long x, final long y) {
		final long dx = this.x - x;
		final long dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public float distance(final MutableVector2l v) {
		return this.distance(v.x, v.y);
	}
	
	public float distance(final Vector2l v) {
		return this.distance(v.x(), v.y());
	}
	
	public float distance(final double x, final double y) {
		return this.distance(GenericMath.floorl(x), GenericMath.floorl(y));
	}
	
	public float distance(final long x, final long y) {
		return (float) Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public long lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public double length() {
		return Math.sqrt(this.lengthSquared());
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
	public long[] toArray() {
		return new long[]{this.x, this.y};
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
		} else if (!(other instanceof final MutableVector2l that)) {
			return false;
		} else {
			return that.x == this.x
				&& that.y == this.y;
		}
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(this.x) * 31 + Long.hashCode(this.y);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static MutableVector2l zero() {
		return MutableVector2l.of(0, 0);
	}
	
	public static MutableVector2l unitX() {
		return MutableVector2l.of(1, 0);
	}
	
	public static MutableVector2l unitY() {
		return MutableVector2l.of(0, 1);
	}
	
	public static MutableVector2l one() {
		return MutableVector2l.of(1, 1);
	}
	
	public static  MutableVector2l from(final double n) {
		return MutableVector2l.from(GenericMath.floorl(n));
	}
	
	public static MutableVector2l from(final long n) {
		return MutableVector2l.of(n, n);
	}
	
	public static MutableVector2l from(final MutableVector2l v) {
		return MutableVector2l.of(v.x, v.y);
	}
	
	public static MutableVector2l from(final Vector2l v) {
		return MutableVector2l.of(v.x(), v.y());
	}
	
	public static MutableVector2l of(final double x, final double y) {
		return new MutableVector2l(x, y);
	}
	
	public static MutableVector2l of(final long x, final long y) {
		return new MutableVector2l(x, y);
	}
}
