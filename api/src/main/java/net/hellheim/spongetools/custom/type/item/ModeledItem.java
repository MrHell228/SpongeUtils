package net.hellheim.spongetools.custom.type.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.ModelLike;
import net.hellheim.spongetools.resourcepack.ModelTemplateProvider;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.resourcepack.item.ItemDefinitionLike;
import net.hellheim.spongetools.resourcepack.item.ItemModel;
import net.hellheim.spongetools.util.ModelUtil;

/**
 * Wrapper over regular {@link ItemType} with additional ResourcePack data. <br>
 * Registering to {@link #registry()} will register the wrapped type as well as model data.
 * 
 * @see #builder(ResourceKey)
 */
public record ModeledItem(ItemType type, ItemDefinition definition, Map<ResourceKey, Model> models)
		implements Supplier<ItemType> {
	
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
	
	@Override
	public ItemType get() {
		return this.type;
	}
	
	public static final class Builder implements org.spongepowered.api.util.Builder<ModeledItem, Builder> {
		
		private final ResourceKey key;
		private Consumer<ItemTypeBuilder> item;
		private @Nullable ItemDefinition definition;
		private final Map<ResourceKey, Model> models = new HashMap<>();
		
		private Builder(final ResourceKey key) {
			this.key = Objects.requireNonNull(key, "key");
			this.reset();
		}
		
		public Builder item(final Consumer<ItemTypeBuilder> configurator) {
			this.item = this.item.andThen(Objects.requireNonNull(configurator, "configurator"));
			return this;
		}
		
		public Builder definition(ItemDefinitionLike definition) {
			this.definition = Objects.requireNonNull(definition, "definition").asDefinition();
			return this;
		}
		
		public Builder definition(Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.apply(this.key));
		}
		
		public Builder itemDefinition(Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.compose(ModelUtil::withItemPrefix));
		}
		
		public Builder blockDefinition(Function<ResourceKey, ItemDefinitionLike> definition) {
			return this.definition(definition.compose(ModelUtil::withBlockPrefix));
		}
		
		public Builder simpleItemDefinition() {
			return this.itemDefinition(key -> ItemModel.simple(key));
		}
		
		public Builder simpleBlockDefinition() {
			return this.blockDefinition(key -> ItemModel.simple(key));
		}
		
		public Builder model(final ResourceKey key, final ModelLike model) {
			this.models.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(model, "model").asModel());
			return this;
		}
		
		public Builder model(final Function<ResourceKey, ResourceKey> key, final ModelLike model) {
			return this.model(key.apply(this.key), model);
		}
		
		public Builder itemModel(final Function<ResourceKey, ResourceKey> key, final ModelLike model) {
			return this.model(key.compose(ModelUtil::withItemPrefix), model);
		}
		
		public Builder itemModel(final String keySuffix, final ModelLike model) {
			return this.itemModel(key -> ModelUtil.withSuffix(key, keySuffix), model);
		}
		
		public Builder blockModel(final Function<ResourceKey, ResourceKey> key, final ModelLike model) {
			return this.model(key.compose(ModelUtil::withBlockPrefix), model);
		}
		
		public Builder blockModel(final String keySuffix, final ModelLike model) {
			return this.blockModel(key -> ModelUtil.withSuffix(key, keySuffix), model);
		}
		
		public Builder simpleItemModel(final ModelLike model) {
			return this.simpleItemDefinition()
					.itemModel(Function.identity(), model);
		}
		
		public Builder simpleItemModel(final Function<ResourceKey, ModelLike> model) {
			return this.simpleItemModel(model.apply(ModelUtil.withItemPrefix(this.key)));
		}
		
		public Builder simpleItemModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleItemModel(templateProvider::textured);
		}
		
		public Builder simpleBlockModel(final ModelLike model) {
			return this.simpleBlockDefinition()
					.blockModel(Function.identity(), model);
		}
		
		public Builder simpleBlockModel(final Function<ResourceKey, ModelLike> model) {
			return this.simpleBlockModel(model.apply(ModelUtil.withBlockPrefix(this.key)));
		}
		
		public Builder simpleBlockModel(final ModelTemplateProvider.T1 templateProvider) {
			return this.simpleBlockModel(templateProvider::textured);
		}
		
		@Override
		public Builder reset() {
			this.item = builder -> {};
			this.definition = null;
			this.models.clear();
			return this;
		}
		
		@Override
		public ModeledItem build() {
			if (this.definition == null) {
				throw new IllegalStateException("definition must be set");
			}
			
			final ItemTypeBuilder builder = ItemTypeBuilder.create().id(this.key);
			this.item.accept(builder);
			final ItemType item = builder.build();
			
			return new ModeledItem(item, this.definition, this.models);
		}
	}
}
