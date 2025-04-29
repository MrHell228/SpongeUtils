package net.hellheim.spongetools.custom.behaviour.util;

import org.spongepowered.api.Sponge;

public interface SwingType {
	
	SwingType NONE = SwingType.factory().none();
	
	SwingType SERVER = SwingType.factory().server();
	
	SwingType CLIENT = SwingType.factory().client();
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	interface Factory {
		
		SwingType none();
		
		SwingType server();
		
		SwingType client();
	}
}
