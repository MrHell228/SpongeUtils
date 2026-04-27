package net.hellheim.spongetools.mixin.world.effect;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.MobEffectAttributeTemplateBridge;
import net.hellheim.spongetools.common.factory.AttributeModifierTemplateFactory;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.IntToDoubleFunction;

@Mixin(targets = "net.minecraft.world.effect.MobEffect$AttributeTemplate")
@Implements(@Interface(iface = AttributeModifierTemplate.class, prefix = "template$"))
public abstract class MobEffect_AttributeTemplateMixin implements AttributeModifierTemplate, MobEffectAttributeTemplateBridge {

    @Shadow @Final private Identifier id;
    @Shadow @Final private AttributeModifier.Operation operation;
    @Shadow @Final private double amount;

    @Shadow public abstract AttributeModifier shadow$create(int amplifier);

    @Unique private @Nullable IntToDoubleFunction spongetools$curve;

    @Override
    public ResourceKey key() {
        return Converter.asSponge(this.id);
    }

    @Intrinsic
    public AttributeOperation template$operation() {
        return Converter.asSponge(this.operation);
    }

    @Override
    public IntToDoubleFunction curve() {
        if (this.spongetools$curve == null) {
            this.spongetools$curve = AttributeModifierTemplateFactory.vanillaCurve(this.amount);
        }
        return this.spongetools$curve;
    }

    @Override
    public org.spongepowered.api.entity.attribute.AttributeModifier build(final int amplifier) {
        return Converter.asSponge(this.shadow$create(amplifier));
    }

    @Override
    public void spongetools$bridge$setCurve(final IntToDoubleFunction curve) {
        this.spongetools$curve = curve;
    }

    @WrapOperation(
            method = "create",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/resources/Identifier;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"
            )
    )
    private AttributeModifier spongetools$useCurve(
            final Identifier id, final double amount, final AttributeModifier.Operation operation,
            final Operation<AttributeModifier> original, final int amplifier
    ) {
        return original.call(id, this.curve().applyAsDouble(amplifier), operation);
    }
}
