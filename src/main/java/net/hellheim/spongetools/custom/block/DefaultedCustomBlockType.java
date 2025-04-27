package net.hellheim.spongetools.custom.block;

import java.util.Objects;

import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class DefaultedCustomBlockType implements CustomBlockType {
	
	private final BlockStateProvider stateProvider;
	private final BlockStateExtension stateExtension;
	
	public DefaultedCustomBlockType(final BlockStateProvider stateProvider) {
		this.stateProvider = Objects.requireNonNull(stateProvider, "stateProvider");
		this.stateExtension = BlockStateExtension.getFor(this.stateProvider.provide());
	}
	
	@Override
	public BlockStateProvider stateProvider() {
		return this.stateProvider;
	}
	
	@Override
	public BlockStateExtension stateExtension() {
		return this.stateExtension;
	}
}
