package net.hellheim.spongetools.custom.type.item;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.codec.list.RegistryCodecs;
import net.hellheim.spongetools.object.CodecDataSerializable;
import net.hellheim.spongetools.object.TypedKeyMap;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;
import net.kyori.adventure.text.Component;

public interface LoreProvider extends CodecDataSerializable<LoreProvider>, MapCodecProxy<LoreProvider> {
	
	Codec<LoreProvider> CODEC = LoreProvider.registryCodec()
			.dispatch(LoreProvider::mapCodec, Function.identity());
	
	static DefaultedRegistryType<MapCodec<? extends LoreProvider>> registry() {
		return SpongeTools.Registries.LORE_PROVIDER_TYPE;
	}
	
	static Codec<MapCodec<? extends LoreProvider>> registryCodec() {
		return RegistryCodecs.LORE_PROVIDER_TYPE;
	}
	
	static Key<ListValue<LoreProvider>> dataKey() {
		return SpongeTools.Keys.LORE_PROVIDERS;
	}
	
	static DataBuilder<LoreProvider> dataBuilder() {
		return DataCodecs.dataBuilder(CODEC);
	}
	
	static LoreProvider empty() {
		return LoreProvider.Plain.EMPTY;
	}
	
	static LoreProvider.Plain plain(final Component... components) {
		return LoreProvider.plain(List.of(components));
	}
	
	static LoreProvider.Plain plain(final List<Component> components) {
		return new LoreProvider.Plain(components);
	}
	
	/**
	 * Returns the list of {@link Component}s this provider adds to item lore. <br>
	 * It's totally valid for provider to return empty list (e.g. if it acts only in the specific context).
	 * 
	 * @param stack The item to provide the lore for
	 * @param context The context to use the provider with
	 * @return The list of components
	 */
	List<Component> provide(ItemStackLike stack, TypedKeyMap context);
	
	/**
	 * Returns the list of {@link Key}s that hide some parts of regular lore on the item. <br>
	 * Useful for providers that change the appearance of vanilla lore (e.g. enchantments, potions).
	 * 
	 * @return The list of keys
	 */
	default Set<Key<Value<Boolean>>> loreHidingKeys() {
		return Set.of();
	}
	
	@Override
	default Codec<LoreProvider> codec() {
		return CODEC;
	}
	
	record Plain(List<Component> components) implements LoreProvider {
		
		public static final Plain EMPTY = LoreProvider.plain();
		public static final MapCodec<Plain> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						AdventureCodecs.COMPONENT.listOf().fieldOf("components").forGetter(Plain::components)
						).apply(instance, Plain::new));
		
		public Plain(final List<Component> components) {
			this.components = List.copyOf(Objects.requireNonNull(components, "components"));
		}
		
		@Override
		public List<Component> provide(final ItemStackLike stack, final TypedKeyMap context) {
			return this.components;
		}
		
		@Override
		public MapCodec<Plain> mapCodec() {
			return Plain.CODEC;
		}
	}
}
