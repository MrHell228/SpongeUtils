package net.hellheim.spongetools.custom.item;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataHolderBuilder;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.util.ResourceKeyedBuilder;

import com.google.common.collect.Sets;
import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.custom.item.model.Item;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.proxy.solid.data.DataHolderProxy;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;
import net.hellheim.spongetools.util.TranslationUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public class CustomItemTypeProperties implements
		ResourceKeyed,
		ComponentLike,
		DataHolderProxy,
		IconProxy,
		ItemStackSnapshotProxy {
	
	public static final MapCodec<CustomItemTypeProperties> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> CustomItemTypeProperties.codecBuilder(instance)
			.apply(instance, CustomItemTypeProperties::new));
	public static final Codec<CustomItemTypeProperties> CODEC = MAP_CODEC.codec();
	
	private final ResourceKey key;
	private final ItemType base;
	private final Optional<Item> model;
	private final Set<Value<?>> data;
	
	private final Component name;
	private final ItemStackSnapshot icon;
	private final ItemStackSnapshot snapshot;
	
	protected CustomItemTypeProperties(final Builder builder) {
		builder.validate();
		this.key = builder.key;
		this.base = builder.base;
		this.model = Optional.ofNullable(builder.model);
		
		this.name = TranslationUtil.item(this.key);
		builder.add(Keys.ITEM_NAME, this.name);
		if (this.model.isPresent()) {
			builder.add(Keys.MODEL, this.key);
		}
		
		this.data = builder.data.stream()
				.map(Value::asImmutable)
				.collect(Collectors.toUnmodifiableSet());
		
		this.icon = this.iconBuilder(builder).getAsItemStackSnapshot();
		this.snapshot = this.snapshotBuilder(builder).getAsItemStackSnapshot();
	}
	
	protected CustomItemTypeProperties(
		final ResourceKey key, final ItemType base, final Optional<Item> model, final Set<Value<?>> data
	) {
		this.key = key;
		this.base = base;
		this.model = model;
		
		final Set<Value<?>> newData = new HashSet<>(data);
		this.name = TranslationUtil.item(this.key);
		newData.add(Value.immutableOf(Keys.ITEM_NAME, this.name));
		if (this.model.isPresent()) {
			newData.add(Value.immutableOf(Keys.MODEL, this.key));
		}
		
		this.data = newData.stream()
				.map(Value::asImmutable)
				.collect(Collectors.toUnmodifiableSet());
		
		final Builder builder = this.asBuilder();
		this.icon = this.iconBuilder(builder).getAsItemStackSnapshot();
		this.snapshot = this.snapshotBuilder(builder).getAsItemStackSnapshot();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static <T extends CustomItemTypeProperties> P4<Mu<T>, ResourceKey, ItemType, Optional<Item>, Set<Value<?>>> codecBuilder(Instance<T> instance) {
		return instance.group(
				SpongeCodecs.RESOURCE_KEY.fieldOf("key").forGetter(CustomItemTypeProperties::key),
				RegistryCodecs.ITEM_TYPE.fieldOf("base").forGetter(CustomItemTypeProperties::base),
				Item.CODEC.optionalFieldOf("model").forGetter(CustomItemTypeProperties::model),
				DataCodecs.valueSet(ItemStackSnapshot.empty()).optionalFieldOf("data", Set.of()).forGetter(CustomItemTypeProperties::data)
				);
	}
	
	protected Component getName() {
		return this.data.stream()
				.filter(v -> v.key() == Keys.CUSTOM_NAME)
				.findAny()
				.map(Value::get)
				.map(Component.class::cast)
				.orElseGet(() -> TranslationUtil.item(this.key));
	}
	
	@Override
	public ResourceKey key() {
		return this.key;
	}
	
	@Override
	public Component asComponent() {
		return this.name;
	}
	
	public ItemType base() {
		return this.base;
	}
	
	public Optional<Item> model() {
		return this.model;
	}
	
	public Set<Value<?>> data() {
		return this.data;
	}
	
	@Override
	public DataHolder getAsDataHolder() {
		return this.snapshot;
	}
	
	@Override
	public ItemStackSnapshot getAsIcon() {
		return this.icon;
	}
	
	@Override
	public ItemStackSnapshot getAsItemStackSnapshot() {
		return this.snapshot;
	}
	
	protected ItemBuilder iconBuilder(final Builder builder) {
		final ItemBuilder item = ItemBuilder.of(this.base).displayName(this.name);
		this.data.forEach(item::offer);
		return item;
	}
	
	protected ItemBuilder snapshotBuilder(final Builder builder) {
		return ItemBuilder.of(this.icon);
	}
	
	protected Builder asBuilder() {
		return new Builder().from(this);
	}
	
	public static class Builder implements
			ResourceKeyedBuilder<CustomItemTypeProperties, Builder>,
			DataHolderBuilder<CustomItemTypeProperties, Builder> {
		
		protected @Nullable ResourceKey key;
		protected @Nullable ItemType base;
		protected @Nullable Item model;
		protected Set<Value<?>> data = new HashSet<>();
		
		public Builder() {
			this.reset();
		}
		
		@Override
		public Builder key(final ResourceKey key) {
			this.key = Objects.requireNonNull(key, "key");
			return this;
		}
		
		public Builder base(final Supplier<ItemType> base) {
			return this.base(Objects.requireNonNull(base, "base").get());
		}
		
		public Builder base(final ItemType base) {
			this.base = Objects.requireNonNull(base, "base");
			return this;
		}
		
		public Builder model(final Item model) {
			this.model = Objects.requireNonNull(model, "model");
			return this;
		}
		
		public <V> Builder supply(final Key<? extends Value<V>> key, final Supplier<V> value) {
			return this.add(key, value.get());
		}
		
		@Override
		public <V> Builder add(final Key<? extends Value<V>> key, final V value) {
			this.data.add(Value.immutableOf(key, value));
			return this;
		}
		
		@Override
		public Builder reset() {
			this.key = null;
			this.base = null;
			this.model = null;
			this.data.clear();
			return this;
		}
		
		@Override
		public Builder from(final CustomItemTypeProperties holder) {
			this.key = holder.key;
			this.base = holder.base;
			this.model = holder.model.orElse(null);
			this.data = Sets.newHashSet(holder.data);
			return this;
		}
		
		@Override
		public CustomItemTypeProperties build() {
			return new CustomItemTypeProperties(this);
		}
		
		public Builder validate() {
			Objects.requireNonNull(this.key, "key must be set");
			
			if (this.base == null) {
				this.base = ItemTypes.RABBIT_FOOT.get();
			}
			
			return this;
		}
	}
}
