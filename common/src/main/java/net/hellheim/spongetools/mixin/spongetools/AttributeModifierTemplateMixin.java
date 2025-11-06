package net.hellheim.spongetools.mixin.spongetools;

import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.attribute.AttributeModifier;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AttributeModifierTemplate.class)
public abstract class AttributeModifierTemplateMixin {

    @Shadow @Final private ResourceKey key;
    @Shadow @Final private AttributeOperation operation;
    @Shadow @Final private double amountPerLevel;

    /**
     * @author MrHell228
     * @reason To make modifier creation faster, builder usage is eliminated.
     */
    @Overwrite
    public AttributeModifier build(final int amplifier) {
        return (AttributeModifier) (Object) new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                (ResourceLocation) (Object) this.key,
                this.amountPerLevel * (amplifier + 1),
                (net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation) (Object) this.operation
        );
    }
}
