package net.hellheim.spongetools.resourcepack.item;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.api.ResourceKey;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;

public interface ItemModel extends MapCodecProxy<ItemModel> {
	
	LateBoundIdMapper<ResourceKey, MapCodec<? extends ItemModel>> ID_MAPPER = new LateBoundIdMapper<>();
	
	Codec<ItemModel> CODEC = ItemModel.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatch(ItemModel::mapCodec, Function.identity());
	
	static Empty empty() {
		return Empty.INSTANCE;
	}
	
	static BundleSelectedItem bundleSelectedItem() {
		return BundleSelectedItem.INSTANCE;
	}
	
	static Simple simple(final ResourceKey model, final TintSource... tints) {
		return new Simple(model, List.of(tints));
	}
	
	static Simple simple(final ResourceKey model, final Collection<? extends TintSource> tints) {
		return new Simple(model, List.copyOf(tints));
	}
	
	static Composite composite(final ItemModel... models) {
		return new Composite(List.of(models));
	}
	
	static Composite composote(final Collection<? extends ItemModel> models) {
		return new Composite(List.copyOf(models));
	}
	
	static Conditional conditional(final ConditionalProperty property, final ItemModel onTrue, final ItemModel onFalse) {
		return new Conditional(property, onTrue, onFalse);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final SelectSwitchCase<T>... cases) {
		return ItemModel.select(property, Optional.empty(), cases);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final ItemModel fallback, final SelectSwitchCase<T>... cases) {
		return ItemModel.select(property, Optional.of(fallback), cases);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final Optional<ItemModel> fallback, final SelectSwitchCase<T>... cases) {
		return ItemModel.select(SelectSwitch.of(property, List.of(cases)), fallback);
	}
	
	static <T> Select select(final SelectProperty<T> property, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModel.select(property, Optional.empty(), cases);
	}
	
	static <T> Select select(final SelectProperty<T> property, final ItemModel fallback, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModel.select(property, Optional.of(fallback), cases);
	}
	
	static <T> Select select(final SelectProperty<T> property, final Optional<ItemModel> fallback, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModel.select(SelectSwitch.of(property, List.copyOf(cases)), fallback);
	}
	
	static Select select(final SelectSwitch<?, ?> body) {
		return ItemModel.select(body, Optional.empty());
	}
	
	static Select select(final SelectSwitch<?, ?> body, final ItemModel fallback) {
		return ItemModel.select(body, Optional.of(fallback));
	}
	
