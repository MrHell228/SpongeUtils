package net.hellheim.spongetools.math.mutable.vector;

import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector2f;
import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector2l;
import org.spongepowered.math.vector.Vectori;

public final class MutableVector2i implements Vectori {
	
	private int x;
	private int y;
	
	public MutableVector2i(final double x, final double y) {
		this(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public MutableVector2i copy() {
		return MutableVector2i.from(this);
	}
	
	public MutableVector2i x(final MutableVector2i v) {
		return this.x(v.x);
	}
	
	public MutableVector2i x(final Vector2i v) {
		return this.x(v.x());
	}
	
	public MutableVector2i x(final double a) {
		return this.x(GenericMath.floor(a));
	}
	
	public MutableVector2i x(final int a) {
		this.x = a;
		return this;
	}
	
	public MutableVector2i y(final MutableVector2i v) {
		return this.y(v.y);
	}
	
	public MutableVector2i y(final Vector2i v) {
		return this.y(v.y());
	}
	
	public MutableVector2i y(final double a) {
		return this.y(GenericMath.floor(a));
	}
	
	public MutableVector2i y(final int a) {
		this.y = a;
		return this;
	}
	
	public MutableVector2i set(final MutableVector2i v) {
		return this.set(v.x, v.y);
	}
	
	public MutableVector2i set(final Vector2i v) {
		return this.set(v.x(), v.y());
	}
	
	public MutableVector2i set(final double x, final double y) {
		return this.set(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i set(final int x, final int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public MutableVector2i add(final MutableVector2i v) {
		return this.add(v.x, v.y);
	}
	
	public MutableVector2i add(final Vector2i v) {
		return this.add(v.x(), v.y());
	}
	
	public MutableVector2i add(final double x, final double y) {
		return this.add(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i add(final int x, final int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public MutableVector2i sub(final MutableVector2i v) {
		return this.sub(v.x, v.y);
	}
	
	public MutableVector2i sub(final Vector2i v) {
		return this.sub(v.x(), v.y());
	}
	
	public MutableVector2i sub(final double x, final double y) {
		return this.sub(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i sub(final int x, final int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public MutableVector2i mul(final double a) {
		return this.mul(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector2i mul(final int a) {
		return this.mul(a, a);
	}
	
	public MutableVector2i mul(final MutableVector2i v) {
		return this.mul(v.x, v.y);
	}
	
	public MutableVector2i mul(final Vector2i v) {
		return this.mul(v.x(), v.y());
	}
	
	public MutableVector2i mul(final double x, final double y) {
		return this.mul(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i mul(final int x, final int y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public MutableVector2i div(final double a) {
		return this.div(GenericMath.floor(a));
	}
	
	@Override
	public MutableVector2i div(final int a) {
		return this.div(a, a);
	}
	
	public MutableVector2i div(final MutableVector2i v) {
		return this.div(v.x, v.y);
	}
	
	public MutableVector2i div(final Vector2i v) {
		return this.div(v.x(), v.y());
	}
	
	public MutableVector2i div(final double x, final double y) {
		return this.div(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i div(final int x, final int y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public int dot(final MutableVector2i v) {
		return this.dot(v.x, v.y);
	}
	
	public int dot(final Vector2i v) {
		return this.dot(v.x(), v.y());
	}
	
	public int dot(final double x, final double y) {
		return this.dot(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public int dot(final int x, final int y) {
		return this.x * x + this.y * y;
	}
	
	public MutableVector2i project(final MutableVector2i v) {
		return this.project(v.x, v.y);
	}
	
	public MutableVector2i project(final Vector2i v) {
		return this.project(v.x(), v.y());
	}
	
	public MutableVector2i project(final double x, final double y) {
		return this.project(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i project(final int x, final int y) {
		final int lengthSquared = x * x + y * y;
		if (lengthSquared == 0) {
			throw new ArithmeticException("Cannot project onto the zero vector");
		}
		final float a = (float) this.dot(x, y) / lengthSquared;
		return this.set(a * x, a * y);
	}
	
	public MutableVector2i pow(final double pow) {
		return this.pow(GenericMath.floor(pow));
	}
	
	@Override
	public MutableVector2i pow(final int pow) {
		return this.pow(pow, pow);
	}
	
	public MutableVector2i pow(final MutableVector2i v) {
		return this.pow(v.x, v.y);
	}
	
	public MutableVector2i pow(final Vector2i v) {
		return this.pow(v.x(), v.y());
	}
	
	public MutableVector2i pow(final double x, final double y) {
		return this.pow(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i pow(final int x, final int y) {
		return this.set(Math.pow(this.x, x), Math.pow(this.y, y));
	}
	
	@Override
	public MutableVector2i abs() {
		return this.set(Math.abs(this.x), Math.abs(this.y));
    }
	
	@Override
	public MutableVector2i negate() {
		return this.set(-this.x, -this.y);
	}
	
	public MutableVector2i min(final MutableVector2i v) {
		return this.min(v.x, v.y);
	}
	
	public MutableVector2i min(final Vector2i v) {
		return this.min(v.x(), v.y());
	}
	
	public MutableVector2i min(final double x, final double y) {
		return this.min(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i min(final int x, final int y) {
		return this.set(Math.min(this.x, x), Math.min(this.y, y));
	}
	
	public MutableVector2i max(final MutableVector2i v) {
		return this.max(v.x, v.y);
	}
	
	public MutableVector2i max(final Vector2i v) {
		return this.max(v.x(), v.y());
	}
	
	public MutableVector2i max(final double x, final double y) {
		return this.max(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public MutableVector2i max(final int x, final int y) {
		return this.set(Math.max(this.x, x), Math.max(this.y, y));
	}
	
	public int distanceSquared(final MutableVector2i v) {
		return this.distanceSquared(v.x, v.y);
	}
	
	public int distanceSquared(final Vector2i v) {
		return this.distanceSquared(v.x(), v.y());
	}
	
	public int distanceSquared(final double x, final double y) {
		return this.distanceSquared(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public int distanceSquared(final int x, final int y) {
		final int dx = this.x - x;
		final int dy = this.y - y;
		return dx * dx + dy * dy;
	}
	
	public float distance(final MutableVector2i v) {
		return this.distance(v.x, v.y);
	}
	
	public float distance(final Vector2i v) {
		return this.distance(v.x(), v.y());
	}
	
	public float distance(final double x, final double y) {
		return this.distance(GenericMath.floor(x), GenericMath.floor(y));
	}
	
	public float distance(final int x, final int y) {
		return (float) Math.sqrt(this.distanceSquared(x, y));
	}
	
	@Override
	public int lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
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
	public int[] toArray() {
		return new int[]{this.x, this.y};
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
		} else if (!(other instanceof final MutableVector2i that)) {
			return false;
		} else {
			return that.x == this.x
				&& that.y == this.y;
		}
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(this.x) * 31 + Integer.hashCode(this.y);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	public static MutableVector2i zero() {
		return MutableVector2i.of(0, 0);
	}
	
	public static MutableVector2i unitX() {
		return MutableVector2i.of(1, 0);
	}
	
	public static MutableVector2i unitY() {
		return MutableVector2i.of(0, 1);
	}
	
	public static MutableVector2i one() {
		return MutableVector2i.of(1, 1);
	}
	
	public static  MutableVector2i from(final double n) {
		return MutableVector2i.from(GenericMath.floor(n));
	}
	
	public static MutableVector2i from(final int n) {
		return MutableVector2i.of(n, n);
	}
	
	public static MutableVector2i from(final MutableVector2i v) {
		return MutableVector2i.of(v.x, v.y);
	}
	
	public static MutableVector2i from(final Vector2i v) {
		return MutableVector2i.of(v.x(), v.y());
	}
	
	public static MutableVector2i of(final double x, final double y) {
		return new MutableVector2i(x, y);
	}
	
	public static MutableVector2i of(final int x, final int y) {
		return new MutableVector2i(x, y);
	}
}
