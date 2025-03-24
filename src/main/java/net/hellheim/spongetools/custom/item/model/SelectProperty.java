package net.hellheim.spongetools.custom.item.model;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.data.type.HandPreference;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.recipe.smithing.TrimMaterial;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.world.WorldType;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.custom.item.model.enums.ChargeType;

public interface SelectProperty<T> {
	
	final LateBoundIdMapper<ResourceKey, MapCodec<? extends SelectSwitch<?, ?>>> ID_MAPPER = new LateBoundIdMapper<>();
	
    final Codec<MapCodec<? extends SelectSwitch<?, ?>>> CODEC = SelectProperty.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY);
	
	static Charge charge() {
		return Charge.INSTANCE;
	}
	
	static World world() {
		return World.INSTANCE;
	}
	
	static Entity entity() {
		return Entity.INSTANCE;
	}
	
	static Trim trim() {
		return Trim.INSTANCE;
	}
	
	static Display display() {
		return Display.INSTANCE;
	}
	
	static MainHand mainHand() {
		return MainHand.INSTANCE;
	}
	
	static CustomModelData custom() {
		return SelectProperty.custom(CustomModelData.DEFAULT_INDEX);
	}
	
	static CustomModelData custom(final int index) {
		return new CustomModelData(index);
	}
	
	static StateProperty stateProperty(final String property) {
		return new StateProperty(property);
	}
	
	static LocalTime localTime(final String format, final TimeZone timeZone) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT_LOCALE, Optional.of(timeZone));
	}
	
	static LocalTime localTime(final String format) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT_LOCALE, Optional.empty());
	}
	
	static LocalTime localTime(final String format, final Optional<TimeZone> timeZone) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT_LOCALE, timeZone);
	}
	
	static LocalTime localTime(final String format, final Locale localeId, final TimeZone timeZone) {
		return SelectProperty.localTime(format, localeId, Optional.of(timeZone));
	}
	
	static LocalTime localTime(final String format, final Locale localeId) {
		return SelectProperty.localTime(format, localeId, Optional.empty());
	}
	
	static LocalTime localTime(final String format, final Locale locale, final Optional<TimeZone> timeZone) {
		final LocalTime time = new LocalTime(format, locale, timeZone);
		LocalTime.validate(time).getOrThrow(IllegalArgumentException::new);
		return time;
	}
	
	MapCodec<? extends SelectSwitch<?, ?>> codec();
	
	record Charge() implements SelectProperty<ChargeType> {
		public static final Charge INSTANCE = new Charge();
		public static final MapCodec<Charge> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, ChargeType.CODEC);
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record World() implements SelectProperty<RegistryKey<WorldType>> {
		public static final World INSTANCE = new World();
		public static final MapCodec<World> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, SpongeCodecs.registryKey(RegistryTypes.WORLD_TYPE));
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record Entity() implements SelectProperty<RegistryKey<EntityType<? extends org.spongepowered.api.entity.Entity>>> {
		public static final Entity INSTANCE = new Entity();
		public static final MapCodec<Entity> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, SpongeCodecs.registryKey(RegistryTypes.ENTITY_TYPE));
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record Trim() implements SelectProperty<RegistryKey<TrimMaterial>> {
		public static final Trim INSTANCE = new Trim();
		public static final MapCodec<Trim> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, SpongeCodecs.registryKey(RegistryTypes.TRIM_MATERIAL));
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record Display() implements SelectProperty<ItemDisplayContext> {
		public static final Display INSTANCE = new Display();
		public static final MapCodec<Display> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, ItemDisplayContext.CODEC);
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record MainHand() implements SelectProperty<HandPreference> {
		public static final MainHand INSTANCE = new MainHand();
		public static final MapCodec<MainHand> CODEC = MapCodec.unit(INSTANCE);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, RegistryCodecs.HAND_PREFERENCE);
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record CustomModelData(int index) implements SelectProperty<String> {
		public static final int DEFAULT_INDEX = 0;
		public static final MapCodec<CustomModelData> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("index", CustomModelData.DEFAULT_INDEX).forGetter(CustomModelData::index)
						).apply(instance, CustomModelData::new));
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, Codec.STRING);
		
		public CustomModelData(final int index) {
			Preconditions.checkArgument(index >= 0, "Index must not be negative: " + index);
			this.index = index;
		}
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record StateProperty(String property) implements SelectProperty<String> {
		public static final MapCodec<StateProperty> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.STRING.fieldOf("block_state_property").forGetter(StateProperty::property)
						).apply(instance, StateProperty::new));
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, Codec.STRING);
		
		public StateProperty(final String property) {
			this.property = Objects.requireNonNull(property, "property");
		}
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	record LocalTime(String format, Locale locale, Optional<TimeZone> timeZone) implements SelectProperty<String> {
		public static final Locale DEFAULT_LOCALE = Locale.of("");
		public static final MapCodec<LocalTime> CODEC = RecordCodecBuilder.<LocalTime>mapCodec(
				instance -> instance.group(
						Codec.STRING.fieldOf("pattern").forGetter(LocalTime::format),
						ExtraCodecs.LOCALE.optionalFieldOf("locale", LocalTime.DEFAULT_LOCALE).forGetter(LocalTime::locale),
						ExtraCodecs.TIME_ZONE.optionalFieldOf("time_zone").forGetter(LocalTime::timeZone)
						).apply(instance, LocalTime::new)
				).validate(LocalTime::validate);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, Codec.STRING);
		
		public LocalTime(final String format, final Locale locale, final Optional<TimeZone> timeZone) {
			this.format = Objects.requireNonNull(format, "format");
			this.locale = Objects.requireNonNull(locale, "locale");
			this.timeZone = Objects.requireNonNull(timeZone, "timeZone");
		}
		
		private static DataResult<LocalTime> validate(LocalTime data) {
			try {
				new SimpleDateFormat(data.format, data.locale);
		        return DataResult.success(data);
			} catch (final IllegalArgumentException e) {
				return DataResult.error(e::toString);
	        }
		}
		
		@Override
		public MapCodec<? extends SelectSwitch<?, ?>> codec() {
			return SWITCH;
		}
	}
	
	private static <T, P extends SelectProperty<T>> MapCodec<SelectSwitch<T, P>> create(
		MapCodec<P> propertyCodec, Codec<T> valueCodec
	) {
		final Codec<List<SelectSwitchCase<T>>> codec = SelectSwitchCase.codec(valueCodec)
				.listOf()
				.validate(
					cases -> {
						if (cases.isEmpty()) {
							return DataResult.error(() -> "Empty case list");
						} else {
							Multiset<T> multiset = HashMultiset.create();
							
							for (SelectSwitchCase<T> switchcase : cases) {
								multiset.addAll(switchcase.values());
							}
							
							return multiset.size() != multiset.entrySet().size()
								? DataResult.error(
									() -> "Duplicate case conditions: "
											+ multiset.entrySet()
												.stream()
												.filter(p_388701_ -> p_388701_.getCount() > 1)
												.map(p_387521_ -> p_387521_.getElement().toString())
												.collect(Collectors.joining(", "))
												)
								: DataResult.success(cases);
						}
					}
				);
		
		return RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						propertyCodec.forGetter(SelectSwitch::property),
						codec.fieldOf("cases").forGetter(SelectSwitch::cases)
						).apply(instance, SelectSwitch::new));
	}
}