	static Select select(final SelectSwitch<?, ?> body, final Optional<ItemModel> fallback) {
		return new Select(body, fallback);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final RangeSelectEntry... entries) {
		return ItemModel.rangeSelect(property, scale, Optional.empty(), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final ItemModel fallback, final RangeSelectEntry... entries) {
		return ItemModel.rangeSelect(property, scale, Optional.of(fallback), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModel> fallback, final RangeSelectEntry... entries) {
		return new RangeSelect(property, scale, fallback, List.of(entries));
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Collection<RangeSelectEntry> entries) {
		return ItemModel.rangeSelect(property, scale, Optional.empty(), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final ItemModel fallback, final Collection<RangeSelectEntry> entries) {
		return ItemModel.rangeSelect(property, scale, Optional.of(fallback), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModel> fallback, final Collection<RangeSelectEntry> entries) {
		return new RangeSelect(property, scale, fallback, List.copyOf(entries));
	}
	
	static Special special(final ResourceKey base, final SpecialModel model) {
		return new Special(base, model);
	}
	
	default ItemDefinition asDefinition(final boolean handAnimationOnSwap) {
		return ItemDefinition.of(this, handAnimationOnSwap);
	}
	
	default ItemDefinition asDefinition() {
		return ItemDefinition.of(this);
	}
	
	record Empty() implements ItemModel {
		public static final Empty INSTANCE = new Empty();
		public static final MapCodec<Empty> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record BundleSelectedItem() implements ItemModel {
		public static final BundleSelectedItem INSTANCE = new BundleSelectedItem();
		public static final MapCodec<BundleSelectedItem> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record Simple(ResourceKey model, List<TintSource> tints) implements ItemModel {
		public static final MapCodec<Simple> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SpongeCodecs.RESOURCE_KEY.fieldOf("model").forGetter(Simple::model),
						TintSource.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(Simple::tints)
						).apply(instance, Simple::new));
		
		public Simple(final ResourceKey model, final List<TintSource> tints) {
			this.model = Objects.requireNonNull(model, "model");
			this.tints = Objects.requireNonNull(tints, "tints");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record Composite(List<ItemModel> models) implements ItemModel {
		public static final MapCodec<Composite> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ItemModel.CODEC.listOf().fieldOf("models").forGetter(Composite::models)
						).apply(instance, Composite::new));
		
		public Composite(final List<ItemModel> models) {
			this.models = Objects.requireNonNull(models, "models");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record Conditional(ConditionalProperty property, ItemModel onTrue, ItemModel onFalse) implements ItemModel {
		public static final MapCodec<Conditional> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ConditionalProperty.CODEC.forGetter(Conditional::property),
						ItemModel.CODEC.fieldOf("on_true").forGetter(Conditional::onTrue),
						ItemModel.CODEC.fieldOf("on_false").forGetter(Conditional::onFalse))
						.apply(instance, Conditional::new));
		
		public Conditional(final ConditionalProperty property, final ItemModel onTrue, final ItemModel onFalse) {
			this.property = Objects.requireNonNull(property, "property");
			this.onTrue = Objects.requireNonNull(onTrue, "onTrue");
			this.onFalse = Objects.requireNonNull(onFalse, "onFalse");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record Select(SelectSwitch<?, ?> body, Optional<ItemModel> fallback) implements ItemModel {
		public static final MapCodec<Select> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SelectSwitch.CODEC.forGetter(Select::body),
						ItemModel.CODEC.optionalFieldOf("fallback").forGetter(Select::fallback)
						).apply(instance, Select::new));
		
		public Select(final SelectSwitch<?, ?> body, final Optional<ItemModel> fallback) {
			this.body = Objects.requireNonNull(body, "body");
			this.fallback = Objects.requireNonNull(fallback, "fallback");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record RangeSelect(RangeSelectProperty property, float scale, Optional<ItemModel> fallback, List<RangeSelectEntry> entries) implements ItemModel {
		public static final MapCodec<RangeSelect> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						RangeSelectProperty.CODEC.forGetter(RangeSelect::property),
						Codec.FLOAT.optionalFieldOf("scale", Float.valueOf(1.0F)).forGetter(RangeSelect::scale),
						ItemModel.CODEC.optionalFieldOf("fallback").forGetter(RangeSelect::fallback),
						RangeSelectEntry.CODEC.listOf().fieldOf("entries").forGetter(RangeSelect::entries)
						).apply(instance, RangeSelect::new));
		
		public RangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModel> fallback, final List<RangeSelectEntry> entries) {
			this.property = Objects.requireNonNull(property, "property");
			this.scale = scale;
			this.fallback = Objects.requireNonNull(fallback, "fallback");
			this.entries = Objects.requireNonNull(entries, "entries");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
	
	record Special(ResourceKey base, SpecialModel model) implements ItemModel {
		public static final MapCodec<Special> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SpongeCodecs.RESOURCE_KEY.fieldOf("base").forGetter(Special::base),
						SpecialModel.CODEC.fieldOf("model").forGetter(Special::model)
						).apply(instance, Special::new));
		
		public Special(final ResourceKey base, final SpecialModel model) {
			this.base = Objects.requireNonNull(base, "base");
			this.model = Objects.requireNonNull(model, "model");
		}
		
		@Override
		public MapCodec<? extends ItemModel> mapCodec() {
			return CODEC;
		}
	}
}
