package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.vector.Vector3d;

import net.hellheim.spongetools.math.mutable.vector.MutableVector3d;

public final class OptionalVector3d {
	
	private final OptionalDouble x;
	private final OptionalDouble y;
	private final OptionalDouble z;
	private final Transformer transformer;
	
	private OptionalVector3d(final OptionalDouble x, final OptionalDouble y, final OptionalDouble z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		if (this.x.isPresent()) {
			final double x0 = this.x.getAsDouble();
			if (this.y.isPresent()) {
				final double y0 = this.y.getAsDouble();
				if (this.z.isPresent()) {
					final double z0 = this.z.getAsDouble();
					this.transformer = (o, v) -> o.apply(x0, y0, z0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v);
				}
			} else if (this.z.isPresent()) {
				final double z0 = this.z.getAsDouble();
				this.transformer = (o, v) -> o.apply(x0, v, z0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v);
			}
		} else if (this.y.isPresent()) {
			final double y0 = this.y.getAsDouble();
			if (this.z.isPresent()) {
				final double z0 = this.z.getAsDouble();
				this.transformer = (o, v) -> o.apply(v, y0, z0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v);
			}
		} else if (this.z.isPresent()) {
			final double z0 = this.z.getAsDouble();
			this.transformer = (o, v) -> o.apply(v, v, z0);
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
	
	public OptionalDouble z() {
		return this.z;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final double neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector3d set(final MutableVector3d v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		return v;
	}
	
	public Vector3d set(final Vector3d v) {
		final double x = this.x.orElse(v.x());
		final double y = this.y.orElse(v.y());
		final double z = this.z.orElse(v.z());
		return new Vector3d(x, y, z);
	}
	
	public MutableVector3d add(final MutableVector3d v) {
		return this.apply(v::add, 0);
	}
	
	public Vector3d add(final Vector3d v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector3d sub(final MutableVector3d v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector3d sub(final Vector3d v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector3d mul(final MutableVector3d v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector3d mul(final Vector3d v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector3d div(final MutableVector3d v) {
		return this.apply(v::div, 1);
	}
	
	public Vector3d div(final Vector3d v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector3d pow(final MutableVector3d v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector3d pow(final Vector3d v) {
		final double x = Math.pow(v.x(), this.x.orElse(1));
		final double y = Math.pow(v.y(), this.y.orElse(1));
		final double z = Math.pow(v.z(), this.z.orElse(1));
		return new Vector3d(x, y, z);
	}
	
	public MutableVector3d min(final MutableVector3d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public Vector3d min(final Vector3d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public MutableVector3d max(final MutableVector3d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	public Vector3d max(final Vector3d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector3d that)) {
			return false;
		} else {
			return this.x.equals(that.x)
				&& this.y.equals(that.y)
				&& this.z.equals(that.z);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y, this.z);
	}
	
	@Override
	public String toString() {
		return "("
				+ "x=" + (this.x.isPresent() ? this.x.getAsDouble() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsDouble() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsDouble() : "?")
				+ ")";
	}
	
	public static OptionalVector3d x(final double x) {
		return OptionalVector3d.x(convert(x));
	}
	
	public static OptionalVector3d x(final @Nullable Double x) {
		return OptionalVector3d.x(convert(x));
	}
	
	public static OptionalVector3d x(final Optional<Double> x) {
		return OptionalVector3d.x(convert(x));
	}
	
	public static OptionalVector3d x(final OptionalDouble x) {
		return OptionalVector3d.of(x, empty(), empty());
	}
	
	public static OptionalVector3d y(final double y) {
		return OptionalVector3d.y(convert(y));
	}
	
	public static OptionalVector3d y(final @Nullable Double y) {
		return OptionalVector3d.y(convert(y));
	}
	
	public static OptionalVector3d y(final Optional<Double> y) {
		return OptionalVector3d.y(convert(y));
	}
	
	public static OptionalVector3d y(final OptionalDouble y) {
		return OptionalVector3d.of(empty(), y, empty());
	}
	
	public static OptionalVector3d z(final double z) {
		return OptionalVector3d.z(convert(z));
	}
	
	public static OptionalVector3d z(final @Nullable Double z) {
		return OptionalVector3d.z(convert(z));
	}
	
	public static OptionalVector3d z(final Optional<Double> z) {
		return OptionalVector3d.z(convert(z));
	}
	
	public static OptionalVector3d z(final OptionalDouble z) {
		return OptionalVector3d.of(empty(), empty(), z);
	}
	
	public static OptionalVector3d xy(final double x, final double y) {
		return OptionalVector3d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3d xy(final @Nullable Double x, final @Nullable Double y) {
		return OptionalVector3d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3d xy(final Optional<Double> x, final Optional<Double> y) {
		return OptionalVector3d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3d xy(final OptionalDouble x, final OptionalDouble y) {
		return OptionalVector3d.of(x, y, empty());
	}
	
	public static OptionalVector3d xz(final double x, final double z) {
		return OptionalVector3d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3d xz(final @Nullable Double x, final @Nullable Double z) {
		return OptionalVector3d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3d xz(final Optional<Double> x, final Optional<Double> z) {
		return OptionalVector3d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3d xz(final OptionalDouble x, final OptionalDouble z) {
		return OptionalVector3d.of(x, empty(), z);
	}
	
	public static OptionalVector3d yz(final double y, final double z) {
		return OptionalVector3d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3d yz(final @Nullable Double y, final @Nullable Double z) {
		return OptionalVector3d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3d yz(final Optional<Double> y, final Optional<Double> z) {
		return OptionalVector3d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3d yz(final OptionalDouble y, final OptionalDouble z) {
		return OptionalVector3d.of(empty(), y, z);
	}
	
	public static OptionalVector3d of(final double x, final double y, final double z) {
		return OptionalVector3d.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3d of(final @Nullable Double x, final @Nullable Double y, final @Nullable Double z) {
		return OptionalVector3d.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3d of(final Optional<Double> x, final Optional<Double> y, final Optional<Double> z) {
		return OptionalVector3d.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3d of(final OptionalDouble x, final OptionalDouble y, final OptionalDouble z) {
		return new OptionalVector3d(x, y, z);
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
		return a.map(OptionalDouble::of).orElseGet(OptionalVector3d::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, double neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(double x, double y, double z);
	}
}
