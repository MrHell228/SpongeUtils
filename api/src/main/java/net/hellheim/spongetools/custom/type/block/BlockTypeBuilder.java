package net.hellheim.spongetools.custom.type.block;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.state.StateProperty;

import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.hellheim.spongetools.util.ModelUtil;
import net.hellheim.spongetools.util.TranslationUtil;

/**
 * @see CustomTypeBuilder
 */
public interface BlockTypeBuilder extends
		CustomTypeBuilder<BlockType, BlockArchetype, BlockTypeBuilder> {
	
	/**
	 * Creates the new {@link BlockTypeBuilder}.
	 * 
	 * @return The new builder
	 */
	static BlockTypeBuilder create() {
		return Sponge.game().builderProvider().provide(BlockTypeBuilder.class);
	}
	
	/**
	 * Returns the mandatory registry dependencies that must be used when registering custom type. <br>
	 * These registries include:
	 * <ul>
	 * <li> {@link BlockArchetype#registry()}
	 * </ul>
	 * 
	 * @return The registry types
	 * @see #dependencies(RegistryType...)
	 */
	static RegistryType<?>[] dependencies() {
		return new RegistryType[] {
				BlockArchetype.registry()
				};
	}
	
	/**
	 * Returns the registry dependencies from {@link #dependencies()}, and the additional given ones.
	 * 
	 * @param registryTypes The additional registry dependencies
	 * @return The registry types
	 */
	static RegistryType<?>[] dependencies(final RegistryType<?>... registryTypes) {
		return ArrayUtils.addAll(BlockTypeBuilder.dependencies(),  registryTypes);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#TRANSLATION_KEY} and {@link BlockTypeKeys#LOOT_TABLE} context keys.
	 */
	default BlockTypeBuilder id(final ResourceKey key) {
		return this.translationKey(key).lootTable(ModelUtil.withPrefix(key, "blocks/"));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#TRANSLATION_KEY} context key.
	 */
	default BlockTypeBuilder translationKey(final ResourceKey key) {
		return this.set(BlockTypeKeys.TRANSLATION_KEY, TranslationUtil.block(key).key());
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#LOOT_TABLE} context key.
	 */
	default BlockTypeBuilder lootTable(final ResourceKey key) {
		return this.set(BlockTypeKeys.LOOT_TABLE, key);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#STATE_PROPERTIES} context key.
	 */
	default BlockTypeBuilder stateProperties(final StateProperty<?>... properties) {
		return this.set(BlockTypeKeys.STATE_PROPERTIES, Set.of(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#STATE_PROPERTIES} context key.
	 */
	default BlockTypeBuilder stateProperties(final Collection<? extends StateProperty<?>> properties) {
		return this.set(BlockTypeKeys.STATE_PROPERTIES, Set.copyOf(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DEFAULT_STATE} context key.
	 */
	default BlockTypeBuilder defaultState(final StatePropertyValue<?>... properties) {
		return this.set(BlockTypeKeys.DEFAULT_STATE, List.of(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DEFAULT_STATE} context key.
	 */
	default BlockTypeBuilder defaultState(final Collection<? extends StatePropertyValue<?>> properties) {
		return this.set(BlockTypeKeys.DEFAULT_STATE, List.copyOf(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SOUND_GROUP} context key.
	 */
	default BlockTypeBuilder sound(final BlockSoundGroup soundGroup) {
		return this.set(BlockTypeKeys.SOUND_GROUP, soundGroup);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#MAP_COLOR} context key.
	 */
	default BlockTypeBuilder mapColor(final MapColorType mapColor) {
		return this.set(BlockTypeKeys.MAP_COLOR, mapColor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#MAP_COLOR} context key.
	 */
	default BlockTypeBuilder mapColor(final Supplier<? extends MapColorType> mapColor) {
		return this.mapColor(Objects.requireNonNull(mapColor, "mapColor").get());
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#INSTRUMENT} context key.
	 */
	default BlockTypeBuilder instrument(final InstrumentType instrument) {
		return this.set(BlockTypeKeys.INSTRUMENT, instrument);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#INSTRUMENT} context key.
	 */
	default BlockTypeBuilder instrument(final Supplier<? extends InstrumentType> instrument) {
		return this.instrument(Objects.requireNonNull(instrument, "instrument").get());
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DESTRUCTION_RESISTANCE} context key.
	 */
	default BlockTypeBuilder destructionResistance(final double resistance) {
		return this.set(BlockTypeKeys.DESTRUCTION_RESISTANCE, resistance);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#EXPLOSION_RESISTANCE} context key.
	 */
	default BlockTypeBuilder explosionResistance(final double resistance) {
		return this.set(BlockTypeKeys.EXPLOSION_RESISTANCE, resistance);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DESTRUCTION_RESISTANCE} and
	 * {@link BlockTypeKeys#EXPLOSION_RESISTANCE} context keys.
	 */
	default BlockTypeBuilder resistance(final double destruction, final double explosion) {
		return this.destructionResistance(destruction).explosionResistance(explosion);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DESTRUCTION_RESISTANCE} and
	 * {@link BlockTypeKeys#EXPLOSION_RESISTANCE} context keys.
	 */
	default BlockTypeBuilder resistance(final double resistance) {
		return this.resistance(resistance, resistance);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DESTRUCTION_RESISTANCE} and
	 * {@link BlockTypeKeys#EXPLOSION_RESISTANCE} context keys tp 0.
	 */
	default BlockTypeBuilder instabreak() {
		return this.resistance(0);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#REQUIRE_TOOL} context key.
	 */
	default BlockTypeBuilder requireTool() {
		return this.set(BlockTypeKeys.REQUIRE_TOOL, true);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SPEED_FACTOR} context key.
	 */
	default BlockTypeBuilder speedFactor(final double factor) {
		return this.set(BlockTypeKeys.SPEED_FACTOR, factor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#JUMP_FACTOR} context key.
	 */
	default BlockTypeBuilder jumpFactor(final double factor) {
		return this.set(BlockTypeKeys.JUMP_FACTOR, factor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#FRICTION_FACTOR} context key.
	 */
	default BlockTypeBuilder frictionFactor(final double factor) {
		return this.set(BlockTypeKeys.FRICTION_FACTOR, factor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SPEED_FACTOR} and {@link BlockTypeKeys#JUMP_FACTOR} context keys.
	 */
	default BlockTypeBuilder movement(final double speed, final double jump) {
		return this.speedFactor(speed).jumpFactor(jump);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SPEED_FACTOR}, {@link BlockTypeKeys#JUMP_FACTOR}
	 * and {@link BlockTypeKeys#FRICTION_FACTOR} context keys.
	 */
	default BlockTypeBuilder movement(final double speed, final double jump, final double friction) {
		return this.speedFactor(speed).jumpFactor(jump).frictionFactor(friction);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#BURNABLE} context key.
	 */
	default BlockTypeBuilder burnable() {
		return this.set(BlockTypeKeys.BURNABLE, true);
	}
}
