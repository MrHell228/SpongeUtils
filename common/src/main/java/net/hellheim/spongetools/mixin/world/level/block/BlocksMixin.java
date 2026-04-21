package net.hellheim.spongetools.mixin.world.level.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @WrapOperation(method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=obsidian"
                    )
            )
    )
    private static BlockBehaviour.Properties spongetools$setPushReaction$obsidian(
            final Operation<BlockBehaviour.Properties> original
    ) {
        return original.call().pushReaction(PushReaction.BLOCK);
    }

    @WrapOperation(method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=crying_obsidian"
                    )
            )
    )
    private static BlockBehaviour.Properties spongetools$setPushReaction$cryingObsidian(
            final Operation<BlockBehaviour.Properties> original
    ) {
        return original.call().pushReaction(PushReaction.BLOCK);
    }

    @WrapOperation(method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=respawn_anchor"
                    )
            )
    )
    private static BlockBehaviour.Properties spongetools$setPushReaction$respawnAnchor(
            final Operation<BlockBehaviour.Properties> original
    ) {
        return original.call().pushReaction(PushReaction.BLOCK);
    }

    @WrapOperation(method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=reinforced_deepslate"
                    )
            )
    )
    private static BlockBehaviour.Properties spongetools$setPushReaction$reinforcedDeepslate(
            final Operation<BlockBehaviour.Properties> original
    ) {
        return original.call().pushReaction(PushReaction.BLOCK);
    }
}
