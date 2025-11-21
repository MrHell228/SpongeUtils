package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.block.BlockTypeBuilder;

public final class BlockTypeBuilderImpl
		extends CustomTypeBuilderImpl.WithData<BlockType, BlockState, BlockArchetype, BlockTypeBuilder>
		implements BlockTypeBuilder {
	
	public BlockTypeBuilderImpl() {
		this.reset();
	}
	
	@Override
	protected BlockArchetype baseArchetype() {
		return BlockTypeUtil.Archetypes.DEFAULT;
	}
	
	@Override
	protected DefaultedRegistryType<BlockArchetype> archetypeRegistry() {
		return BlockArchetype.registry();
	}
	
	@Override
	protected void extractData(final BlockType value) {
		this.addFrom(value.defaultState());
	}
	
	@Override
	protected BlockType build0() {
		// TODO behaviour
		return this.archetype.assembler().apply(this.data.asImmutableManipulator(), this.context, null);
	}
}
