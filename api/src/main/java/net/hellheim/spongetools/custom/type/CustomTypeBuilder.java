package net.hellheim.spongetools.custom.type;

import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.util.Builder;
import org.spongepowered.api.util.CopyableBuilder;

import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.type.block.BlockTypeBuilder;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Builder for custom type. It's represented by some specific concepts: <br>
 * - {@link CustomArchetype} - the core of the type; <br> 
 * - Context (applied through {@link TypedKey}s) - used to construct the type itself; <br>
 * - Data (applied through {@link Key}s) - applied to the mutable instances created from the built type (if applicable); <br>
 * - Behaviour (applied through {@link BehaviourType}s) - TODO <br>
 * <br>
 * <b>Note:</b> No data involving server-scoped registries should be used while building the type. <br>
 * It should not be an issue as most of mentioned data is usually applied per mutable instance. <br>
 * <b>Note:</b> All registries that the built type depends on must be passed to {@link RegisterRegistryValueEvent}. <br>
 * Mandatory dependencies usually can be retrieved via {@link #dependencies()} or
 * {@link #dependencies(RegistryType...)} methods of the specific builder classes.
 * 
 * @see ItemTypeBuilder
 * @see BlockTypeBuilder
 */
public interface CustomTypeBuilder<T, A extends CustomArchetype<T, A>, B extends CustomTypeBuilder<T, A, B>> extends
		Builder<T, B>,
		CopyableBuilder<T, B>,
		TypedKeyMap.Operator<B> {
	
	/**
	 * Sets the archetype of the built type.
	 * 
	 * @param archetype The archetype
	 * @return This builder, for chaining
	 */
	default B archetype(final Supplier<? extends A> archetype) {
		return this.archetype(Objects.requireNonNull(archetype, "archetype").get());
	}
	
	/**
	 * Sets the archetype of the build type.
	 * 
	 * @param archetype The archetype
	 * @return This builder, for chaining
	 */
	B archetype(A archetype);
	
	@Override
	B reset();
}
