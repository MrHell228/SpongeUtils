package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.common.util.ItemTypeUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemBridge {

    ItemTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();
}
