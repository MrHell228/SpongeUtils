package net.hellheim.spongeutils.object;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MutableBigDec implements MutableBigNumber {
	
	private BigDecimal value;
	
	public MutableBigDec() {
		this(BigDecimal.ZERO);
	}
	
	public MutableBigDec(final BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal get() {
		return this.value;
	}
	
	@Override
	public void set(final BigInteger num) {
		this.set(toDecimal(num));
	}
	
	@Override
	public void set(final BigDecimal value) {
		this.value = value;
	}
	
	@Override
	public void add(final BigInteger num) {
		this.add(toDecimal(num));
	}
	
	@Override
	public void add(final BigDecimal num) {
		this.value = this.value.add(num);
	}
	
	@Override
	public void subtract(final BigInteger num) {
		this.subtract(toDecimal(num));
	}
	
	@Override
	public void subtract(final BigDecimal num) {
		this.value = this.value.subtract(num);
	}
	
	@Override
	public void mul(final BigInteger num) {
		this.mul(toDecimal(num));
	}
	
	@Override
	public void mul(final BigDecimal num) {
		this.value = this.value.multiply(num);
	}
	
	@Override
	public int compareTo(final BigInteger num) {
		return this.compareTo(toDecimal(num));
	}
	
	@Override
	public int compareTo(final BigDecimal num) {
		return this.value.compareTo(num);
	}
	
	@Override
	public int signum() {
		return this.value.signum();
	}
	
	@Override
	public void negate() {
		this.value = this.value.negate();
	}
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		return this.value.equals(((MutableBigDec) obj).value);
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
	
	public BigInteger intValue() {
		return this.value.toBigInteger();
	}
	
	private static BigDecimal toDecimal(final BigInteger num) {
		return new BigDecimal(num);
	}
}
