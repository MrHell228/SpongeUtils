package net.hellheim.spongetools.custom.item;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.ResourceKeyed;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.registry.RegistryTypes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.proxy.solid.data.ValueContainerProxy;
import net.hellheim.spongetools.proxy.solid.item.IItemProxy;
import net.hellheim.spongetools.proxy.solid.item.ItemStackSnapshotProxy;
import net.hellheim.spongetools.proxy.solid.item.ItemTypeProxy;
import net.hellheim.spongetools.util.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

/**
 * Wrapper for either {@link ItemType} or {@link CustomItemType}.
 */
public sealed abstract class EitherItemType
		implements ResourceKeyed, ComponentLike, ValueContainerProxy, IconProxy, IItemProxy
		permits EitherItemType.Common, EitherItemType.Custom {
	
	private static final Map<ItemType, EitherItemType> COMMON_MAP = new IdentityHashMap<>();
	private static final Map<CustomItemType, EitherItemType> CUSTOM_MAP = new IdentityHashMap<>();
	
	public static final Codec<EitherItemType> CODEC = Codec
			.either(RegistryCodecs.ITEM_TYPE, RegistryCodecs.CUSTOM_ITEM_TYPE)
			.xmap(EitherItemType::of, EitherItemType::either);
	
	public static final DefaultedRegistryType<EitherItemType> registry() {
		return SpongeTools.Registries.EITHER_ITEM_TYPE;
	}
	
	public static Codec<EitherItemType> registryCodec() {
		return RegistryCodecs.EITHER_ITEM_TYPE;
	}
	
	public static EitherItemType common(final Supplier<? extends ItemType> type) {
		return EitherItemType.common(type.get());
	}
	
	public static EitherItemType common(final ItemType type) {
		return EitherItemType.COMMON_MAP.computeIfAbsent(type, EitherItemType.Common::new);
	}
	
	public static EitherItemType custom(final Supplier<? extends CustomItemType> type) {
		return EitherItemType.custom(type.get());
	}
	
	public static EitherItemType custom(final CustomItemType type) {
		return EitherItemType.CUSTOM_MAP.computeIfAbsent(type, EitherItemType.Custom::new);
	}
	
	public static EitherItemType of(final ItemStackLike stack) {
		final Optional<CustomItemType> custom = CustomItemType.get(stack);
		return custom.isPresent() ? EitherItemType.custom(custom.get()) : EitherItemType.common(stack.type());
	}
	
	public static EitherItemType of(final IItemProxy proxy) {
		return proxy instanceof final EitherItemType either
				? either
				: proxy instanceof final CustomItemType custom
				? EitherItemType.custom(custom)
				: proxy instanceof final ItemTypeProxy type
				? EitherItemType.common(type.getAsItemType())
				: EitherItemType.of(proxy.getAsItemStackLike());
	}
	
	public static EitherItemType of(final Either<ItemType, CustomItemType> either) {
		return either.map(EitherItemType::common, EitherItemType::custom);
	}
	
	public static Optional<EitherItemType> resolve(final ResourceKey key) {
		return RegistryTypes.ITEM_TYPE
				.get()
				.findValue(key)
				.map(type -> EitherItemType.common(type))
				.or(() -> CustomItemType.resolve(key)
						.map(type -> EitherItemType.custom(type)));
	}
	
	
	
	public abstract boolean isCommon();
	
	public abstract boolean isCustom();
	
	public abstract Optional<ItemType> common();
	
	public abstract Optional<CustomItemType> custom();
	
	public abstract Either<ItemType, CustomItemType> either();
	
	public EitherItemType mapCommon(final UnaryOperator<ItemType> commonOperator) {
		return this.map(commonOperator, UnaryOperator.identity());
	}
	
	public EitherItemType mapCustom(final UnaryOperator<CustomItemType> customOperator) {
		return this.map(UnaryOperator.identity(), customOperator);
	}
	
	public abstract EitherItemType map(UnaryOperator<ItemType> commonOperator, UnaryOperator<CustomItemType> customOperator);
	
	public abstract <T> T apply(Function<ItemType, T> commonFunction, Function<CustomItemType, T> customFunction);
	
	public abstract void accept(Consumer<ItemType> commonAction, Consumer<CustomItemType> customAction);
	
	public abstract boolean test(Predicate<ItemType> commonTest, Predicate<CustomItemType> customTest);
	
	public abstract boolean is(ItemStackLike stack);
	
	public boolean isAny(final ItemStackLike... stacks) {
		for (final ItemStackLike stack : stacks) {
			if (this.is(stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAny(final Iterable<? extends ItemStackLike> stacks) {
		for (final ItemStackLike stack : stacks) {
			if (this.is(stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);
	
	protected static final class Common extends EitherItemType implements ItemTypeProxy {
		
		private final ItemType type;
		private final IconProxy icon;
		
		protected Common(final ItemType type) {
			this.type = Objects.requireNonNull(type, "type");
			this.icon = IconProxy.wrapTypeCached(() -> this.type);
		}
		
		@Override
		public ItemStackSnapshot getAsIcon() {
			return this.icon.getAsIcon();
		}
		
		@Override
		public ItemType getAsItemType() {
			return this.type;
		}
		
		@Override
		public ValueContainer getAsData() {
			return this.type;
		}
		
		@Override
		public Component asComponent() {
			return this.type.asComponent();
		}
		
		@Override
		public ResourceKey key() {
			return this.type.key(RegistryTypes.ITEM_TYPE);
		}
		
		@Override
		public boolean isCommon() {
			return true;
		}
		
		@Override
		public boolean isCustom() {
			return false;
		}
		
		@Override
		public Optional<ItemType> common() {
			return Optional.of(this.type);
		}
		
		@Override
		public Optional<CustomItemType> custom() {
			return Optional.empty();
		}
		
		@Override
		public Either<ItemType, CustomItemType> either() {
			return Either.left(this.type);
		}
		
		@Override
		public EitherItemType map(final UnaryOperator<ItemType> commonOperator, final UnaryOperator<CustomItemType> customOperator) {
			final ItemType newType = commonOperator.apply(this.type);
			return newType == this.type ? this : EitherItemType.common(newType);
		}
		
		@Override
		public <T> T apply(final Function<ItemType, T> commonMapper, final Function<CustomItemType, T> customMapper) {
			return commonMapper.apply(this.type);
		}
		
		@Override
		public void accept(final Consumer<ItemType> commonAction, final Consumer<CustomItemType> customAction) {
			commonAction.accept(this.type);
		}
		
		@Override
		public boolean test(final Predicate<ItemType> commonTest, final Predicate<CustomItemType> customTest) {
			return commonTest.test(this.type);
		}
		
		@Override
		public boolean is(final ItemStackLike stack) {
			return ItemUtil.is(this.type, stack) && !CustomItemType.isCustom(stack);
		}
		
		@Override
		public int hashCode() {
			return this.type.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			
			return this.type.equals(((EitherItemType.Common) obj).type);
		}
	}
	
	protected static final class Custom extends EitherItemType implements ItemStackSnapshotProxy {
		
		private final CustomItemType type;
		
		protected Custom(final CustomItemType type) {
			this.type = Objects.requireNonNull(type, "type");
		}
		
		@Override
		public ItemStackSnapshot getAsIcon() {
			return this.type.getAsIcon();
		}
		
		@Override
		public ItemStackSnapshot getAsItemStackSnapshot() {
			return this.type.getAsItemStackSnapshot();
		}
		
		@Override
		public ValueContainer getAsData() {
			return this.type;
		}
		
		@Override
		public Component asComponent() {
			return this.type.asComponent();
		}
		
		@Override
		public ResourceKey key() {
			return this.type.key();
		}
		
		@Override
		public boolean isCommon() {
			return false;
		}
		
		@Override
		public boolean isCustom() {
			return true;
		}
		
		@Override
		public Optional<ItemType> common() {
			return Optional.empty();
		}
		
		@Override
		public Optional<CustomItemType> custom() {
			return Optional.of(this.type);
		}
		
		@Override
		public Either<ItemType, CustomItemType> either() {
			return Either.right(this.type);
		}
		
		@Override
		public EitherItemType map(final UnaryOperator<ItemType> commonOperator, final UnaryOperator<CustomItemType> customOperator) {
			final CustomItemType newType = customOperator.apply(this.type);
			return newType == this.type ? this : EitherItemType.custom(newType);
		}
		
		@Override
		public <T> T apply(final Function<ItemType, T> commonMapper, final Function<CustomItemType, T> customMapper) {
			return customMapper.apply(this.type);
		}
		
		@Override
		public void accept(final Consumer<ItemType> commonAction, final Consumer<CustomItemType> customAction) {
			customAction.accept(this.type);
		}
		
		@Override
		public boolean test(final Predicate<ItemType> commonTest, final Predicate<CustomItemType> customTest) {
			return customTest.test(this.type);
		}
		
		@Override
		public boolean is(final ItemStackLike stack) {
			return CustomItemType.is(this.type, stack);
		}
		
		@Override
		public int hashCode() {
			return this.type.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			
			return this.type.equals(((EitherItemType.Custom) obj).type);
		}
	}
}
