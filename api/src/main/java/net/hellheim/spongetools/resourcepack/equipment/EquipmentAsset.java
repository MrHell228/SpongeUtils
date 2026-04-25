package net.hellheim.spongetools.resourcepack.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.spongepowered.api.registry.DefaultedRegistryType;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;

/**
 * @see <a href=https://minecraft.wiki/w/Equipment> Minecraft Wiki </a>
 */
public record EquipmentAsset(Map<EquipmentLayerType, List<EquipmentLayer>> layers) {
	
	private static final Codec<Map<EquipmentLayerType, List<EquipmentLayer>>> MAP_CODEC =
			ExtraCodecs.nonEmptyMap(Codec.unboundedMap(EquipmentLayerType.CODEC, EquipmentLayer.LIST_CODEC));
	
	public static final Codec<EquipmentAsset> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					EquipmentAsset.MAP_CODEC.fieldOf("layers").forGetter(EquipmentAsset::layers))
			.apply(instance, EquipmentAsset::new));
	
	public EquipmentAsset(final Map<EquipmentLayerType, List<EquipmentLayer>> layers) {
		final var builder = ImmutableMap.<EquipmentLayerType, List<EquipmentLayer>>builder();
		Objects.requireNonNull(layers, "layers").forEach((type, list) -> builder.put(type, List.copyOf(list)));
		this.layers = builder.build();
	}
	
	public static DefaultedRegistryType<EquipmentAsset> registry() {
		return SpongeTools.Registries.EQUIPMENT_ASSET;
	}
	
	public static Codec<EquipmentAsset> registryCodec() {
		return RegistryCodecs.EQUIPMENT_ASSET;
	}
	
	/**
	 * The new {@link Builder}.
	 * 
	 * @return The new builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * Builder for {@link EquipmentAsset}.
	 */
	public static final class Builder implements org.spongepowered.api.util.Builder<EquipmentAsset, Builder> {
		
		private final Map<EquipmentLayerType, List<EquipmentLayer>> layers = new HashMap<>();
		
		public Builder() {
			this.reset();
		}
		
		/**
		 * Adds the given layer for all of the given layer types.
		 * 
		 * @param layer The equipment layer
		 * @param types The equipment layer types
		 * @return This builder, for chaining
		 */
		public Builder add(final EquipmentLayer layer, final EquipmentLayerType... types) {
			for (final EquipmentLayerType type : Objects.requireNonNull(types, "types")) {
				this.add(type, layer);
			}
			return this;
		}
		
		/**
		 * Adds the given layer for all of the given layer types.
		 * 
		 * @param layer The equipment layer
		 * @param types The equipment layer types
		 * @return This builder, for chaining
		 */
		public Builder add(final EquipmentLayer layer, final Iterable<? extends EquipmentLayerType> types) {
			for (final EquipmentLayerType type : Objects.requireNonNull(types, "types")) {
				this.add(type, layer);
			}
			return this;
		}
		
		/**
		 * Adds the given layers for the given layer type.
		 * 
		 * @param type The equipment layer type
		 * @param layers The equipment layers
		 * @return This builder, for chaining
		 */
		public Builder add(final EquipmentLayerType type, final EquipmentLayer... layers) {
			final var list = this.layers.computeIfAbsent(Objects.requireNonNull(type, "type"), $ -> new ArrayList<>());
			for (final EquipmentLayer layer : Objects.requireNonNull(layers, "layers")) {
				list.add(Objects.requireNonNull(layer, "layer"));
			}
			return this;
		}
		
		/**
		 * Adds the given layers for the given layer type.
		 * 
		 * @param type The equipment layer type
		 * @param layers The equipment layers
		 * @return This builder, for chaining
		 */
		public Builder add(final EquipmentLayerType type, final Iterable<? extends EquipmentLayer> layers) {
			final var list = this.layers.computeIfAbsent(Objects.requireNonNull(type, "type"), $ -> new ArrayList<>());
			for (final EquipmentLayer layer : Objects.requireNonNull(layers, "layers")) {
				list.add(Objects.requireNonNull(layer, "layer"));
			}
			return this;
		}
		
		/**
		 * Replaces the given layers for the given layer type.
		 * 
		 * @param type The equipment layer type
		 * @param layers The equipment layers
		 * @return This builder, for chaining
		 */
		public Builder put(final EquipmentLayerType type, final EquipmentLayer... layers) {
			final var list = this.layers.computeIfAbsent(Objects.requireNonNull(type, "type"), $ -> new ArrayList<>());
			list.clear();
			for (final EquipmentLayer layer : Objects.requireNonNull(layers, "layers")) {
				list.add(Objects.requireNonNull(layer, "layer"));
			}
			return this;
		}
		
		/**
		 * Replaces the given layers for the given layer type.
		 * 
		 * @param type The equipment layer type
		 * @param layers The equipment layers
		 * @return This builder, for chaining
		 */
		public Builder put(final EquipmentLayerType type, final Iterable<? extends EquipmentLayer> layers) {
			final var list = this.layers.computeIfAbsent(Objects.requireNonNull(type, "type"), $ -> new ArrayList<>());
			list.clear();
			for (final EquipmentLayer layer : Objects.requireNonNull(layers, "layers")) {
				list.add(Objects.requireNonNull(layer, "layer"));
			}
			return this;
		}
		
		/**
		 * Removes the given layer type from the asset.
		 * 
		 * @param type The equipment layer type
		 * @return This builder, for chaining
		 */
		public Builder remove(final EquipmentLayerType type) {
			this.layers.remove(Objects.requireNonNull(type, "type"));
			return this;
		}
		
		@Override
		public Builder reset() {
			this.layers.clear();
			return this;
		}
		
		@Override
		public EquipmentAsset build() {
			return new EquipmentAsset(this.layers);
		}
	}
}
