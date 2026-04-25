package net.hellheim.spongetools.resourcepack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.util.ModelUtil;

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
	
	// Generic builders
	
	public static Textures of(final Map<TextureSlot, ResourceKey> textures) {
		return new Textures(textures);
	}
	
	public static Textures of(
		final TextureSlot slot, final ResourceKey texture
	) {
		return Textures.of(Map.of(
				slot, texture
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2,
				slot3, texture3
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2,
				slot3, texture3,
				slot4, texture4
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2,
				slot3, texture3,
				slot4, texture4,
				slot5, texture5
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2,
				slot3, texture3,
				slot4, texture4,
				slot5, texture5,
				slot6, texture6
				));
	}
	
	public static Textures of(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6,
		final TextureSlot slot7, final ResourceKey texture7
	) {
		return Textures.of(Map.of(
				slot1, texture1,
				slot2, texture2,
				slot3, texture3,
				slot4, texture4,
				slot5, texture5,
				slot6, texture6,
				slot7, texture7
				));
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot, final String suffix
	) {
		return Textures.of(
				slot, ModelUtil.suffix(texture, suffix)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2),
				slot3, ModelUtil.suffix(texture, suffix3)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2),
				slot3, ModelUtil.suffix(texture, suffix3),
				slot4, ModelUtil.suffix(texture, suffix4)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2),
				slot3, ModelUtil.suffix(texture, suffix3),
				slot4, ModelUtil.suffix(texture, suffix4),
				slot5, ModelUtil.suffix(texture, suffix5)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2),
				slot3, ModelUtil.suffix(texture, suffix3),
				slot4, ModelUtil.suffix(texture, suffix4),
				slot5, ModelUtil.suffix(texture, suffix5),
				slot6, ModelUtil.suffix(texture, suffix6)
				);
	}
	
	public static Textures of(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6,
		final TextureSlot slot7, final String suffix7
	) {
		return Textures.of(
				slot1, ModelUtil.suffix(texture, suffix1),
				slot2, ModelUtil.suffix(texture, suffix2),
				slot3, ModelUtil.suffix(texture, suffix3),
				slot4, ModelUtil.suffix(texture, suffix4),
				slot5, ModelUtil.suffix(texture, suffix5),
				slot6, ModelUtil.suffix(texture, suffix6),
				slot7, ModelUtil.suffix(texture, suffix7)
				);
	}
	
	// Block-specific builders
	
	public static Textures block(
		final TextureSlot slot, final ResourceKey texture
	) {
		return Textures.of(
				slot, ModelUtil.blockPrefix(texture)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2),
				slot3, ModelUtil.blockPrefix(texture3)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2),
				slot3, ModelUtil.blockPrefix(texture3),
				slot4, ModelUtil.blockPrefix(texture4)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2),
				slot3, ModelUtil.blockPrefix(texture3),
				slot4, ModelUtil.blockPrefix(texture4),
				slot5, ModelUtil.blockPrefix(texture5)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2),
				slot3, ModelUtil.blockPrefix(texture3),
				slot4, ModelUtil.blockPrefix(texture4),
				slot5, ModelUtil.blockPrefix(texture5),
				slot6, ModelUtil.blockPrefix(texture6)
				);
	}
	
	public static Textures block(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6,
		final TextureSlot slot7, final ResourceKey texture7
	) {
		return Textures.of(
				slot1, ModelUtil.blockPrefix(texture1),
				slot2, ModelUtil.blockPrefix(texture2),
				slot3, ModelUtil.blockPrefix(texture3),
				slot4, ModelUtil.blockPrefix(texture4),
				slot5, ModelUtil.blockPrefix(texture5),
				slot6, ModelUtil.blockPrefix(texture6),
				slot7, ModelUtil.blockPrefix(texture7)
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot, final String suffix
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot, suffix
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5,
				slot6, suffix6
				);
	}
	
	public static Textures block(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6,
		final TextureSlot slot7, final String suffix7
	) {
		return Textures.of(ModelUtil.blockPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5,
				slot6, suffix6,
				slot7, suffix7
				);
	}
	
	// Item-specific builders
	
	public static Textures item(
		final TextureSlot slot, final ResourceKey texture
	) {
		return Textures.of(
				slot, ModelUtil.itemPrefix(texture)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2),
				slot3, ModelUtil.itemPrefix(texture3)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2),
				slot3, ModelUtil.itemPrefix(texture3),
				slot4, ModelUtil.itemPrefix(texture4)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2),
				slot3, ModelUtil.itemPrefix(texture3),
				slot4, ModelUtil.itemPrefix(texture4),
				slot5, ModelUtil.itemPrefix(texture5)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2),
				slot3, ModelUtil.itemPrefix(texture3),
				slot4, ModelUtil.itemPrefix(texture4),
				slot5, ModelUtil.itemPrefix(texture5),
				slot6, ModelUtil.itemPrefix(texture6)
				);
	}
	
	public static Textures item(
		final TextureSlot slot1, final ResourceKey texture1,
		final TextureSlot slot2, final ResourceKey texture2,
		final TextureSlot slot3, final ResourceKey texture3,
		final TextureSlot slot4, final ResourceKey texture4,
		final TextureSlot slot5, final ResourceKey texture5,
		final TextureSlot slot6, final ResourceKey texture6,
		final TextureSlot slot7, final ResourceKey texture7
	) {
		return Textures.of(
				slot1, ModelUtil.itemPrefix(texture1),
				slot2, ModelUtil.itemPrefix(texture2),
				slot3, ModelUtil.itemPrefix(texture3),
				slot4, ModelUtil.itemPrefix(texture4),
				slot5, ModelUtil.itemPrefix(texture5),
				slot6, ModelUtil.itemPrefix(texture6),
				slot7, ModelUtil.itemPrefix(texture7)
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot, final String suffix
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot, suffix
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5,
				slot6, suffix6
				);
	}
	
	public static Textures item(
		final ResourceKey texture,
		final TextureSlot slot1, final String suffix1,
		final TextureSlot slot2, final String suffix2,
		final TextureSlot slot3, final String suffix3,
		final TextureSlot slot4, final String suffix4,
		final TextureSlot slot5, final String suffix5,
		final TextureSlot slot6, final String suffix6,
		final TextureSlot slot7, final String suffix7
	) {
		return Textures.of(ModelUtil.itemPrefix(texture),
				slot1, suffix1,
				slot2, suffix2,
				slot3, suffix3,
				slot4, suffix4,
				slot5, suffix5,
				slot6, suffix6,
				slot7, suffix7
				);
	}
	
	// Other builders
	// TODO remove?
	
	public static Textures all(final ResourceKey texture) {
		return Textures.of(TextureSlots.ALL, texture);
	}
	
	public static Textures column(final ResourceKey texture) {
		return Textures.of(texture,
				TextureSlots.SIDE, "_side",
				TextureSlots.END, "_top");
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
