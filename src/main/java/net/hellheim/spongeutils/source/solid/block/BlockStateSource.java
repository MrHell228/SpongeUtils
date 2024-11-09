package net.hellheim.spongeutils.source.solid.block;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;

public interface BlockStateSource extends BlockTypeSource {
	
	BlockState getAsBlockState();
	
	@Override
	default BlockType getAsBlockType() {
		return this.getAsBlockState().type();
	}
}
