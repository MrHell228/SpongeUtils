package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector3i;

import net.hellheim.spongetools.math.mutable.vector.MutableVector3i;

public final class OptionalVector3i {
	
	private final OptionalInt x;
	private final OptionalInt y;
	private final OptionalInt z;
	private final Transformer transformer;
	
	private OptionalVector3i(final OptionalInt x, final OptionalInt y, final OptionalInt z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		if (this.x.isPresent()) {
			final int x0 = this.x.getAsInt();
			if (this.y.isPresent()) {
				final int y0 = this.y.getAsInt();
				if (this.z.isPresent()) {
					final int z0 = this.z.getAsInt();
					this.transformer = (o, v) -> o.apply(x0, y0, z0);
				} else {
					this.transformer = (o, v) -> o.apply(x0, y0, v);
				}
			} else if (this.z.isPresent()) {
				final int z0 = this.z.getAsInt();
				this.transformer = (o, v) -> o.apply(x0, v, z0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v, v);
			}
		} else if (this.y.isPresent()) {
			final int y0 = this.y.getAsInt();
			if (this.z.isPresent()) {
				final int z0 = this.z.getAsInt();
				this.transformer = (o, v) -> o.apply(v, y0, z0);
			} else {
				this.transformer = (o, v) -> o.apply(v, y0, v);
			}
		} else if (this.z.isPresent()) {
			final int z0 = this.z.getAsInt();
			this.transformer = (o, v) -> o.apply(v, v, z0);
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
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final int neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector3i set(final MutableVector3i v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		this.z.ifPresent(v::z);
		return v;
	}
	
	public Vector3i set(final Vector3i v) {
		final int x = this.x.orElse(v.x());
		final int y = this.y.orElse(v.y());
		final int z = this.z.orElse(v.z());
		return new Vector3i(x, y, z);
	}
	
	public MutableVector3i add(final MutableVector3i v) {
		return this.apply(v::add, 0);
	}
	
	public Vector3i add(final Vector3i v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector3i sub(final MutableVector3i v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector3i sub(final Vector3i v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector3i mul(final MutableVector3i v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector3i mul(final Vector3i v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector3i div(final MutableVector3i v) {
		return this.apply(v::div, 1);
	}
	
	public Vector3i div(final Vector3i v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector3i pow(final MutableVector3i v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector3i pow(final Vector3i v) {
		final int x = GenericMath.floor(Math.pow(v.x(), this.x.orElse(1)));
		final int y = GenericMath.floor(Math.pow(v.y(), this.y.orElse(1)));
		final int z = GenericMath.floor(Math.pow(v.z(), this.z.orElse(1)));
		return new Vector3i(x, y, z);
	}
	
	public MutableVector3i min(final MutableVector3i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public Vector3i min(final Vector3i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public MutableVector3i max(final MutableVector3i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	public Vector3i max(final Vector3i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector3i that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsInt() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsInt() : "?")
				+ "z=" + (this.z.isPresent() ? this.z.getAsInt() : "?")
				+ ")";
	}
	
	public static OptionalVector3i x(final int x) {
		return OptionalVector3i.x(convert(x));
	}
	
	public static OptionalVector3i x(final @Nullable Integer x) {
		return OptionalVector3i.x(convert(x));
	}
	
	public static OptionalVector3i x(final Optional<Integer> x) {
		return OptionalVector3i.x(convert(x));
	}
	
	public static OptionalVector3i x(final OptionalInt x) {
		return OptionalVector3i.of(x, empty(), empty());
	}
	
	public static OptionalVector3i y(final int y) {
		return OptionalVector3i.y(convert(y));
	}
	
	public static OptionalVector3i y(final @Nullable Integer y) {
		return OptionalVector3i.y(convert(y));
	}
	
	public static OptionalVector3i y(final Optional<Integer> y) {
		return OptionalVector3i.y(convert(y));
	}
	
	public static OptionalVector3i y(final OptionalInt y) {
		return OptionalVector3i.of(empty(), y, empty());
	}
	
	public static OptionalVector3i z(final int z) {
		return OptionalVector3i.z(convert(z));
	}
	
	public static OptionalVector3i z(final @Nullable Integer z) {
		return OptionalVector3i.z(convert(z));
	}
	
	public static OptionalVector3i z(final Optional<Integer> z) {
		return OptionalVector3i.z(convert(z));
	}
	
	public static OptionalVector3i z(final OptionalInt z) {
		return OptionalVector3i.of(empty(), empty(), z);
	}
	
	public static OptionalVector3i xy(final int x, final int y) {
		return OptionalVector3i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3i xy(final @Nullable Integer x, final @Nullable Integer y) {
		return OptionalVector3i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3i xy(final Optional<Integer> x, final Optional<Integer> y) {
		return OptionalVector3i.xy(convert(x), convert(y));
	}
	
	public static OptionalVector3i xy(final OptionalInt x, final OptionalInt y) {
		return OptionalVector3i.of(x, y, empty());
	}
	
	public static OptionalVector3i xz(final int x, final int z) {
		return OptionalVector3i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3i xz(final @Nullable Integer x, final @Nullable Integer z) {
		return OptionalVector3i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3i xz(final Optional<Integer> x, final Optional<Integer> z) {
		return OptionalVector3i.xz(convert(x), convert(z));
	}
	
	public static OptionalVector3i xz(final OptionalInt x, final OptionalInt z) {
		return OptionalVector3i.of(x, empty(), z);
	}
	
	public static OptionalVector3i yz(final int y, final int z) {
		return OptionalVector3i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3i yz(final @Nullable Integer y, final @Nullable Integer z) {
		return OptionalVector3i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3i yz(final Optional<Integer> y, final Optional<Integer> z) {
		return OptionalVector3i.yz(convert(y), convert(z));
	}
	
	public static OptionalVector3i yz(final OptionalInt y, final OptionalInt z) {
		return OptionalVector3i.of(empty(), y, z);
	}
	
	public static OptionalVector3i of(final int x, final int y, final int z) {
		return OptionalVector3i.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3i of(final @Nullable Integer x, final @Nullable Integer y, final @Nullable Integer z) {
		return OptionalVector3i.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3i of(final Optional<Integer> x, final Optional<Integer> y, final Optional<Integer> z) {
		return OptionalVector3i.of(convert(x), convert(y), convert(z));
	}
	
	public static OptionalVector3i of(final OptionalInt x, final OptionalInt y, final OptionalInt z) {
		return new OptionalVector3i(x, y, z);
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
		return a.map(OptionalInt::of).orElseGet(OptionalVector3i::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, int neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(int x, int y, int z);
	}
}
