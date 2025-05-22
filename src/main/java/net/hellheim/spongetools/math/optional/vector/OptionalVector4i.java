package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector4i;

import net.hellheim.spongetools.math.mutable.vector.MutableVector4i;

public final class OptionalVector4i {
	
	private final OptionalInt x;
	private final OptionalInt y;
	private final OptionalInt z;
	private final OptionalInt w;
	private final Transformer transformer;
	
	private OptionalVector4i(final OptionalInt x, final OptionalInt y, final OptionalInt z, final OptionalInt w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		if (this.x.isPresent()) {
			final int x0 = this.x.getAsInt();
			if (this.y.isPresent()) {
				final int y0 = this.y.getAsInt();
				if (this.z.isPresent()) {
					final int z0 = this.z.getAsInt();
					if (this.w.isPresent()) {
						final int w0 = this.w.getAsInt();
						this.transformer = (o, v) -> o.apply(x0, y0, z0, w0);
					} else {
						this.transformer = (o, v) -> o.apply(x0, y0, z0, v);
					}
				} else if (this.w.isPresent()) {
					final int w0 = this.w.getAsInt();
					this.transformer = (o, v) -> o.apply(x0, y0, v, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v, v);
				}
			} else if (this.z.isPresent()) {
				final int z0 = this.z.getAsInt();
				if (this.w.isPresent()) {
					final int w0 = this.w.getAsInt();
					this.transformer = (o, v) -> o.apply(x0, v, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, v, z0, v);
				}
			} else if (this.w.isPresent()) {
				final int w0 = this.w.getAsInt();
				this.transformer = (o, v) -> o.apply(x0, v, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v, v);
			}
		} else if (this.y.isPresent()) {
			final int y0 = this.y.getAsInt();
			if (this.z.isPresent()) {
				final int z0 = this.z.getAsInt();
				if (this.w.isPresent()) {
					final int w0 = this.w.getAsInt();
					this.transformer = (o, v) -> o.apply(v, y0, z0, w0);
				} else {
					this.transformer = (o, v) -> o.apply(v, y0, z0, v);
				}
			} else if (this.w.isPresent()) {
				final int w0 = this.w.getAsInt();
				this.transformer = (o, v) -> o.apply(v, y0, v, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v, v);
			}
		} else if (this.z.isPresent()) {
			final int z0 = this.z.getAsInt();
			if (this.w.isPresent()) {
				final int w0 = this.w.getAsInt();
				this.transformer = (o, v) -> o.apply(v, v, z0, w0);
			} else {
				this.transformer = (o, v) -> o.apply(v, v, z0, v);
			}
		} else if (this.w.isPresent()) {
			final int w0 = this.w.getAsInt();
			this.transformer = (o, v) -> o.apply(v, v, v, w0);
		} else {
			this.transformer = (o, v) -> o;
		}
	}
	
	public OptionalInt x() {
		return this.x;
	}
	
	public OptionalInt y() {
		return this.y;
	}
	
	public OptionalInt z() {
		return this.z;
	}
	
	public OptionalInt w() {
		return this.w;
	}
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final int neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector4i set(final MutableVector4i v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		this.w.ifPresent(v::w);
		return v;
	}
	
	public Vector4i set(final Vector4i v) {
		final int x = this.x.orElse(v.x());
		final int y = this.y.orElse(v.y());
		final int z = this.z.orElse(v.z());
		final int w = this.w.orElse(v.w());
		return new Vector4i(x, y, z, w);
	}
	
	public MutableVector4i add(final MutableVector4i v) {
		return this.apply(v::add, 0);
	}
	
