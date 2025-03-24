package net.hellheim.spongetools.custom.item.model;

import java.util.function.Function;

import org.spongepowered.api.ResourceKey;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

public interface TintSource {
	
	final LateBoundIdMapper<ResourceKey, MapCodec<? extends TintSource>> ID_MAPPER = new LateBoundIdMapper<>();
	
	final Codec<TintSource> CODEC = TintSource.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatch(TintSource::codec, Function.identity());
	
	static Constant constant(final int value) {
		return new Constant(value);
	}
	
	static CustomModelData custom(final int defaultColor) {
		return TintSource.custom(CustomModelData.DEFAULT_INDEX, defaultColor);
	}
	
	static CustomModelData custom(final int index, final int defaultColor) {
		return new CustomModelData(index, defaultColor);
	}
	
	static Grass grass() {
		return grass(0.5F, 1.0F);
	}
	
	static Grass grass(final float temperature, final float downfall) {
		return new Grass(temperature, downfall);
	}
	
	static Dye dye(final int defaultColor) {
		return new Dye(defaultColor);
	}
	
	static Firework firework() {
		return firework(-7697782);
	}
	
	static Firework firework(final int defaultColor) {
		return new Firework(defaultColor);
	}
	
	static Map map() {
		return map(4603950);
	}
	
	static Map map(final int defaultColor) {
		return new Map(defaultColor);
	}
	
	static Potion potion() {
		return potion(-13083194);
	}
	
	static Potion potion(final int defaultColor) {
		return new Potion(defaultColor);
	}
	
	static Team team(final int defaultColor) {
		return new Team(defaultColor);
	}
	
	MapCodec<? extends TintSource> codec();
	
	record Constant(int value) implements TintSource {
		public static final MapCodec<Constant> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("value").forGetter(Constant::value)
						).apply(instance, Constant::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record CustomModelData(int index, int defaultColor) implements TintSource {
		public static final int DEFAULT_INDEX = 0;
		public static final MapCodec<CustomModelData> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("index", CustomModelData.DEFAULT_INDEX).forGetter(CustomModelData::index),
						ExtraCodecs.RGB.fieldOf("default").forGetter(CustomModelData::defaultColor)
						).apply(instance, CustomModelData::new));
		
		public CustomModelData(final int index, final int defaultColor) {
			Preconditions.checkArgument(index >= 0, "Index must not be negative: " + index);
			this.index = index;
			this.defaultColor = defaultColor;
		}
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Grass(float temperature, float downfall) implements TintSource {
		public static final MapCodec<Grass> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("temperature").forGetter(Grass::temperature),
						ExtraCodecs.floatRange(0.0F, 1.0F).fieldOf("downfall").forGetter(Grass::downfall)
						).apply(instance, Grass::new));
		
		public Grass(final float temperature, final float downfall) {
			Preconditions.checkArgument(0 <= temperature && temperature <= 1, "Temperature must be between 0 and 1: " + temperature);
			Preconditions.checkArgument(0 <= downfall && downfall <= 1, "Downfall must be between 0 and 1: " + downfall);
			this.temperature = temperature;
			this.downfall = downfall;
		}
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Dye(int defaultColor) implements TintSource {
		public static final MapCodec<Dye> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("default").forGetter(Dye::defaultColor)
						).apply(instance, Dye::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Firework(int defaultColor) implements TintSource {
		public static final MapCodec<Firework> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("default").forGetter(Firework::defaultColor)
						).apply(instance, Firework::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Map(int defaultColor) implements TintSource {
		public static final MapCodec<Map> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("default").forGetter(Map::defaultColor)
						).apply(instance, Map::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Potion(int defaultColor) implements TintSource {
		public static final MapCodec<Potion> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("default").forGetter(Potion::defaultColor)
						).apply(instance, Potion::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
	
	record Team(int defaultColor) implements TintSource {
		public static final MapCodec<Team> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.RGB.fieldOf("default").forGetter(Team::defaultColor)
						).apply(instance, Team::new));
		
		@Override
		public MapCodec<? extends TintSource> codec() {
			return CODEC;
		}
	}
}
