package net.hellheim.spongetools.mixin.world.effect;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.MobEffectAttributeTemplateBridge;
import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;

@Mixin(MobEffect.class)
public abstract class MobEffectMixin implements MobEffectBridge {

    @Shadow public abstract MobEffect shadow$addAttributeModifier(Holder<Attribute> attribute, Identifier id, double amount, AttributeModifier.Operation operation);

    @Shadow @Final private Map<Holder<Attribute>, Object> attributeModifiers;
    @Shadow private Optional<SoundEvent> soundOnAdded;

    @Override
    public Map<AttributeType, AttributeModifierTemplate> spongetools$bridge$modifierTemplates() {
        return this.attributeModifiers.entrySet().stream().collect(Collectors.toMap(
                e -> (AttributeType) e.getKey().value(),
                e -> (AttributeModifierTemplate) e.getValue()));
    }

    @Override
    public void spongetools$bridge$setModifierTemplates(
            final Map<AttributeType, AttributeModifierTemplate> attributes
    ) {
        this.attributeModifiers.clear();
        attributes.forEach((type, template) -> this.attributeModifiers.put(
                BuiltInRegistries.ATTRIBUTE.wrapAsHolder(((Attribute) type)),
                template));
    }

    @Override
    public Optional<SoundType> spongetools$bridge$sound() {
        return this.soundOnAdded.map(Converter::asSponge);
    }

    @Override
    public void spongetools$bridge$setSound(final Optional<SoundType> sound) {
        this.soundOnAdded = sound.map(Converter::asVanilla);
    }

    @Override
    public AttributeModifierTemplate spongetools$bridge$assembleTemplate(
            final ResourceKey key, final AttributeOperation operation, final IntToDoubleFunction curve
    ) {
        final var attribute = Attributes.FOLLOW_RANGE;
        this.shadow$addAttributeModifier(attribute, Converter.asVanilla(key), curve.applyAsDouble(0), Converter.asVanilla(operation));
        final Object template = this.attributeModifiers.remove(attribute);
        ((MobEffectAttributeTemplateBridge) template).spongetools$bridge$setCurve(curve);
        return (AttributeModifierTemplate) template;
    }
}
