package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.common.util.EntityTypeUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityTypeBridge {

    void spongetools$bridge$applyData(EntityTypeUtil.AdditionalData data);

    EntityTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();
}
