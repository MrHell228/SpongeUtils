package net.hellheim.spongetools.custom.type.item;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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
import net.hellheim.spongetools.custom.type.EitherType;
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
		implements EitherType<ItemType, CustomItemType, EitherItemType>, ResourceKeyed, ComponentLike, ValueContainerProxy, IItemProxy
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
				.map(EitherItemType::common)
				.or(() -> CustomItemType.resolve(key)
						.map(EitherItemType::custom));
	}
	
	
	
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
	
	protected static final class Common extends EitherItemType implements
			EitherType.Common<ItemType, CustomItemType, EitherItemType>,
			ItemTypeProxy {
		
		private final ItemType type;
		
		protected Common(final ItemType type) {
			this.type = Objects.requireNonNull(type, "type");
		}
		
		@Override
		public ItemType get() {
			return this.type;
		};
		
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
		public EitherItemType map(
			final Function<? super ItemType, ? extends ItemType> commonMapper,
			final Function<? super CustomItemType, ? extends CustomItemType> customMapper
		) {
			final ItemType newType = commonMapper.apply(this.type);
			return newType == this.type ? this : EitherItemType.common(newType);
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
	
	protected static final class Custom extends EitherItemType implements
			EitherType.Custom<ItemType, CustomItemType, EitherItemType>,
			ItemStackSnapshotProxy {
		
		private final CustomItemType type;
		
		protected Custom(final CustomItemType type) {
			this.type = Objects.requireNonNull(type, "type");
		}
		
		@Override
		public CustomItemType get() {
			return this.type;
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
		public EitherItemType map(
			final Function<? super ItemType, ? extends ItemType> commonMapper,
			final Function<? super CustomItemType, ? extends CustomItemType> customMapper
		) {
			final CustomItemType newType = customMapper.apply(this.type);
			return newType == this.type ? this : EitherItemType.custom(newType);
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
