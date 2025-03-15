package net.hellheim.spongetools.custom.item.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.SpongeCodecs;

public record Textures(Map<TextureSlot, ResourceKey> textures) {
	
	public static final Codec<Textures> CODEC =
			Codec.unboundedMap(TextureSlot.CODEC, SpongeCodecs.RESOURCE_KEY)
			.xmap(Textures::new, Textures::textures);
	
	public Textures(final Map<TextureSlot, ResourceKey> textures) {
		this.textures = Map.copyOf(textures);
	}
	
	public Set<TextureSlot> slots() {
		return this.textures.keySet();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<Textures, Builder>,
			CopyableBuilder<Textures, Builder> {
		
		private final Map<TextureSlot, ResourceKey> textures = new HashMap<>();
		
		public Builder put(final TextureSlot slot, final ResourceKey texture) {
			this.textures.put(
					Objects.requireNonNull(slot, "slot"),
					Objects.requireNonNull(texture, "texture"));
			return this;
		}
		
		public Builder putIfAbsent(final TextureSlot slot, final ResourceKey texture) {
			this.textures.putIfAbsent(
					Objects.requireNonNull(slot, "slot"),
					Objects.requireNonNull(texture, "texture"));
			return this;
		}
		
		public Builder putAll(final Map<? extends TextureSlot, ? extends ResourceKey> textures) {
			Objects.requireNonNull(textures, "textures").forEach(this::put);
			return this;
		}
		
		public Builder putAll(final ResourceKey texture, final TextureSlot... slots) {
			for (final TextureSlot slot : Objects.requireNonNull(slots, "slots")) {
				this.put(slot, texture);
			}
			return this;
		}
		
		public Builder putAll(final ResourceKey texture, final Iterable<? extends TextureSlot> slots) {
			for (final TextureSlot slot : Objects.requireNonNull(slots, "slots")) {
				this.put(slot, texture);
			}
			return this;
		}
		
		@Override
		public Builder from(final Textures value) {
			this.reset();
			this.textures.putAll(value.textures);
			return this;
		}
		
		@Override
		public Builder reset() {
			this.textures.clear();
			return this;
		}
		
		@Override
		public Textures build() {
			return new Textures(this.textures);
		}
	}
}
