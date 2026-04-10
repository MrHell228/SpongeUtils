package net.hellheim.spongetools.custom.type.entity;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;

public interface EntityTypeExtension extends BehaviourCallbackHolder<Entity> {
	
	static EntityTypeExtension getFor(final EntityType<?> type) {
		return (EntityTypeExtension) type;
	}
	
	EntityType<?> owner();
	
	List<EntityDisplayType> display();
}
