package net.hellheim.spongetools.custom.type.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelLike;
import net.hellheim.spongetools.resourcepack.ModelTemplateProvider;
import net.hellheim.spongetools.resourcepack.block.Variant;
import net.hellheim.spongetools.util.ModelUtil;

/**
 * Wrapper over regular {@link BlockType} with additional ResourcePack data. <br>
 * Registering to {@link #registry()} will register the wrapped type as well as model data.
 * 
 * @see #builder(ResourceKey)
 */
public record ModeledBlock(BlockType type, Map<BlockState, Variant> variants, Map<ResourceKey, Model> models)
		implements Supplier<BlockType> {
	
	public ModeledBlock(
		final BlockType type, final Map<BlockState, Variant> variants, final Map<ResourceKey, Model> models
	) {
		this.type = Objects.requireNonNull(type, "type");
		this.variants = Map.copyOf(variants);
		this.models = Map.copyOf(models);
	}
	
	public static DefaultedRegistryType<ModeledBlock> registry() {
		return SpongeTools.Registries.MODELED_BLOCK;
	}
	
	public static Builder builder(final ResourceKey key) {
		return new Builder(key);
	}
	
	@Override
	public BlockType get() {
		return this.type;
	}
	
	public static final class Builder implements org.spongepowered.api.util.Builder<ModeledBlock, Builder> {
		
		private final ResourceKey key;
		private final ResourceKey prefixedKey;
		private Consumer<BlockTypeBuilder> block;
		private StateFunction<Variant> variants;
		private final Map<ResourceKey, Model> models = new HashMap<>();
		
		public Builder(final ResourceKey key) {
			this.key = Objects.requireNonNull(key, "key");
			this.prefixedKey = ModelUtil.withBlockPrefix(this.key);
			this.reset();
		}
		
		public Builder block(final Consumer<BlockTypeBuilder> configurator) {
			this.block = this.block.andThen(Objects.requireNonNull(configurator, "configurator"));
			return this;
		}
		
		public Builder variants(final StateFunction<Variant> variantProvider) {
			this.variants = Objects.requireNonNull(variantProvider, "variantProvider");
			return this;
		}
		
		public Builder model(final ResourceKey key, final ModelLike model) {
			this.models.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(model, "model").asModel());
			return this;
		}
		
		public Builder model(final UnaryOperator<ResourceKey> key, final ModelLike model) {
			return this.model(key.apply(this.prefixedKey), model);
		}
		
		public Builder model(final String keySuffix, final ModelLike model) {
			return this.model(key -> ModelUtil.withSuffix(key, keySuffix), model);
		}
		
		public Builder dispatch(final List<ModelLike> models, final StateFunction<Integer> modelProvider) {
			Objects.requireNonNull(models, "models");
			Objects.requireNonNull(modelProvider, "modelProvider");
			final List<ResourceKey> modelKeys = new ArrayList<>();
			for (int i = 0; i < models.size(); ++i) {
				final ResourceKey modelKey = ModelUtil.withSuffix(this.prefixedKey, "_" + i);
				modelKeys.add(modelKey);
				this.model(modelKey, models.get(i));
			}
			
			return this.variants((display, states) -> {
				final int i = modelProvider.apply(display, states);
				final ResourceKey key = modelKeys.get(i);
				return Variant.model(key);
			});
		}
		
		public Builder simpleModel(final ModelLike model) {
			return this
					.variants((display, states) -> Variant.model(this.prefixedKey))
					.model(this.prefixedKey, model);
		}
		
		public Builder simpleModel(final Function<ResourceKey, ModelLike> model) {
			return this.simpleModel(model.apply(this.prefixedKey));
		}
		
		public Builder simpleModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleModel(key -> templateProvider.textured(key));
		}
		
		@Override
		public Builder reset() {
			this.block = builder -> {};
			this.variants = null;
			this.models.clear();
			return this;
		}
		
		@Override
		public ModeledBlock build() {
			if (this.variants == null) {
				throw new IllegalStateException("variants must be set");
			}
			
			final BlockTypeBuilder builder = BlockTypeBuilder.create().id(this.key);
			this.block.accept(builder);
			final BlockType block = builder.build();
			
			final Map<BlockState, Variant> variants = block.validStates().stream()
					.collect(Collectors.groupingBy(state -> BlockStateExtension.getFor(state).display()))
					.entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey(), e -> this.variants.apply(e.getKey(), e.getValue())));
			
			return new ModeledBlock(block, variants, this.models);
		}
	}
	
	@FunctionalInterface
	public static interface StateFunction<R> {
		
		R apply(BlockState display, List<BlockState> states);
	}
}
