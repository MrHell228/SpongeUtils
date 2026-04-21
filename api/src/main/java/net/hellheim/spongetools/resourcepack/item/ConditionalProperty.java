package net.hellheim.spongetools.resourcepack.item;

import java.util.function.Function;

import org.spongepowered.api.ResourceKey;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;

public interface ConditionalProperty extends MapCodecProxy<ConditionalProperty> {
	
	LateBoundIdMapper<ResourceKey, MapCodec<? extends ConditionalProperty>> ID_MAPPER = new LateBoundIdMapper<>();
	
	MapCodec<ConditionalProperty> CODEC = ConditionalProperty.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatchMap("property", ConditionalProperty::mapCodec, Function.identity());
	
	static Broken broken() {
		return Broken.INSTANCE;
	}
	
	static Damaged damaged() {
		return Damaged.INSTANCE;
	}
	
	static Carried carried() {
		return Carried.INSTANCE;
	}
	
	static Selected selected() {
		return Selected.INSTANCE;
	}
	
	static ExtendedView extendedView() {
		return ExtendedView.INSTANCE;
	}
	
	static FishingRodCast rodCast() {
		return FishingRodCast.INSTANCE;
	}
	
	static BundleHasSelectedItem bundleHasSelectedItem() {
		return BundleHasSelectedItem.INSTANCE;
	}
	
	static Using using() {
		return Using.INSTANCE;
	}
	
	static ViewingEntity viewingEntity() {
		return ViewingEntity.INSTANCE;
	}
	
	static CustomModelData custom() {
		return ConditionalProperty.custom(CustomModelData.DEFAULT_INDEX);
	}
	
	static CustomModelData custom(final int index) {
		return new CustomModelData(index);
	}
	
	record Broken() implements ConditionalProperty {
		public static final Broken INSTANCE = new Broken();
		public static final MapCodec<Broken> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record Damaged() implements ConditionalProperty {
		public static final Damaged INSTANCE = new Damaged();
		public static final MapCodec<Damaged> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record Carried() implements ConditionalProperty {
		public static final Carried INSTANCE = new Carried();
		public static final MapCodec<Carried> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record Selected() implements ConditionalProperty {
		public static final Selected INSTANCE = new Selected();
		public static final MapCodec<Selected> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record ExtendedView() implements ConditionalProperty {
		public static final ExtendedView INSTANCE = new ExtendedView();
		public static final MapCodec<ExtendedView> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record FishingRodCast() implements ConditionalProperty {
		public static final FishingRodCast INSTANCE = new FishingRodCast();
		public static final MapCodec<FishingRodCast> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record BundleHasSelectedItem() implements ConditionalProperty {
		public static final BundleHasSelectedItem INSTANCE = new BundleHasSelectedItem();
		public static final MapCodec<BundleHasSelectedItem> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record Using() implements ConditionalProperty {
		public static final Using INSTANCE = new Using();
		public static final MapCodec<Using> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record ViewingEntity() implements ConditionalProperty {
		public static final ViewingEntity INSTANCE = new ViewingEntity();
		public static final MapCodec<ViewingEntity> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	record CustomModelData(int index) implements ConditionalProperty {
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
		public MapCodec<? extends ConditionalProperty> mapCodec() {
			return CODEC;
		}
	}
	
	// TODO expose DataComponentType
	/*record HasComponent() implements ConditionalProperty {
		
	}*/
	
	// TODO
	/*record KeybindDown() implements ConditionalProperty {
		
	}*/
}