	public Vector4i add(final Vector4i v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector4i sub(final MutableVector4i v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector4i sub(final Vector4i v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector4i mul(final MutableVector4i v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector4i mul(final Vector4i v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector4i div(final MutableVector4i v) {
		return this.apply(v::div, 1);
	}
	
	public Vector4i div(final Vector4i v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector4i pow(final MutableVector4i v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector4i pow(final Vector4i v) {
		final int x = GenericMath.floor(Math.pow(v.x(), this.x.orElse(1)));
		final int y = GenericMath.floor(Math.pow(v.y(), this.y.orElse(1)));
		final int z = GenericMath.floor(Math.pow(v.z(), this.z.orElse(1)));
		final int w = GenericMath.floor(Math.pow(v.w(), this.w.orElse(1)));
		return new Vector4i(x, y, z, w);
	}
	
	public MutableVector4i min(final MutableVector4i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public Vector4i min(final Vector4i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public MutableVector4i max(final MutableVector4i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	public Vector4i max(final Vector4i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector4i that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsInt() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsInt() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsInt() : "?")
				+ "w=" + (this.w.isPresent() ? this.w.getAsInt() : "?")
				+ ")";
	}
	
	public static OptionalVector4i x(final int x) {
		return OptionalVector4i.x(convert(x));
	}
	
	public static OptionalVector4i x(final @Nullable Integer x) {
		return OptionalVector4i.x(convert(x));
	}
	
	public static OptionalVector4i x(final Optional<Integer> x) {
		return OptionalVector4i.x(convert(x));
	}
	
	public static OptionalVector4i x(final OptionalInt x) {
		return OptionalVector4i.of(x, empty(), empty(), empty());
	}
	
	public static OptionalVector4i y(final int y) {
		return OptionalVector4i.y(convert(y));
	}
	
	public static OptionalVector4i y(final @Nullable Integer y) {
		return OptionalVector4i.y(convert(y));
	}
	
	public static OptionalVector4i y(final Optional<Integer> y) {
		return OptionalVector4i.y(convert(y));
	}
	
	public static OptionalVector4i y(final OptionalInt y) {
		return OptionalVector4i.of(empty(), y, empty(), empty());
	}
	
	public static OptionalVector4i z(final int z) {
		return OptionalVector4i.z(convert(z));
	}
	
	public static OptionalVector4i z(final @Nullable Integer z) {
		return OptionalVector4i.z(convert(z));
	}
	
	public static OptionalVector4i z(final Optional<Integer> z) {
		return OptionalVector4i.z(convert(z));
	}
	
	public static OptionalVector4i z(final OptionalInt z) {
		return OptionalVector4i.of(empty(), empty(), z, empty());
	}
	
	public static OptionalVector4i w(final int w) {
		return OptionalVector4i.w(convert(w));
	}
	
	public static OptionalVector4i w(final @Nullable Integer w) {
		return OptionalVector4i.w(convert(w));
	}
	
	public static OptionalVector4i w(final Optional<Integer> w) {
		return OptionalVector4i.w(convert(w));
	}
	
	public static OptionalVector4i w(final OptionalInt w) {
		return OptionalVector4i.of(empty(), empty(), empty(), w);
	}
	
	public static OptionalVector4i xy(final int x, final int y) {
		return OptionalVector4i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4i xy(final @Nullable Integer x, final @Nullable Integer y) {
		return OptionalVector4i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4i xy(final Optional<Integer> x, final Optional<Integer> y) {
		return OptionalVector4i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector4i xy(final OptionalInt x, final OptionalInt y) {
		return OptionalVector4i.of(x, y, empty(), empty());
	}
	
	public static OptionalVector4i xz(final int x, final int z) {
		return OptionalVector4i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4i xz(final @Nullable Integer x, final @Nullable Integer z) {
		return OptionalVector4i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4i xz(final Optional<Integer> x, final Optional<Integer> z) {
		return OptionalVector4i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector4i xz(final OptionalInt x, final OptionalInt z) {
		return OptionalVector4i.of(x, empty(), z, empty());
	}
	
	public static OptionalVector4i xw(final int x, final int w) {
		return OptionalVector4i.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4i xw(final @Nullable Integer x, final @Nullable Integer w) {
		return OptionalVector4i.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4i xw(final Optional<Integer> x, final Optional<Integer> w) {
		return OptionalVector4i.xw(convert(x), convert(w));
	}
	
	public static OptionalVector4i xw(final OptionalInt x, final OptionalInt w) {
		return OptionalVector4i.of(x, empty(), empty(), w);
	}
	
	public static OptionalVector4i yz(final int y, final int z) {
		return OptionalVector4i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4i yz(final @Nullable Integer y, final @Nullable Integer z) {
		return OptionalVector4i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4i yz(final Optional<Integer> y, final Optional<Integer> z) {
		return OptionalVector4i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector4i yz(final OptionalInt y, final OptionalInt z) {
		return OptionalVector4i.of(empty(), y, z, empty());
	}
	
	public static OptionalVector4i yw(final int y, final int w) {
		return OptionalVector4i.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4i yw(final @Nullable Integer y, final @Nullable Integer w) {
		return OptionalVector4i.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4i yw(final Optional<Integer> y, final Optional<Integer> w) {
		return OptionalVector4i.yw(convert(y), convert(w));
	}
	
	public static OptionalVector4i yw(final OptionalInt y, final OptionalInt w) {
		return OptionalVector4i.of(empty(), y, empty(), w);
	}
	
	public static OptionalVector4i zw(final int z, final int w) {
		return OptionalVector4i.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4i zw(final @Nullable Integer z, final @Nullable Integer w) {
		return OptionalVector4i.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4i zw(final Optional<Integer> z, final Optional<Integer> w) {
		return OptionalVector4i.zw(convert(z), convert(w));
	}
	
	public static OptionalVector4i zw(final OptionalInt z, final OptionalInt w) {
		return OptionalVector4i.of(empty(), empty(), z, w);
	}
	
	public static OptionalVector4i xyz(final int x, final int y, final int z) {
		return OptionalVector4i.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4i xyz(final @Nullable Integer x, final @Nullable Integer y, final @Nullable Integer z) {
		return OptionalVector4i.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4i xyz(final Optional<Integer> x, final Optional<Integer> y, final Optional<Integer> z) {
		return OptionalVector4i.xyz(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector4i xyz(final OptionalInt x, final OptionalInt y, final OptionalInt z) {
		return OptionalVector4i.of(x, y, z, empty());
	}
	
	public static OptionalVector4i xyw(final int x, final int y, final int w) {
		return OptionalVector4i.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4i xyw(final @Nullable Integer x, final @Nullable Integer y, final @Nullable Integer w) {
		return OptionalVector4i.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4i xyw(final Optional<Integer> x, final Optional<Integer> y, final Optional<Integer> w) {
		return OptionalVector4i.xyw(convert(x), convert(y), convert(w));
	}
	
	public static OptionalVector4i xyw(final OptionalInt x, final OptionalInt y, final OptionalInt w) {
		return OptionalVector4i.of(x, y, empty(), w);
	}
	
	public static OptionalVector4i xzw(final int x, final int z, final int w) {
		return OptionalVector4i.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4i xzw(final @Nullable Integer x, final @Nullable Integer z, final @Nullable Integer w) {
		return OptionalVector4i.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4i xzw(final Optional<Integer> x, final Optional<Integer> z, final Optional<Integer> w) {
		return OptionalVector4i.xzw(convert(x), convert(z), convert(w));
	}
	
	public static OptionalVector4i xzw(final OptionalInt x, final OptionalInt z, final OptionalInt w) {
		return OptionalVector4i.of(x, empty(), z, w);
	}
	
	public static OptionalVector4i yzw(final int y, final int z, final int w) {
		return OptionalVector4i.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i yzw(final @Nullable Integer y, final @Nullable Integer z, final @Nullable Integer w) {
		return OptionalVector4i.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i yzw(final Optional<Integer> y, final Optional<Integer> z, final Optional<Integer> w) {
		return OptionalVector4i.yzw(convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i yzw(final OptionalInt y, final OptionalInt z, final OptionalInt w) {
		return OptionalVector4i.of(empty(), y, z, w);
	}
	
	public static OptionalVector4i of(final int x, final int y, final int z, final int w) {
		return OptionalVector4i.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i of(final @Nullable Integer x, final @Nullable Integer y, final @Nullable Integer z, final @Nullable Integer w) {
		return OptionalVector4i.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i of(final Optional<Integer> x, final Optional<Integer> y, final Optional<Integer> z, final Optional<Integer> w) {
		return OptionalVector4i.of(convert(x), convert(y), convert(z), convert(w));
	}
	
	public static OptionalVector4i of(final OptionalInt x, final OptionalInt y, final OptionalInt z, final OptionalInt w) {
		return new OptionalVector4i(x, y, z, w);
	}
	
	private static OptionalInt empty() {
		return OptionalInt.empty();
	}
	
	private static OptionalInt convert(final int a) {
		return OptionalInt.of(a);
	}
	
	private static OptionalInt convert(final @Nullable Integer a) {
		return a == null ? empty() : OptionalInt.of(a.intValue());
	}
	
	private static OptionalInt convert(final Optional<Integer> a) {
		return a.map(OptionalInt::of).orElseGet(OptionalVector4i::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, int neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(int x, int y, int z, int w);
	}
}
