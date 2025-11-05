package net.hellheim.spongetools.mixin.server.level;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.common.SpongeEngine;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {

    @Shadow public abstract boolean shadow$destroyBlock(BlockPos $$0);

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayerGameMode;incrementDestroyProgress(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;I)F",
                    ordinal = 1
            )
    )
    private float spongetools$breakBlockOnTick(
            final ServerPlayerGameMode instance, final BlockState state, final BlockPos pos, final int time,
            final Operation<Float> original
    ) {
        final float f = original.call(instance, state, pos, time);
        if (f >= 1.0F && SpongeToolsPlugin.customMiningEnabled()) {
            this.shadow$destroyBlock(pos);
        }
        return f;
    }

    @WrapOperation(
            method = {
                    "tick",
                    "incrementDestroyProgress",
                    "handleBlockBreakAction"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;destroyBlockProgress(ILnet/minecraft/core/BlockPos;I)V")
    )
    private void spongetools$sendDestroyProgress(
            final ServerLevel instance, final int playerId, final BlockPos pos, final int progressStage,
            final Operation<Void> original
    ) {
        original.call(
                instance,
                SpongeToolsPlugin.customMiningEnabled()
                    ? ((SpongeEngine) instance.getServer()).getBlockDestructionIdCache().getOrCreate(pos)
                    : playerId,
                pos,
                progressStage
        );
    }

    @WrapOperation(
            method = "handleBlockBreakAction",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroyProgress(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket$Action;STOP_DESTROY_BLOCK:Lnet/minecraft/network/protocol/game/ServerboundPlayerActionPacket$Action;"
                    )
            )
    )
    private float spongetools$doNotDestroyBlockOnPlayerStopBreaking(
            final BlockState instance, final Player player,
            final BlockGetter blockGetter, final BlockPos pos,
            final Operation<Float> original
    ) {
        return SpongeToolsPlugin.customMiningEnabled()
                ? 0
                : original.call(instance, player, blockGetter, pos);
    }
}
