package net.hellheim.spongetools.mixin.world.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.hellheim.spongetools.bridge.BlockStateBaseBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    /*
    //@WrapOperation(method = "use", at = @At(value = "CONSTANT", args = "classValue=net/minecraft/world/level/block/BucketPickup"))
    //@Redirect(method = "use", at = @At(value = "CONSTANT", args = "classValue=net/minecraft/world/level/block/BucketPickup"))
    @ModifyConstant(method = "use", constant = @Constant(classValue = BucketPickup.class, ordinal = 0))
    private boolean spongetools$alwaysPass(Object block, Class<?> bucketPickup) {
        return true;
    }*/

    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            )
    )
    private Block spongetools$alwaysPassInstanceof(final BlockState instance) {
        return Blocks.IRON_CHAIN;
    }

    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/BucketPickup;pickupBlock(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack spongetools$redirect$bucketPickupItem(
            final BucketPickup instance,
            final LivingEntity user, final LevelAccessor accessor,
            final BlockPos pos, final BlockState state
    ) {
        return ((BlockStateBaseBridge) state).spongetools$bridge$bucketPickup$item(accessor, pos, user);
    }

    @Redirect(
            require = 0,
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/BucketPickup;getPickupSound()Ljava/util/Optional;"
            )
    )
    private Optional<SoundEvent> spongetools$redirect$bucketPickupSound$vanilla(
            final BucketPickup instance, final @Local(name = "blockState") BlockState state
    ) {
        return ((BlockStateBaseBridge) state).spongetools$bridge$bucketPickup$sound();
    }

    @Redirect(
            require = 0,
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/BucketPickup;getPickupSound()Ljava/util/Optional;"
            )
    )
    private Optional<SoundEvent> spongetools$redirect$bucketPickupSound$neo(
            final BucketPickup instance, final @Local(name = "blockState") BlockState state
    ) {
        return ((BlockStateBaseBridge) state).spongetools$bridge$bucketPickup$sound();
    }
}
