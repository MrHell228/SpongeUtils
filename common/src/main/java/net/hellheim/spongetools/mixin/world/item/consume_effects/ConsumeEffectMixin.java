package net.hellheim.spongetools.mixin.world.item.consume_effects;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.util.CustomConsumeEffect;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(ConsumeEffect.class)
public interface ConsumeEffectMixin {

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/codec/StreamCodec;dispatch(Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/network/codec/StreamCodec;"
            )
    )
    private static StreamCodec<RegistryFriendlyByteBuf, ConsumeEffect> spongetools$adjustStreamCodec(
            final StreamCodec<RegistryFriendlyByteBuf, ConsumeEffect.Type<?>> instance,
            final Function<? super ConsumeEffect, ? extends ConsumeEffect.Type<?>> getType,
            final Function<? super ConsumeEffect.Type<?>, ? extends StreamCodec<? super RegistryFriendlyByteBuf, ? extends ConsumeEffect>> streamCodec,
            final Operation<StreamCodec<RegistryFriendlyByteBuf, ConsumeEffect>> original
    ) {
        return original.call(instance, getType, streamCodec).map(
                Function.identity(),
                effect -> effect.getType() != CustomConsumeEffect.TYPE
                        ? effect
                        : ClearAllStatusEffectsConsumeEffect.INSTANCE);
    }
}
