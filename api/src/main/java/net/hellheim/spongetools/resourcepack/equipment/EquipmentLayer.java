package net.hellheim.spongetools.resourcepack.equipment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

/**
 * A single layer of the {@link EquipmentAsset}.
 */
public record EquipmentLayer(ResourceKey texture, Optional<EquipmentDye> dyeable, boolean usePlayerTexture) {
	
	private static final boolean DEFAULT_USE_PLAYER_TEXTURE = false;
	
	public static final Codec<EquipmentLayer> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					SpongeCodecs.RESOURCE_KEY.fieldOf("texture").forGetter(EquipmentLayer::texture),
					EquipmentDye.CODEC.optionalFieldOf("dyeable").forGetter(EquipmentLayer::dyeable),
					Codec.BOOL.optionalFieldOf("use_player_texture", EquipmentLayer.DEFAULT_USE_PLAYER_TEXTURE).forGetter(EquipmentLayer::usePlayerTexture))
			.apply(instance, EquipmentLayer::new));
	
	public static final Codec<List<EquipmentLayer>> LIST_CODEC = ExtraCodecs.nonEmptyCollection(EquipmentLayer.CODEC.listOf());
	
	public EquipmentLayer(
		final ResourceKey texture, final Optional<EquipmentDye> dyeable, final boolean usePlayerTexture
	) {
		this.texture = Objects.requireNonNull(texture, "texture");
		this.dyeable = Objects.requireNonNull(dyeable, "dyeable");
		this.usePlayerTexture = usePlayerTexture;
	}
	
	/**
	 * Returns the new {@link Builder}.
	 * 
	 * @return The new builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * Builder for {@link EquipmentLayer}.
	 */
	public static final class Builder implements org.spongepowered.api.util.Builder<EquipmentLayer, Builder> {
		
		private @Nullable ResourceKey texture;
		private @Nullable EquipmentDye dyeable;
		private boolean usePlayerTexture;
		
		public Builder() {
			this.reset();
		}
		
		/**
		 * Sets the texture key.
		 * 
		 * @param texture The texture key
		 * @return This builder, for chaining
		 */
		public Builder texture(final ResourceKey texture) {
			this.texture = Objects.requireNonNull(texture, "texture");
			return this;
		}
		
		/**
		 * Sets the dyeable data.
		 * 
		 * @param dyeable The equipment dye
		 * @return This builder, for chaining
		 */
		public Builder dyeable(final @Nullable EquipmentDye dyeable) {
			this.dyeable = dyeable;
			return this;
		}
		
		/**
		 * Sets if the layer texture should be overriden by a texture given by the player.
		 * 
		 * @param usePlayerTexture The boolean flag
		 * @return This builder, for chaining
		 */
		public Builder usePlayerTexture(final boolean usePlayerTexture) {
			this.usePlayerTexture = usePlayerTexture;
			return this;
		}
		
		/**
		 * Sets the {@link #usePlayerTexture()} to true.
		 * 
		 * @return This builder, for chaining
		 */
		public Builder usePlayerTexture() {
			return this.usePlayerTexture(true);
		}
		
		@Override
		public Builder reset() {
			this.texture = null;
			this.dyeable = null;
			this.usePlayerTexture = EquipmentLayer.DEFAULT_USE_PLAYER_TEXTURE;
			return this;
		}
		
		@Override
		public EquipmentLayer build() {
			if (this.texture == null) {
				throw new IllegalStateException("texture must be set");
			}
			return new EquipmentLayer(this.texture, Optional.ofNullable(this.dyeable), this.usePlayerTexture);
		}
	}
}
