package net.hellheim.spongetools.manager;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongetools.menu.BasicMenu;
import net.hellheim.spongetools.menu.IMenuType;
import net.hellheim.spongetools.menu.MenuAction;

public class BasicMenuManager extends Manager implements IMenuManager<BasicMenu> {
	
	public BasicMenuManager(final PluginContainer plugin) {
		super(plugin);
	}
	
	@Override
	public BasicMenu create(final ServerPlayer player, final IMenuType<BasicMenu> type, final MenuAction<BasicMenu> dataSetter) {
		return new BasicMenu(this.plugin(), player, type, dataSetter);
	}
}
