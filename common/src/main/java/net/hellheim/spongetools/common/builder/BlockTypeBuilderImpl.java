package net.hellheim.spongetools.common.builder;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.registry.DefaultedRegistryType;

import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.block.BlockTypeBuilder;

public final class BlockTypeBuilderImpl extends CustomTypeBuilderImpl<BlockType, BlockArchetype, BlockTypeBuilder>
		implements BlockTypeBuilder {
	
	@Override
	protected BlockType build0() {
		// TODO behaviour
		return this.archetype.assembler().apply(this.context, null);
	}

	@Override
	protected BlockArchetype baseArchetype() {
		return BlockTypeUtil.Archetypes.BLOCK;
	}

	@Override
	protected DefaultedRegistryType<BlockArchetype> archetypeRegistry() {
		return BlockArchetype.registry();
	}
}
