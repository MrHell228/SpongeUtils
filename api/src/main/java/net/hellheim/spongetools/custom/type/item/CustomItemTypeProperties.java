package net.hellheim.spongetools.custom.type.item;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.CopyableBuilder;
import org.spongepowered.api.util.ResourceKeyedBuilder;

import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.hellheim.spongetools.codec.list.SpongeCodecs;
import net.hellheim.spongetools.object.DeferredValueContainer;
import net.hellheim.spongetools.object.ItemBuilder;
import net.hellheim.spongetools.object.ValueSetBuilder;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.resourcepack.item.ItemModel;
import net.hellheim.spongetools.resourcepack.item.TintSource;
import net.hellheim.spongetools.util.ModelUtil;
import net.hellheim.spongetools.util.TranslationUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public class CustomItemTypeProperties implements
		ResourceKeyed,
		ComponentLike,
		ValueContainerProxy,
		ItemStackSnapshotProxy {
	
	public static final MapCodec<CustomItemTypeProperties> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> CustomItemTypeProperties.codecBuilder(instance)
			.apply(instance, CustomItemTypeProperties::new));
	public static final Codec<CustomItemTypeProperties> CODEC = MAP_CODEC.codec();
	
	private final ResourceKey key;
	private final DefaultedRegistryReference<ItemType> base;
	private final Optional<ItemDefinition> model;
	private final DeferredValueContainer data;
	
	private final Component name;
	private final ItemStackSnapshotProxy icon;
	private final ItemStackSnapshotProxy ingredientIcon;
	private final ItemStackSnapshotProxy snapshot;
	
	protected CustomItemTypeProperties(final Builder builder) {
		builder.validate();
		this.key = builder.key;
		this.base = builder.base.asScopedReference();
		this.model = Optional.ofNullable(builder.model);
		
		this.name = TranslationUtil.item(this.key);
		DeferredValueContainer data = builder.data;
		data = data.withBefore(b -> b.add(Keys.ITEM_NAME, this.name));
		if (this.model.isPresent()) {
			data = data.withBefore(b -> b.add(Keys.MODEL, this.key));
		}
		this.data = data;
		
		this.icon = ItemStackSnapshotProxy.of(() -> this.iconBuilder().getAsItemStackSnapshot());
		this.ingredientIcon = ItemStackSnapshotProxy.of(() -> this.ingredientIconBuilder().getAsItemStackSnapshot());
		this.snapshot = ItemStackSnapshotProxy.of(() -> this.snapshotBuilder().getAsItemStackSnapshot());
	}
	
	protected CustomItemTypeProperties(
		final ResourceKey key, final RegistryKey<ItemType> base, final Optional<ItemDefinition> model, DeferredValueContainer data
	) {
		this.key = key;
		this.base = base.asScopedReference();
		this.model = model;
		
		this.name = TranslationUtil.item(this.key);
		data = data.withBefore(b -> b.add(Keys.ITEM_NAME, this.name));
		if (this.model.isPresent()) {
			data = data.withBefore(b -> b.add(Keys.MODEL, this.key));
		}
		this.data = data;
		
		this.icon = ItemStackSnapshotProxy.of(() -> this.iconBuilder().getAsItemStackSnapshot());
		this.ingredientIcon = ItemStackSnapshotProxy.of(() -> this.ingredientIconBuilder().getAsItemStackSnapshot());
		this.snapshot = ItemStackSnapshotProxy.of(() -> this.snapshotBuilder().getAsItemStackSnapshot());
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static <T extends CustomItemTypeProperties> P4<Mu<T>, ResourceKey, RegistryKey<ItemType>, Optional<ItemDefinition>, DeferredValueContainer> codecBuilder(Instance<T> instance) {
		return instance.group(
				SpongeCodecs.RESOURCE_KEY.fieldOf("key").forGetter(CustomItemTypeProperties::key),
				SpongeCodecs.registryKey(RegistryTypes.ITEM_TYPE).fieldOf("base").forGetter(CustomItemTypeProperties::base),
				ItemDefinition.CODEC.optionalFieldOf("model").forGetter(CustomItemTypeProperties::model),
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
	
	public DefaultedRegistryReference<ItemType> base() {
		return this.base;
	}
	
	public Optional<ItemDefinition> model() {
		return this.model;
	}
	
	@Override
	public DeferredValueContainer getAsData() {
		return this.data;
	}
	
	public ItemStackSnapshot icon() {
		return this.icon.getAsItemStackSnapshot();
	}
	
	public ItemStackSnapshot ingredientIcon() {
		return this.ingredientIcon.getAsItemStackSnapshot();
	}
	
	@Override
	public ItemStackSnapshot getAsItemStackSnapshot() {
		return this.snapshot.getAsItemStackSnapshot();
	}
	
	protected LoreApplicator loreApplicator() {
		return LoreApplicator.empty();
	}
	
	protected ItemBuilder iconBuilder() {
		final ItemBuilder item = ItemBuilder.of(this.base);
		this.getValue(Keys.ITEM_NAME).ifPresent(item::offer);
		this.getValue(Keys.MODEL).ifPresent(item::offer);
		return item;
	}
	
	protected ItemBuilder ingredientIconBuilder() {
		return this.snapshotBuilder();
	}
	
	protected ItemBuilder snapshotBuilder() {
		return this.iconBuilder()
				.copyFrom(this.data)
				.lore(this.loreApplicator());
	}
	
	public static class Builder implements
			ResourceKeyedBuilder<CustomItemTypeProperties, Builder>,
			CopyableBuilder<CustomItemTypeProperties, Builder> {
		
		protected @Nullable ResourceKey key;
		protected @Nullable RegistryKey<ItemType> base;
		protected @Nullable ItemDefinition model;
		protected @Nullable DeferredValueContainer data;
		
		public Builder() {
			this.reset();
		}
		
		@Override
		public Builder key(final ResourceKey key) {
			this.key = Objects.requireNonNull(key, "key");
			return this;
		}
		
		public Builder base(final RegistryKey<ItemType> base) {
			this.base = Objects.requireNonNull(base, "base");
			return this;
		}
		
		public Builder model(final ItemDefinition model) {
			this.model = Objects.requireNonNull(model, "model");
			return this;
		}
		
		public Builder model(final ItemModel definition) {
			return this.model(ItemDefinition.of(definition));
		}
		
		public Builder simpleModel(final TintSource... tints) {
			if (this.key == null) {
				throw new IllegalStateException("key must be set");
			}
			return this.model(ItemModel.simple(ModelUtil.withItemPrefix(this.key), tints));
		}
		
		public Builder data(final DeferredValueContainer data) {
			this.data = Objects.requireNonNull(data, "data");
			return this;
		}
		
		public Builder dataBefore(final Consumer<ValueSetBuilder> data) {
			return this.data == null
					? this.data(DeferredValueContainer.of(data))
					: this.data(this.data.withBefore(data));
		}
		
		public Builder dataAfter(final Consumer<ValueSetBuilder> data) {
			return this.data == null
					? this.data(DeferredValueContainer.of(data))
					: this.data(this.data.withAfter(data));
		}
		
		// TODO keep or remove?
		public Builder data(final Consumer<ValueSetBuilder> data) {
			return this.dataAfter(data);
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
				this.base = ItemTypes.RABBIT_FOOT;
			}
			
			if (this.data == null) {
				this.data = DeferredValueContainer.EMPTY;
			}
			
			return this;
		}
	}
}
