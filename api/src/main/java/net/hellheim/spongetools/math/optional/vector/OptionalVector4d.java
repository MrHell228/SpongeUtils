package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.vector.Vector4d;

import net.hellheim.spongetools.math.mutable.vector.MutableVector4d;

public final class OptionalVector4d {
	
	private final OptionalDouble x;
	private final OptionalDouble y;
	private final OptionalDouble z;
	private final OptionalDouble w;
	private final Transformer transformer;
	
	private OptionalVector4d(final OptionalDouble x, final OptionalDouble y, final OptionalDouble z, final OptionalDouble w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		if (this.x.isPresent()) {
			final double x0 = this.x.getAsDouble();
			if (this.y.isPresent()) {
				final double y0 = this.y.getAsDouble();
				if (this.z.isPresent()) {
					final double z0 = this.z.getAsDouble();
					if (this.w.isPresent()) {
						final double w0 = this.w.getAsDouble();
						this.transformer = (o, v) -> o.apply(x0, y0, z0, w0);
					} else {
						this.transformer = (o, v) -> o.apply(x0, y0, z0, v);
					}
				} else if (this.w.isPresent()) {
					final double w0 = this.w.getAsDouble();
					this.transformer = (o, v) -> o.apply(x0, y0, v, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v, v);
				}
			} else if (this.z.isPresent()) {
				final double z0 = this.z.getAsDouble();
				if (this.w.isPresent()) {
					final double w0 = this.w.getAsDouble();
					this.transformer = (o, v) -> o.apply(x0, v, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, v, z0, v);
				}
			} else if (this.w.isPresent()) {
				final double w0 = this.w.getAsDouble();
				this.transformer = (o, v) -> o.apply(x0, v, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v, v);
			}
		} else if (this.y.isPresent()) {
			final double y0 = this.y.getAsDouble();
			if (this.z.isPresent()) {
				final double z0 = this.z.getAsDouble();
				if (this.w.isPresent()) {
					final double w0 = this.w.getAsDouble();
					this.transformer = (o, v) -> o.apply(v, y0, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(v, y0, z0, v);
				}
			} else if (this.w.isPresent()) {
				final double w0 = this.w.getAsDouble();
				this.transformer = (o, v) -> o.apply(v, y0, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v, v);
			}
		} else if (this.z.isPresent()) {
			final double z0 = this.z.getAsDouble();
			if (this.w.isPresent()) {
				final double w0 = this.w.getAsDouble();
				this.transformer = (o, v) -> o.apply(v, v, z0, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, v, z0, v);
			}
		} else if (this.w.isPresent()) {
			final double w0 = this.w.getAsDouble();
			this.transformer = (o, v) -> o.apply(v, v, v, w0);
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
	
	public OptionalDouble w() {
		return this.w;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final double neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector4d set(final MutableVector4d v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		this.w.ifPresent(v::w);
		return v;
	}
	
	public Vector4d set(final Vector4d v) {
		final double x = this.x.orElse(v.x());
		final double y = this.y.orElse(v.y());
		final double z = this.z.orElse(v.z());
		final double w = this.w.orElse(v.w());
		return new Vector4d(x, y, z, w);
	}
	
	public MutableVector4d add(final MutableVector4d v) {
		return this.apply(v::add, 0);
	}
	
	public Vector4d add(final Vector4d v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector4d sub(final MutableVector4d v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector4d sub(final Vector4d v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector4d mul(final MutableVector4d v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector4d mul(final Vector4d v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector4d div(final MutableVector4d v) {
		return this.apply(v::div, 1);
	}
	
	public Vector4d div(final Vector4d v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector4d pow(final MutableVector4d v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector4d pow(final Vector4d v) {
		final double x = Math.pow(v.x(), this.x.orElse(1));
		final double y = Math.pow(v.y(), this.y.orElse(1));
		final double z = Math.pow(v.z(), this.z.orElse(1));
		final double w = Math.pow(v.w(), this.w.orElse(1));
		return new Vector4d(x, y, z, w);
	}
	
	public MutableVector4d min(final MutableVector4d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public Vector4d min(final Vector4d v) {
		return this.apply(v::min, Double.POSITIVE_INFINITY);
	}
	
	public MutableVector4d max(final MutableVector4d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	public Vector4d max(final Vector4d v) {
		return this.apply(v::max, Double.NEGATIVE_INFINITY);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector4d that)) {
			return false;
		} else {
			return this.x.equals(that.x)
				&& this.y.equals(that.y)
				&& this.z.equals(that.z)
				&& this.w.equals(that.w);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y, this.z, this.w);
	}
	
	@Override
	public String toString() {
		return "("
				+ "x=" + (this.x.isPresent() ? this.x.getAsDouble() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsDouble() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsDouble() : "?")
				+ "w=" + (this.w.isPresent() ? this.w.getAsDouble() : "?")
				+ ")";
	}
	
	public static OptionalVector4d x(final double x) {
		return OptionalVector4d.x(convert(x));
	}
	
	public static OptionalVector4d x(final @Nullable Double x) {
		return OptionalVector4d.x(convert(x));
	}
	
	public static OptionalVector4d x(final Optional<Double> x) {
		return OptionalVector4d.x(convert(x));
	}
	
	public static OptionalVector4d x(final OptionalDouble x) {
		return OptionalVector4d.of(x, empty(), empty(), empty());
	}
	
	public static OptionalVector4d y(final double y) {
		return OptionalVector4d.y(convert(y));
	}
	
	public static OptionalVector4d y(final @Nullable Double y) {
		return OptionalVector4d.y(convert(y));
	}
	
	public static OptionalVector4d y(final Optional<Double> y) {
		return OptionalVector4d.y(convert(y));
	}
	
	public static OptionalVector4d y(final OptionalDouble y) {
		return OptionalVector4d.of(empty(), y, empty(), empty());
	}
	
	public static OptionalVector4d z(final double z) {
		return OptionalVector4d.z(convert(z));
	}
	
	public static OptionalVector4d z(final @Nullable Double z) {
		return OptionalVector4d.z(convert(z));
	}
	
	public static OptionalVector4d z(final Optional<Double> z) {
		return OptionalVector4d.z(convert(z));
	}
	
	public static OptionalVector4d z(final OptionalDouble z) {
		return OptionalVector4d.of(empty(), empty(), z, empty());
	}
	
	public static OptionalVector4d w(final double w) {
		return OptionalVector4d.w(convert(w));
	}
	
	public static OptionalVector4d w(final @Nullable Double w) {
		return OptionalVector4d.w(convert(w));
	}
	
	public static OptionalVector4d w(final Optional<Double> w) {
		return OptionalVector4d.w(convert(w));
	}
	
	public static OptionalVector4d w(final OptionalDouble w) {
		return OptionalVector4d.of(empty(), empty(), empty(), w);
	}
	
	public static OptionalVector4d xy(final double x, final double y) {
		return OptionalVector4d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4d xy(final @Nullable Double x, final @Nullable Double y) {
		return OptionalVector4d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4d xy(final Optional<Double> x, final Optional<Double> y) {
		return OptionalVector4d.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4d xy(final OptionalDouble x, final OptionalDouble y) {
		return OptionalVector4d.of(x, y, empty(), empty());
	}
	
	public static OptionalVector4d xz(final double x, final double z) {
		return OptionalVector4d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4d xz(final @Nullable Double x, final @Nullable Double z) {
		return OptionalVector4d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4d xz(final Optional<Double> x, final Optional<Double> z) {
		return OptionalVector4d.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4d xz(final OptionalDouble x, final OptionalDouble z) {
		return OptionalVector4d.of(x, empty(), z, empty());
	}
	
	public static OptionalVector4d xw(final double x, final double w) {
		return OptionalVector4d.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4d xw(final @Nullable Double x, final @Nullable Double w) {
		return OptionalVector4d.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4d xw(final Optional<Double> x, final Optional<Double> w) {
		return OptionalVector4d.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4d xw(final OptionalDouble x, final OptionalDouble w) {
		return OptionalVector4d.of(x, empty(), empty(), w);
	}
	
	public static OptionalVector4d yz(final double y, final double z) {
		return OptionalVector4d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4d yz(final @Nullable Double y, final @Nullable Double z) {
		return OptionalVector4d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4d yz(final Optional<Double> y, final Optional<Double> z) {
		return OptionalVector4d.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4d yz(final OptionalDouble y, final OptionalDouble z) {
		return OptionalVector4d.of(empty(), y, z, empty());
	}
	
	public static OptionalVector4d yw(final double y, final double w) {
		return OptionalVector4d.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4d yw(final @Nullable Double y, final @Nullable Double w) {
		return OptionalVector4d.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4d yw(final Optional<Double> y, final Optional<Double> w) {
		return OptionalVector4d.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4d yw(final OptionalDouble y, final OptionalDouble w) {
		return OptionalVector4d.of(empty(), y, empty(), w);
	}
	
	public static OptionalVector4d zw(final double z, final double w) {
		return OptionalVector4d.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4d zw(final @Nullable Double z, final @Nullable Double w) {
		return OptionalVector4d.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4d zw(final Optional<Double> z, final Optional<Double> w) {
		return OptionalVector4d.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4d zw(final OptionalDouble z, final OptionalDouble w) {
		return OptionalVector4d.of(empty(), empty(), z, w);
	}
	
	public static OptionalVector4d xyz(final double x, final double y, final double z) {
		return OptionalVector4d.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4d xyz(final @Nullable Double x, final @Nullable Double y, final @Nullable Double z) {
		return OptionalVector4d.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4d xyz(final Optional<Double> x, final Optional<Double> y, final Optional<Double> z) {
		return OptionalVector4d.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4d xyz(final OptionalDouble x, final OptionalDouble y, final OptionalDouble z) {
		return OptionalVector4d.of(x, y, z, empty());
	}
	
	public static OptionalVector4d xyw(final double x, final double y, final double w) {
		return OptionalVector4d.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4d xyw(final @Nullable Double x, final @Nullable Double y, final @Nullable Double w) {
		return OptionalVector4d.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4d xyw(final Optional<Double> x, final Optional<Double> y, final Optional<Double> w) {
		return OptionalVector4d.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4d xyw(final OptionalDouble x, final OptionalDouble y, final OptionalDouble w) {
		return OptionalVector4d.of(x, y, empty(), w);
	}
	
	public static OptionalVector4d xzw(final double x, final double z, final double w) {
		return OptionalVector4d.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4d xzw(final @Nullable Double x, final @Nullable Double z, final @Nullable Double w) {
		return OptionalVector4d.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4d xzw(final Optional<Double> x, final Optional<Double> z, final Optional<Double> w) {
		return OptionalVector4d.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4d xzw(final OptionalDouble x, final OptionalDouble z, final OptionalDouble w) {
		return OptionalVector4d.of(x, empty(), z, w);
	}
	
	public static OptionalVector4d yzw(final double y, final double z, final double w) {
		return OptionalVector4d.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d yzw(final @Nullable Double y, final @Nullable Double z, final @Nullable Double w) {
		return OptionalVector4d.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d yzw(final Optional<Double> y, final Optional<Double> z, final Optional<Double> w) {
		return OptionalVector4d.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d yzw(final OptionalDouble y, final OptionalDouble z, final OptionalDouble w) {
		return OptionalVector4d.of(empty(), y, z, w);
	}
	
	public static OptionalVector4d of(final double x, final double y, final double z, final double w) {
		return OptionalVector4d.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d of(final @Nullable Double x, final @Nullable Double y, final @Nullable Double z, final @Nullable Double w) {
		return OptionalVector4d.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d of(final Optional<Double> x, final Optional<Double> y, final Optional<Double> z, final Optional<Double> w) {
		return OptionalVector4d.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4d of(final OptionalDouble x, final OptionalDouble y, final OptionalDouble z, final OptionalDouble w) {
		return new OptionalVector4d(x, y, z, w);
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
		return a.map(OptionalDouble::of).orElseGet(OptionalVector4d::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, double neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(double x, double y, double z, double w);
	}
}
