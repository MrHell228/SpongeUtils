package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.EntityCategory;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.attribute.AttributeHolder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.object.TypedKey;

public final class EntityTypeKeys {
	
	/**
	 * @see EntityType#category()
	 */
	public static final TypedKey<EntityCategory> CATEGORY = TypedKey.of(SpongeTools.key("category"), EntityCategory.class);
	
	/**
	 * The default {@link AttributeHolder}'s attributes.
	 */
	public static final TypedKey<EntityDefaultAttributes> ATTRIBUTES = TypedKey.of(SpongeTools.key("attributes"), EntityDefaultAttributes.class);
	
	/**
	 * @see EntityType#isFlammable()
	 */
	public static final TypedKey<Boolean> FLAMMABLE = TypedKey.of(SpongeTools.key("flammable"), Boolean.class);
	
	/**
	 * If not set, entity will not be serializable.
	 * 
	 * @see EntityType#isTransient()
	 */
	public static final TypedKey<ResourceKey> SERIALIZATION_KEY = TypedKey.of(SpongeTools.key("serialization_key"), ResourceKey.class);
	
	/**
	 * @see EntityType#canSpawnAwayFromPlayer()
	 */
	public static final TypedKey<Boolean> SPAWN_AWAY_FROM_PLAYER = TypedKey.of(SpongeTools.key("spawn_away_from_player"), Boolean.class);
	
	/**
	 * @see EntityType#isSummonable()
	 */
	public static final TypedKey<Boolean> SUMMONABLE = TypedKey.of(SpongeTools.key("summonable"), Boolean.class);
	
	/**
	 * @see EntityType#asComponent()
	 */
	public static final TypedKey<String> TRANSLATION_KEY = TypedKey.of(SpongeTools.key("translation_key"), String.class);
	
	/**
	 * The entity drops.
	 */
	public static final TypedKey<ResourceKey> LOOT_TABLE = TypedKey.of(SpongeTools.key("loot_table"), ResourceKey.class);
	
	private EntityTypeKeys() {
	}
}
