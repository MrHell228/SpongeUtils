package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3l;

import net.hellheim.spongetools.math.mutable.vector.MutableVector3l;

public final class OptionalVector3l {
	
	private final OptionalLong x;
	private final OptionalLong y;
	private final OptionalLong z;
	private final Transformer transformer;
	
	private OptionalVector3l(final OptionalLong x, final OptionalLong y, final OptionalLong z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		if (this.x.isPresent()) {
			final long x0 = this.x.getAsLong();
			if (this.y.isPresent()) {
				final long y0 = this.y.getAsLong();
				if (this.z.isPresent()) {
					final long z0 = this.z.getAsLong();
					this.transformer = (o, v) -> o.apply(x0, y0, z0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v);
				}
			} else if (this.z.isPresent()) {
				final long z0 = this.z.getAsLong();
				this.transformer = (o, v) -> o.apply(x0, v, z0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v);
			}
		} else if (this.y.isPresent()) {
			final long y0 = this.y.getAsLong();
			if (this.z.isPresent()) {
				final long z0 = this.z.getAsLong();
				this.transformer = (o, v) -> o.apply(v, y0, z0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v);
			}
		} else if (this.z.isPresent()) {
			final long z0 = this.z.getAsLong();
			this.transformer = (o, v) -> o.apply(v, v, z0);
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
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final long neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector3l set(final MutableVector3l v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		return v;
	}
	
	public Vector3l set(final Vector3l v) {
		final long x = this.x.orElse(v.x());
		final long y = this.y.orElse(v.y());
		final long z = this.z.orElse(v.z());
		return new Vector3l(x, y, z);
	}
	
	public MutableVector3l add(final MutableVector3l v) {
		return this.apply(v::add, 0);
	}
	
	public Vector3l add(final Vector3l v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector3l sub(final MutableVector3l v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector3l sub(final Vector3l v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector3l mul(final MutableVector3l v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector3l mul(final Vector3l v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector3l div(final MutableVector3l v) {
		return this.apply(v::div, 1);
	}
	
	public Vector3l div(final Vector3l v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector3l pow(final MutableVector3l v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector3l pow(final Vector3l v) {
		final long x = GenericMath.floorl(Math.pow(v.x(), this.x.orElse(1)));
		final long y = GenericMath.floorl(Math.pow(v.y(), this.y.orElse(1)));
		final long z = GenericMath.floorl(Math.pow(v.z(), this.z.orElse(1)));
		return new Vector3l(x, y, z);
	}
	
	public MutableVector3l min(final MutableVector3l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public Vector3l min(final Vector3l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public MutableVector3l max(final MutableVector3l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	public Vector3l max(final Vector3l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector3l that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsLong() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsLong() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsLong() : "?")
				+ ")";
	}
	
	public static OptionalVector3l x(final long x) {
		return OptionalVector3l.x(convert(x));
	}
	
	public static OptionalVector3l x(final @Nullable Long x) {
		return OptionalVector3l.x(convert(x));
	}
	
	public static OptionalVector3l x(final Optional<Long> x) {
		return OptionalVector3l.x(convert(x));
	}
	
	public static OptionalVector3l x(final OptionalLong x) {
		return OptionalVector3l.of(x, empty(), empty());
	}
	
	public static OptionalVector3l y(final long y) {
		return OptionalVector3l.y(convert(y));
	}
	
	public static OptionalVector3l y(final @Nullable Long y) {
		return OptionalVector3l.y(convert(y));
	}
	
	public static OptionalVector3l y(final Optional<Long> y) {
		return OptionalVector3l.y(convert(y));
	}
	
	public static OptionalVector3l y(final OptionalLong y) {
		return OptionalVector3l.of(empty(), y, empty());
	}
	
	public static OptionalVector3l z(final long z) {
		return OptionalVector3l.z(convert(z));
	}
	
	public static OptionalVector3l z(final @Nullable Long z) {
		return OptionalVector3l.z(convert(z));
	}
	
	public static OptionalVector3l z(final Optional<Long> z) {
		return OptionalVector3l.z(convert(z));
	}
	
	public static OptionalVector3l z(final OptionalLong z) {
		return OptionalVector3l.of(empty(), empty(), z);
	}
	
	public static OptionalVector3l xy(final long x, final long y) {
		return OptionalVector3l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3l xy(final @Nullable Long x, final @Nullable Long y) {
		return OptionalVector3l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3l xy(final Optional<Long> x, final Optional<Long> y) {
		return OptionalVector3l.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3l xy(final OptionalLong x, final OptionalLong y) {
		return OptionalVector3l.of(x, y, empty());
	}
	
	public static OptionalVector3l xz(final long x, final long z) {
		return OptionalVector3l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3l xz(final @Nullable Long x, final @Nullable Long z) {
		return OptionalVector3l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3l xz(final Optional<Long> x, final Optional<Long> z) {
		return OptionalVector3l.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3l xz(final OptionalLong x, final OptionalLong z) {
		return OptionalVector3l.of(x, empty(), z);
	}
	
	public static OptionalVector3l yz(final long y, final long z) {
		return OptionalVector3l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3l yz(final @Nullable Long y, final @Nullable Long z) {
		return OptionalVector3l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3l yz(final Optional<Long> y, final Optional<Long> z) {
		return OptionalVector3l.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3l yz(final OptionalLong y, final OptionalLong z) {
		return OptionalVector3l.of(empty(), y, z);
	}
	
	public static OptionalVector3l of(final long x, final long y, final long z) {
		return OptionalVector3l.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3l of(final @Nullable Long x, final @Nullable Long y, final @Nullable Long z) {
		return OptionalVector3l.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3l of(final Optional<Long> x, final Optional<Long> y, final Optional<Long> z) {
		return OptionalVector3l.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3l of(final OptionalLong x, final OptionalLong y, final OptionalLong z) {
		return new OptionalVector3l(x, y, z);
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
		return a.map(OptionalLong::of).orElseGet(OptionalVector3l::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, long neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(long x, long y, long z);
	}
}
