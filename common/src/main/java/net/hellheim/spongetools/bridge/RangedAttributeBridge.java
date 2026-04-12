package net.hellheim.spongetools.bridge;

import java.util.function.DoubleUnaryOperator;

public interface RangedAttributeBridge extends AttributeBridge {
	
	void spongetools$bridge$modifyMin(DoubleUnaryOperator modifier);
	
	void spongetools$bridge$modifyMax(DoubleUnaryOperator modifier);
}
