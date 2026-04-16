package net.hellheim.spongetools.resourcepack.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.resource.pack.PackType;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.resourcepack.util.InclusiveRange;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public interface MetadataSection extends MetadataSectionLike {
	
	BiMap<String, Codec<? extends MetadataSection>> ID_MAPPER_CLIENT = HashBiMap.create();
	BiMap<String, Codec<? extends MetadataSection>> ID_MAPPER_SERVER = HashBiMap.create();
	
	Codec<Map<String, MetadataSection>> MAP_CODEC_CLIENT = MetadataSection.mapCodec(MetadataSection.ID_MAPPER_CLIENT);
	Codec<Map<String, MetadataSection>> MAP_CODEC_SERVER = MetadataSection.mapCodec(MetadataSection.ID_MAPPER_SERVER);
	
	Codec<List<MetadataSection>> LIST_CODEC_CLIENT = MetadataSection.codec(MetadataSection.ID_MAPPER_CLIENT, MetadataSection.MAP_CODEC_CLIENT);
	Codec<List<MetadataSection>> LIST_CODEC_SERVER = MetadataSection.codec(MetadataSection.ID_MAPPER_SERVER, MetadataSection.MAP_CODEC_SERVER);
	
	static Pack pack(final PackType type, final Component description, final PackFormat minSupportedFormat) {
		return MetadataSection.pack(type, description, minSupportedFormat, minSupportedFormat.minorRange());
	}
	
	static Pack pack(final PackType type, final Component description, final PackFormat minSupportedFormat, final PackFormat maxSupportedFormat) {
		return MetadataSection.pack(type, description, InclusiveRange.of(maxSupportedFormat));
	}
	
	static Pack pack(final PackType type, final Component description, final InclusiveRange<PackFormat> supportedFormats) {
		final PackFormat min = supportedFormats.minInclusive(), max = supportedFormats.maxInclusive();
		if (min.compareTo(max) > 0) {
			throw new IllegalArgumentException(String.format(
					"min format (%s) must be less than or equal to max format (%s)", min, max));
		}
		return new Pack(type, description, supportedFormats);
	}
	
	static Overlays overlays(final PackType type, final Collection<? extends OverlayEntry> entries) {
		return new Overlays(type, List.copyOf(entries));
	}
	
	static Overlays overlays(final PackType type, final OverlayEntry... entries) {
		return new Overlays(type, List.of(entries));
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
	
	static Filter filter(final Collection<? extends KeyPattern> patterns) {
		return new Filter(List.copyOf(patterns));
	}
	
	static Filter filter(final KeyPattern... patterns) {
		return new Filter(List.of(patterns));
	}
	
	private static Codec<Map<String, MetadataSection>> mapCodec(
		final BiMap<String, Codec<? extends MetadataSection>> idMapper
	) {
		return Codec.dispatchedMap(Codec.STRING, idMapper::get);
	}
	
	private static Codec<List<MetadataSection>> codec(
		final BiMap<String, Codec<? extends MetadataSection>> idMapper,
		final Codec<Map<String, MetadataSection>> mapCodec
	) {
		return mapCodec.xmap(
				map -> List.<MetadataSection>copyOf(map.values()),
				list -> list.stream().collect(Collectors.toUnmodifiableMap(
						section -> idMapper.inverse().get(section.codec()),
						Function.identity()
						))
				);
	}
	
	@Override
	default MetadataSection asSection() {
		return this;
	}
	
	Codec<? extends MetadataSection> codec();
	
	record Pack(PackType type, Component description, InclusiveRange<PackFormat> supportedFormats) implements MetadataSection {
		public static final Codec<Pack> CODEC_CLIENT = Pack.codec(PackType::client);
		public static final Codec<Pack> CODEC_SERVER = Pack.codec(PackType::server);
		
		public Pack(final PackType type, final Component description, final InclusiveRange<PackFormat> supportedFormats) {
			this.type = Objects.requireNonNull(type, "type");
			this.description = Objects.requireNonNull(description, "description");
			this.supportedFormats = Objects.requireNonNull(supportedFormats, "supportedFormats");
		}

		private static Codec<Pack> codec(final Supplier<PackType> type) {
			return Codec.lazyInitialized(() -> RecordCodecBuilder.create(
					instance -> instance.group(
							AdventureCodecs.COMPONENT.fieldOf("description").forGetter(Pack::description),
							PackFormat.packCodec(type.get()).forGetter(Pack::supportedFormats))
							.apply(instance, (description, supportedFormats) -> new Pack(type.get(), description, supportedFormats))));
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return this.type() == PackType.client() ? Pack.CODEC_CLIENT : Pack.CODEC_SERVER;
		}
	}
	
	record Overlays(PackType type, List<OverlayEntry> overlays) implements MetadataSection {
		
		public static final Codec<Overlays> CODEC_CLIENT = Overlays.codec(PackType::client);
		public static final Codec<Overlays> CODEC_SERVER = Overlays.codec(PackType::server);
		
		public Overlays(final PackType type, final List<OverlayEntry> overlays) {
			this.type = Objects.requireNonNull(type, "type");
			this.overlays = List.copyOf(overlays);
		}
		
		private static Codec<Overlays> codec(final Supplier<PackType> type) {
			return Codec.lazyInitialized(() -> RecordCodecBuilder.create(
					instance -> instance.group(
							OverlayEntry.codec(type.get()).fieldOf("entries").forGetter(Overlays::overlays))
					.apply(instance, overlays -> new Overlays(type.get(), overlays))));
		}
		
		public List<String> overlaysForVersion(final PackFormat version) {
			return this.overlays.stream()
					.filter((entry) -> entry.isApplicable(version))
					.map(OverlayEntry::overlay)
					.toList();
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return this.type() == PackType.client() ? Overlays.CODEC_CLIENT : Overlays.CODEC_SERVER;
		}
	}
	
	record Villager(VillagerHat hat) implements MetadataSection, MetadataSectionLike.ClientSection {
		
		public static final Codec<Villager> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						VillagerHat.CODEC.optionalFieldOf("hat", VillagerHat.NONE).forGetter(Villager::hat)
						).apply(instance, Villager::new));
		
		public Villager(final VillagerHat hat) {
			this.hat = Objects.requireNonNull(hat, "hat");
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return Villager.CODEC;
		}
	}
	
	record Gui(GuiScaling scaling) implements MetadataSection, MetadataSectionLike.ClientSection {
		
		public static final Codec<Gui> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						GuiScaling.CODEC.optionalFieldOf("scaling", GuiScaling.DEFAULT).forGetter(Gui::scaling)
						).apply(instance, Gui::new)
		);
		
		public Gui(final GuiScaling scaling) {
			this.scaling = Objects.requireNonNull(scaling, "scaling");
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return Gui.CODEC;
		}
	}
	
	record Texture(boolean blur, boolean clamp) implements MetadataSection, MetadataSectionLike.ClientSection {
	    
		public static final boolean DEFAULT_BLUR = false;
		public static final boolean DEFAULT_CLAMP = false;
		public static final Codec<Texture> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("blur", Texture.DEFAULT_BLUR).forGetter(Texture::blur),
						Codec.BOOL.optionalFieldOf("clamp", Texture.DEFAULT_CLAMP).forGetter(Texture::clamp)
						).apply(instance, Texture::new));
		
		public Texture(final boolean blur, final boolean clamp) {
			this.blur = blur;
			this.clamp = clamp;
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return Texture.CODEC;
		}
	}
	
	record Animation(
		Optional<Integer> width, Optional<Integer> height,
		int frameTime, boolean interpolate,
		List<AnimationFrame> frames
	) implements MetadataSection, MetadataSectionLike.ClientSection {
		
		public static final int DEFAULT_FRAME_TIME = 1;
		public static final boolean DEFAULT_INTERPOLATE = false;
		public static final Codec<Animation> CODEC = RecordCodecBuilder.create(
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
		public Codec<? extends MetadataSection> codec() {
			return Animation.CODEC;
		}
		
		public static final class Builder implements
				org.spongepowered.api.util.Builder<Animation, Builder>,
				MetadataSectionLike.ClientSection {
			
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
			
			@Override
			public MetadataSection asSection() {
				return this.build();
			}
		}
	}
	
	record Filter(List<KeyPattern> patterns) implements MetadataSection, MetadataSectionLike.ServerSection {
		
		public static final Codec<Filter> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
						Codec.list(KeyPattern.CODEC).fieldOf("block").forGetter(Filter::patterns))
				.apply(instance, Filter::new));
		
		public Filter(final List<KeyPattern> patterns) {
			this.patterns = List.copyOf(patterns);
		}
		
		public boolean test(final RegistryKey<?> key) {
			return this.test(key.location());
		}
		
		public boolean test(final Key key) {
			return this.patterns.stream().anyMatch(pattern -> pattern.test(key));
		}
		
		@Override
		public Codec<? extends MetadataSection> codec() {
			return Filter.CODEC;
		}
	}
}
