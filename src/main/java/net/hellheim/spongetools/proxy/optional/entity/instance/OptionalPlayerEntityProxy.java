package net.hellheim.spongetools.proxy.optional.entity.instance;

import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.entity.living.player.Player;

import net.hellheim.spongetools.ViewerUtil;
import net.hellheim.spongetools.proxy.solid.adventure.ViewerProxy;

public interface OptionalPlayerEntityProxy<T extends Player> extends OptionalLivingEntityProxy<T>, ViewerProxy {
	
	@Override
	default Viewer getAsAudience() {
		return this.getAsEntity().map(Viewer.class::cast).orElse(ViewerUtil.empty());
	}
}
