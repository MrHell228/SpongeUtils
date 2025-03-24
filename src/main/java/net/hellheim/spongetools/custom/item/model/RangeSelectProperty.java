package net.hellheim.spongetools.custom.item.model;

import java.util.Objects;
import java.util.function.Function;

import org.spongepowered.api.ResourceKey;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.custom.item.model.enums.CompassTarget;
import net.hellheim.spongetools.custom.item.model.enums.TimeSource;

public interface RangeSelectProperty {
	
	final LateBoundIdMapper<ResourceKey, MapCodec<? extends RangeSelectProperty>> ID_MAPPER = new LateBoundIdMapper<>();
	
	final MapCodec<RangeSelectProperty> CODEC = RangeSelectProperty.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatchMap("property", RangeSelectProperty::codec, Function.identity());
	
	static Cooldown cooldown() {
		return Cooldown.INSTANCE;
	}
	
	static BundleFullness bundleFullness() {
		return BundleFullness.INSTANCE;
	}
	
	static CrossbowPull crossbowPull() {
		return CrossbowPull.INSTANCE;
	}
	
	static Quantity quantity() {
		return RangeSelectProperty.quantity(Quantity.DEFAULT_NORMALIZE);
	}
	
	static Quantity quantity(final boolean normalize) {
		return new Quantity(normalize);
	}
	
	static Damage damage() {
		return RangeSelectProperty.damage(Damage.DEFAULT_NORMALIZE);
	}
	
	static Damage damage(final boolean normalize) {
		return new Damage(normalize);
	}
	
	static CustomModelData custom() {
		return RangeSelectProperty.custom(CustomModelData.DEFAULT_INDEX);
	}
	
	static CustomModelData custom(final int index) {
		return new CustomModelData(index);
	}
	
	static UseCycle useCycle() {
		return RangeSelectProperty.useCycle(UseCycle.DEFAULT_PERIOD);
	}
	
	static UseCycle useCycle(final float period) {
		return new UseCycle(period);
	}
	
	static UseDuration useDuration() {
		return RangeSelectProperty.useDuration(UseDuration.DEFAULT_REMAINING);
	}
	
	static UseDuration useDuration(final boolean remaining) {
		return new UseDuration(remaining);
	}
	
	static CompassAngle compassAngle(final CompassTarget target) {
		return RangeSelectProperty.compassAngle(CompassAngle.DEFAULT_WOBBLE, target);
	}
	
	static CompassAngle compassAngle(final boolean wobble, final CompassTarget target) {
		return new CompassAngle(wobble, target);
	}
	
	static Time time(final TimeSource source) {
		return RangeSelectProperty.time(Time.DEFAULT_WOBBLE, source);
	}
	
	static Time time(final boolean wobble, final TimeSource source) {
		return new Time(wobble, source);
	}
	
	MapCodec<? extends RangeSelectProperty> codec();
	
	record Cooldown() implements RangeSelectProperty {
		public static final Cooldown INSTANCE = new Cooldown();
		public static final MapCodec<Cooldown> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record BundleFullness() implements RangeSelectProperty {
		public static final BundleFullness INSTANCE = new BundleFullness();
		public static final MapCodec<BundleFullness> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record CrossbowPull() implements RangeSelectProperty {
		public static final CrossbowPull INSTANCE = new CrossbowPull();
		public static final MapCodec<CrossbowPull> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record Quantity(boolean normalize) implements RangeSelectProperty {
		public static final boolean DEFAULT_NORMALIZE = true;
		public static final MapCodec<Quantity> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("normalize", Quantity.DEFAULT_NORMALIZE).forGetter(Quantity::normalize)
						).apply(instance, Quantity::new));
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record Damage(boolean normalize) implements RangeSelectProperty {
		public static final boolean DEFAULT_NORMALIZE = true;
		public static final MapCodec<Damage> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("normalize", Damage.DEFAULT_NORMALIZE).forGetter(Damage::normalize)
						).apply(instance, Damage::new));
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record CustomModelData(int index) implements RangeSelectProperty {
		public static final int DEFAULT_INDEX = 0;
		public static final MapCodec<CustomModelData> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("index", CustomModelData.DEFAULT_INDEX).forGetter(CustomModelData::index)
						).apply(instance, CustomModelData::new));
		
		public CustomModelData(final int index) {
			Preconditions.checkArgument(index >= 0, "Index must not be negative: " + index);
			this.index = index;
		}
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record UseCycle(float period) implements RangeSelectProperty {
		public static final float DEFAULT_PERIOD = 1.0F;
		public static final MapCodec<UseCycle> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("period", UseCycle.DEFAULT_PERIOD).forGetter(UseCycle::period)
						).apply(instance, UseCycle::new));
		
		public UseCycle(final float period) {
			Preconditions.checkArgument(period > 0, "Period must be positive: " + period);
			this.period = period;
		}
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record UseDuration(boolean remaining) implements RangeSelectProperty {
		public static final boolean DEFAULT_REMAINING = false;
		public static final MapCodec<UseDuration> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("remaining", UseDuration.DEFAULT_REMAINING).forGetter(UseDuration::remaining)
						).apply(instance, UseDuration::new));
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record CompassAngle(boolean wobble, CompassTarget target) implements RangeSelectProperty {
		public static final boolean DEFAULT_WOBBLE = true;
		public static final MapCodec<CompassAngle> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("wobble", CompassAngle.DEFAULT_WOBBLE).forGetter(CompassAngle::wobble),
						CompassTarget.CODEC.fieldOf("target").forGetter(CompassAngle::target)
						).apply(instance, CompassAngle::new));
		
		public CompassAngle(final boolean wobble, final CompassTarget target) {
			this.wobble = wobble;
			this.target = Objects.requireNonNull(target, "target");
		}
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
	
	record Time(boolean wobble, TimeSource source) implements RangeSelectProperty {
		public static final boolean DEFAULT_WOBBLE = true;
		public static final MapCodec<Time> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.BOOL.optionalFieldOf("wobble", Time.DEFAULT_WOBBLE).forGetter(Time::wobble),
						TimeSource.CODEC.fieldOf("source").forGetter(p_390088_ -> p_390088_.source)
						).apply(instance, Time::new));
		
		public Time(final boolean wobble, final TimeSource source) {
			this.wobble = wobble;
			this.source = Objects.requireNonNull(source, "source");
		}
		
		@Override
		public MapCodec<? extends RangeSelectProperty> codec() {
			return CODEC;
		}
	}
}
