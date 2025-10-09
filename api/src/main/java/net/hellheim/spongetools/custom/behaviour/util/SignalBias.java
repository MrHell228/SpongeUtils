package net.hellheim.spongetools.custom.behaviour.util;

import org.spongepowered.api.Sponge;

public interface SignalBias {
	
	SignalBias LEFT = SignalBias.factory().left();
	
	SignalBias RIGHT = SignalBias.factory().right();
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	SignalBias opposite();
	
	interface Factory {
		
		SignalBias left();
		
		SignalBias right();
	}
}
