package net.hellheim.spongetools.custom.type.entity;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.CustomTypeArchetype;

/**
 * {@link CustomTypeArchetype} of {@link EntityType} used for {@link EntityTypeBuilder}.
 */
@CatalogedBy(EntityArchetypes.class)
public record EntityTypeArchetype(Optional<EntityTypeArchetype> parent, Class<?> baseClass)
		implements CustomTypeArchetype.InstanceBased<EntityType<?>, Entity, EntityTypeArchetype> {
	
	public EntityTypeArchetype(final Optional<EntityTypeArchetype> parent, final Class<?> baseClass) {
		CustomTypeArchetype.validate(Entity.class, baseClass, parent);
		this.parent = parent;
		this.baseClass = baseClass;
	}
	
	public static DefaultedRegistryType<EntityTypeArchetype> registry() {
		return SpongeTools.Registries.ENTITY_TYPE_ARCHETYPE;
	}
	
	public static <I> EntityTypeArchetype of(final Optional<EntityTypeArchetype> parent, final Class<I> baseClass) {
		return new EntityTypeArchetype(parent, baseClass);
	}
	
	@Override
	public Class<Factory> contextFactory() {
		return Factory.class;
	}
	
	public interface Factory extends CustomTypeArchetype.InstanceBased.Factory<EntityType<?>> {
	}
}
