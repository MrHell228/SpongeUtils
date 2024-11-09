package net.hellheim.spongeutils.function;

@FunctionalInterface
public interface TriPredicate<F, S, T> {
	
	boolean test(F f, S s, T t);
	
	default TriPredicate<F, S, T> and(final TriPredicate<? super F, ? super S, ? super T> predicate) {
		return (f, s, t) -> this.test(f, s, t) && predicate.test(f, s, t);
	}
	
	default TriPredicate<F, S, T> or(final TriPredicate<? super F, ? super S, ? super T> predicate) {
		return (f, s, t) -> this.test(f, s, t) || predicate.test(f, s, t);
	}
	
	default TriPredicate<F, S, T> negate() {
		return (f, s, t) -> !this.test(f, s, t);
	}
}
