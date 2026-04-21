package net.hellheim.spongetools.util;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.ArtType;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.sound.music.MusicDisc;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.recipe.smithing.TrimMaterial;
import org.spongepowered.api.item.recipe.smithing.TrimPattern;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.StatisticCategory;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.generation.config.flat.FlatGeneratorConfig;

import net.hellheim.spongetools.manager.TranslationManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Utility class for making {@link TranslatableComponent}s.
 */
public final class TranslationUtil {
	
	/**
	 * {@link TranslationManager} for minecraft namespace.
	 */
	public static final TranslationManager MINECRAFT = new TranslationManager(ResourceKey.MINECRAFT_NAMESPACE);
	
	public static TranslatableComponent of(final String type, final String namespace, final String value) {
		return Component.translatable(type + "." + namespace + "." + value);
	}
	
	public static TranslatableComponent of(final String type, final ResourceKey key) {
		return TranslationUtil.of(type, key.namespace(), key.value());
	}
	
	public static TranslatableComponent of(final RegistryType<?> registry, final String namespace, final String value) {
		return TranslationUtil.of(registry.location().value(), namespace, value);
	}
	
	public static TranslatableComponent of(final RegistryType<?> registry, final ResourceKey key) {
		return TranslationUtil.of(registry, key.namespace(), key.value());
	}
	
