package net.hellheim.spongetools.custom.type.block;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.type.InstrumentType;
import org.spongepowered.api.data.type.PushReaction;
import org.spongepowered.api.map.color.MapColorType;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.state.StateProperty;

import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.hellheim.spongetools.util.ModelUtil;
import net.hellheim.spongetools.util.TranslationUtil;

/**
 * {@link CustomTypeBuilder} for {@link BlockType}.
 */
public interface BlockTypeBuilder
		extends CustomTypeBuilder.WithData<BlockType, BlockState, BlockTypeArchetype, BlockTypeBuilder> {
	
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
	 * <li> {@link BlockTypeArchetype#registry()}
	 * </ul>
	 * 
	 * @return The registry types
	 * @see #dependencies(RegistryType...)
	 */
	static RegistryType<?>[] dependencies() {
		return new RegistryType[] {
				BlockTypeArchetype.registry()
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
	 * Sets {@link #translationKey(ResourceKey)} and {@link #lootTable(ResourceKey)}.
	 */
	default BlockTypeBuilder id(final ResourceKey key) {
		return this.translationKey(key).lootTable(ModelUtil.withPrefix(key, "blocks/"));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#TRANSLATION_KEY}.
	 */
	default BlockTypeBuilder translationKey(final ResourceKey key) {
		return this.set(BlockTypeKeys.TRANSLATION_KEY, TranslationUtil.block(key).key());
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#LOOT_TABLE}.
	 */
	default BlockTypeBuilder lootTable(final ResourceKey key) {
		return this.add(BlockTypeKeys.LOOT_TABLE, key);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#STATE_PROPERTIES}.
	 */
	default BlockTypeBuilder stateProperties(final StateProperty<?>... properties) {
		return this.set(BlockTypeKeys.STATE_PROPERTIES, Set.of(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#STATE_PROPERTIES}.
	 */
	default BlockTypeBuilder stateProperties(final Collection<? extends StateProperty<?>> properties) {
		return this.set(BlockTypeKeys.STATE_PROPERTIES, Set.copyOf(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DEFAULT_STATE}.
	 */
	default BlockTypeBuilder defaultState(final StatePropertyValue<?>... properties) {
		return this.set(BlockTypeKeys.DEFAULT_STATE, List.of(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DEFAULT_STATE}.
	 */
	default BlockTypeBuilder defaultState(final Collection<? extends StatePropertyValue<?>> properties) {
		return this.set(BlockTypeKeys.DEFAULT_STATE, List.copyOf(properties));
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SOUND_GROUP}.
	 */
	default BlockTypeBuilder sound(final BlockSoundGroup soundGroup) {
		return this.add(BlockTypeKeys.SOUND_GROUP, soundGroup);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#MAP_COLOR}.
	 */
	default BlockTypeBuilder mapColor(final MapColorType mapColor) {
		return this.add(BlockTypeKeys.MAP_COLOR, mapColor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#MAP_COLOR}.
	 */
	default BlockTypeBuilder mapColor(final Supplier<? extends MapColorType> mapColor) {
		return this.supply(BlockTypeKeys.MAP_COLOR, mapColor);
	}
	
	/**
	 * Sets the {@link Keys#REPRESENTED_INSTRUMENT}.
	 */
	default BlockTypeBuilder instrument(final InstrumentType instrument) {
		return this.add(Keys.REPRESENTED_INSTRUMENT, instrument);
	}
	
	/**
	 * Sets the {@link Keys#REPRESENTED_INSTRUMENT}.
	 */
	default BlockTypeBuilder instrument(final Supplier<? extends InstrumentType> instrument) {
		return this.supply(Keys.REPRESENTED_INSTRUMENT, instrument);
	}
	
	/**
	 * Sets the {@link Keys#DESTROY_SPEED}. <br>
	 * <br>
	 * Defines the strength of the block for destruction. <br>
	 * The higher this value, the longer it will take for block to be destroyed. <br>
	 * Special value of -1 makes block unbreakable and not movable by piston.
	 */
	default BlockTypeBuilder destroyResistance(final double resistance) {
		return this.add(Keys.DESTROY_SPEED, resistance);
	}
	
	/**
	 * Sets the {@link Keys#BLAST_RESISTANCE}. <br>
	 * <br>
	 * Defines the strength of the block for explosion. <br>
	 * The higher this value, the less likely the block will be affected by explosions.
	 */
	default BlockTypeBuilder blastResistance(final double resistance) {
		return this.add(Keys.BLAST_RESISTANCE, resistance);
	}
	
	/**
	 * Sets {@link #destroyResistance(double)} and {@link #blastResistance(double)}.
	 */
	default BlockTypeBuilder resistance(final double destroy, final double blast) {
		return this.destroyResistance(destroy).blastResistance(blast);
	}
	
	/**
	 * Sets {@link #destroyResistance(double)} and {@link #blastResistance(double)}.
	 */
	default BlockTypeBuilder resistance(final double resistance) {
		return this.resistance(resistance, resistance);
	}
	
	/**
	 * Sets {@link #destroyResistance(double)} and {@link #blastResistance(double)} to 0.
	 */
	default BlockTypeBuilder instabreak() {
		return this.resistance(0);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#REQUIRE_TOOL} to true.
	 */
	default BlockTypeBuilder requireTool() {
		return this.add(BlockTypeKeys.REQUIRE_TOOL, true);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#SPEED_FACTOR}.
	 */
	default BlockTypeBuilder speedFactor(final double factor) {
		return this.add(BlockTypeKeys.SPEED_FACTOR, factor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#JUMP_FACTOR}.
	 */
	default BlockTypeBuilder jumpFactor(final double factor) {
		return this.add(BlockTypeKeys.JUMP_FACTOR, factor);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#FRICTION_FACTOR}.
	 */
	default BlockTypeBuilder frictionFactor(final double factor) {
		return this.add(BlockTypeKeys.FRICTION_FACTOR, factor);
	}
	
	/**
	 * Sets {@link #speedFactor(double)} and {@link #jumpFactor(double)}.
	 */
	default BlockTypeBuilder movement(final double speed, final double jump) {
		return this.speedFactor(speed).jumpFactor(jump);
	}
	
	/**
	 * Sets {@link #speedFactor(double)}, {@link #jumpFactor(double)} and {@link #frictionFactor(double)}.
	 */
	default BlockTypeBuilder movement(final double speed, final double jump, final double friction) {
		return this.speedFactor(speed).jumpFactor(jump).frictionFactor(friction);
	}
	
	/**
	 * Sets the {@link Keys#PUSH_REACTION}.
	 */
	default BlockTypeBuilder pushReaction(final PushReaction reaction) {
		return this.add(Keys.PUSH_REACTION, reaction);
	}
	
	/**
	 * Sets the {@link Keys#PUSH_REACTION}.
	 */
	default BlockTypeBuilder pushReaction(final Supplier<? extends PushReaction> reaction) {
		return this.supply(Keys.PUSH_REACTION, reaction);
	}
	
	/**
	 * Sets the {@link Keys#BURNABLE} to true.
	 */
	default BlockTypeBuilder burnable() {
		return this.add(Keys.BURNABLE, true);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#HAS_DYNAMIC_SHAPE} to true.
	 */
	default BlockTypeBuilder dynamicShape() {
		return this.add(BlockTypeKeys.HAS_DYNAMIC_SHAPE, true);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#HAS_COLLISION} to false.
	 */
	default BlockTypeBuilder noCollision() {
		return this.add(BlockTypeKeys.HAS_COLLISION, false);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#HAS_OCCLUSION} to false.
	 */
	default BlockTypeBuilder noOcclusion() {
		return this.add(BlockTypeKeys.HAS_OCCLUSION, false);
	}
}
