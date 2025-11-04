package net.hellheim.spongetools.bridge;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.hellheim.spongetools.common.util.BlockTypeUtil;

public interface BlockPropertiesBridge {

    void spongetools$bridge$applyData(BlockTypeUtil.AdditionalData data);

    BlockTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();
}
