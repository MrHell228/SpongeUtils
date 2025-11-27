package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.custom.type.block.BlockTypeArchetype;
import net.hellheim.spongetools.custom.type.block.BlockTypeBuilder;

public final class BlockTypeBuilderImpl
		extends CustomTypeBuilderImpl.TypeBasedWithData<BlockType, BlockState, BlockTypeArchetype, BlockTypeBuilder>
		implements BlockTypeBuilder {
	
	public BlockTypeBuilderImpl() {
		this.reset();
	}
	
	@Override
	protected BlockTypeArchetype baseArchetype() {
		return BlockTypeUtil.Archetypes.DEFAULT;
	}
	
	@Override
	protected DefaultedRegistryType<BlockTypeArchetype> archetypeRegistry() {
		return BlockTypeArchetype.registry();
	}
	
	@Override
	protected BlockTypeArchetype extractArchetype(final BlockType value) {
		return BlockTypeArchetype.forType(value);
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
