package net.hellheim.spongetools.proxy.solid.block;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;

public interface BlockStateProxy extends BlockTypeProxy {
	
	BlockState getAsBlockState();
	
	@Override
	default BlockType getAsBlockType() {
		return this.getAsBlockState().type();
	}
}
