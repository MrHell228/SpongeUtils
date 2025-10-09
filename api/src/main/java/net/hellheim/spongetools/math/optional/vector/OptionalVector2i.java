package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2i;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2i;

public final class OptionalVector2i {
	
	private final OptionalInt x;
	private final OptionalInt y;
	private final Transformer transformer;
	
	private OptionalVector2i(final OptionalInt x, final OptionalInt y) {
		this.x = x;
		this.y = y;
		
		if (this.x.isPresent()) {
			final int x0 = this.x.getAsInt();
			if (this.y.isPresent()) {
				final int y0 = this.y.getAsInt();
				this.transformer = (o, v) -> o.apply(x0, y0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v);
			}
		} else if (this.y.isPresent()) {
			final int y0 = this.y.getAsInt();
			this.transformer = (o, v) -> o.apply(v, y0);
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
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final int neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector2i set(final MutableVector2i v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		return v;
	}
	
	public Vector2i set(final Vector2i v) {
		final int x = this.x.orElse(v.x());
		final int y = this.y.orElse(v.y());
		return new Vector2i(x, y);
	}
	
	public MutableVector2i add(final MutableVector2i v) {
		return this.apply(v::add, 0);
	}
	
	public Vector2i add(final Vector2i v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector2i sub(final MutableVector2i v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector2i sub(final Vector2i v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector2i mul(final MutableVector2i v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector2i mul(final Vector2i v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector2i div(final MutableVector2i v) {
		return this.apply(v::div, 1);
	}
	
	public Vector2i div(final Vector2i v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector2i pow(final MutableVector2i v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector2i pow(final Vector2i v) {
		final int x = GenericMath.floor(Math.pow(v.x(), this.x.orElse(1)));
		final int y = GenericMath.floor(Math.pow(v.y(), this.y.orElse(1)));
		return new Vector2i(x, y);
	}
	
	public MutableVector2i min(final MutableVector2i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public Vector2i min(final Vector2i v) {
		return this.apply(v::min, Integer.MAX_VALUE);
	}
	
	public MutableVector2i max(final MutableVector2i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	public Vector2i max(final Vector2i v) {
		return this.apply(v::max, Integer.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector2i that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsInt() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsInt() : "?")
				+ ")";
	}
	
	public static OptionalVector2i x(final int x) {
		return OptionalVector2i.x(convert(x));
	}
	
	public static OptionalVector2i x(final @Nullable Integer x) {
		return OptionalVector2i.x(convert(x));
	}
	
	public static OptionalVector2i x(final Optional<Integer> x) {
		return OptionalVector2i.x(convert(x));
	}
	
	public static OptionalVector2i x(final OptionalInt x) {
		return OptionalVector2i.of(x, empty());
	}
	
	public static OptionalVector2i y(final int y) {
		return OptionalVector2i.y(convert(y));
	}
	
	public static OptionalVector2i y(final @Nullable Integer y) {
		return OptionalVector2i.y(convert(y));
	}
	
	public static OptionalVector2i y(final Optional<Integer> y) {
		return OptionalVector2i.y(convert(y));
	}
	
	public static OptionalVector2i y(final OptionalInt y) {
		return OptionalVector2i.of(empty(), y);
	}
	
	public static OptionalVector2i of(final int x, final int y) {
		return OptionalVector2i.of(convert(x), convert(y));
	}
	
	public static OptionalVector2i of(final @Nullable Integer x, final @Nullable Integer y) {
		return OptionalVector2i.of(convert(x), convert(y));
	}
	
	public static OptionalVector2i of(final Optional<Integer> x, final Optional<Integer> y) {
		return OptionalVector2i.of(convert(x), convert(y));
	}
	
	public static OptionalVector2i of(final OptionalInt x, final OptionalInt y) {
		return new OptionalVector2i(x, y);
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
		return a.map(OptionalInt::of).orElseGet(OptionalVector2i::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, int neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(int x, int y);
	}
}
