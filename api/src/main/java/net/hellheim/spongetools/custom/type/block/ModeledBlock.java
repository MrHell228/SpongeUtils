package net.hellheim.spongetools.custom.type.block;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Sets;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.ModeledCustomType;
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
 * {@link ModeledCustomType} for {@link BlockType}.
 * 
 * @see #registry()
 * @see #builder(ResourceKey)
 */
public record ModeledBlock(
		BlockType type,
		StateDispatch<BlockStateProvider> providers,
		StateDispatch<VariantList> variants,
		Map<ResourceKey, Model> models
		) implements ModeledCustomType<BlockType> {
	
	public ModeledBlock(
		final BlockType type, final StateDispatch<BlockStateProvider> providers,
		final StateDispatch<VariantList> variants, final Map<ResourceKey, Model> models
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
	
	public Set<Tuple<BlockStateProvider, VariantList>> uniqueModels() {
		final Set<Tuple<BlockStateProvider, VariantList>> set = new HashSet<>();
		StateSelector.populate(Sets.union(this.providers.properties(), this.variants.properties())).forEach(
				selector -> set.add(Tuple.of(this.providers.getFor(selector), this.variants.getFor(selector))));
		return set;
	}
	
	public Tuple<BlockStateProvider, VariantList> model(final BlockState state) {
		return Tuple.of(this.providers.getFor(state), this.variants.getFor(state));
	}
	
	public static final class Builder
			extends ModeledCustomType.Builder<BlockType, BlockTypeBuilder, ModeledBlock, Builder> {
		
		private final ResourceKey prefixedKey;
		private StateDispatch<BlockStateProvider> providers;
		private StateDispatch<VariantList> variants;
		
		public Builder(final ResourceKey key) {
			super(key);
			this.prefixedKey = ModelUtil.blockPrefix(this.key);
			this.reset();
		}
		
		public Builder providers(final StateDispatch<? extends BlockStateProvider> providers) {
			this.providers = Objects.requireNonNull(providers, "providers").map(Function.identity());
			return this;
		}
		
		public Builder providers(final StateDispatch.Builder<? extends BlockStateProvider, ?> builder) {
			return this.providers(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder provider(final BlockStateProvider provider) {
			return this.providers(StateDispatch.of(provider));
		}
		
		public Builder variants(final StateDispatch<? extends VariantListLike> variants) {
			this.variants = Objects.requireNonNull(variants, "variants").map(VariantListLike::asVariantList);
			return this;
		}
		
		public Builder variants(final StateDispatch.Builder<? extends VariantListLike, ?> builder) {
			return this.variants(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder variants(final VariantListLike variants) {
			return this.variants(StateDispatch.of(variants));
		}
		
		public Builder variants(final Function<ResourceKey, StateDispatch<? extends VariantListLike>> variants) {
			return this.variants(variants.apply(this.prefixedKey));
		}
		
		public Builder model(final UnaryOperator<ResourceKey> key, final ModelLike model) {
			return this.model(key.apply(this.prefixedKey), model);
		}
		
		public Builder model(final String keySuffix, final ModelLike model) {
			return this.model(key -> ModelUtil.suffix(key, keySuffix), model);
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
			this.providers = null;
			this.variants = StateDispatch.of(Variant.model(this.prefixedKey).asVariantList());
			return super.reset();
		}
		
		@Override
		public ModeledBlock build() {
			if (this.providers == null) {
				throw new IllegalStateException("providers must be set");
			}
			
			final BlockType block = this.buildType(BlockTypeBuilder.create().id(this.key));
			final ModeledBlock modeledBlock = new ModeledBlock(block, this.providers, this.variants, this.models);
			modeledBlock.uniqueModels(); // Validation
			return modeledBlock;
		}
	}
}
