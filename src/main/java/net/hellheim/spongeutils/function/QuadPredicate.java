package net.hellheim.spongeutils.function;

@FunctionalInterface
public interface QuadPredicate<F, S, T, E> {
	
	boolean test(F f, S s, T t, E e);
	
	default QuadPredicate<F, S, T, E> and(final QuadPredicate<? super F, ? super S, ? super T, ? super E> predicate) {
		return (f, s, t, e) -> this.test(f, s, t, e) && predicate.test(f, s, t, e);
	}
	
	default QuadPredicate<F, S, T, E> or(final QuadPredicate<? super F, ? super S, ? super T, ? super E> predicate) {
		return (f, s, t, e) -> this.test(f, s, t, e) || predicate.test(f, s, t, e);
	}
	
	default QuadPredicate<F, S, T, E> negate() {
		return (f, s, t, e) -> !this.test(f, s, t, e);
	}
}
