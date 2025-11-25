package net.hellheim.spongetools.custom.type.item;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.type.ModeledCustomType;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelLike;
import net.hellheim.spongetools.resourcepack.ModelTemplateProvider;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.resourcepack.item.ItemDefinitionLike;
import net.hellheim.spongetools.resourcepack.item.ItemModel;
import net.hellheim.spongetools.util.ModelUtil;

/**
 * {@link ModeledCustomType} for {@link ItemType}.
 * 
 * @see #registry()
 * @see #builder(ResourceKey)
 */
public record ModeledItem(ItemType type, ItemDefinition definition, Map<ResourceKey, Model> models)
		implements ModeledCustomType<ItemType> {
	
	public ModeledItem(final ItemType type, final ItemDefinition definition, final Map<ResourceKey, Model> models) {
		this.type = Objects.requireNonNull(type, "type");
		this.definition = Objects.requireNonNull(definition, "defitnion");
		this.models = Map.copyOf(models);
	}
	
	public static DefaultedRegistryType<ModeledItem> registry() {
		return SpongeTools.Registries.MODELED_ITEM;
	}
	
	public static Builder builder(final ResourceKey key) {
		return new Builder(key);
	}
	
	public static final class Builder extends ModeledCustomType.Builder<ItemType, ItemTypeBuilder, ModeledItem, Builder> {
		
		private @Nullable ItemDefinition definition;
		
		private Builder(final ResourceKey key) {
			super(key);
			this.reset();
		}
		
		public Builder definition(final ItemDefinitionLike definition) {
			this.definition = Objects.requireNonNull(definition, "definition").asDefinition();
			return this;
		}
		
		public Builder definition(final Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.apply(this.key));
		}
		
		public Builder itemDefinition(final Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.compose(ModelUtil::withItemPrefix));
		}
		
		public Builder blockDefinition(final Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.compose(ModelUtil::withBlockPrefix));
		}
		
		public Builder simpleItemDefinition() {
			return this.itemDefinition(key -> ItemModel.simple(key));
		}
		
		public Builder simpleBlockDefinition() {
			return this.blockDefinition(key -> ItemModel.simple(key));
		}
		
		public Builder model(final UnaryOperator<ResourceKey> key, final ModelLike model) {
			return this.model(key.apply(this.key), model);
		}
		
		public Builder itemModel(final UnaryOperator<ResourceKey> key, final ModelLike model) {
			return this.model(k -> ModelUtil.withItemPrefix(key.apply(k)), model);
		}
		
		public Builder itemModel(final String keySuffix, final ModelLike model) {
			return this.itemModel(key -> ModelUtil.withSuffix(key, keySuffix), model);
		}
		
		public Builder blockModel(final UnaryOperator<ResourceKey> key, final ModelLike model) {
			return this.model(k -> ModelUtil.withBlockPrefix(key.apply(k)), model);
		}
		
		public Builder blockModel(final String keySuffix, final ModelLike model) {
			return this.blockModel(key -> ModelUtil.withSuffix(key, keySuffix), model);
		}
		
		public Builder simpleItemModel(final ModelLike model) {
			return this.simpleItemDefinition()
					.itemModel(UnaryOperator.identity(), model);
		}
		
		public Builder simpleItemModel(final Function<ResourceKey, ? extends ModelLike> model) {
			return this.simpleItemModel(model.apply(ModelUtil.withItemPrefix(this.key)));
		}
		
		public Builder simpleItemModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleItemModel(templateProvider::textured);
		}
		
		public Builder simpleBlockModel(final ModelLike model) {
			return this.simpleBlockDefinition()
					.blockModel(UnaryOperator.identity(), model);
		}
		
		public Builder simpleBlockModel(final Function<ResourceKey, ? extends ModelLike> model) {
			return this.simpleBlockModel(model.apply(ModelUtil.withBlockPrefix(this.key)));
		}
		
		public Builder simpleBlockModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleBlockModel(templateProvider::textured);
		}
		
		@Override
		public Builder reset() {
			this.definition = null;
			return super.reset();
		}
		
		@Override
		public ModeledItem build() {
			if (this.definition == null) {
				throw new IllegalStateException("definition must be set");
			}
			
			final ItemType item = this.buildType(ItemTypeBuilder.create().id(this.key));
			return new ModeledItem(item, this.definition, this.models);
		}
	}
}
