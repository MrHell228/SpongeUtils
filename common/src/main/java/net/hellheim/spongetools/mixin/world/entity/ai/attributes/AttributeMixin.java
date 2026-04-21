package net.hellheim.spongetools.mixin.world.entity.ai.attributes;

import net.hellheim.spongetools.bridge.AttributeBridge;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.DoubleUnaryOperator;

@Mixin(Attribute.class)
public abstract class AttributeMixin implements AttributeBridge, FakeableNetworkValueBridge {

    @Shadow @Final @Mutable private double defaultValue;

    @Unique private boolean spongetools$custom = false;

    @Override
    public void spongetools$bridge$modifyBase(final DoubleUnaryOperator modifier) {
        this.defaultValue = modifier.applyAsDouble(this.defaultValue);
    }

    @Override
    public void spongetools$bridge$setCustom() {
        this.spongetools$custom = true;
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$custom ? Attributes.LUCK.value() : null;
    }
}
