package net.hellheim.spongetools.mixin.world.entity.ai.attributes;

import net.hellheim.spongetools.bridge.RangedAttributeBridge;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.DoubleUnaryOperator;

@Mixin(RangedAttribute.class)
public abstract class RangedAttributeMixin extends AttributeMixin implements RangedAttributeBridge {

    @Shadow @Final @Mutable private double minValue;
    @Shadow @Final @Mutable private double maxValue;

    @Override
    public void spongetools$bridge$modifyMin(final DoubleUnaryOperator modifier) {
        this.minValue = modifier.applyAsDouble(this.minValue);
    }

    @Override
    public void spongetools$bridge$modifyMax(final DoubleUnaryOperator modifier) {
        this.maxValue = modifier.applyAsDouble(this.maxValue);
    }
}
