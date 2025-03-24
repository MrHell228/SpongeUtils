package net.hellheim.spongetools.custom.item.model;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.util.Direction;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.codec.list.StringRepresentableCodecs;
import net.hellheim.spongetools.custom.item.model.enums.SkullType;

public interface SpecialModel {
	
	final LateBoundIdMapper<ResourceKey, MapCodec<? extends SpecialModel>> ID_MAPPER = new LateBoundIdMapper<>();
	
	final Codec<SpecialModel> CODEC = SpecialModel.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatch(SpecialModel::codec, Function.identity());
	
	static Conduit conduit() {
		return Conduit.INSTANCE;
	}
	
	static DecoratedPot decoratedPot() {
		return DecoratedPot.INSTANCE;
	}
	
	static Trident triden() {
		 return Trident.INSTANCE;
	}
	
	static Shield shield() {
		return Shield.INSTANCE;
	}
	
	static Banner banner(final DyeColor base) {
		return new Banner(base);
	}
	
	static Bed bed(final ResourceKey texture) {
		return new Bed(texture);
	}
	
	static Chest chest(final ResourceKey texture) {
		return SpecialModel.chest(texture, Chest.DEFAULT_OPENNESS);
	}
	
	static Chest chest(final ResourceKey texture, final float openness) {
		return new Chest(texture, openness);
	}
	
	static ShulkerBox shulkerBox(final ResourceKey texture) {
		return SpecialModel.shulkerBox(texture, ShulkerBox.DEFAULT_OPENNESS, ShulkerBox.DEFAULT_ORIENTATION);
	}
	
	static ShulkerBox shulkerBox(final ResourceKey texture, final Direction orientation) {
		return SpecialModel.shulkerBox(texture, ShulkerBox.DEFAULT_OPENNESS, orientation);
	}
	
	static ShulkerBox shulkerBox(final ResourceKey texture, final float openness) {
		return SpecialModel.shulkerBox(texture, openness, ShulkerBox.DEFAULT_ORIENTATION);
	}
	
	static ShulkerBox shulkerBox(final ResourceKey texture, final float openness, final Direction orientation) {
		return new ShulkerBox(texture, openness, orientation);
	}
	
	static StandingSign standingSign(final String woodType) {
		return SpecialModel.standingSign(woodType, Optional.empty());
	}
	
	static StandingSign standingSign(final String woodType, final ResourceKey texture) {
		return SpecialModel.standingSign(woodType, Optional.of(texture));
	}
	
	static StandingSign standingSign(final String woodType, final Optional<ResourceKey> texture) {
		return new StandingSign(woodType, texture);
	}
	
	static HangingSign hangingSign(final String woodType) {
		return SpecialModel.hangingSign(woodType, Optional.empty());
	}
	
	static HangingSign hangingSign(final String woodType, final ResourceKey texture) {
		return SpecialModel.hangingSign(woodType, Optional.of(texture));
	}
	
	static HangingSign hangingSign(final String woodType, final Optional<ResourceKey> texture) {
		return new HangingSign(woodType, texture);
	}
	
	static Skull skull(final SkullType type) {
		return SpecialModel.skull(type, Skull.DEFAULT_ANIMATION, Optional.empty());
	}
	
	static Skull skull(final SkullType type, final ResourceKey textureOverride) {
		return SpecialModel.skull(type, Skull.DEFAULT_ANIMATION, Optional.of(textureOverride));
	}
	
	static Skull skull(final SkullType type, final Optional<ResourceKey> textureOverride) {
		return SpecialModel.skull(type, Skull.DEFAULT_ANIMATION, textureOverride);
	}
	
	static Skull skull(final SkullType type, final float animation) {
		return SpecialModel.skull(type, animation, Optional.empty());
	}
	
	static Skull skull(final SkullType type, final float animation, final ResourceKey textureOverride) {
		return SpecialModel.skull(type, animation, Optional.of(textureOverride));
	}
	
	static Skull skull(final SkullType type, final float animation, final Optional<ResourceKey> textureOverride) {
		return new Skull(type, animation, textureOverride);
	}
	
	MapCodec<? extends SpecialModel> codec();
	
