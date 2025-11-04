package net.hellheim.spongetools.mixin.world.item;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.bridge.ItemBridge;
import net.hellheim.spongetools.bridge.ItemPropertiesBridge;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.minecraft.world.item.Item;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemBridge, FakeableNetworkValueBridge {

    @Shadow @Final @Mutable protected String descriptionId;

    private @Unique ItemTypeUtil.@Nullable AdditionalData spongetools$data;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void spongetools$applyData(final Item.Properties properties, final CallbackInfo ci) {
        this.spongetools$data = ((ItemPropertiesBridge) properties).spongetools$bridge$getData();
        if (this.spongetools$data != null) {
            this.descriptionId = this.spongetools$data.translationKey();
        }
    }

    @Override
    public ItemTypeUtil.@Nullable AdditionalData spongetools$bridge$getData() {
        return this.spongetools$data;
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$data == null ? null : this.spongetools$data.networkItem();
    }
}
