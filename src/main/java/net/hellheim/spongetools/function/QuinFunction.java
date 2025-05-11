package net.hellheim.spongetools.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuinFunction<F, S, T, E, B, R> {
	
	R apply(F f, S s, T t, E e, B b);
	
	default <V> QuinFunction<F, S, T, E, B, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (F f, S s, T t, E e, B b) -> after.apply(apply(f, s, t, e, b));
    }
}
