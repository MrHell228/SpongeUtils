package net.hellheim.spongetools.custom.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.RegistryKey;

import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.util.ModelUtil;

public record TexturedModel(ModelTemplate parent, Textures textures) {
	
	public static final MapCodec<TexturedModel> MAP_CODEC = RecordCodecBuilder.<TexturedModel>mapCodec(
			instance -> instance.group(
					SpongeCodecs.RESOURCE_KEY.optionalFieldOf("parent").forGetter(TexturedModel::key),
					Textures.CODEC.fieldOf("textures").forGetter(TexturedModel::textures)
					).apply(instance, TexturedModel::of)
			).validate(model -> {
				final Optional<String> exception = TexturedModel.validate(model);
				return exception.isEmpty()
						? DataResult.success(model)
						: DataResult.error(exception::get);
			});
	
	public TexturedModel(final ModelTemplate parent, final Textures textures) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.textures = Objects.requireNonNull(textures, "textures");
	}
	
	public Optional<ResourceKey> key() {
		return this.parent.key();
	}
	
	public static TexturedModel of(final Supplier<ModelTemplate> parent, final Textures textures) {
		return TexturedModel.of(Objects.requireNonNull(parent, "parent").get(), textures);
	}
	
	public static TexturedModel of(final ModelTemplate parent, final Textures textures) {
		final TexturedModel model = new TexturedModel(parent, textures);
		final Optional<String> exception = TexturedModel.validate(model);
		if (exception.isPresent()) {
			throw new IllegalArgumentException(exception.get());
		}
		return model;
	}
	
	public static Optional<String> validate(final TexturedModel model) {
		final Set<TextureSlot> required = Set.copyOf(model.parent.slots());
		final Set<TextureSlot> provided = model.textures.slots();
		
		if (required.equals(provided)) {
			return Optional.empty();
		}
		
		String message = "\n";
		
		for (final TextureSlot slot : Sets.difference(required, provided)) {
			message += "TextureSlot required but not provided: " + slot.id() + "\n";
		}
		
		for (final TextureSlot slot : Sets.difference(provided, required)) {
			message += "TextureSlot provided but not required: " + slot.id() + "\n";
		}
		
		return Optional.of(message);
	}
	
	private static TexturedModel of(final Optional<ResourceKey> optionalKey, final Textures textures) {
		if (optionalKey.isEmpty()) {
			return new TexturedModel(ModelTemplates.PARTICLE_ONLY.get(), textures);
		}
		
		final ResourceKey key = optionalKey.get();
		final Collection<ModelTemplate> templates = ModelTemplate.get(key);
		if (templates.isEmpty()) {
			final ModelTemplate template = new ModelTemplate(optionalKey, Optional.empty(), List.of());
			return new TexturedModel(template, textures);
		}
		
		if (templates.size() == 1) {
			return new TexturedModel(templates.iterator().next(), textures);
		}
		
		for (final ModelTemplate template : templates) {
			if (textures.slots().size() == template.slots().size()
					&& textures.slots().containsAll(template.slots())) {
				return new TexturedModel(template, textures);
			}
		}
		
		// TODO What to do here?
		return new TexturedModel(templates.iterator().next(), textures);
	}
	
	// TODO remove?
	public static interface Provider {
		
		TexturedModel get(ResourceKey key);
		
		default TexturedModel get(final RegistryKey<?> key) {
			return this.get(key.location());
		}
		
		default TexturedModel block(final ResourceKey key) {
			return this.get(ModelUtil.withBlockPrefix(key));
		}
		
		default TexturedModel block(final RegistryKey<?> key) {
			return this.block(key.location());
		}
		
		default TexturedModel item(final ResourceKey key) {
			return this.get(ModelUtil.withItemPrefix(key));
		}
		
		default TexturedModel item(final RegistryKey<?> key) {
			return this.item(key.location());
		}
	}
}
