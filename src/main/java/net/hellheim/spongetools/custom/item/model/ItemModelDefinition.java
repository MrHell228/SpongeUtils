package net.hellheim.spongetools.custom.item.model;

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

public interface ItemModelDefinition {
	
	final LateBoundIdMapper<ResourceKey, MapCodec<? extends ItemModelDefinition>> ID_MAPPER = new LateBoundIdMapper<>();
	
	final Codec<ItemModelDefinition> CODEC = ItemModelDefinition.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY)
			.dispatch(ItemModelDefinition::codec, Function.identity());
	
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
	
	static Composite composite(final ItemModelDefinition... models) {
		return new Composite(List.of(models));
	}
	
	static Composite composote(final Collection<? extends ItemModelDefinition> models) {
		return new Composite(List.copyOf(models));
	}
	
	static Conditional conditional(final ConditionalProperty property, final ItemModelDefinition onTrue, final ItemModelDefinition onFalse) {
		return new Conditional(property, onTrue, onFalse);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final SelectSwitchCase<T>... cases) {
		return ItemModelDefinition.select(property, Optional.empty(), cases);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final ItemModelDefinition fallback, final SelectSwitchCase<T>... cases) {
		return ItemModelDefinition.select(property, Optional.of(fallback), cases);
	}
	
	@SafeVarargs
	static <T> Select select(final SelectProperty<T> property, final Optional<ItemModelDefinition> fallback, final SelectSwitchCase<T>... cases) {
		return ItemModelDefinition.select(SelectSwitch.of(property, List.of(cases)), fallback);
	}
	
	static <T> Select select(final SelectProperty<T> property, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModelDefinition.select(property, Optional.empty(), cases);
	}
	
	static <T> Select select(final SelectProperty<T> property, final ItemModelDefinition fallback, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModelDefinition.select(property, Optional.of(fallback), cases);
	}
	
	static <T> Select select(final SelectProperty<T> property, final Optional<ItemModelDefinition> fallback, final Collection<SelectSwitchCase<T>> cases) {
		return ItemModelDefinition.select(SelectSwitch.of(property, List.copyOf(cases)), fallback);
	}
	
	static Select select(final SelectSwitch<?, ?> body) {
		return ItemModelDefinition.select(body, Optional.empty());
	}
	
	static Select select(final SelectSwitch<?, ?> body, final ItemModelDefinition fallback) {
		return ItemModelDefinition.select(body, Optional.of(fallback));
	}
	
	static Select select(final SelectSwitch<?, ?> body, final Optional<ItemModelDefinition> fallback) {
		return new Select(body, fallback);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final RangeSelectEntry... entries) {
		return ItemModelDefinition.rangeSelect(property, scale, Optional.empty(), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final ItemModelDefinition fallback, final RangeSelectEntry... entries) {
		return ItemModelDefinition.rangeSelect(property, scale, Optional.of(fallback), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModelDefinition> fallback, final RangeSelectEntry... entries) {
		return new RangeSelect(property, scale, fallback, List.of(entries));
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Collection<RangeSelectEntry> entries) {
		return ItemModelDefinition.rangeSelect(property, scale, Optional.empty(), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final ItemModelDefinition fallback, final Collection<RangeSelectEntry> entries) {
		return ItemModelDefinition.rangeSelect(property, scale, Optional.of(fallback), entries);
	}
	
	static RangeSelect rangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModelDefinition> fallback, final Collection<RangeSelectEntry> entries) {
		return new RangeSelect(property, scale, fallback, List.copyOf(entries));
	}
	
	static Special special(final ResourceKey base, final SpecialModel model) {
		return new Special(base, model);
	}
	
	MapCodec<? extends ItemModelDefinition> codec();
	
	default Item asItem(final boolean handAnimationOnSwap) {
		return Item.of(this, handAnimationOnSwap);
	}
	
	default Item asItem() {
		return Item.of(this);
	}
	
	record Empty() implements ItemModelDefinition {
		public static final Empty INSTANCE = new Empty();
		public static final MapCodec<Empty> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record BundleSelectedItem() implements ItemModelDefinition {
		public static final BundleSelectedItem INSTANCE = new BundleSelectedItem();
		public static final MapCodec<BundleSelectedItem> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record Simple(ResourceKey model, List<TintSource> tints) implements ItemModelDefinition {
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
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record Composite(List<ItemModelDefinition> models) implements ItemModelDefinition {
		public static final MapCodec<Composite> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ItemModelDefinition.CODEC.listOf().fieldOf("models").forGetter(Composite::models)
						).apply(instance, Composite::new));
		
		public Composite(final List<ItemModelDefinition> models) {
			this.models = Objects.requireNonNull(models, "models");
		}
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record Conditional(ConditionalProperty property, ItemModelDefinition onTrue, ItemModelDefinition onFalse) implements ItemModelDefinition {
		public static final MapCodec<Conditional> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ConditionalProperty.CODEC.forGetter(Conditional::property),
						ItemModelDefinition.CODEC.fieldOf("on_true").forGetter(Conditional::onTrue),
						ItemModelDefinition.CODEC.fieldOf("on_false").forGetter(Conditional::onFalse))
						.apply(instance, Conditional::new));
		
		public Conditional(final ConditionalProperty property, final ItemModelDefinition onTrue, final ItemModelDefinition onFalse) {
			this.property = Objects.requireNonNull(property, "property");
			this.onTrue = Objects.requireNonNull(onTrue, "onTrue");
			this.onFalse = Objects.requireNonNull(onFalse, "onFalse");
		}
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record Select(SelectSwitch<?, ?> body, Optional<ItemModelDefinition> fallback) implements ItemModelDefinition {
		public static final MapCodec<Select> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						SelectSwitch.CODEC.forGetter(Select::body),
						ItemModelDefinition.CODEC.optionalFieldOf("fallback").forGetter(Select::fallback)
						).apply(instance, Select::new));
		
		public Select(final SelectSwitch<?, ?> body, final Optional<ItemModelDefinition> fallback) {
			this.body = Objects.requireNonNull(body, "body");
			this.fallback = Objects.requireNonNull(fallback, "fallback");
		}
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record RangeSelect(RangeSelectProperty property, float scale, Optional<ItemModelDefinition> fallback, List<RangeSelectEntry> entries) implements ItemModelDefinition {
		public static final MapCodec<RangeSelect> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						RangeSelectProperty.CODEC.forGetter(RangeSelect::property),
						Codec.FLOAT.optionalFieldOf("scale", Float.valueOf(1.0F)).forGetter(RangeSelect::scale),
						ItemModelDefinition.CODEC.optionalFieldOf("fallback").forGetter(RangeSelect::fallback),
						RangeSelectEntry.CODEC.listOf().fieldOf("entries").forGetter(RangeSelect::entries)
						).apply(instance, RangeSelect::new));
		
		public RangeSelect(final RangeSelectProperty property, final float scale, final Optional<ItemModelDefinition> fallback, final List<RangeSelectEntry> entries) {
			this.property = Objects.requireNonNull(property, "property");
			this.scale = scale;
			this.fallback = Objects.requireNonNull(fallback, "fallback");
			this.entries = Objects.requireNonNull(entries, "entries");
		}
		
		@Override
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
	
	record Special(ResourceKey base, SpecialModel model) implements ItemModelDefinition {
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
		public MapCodec<? extends ItemModelDefinition> codec() {
			return CODEC;
		}
	}
}
