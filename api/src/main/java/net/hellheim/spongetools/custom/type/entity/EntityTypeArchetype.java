package net.hellheim.spongetools.custom.type.entity;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.annotation.CatalogedBy;
import org.spongepowered.api.world.World;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolder;
import net.hellheim.spongetools.custom.type.CustomTypeArchetype;
import net.hellheim.spongetools.function.TriFunction;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * {@link CustomTypeArchetype} of {@link EntityType} used for {@link EntityTypeBuilder}.
 */
@CatalogedBy(EntityArchetypes.class)
public record EntityTypeArchetype(
		Optional<EntityTypeArchetype> parent,
		Class<?> baseClass,
		TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<Entity>, EntityType<?>> typeAssembler,
		BiFunction<EntityType<?>, World<?, ?>, Entity> entityAssembler
		) implements CustomTypeArchetype.InstanceBased<EntityType<?>, Entity, EntityTypeArchetype> {
	
	public EntityTypeArchetype(
		final Optional<EntityTypeArchetype> parent,
		final Class<?> baseClass,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<Entity>, EntityType<?>> typeAssembler,
		final BiFunction<EntityType<?>, World<?, ?>, Entity> entityAssembler
	) {
		CustomTypeArchetype.validate(Entity.class, baseClass, parent);
		this.parent = parent;
		this.baseClass = baseClass;
		this.typeAssembler = Objects.requireNonNull(typeAssembler, "typeAssembler");
		this.entityAssembler = Objects.requireNonNull(entityAssembler, "entityAssembler");
	}
	
	public static DefaultedRegistryType<EntityTypeArchetype> registry() {
		return SpongeTools.Registries.ENTITY_TYPE_ARCHETYPE;
	}
	
	public static <I> EntityTypeArchetype of(
		final Optional<EntityTypeArchetype> parent,
		final Class<I> baseClass,
		final TriFunction<ValueContainer, TypedKeyMap, BehaviourCallbackHolder<Entity>, EntityType<?>> typeAssembler,
		final BiFunction<EntityType<?>, World<?, ?>, I> entityAssembler
	) {
		return new EntityTypeArchetype(
				parent, baseClass, typeAssembler,
				(type, world) -> (Entity) entityAssembler.apply(type, world)
				);
	}
	
	@Override
	public Class<Factory> contextFactory() {
		return Factory.class;
	}
	
	public interface Factory extends CustomTypeArchetype.InstanceBased.Factory<EntityType<?>> {
	}
}
