package net.hellheim.spongetools.custom.item.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.Transform;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.MathCodecs;

public record ItemTransforms(Map<ItemDisplayContext, Transform> transforms) {
	
	public static final Codec<ItemTransforms> CODEC =
			Codec.unboundedMap(ItemDisplayContext.CODEC, MathCodecs.TRANSFORM)
			.xmap(ItemTransforms::new, ItemTransforms::transforms);
	
	public ItemTransforms(final Map<ItemDisplayContext, Transform> transforms) {
		this.transforms = Map.copyOf(transforms);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<ItemTransforms, Builder>,
			CopyableBuilder<ItemTransforms, Builder> {
		
		private final Map<ItemDisplayContext, Transform> transforms = new HashMap<>();
		
		public Builder put(final ItemDisplayContext display, final Transform transform) {
			this.transforms.put(
					Objects.requireNonNull(display, "display"),
					Objects.requireNonNull(transform, "transform"));
			return this;
		}
		
		public Builder putIfAbsent(final ItemDisplayContext display, final Transform transform) {
			this.transforms.putIfAbsent(
					Objects.requireNonNull(display, "display"),
					Objects.requireNonNull(transform, "transform"));
			return this;
		}
		
		public Builder putAll(final Map<? extends ItemDisplayContext, ? extends Transform> transforms) {
			Objects.requireNonNull(transforms, "transforms").forEach(this::put);
			return this;
		}
		
		public Builder putAll(final Transform transform, final ItemDisplayContext... displays) {
			for (final ItemDisplayContext display : Objects.requireNonNull(displays, "displays")) {
				this.put(display, transform);
			}
			return this;
		}
		
		public Builder putAll(final Transform transform, final Iterable<? extends ItemDisplayContext> displays) {
			for (final ItemDisplayContext display : Objects.requireNonNull(displays, "displays")) {
				this.put(display, transform);
			}
			return this;
		}
		
		@Override
		public Builder from(final ItemTransforms value) {
			this.reset();
			this.transforms.putAll(value.transforms);
			return this;
		}
		
		@Override
		public Builder reset() {
			this.transforms.clear();
			return this;
		}
		
		@Override
		public ItemTransforms build() {
			return new ItemTransforms(this.transforms);
		}
	}
}
