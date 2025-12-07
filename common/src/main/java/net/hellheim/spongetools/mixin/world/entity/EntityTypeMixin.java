package net.hellheim.spongetools.mixin.world.entity;

import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.common.util.EntityTypeUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.asm.mixin.Unique;

public abstract class EntityTypeMixin implements EntityTypeBridge, FakeableNetworkValueBridge {

    @Unique private EntityTypeUtil.@Nullable AdditionalData spongetools$data;

    @Override
    public void spongetools$bridge$applyData(final EntityTypeUtil.AdditionalData data) {
        this.spongetools$data = data;
    }

    @Override
    public EntityTypeUtil.@Nullable AdditionalData spongetools$bridge$getData() {
        return this.spongetools$data;
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$data == null ? null : this.spongetools$data.networkType();
    }
}
