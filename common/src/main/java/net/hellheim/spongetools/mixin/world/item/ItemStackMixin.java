package net.hellheim.spongetools.mixin.world.item;

import net.hellheim.spongetools.bridge.ItemBridge;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item shadow$getItem();

    @Shadow @Final private PatchedDataComponentMap components;

    @Inject(
            method = "<init>(Lnet/minecraft/core/Holder;ILnet/minecraft/core/component/PatchedDataComponentMap;)V",
            at = @At("RETURN")
    )
    private void spongetools$applyPatch(final CallbackInfo ci) {
        final ItemTypeUtil.@Nullable AdditionalData data = ((ItemBridge) this.shadow$getItem()).spongetools$bridge$getData();
        if (data != null) {
            this.components.applyPatch(data.patch().get());
        }
    }
}
