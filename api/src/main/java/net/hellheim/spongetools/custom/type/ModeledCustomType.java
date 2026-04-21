package net.hellheim.spongetools.custom.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;

import net.hellheim.spongetools.custom.type.block.ModeledBlock;
import net.hellheim.spongetools.custom.type.item.ModeledItem;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelLike;

/**
 * Wrapper over regular {@link T type} with additional ResourcePack data. <br>
 * Registering modeled types will register the wrapped type as well as all the model data. <br>
 * Registry can usually be retrieved via {@link #registry()} method of the corresponding modeled type class. <br>
 * 
 * @param <T> The type of the custom type to wrap
 * 
 * @see ModeledItem
 * @see ModeledBlock
 */
public interface ModeledCustomType<T> extends Supplier<T> {
	
	/**
	 * Returns the wrapped type.
	 * 
	 * @return The wrapped type
	 */
	T type();
	
	/**
	 * Returns the models.
	 * 
	 * @return The models
	 */
	Map<ResourceKey, Model> models();
	
	@Override
	default T get() {
		return this.type();
	}
	
	/**
	 * Builder for {@link M modeled type}.
	 * 
	 * @param <T> The type of the custom type to built
	 * @param <TB> The type of the corresponding {@link CustomTypeBuilder}
	 * @param <M> The type of the corresponding {@link ModeledCustomType}
	 * @param <B> The type of this builder
	 */
	abstract class Builder<T, TB extends CustomTypeBuilder<T, ?, ?, TB>, M extends ModeledCustomType<T>, B extends Builder<T, TB, M, B>>
			implements org.spongepowered.api.util.Builder<M, B> {
		
		protected final ResourceKey key;
		protected final Map<ResourceKey, Model> models = new HashMap<>();
		protected UnaryOperator<TB> type = UnaryOperator.identity();
		
		public Builder(final ResourceKey key) {
			this.key = Objects.requireNonNull(key, "key");
		}
		
		@SuppressWarnings("unchecked")
		private B cast() {
			return (B) this;
		}
		
		public B type(final UnaryOperator<TB> configurator) {
			this.type = this.type.compose(Objects.requireNonNull(configurator, "configurator"))::apply;
			return this.cast();
		}
		
		public B model(final ResourceKey key, final ModelLike model) {
			this.models.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(model, "model").asModel());
			return this.cast();
		}
		
		@Override
		public B reset() {
			this.models.clear();
			this.type = UnaryOperator.identity();
			return this.cast();
		}
		
		protected T buildType(final TB builder) {
			return this.type.apply(builder).build();
		}
	}
}
