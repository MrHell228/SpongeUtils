package net.hellheim.spongetools.mixin.world.item;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @ModifyArg(
            method = "place",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private Entity spongetools$playPlaceSoundToPlayerForCustomBlocks(final Entity player) {
        return FakeableNetworkValueBridge.isNetworkFaked(this) ? null : player;
    }
}
