package net.hellheim.spongetools.mixin.world.entity;

import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.common.util.EntityTypeUtil;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderProxy;
import net.hellheim.spongetools.custom.type.entity.EntityDisplayType;
import net.hellheim.spongetools.custom.type.entity.EntityTypeExtension;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public abstract class EntityTypeMixin implements
        EntityTypeBridge,
        FakeableNetworkValueBridge,
        EntityTypeExtension,
        BehaviourCallbackHolderProxy<Entity> {

    @Unique private EntityTypeUtil.@Nullable AdditionalData spongetools$data;
    @Unique private List<EntityDisplayType> spongetools$display = List.of();
    @Unique private BehaviourCallbackHolderLogic<Entity> spongetools$callbacks;

    @Override
    public EntityType<?> owner() {
        return (EntityType<?>) this;
    }

    @Override
    public List<EntityDisplayType> display() {
        return this.spongetools$display;
    }

    @Override
    public BehaviourCallbackHolder<Entity> getAsBehaviourCallbackHolder() {
        return this.spongetools$callbacks;
    }

    @Override
    public EntityTypeUtil.@Nullable AdditionalData spongetools$bridge$getData() {
        return this.spongetools$data;
    }

    @Override
    public void spongetools$bridge$setData(final EntityTypeUtil.AdditionalData data) {
        this.spongetools$data = data;
    }

    @Override
    public void spongetools$bridge$setDisplay(final List<EntityDisplayType> display) {
        this.spongetools$display = List.copyOf(display);
    }

    @Override
    public void spongetools$bridge$setCallbacks(final BehaviourCallbackHolderLogic<Entity> callbacks) {
        this.spongetools$callbacks = callbacks.asImmutable();
    }

    @Override
    public @Nullable Object spongetools$bridge$asNetworkValue() {
        return this.spongetools$data == null ? null : this.spongetools$data.networkType();
    }
}
