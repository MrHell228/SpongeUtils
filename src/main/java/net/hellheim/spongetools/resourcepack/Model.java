package net.hellheim.spongetools.resourcepack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.resourcepack.util.GuiLight;

// TODO make ModelTemplate optional (?)
/**
 * @see <a href=https://minecraft.wiki/w/Model> Minecraft Wiki </a>
 */
public record Model(TexturedModel model, ItemTransform display, List<ModelPart> parts, GuiLight guiLight, boolean ambientOcclusion) {
	
	public static final boolean DEFAULT_AMBIENT_OCCLUSION = true;
	
	public static final Codec<Model> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					TexturedModel.MAP_CODEC.forGetter(Model::model),
					ItemTransform.CODEC.optionalFieldOf("display", ItemTransform.DEFAULT).forGetter(Model::display),
					ModelPart.CODEC.listOf().optionalFieldOf("elements", List.of()).forGetter(Model::parts),
					GuiLight.CODEC.optionalFieldOf("gui_light", GuiLight.DEFAULT).forGetter(Model::guiLight),
					Codec.BOOL.optionalFieldOf("ambientocclusion", Model.DEFAULT_AMBIENT_OCCLUSION).forGetter(Model::ambientOcclusion)
					).apply(instance, Model::new));
	
	public Model(
		final TexturedModel model,
		final ItemTransform display, final List<ModelPart> parts,
		final GuiLight guiLight, final boolean ambientOcclusion
	) {
		this.model = Objects.requireNonNull(model, "model");
		this.parts = Objects.requireNonNull(parts, "parts");
		this.display = Objects.requireNonNull(display, "display");
		this.guiLight = Objects.requireNonNull(guiLight, "guiLight");
		this.ambientOcclusion = ambientOcclusion;
	}
	
	public static DefaultedRegistryType<Model> registry() {
		return SpongeTools.Registries.MODEL;
	}
	
	public static Codec<Model> registryCodec() {
		return RegistryCodecs.MODEL;
	}
	
	public static Model of(final ModelTemplate parent, final Textures textures, final ModelPart... parts) {
		return Model.of(TexturedModel.of(parent, textures), parts);
	}
	
	public static Model of(final ModelTemplate parent, final Textures textures, final ItemTransform display, final ModelPart... parts) {
		return Model.of(TexturedModel.of(parent, textures), display, parts);
	}
	
	public static Model of(final TexturedModel model, final ModelPart... parts) {
		return Model.of(model, ItemTransform.DEFAULT, parts);
	}
	
	public static Model of(final TexturedModel model, final ItemTransform display, final ModelPart... parts) {
		return Model.builder().model(model).display(display).parts(parts).build();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public ModelTemplate parent() {
		return this.model().parent();
	}
	
	public Textures textures() {
		return this.model().textures();
	}
	
	public static final class Builder implements
			org.spongepowered.api.util.Builder<Model, Builder>,
			CopyableBuilder<Model, Builder> {
		
		private @Nullable ModelTemplate parent;
		private @Nullable Textures textures;
		private ItemTransform display;
		private GuiLight guiLight;
		private boolean ambientOcclusion;
		private final List<ModelPart> parts = new ArrayList<>();
		
		private Builder() {
			this.reset();
		}
		
		public Builder display(final ItemTransform display) {
			this.display = Objects.requireNonNull(display, "display");
			return this;
		}
		
		public Builder display(final ItemTransform.Builder builder) {
			return this.display(Objects.requireNonNull(builder, "builder").build());
		}
		
		public Builder display(final Consumer<ItemTransform.Builder> configurator) {
			final ItemTransform.Builder builder = ItemTransform.builder();
			Objects.requireNonNull(configurator, "configurator").accept(builder);
			return this.display(builder);
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
		
		public Builder light(final GuiLight guiLight) {
			this.guiLight = Objects.requireNonNull(guiLight, "guiLight");
			return this;
		}
		
		public Builder occlusion(final boolean ambientOcclusion) {
			this.ambientOcclusion = ambientOcclusion;
			return this;
		}
		
		public Builder parts(final ModelPart.Builder... builders) {
			return this.parts(Arrays.stream(Objects.requireNonNull(builders, "builders")).map(ModelPart.Builder::build));
		}
		
		public Builder parts(final ModelPart... parts) {
			return this.parts(Arrays.stream(Objects.requireNonNull(parts, "parts")));
		}
		
		public Builder parts(final Collection<ModelPart> parts) {
			return this.parts(Objects.requireNonNull(parts, "parts").stream());
		}
		
		private Builder parts(final Stream<ModelPart> parts) {
			parts.forEach(part -> this.parts.add(Objects.requireNonNull(part, "part")));
			return this;
		}
		
		@Override
		public Builder from(final Model model) {
			return this.model(model.model())
					.display(model.display())
					.light(model.guiLight())
					.occlusion(model.ambientOcclusion())
					.parts(model.parts());
		}
		
		@Override
		public Builder reset() {
			this.parent = null;
			this.textures = null;
			this.display = ItemTransform.DEFAULT;
			this.guiLight = GuiLight.DEFAULT;
			this.ambientOcclusion = Model.DEFAULT_AMBIENT_OCCLUSION;
			this.parts.clear();
			return this;
		}
		
		@Override
		public Model build() {
			if (this.parent == null) {
				throw new IllegalStateException("parent must be set");
			} else if (this.textures == null) {
				throw new IllegalStateException("textures must be set");
			}
			
			return new Model(
					TexturedModel.of(this.parent, this.textures),
					this.display, this.parts,
					this.guiLight, this.ambientOcclusion);
		}
	}
}
