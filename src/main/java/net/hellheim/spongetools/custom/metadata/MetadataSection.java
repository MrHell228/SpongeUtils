package net.hellheim.spongetools.custom.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;
import net.kyori.adventure.text.Component;

public interface MetadataSection extends MapCodecProxy<MetadataSection>, MetadataSectionLike {
	
	BiMap<String, Codec<? extends MetadataSection>> ID_MAPPER = HashBiMap.create();
	
	Codec<Map<String, MetadataSection>> MAP_CODEC = Codec.dispatchedMap(Codec.STRING, ID_MAPPER::get);
	
	Codec<List<MetadataSection>> LIST_CODEC = MAP_CODEC.xmap(
			map -> List.<MetadataSection>copyOf(map.values()),
			list -> list.stream().collect(Collectors.toUnmodifiableMap(
					section -> ID_MAPPER.inverse().get(section.mapCodec().codec()),
					Function.identity()
					))
			);
	
	static Pack pack(final Component description, final int format) {
		return MetadataSection.pack(description, format, Optional.empty());
	}
	
	static Pack pack(final Component description, final int format, final Pair<Integer, Integer> supportedFormats) {
		return MetadataSection.pack(description, format, Optional.of(supportedFormats));
	}
	
	static Pack pack(final Component description, final int format, final Optional<Pair<Integer, Integer>> supportedFormats) {
		supportedFormats.ifPresent(range -> {
			final int min = range.getFirst(), max = range.getSecond();
			if (min > max) {
				throw new IllegalArgumentException(String.format(
						"min_inclusive (%s) must be less than or equal to max_inclusive (%s)", min, max));
			}
		});
		return new Pack(description, format, supportedFormats);
	}
	
	static Villager villager(final VillagerHat hat) {
		return new Villager(hat);
	}
	
	static Gui gui(final GuiScaling scaling) {
		return new Gui(scaling);
	}
	
	static Texture textureBlur(final boolean blur) {
		return MetadataSection.texture(blur, Texture.DEFAULT_CLAMP);
	}
	
	static Texture textureClamp(final boolean clamp) {
		return MetadataSection.texture(Texture.DEFAULT_BLUR, clamp);
	}
	
	static Texture texture(final boolean blur, final boolean clamp) {
		return new Texture(blur, clamp);
	}
	
	static Animation.Builder animation() {
		return new Animation.Builder();
	}
	
	@Override
	default MetadataSection asSection() {
		return this;
	}
	
	record Pack(Component description, int format, Optional<Pair<Integer, Integer>> supportedFormats) implements MetadataSection {
		public static final Codec<Pair<Integer, Integer>> RANGE_CODEC = ExtraCodecs.interval(Codec.INT,
				"min_inclusive", "max_inclusive",
				(min, max) -> {
					return min <= max
							? DataResult.success(Pair.of(min, max))
							: DataResult.error(() -> "min_inclusive must be less than or equal to max_inclusive");
				},
				Pair::getFirst, Pair::getSecond);
		public static final MapCodec<Pack> CODEC = RecordCodecBuilder.mapCodec(
				p_337567_ -> p_337567_.group(
						AdventureCodecs.COMPONENT.fieldOf("description").forGetter(Pack::description),
						Codec.INT.fieldOf("pack_format").forGetter(Pack::format),
						Pack.RANGE_CODEC.optionalFieldOf("supported_formats").forGetter(Pack::supportedFormats)
						).apply(p_337567_, Pack::new));
		
		public Pack(final Component description, final int format, final Optional<Pair<Integer, Integer>> supportedFormats) {
			this.description = Objects.requireNonNull(description, "description");
			this.format = format;
			this.supportedFormats = Objects.requireNonNull(supportedFormats, "supportedFormats");
		}
		
		@Override
		public MapCodec<? extends MetadataSection> mapCodec() {
			return CODEC;
		}
	}
	
	record Villager(VillagerHat hat) implements MetadataSection {
		
