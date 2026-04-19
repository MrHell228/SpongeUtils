package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemBridge {

    ItemTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();

    ResourceKey<Item> spongetools$bridge$id();
}
