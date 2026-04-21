package net.hellheim.spongetools.mixin.world.entity.ai.attributes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Attributes.class)
public abstract class AttributesMixin {

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/attributes/RangedAttribute;setSyncable(Z)Lnet/minecraft/world/entity/ai/attributes/Attribute;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=block_break_speed"
                    )
            )
    )
    private static Attribute spongetools$disableMiningSpeedSync(
            final RangedAttribute instance, final boolean sync, final Operation<Attribute> original
    ) {
        return original.call(instance, sync && !SpongeToolsPlugin.customMiningEnabled());
    }
}
