package net.hellheim.spongetools.manager;

import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import net.hellheim.spongetools.menu.IMenuType;
import net.hellheim.spongetools.menu.Menu;
import net.hellheim.spongetools.menu.MenuAction;

public interface IMenuManager<M extends Menu<M>> {
	
	default M create(ServerPlayer player, IMenuType<M> type) {
		return this.create(player, type, MenuAction.empty());
	}
	
	M create(ServerPlayer player, IMenuType<M> type, MenuAction<M> dataSetter);
}
