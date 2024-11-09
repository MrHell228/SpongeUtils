package net.hellheim.spongeutils.source.optional.entity.instance;

import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.entity.living.player.Player;

import net.hellheim.spongeutils.ViewerUtil;
import net.hellheim.spongeutils.source.solid.adventure.ViewerSource;

public interface OptionalPlayerEntitySource<T extends Player> extends OptionalLivingEntitySource<T>, ViewerSource {
	
	@Override
	default Viewer getAsAudience() {
		return this.getAsEntity().map(Viewer.class::cast).orElse(ViewerUtil.empty());
	}
}
