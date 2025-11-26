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
import net.hellheim.spongetools.object.DataOperator;
import net.hellheim.spongetools.object.TypedKey;
import net.hellheim.spongetools.object.TypedKeyMap;

/**
 * Builder for custom type. It's represented by some specific concepts: <br>
 * - {@link A Archetype} - the core of the built type; <br> 
 * - Context (applied through {@link TypedKey}s) - used to construct the type itself; <br>
 * - Behaviour (applied through {@link BehaviourType}s) - TODO <br>
 * <br>
 * <b>Note:</b> All registries that the built type depends on must be passed to {@link RegisterRegistryValueEvent}. <br>
 * Mandatory dependencies usually can be retrieved via {@link #dependencies()} or
 * {@link #dependencies(RegistryType...)} methods of the specific builder classes.
 * 
 * @param <T> The type built by this builder
 * @param <I> The type of the corresponding instance with the custom type
 * @param <A> The type of the corresponding {@link CustomTypeArchetype}
 * @param <B> The type of this builder
 * 
 * @see ItemTypeBuilder
 * @see BlockTypeBuilder
 * @see TODO BlockEntityTypeBuilder
 * @see TODO EntityTypeBuilder
 * @see TODO PotionEffectTypeBuilder (no data)
 * @see TODO AttributeTypeBuilder    (no data)
 */
public interface CustomTypeBuilder<T, I, A extends CustomTypeArchetype<T, A>, B extends CustomTypeBuilder<T, I, A, B>>
		extends Builder<T, B>, TypedKeyMap.Operator<B> {
	
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
	 * Sets the archetype of the built type.
	 * 
	 * @param archetype The archetype
	 * @return This builder, for chaining
	 */
	B archetype(A archetype);
	
	@Override
	B reset();
	
	/**
	 * {@link CustomTypeBuilder} for type which instance's logic depends on corresponding type's logic. <br>
	 * 
	 */
	interface TypeBased<T, I, A extends CustomTypeArchetype<T, A>, B extends TypeBased<T, I, A, B>> extends
			CustomTypeBuilder<T, I, A, B>,
			CopyableBuilder<T, B> {
	}
	
	interface InstanceBased<T, I, A extends CustomTypeArchetype<T, A>, B extends InstanceBased<T, I, A, B>> extends
			CustomTypeBuilder<T, I, A, B> {
	}
	
	/**
	 * {@link CustomTypeBuilder} that supports data (applied through {@link Key}s). <br>
	 * All the supported data applies to the {@link I instances}. <br>
	 * <br>
	 * <b>Note:</b> No data involving server-scoped registries should be used while building the type. <br>
	 * It should not be an issue as most of mentioned data is usually applied per mutable {@link I instance}.
	 * 
	 * @param <T> The type built by this builder
	 * @param <I> The type of the corresponding instance with the custom type
	 * @param <A> The type of the corresponding {@link CustomTypeArchetype}
	 * @param <B> The type of this builder
	 */
	interface WithData<T, I, A extends CustomTypeArchetype<T, A>, B extends WithData<T, I, A, B>> extends
			CustomTypeBuilder<T, I, A, B>,
			DataOperator<B> {
	}
}
