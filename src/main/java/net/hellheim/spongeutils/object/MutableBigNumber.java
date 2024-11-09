package net.hellheim.spongeutils.object;

import java.math.BigDecimal;
import java.math.BigInteger;

interface MutableBigNumber {
	
	default void set(final MutableBigInt num) {
		this.set(num.get());
	}
	
	default void set(final MutableBigDec num) {
		this.set(num.get());
	}
	
	void set(BigInteger num);
	
	void set(BigDecimal num);
	
	default void add(final MutableBigInt num) {
		this.add(num.get());
	}
	
	default void add(final MutableBigDec num) {
		this.add(num.get());
	}
	
	void add(BigInteger num);
	
	void add(BigDecimal num);
	
	default void subtract(final MutableBigInt num) {
		this.subtract(num.get());
	}
	
	default void subtract(final MutableBigDec num) {
		this.subtract(num.get());
	}
	
	void subtract(BigInteger num);
	
	void subtract(BigDecimal num);
	
	default void mul(final MutableBigInt num) {
		this.mul(num.get());
	}
	
	default void mul(final MutableBigDec num) {
		this.mul(num.get());
	}
	
	void mul(BigInteger num);
	
	void mul(BigDecimal num);
	
	default int compareTo(final MutableBigInt num) {
		return this.compareTo(num.get());
	}
	
	default int compareTo(final MutableBigDec num) {
		return this.compareTo(num.get());
	}
	
	int compareTo(BigInteger num);
	
	int compareTo(BigDecimal num);
	
	default void clamp(final BigInteger lower, final BigInteger upper) {
		if (this.compareTo(lower) < 0) {
			this.set(lower);
		} else if (this.compareTo(upper) > 0) {
			this.set(upper);
		}
	}
	
	default void clamp(final BigDecimal lower, final BigDecimal upper) {
		if (this.compareTo(lower) < 0) {
			this.set(lower);
		} else if (this.compareTo(upper) > 0) {
			this.set(upper);
		}
	}
	
	default void min(final MutableBigInt num) {
		this.min(num.get());
	}
	
	default void min(final MutableBigDec num) {
		this.min(num.get());
	}
	
	default void min(final BigInteger num) {
		if (this.compareTo(num) > 0) {
			this.set(num);
		}
	}
	
	default void min(final BigDecimal num) {
		if (this.compareTo(num) > 0) {
			this.set(num);
		}
	}
	
	default void max(final MutableBigInt num) {
		this.max(num.get());
	}
	
	default void max(final MutableBigDec num) {
		this.max(num.get());
	}
	
	default void max(final BigInteger num) {
		if (this.compareTo(num) < 0) {
			this.set(num);
		}
	}
	
	default void max(final BigDecimal num) {
		if (this.compareTo(num) < 0) {
			this.set(num);
		}
	}
	
	default boolean less(final BigInteger num) {
		return this.compareTo(num) < 0;
	}
	
	default boolean less(final BigDecimal num) {
		return this.compareTo(num) < 0;
	}
	
	default boolean more(final BigInteger num) {
		return this.compareTo(num) > 0;
	}
	
	default boolean more(final BigDecimal num) {
		return this.compareTo(num) > 0;
	}
	
	default boolean isZero() {
		return this.signum() == 0;
	}
	
	default boolean isPositive() {
		return this.signum() == 1;
	}
	
	default boolean isNegative() {
		return this.signum() == -1;
	}
	
	int signum();
	
	void negate();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object obj);
	
	@Override
	String toString();
}
