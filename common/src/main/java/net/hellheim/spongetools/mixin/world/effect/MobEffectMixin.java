package net.hellheim.spongetools.mixin.world.effect;

import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.api.entity.attribute.type.AttributeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(MobEffect.class)
public abstract class MobEffectMixin implements MobEffectBridge {

    @Unique private final Map<Holder<Attribute>, AttributeModifierTemplate> spongetools$rawAttributeModifiers = new HashMap<>();
    @Unique private final Map<AttributeType, AttributeModifierTemplate> spongetools$attributeModifiers = new HashMap<>();
    @Unique private final Map<AttributeType, AttributeModifierTemplate> spongetools$attributeModifiersView = Collections.unmodifiableMap(this.spongetools$attributeModifiers);

    @Inject(method = "addAttributeModifier", at = @At("HEAD"))
    private void spongetools$addModifierTemplate(
            final Holder<Attribute> attribute, final Identifier id,
            final double amountPerLevel, final AttributeModifier.Operation operation,
            final CallbackInfoReturnable<MobEffect> cir
    ) {
        this.spongetools$rawAttributeModifiers.put(attribute, AttributeModifierTemplate.of(Converter.asSponge(id), (AttributeOperation) (Object) operation, amountPerLevel));
    }

    @Override
    public Map<AttributeType, AttributeModifierTemplate> spongetools$bridge$modifierTemplates() {
        if (this.spongetools$rawAttributeModifiers.size() != this.spongetools$attributeModifiers.size()) {
            this.spongetools$attributeModifiers.clear();
            this.spongetools$rawAttributeModifiers.forEach((attribute, template) ->
                    this.spongetools$attributeModifiers.put((AttributeType) attribute.value(), template));
        }
        return this.spongetools$attributeModifiersView;
    }
}
