package net.hellheim.spongetools.mixin.world.entity.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.bytebuddy.jar.asm.Opcodes;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @WrapOperation(
            method = {
                    // Vanilla
                    "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F",
                    // Neoforge
                    "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)F"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectUtil;hasDigSpeed(Lnet/minecraft/world/entity/LivingEntity;)Z"
            ),
            require = 1
    )
    private boolean spongetools$disableVanillaEffectDigSpeedIncrease(
            final LivingEntity mob, final Operation<Boolean> original
    ) {
        return !SpongeToolsPlugin.customMiningEnabled() && original.call(mob);
    }

    @WrapOperation(
            method = {
                    // Vanilla
                    "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F",
                    // Neoforge
                    "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)F"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/world/effect/MobEffects;MINING_FATIGUE:Lnet/minecraft/core/Holder;",
                            opcode = Opcodes.GETSTATIC
                    )
            ),
            require = 1
    )
    private boolean spongetools$disableVanillaEffectDigSpeedDecrease(
            final Player instance, final Holder<MobEffect> holder, final Operation<Boolean> original
    ) {
        return !SpongeToolsPlugin.customMiningEnabled() && original.call(instance, holder);
    }
}
