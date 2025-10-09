package net.hellheim.spongetools.mixin.world.level.block.piston;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin {

    @Redirect(method = "isPushable",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;OBSIDIAN:Lnet/minecraft/world/level/block/Block;"
            )
    )
    private static Block spongetools$unhardcodePushReaction$obsidian() {
        return Blocks.AIR;
    }

    @Redirect(method = "isPushable",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;CRYING_OBSIDIAN:Lnet/minecraft/world/level/block/Block;"
            )
    )
    private static Block spongetools$unhardcodePushReaction$cryingObsidian() {
        return Blocks.AIR;
    }

    @Redirect(method = "isPushable",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;RESPAWN_ANCHOR:Lnet/minecraft/world/level/block/Block;"
            )
    )
    private static Block spongetools$unhardcodePushReaction$respawnAnchor() {
        return Blocks.AIR;
    }

    @Redirect(method = "isPushable",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;REINFORCED_DEEPSLATE:Lnet/minecraft/world/level/block/Block;"
            )
    )
    private static Block spongetools$unhardcodePushReaction$reinforcedDeepslate() {
        return Blocks.AIR;
    }
}
