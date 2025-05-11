package net.hellheim.spongetools.resourcepack.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.entity.display.ItemDisplayType;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.Transform;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.MathCodecs;
import net.hellheim.spongetools.codec.list.StringRepresentableCodecs;

public record ItemTransforms(Map<ItemDisplayType, Transform> transforms) {
	
	public static final ItemTransforms DEFAULT = new ItemTransforms(Map.of());
	
	public static final Codec<ItemTransforms> CODEC =
			Codec.unboundedMap(StringRepresentableCodecs.ITEM_DISPLAY_TYPE, MathCodecs.TRANSFORM)
			.xmap(ItemTransforms::new, ItemTransforms::transforms);
	
	public ItemTransforms(final Map<ItemDisplayType, Transform> transforms) {
		this.transforms = Map.copyOf(transforms);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements
			org.spongepowered.api.util.Builder<ItemTransforms, Builder>,
			CopyableBuilder<ItemTransforms, Builder> {
		
		private final Map<ItemDisplayType, Transform> transforms = new HashMap<>();
		
		public Builder put(final ItemDisplayType display, final Transform transform) {
			this.transforms.put(
					Objects.requireNonNull(display, "display"),
					Objects.requireNonNull(transform, "transform"));
			return this;
		}
		
		public Builder put(final Supplier<? extends ItemDisplayType> display, final Transform transform) {
			return this.put(display.get(), transform);
		}
		
		public Builder putIfAbsent(final ItemDisplayType display, final Transform transform) {
			this.transforms.putIfAbsent(
					Objects.requireNonNull(display, "display"),
					Objects.requireNonNull(transform, "transform"));
			return this;
		}
		
		public Builder putIfAbsent(final Supplier<? extends ItemDisplayType> display, final Transform transform) {
			return this.putIfAbsent(display.get(), transform);
		}
		
		public Builder putAll(final Map<? extends ItemDisplayType, ? extends Transform> transforms) {
			Objects.requireNonNull(transforms, "transforms").forEach(this::put);
			return this;
		}
		
		public Builder putAll(final Transform transform, final ItemDisplayType... displays) {
			for (final ItemDisplayType display : Objects.requireNonNull(displays, "displays")) {
				this.put(display, transform);
			}
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder putAll(final Transform transform, final Supplier<? extends ItemDisplayType>... displays) {
			for (final Supplier<? extends ItemDisplayType> display : Objects.requireNonNull(displays, "displays")) {
				this.put(display, transform);
			}
			return this;
		}
		
		public Builder putAll(final Transform transform, final Iterable<? extends ItemDisplayType> displays) {
			for (final ItemDisplayType display : Objects.requireNonNull(displays, "displays")) {
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
