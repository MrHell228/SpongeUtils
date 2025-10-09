package net.hellheim.spongetools.resourcepack;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.annotation.CatalogedBy;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

@CatalogedBy(ModelTemplates.class)
public record ModelTemplate(Optional<ResourceKey> key, Optional<String> suffix, List<TextureSlot> slots) {
	
	private static final Multimap<ResourceKey, ModelTemplate> MULTIMAP = LinkedHashMultimap.create();
	
	public ModelTemplate(
		final Optional<ResourceKey> key,
		final Optional<String> suffix,
		final List<TextureSlot> slots
	) {
		this.key = Objects.requireNonNull(key, "key");
		this.suffix = Objects.requireNonNull(suffix, "suffix");
		this.slots = slots.stream().distinct().collect(Collectors.toUnmodifiableList());
		
		this.key.ifPresent(k -> ModelTemplate.MULTIMAP.put(k, this));
	}
	
	public static Collection<ModelTemplate> get(final ResourceKey key) {
		return ModelTemplate.MULTIMAP.get(Objects.requireNonNull(key, "key"));
	}
}
