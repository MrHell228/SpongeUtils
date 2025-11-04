package net.hellheim.spongetools.mixin.world.item;

import net.hellheim.spongetools.bridge.ItemPropertiesBridge;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.minecraft.world.item.Item;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.Properties.class)
public abstract class Item_PropertiesMixin implements ItemPropertiesBridge {

    private @Unique ItemTypeUtil.@Nullable AdditionalData spongetools$data;

    @Override
    public void spongetools$bridge$applyData(final ItemTypeUtil.AdditionalData data) {
        this.spongetools$data = data;
    }

    @Override
    public ItemTypeUtil.@Nullable AdditionalData spongetools$bridge$getData() {
        return this.spongetools$data;
    }
}
