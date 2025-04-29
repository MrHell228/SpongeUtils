package net.hellheim.spongetools.custom.model.item;

import java.util.Objects;
import java.util.function.Consumer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.custom.model.ModelTemplate;
import net.hellheim.spongetools.custom.model.TexturedModel;
import net.hellheim.spongetools.custom.model.Textures;
import net.hellheim.spongetools.custom.model.util.GuiLight;

public record ItemModel(TexturedModel model, ItemTransforms display, GuiLight guiLight) {
	
	public static final Codec<ItemModel> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					TexturedModel.MAP_CODEC.forGetter(ItemModel::model),
					ItemTransforms.CODEC.optionalFieldOf("display", ItemTransforms.DEFAULT).forGetter(ItemModel::display),
					GuiLight.CODEC.optionalFieldOf("gui_light", GuiLight.DEFAULT).forGetter(ItemModel::guiLight)
					).apply(instance, ItemModel::new));
	
	public ItemModel(final TexturedModel model, final ItemTransforms display, final GuiLight guiLight) {
		this.model = Objects.requireNonNull(model, "model");
		this.display = Objects.requireNonNull(display, "display");
		this.guiLight = Objects.requireNonNull(guiLight, "guiLight");
	}
	
	public static DefaultedRegistryType<ItemModel> registry() {
		return SpongeTools.Registries.ITEM_MODEL;
	}
	
	public static Codec<ItemModel> registryCodec() {
		return RegistryCodecs.ITEM_MODEL;
	}
	
	public static ItemModel of(final ModelTemplate parent, final Textures textures) {
		return ItemModel.of(TexturedModel.of(parent, textures));
	}
	
	public static ItemModel of(final ModelTemplate parent, final Textures textures, final ItemTransforms display) {
		return ItemModel.of(TexturedModel.of(parent, textures), display);
	}
	
	public static ItemModel of(final ModelTemplate parent, final Textures textures, final GuiLight light) {
		return ItemModel.of(TexturedModel.of(parent, textures), light);
	}
	
	public static ItemModel of(final ModelTemplate parent, final Textures textures, final ItemTransforms display, final GuiLight light) {
		return ItemModel.of(TexturedModel.of(parent, textures), display, light);
	}
	
	public static ItemModel of(final TexturedModel model) {
		return ItemModel.of(model, ItemTransforms.DEFAULT, GuiLight.DEFAULT);
	}
	
	public static ItemModel of(final TexturedModel model, final ItemTransforms display) {
		return ItemModel.of(model, display, GuiLight.DEFAULT);
	}
	
	public static ItemModel of(final TexturedModel model, final GuiLight light) {
		return ItemModel.of(model, ItemTransforms.DEFAULT, light);
	}
	
	public static ItemModel of(final TexturedModel model, final ItemTransforms display, final GuiLight light) {
		return new ItemModel(model, display, light);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public ModelTemplate parent() {
		return this.model.parent();
	}
	
	public Textures textures() {
		return this.model.textures();
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<ItemModel, Builder>,
			CopyableBuilder<ItemModel, Builder> {
		
		private @Nullable ModelTemplate parent;
		private @Nullable Textures textures;
		private @Nullable ItemTransforms display;
		private @Nullable GuiLight guiLight;
		
		public Builder() {
			this.reset();
		}
		
		public Builder model(final TexturedModel model) {
			Objects.requireNonNull(model, "model");
			return this.parent(model.parent()).textures(model.textures());
		}
		
		public Builder parent(final ModelTemplate parent) {
			this.parent = Objects.requireNonNull(parent, "parent");
			return this;
		}
		
		public Builder textures(final Textures textures) {
			this.textures = Objects.requireNonNull(textures, "textures");
			return this;
		}
		
		public Builder textures(final Textures.Builder builder) {
			return this.textures(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder textures(final Consumer<Textures.Builder> configurator) {
			final Textures.Builder builder = Textures.builder();
			Objects.requireNonNull(configurator, "configurator").accept(builder);
			return this.textures(builder);
		}
		
		public Builder display(final ItemTransforms display) {
			this.display = Objects.requireNonNull(display, "display");
			return this;
		}
		
		public Builder display(final ItemTransforms.Builder builder) {
			return this.display(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder display(final Consumer<ItemTransforms.Builder> configurator) {
			final ItemTransforms.Builder builder = ItemTransforms.builder();
			Objects.requireNonNull(configurator, "configurator").accept(builder);
			return this.display(builder);
		}
		
		public Builder light(final GuiLight guiLight) {
			this.guiLight = Objects.requireNonNull(guiLight, "guiLight");
			return this;
		}
		
		@Override
		public Builder from(final ItemModel value) {
			this.parent = value.parent();
			this.textures = value.textures();
			this.display = value.display();
			this.guiLight = value.guiLight;
			return this;
		}
		
		@Override
		public Builder reset() {
			this.parent = null;
			this.textures = null;
			this.display = null;
			this.guiLight = null;
			return this;
		}
		
		@Override
		public ItemModel build() {
			return new ItemModel(TexturedModel.of(this.parent, this.textures), this.display, this.guiLight);
		}
	}
}
