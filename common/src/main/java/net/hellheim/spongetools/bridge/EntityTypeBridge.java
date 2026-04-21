package net.hellheim.spongetools.bridge;

import net.hellheim.spongetools.common.util.EntityTypeUtil;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.type.entity.EntityDisplayType;

import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.Entity;

public interface EntityTypeBridge {

    EntityTypeUtil.@Nullable AdditionalData spongetools$bridge$getData();

    void spongetools$bridge$setData(EntityTypeUtil.AdditionalData data);

    void spongetools$bridge$setDisplay(List<EntityDisplayType> display);

    void spongetools$bridge$setCallbacks(BehaviourCallbackHolderLogic<Entity> callbacks);
}
