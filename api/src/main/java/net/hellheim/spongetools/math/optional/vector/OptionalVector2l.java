package net.hellheim.spongetools.math.optional.vector;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.math.GenericMath;
import org.spongepowered.math.vector.Vector2l;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2l;

public final class OptionalVector2l {
	
	private final OptionalLong x;
	private final OptionalLong y;
	private final Transformer transformer;
	
	private OptionalVector2l(final OptionalLong x, final OptionalLong y) {
		this.x = x;
		this.y = y;
		
		if (this.x.isPresent()) {
			final long x0 = this.x.getAsLong();
			if (this.y.isPresent()) {
				final long y0 = this.y.getAsLong();
				this.transformer = (o, v) -> o.apply(x0, y0);
			} else {
				this.transformer = (o, v) -> o.apply(x0, v);
			}
		} else if (this.y.isPresent()) {
			final long y0 = this.y.getAsLong();
			this.transformer = (o, v) -> o.apply(v, y0);
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
	
	@SuppressWarnings("unchecked")
	private <V> V apply(final Operator<V> operator, final long neutralValue) {
		return (V) this.transformer.apply(operator, neutralValue);
	}
	
	public MutableVector2l set(final MutableVector2l v) {
		this.x.ifPresent(v::x);
		this.y.ifPresent(v::y);
		return v;
	}
	
	public Vector2l set(final Vector2l v) {
		final long x = this.x.orElse(v.x());
		final long y = this.y.orElse(v.y());
		return new Vector2l(x, y);
	}
	
	public MutableVector2l add(final MutableVector2l v) {
		return this.apply(v::add, 0);
	}
	
	public Vector2l add(final Vector2l v) {
		return this.apply(v::add, 0);
	}
	
	public MutableVector2l sub(final MutableVector2l v) {
		return this.apply(v::sub, 0);
	}
	
	public Vector2l sub(final Vector2l v) {
		return this.apply(v::sub, 0);
	}
	
	public MutableVector2l mul(final MutableVector2l v) {
		return this.apply(v::mul, 1);
	}
	
	public Vector2l mul(final Vector2l v) {
		return this.apply(v::mul, 1);
	}
	
	public MutableVector2l div(final MutableVector2l v) {
		return this.apply(v::div, 1);
	}
	
	public Vector2l div(final Vector2l v) {
		return this.apply(v::div, 1);
	}
	
	public MutableVector2l pow(final MutableVector2l v) {
		return this.apply(v::pow, 1);
	}
	
	public Vector2l pow(final Vector2l v) {
		final long x = GenericMath.floorl(Math.pow(v.x(), this.x.orElse(1)));
		final long y = GenericMath.floorl(Math.pow(v.y(), this.y.orElse(1)));
		return new Vector2l(x, y);
	}
	
	public MutableVector2l min(final MutableVector2l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public Vector2l min(final Vector2l v) {
		return this.apply(v::min, Long.MAX_VALUE);
	}
	
	public MutableVector2l max(final MutableVector2l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	public Vector2l max(final Vector2l v) {
		return this.apply(v::max, Long.MIN_VALUE);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof final OptionalVector2l that)) {
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
				+ "x=" + (this.x.isPresent() ? this.x.getAsLong() : "?")
				+ "y=" + (this.y.isPresent() ? this.y.getAsLong() : "?")
				+ ")";
	}
	
	public static OptionalVector2l x(final long x) {
		return OptionalVector2l.x(convert(x));
	}
	
	public static OptionalVector2l x(final @Nullable Long x) {
		return OptionalVector2l.x(convert(x));
	}
	
	public static OptionalVector2l x(final Optional<Long> x) {
		return OptionalVector2l.x(convert(x));
	}
	
	public static OptionalVector2l x(final OptionalLong x) {
		return OptionalVector2l.of(x, empty());
	}
	
	public static OptionalVector2l y(final long y) {
		return OptionalVector2l.y(convert(y));
	}
	
	public static OptionalVector2l y(final @Nullable Long y) {
		return OptionalVector2l.y(convert(y));
	}
	
	public static OptionalVector2l y(final Optional<Long> y) {
		return OptionalVector2l.y(convert(y));
	}
	
	public static OptionalVector2l y(final OptionalLong y) {
		return OptionalVector2l.of(empty(), y);
	}
	
	public static OptionalVector2l of(final long x, final long y) {
		return OptionalVector2l.of(convert(x), convert(y));
	}
	
	public static OptionalVector2l of(final @Nullable Long x, final @Nullable Long y) {
		return OptionalVector2l.of(convert(x), convert(y));
	}
	
	public static OptionalVector2l of(final Optional<Long> x, final Optional<Long> y) {
		return OptionalVector2l.of(convert(x), convert(y));
	}
	
	public static OptionalVector2l of(final OptionalLong x, final OptionalLong y) {
		return new OptionalVector2l(x, y);
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
		return a.map(OptionalLong::of).orElseGet(OptionalVector2l::empty);
	}
	
	private interface Transformer {
		
		Object apply(Operator<?> operator, long neutralValue);
	}
	
	private interface Operator<V> {
		
		V apply(long x, long y);
	}
}
