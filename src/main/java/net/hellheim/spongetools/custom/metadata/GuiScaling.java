package net.hellheim.spongetools.custom.metadata;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;

public interface GuiScaling extends MapCodecProxy<GuiScaling>, MetadataSectionLike {
	
	GuiScaling DEFAULT = GuiScaling.stretch();
	
	LateBoundIdMapper<String, MapCodec<? extends GuiScaling>> ID_MAPPER = new LateBoundIdMapper<>();
	
	Codec<GuiScaling> CODEC = GuiScaling.ID_MAPPER.codec(Codec.STRING)
			.dispatch(GuiScaling::mapCodec, Function.identity());
	
	static Stretch stretch() {
		return Stretch.INSTANCE;
	}
	
	static Tile tile(final int width, final int height) {
		return new Tile(width, height);
	}
	
	static NineSlice nineSlice(final int width, final int height, final NineSlice.Border border) {
		return GuiScaling.nineSlice(width, height, border, NineSlice.DEFAULT_STRETCH_INNER);
	}
	
	static NineSlice nineSlice(final int width, final int height, final NineSlice.Border border, final boolean stretchInner) {
		return new NineSlice(width, height, border, stretchInner);
	}
	
	static NineSlice.Border border(final int size) {
		Preconditions.checkArgument(size >= 0, "size must not be negative: " + size);
		return GuiScaling.border(size, size, size, size);
	}
	
	static NineSlice.Border border(final int left, final int top, final int right, final int bottom) {
		return new NineSlice.Border(left, top, right, bottom);
	}
	
	@Override
	default MetadataSection.Gui asSection() {
		return MetadataSection.gui(this);
	}
	
	record Stretch() implements GuiScaling {
		
		public static final Stretch INSTANCE = new Stretch();
		public static final MapCodec<Stretch> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends GuiScaling> mapCodec() {
			return CODEC;
		}
	}
	
	record Tile(int width, int height) implements GuiScaling {
		
		public static final MapCodec<Tile> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance .group(
						ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(Tile::width),
						ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(Tile::height)
						).apply(instance, Tile::new));
		
		public Tile(final int width, final int height) {
			Preconditions.checkArgument(width > 0, "width must be positive: " + width);
			Preconditions.checkArgument(height > 0, "height must be positive: " + height);
			this.width = width;
			this.height = height;
		}
		
		@Override
		public MapCodec<? extends GuiScaling> mapCodec() {
			return CODEC;
		}
	}
	
	record NineSlice(
		int width, int height, Border border, boolean stretchInner
	) implements GuiScaling {
		
		public static final boolean DEFAULT_STRETCH_INNER = false;
		public static final MapCodec<NineSlice> CODEC = RecordCodecBuilder.<NineSlice>mapCodec(
				instance -> instance.group(
						ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(NineSlice::width),
						ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(NineSlice::height),
						Border.CODEC.fieldOf("border").forGetter(NineSlice::border),
						Codec.BOOL.optionalFieldOf("stretch_inner", NineSlice.DEFAULT_STRETCH_INNER).forGetter(NineSlice::stretchInner)
						).apply(instance, NineSlice::new))
				.validate(NineSlice::validate);
		
		private static DataResult<NineSlice> validate(NineSlice nineSlice) {
			Border border = nineSlice.border();
			if (border.left() + border.right() >= nineSlice.width()) {
				return DataResult.error(() -> "Nine-sliced texture has no horizontal center slice: "
						+ border.left() + " + " + border.right()
						+ " >= " + nineSlice.width());
			} else {
				return border.top() + border.bottom() >= nineSlice
						.height()
								? DataResult.error(() -> "Nine-sliced texture has no vertical center slice: "
										+ border.top() + " + " + border.bottom() + " >= " + nineSlice.height())
								: DataResult.success(nineSlice);
			}
		}
		
		public NineSlice(final int width, final int height, final Border border, final boolean stretchInner) {
			Preconditions.checkArgument(width > 0, "width must be positive: " + width);
			Preconditions.checkArgument(height > 0, "height must be positive: " + height);
			this.width = width;
			this.height = height;
			this.border = Objects.requireNonNull(border, "border");
			this.stretchInner = stretchInner;
		}
		
		@Override
		public MapCodec<? extends GuiScaling> mapCodec() {
			return CODEC;
		}
		
		public record Border(int left, int top, int right, int bottom) {
			public static final Codec<Border> INLINE_CODEC =
					ExtraCodecs.POSITIVE_INT.flatComapMap(
							i -> new Border(i, i, i, i),
							border -> {
								OptionalInt optionalint = border.inlined();
								return optionalint.isPresent() ? DataResult.success(optionalint.getAsInt())
										: DataResult.error(() -> "Border has different side sizes");
							});
			
			public static final Codec<Border> FULL_CODEC = RecordCodecBuilder.create(
					instance -> instance.group(
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("left").forGetter(Border::left),
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("top").forGetter(Border::top),
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("right").forGetter(Border::right),
							ExtraCodecs.NON_NEGATIVE_INT.fieldOf("bottom").forGetter(Border::bottom)
							).apply(instance, Border::new));
			
			public static final Codec<Border> CODEC = Codec.either(INLINE_CODEC, FULL_CODEC)
					.xmap(Either::unwrap, border -> border.inlined().isPresent()
							? Either.left(border)
							: Either.right(border));
			
			public Border(final int left, final int top, final int right, final int bottom) {
				Preconditions.checkArgument(left >= 0, "left must not be negative: " + left);
				Preconditions.checkArgument(top >= 0, "top must not be negative: " + top);
				Preconditions.checkArgument(right >= 0, "right must not be negative: " + right);
				Preconditions.checkArgument(bottom >= 0, "bottom must not be negative: " + bottom);
				this.left = left;
				this.top = top;
				this.right = right;
				this.bottom = bottom;
			}
			
			private OptionalInt inlined() {
				return this.left() == this.top() && this.top() == this.right() && this.right() == this.bottom()
						? OptionalInt.of(this.left())
						: OptionalInt.empty();
			}
		}
	}
}
