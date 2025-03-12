package net.hellheim.spongetools.manager;

import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.metadata.PluginMetadata;

import net.hellheim.spongetools.util.TranslationUtil;
import net.kyori.adventure.key.Namespaced;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Manager for making {@link TranslatableComponent}s using
 * {@link TranslationUtil} methods with namespace provided to initializer.
 */
public class TranslationManager implements Namespaced {
	
	private final String namespace;
	
	public TranslationManager(final PluginContainer plugin) {
		this(plugin.metadata());
	}
	
	public TranslationManager(final PluginMetadata metadata) {
		this(metadata.id());
	}
	
	public TranslationManager(final String namespace) {
		this.namespace = namespace;
	}
	
	@Override
	public String namespace() {
		return this.namespace;
	}
	
	/**
	 * See {@link TranslationUtil#of(String, String, String)}
	 */
	public TranslatableComponent of(final String type, final String value) {
		return TranslationUtil.of(type, this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#of(RegistryType, String, String)}
	 */
	public TranslatableComponent of(final RegistryType<?> registry, final String value) {
		return TranslationUtil.of(registry, this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#biome(String, String)}
	 */
	public TranslatableComponent biome(final String value) {
		return TranslationUtil.biome(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#block(String, String)}
	 */
	public TranslatableComponent block(final String value) {
		return TranslationUtil.block(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#color(String, String)}
	 */
	public TranslatableComponent color(final String value) {
		return TranslationUtil.color(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#color(String, NamedTextColor)}
	 */
	public TranslatableComponent color(final NamedTextColor color) {
		return TranslationUtil.color(this.namespace, color);
	}
	
	/**
	 * See {@link TranslationUtil#effect(String, String)}
	 */
	public TranslatableComponent effect(final String value) {
		return TranslationUtil.effect(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#enchantment(String, String)}
	 */
	public TranslatableComponent enchantment(final String value) {
		return TranslationUtil.enchantment(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#entity(String, String)}
	 */
	public TranslatableComponent entity(final String value) {
		return TranslationUtil.entity(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#event(String, String)}
	 */
	public TranslatableComponent event(final String value) {
		return TranslationUtil.event(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#flatWorldPreset(String, String)}
	 */
	public TranslatableComponent flatWorldPreset(final String value) {
		return TranslationUtil.flatWorldPreset(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#generator(String, String)}
	 */
	public TranslatableComponent generator(final String value) {
		return TranslationUtil.generator(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#instrument(String, String)}
	 */
	public TranslatableComponent instrument(final String value) {
		return TranslationUtil.instrument(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#item(String, String)}
	 */
	public TranslatableComponent item(final String value) {
		return TranslationUtil.item(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#jukeboxSong(String, String)}
	 */
	public TranslatableComponent jukeboxSong(final String value) {
		return TranslationUtil.jukeboxSong(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#painting(String, String)}
	 */
	public TranslatableComponent painting(final String value) {
		return TranslationUtil.painting(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#statType(String, String)}
	 */
	public TranslatableComponent statType(final String value) {
		return TranslationUtil.statType(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#stat(String, String)}
	 */
	public TranslatableComponent stat(final String value) {
		return TranslationUtil.stat(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#trimMaterial(String, String)}
	 */
	public TranslatableComponent trimMaterial(final String value) {
		return TranslationUtil.trimMaterial(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#trimPattern(String, String)}
	 */
	public TranslatableComponent trimPattern(final String value) {
		return TranslationUtil.trimPattern(this.namespace, value);
	}
	
	/**
	 * See {@link TranslationUtil#upgrade(String, String)}
	 */
	public TranslatableComponent upgrade(final String value) {
		return TranslationUtil.upgrade(this.namespace, value);
	}
}
