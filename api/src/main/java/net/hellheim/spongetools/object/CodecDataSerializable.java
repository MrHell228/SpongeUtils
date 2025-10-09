package net.hellheim.spongetools.object;

import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.persistence.DataSerializable;

import net.hellheim.spongetools.codec.list.DataCodecs;
import net.hellheim.spongetools.proxy.solid.codec.CodecProxy;

public interface CodecDataSerializable<T> extends DataSerializable, CodecProxy<T> {
	
	@Override
	default int contentVersion() {
		return 0;
	}
	
	@Override
	default DataContainer toContainer() {
		@SuppressWarnings("unchecked")
		final T $this = (T) this;
		return DataCodecs.toContainer(this.codec(), $this);
	}
}
