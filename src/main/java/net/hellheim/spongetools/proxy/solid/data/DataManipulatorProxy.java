package net.hellheim.spongetools.proxy.solid.data;

import org.spongepowered.api.data.DataManipulator;

public interface DataManipulatorProxy extends ValueContainerProxy, DataManipulator {
	
	@Override
	DataManipulator getAsData();
	
	@Override
	default DataManipulator copy() {
		return this.getAsData().copy();
	}
	
	@Override
	default DataManipulator.Mutable asMutableCopy() {
		return this.getAsData().asMutableCopy();
	}
	
	@Override
	default DataManipulator.Mutable asMutable() {
		return this.getAsData().asMutable();
	}
	
	@Override
	default DataManipulator.Immutable asImmutable() {
		return this.getAsData().asImmutable();
	}
}
