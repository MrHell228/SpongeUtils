package net.hellheim.spongetools.mixin.world.effect;

import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.api.ResourceKey;
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

    @Unique private final Map<AttributeType, AttributeModifierTemplate> spongetools$attributeModifiers = new HashMap<>();
    @Unique private final Map<AttributeType, AttributeModifierTemplate> spongetools$attributeModifiersView = Collections.unmodifiableMap(this.spongetools$attributeModifiers);

    @Inject(method = "addAttributeModifier", at = @At("HEAD"))
    private void spongetools$addModifierTemplate(
            final Holder<Attribute> attribute, final ResourceLocation id,
            double amountPerLevel, AttributeModifier.Operation operation,
            CallbackInfoReturnable<MobEffect> cir
    ) {
        // TODO check if unpacking holder value is safe
        //this.spongetools$attributeModifiers.put((AttributeType) attribute.value(), AttributeModifierTemplate.of((ResourceKey) (Object) id, (AttributeOperation) (Object) operation, amountPerLevel));
    }

    @Override
    public Map<AttributeType, AttributeModifierTemplate> spongetools$bridge$modifierTemplates() {
        return this.spongetools$attributeModifiersView;
    }
}
