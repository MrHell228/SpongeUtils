package net.hellheim.spongetools.custom.type.block;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.state.StateProperty;

import net.hellheim.spongetools.custom.type.CustomTypeBuilder;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
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
		return this.translationKey(key).lootTable(key);
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
	 * Sets the {@link BlockTypeKeys#DISPLAY_STATE} context key.
	 */
	default BlockTypeBuilder displayState(final BlockState display) {
		return this.displayState(customState -> display);
	}
	
	/**
	 * Sets the {@link BlockTypeKeys#DISPLAY_STATE} context key.
	 */
	default BlockTypeBuilder displayState(final UnaryOperator<BlockState> display) {
		return this.set(BlockTypeKeys.DISPLAY_STATE, display);
	}
}
