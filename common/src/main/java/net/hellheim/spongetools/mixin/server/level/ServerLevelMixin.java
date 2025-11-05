package net.hellheim.spongetools.mixin.server.level;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Inject(method = "levelEvent", at = @At(value = "HEAD"))
    private void spongetools$sendBlockBreakEffectsToBreaker(
            final CallbackInfo ci,
            final @Local(argsOnly = true) LocalRef<@Nullable Player> player,
            final @Local(argsOnly = true, ordinal = 0) int eventType
    ) {
        if (eventType == 2001 && SpongeToolsPlugin.customMiningEnabled()) {
            player.set(null);
        }
    }
}