	record Conduit() implements SpecialModel {
		public static final Conduit INSTANCE = new Conduit();
		public static final MapCodec<Conduit> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record DecoratedPot() implements SpecialModel {
		public static final DecoratedPot INSTANCE = new DecoratedPot();
		public static final MapCodec<DecoratedPot> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Trident() implements SpecialModel {
		public static final Trident INSTANCE = new Trident();
		public static final MapCodec<Trident> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Shield() implements SpecialModel {
		public static final Shield INSTANCE = new Shield();
		public static final MapCodec<Shield> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Banner(DyeColor base) implements SpecialModel {
		public static final MapCodec<Banner> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						StringRepresentableCodecs.DYE_COLOR.fieldOf("color").forGetter(Banner::base)
						).apply(instance, Banner::new));
		
		public Banner(final DyeColor base) {
			this.base = Objects.requireNonNull(base, "base");
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Bed(ResourceKey texture) implements SpecialModel {
		public static final MapCodec<Bed> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SpongeCodecs.RESOURCE_KEY.fieldOf("texture").forGetter(Bed::texture)
						).apply(instance, Bed::new));
		
		public Bed(final ResourceKey texture) {
			this.texture = Objects.requireNonNull(texture, "texture");
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Chest(ResourceKey texture, float openness) implements SpecialModel {
		public static final float DEFAULT_OPENNESS = 0.0F;
		public static final MapCodec<Chest> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SpongeCodecs.RESOURCE_KEY.fieldOf("texture").forGetter(Chest::texture),
						Codec.FLOAT.optionalFieldOf("openness", Chest.DEFAULT_OPENNESS).forGetter(Chest::openness)
						).apply(instance, Chest::new));
		
		public Chest(final ResourceKey texture, final float openness) {
			this.texture = Objects.requireNonNull(texture, "texture");
			this.openness = openness;
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record ShulkerBox(ResourceKey texture, float openness, Direction orientation) implements SpecialModel {
		public static final float DEFAULT_OPENNESS = 0.0F;
		public static final Direction DEFAULT_ORIENTATION = Direction.UP;
		public static final MapCodec<ShulkerBox> CODEC = RecordCodecBuilder.mapCodec(
				p_386593_ -> p_386593_.group(
						SpongeCodecs.RESOURCE_KEY.fieldOf("texture").forGetter(ShulkerBox::texture),
						Codec.FLOAT.optionalFieldOf("openness", ShulkerBox.DEFAULT_OPENNESS).forGetter(ShulkerBox::openness),
						SpongeCodecs.CARDINAL_DIRECTION.optionalFieldOf("orientation", ShulkerBox.DEFAULT_ORIENTATION).forGetter(ShulkerBox::orientation)
						).apply(p_386593_, ShulkerBox::new));
		
		public ShulkerBox(final ResourceKey texture, final float openness, final Direction orientation) {
			this.texture = Objects.requireNonNull(texture, "texture");
			this.openness = openness;
			this.orientation = Objects.requireNonNull(orientation, "orientation");
			Preconditions.checkArgument(orientation.isCardinal(), "Direction must be cardinal: " + orientation);
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record StandingSign(String woodType, Optional<ResourceKey> texture) implements SpecialModel {
		public static final MapCodec<StandingSign> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.STRING.fieldOf("wood_type").forGetter(StandingSign::woodType),
						SpongeCodecs.RESOURCE_KEY.optionalFieldOf("texture").forGetter(StandingSign::texture)
						).apply(instance, StandingSign::new));
		
		public StandingSign(final String woodType, final Optional<ResourceKey> texture) {
			this.woodType = Objects.requireNonNull(woodType, "woodType");
			this.texture = Objects.requireNonNull(texture, "texture");
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record HangingSign(String woodType, Optional<ResourceKey> texture) implements SpecialModel {
		public static final MapCodec<HangingSign> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.STRING.fieldOf("wood_type").forGetter(HangingSign::woodType),
						SpongeCodecs.RESOURCE_KEY.optionalFieldOf("texture").forGetter(HangingSign::texture)
						).apply(instance, HangingSign::new));
		
		public HangingSign(final String woodType, final Optional<ResourceKey> texture) {
			this.woodType = Objects.requireNonNull(woodType, "woodType");
			this.texture = Objects.requireNonNull(texture, "texture");
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
	
	record Skull(SkullType type, float animation, Optional<ResourceKey> textureOverride) implements SpecialModel {
		public static final float DEFAULT_ANIMATION = 0.0F;
		public static final MapCodec<Skull> CODEC = RecordCodecBuilder.mapCodec(
				p_390096_ -> p_390096_.group(
						SkullType.CODEC.fieldOf("kind").forGetter(Skull::type),
						Codec.FLOAT.optionalFieldOf("animation", Skull.DEFAULT_ANIMATION).forGetter(Skull::animation),
						SpongeCodecs.RESOURCE_KEY.optionalFieldOf("texture").forGetter(Skull::textureOverride)
						).apply(p_390096_, Skull::new));
		
		public Skull(final SkullType type, final float animation, final Optional<ResourceKey> textureOverride) {
			this.type = Objects.requireNonNull(type, "type");
			this.animation = animation;
			this.textureOverride = Objects.requireNonNull(textureOverride, "textureOverride");
		}
		
		@Override
		public MapCodec<? extends SpecialModel> codec() {
			return CODEC;
		}
	}
}
