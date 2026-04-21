package net.hellheim.spongetools.proxy.optional.entity.instance;

import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.entity.living.player.Player;

import net.hellheim.spongetools.proxy.solid.adventure.ViewerProxy;
import net.hellheim.spongetools.util.ViewerUtil;

public interface OptionalPlayerEntityProxy<T extends Player> extends OptionalLivingEntityProxy<T>, ViewerProxy {
	
	@Override
	default Viewer getAsAudience() {
		return this.getAsEntity().map(Viewer.class::cast).orElse(ViewerUtil.empty());
	}
}
