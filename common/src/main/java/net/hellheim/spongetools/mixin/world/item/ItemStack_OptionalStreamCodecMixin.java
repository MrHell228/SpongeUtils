package net.hellheim.spongetools.mixin.world.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.ItemBridge;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/item/ItemStack$1")
public abstract class ItemStack_OptionalStreamCodecMixin {

    @WrapOperation(
            method = "encode(Lnet/minecraft/network/RegistryFriendlyByteBuf;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getItemHolder()Lnet/minecraft/core/Holder;"
            )
    )
    private Holder<Item> spongetools$useHiddenNetworkItemForCustomTypes(
            final ItemStack instance, final Operation<Holder<Item>> original) {
        final Holder<Item> holder = original.call(instance);
        final ItemTypeUtil.@Nullable AdditionalData data = ((ItemBridge) holder.value()).spongetools$bridge$getData();
        return data == null ? holder : data.networkItem().builtInRegistryHolder();
    }
}
