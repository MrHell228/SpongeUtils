package net.hellheim.spongetools.custom.type.entity;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityCategory;
import org.spongepowered.api.entity.EntityType;

import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.util.ModelUtil;
import net.hellheim.spongetools.util.TranslationUtil;

public interface EntityTypeBuilder extends
		CustomTypeBuilder.InstanceBased<EntityType<?>, Entity, EntityTypeArchetype, EntityTypeBuilder>,
		CustomTypeBuilder.WithData<EntityType<?>, Entity, EntityTypeArchetype, EntityTypeBuilder> {
	
	/**
	 * Sets {@link #serializationKey(ResourceKey)}, {@link #translationKey(ResourceKey)}
	 * and {@link #lootTable(ResourceKey)}.
	 */
	default EntityTypeBuilder id(final ResourceKey key) {
		return this.serializationKey(key).translationKey(key).lootTable(ModelUtil.withPrefix(key, "entities/"));
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#SERIALIZATION_KEY}.
	 */
	default EntityTypeBuilder serializationKey(final ResourceKey key) {
		return this.set(EntityTypeKeys.SERIALIZATION_KEY, key);
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#TRANSLATION_KEY}.
	 */
	default EntityTypeBuilder translationKey(final ResourceKey key) {
		return this.set(EntityTypeKeys.TRANSLATION_KEY, TranslationUtil.entity(key).key());
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#LOOT_TABLE}.
	 */
	default EntityTypeBuilder lootTable(final ResourceKey key) {
		return this.set(EntityTypeKeys.LOOT_TABLE, key);
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#FLAGS}.
	 */
	default EntityTypeBuilder flags(final ResourceKey... flags) {
		return this.set(EntityTypeKeys.FLAGS, Set.of(flags));
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#FLAGS}.
	 */
	default EntityTypeBuilder flags(final Collection<? extends ResourceKey> flags) {
		return this.set(EntityTypeKeys.FLAGS, Set.copyOf(flags));
	}
	
	/**
	 * Sets tee {@link EntityTypeKeys#ATTRIBUTES}.
	 */
	default EntityTypeBuilder attributes(final EntityDefaultAttributes attributes) {
		return this.set(EntityTypeKeys.ATTRIBUTES, attributes);
	}
	
	/**
	 * Sets tee {@link EntityTypeKeys#ATTRIBUTES}.
	 */
	default EntityTypeBuilder attributes(final EntityDefaultAttributes.Builder attributes) {
		return this.attributes(attributes.build());
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#CATEGORY}.
	 */
	default EntityTypeBuilder category(final EntityCategory category) {
		return this.set(EntityTypeKeys.CATEGORY, category);
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#CATEGORY}.
	 */
	default EntityTypeBuilder category(final Supplier<? extends EntityCategory> category) {
		return this.category(Objects.requireNonNull(category, "category").get());
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#SUMMONABLE} to false.
	 */
	default EntityTypeBuilder noSummon() {
		return this.set(EntityTypeKeys.SUMMONABLE, false);
	}
	
	/**
	 * Removes the {@link EntityTypeKeys#SERIALIZATION_KEY}.
	 */
	default EntityTypeBuilder noSave() {
		return this.remove(EntityTypeKeys.SERIALIZATION_KEY);
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#FLAMMABLE} to false.
	 */
	default EntityTypeBuilder fireImmune() {
		return this.set(EntityTypeKeys.FLAMMABLE, false);
	}
	
	/**
	 * Sets the {@link EntityTypeKeys#SPAWN_AWAY_FROM_PLAYER} to true.
	 */
	default EntityTypeBuilder canSpawnAwayFromPlayer() {
		return this.set(EntityTypeKeys.SPAWN_AWAY_FROM_PLAYER, true);
	}
	
	/**
	 * Builds and returns typed {@link EntityType}.
	 * 
	 * @param <E> The type of the entity
	 * @return The typed entity type
	 */
	@SuppressWarnings("unchecked")
	default <E extends Entity> EntityType<E> buildTyped() {
		return (EntityType<E>) this.build();
	}
}
