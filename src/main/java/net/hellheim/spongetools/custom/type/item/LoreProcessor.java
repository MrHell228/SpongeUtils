package net.hellheim.spongetools.custom.type.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.DefaultedRegistryType;
import org.spongepowered.api.util.CopyableBuilder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.object.CodecDataSerializable;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;
import net.hellheim.spongetools.proxy.solid.item.IItemProxy;
import net.kyori.adventure.text.Component;

public interface LoreProcessor extends CodecDataSerializable<LoreProcessor>, MapCodecProxy<LoreProcessor> {
	
	Codec<LoreProcessor> CODEC = LoreProcessor.registryCodec()
			.dispatch(LoreProcessor::mapCodec, Function.identity());
	
	static DefaultedRegistryType<MapCodec<? extends LoreProcessor>> registry() {
		return SpongeTools.Registries.LORE_PROCESSOR_TYPE;
	}
	
	static Codec<MapCodec<? extends LoreProcessor>> registryCodec() {
		return RegistryCodecs.LORE_PROCESSOR_TYPE;
	}
	
	static Key<Value<LoreProcessor>> dataKey() {
		return SpongeTools.Keys.LORE_PROCESSOR;
	}
	
	static DataBuilder<LoreProcessor> dataBuilder() {
		return DataCodecs.dataBuilder(CODEC);
	}
	
	static LoreProcessor.Plain plain() {
		return Plain.INSTANCE;
	}
	
	static LoreProcessor.Separated.Builder separated() {
		return new LoreProcessor.Separated.Builder();
	}
	
	static ItemStackLike apply(final ItemStackLike stack, final TypedKeyMap context) {
		return stack.get(LoreProcessor.dataKey())
				.<ItemStackLike>map(processor -> processor.apply(stack, context))
				.orElse(stack);
	}
	
	static ItemStackLike apply(final IItemProxy stack, final TypedKeyMap context) {
		return LoreProcessor.applyProcessor(stack.getAsItemStackLike(), context);
	}
	
	/**
	 * Processes all the given {@link LoreProvider}s and returns accumulated list of components.
	 * 
	 * @param stack The stack to process lore for
	 * @param context The context to use providers with
	 * @param providers The list of lore providers
	 * @return The list of components
	 */
	List<Component> process(ItemStackLike stack, TypedKeyMap context, List<LoreProvider> providers);
	
	default ListValue<Component> processValue(
		final ItemStackLike stack, final TypedKeyMap context, final List<LoreProvider> providers
	) {
		return ListValue.immutableOf(Keys.LORE, this.process(stack, context, providers));
	}
	
	/**
	 * Processes and applies given lore providers to the given item. <br>
	 * If the item is mutable, it is modified and returned. <br>
	 * If the item is immutable, its mutable copy is modified and returned.
	 * 
	 * @param stack The stack to apply lore to
	 * @param context The context to use providers with
	 * @param providers The list of lore providers
	 * @return The stack with applied lore
	 *
	default ItemStack apply(final ItemStackLike stack, final TypedKeyMap context, final List<LoreProvider> providers) {
		final ItemStack mutable = stack.asMutable();
		mutable.offer(Keys.LORE, this.process(stack, context, providers));
		providers.forEach(provider ->
				provider.loreHidingKeys().forEach(key ->
						mutable.offer(key, true)));
		return mutable;
	}
	
	/**
	 * Processes and applies lore to the given item. <br>
	 * If item does not have any lore providers, then empty list is used. <br>
	 * If item is mutable, it is modified and returned. <br>
	 * If item is immutable, its mutable copy is modified and returned.
	 * 
	 * @param stack The stack to apply lore to
	 * @param context The context to use providers with
	 * @return The stack with applied lore
	 *
	default ItemStack apply(final ItemStackLike stack, final TypedKeyMap context) {
		return this.apply(stack, context, stack.getOrElse(LoreProvider.dataKey(), List.of()));
	}
	
	/**
	 * Uses {@link #apply(ItemStackLike, TypedKeyMap)} with {@link IItemProxy#getAsItemStackLike()}.
	 * 
	 * @param stack The stack to apply lore to
	 * @param context The context to use providers with
	 * @return The stack with applied lore
	 *
	default ItemStack apply(final IItemProxy stack, final TypedKeyMap context) {
		return this.apply(stack.getAsItemStackLike(), context);
	}
	
	/**
	 * Processes and applies lore to the given item. <br>
	 * If item does not have any lore providers, then the original stack is returned. <br>
	 * If item is mutable, it is modified and returned. <br>
	 * If item is immutable, its mutable copy is modified and returned.
	 * 
	 * @param stack The stack to apply lore to
	 * @param context The context to use providers with
	 * @return The stack with applied lore, or original stack
	 *
	default ItemStackLike applyIfPresent(final ItemStackLike stack, final TypedKeyMap context) {
		return stack.get(LoreProvider.dataKey())
				.<ItemStackLike>map(providers -> this.apply(stack, context, providers))
				.orElse(stack);
	}
	
	/**
	 * Uses {@link #applyIfPresent(ItemStackLike, TypedKeyMap)} with {@link IItemProxy#getAsItemStackLike()}.
	 * 
	 * @param stack The stack to apply lore to
	 * @param context The context to use providers with
	 * @return The stack with applied lore, or original stack
	 *
	default ItemStackLike applyIfPresent(final IItemProxy stack, final TypedKeyMap context) {
		return this.applyIfPresent(stack.getAsItemStackLike(), context);
	}
	*/
	@Override
	default Codec<LoreProcessor> codec() {
		return CODEC;
	}
	
