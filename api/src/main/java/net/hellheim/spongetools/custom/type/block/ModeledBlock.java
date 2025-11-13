package net.hellheim.spongetools.custom.type.block;

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
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelLike;
import net.hellheim.spongetools.resourcepack.ModelTemplateProvider;
import net.hellheim.spongetools.resourcepack.block.StateDispatch;
import net.hellheim.spongetools.resourcepack.block.StateSelector;
import net.hellheim.spongetools.resourcepack.block.Variant;
import net.hellheim.spongetools.resourcepack.block.VariantList;
import net.hellheim.spongetools.resourcepack.block.VariantListLike;
import net.hellheim.spongetools.util.ModelUtil;

/**
 * Wrapper over regular {@link BlockType} with additional ResourcePack data. <br>
 * Registering to {@link #registry()} will register the wrapped type as well as model data.
 * 
 * @see #builder(ResourceKey)
 */
public record ModeledBlock(
		BlockType type,
		StateDispatch<BlockStateProvider> providers,
		StateDispatch<VariantListLike> variants,
		Map<ResourceKey, Model> models
		) implements Supplier<BlockType> {
	
	public ModeledBlock(
		final BlockType type, final StateDispatch<BlockStateProvider> providers,
		final StateDispatch<VariantListLike> variants, final Map<ResourceKey, Model> models
	) {
		this.type = Objects.requireNonNull(type, "type");
		this.providers = Objects.requireNonNull(providers, "providers");
		this.variants = Objects.requireNonNull(variants, "variants");
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
	
	public Map<VariantList, List<StateSelector>> variantToSelectorMap() {
		return this.variants.values().entrySet().stream()
				.collect(Collectors.groupingBy(
						e -> e.getValue().asVariantList(), Collectors.mapping(
								Map.Entry::getKey, Collectors.toList())));
	}
	
	public Map<VariantList, BlockStateProvider> variantToProviderMap() {
		final Map<VariantList, BlockStateProvider> map = new HashMap<>();
		this.variantToSelectorMap().forEach((variant, selectors) ->
				map.put(variant, this.providers.getForAnySelector(selectors)));
		return map;
	}
	
	public static final class Builder implements org.spongepowered.api.util.Builder<ModeledBlock, Builder> {
		
		private final ResourceKey key;
		private final ResourceKey prefixedKey;
		private Consumer<BlockTypeBuilder> block;
		private StateDispatch<BlockStateProvider> providers;
		private StateDispatch<VariantListLike> variants;
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
		
		public Builder providers(final StateDispatch<BlockStateProvider> providers) {
			this.providers = Objects.requireNonNull(providers, "providers");
			return this;
		}
		
		public Builder providers(final StateDispatch.Builder<BlockStateProvider, ?> builder) {
			return this.providers(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder provider(final BlockStateProvider provider) {
			return this.providers(StateDispatch.of(provider));
		}
		
		public Builder variants(final StateDispatch<VariantListLike> variants) {
			this.variants = Objects.requireNonNull(variants, "variants");
			return this;
		}
		
		public Builder variants(final StateDispatch.Builder<VariantListLike, ?> builder) {
			return this.variants(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder variants(final VariantListLike variants) {
			return this.variants(StateDispatch.of(variants));
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
		
		public Builder simpleModel(final ModelLike model) {
			return this
					.variants(Variant.model(this.prefixedKey))
					.model(this.prefixedKey, model);
		}
		
		public Builder simpleModel(final Function<ResourceKey, ModelLike> model) {
			return this.simpleModel(model.apply(this.prefixedKey));
		}
		
		public Builder simpleModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleModel(templateProvider::textured);
		}
		
		@Override
		public Builder reset() {
			this.block = builder -> {};
			this.providers = null;
			this.variants = StateDispatch.of(Variant.model(this.prefixedKey));
			this.models.clear();
			return this;
		}
		
		@Override
		public ModeledBlock build() {
			if (this.providers == null) {
				throw new IllegalStateException("providers must be set");
			}
			
			final BlockTypeBuilder builder = BlockTypeBuilder.create().id(this.key);
			this.block.accept(builder);
			final BlockType block = builder.build();
			
			final ModeledBlock modeledBlock = new ModeledBlock(block, this.providers, this.variants, this.models);
			modeledBlock.variantToProviderMap(); // Validation
			return modeledBlock;
		}
	}
}
