package net.hellheim.spongetools.custom.item.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.LateBoundIdMapper;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

public interface SelectProperty<T> {
	
    final Codec<MapCodec<? extends SelectSwitch<?, ?>>> CODEC = Registrar.ID_MAPPER.codec(SpongeCodecs.RESOURCE_KEY);
	
	final class Registrar {
		
		private static final LateBoundIdMapper<ResourceKey, MapCodec<? extends SelectSwitch<?, ?>>> ID_MAPPER = new LateBoundIdMapper<>();
		
		static {
			ID_MAPPER.put(ResourceKey.minecraft("custom_model_data"), CustomModelData.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("main_hand"), MainHand.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("charge_type"), Charge.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("trim_material"), Trim.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("block_state"), StateProperty.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("display_context"), Display.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("local_time"), LocalTime.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("context_entity_type"), Entity.SWITCH);
	        ID_MAPPER.put(ResourceKey.minecraft("context_dimension"), World.SWITCH);
		}
		
		private Registrar() {
		}
	}
	
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
		return SelectProperty.custom(CustomModelData.DEFAULT);
	}
	
	static CustomModelData custom(final int index) {
		return new CustomModelData(index);
	}
	
	static StateProperty stateProperty(final String property) {
		return new StateProperty(property);
	}
	
	static LocalTime localTime(final String format, final TimeZone timeZone) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT, Optional.of(timeZone));
	}
	
	static LocalTime localTime(final String format) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT, Optional.empty());
	}
	
	static LocalTime localTime(final String format, final Optional<TimeZone> timeZone) {
		return SelectProperty.localTime(format, LocalTime.DEFAULT, timeZone);
	}
	
	static LocalTime localTime(final String format, final String localeId, final TimeZone timeZone) {
		return SelectProperty.localTime(format, localeId, Optional.of(timeZone));
	}
	
	static LocalTime localTime(final String format, final String localeId) {
		return SelectProperty.localTime(format, localeId, Optional.empty());
	}
	
	static LocalTime localTime(final String format, final String localeId, final Optional<TimeZone> timeZone) {
		return new LocalTime(format, localeId, timeZone);
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
		public static final int DEFAULT = 0;
		public static final MapCodec<CustomModelData> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("index", DEFAULT).forGetter(CustomModelData::index)
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
	
	record LocalTime(String format, String locale, Optional<TimeZone> timeZone) implements SelectProperty<String> {
		public static final String DEFAULT = "";
		private static final Codec<TimeZone> TIME_ZONE_CODEC = Codec.STRING.comapFlatMap(str -> {
			TimeZone timezone = TimeZone.getTimeZone(str);
			return timezone.equals(TimeZone.UNKNOWN_ZONE)
					? DataResult.error(() -> "Unknown timezone: " + str)
					: DataResult.success(timezone);
		}, TimeZone::getID);
		public static final MapCodec<LocalTime> CODEC = RecordCodecBuilder.mapCodec(
				p_389646_ -> p_389646_.group(
						Codec.STRING.fieldOf("pattern").forGetter(p_390095_ -> p_390095_.format),
						Codec.STRING.optionalFieldOf("locale", DEFAULT).forGetter(p_390090_ -> p_390090_.locale),
						TIME_ZONE_CODEC.optionalFieldOf("time_zone").forGetter(p_390093_ -> p_390093_.timeZone)
						).apply(p_389646_, LocalTime::new)
				).validate(LocalTime::validate);
		public static final MapCodec<? extends SelectSwitch<?, ?>> SWITCH = SelectProperty.create(CODEC, Codec.STRING);
		
		public LocalTime(final String format, final String locale, final Optional<TimeZone> timeZone) {
			this.format = Objects.requireNonNull(format, "format");
			this.locale = Objects.requireNonNull(locale, "locale");
			this.timeZone = Objects.requireNonNull(timeZone, "timeZone");
			LocalTime.validate(this);
		}
		
		private static DataResult<LocalTime> validate(LocalTime data) {
			ULocale ulocale = new ULocale(data.locale);
	        Calendar calendar = data.timeZone
	            .map(p_389490_ -> Calendar.getInstance(p_389490_, ulocale))
	            .orElseGet(() -> Calendar.getInstance(ulocale));
	        SimpleDateFormat simpledateformat = new SimpleDateFormat(data.format, ulocale);
	        simpledateformat.setCalendar(calendar);
	        
	        try {
	            simpledateformat.format(new Date());
	        } catch (Exception exception) {
	            return DataResult.error(() -> "Invalid time format '" + simpledateformat + "': " + exception.getMessage());
	        }
	        
	        return DataResult.success(data);
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
