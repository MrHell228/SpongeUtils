package net.hellheim.spongetools.custom.type.item;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.ItemAction;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.MapCodec;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.proxy.solid.codec.MapCodecProxy;

public interface CustomItemAction extends ItemAction {
	
	static CustomItemAction of(final Config config) {
		return Sponge.game().factoryProvider().provide(Factory.class).of(config);
	}
	
	static DefaultedRegistryType<MapCodec<? extends Config>> registry() {
		return SpongeTools.Registries.ITEM_ACTION_CONFIG_TYPE;
	}
	
	Config config();
	
	/**
	 * This interface is supposed to be implemented by API consumers. <br>
	 * Codec returned by {@link #mapCodec()} must be registered to {@link CustomItemAction#registry()}.
	 */
	interface Config extends MapCodecProxy<Config> {
		
		/**
		 * @see ItemAction#apply(Living, ItemStackLike)
		 */
		boolean apply(Living entity, ItemStackLike stack);
	}
	
	interface Factory {
		
		CustomItemAction of(Config config);
	}
}
