package net.hellheim.spongetools.custom.item;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.ResourceKeyedBuilder;

import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.custom.item.model.Item;
import net.hellheim.spongetools.object.DeferredValueContainer;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.object.ValueSetBuilder;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;
import net.hellheim.spongetools.util.TranslationUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public class CustomItemTypeProperties implements
		ResourceKeyed,
		ComponentLike,
		ValueContainerProxy,
		IconProxy,
		ItemStackSnapshotProxy {
	
	public static final MapCodec<CustomItemTypeProperties> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> CustomItemTypeProperties.codecBuilder(instance)
			.apply(instance, CustomItemTypeProperties::new));
	public static final Codec<CustomItemTypeProperties> CODEC = MAP_CODEC.codec();
	
	private final ResourceKey key;
	private final ItemType base;
	private final Optional<Item> model;
	private final DeferredValueContainer data;
	
	private final Component name;
	private final ItemStackSnapshotProxy icon;
	private final ItemStackSnapshotProxy snapshot;
	
	protected CustomItemTypeProperties(final Builder builder) {
		builder.validate();
		this.key = builder.key;
		this.base = builder.base;
		this.model = Optional.ofNullable(builder.model);
		
		this.name = TranslationUtil.item(this.key);
		DeferredValueContainer data = builder.data;
		data = data.withBefore(b -> b.add(Keys.ITEM_NAME, this.name));
		if (this.model.isPresent()) {
			data = data.withBefore(b -> b.add(Keys.MODEL, this.key));
		}
		this.data = data;
		
		this.icon = ItemStackSnapshotProxy.of(() -> this.iconBuilder(builder).getAsItemStackSnapshot());
		this.snapshot = ItemStackSnapshotProxy.of(() -> this.snapshotBuilder(builder).getAsItemStackSnapshot());
	}
	
	protected CustomItemTypeProperties(
		final ResourceKey key, final ItemType base, final Optional<Item> model, DeferredValueContainer data
	) {
		this.key = key;
		this.base = base;
		this.model = model;
		
		this.name = TranslationUtil.item(this.key);
		data = data.withBefore(b -> b.add(Keys.ITEM_NAME, this.name));
		if (this.model.isPresent()) {
			data = data.withBefore(b -> b.add(Keys.MODEL, this.key));
		}
		this.data = data;
		
		final Builder builder = this.asBuilder();
		this.icon = ItemStackSnapshotProxy.of(() -> this.iconBuilder(builder).getAsItemStackSnapshot());
		this.snapshot = ItemStackSnapshotProxy.of(() -> this.snapshotBuilder(builder).getAsItemStackSnapshot());
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static <T extends CustomItemTypeProperties> P4<Mu<T>, ResourceKey, ItemType, Optional<Item>, DeferredValueContainer> codecBuilder(Instance<T> instance) {
		return instance.group(
				SpongeCodecs.RESOURCE_KEY.fieldOf("key").forGetter(CustomItemTypeProperties::key),
				RegistryCodecs.ITEM_TYPE.fieldOf("base").forGetter(CustomItemTypeProperties::base),
				Item.CODEC.optionalFieldOf("model").forGetter(CustomItemTypeProperties::model),
				DeferredValueContainer.codec(ItemStackSnapshot.empty()).optionalFieldOf("data", DeferredValueContainer.EMPTY).forGetter(CustomItemTypeProperties::getAsData)
				);
	}
	
	protected Component getName() {
		return this.data.getAsData().get(Keys.CUSTOM_NAME)
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
	
	@Override
	public DeferredValueContainer getAsData() {
		return this.data;
	}
	
	@Override
	public ItemStackSnapshot getAsIcon() {
		return this.icon.getAsItemStackSnapshot();
	}
	
	@Override
	public ItemStackSnapshot getAsItemStackSnapshot() {
		return this.snapshot.getAsItemStackSnapshot();
	}
	
	protected ItemBuilder iconBuilder(final Builder builder) {
		final ItemBuilder item = ItemBuilder.of(this.base).displayName(this.name);
		this.data.getAsData().getValues().forEach(item::offer);
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
			CopyableBuilder<CustomItemTypeProperties, Builder> {
		
		protected @Nullable ResourceKey key;
		protected @Nullable ItemType base;
		protected @Nullable Item model;
		protected @Nullable DeferredValueContainer data;
		
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
		
		public Builder data(final DeferredValueContainer data) {
			this.data = Objects.requireNonNull(data, "data");
			return this;
		}
		
		public Builder data(final Consumer<ValueSetBuilder> data) {
			return this.data(DeferredValueContainer.of(data));
		}
		
		@Override
		public Builder reset() {
			this.key = null;
			this.base = null;
			this.model = null;
			this.data = null;
			return this;
		}
		
		@Override
		public Builder from(final CustomItemTypeProperties holder) {
			this.key = holder.key;
			this.base = holder.base;
			this.model = holder.model.orElse(null);
			this.data = holder.data;
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
			
			if (this.data == null) {
				this.data = DeferredValueContainer.EMPTY;
			}
			
			return this;
		}
	}
}
