package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.vector.Vector2d;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2d;

public final class OptionalVector2d {
	
	private final OptionalDouble x;
	private final OptionalDouble y;
	private final Transformer transformer;
	
	private OptionalVector2d(final OptionalDouble x, final OptionalDouble y) {
		this.x = x;
		this.y = y;
		
		if (this.x.isPresent()) {
			final double x0 = this.x.getAsDouble();
			if (this.y.isPresent()) {
				final double y0 = this.y.getAsDouble();
				this.transformer = (o, v) -> o.apply(x0, y0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v);
			}
		} else if (this.y.isPresent()) {
			final double y0 = this.y.getAsDouble();
			this.transformer = (o, v) -> o.apply(v, y0);
		} else {
			this.transformer = (o, v) -> o;
		}
	}
	
	public OptionalDouble x() {
		return this.x;
	}
	
	public OptionalDouble y() {
		return this.y;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final double neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector2d set(final MutableVector2d v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		return v;
	}
	
	public Vector2d set(final Vector2d v) {
		final double x = this.x.orElse(v.x());
		final double y = this.y.orElse(v.y());
		return new Vector2d(x, y);
	}
	
	public MutableVector2d add(final MutableVector2d v) {
		return this.apply(v::add, 0);
	}
	
	public Vector2d add(final Vector2d v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector2d sub(final MutableVector2d v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector2d sub(final Vector2d v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector2d mul(final MutableVector2d v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector2d mul(final Vector2d v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector2d div(final MutableVector2d v) {
		return this.apply(v::div, 1);
	}
	
	public Vector2d div(final Vector2d v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector2d pow(final MutableVector2d v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector2d pow(final Vector2d v) {
		final double x = Math.pow(v.x(), this.x.orElse(1));
		final double y = Math.pow(v.y(), this.y.orElse(1));
		return new Vector2d(x, y);
	}
	
	public MutableVector2d min(final MutableVector2d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public Vector2d min(final Vector2d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public MutableVector2d max(final MutableVector2d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	public Vector2d max(final Vector2d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector2d that)) {
			return false;
		} else {
			return this.x.equals(that.x)
				&& this.y.equals(that.y);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "("
				+ "x=" + (this.x.isPresent() ? this.x.getAsDouble() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsDouble() : "?")
				+ ")";
	}
	
	public static OptionalVector2d x(final double x) {
		return OptionalVector2d.x(convert(x));
	}
	
	public static OptionalVector2d x(final @Nullable Double x) {
		return OptionalVector2d.x(convert(x));
	}
	
	public static OptionalVector2d x(final Optional<Double> x) {
		return OptionalVector2d.x(convert(x));
	}
	
	public static OptionalVector2d x(final OptionalDouble x) {
		return OptionalVector2d.of(x, empty());
	}
	
	public static OptionalVector2d y(final double y) {
		return OptionalVector2d.y(convert(y));
	}
	
	public static OptionalVector2d y(final @Nullable Double y) {
		return OptionalVector2d.y(convert(y));
	}
	
	public static OptionalVector2d y(final Optional<Double> y) {
		return OptionalVector2d.y(convert(y));
	}
	
	public static OptionalVector2d y(final OptionalDouble y) {
		return OptionalVector2d.of(empty(), y);
	}
	
	public static OptionalVector2d of(final double x, final double y) {
		return OptionalVector2d.of(convert(x), convert(y));
	}
	
	public static OptionalVector2d of(final @Nullable Double x, final @Nullable Double y) {
		return OptionalVector2d.of(convert(x), convert(y));
	}
	
	public static OptionalVector2d of(final Optional<Double> x, final Optional<Double> y) {
		return OptionalVector2d.of(convert(x), convert(y));
	}
	
	public static OptionalVector2d of(final OptionalDouble x, final OptionalDouble y) {
		return new OptionalVector2d(x, y);
	}
	
	private static OptionalDouble empty() {
		return OptionalDouble.empty();
	}
	
	private static OptionalDouble convert(final double a) {
		return OptionalDouble.of(a);
	}
	
	private static OptionalDouble convert(final @Nullable Double a) {
		return a == null ? empty() : OptionalDouble.of(a.doubleValue());
	}
	
	private static OptionalDouble convert(final Optional<Double> a) {
		return a.map(OptionalDouble::of).orElseGet(OptionalVector2d::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, double neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(double x, double y);
	}
}
