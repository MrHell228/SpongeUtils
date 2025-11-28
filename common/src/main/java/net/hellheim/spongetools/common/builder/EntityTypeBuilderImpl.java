package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.custom.type.entity.EntityTypeArchetype;
import net.hellheim.spongetools.custom.type.entity.EntityTypeBuilder;

public class EntityTypeBuilderImpl
		extends CustomTypeBuilderImpl.WithDataImpl<EntityType<?>, Entity, EntityTypeArchetype, EntityTypeBuilder>
		implements EntityTypeBuilder {
	
	@Override
	protected EntityTypeArchetype baseArchetype() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected DefaultedRegistryType<EntityTypeArchetype> archetypeRegistry() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected EntityType<?> build0() {
		// TODO Auto-generated method stub
		return null;
	}
}
