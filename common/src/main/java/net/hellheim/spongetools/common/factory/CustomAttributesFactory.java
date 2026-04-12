package net.hellheim.spongetools.common.factory;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

import org.spongepowered.api.entity.attribute.type.AttributeType;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.hellheim.spongetools.bridge.RangedAttributeBridge;
import net.hellheim.spongetools.custom.type.entity.CustomAttributes;

public final class CustomAttributesFactory implements CustomAttributes.Factory {
	
	@Override
	public void modify(
		final AttributeType type,
		final DoubleUnaryOperator baseModifier,
		final DoubleUnaryOperator minModifier,
		final DoubleUnaryOperator maxModifier
	) {
		Objects.requireNonNull(type, "type");
		Objects.requireNonNull(baseModifier, "baseModifier");
		Objects.requireNonNull(minModifier, "minModifier");
		Objects.requireNonNull(maxModifier, "maxModifier");
		((AttributeBridge) type).spongetools$bridge$modifyBase(baseModifier);
		if (type instanceof final RangedAttributeBridge bridge) {
			bridge.spongetools$bridge$modifyMin(minModifier);
			bridge.spongetools$bridge$modifyMax(maxModifier);
		}
	}
}
