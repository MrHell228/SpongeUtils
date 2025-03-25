package net.hellheim.spongetools.custom.item;

import java.util.Optional;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.persistence.DataQuery;
import org.spongepowered.api.data.persistence.DataSerializable;
import org.spongepowered.api.data.persistence.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.persistence.Queries;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.registry.DefaultedRegistryReference;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.model.item.Item;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;
import net.kyori.adventure.text.ComponentLike;

public interface CustomItemType extends
		ResourceKeyed,
		ComponentLike,
		DataSerializable,
		ValueContainer,
		IconProxy,
		ItemStackSnapshotProxy {
	
	static DefaultedRegistryType<CustomItemType> registry() {
		return SpongeTools.Registries.CUSTOM_ITEM;
	}
	
	static Key<Value<CustomItemType>> dataKey() {
		return SpongeTools.Keys.CUSTOM_ITEM;
	}
	
	static DataQuery dataQuery() {
		return SpongeTools.Queries.CUSTOM_ITEM;
	}
	
	static int dataVersion() {
		return SpongeTools.Versions.CUSTOM_ITEM;
	}
	
	static DataBuilder dataBuilder() {
		return CustomItemType.DataBuilder.INSTANCE;
	}
	
	static Optional<CustomItemType> resolve(final ResourceKey key) {
		return CustomItemType.registry()
				.get()
				.findValue(key);
	}
	
	static Optional<CustomItemType> get(final ValueContainer container) {
		return container.get(CustomItemType.dataKey());
	}
	
	static CustomItemType require(final ValueContainer container) {
		return container.require(CustomItemType.dataKey());
	}
	
	static @Nullable CustomItemType getOrNull(final ValueContainer container) {
		return container.get(CustomItemType.dataKey()).orElse(null);
	}
	
	static CustomItemType getOrElse(final ValueContainer container, final CustomItemType defaultValue) {
		return container.getOrElse(CustomItemType.dataKey(), defaultValue);
	}
	
	static boolean supports(final ValueContainer container) {
		return container.supports(CustomItemType.dataKey());
	}
	
	static boolean isCustom(final ValueContainer container) {
		return container.get(CustomItemType.dataKey()).isPresent();
	}
	
	static boolean is(final @Nullable CustomItemType item1, final @Nullable CustomItemType item2) {
		return item1 == null ? item2 == null : item1.is(item2);
	}
	
	static boolean is(final @Nullable CustomItemType item1, final @Nullable ValueContainer item2) {
		return item1 == null ? item2 == null : item1.is(item2);
	}
	
	static boolean is(final @Nullable ValueContainer item1, final @Nullable CustomItemType item2) {
		return item2 == null ? item1 == null : item2.is(item1);
	}
	
	static boolean isAny(final @Nullable CustomItemType itemToCompare, final CustomItemType... items) {
		return itemToCompare != null && itemToCompare.isAnyOf(items);
	}
	
	static boolean isAny(final @Nullable CustomItemType itemToCompare, final Iterable<? extends CustomItemType> items) {
		return itemToCompare != null && itemToCompare.isAnyOfItem(items);
	}
	
	static boolean isAny(final @Nullable ValueContainer container, final CustomItemType... items) {
		return container != null && CustomItemType.isAny(CustomItemType.getOrNull(container), items);
	}
	
	static boolean isAny(final @Nullable ValueContainer container, final Iterable<? extends CustomItemType> items) {
		return container != null && CustomItemType.isAny(CustomItemType.getOrNull(container), items);
	}
	
	static boolean isNone(final @Nullable CustomItemType itemToCompare, final CustomItemType... items) {
		return !CustomItemType.isAny(itemToCompare, items);
	}
	
	static boolean isNone(final @Nullable CustomItemType itemToCompare, final Iterable<? extends CustomItemType> items) {
		return !CustomItemType.isAny(itemToCompare, items);
	}
	
	static boolean isNone(final @Nullable ValueContainer container, final CustomItemType... items) {
		return !CustomItemType.isAny(container, items);
	}
	
	static boolean isNone(final @Nullable ValueContainer container, final Iterable<? extends CustomItemType> items) {
		return !CustomItemType.isAny(container, items);
	}
	
	// Methods that should not be overriden
	
	default boolean is(final @Nullable CustomItemType item) {
		return item == this;
	}
	
	default boolean is(final @Nullable ValueContainer container) {
		return container != null && this.is(CustomItemType.getOrNull(container));
	}
	
	default boolean isAnyOf(final CustomItemType... items) {
		for (final CustomItemType item : items) {
			if (this.is(item)) {
				return true;
			}
		}
		
		return false;
	}
	
	default boolean isAnyOf(final ValueContainer... containers) {
		for (final ValueContainer container : containers) { 
			if (this.is(container)) {
				return true;
			}
		}
		
		return false;
	}
	
	default boolean isAnyOfItem(final Iterable<? extends CustomItemType> items) {
		for (final CustomItemType item : items) { 
			if (this.is(item)) {
				return true;
			}
		}
		
		return false;
	}
	
	default boolean isAnyOfContainer(final Iterable<? extends ValueContainer> containers) {
		for (final ValueContainer container : containers) { 
			if (this.is(container)) {
				return true;
			}
		}
		
		return false;
	}
	
	default boolean isNoneOf(final CustomItemType... items) {
		return !this.isAnyOf(items);
	}
	
	default boolean isNoneOf(final ValueContainer... containers) {
		return !this.isAnyOf(containers);
	}
	
	default boolean isNoneOfItem(final Iterable<? extends CustomItemType> items) {
		return !this.isAnyOfItem(items);
	}
	
	default boolean isNoneOfContainer(final Iterable<? extends ValueContainer> containers) {
		return !this.isAnyOfContainer(containers);
	}
	
	default EitherItemType either() {
		return EitherItemType.custom(this);
	}
	
	// Methods that may be overriden
	
	@Override
	default int contentVersion() {
		return CustomItemType.dataVersion();
	}
	
	@Override
	default DataContainer toContainer() {
		return DataContainer.createNew()
				.set(Queries.CONTENT_VERSION, this.contentVersion())
				.set(CustomItemType.dataQuery(), this.key());
	}
	
	// Methods to implement
	
	DefaultedRegistryReference<ItemType> base();
	
	Optional<Item> model();
	
	public static class DataBuilder extends AbstractDataBuilder<CustomItemType> {
		
		private static final DataBuilder INSTANCE = new DataBuilder(CustomItemType.class, CustomItemType.dataVersion());
		
		private DataBuilder(final Class<CustomItemType> requiredClass, final int supportedVersion) {
			super(requiredClass, supportedVersion);
		}

		@Override
		protected Optional<CustomItemType> buildContent(final DataView container) throws InvalidDataException {
			final ResourceKey key = container.getResourceKey(CustomItemType.dataQuery())
					.orElseThrow(() -> new InvalidDataException("Container must contain " + CustomItemType.dataQuery().toString() + " entry"));
			return CustomItemType.resolve(key);
		}
		
	}
}
