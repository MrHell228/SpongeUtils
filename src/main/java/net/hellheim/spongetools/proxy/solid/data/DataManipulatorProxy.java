package net.hellheim.spongetools.proxy.solid.data;

import org.spongepowered.api.data.DataManipulator;

public interface DataManipulatorProxy extends ValueContainerProxy, DataManipulator {
	
	@Override
	DataManipulator getAsDataHolder();
	
	@Override
	default DataManipulator copy() {
		return this.getAsDataHolder().copy();
	}
	
	@Override
	default DataManipulator.Mutable asMutableCopy() {
		return this.getAsDataHolder().asMutableCopy();
	}
	
	@Override
	default DataManipulator.Mutable asMutable() {
		return this.getAsDataHolder().asMutable();
	}
	
	@Override
	default DataManipulator.Immutable asImmutable() {
		return this.getAsDataHolder().asImmutable();
	}
}
