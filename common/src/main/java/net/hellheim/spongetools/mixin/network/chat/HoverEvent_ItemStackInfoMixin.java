package net.hellheim.spongetools.mixin.network.chat;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HoverEvent.ItemStackInfo.class)
public abstract class HoverEvent_ItemStackInfoMixin {

    @ModifyVariable(
            method = "<init>(Lnet/minecraft/core/Holder;ILnet/minecraft/core/component/DataComponentPatch;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static Holder<Item> spongetools$useNetworkItem(final Holder<Item> holder) {
        return FakeableNetworkValueBridge.asNetworkHolder(holder, Item::builtInRegistryHolder);
    }
}
