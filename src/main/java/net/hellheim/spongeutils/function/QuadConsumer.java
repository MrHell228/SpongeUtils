package net.hellheim.spongeutils.function;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<F, S, T, E> {
	
	void accept(F f, S s, T t, E e);
	
	default QuadConsumer<F, S, T, E> andThen(final QuadConsumer<? super F, ? super S, ? super T, ? super E> after) {
        Objects.requireNonNull(after);
        return (f, s, t, e) -> {
            this.accept(f, s, t, e);
            after.accept(f, s, t, e);
        };
    }
}
