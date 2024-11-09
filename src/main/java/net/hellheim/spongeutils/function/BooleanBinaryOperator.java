package net.hellheim.spongeutils.function;

@FunctionalInterface
public interface BooleanBinaryOperator {
	
	boolean applyAsBoolean(boolean b1, boolean b2);
	
	static final BooleanBinaryOperator AND = (b1, b2) -> b1 & b2;
	
	static final BooleanBinaryOperator OR = (b1, b2) -> b1 | b2;
	
	static final BooleanBinaryOperator XOR = (b1, b2) -> b1 ^ b2;
	
	static final BooleanBinaryOperator FIRST = (b1, b2) -> b1;
	
	static final BooleanBinaryOperator SECOND = (b1, b2) -> b2;
	
	static final BooleanBinaryOperator NOT_FIRST = (b1, b2) -> !b1;
	
	static final BooleanBinaryOperator NOT_SECOND = (b1, b2) -> !b2;
	
}
