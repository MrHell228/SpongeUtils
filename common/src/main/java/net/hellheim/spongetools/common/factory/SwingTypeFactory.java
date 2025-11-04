package net.hellheim.spongetools.common.factory;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.minecraft.world.InteractionResult;

public final class SwingTypeFactory implements SwingType.Factory {
	
	@Override
	public SwingType none() {
		return Converter.asSponge(InteractionResult.SwingSource.NONE);
	}
	
	@Override
	public SwingType server() {
		return Converter.asSponge(InteractionResult.SwingSource.SERVER);
	}
	
	@Override
	public SwingType client() {
		return Converter.asSponge(InteractionResult.SwingSource.CLIENT);
	}
}
