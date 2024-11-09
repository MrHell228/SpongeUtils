package net.hellheim.spongeutils.menu;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.plugin.PluginContainer;

/**
 * Default implemetation of {@link Menu}.
 */
public class BasicMenu extends Menu<BasicMenu> {
	
	public BasicMenu(final PluginContainer plugin, final ServerPlayer player, final IMenuType<BasicMenu> type, final MenuAction<BasicMenu> dataSetter) {
		super(plugin, player, type, dataSetter);
	}
	
}
