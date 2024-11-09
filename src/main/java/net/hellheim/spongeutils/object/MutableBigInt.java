package net.hellheim.spongeutils.object;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MutableBigInt implements MutableBigNumber {
	
	private BigInteger value;
	
	public MutableBigInt() {
		this(BigInteger.ZERO);
	}
	
	public MutableBigInt(final BigInteger value) {
		this.value = value;
	}
	
	public BigInteger get() {
		return this.value;
	}
	
	@Override
	public void set(final BigInteger num) {
		this.value = num;
	}
	
	@Override
	public void set(final BigDecimal num) {
		this.set(num.toBigInteger());
	}
	
	@Override
	public void add(final BigInteger num) {
		this.value = this.value.add(num);
	}
	
	@Override
	public void add(final BigDecimal num) {
		this.add(num.toBigInteger());
	}
	
	@Override
	public void subtract(final BigInteger num) {
		this.value = this.value.subtract(num);
	}
	
	@Override
	public void subtract(final BigDecimal num) {
		this.subtract(num.toBigInteger());
	}
	
	@Override
	public void mul(final BigInteger num) {
		this.value = this.value.multiply(num);
	}
	
	@Override
	public void mul(final BigDecimal num) {
		this.mul(num.toBigInteger());
	}
	
	public BigInteger subtractPart(final int percentage) {
		return this.subtractPart((double)percentage/100.0F);
	}
	
	public BigInteger subtractPart(final double fraction) {
		return this.subtractPart(BigDecimal.valueOf(fraction));
	}
	
	public BigInteger subtractPart(final BigDecimal fraction) {
		final BigInteger b = new BigDecimal(this.value).multiply(fraction).toBigInteger();
		this.subtract(b);
		return b;
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
	public int compareTo(final BigInteger num) {
		return this.value.compareTo(num);
	}
	
	@Override
	public int compareTo(final BigDecimal num) {
		return this.compareTo(num.toBigInteger());
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
		
		return this.value.equals(((MutableBigInt) obj).value);
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}
