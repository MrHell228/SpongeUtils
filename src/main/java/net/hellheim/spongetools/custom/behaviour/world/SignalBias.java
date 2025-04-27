package net.hellheim.spongetools.custom.behaviour.world;

import org.spongepowered.api.Sponge;

public interface SignalBias {
	
	static SignalBias left() {
		return Sponge.game().factoryProvider().provide(Factory.class).left();
	}
	
	static SignalBias right() {
		return Sponge.game().factoryProvider().provide(Factory.class).right();
	}
	
	SignalBias opposite();
	
	interface Factory {
		
		SignalBias left();
		
		SignalBias right();
	}
}