		public static final MapCodec<Villager> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						VillagerHat.CODEC.optionalFieldOf("hat", VillagerHat.NONE).forGetter(Villager::hat)
						).apply(instance, Villager::new));
		
		public Villager(final VillagerHat hat) {
			this.hat = Objects.requireNonNull(hat, "hat");
		}
		
		@Override
		public MapCodec<? extends MetadataSection> mapCodec() {
			return CODEC;
		}
	}
	
	record Gui(GuiScaling scaling) implements MetadataSection {
		
		public static final MapCodec<Gui> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						GuiScaling.CODEC.optionalFieldOf("scaling", GuiScaling.DEFAULT).forGetter(Gui::scaling)
						).apply(instance, Gui::new)
		);
		
		public Gui(final GuiScaling scaling) {
			this.scaling = Objects.requireNonNull(scaling, "scaling");
		}
		
		@Override
		public MapCodec<? extends MetadataSection> mapCodec() {
			return CODEC;
		}
	}
	
	record Texture(boolean blur, boolean clamp) implements MetadataSection {
	    
		public static final boolean DEFAULT_BLUR = false;
		public static final boolean DEFAULT_CLAMP = false;
		public static final MapCodec<Texture> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("blur", Texture.DEFAULT_BLUR).forGetter(Texture::blur),
						Codec.BOOL.optionalFieldOf("clamp", Texture.DEFAULT_CLAMP).forGetter(Texture::clamp)
						).apply(instance, Texture::new));
		
		public Texture(final boolean blur, final boolean clamp) {
			this.blur = blur;
			this.clamp = clamp;
		}
		
		@Override
		public MapCodec<? extends MetadataSection> mapCodec() {
			return CODEC;
		}
	}
	
	record Animation(
		Optional<Integer> width, Optional<Integer> height,
		int frameTime, boolean interpolate,
		List<AnimationFrame> frames
	) implements MetadataSection {
		
		public static final int DEFAULT_FRAME_TIME = 1;
		public static final boolean DEFAULT_INTERPOLATE = false;
		public static final MapCodec<Animation> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.POSITIVE_INT.optionalFieldOf("width").forGetter(Animation::width),
						ExtraCodecs.POSITIVE_INT.optionalFieldOf("height").forGetter(Animation::height),
						ExtraCodecs.POSITIVE_INT.optionalFieldOf("frametime", Animation.DEFAULT_FRAME_TIME).forGetter(Animation::frameTime),
						Codec.BOOL.optionalFieldOf("interpolate", Animation.DEFAULT_INTERPOLATE).forGetter(Animation::interpolate),
						AnimationFrame.CODEC.listOf().optionalFieldOf("frames", List.of()).forGetter(Animation::frames)
						).apply(instance, Animation::new));
		
		public Animation(
			final Optional<Integer> width, final Optional<Integer> height,
			final int frameTime, final boolean interpolate,
			final List<AnimationFrame> frames
		) {
			Objects.requireNonNull(width, "width").ifPresent(Animation::checkWidth);
			Objects.requireNonNull(height, "height").ifPresent(Animation::checkHeight);
			Animation.checkFrameTime(frameTime);
			this.width = width;
			this.height = height;
			this.frameTime = frameTime;
			this.interpolate = interpolate;
			this.frames = List.copyOf(frames);
		}
		
		private static void checkWidth(final @Nullable Integer width) {
			if (width != null) {
				Preconditions.checkArgument(width > 0, "width must be positive: " + width);
			}
		}
		
		private static void checkHeight(final @Nullable Integer height) {
			if (height != null) {
				Preconditions.checkArgument(height > 0, "height must be positive: " + height);
			}
		}
		
		private static void checkFrameTime(final int frameTime) {
			Preconditions.checkArgument(frameTime > 0, "frameTime must be positive: " + frameTime);
		}
		
		@Override
		public MapCodec<? extends MetadataSection> mapCodec() {
			return CODEC;
		}
		
		public static final class Builder implements org.spongepowered.api.util.Builder<Animation, Builder> {
			
			private @Nullable Integer width;
			private @Nullable Integer height;
			private int frameTime;
			private boolean interpolate;
			private final List<AnimationFrame> frames = new ArrayList<>();
			
			public Builder() {
				this.reset();
			}
			
			public Builder addAll(final AnimationFrame... frames) {
				for (AnimationFrame frame : Objects.requireNonNull(frames, "frames")) {
					this.add(frame);
				}
				return this;
			}
			
			public Builder addAll(final Iterable<AnimationFrame> frames) {
				for (AnimationFrame frame : Objects.requireNonNull(frames, "frames")) {
					this.add(frame);
				}
				return this;
			}
			
			public Builder add(final AnimationFrame frame) {
				this.frames.add(Objects.requireNonNull(frame, "frame"));
				return this;
			}
			
			public Builder width(final @Nullable Integer width) {
				Animation.checkWidth(width);
				this.width = width;
				return this;
			}
			
			public Builder height(final @Nullable Integer height) {
				Animation.checkHeight(height);
				this.height = height;
				return this;
			}
			
			public Builder frameTime(final int frameTime) {
				Animation.checkFrameTime(frameTime);
				this.frameTime = frameTime;
				return this;
			}
			
			public Builder interpolate(final boolean interpolate) {
				this.interpolate = interpolate;
				return this;
			}
			
			@Override
			public Builder reset() {
				this.width = null;
				this.height = null;
				this.frameTime = Animation.DEFAULT_FRAME_TIME;
				this.interpolate = Animation.DEFAULT_INTERPOLATE;
				this.frames.clear();
				return this;
			}
			
			@Override
			public Animation build() {
				return new Animation(
						Optional.ofNullable(this.width), Optional.ofNullable(this.height),
						this.frameTime, this.interpolate, this.frames);
			}
		}
	}
}
