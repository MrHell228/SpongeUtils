package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4l;

import net.hellheim.spongetools.math.mutable.vector.MutableVector4l;

public final class OptionalVector4l {
	
	private final OptionalLong x;
	private final OptionalLong y;
	private final OptionalLong z;
	private final OptionalLong w;
	private final Transformer transformer;
	
	private OptionalVector4l(final OptionalLong x, final OptionalLong y, final OptionalLong z, final OptionalLong w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		if (this.x.isPresent()) {
			final long x0 = this.x.getAsLong();
			if (this.y.isPresent()) {
				final long y0 = this.y.getAsLong();
				if (this.z.isPresent()) {
					final long z0 = this.z.getAsLong();
					if (this.w.isPresent()) {
						final long w0 = this.w.getAsLong();
						this.transformer = (o, v) -> o.apply(x0, y0, z0, w0);
					} else {
						this.transformer = (o, v) -> o.apply(x0, y0, z0, v);
					}
				} else if (this.w.isPresent()) {
					final long w0 = this.w.getAsLong();
					this.transformer = (o, v) -> o.apply(x0, y0, v, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v, v);
				}
			} else if (this.z.isPresent()) {
				final long z0 = this.z.getAsLong();
				if (this.w.isPresent()) {
					final long w0 = this.w.getAsLong();
					this.transformer = (o, v) -> o.apply(x0, v, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, v, z0, v);
				}
			} else if (this.w.isPresent()) {
				final long w0 = this.w.getAsLong();
				this.transformer = (o, v) -> o.apply(x0, v, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v, v);
			}
		} else if (this.y.isPresent()) {
			final long y0 = this.y.getAsLong();
			if (this.z.isPresent()) {
				final long z0 = this.z.getAsLong();
				if (this.w.isPresent()) {
					final long w0 = this.w.getAsLong();
					this.transformer = (o, v) -> o.apply(v, y0, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(v, y0, z0, v);
				}
			} else if (this.w.isPresent()) {
				final long w0 = this.w.getAsLong();
				this.transformer = (o, v) -> o.apply(v, y0, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v, v);
			}
		} else if (this.z.isPresent()) {
			final long z0 = this.z.getAsLong();
			if (this.w.isPresent()) {
				final long w0 = this.w.getAsLong();
				this.transformer = (o, v) -> o.apply(v, v, z0, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, v, z0, v);
			}
		} else if (this.w.isPresent()) {
			final long w0 = this.w.getAsLong();
			this.transformer = (o, v) -> o.apply(v, v, v, w0);
		} else {
			this.transformer = (o, v) -> o;
		}
	}
	
	public OptionalLong x() {
		return this.x;
	}
	
	public OptionalLong y() {
		return this.y;
	}
	
	public OptionalLong z() {
		return this.z;
	}
	
	public OptionalLong w() {
		return this.w;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final long neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector4l set(final MutableVector4l v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		this.w.ifPresent(v::w);
		return v;
	}
	
	public Vector4l set(final Vector4l v) {
		final long x = this.x.orElse(v.x());
		final long y = this.y.orElse(v.y());
		final long z = this.z.orElse(v.z());
		final long w = this.w.orElse(v.w());
		return new Vector4l(x, y, z, w);
	}
	
	public MutableVector4l add(final MutableVector4l v) {
		return this.apply(v::add, 0);
	}
	
	public Vector4l add(final Vector4l v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector4l sub(final MutableVector4l v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector4l sub(final Vector4l v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector4l mul(final MutableVector4l v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector4l mul(final Vector4l v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector4l div(final MutableVector4l v) {
		return this.apply(v::div, 1);
	}
	
	public Vector4l div(final Vector4l v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector4l pow(final MutableVector4l v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector4l pow(final Vector4l v) {
		final long x = GenericMath.floorl(Math.pow(v.x(), this.x.orElse(1)));
		final long y = GenericMath.floorl(Math.pow(v.y(), this.y.orElse(1)));
		final long z = GenericMath.floorl(Math.pow(v.z(), this.z.orElse(1)));
		final long w = GenericMath.floorl(Math.pow(v.w(), this.w.orElse(1)));
		return new Vector4l(x, y, z, w);
	}
	
	public MutableVector4l min(final MutableVector4l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public Vector4l min(final Vector4l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public MutableVector4l max(final MutableVector4l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	public Vector4l max(final Vector4l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector4l that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsLong() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsLong() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsLong() : "?")
				+ "w=" + (this.w.isPresent() ? this.w.getAsLong() : "?")
				+ ")";
	}
	
	public static OptionalVector4l x(final long x) {
		return OptionalVector4l.x(convert(x));
	}
	
	public static OptionalVector4l x(final @Nullable Long x) {
		return OptionalVector4l.x(convert(x));
	}
	
	public static OptionalVector4l x(final Optional<Long> x) {
		return OptionalVector4l.x(convert(x));
	}
	
	public static OptionalVector4l x(final OptionalLong x) {
		return OptionalVector4l.of(x, empty(), empty(), empty());
	}
	
	public static OptionalVector4l y(final long y) {
		return OptionalVector4l.y(convert(y));
	}
	
	public static OptionalVector4l y(final @Nullable Long y) {
		return OptionalVector4l.y(convert(y));
	}
	
	public static OptionalVector4l y(final Optional<Long> y) {
		return OptionalVector4l.y(convert(y));
	}
	
	public static OptionalVector4l y(final OptionalLong y) {
		return OptionalVector4l.of(empty(), y, empty(), empty());
	}
	
	public static OptionalVector4l z(final long z) {
		return OptionalVector4l.z(convert(z));
	}
	
	public static OptionalVector4l z(final @Nullable Long z) {
		return OptionalVector4l.z(convert(z));
	}
	
	public static OptionalVector4l z(final Optional<Long> z) {
		return OptionalVector4l.z(convert(z));
	}
	
	public static OptionalVector4l z(final OptionalLong z) {
		return OptionalVector4l.of(empty(), empty(), z, empty());
	}
	
	public static OptionalVector4l w(final long w) {
		return OptionalVector4l.w(convert(w));
	}
	
	public static OptionalVector4l w(final @Nullable Long w) {
		return OptionalVector4l.w(convert(w));
	}
	
	public static OptionalVector4l w(final Optional<Long> w) {
		return OptionalVector4l.w(convert(w));
	}
	
	public static OptionalVector4l w(final OptionalLong w) {
		return OptionalVector4l.of(empty(), empty(), empty(), w);
	}
	
	public static OptionalVector4l xy(final long x, final long y) {
		return OptionalVector4l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4l xy(final @Nullable Long x, final @Nullable Long y) {
		return OptionalVector4l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4l xy(final Optional<Long> x, final Optional<Long> y) {
		return OptionalVector4l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4l xy(final OptionalLong x, final OptionalLong y) {
		return OptionalVector4l.of(x, y, empty(), empty());
	}
	
	public static OptionalVector4l xz(final long x, final long z) {
		return OptionalVector4l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4l xz(final @Nullable Long x, final @Nullable Long z) {
		return OptionalVector4l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4l xz(final Optional<Long> x, final Optional<Long> z) {
		return OptionalVector4l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4l xz(final OptionalLong x, final OptionalLong z) {
		return OptionalVector4l.of(x, empty(), z, empty());
	}
	
	public static OptionalVector4l xw(final long x, final long w) {
		return OptionalVector4l.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4l xw(final @Nullable Long x, final @Nullable Long w) {
		return OptionalVector4l.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4l xw(final Optional<Long> x, final Optional<Long> w) {
		return OptionalVector4l.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4l xw(final OptionalLong x, final OptionalLong w) {
		return OptionalVector4l.of(x, empty(), empty(), w);
	}
	
	public static OptionalVector4l yz(final long y, final long z) {
		return OptionalVector4l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4l yz(final @Nullable Long y, final @Nullable Long z) {
		return OptionalVector4l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4l yz(final Optional<Long> y, final Optional<Long> z) {
		return OptionalVector4l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4l yz(final OptionalLong y, final OptionalLong z) {
		return OptionalVector4l.of(empty(), y, z, empty());
	}
	
	public static OptionalVector4l yw(final long y, final long w) {
		return OptionalVector4l.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4l yw(final @Nullable Long y, final @Nullable Long w) {
		return OptionalVector4l.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4l yw(final Optional<Long> y, final Optional<Long> w) {
		return OptionalVector4l.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4l yw(final OptionalLong y, final OptionalLong w) {
		return OptionalVector4l.of(empty(), y, empty(), w);
	}
	
	public static OptionalVector4l zw(final long z, final long w) {
		return OptionalVector4l.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4l zw(final @Nullable Long z, final @Nullable Long w) {
		return OptionalVector4l.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4l zw(final Optional<Long> z, final Optional<Long> w) {
		return OptionalVector4l.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4l zw(final OptionalLong z, final OptionalLong w) {
		return OptionalVector4l.of(empty(), empty(), z, w);
	}
	
	public static OptionalVector4l xyz(final long x, final long y, final long z) {
		return OptionalVector4l.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4l xyz(final @Nullable Long x, final @Nullable Long y, final @Nullable Long z) {
		return OptionalVector4l.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4l xyz(final Optional<Long> x, final Optional<Long> y, final Optional<Long> z) {
		return OptionalVector4l.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4l xyz(final OptionalLong x, final OptionalLong y, final OptionalLong z) {
		return OptionalVector4l.of(x, y, z, empty());
	}
	
	public static OptionalVector4l xyw(final long x, final long y, final long w) {
		return OptionalVector4l.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4l xyw(final @Nullable Long x, final @Nullable Long y, final @Nullable Long w) {
		return OptionalVector4l.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4l xyw(final Optional<Long> x, final Optional<Long> y, final Optional<Long> w) {
		return OptionalVector4l.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4l xyw(final OptionalLong x, final OptionalLong y, final OptionalLong w) {
		return OptionalVector4l.of(x, y, empty(), w);
	}
	
	public static OptionalVector4l xzw(final long x, final long z, final long w) {
		return OptionalVector4l.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4l xzw(final @Nullable Long x, final @Nullable Long z, final @Nullable Long w) {
		return OptionalVector4l.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4l xzw(final Optional<Long> x, final Optional<Long> z, final Optional<Long> w) {
		return OptionalVector4l.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4l xzw(final OptionalLong x, final OptionalLong z, final OptionalLong w) {
		return OptionalVector4l.of(x, empty(), z, w);
	}
	
	public static OptionalVector4l yzw(final long y, final long z, final long w) {
		return OptionalVector4l.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l yzw(final @Nullable Long y, final @Nullable Long z, final @Nullable Long w) {
		return OptionalVector4l.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l yzw(final Optional<Long> y, final Optional<Long> z, final Optional<Long> w) {
		return OptionalVector4l.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l yzw(final OptionalLong y, final OptionalLong z, final OptionalLong w) {
		return OptionalVector4l.of(empty(), y, z, w);
	}
	
	public static OptionalVector4l of(final long x, final long y, final long z, final long w) {
		return OptionalVector4l.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l of(final @Nullable Long x, final @Nullable Long y, final @Nullable Long z, final @Nullable Long w) {
		return OptionalVector4l.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l of(final Optional<Long> x, final Optional<Long> y, final Optional<Long> z, final Optional<Long> w) {
		return OptionalVector4l.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4l of(final OptionalLong x, final OptionalLong y, final OptionalLong z, final OptionalLong w) {
		return new OptionalVector4l(x, y, z, w);
	}
	
	private static OptionalLong empty() {
		return OptionalLong.empty();
	}
	
	private static OptionalLong convert(final long a) {
		return OptionalLong.of(a);
	}
	
	private static OptionalLong convert(final @Nullable Long a) {
		return a == null ? empty() : OptionalLong.of(a.longValue());
	}
	
	private static OptionalLong convert(final Optional<Long> a) {
		return a.map(OptionalLong::of).orElseGet(OptionalVector4l::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, long neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(long x, long y, long z, long w);
	}
}
