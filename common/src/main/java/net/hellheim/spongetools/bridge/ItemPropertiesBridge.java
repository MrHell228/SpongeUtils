package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.common.util.ItemTypeUtil;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemPropertiesBridge {

    void spongetools$bridge$applyData(ItemTypeUtil.AdditionalData data);

    ItemTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();
}
