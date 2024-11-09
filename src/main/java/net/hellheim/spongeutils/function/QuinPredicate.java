package net.hellheim.spongeutils.function;

public interface QuinPredicate<F, S, T, E, H> {
	
	boolean test(F f, S s, T t, E e, H h);
	
	default QuinPredicate<F, S, T, E, H> and(final QuinPredicate<? super F, ? super S, ? super T, ? super E, ? super H> predicate) {
		return (f, s, t, e, h) -> this.test(f, s, t, e, h) && predicate.test(f, s, t, e, h);
	}
	
	default QuinPredicate<F, S, T, E, H> or(final QuinPredicate<? super F, ? super S, ? super T, ? super E, ? super H> predicate) {
		return (f, s, t, e, h) -> this.test(f, s, t, e, h) || predicate.test(f, s, t, e, h);
	}
	
	default QuinPredicate<F, S, T, E, H> negate() {
		return (f, s, t, e, h) -> !this.test(f, s, t, e, h);
	}
}