	public static TranslatableComponent of(final RegistryKey<?> key) {
		return TranslationUtil.of(key.registry(), key.location());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link Biome} id
	 * @return Translatable component for {@link Biome}
	 */
	public static TranslatableComponent biome(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.BIOME, namespace, value);
	}
	
	/**
	 * @param value The {@link Biome} key
	 * @return Translatable component for {@link Biome}
	 */
	public static TranslatableComponent biome(final ResourceKey key) {
		return TranslationUtil.biome(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link BlockType} id
	 * @return Translatable component for {@link BlockType}
	 */
	public static TranslatableComponent block(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.BLOCK, namespace, value);
	}
	
	/**
	 * @param value The {@link BlockType} key
	 * @return Translatable component for {@link BlockType}
	 */
	public static TranslatableComponent block(final ResourceKey key) {
		return TranslationUtil.block(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link NamedTextColor} id
	 * @return Translatable component for {@link NamedTextColor}
	 */
	public static TranslatableComponent color(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.COLOR, namespace, value);
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link NamedTextColor}
	 * @return Translatable component for {@link NamedTextColor}
	 */
	public static TranslatableComponent color(final String namespace, final NamedTextColor color) {
		return TranslationUtil.color(namespace, color.toString());
	}
	
	/**
	 * @param value The {@link NamedTextColor} key
	 * @return Translatable component for {@link NamedTextColor}
	 */
	public static TranslatableComponent color(final ResourceKey key) {
		return TranslationUtil.color(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link PotionEffect} id
	 * @return Translatable component for {@link PotionEffect}
	 */
	public static TranslatableComponent effect(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.EFFECT, namespace, value);
	}
	
	/**
	 * @param value The {@link PotionEffect} key
	 * @return Translatable component for {@link PotionEffect}
	 */
	public static TranslatableComponent effect(final ResourceKey key) {
		return TranslationUtil.effect(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link EnchantmentType} id
	 * @return Translatable component for {@link EnchantmentType}
	 */
	public static TranslatableComponent enchantment(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.ENCHANTMENT, namespace, value);
	}
	
	/**
	 * @param value The {@link EnchantmentType} key
	 * @return Translatable component for {@link EnchantmentType}
	 */
	public static TranslatableComponent enchantment(final ResourceKey key) {
		return TranslationUtil.enchantment(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link EntityType} id
	 * @return Translatable component for {@link EntityType}
	 */
	public static TranslatableComponent entity(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.ENTITY, namespace, value);
	}
	
	/**
	 * @param value The {@link EntityType} key
	 * @return Translatable component for {@link EntityType}
	 */
	public static TranslatableComponent entity(final ResourceKey key) {
		return TranslationUtil.entity(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The event id
	 * @return Translatable component for event
	 */
	public static TranslatableComponent event(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.EVENT, namespace, value);
	}
	
	/**
	 * @param value The event key
	 * @return Translatable component for event
	 */
	public static TranslatableComponent event(final ResourceKey key) {
		return TranslationUtil.event(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link FlatGeneratorConfig} id
	 * @return Translatable component for {@link FlatGeneratorConfig}
	 */
	public static TranslatableComponent flatWorldPreset(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.FLAT_WORLD_PRESET, namespace, value);
	}
	
	/**
	 * @param value The {@link FlatGeneratorConfig} key
	 * @return Translatable component for {@link FlatGeneratorConfig}
	 */
	public static TranslatableComponent flatWorldPreset(final ResourceKey key) {
		return TranslationUtil.flatWorldPreset(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The generator id
	 * @return Translatable component for generator
	 */
	public static TranslatableComponent generator(final String namespace,final String value) {
		return TranslationUtil.of(NamespacedValueType.GENERATOR, namespace, value);
	}
	
	/**
	 * @param value The generator key
	 * @return Translatable component for generator
	 */
	public static TranslatableComponent generator(final ResourceKey key) {
		return TranslationUtil.generator(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link InstrumentType} id
	 * @return Translatable component for {@link InstrumentType}
	 */
	public static TranslatableComponent instrument(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.INSTRUMENT, namespace, value);
	}
	
	/**
	 * @param value The {@link InstrumentType} key
	 * @return Translatable component for {@link InstrumentType}
	 */
	public static TranslatableComponent instrument(final ResourceKey key) {
		return TranslationUtil.instrument(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link ItemType} id
	 * @return Translatable component for {@link ItemType}
	 */
	public static TranslatableComponent item(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.ITEM, namespace, value);
	}
	
	/**
	 * @param value The {@link ItemType} key
	 * @return Translatable component for {@link ItemType}
	 */
	public static TranslatableComponent item(final ResourceKey key) {
		return TranslationUtil.item(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link MusicDisc} id
	 * @return Translatable component for {@link MusicDisc}
	 */
	public static TranslatableComponent jukeboxSong(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.JUKEBOX_SONG, namespace, value);
	}
	
	/**
	 * @param value The {@link MusicDisc} key
	 * @return Translatable component for {@link MusicDisc}
	 */
	public static TranslatableComponent jukeboxSong(final ResourceKey key) {
		return TranslationUtil.jukeboxSong(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link ArtType} id
	 * @return Translatable component for {@link ArtType}
	 */
	public static TranslatableComponent painting(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.PAINTING, namespace, value);
	}
	
	/**
	 * @param value The {@link ArtType} key
	 * @return Translatable component for {@link ArtType}
	 */
	public static TranslatableComponent painting(final ResourceKey key) {
		return TranslationUtil.painting(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link StatisticCategory} id
	 * @return Translatable component for {@link StatisticCategory}
	 */
	public static TranslatableComponent statType(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.STAT_TYPE, namespace, value);
	}
	
	/**
	 * @param value The {@link StatisticCategory} key
	 * @return Translatable component for {@link StatisticCategory}
	 */
	public static TranslatableComponent statType(final ResourceKey key) {
		return TranslationUtil.statType(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link Statistic} id
	 * @return Translatable component for {@link Statistic}
	 */
	public static TranslatableComponent stat(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.STAT, namespace, value);
	}
	
	/**
	 * @param value The {@link Statistic} key
	 * @return Translatable component for {@link Statistic}
	 */
	public static TranslatableComponent stat(final ResourceKey key) {
		return TranslationUtil.stat(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link TrimMaterial} id
	 * @return Translatable component for {@link TrimMaterial}
	 */
	public static TranslatableComponent trimMaterial(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.TRIM_MATERIAL, namespace, value);
	}
	
	/**
	 * @param value The {@link TrimMaterial} key
	 * @return Translatable component for {@link TrimMaterial}
	 */
	public static TranslatableComponent trimMaterial(final ResourceKey key) {
		return TranslationUtil.trimMaterial(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The {@link TrimPattern} id
	 * @return Translatable component for {@link TrimPattern}
	 */
	public static TranslatableComponent trimPattern(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.TRIM_PATTERN, namespace, value);
	}
	
	/**
	 * @param value The {@link TrimPattern} key
	 * @return Translatable component for {@link TrimPattern}
	 */
	public static TranslatableComponent trimPattern(final ResourceKey key) {
		return TranslationUtil.trimPattern(key.namespace(), key.value());
	}
	
	/**
	 * @param namespace The namespace
	 * @param value The upgrade id
	 * @return Translatable component for upgrade
	 */
	public static TranslatableComponent upgrade(final String namespace, final String value) {
		return TranslationUtil.of(NamespacedValueType.UPGRADE, namespace, value);
	}
	
	/**
	 * @param value The upgrade key
	 * @return Translatable component for upgrade
	 */
	public static TranslatableComponent upgrade(final ResourceKey key) {
		return TranslationUtil.upgrade(key.namespace(), key.value());
	}
	
	/**
	 * Collection of translation component types that are followed by namespace.
	 */
	public static final class NamespacedValueType {
		
		public static final String BIOME = "biome";
		public static final String BLOCK = "block";
		public static final String COLOR = "color";
		public static final String EFFECT = "effect";
		public static final String ENCHANTMENT = "enchantment";
		public static final String ENTITY = "entity";
		public static final String EVENT = "event";
		public static final String FLAT_WORLD_PRESET = "flat_world_preset";
		public static final String GENERATOR = "generator";
		public static final String INSTRUMENT = "instrument";
		public static final String ITEM = "item";
		public static final String JUKEBOX_SONG = "jukebox_song";
		public static final String PAINTING = "painting";
		public static final String STAT_TYPE = "stat_type";
		public static final String STAT = "stat";
		public static final String TRIM_MATERIAL = "trim_material";
		public static final String TRIM_PATTERN = "trim_pattern";
		public static final String UPGRADE = "upgrade";
		
		private NamespacedValueType() {
		}
	}
	
	private TranslationUtil() {
	}
}
