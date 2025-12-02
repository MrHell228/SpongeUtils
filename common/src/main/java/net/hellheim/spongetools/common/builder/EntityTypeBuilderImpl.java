package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.EntityTypeUtil;
import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.EntityTypeBuilder;

public class EntityTypeBuilderImpl
		extends CustomTypeBuilderImpl.WithDataImpl<EntityType<?>, Entity, EntityTypeArchetype, EntityTypeBuilder>
		implements EntityTypeBuilder {
	
	@Override
	protected EntityTypeArchetype baseArchetype() {
		return EntityTypeUtil.Archetypes.ENTITY;
	}
	
	@Override
	protected DefaultedRegistryType<EntityTypeArchetype> archetypeRegistry() {
		return EntityTypeArchetype.registry();
	}
	
	@Override
	protected EntityType<?> build0() {
		return (EntityType<?>) EntityTypeUtil.type(this.archetype, this.data.asImmutableManipulator(), this.context, null);
	}
}