	record Plain() implements LoreProcessor {
		
		public static final Plain INSTANCE = new Plain();
		public static final MapCodec<Plain> CODEC = MapCodec.unit(INSTANCE);
		
		@Override
		public MapCodec<? extends LoreProcessor> mapCodec() {
			return CODEC;
		}
		
		@Override
		public List<Component> process(final ItemStackLike stack, final TypedKeyMap context, final List<LoreProvider> providers) {
			final List<Component> lore = new ArrayList<>();
			providers.forEach(provider -> lore.addAll(provider.provide(stack, context)));
			return lore;
		}
	}
	
	record Separated(LoreProvider prefix, LoreProvider suffix, LoreProvider separator) implements LoreProcessor {
		
		public static final LoreProvider DEFAULT_PREFIX = LoreProvider.empty();
		public static final LoreProvider DEFAULT_SUFFIX = LoreProvider.empty();
		public static final LoreProvider DEFAULT_SEPARATOR = LoreProvider.plain(Component.empty());
		public static final MapCodec<Separated> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						LoreProvider.CODEC.optionalFieldOf("prefix", DEFAULT_PREFIX).forGetter(Separated::prefix),
						LoreProvider.CODEC.optionalFieldOf("suffix", DEFAULT_SUFFIX).forGetter(Separated::suffix),
						LoreProvider.CODEC.optionalFieldOf("separator", DEFAULT_SEPARATOR).forGetter(Separated::separator)
						).apply(instance, Separated::new));
		
		public Separated(final LoreProvider prefix, final LoreProvider suffix, final LoreProvider separator) {
			this.prefix = Objects.requireNonNull(prefix, "prefix");
			this.suffix = Objects.requireNonNull(suffix, "suffix");
			this.separator = Objects.requireNonNull(separator, "separator");
		}
		
		@Override
		public MapCodec<? extends LoreProcessor> mapCodec() {
			return CODEC;
		}
		
		@Override
		public List<Component> process(final ItemStackLike stack, final TypedKeyMap context, final List<LoreProvider> providers) {
			final List<Component> lore = new ArrayList<>();
			if (!providers.isEmpty()) {
				final List<Component> separator = this.separator.provide(stack, context);
				providers.forEach(provider -> {
					final List<Component> loreToAdd = provider.provide(stack, context);
					if (!loreToAdd.isEmpty() && !lore.isEmpty()) {
						lore.addAll(separator);
					}
						
					lore.addAll(loreToAdd);
				});

				lore.addAll(0, this.prefix.provide(stack, context));
				lore.addAll(this.suffix.provide(stack, context));
			}
			return lore;
		}
		
		public static final class Builder implements
				org.spongepowered.api.util.Builder<Separated, Builder>,
				CopyableBuilder<Separated, Builder> {
			
			private LoreProvider prefix;
			private LoreProvider suffix;
			private LoreProvider separator;
			
			public Builder() {
				this.reset();
			}
			
			public Builder prefix(final LoreProvider prefix) {
				this.prefix = Objects.requireNonNull(prefix, "prefix");
				return this;
			}
			
			public Builder suffix(final LoreProvider suffix) {
				this.suffix = Objects.requireNonNull(suffix, "suffix");
				return this;
			}
			
			public Builder separator(final LoreProvider separator) {
				this.separator = Objects.requireNonNull(separator, "separator");
				return this;
			}
			
			@Override
			public Builder reset() {
				this.prefix = Separated.DEFAULT_PREFIX;
				this.suffix = Separated.DEFAULT_SUFFIX;
				this.separator = Separated.DEFAULT_SEPARATOR;
				return this;
			}
			
			@Override
			public Builder from(final Separated value) {
				this.prefix = value.prefix();
				this.suffix = value.suffix();
				this.separator = value.separator();
				return this;
			}
			
			@Override
			public Separated build() {
				return new Separated(this.prefix, this.suffix, this.separator);
			}
		}
	}
}
