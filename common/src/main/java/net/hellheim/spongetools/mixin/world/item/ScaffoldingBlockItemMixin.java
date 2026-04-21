package net.hellheim.spongetools.mixin.world.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.ScaffoldingBlockBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScaffoldingBlockItem.class)
public abstract class ScaffoldingBlockItemMixin {

    @Unique private int spongetools$maxDistance;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void spongetools$setCustomMaxDistance(
            final Block block, final Item.Properties properties, final CallbackInfo ci
    ) {
        this.spongetools$maxDistance = block instanceof final ScaffoldingBlockBridge scaffolding
                ? scaffolding.spongetools$bridge$getMaxDistance()
                : -1;
    }

    @WrapOperation(
            method = "updatePlacementContext",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/ScaffoldingBlock;getDistance(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I"
            )
    )
    private int spongetools$useCustomDistanceCalculator(
            final BlockGetter getter, final BlockPos pos, final Operation<Integer> original
    ) {
        return ((BlockItem) (Object) this).getBlock() instanceof final ScaffoldingBlockBridge scaffolding
                ? scaffolding.spongetools$bridge$getDistance(getter, pos, () -> original.call(getter, pos))
                : original.call(getter, pos);
    }

    @ModifyConstant(
            method = "updatePlacementContext",
            constant = @Constant(intValue = ScaffoldingBlock.STABILITY_MAX_DISTANCE)
    )
    private int spongetools$useCustomMaxDistance(final int constant) {
        return this.spongetools$maxDistance == -1 ? constant : this.spongetools$maxDistance;
    }
}
