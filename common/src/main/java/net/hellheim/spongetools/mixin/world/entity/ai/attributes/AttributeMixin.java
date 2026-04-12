package net.hellheim.spongetools.mixin.world.entity.ai.attributes;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.DoubleUnaryOperator;

@Mixin(Attribute.class)
public abstract class AttributeMixin implements AttributeBridge {

    @Shadow @Final @Mutable private double defaultValue;

    @Override
    public void spongetools$bridge$modifyBase(final DoubleUnaryOperator modifier) {
        this.defaultValue = modifier.applyAsDouble(this.defaultValue);
    }
}
