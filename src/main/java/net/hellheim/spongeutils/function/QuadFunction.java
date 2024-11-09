package net.hellheim.spongeutils.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<F, S, T, E, R> {
	
	R apply(F f, S s, T t, E e);
	
	default <V> QuadFunction<F, S, T, E, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (F f, S s, T t, E e) -> after.apply(apply(f, s, t, e));
    }
}
