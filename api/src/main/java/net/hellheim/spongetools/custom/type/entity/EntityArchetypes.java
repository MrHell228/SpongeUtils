package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Aerial;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.entity.living.PathfinderAgent;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.Registry;
import org.spongepowered.api.registry.RegistryKey;

import net.hellheim.spongetools.SpongeTools;

public final class EntityArchetypes {
	
	/**
	 * {@link Aerial}-based archetype. <br>
	 * Parent archetype: {@link #AGENT}.
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> AERIAL = EntityArchetypes.key("aerial");
	
	/**
	 * {@link Agent}-based archetype. <br>
	 * Parent archetype: {@link #LIVING}.
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> AGENT = EntityArchetypes.key("agent");
	
	/**
	 * {@link Entity}-based archetype. <br>
	 * <br>
	 * Required Context: <br>
	 * - {@link EntityTypeKeys#CATEGORY} <br>
	 * - {@link EntityTypeKeys#TRANSLATION_KEY} <br>
	 * <br>
	 * Supported Context: <br>
	 * - {@link EntityTypeKeys#FLAMMABLE} <br>
	 * - {@link EntityTypeKeys#SPAWN_AWAY_FROM_PLAYER} <br>
	 * - {@link EntityTypeKeys#SUMMONABLE} <br>
	 * - {@link EntityTypeKeys#TRANSIENT} <br>
	 * - {@link EntityTypeKeys#LOOT_TABLE} <br>
	 * <br>
	 * Supported behaviour: <br>
	 * - TODO
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> ENTITY = EntityArchetypes.key("entity");
	
	/**
	 * {@link Living}-based archetype. <br>
	 * Parent archetype: {@link #ENTITY}.
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> LIVING = EntityArchetypes.key("living");
	
	/**
	 * {@link Monster}-based archetype. <br>
	 * Parent archetype: {@link #PATHFINDER_AGENT}.
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> MONSTER = EntityArchetypes.key("monster");
	
	/**
	 * {@link PathfinderAgent}-based archetype. <br>
	 * Parent archetype: {@link #AGENT}.
	 */
	public static final DefaultedRegistryReference<EntityTypeArchetype> PATHFINDER_AGENT = EntityArchetypes.key("pathfinder_agent");
	
	private EntityArchetypes() {
	}
	
	public static Registry<EntityTypeArchetype> registry() {
		return EntityTypeArchetype.registry().get();
	}
	
	private static DefaultedRegistryReference<EntityTypeArchetype> key(final String key) {
		return RegistryKey.of(EntityTypeArchetype.registry(), SpongeTools.key(key)).asDefaultedReference(Sponge::game);
	}
}
