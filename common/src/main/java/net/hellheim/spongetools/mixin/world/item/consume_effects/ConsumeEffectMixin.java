package net.hellheim.spongetools.mixin.world.item.consume_effects;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.util.CustomConsumeEffect;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(ConsumeEffect.class)
public abstract class ConsumeEffectMixin {

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/item/consume_effects/ConsumeEffect;STREAM_CODEC:Lnet/minecraft/network/codec/StreamCodec;",
                    opcode = Opcodes.H_PUTFIELD
            )
    )
    private static void spongetools$adjustStreamCodec(
            final StreamCodec<RegistryFriendlyByteBuf, ConsumeEffect> value,
            final Operation<Void> original
    ) {
        original.call(value.map(
                effect -> effect.getType() != CustomConsumeEffect.TYPE
                        ? effect
                        : ClearAllStatusEffectsConsumeEffect.INSTANCE,
                Function.identity()));
    }
}
