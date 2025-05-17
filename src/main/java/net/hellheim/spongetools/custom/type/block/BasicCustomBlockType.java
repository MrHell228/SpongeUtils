package net.hellheim.spongetools.custom.type.block;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class BasicCustomBlockType implements DefaultedCustomBlockType {
	
	private final CustomBlockTypeProperties propeties;
	private @MonotonicNonNull BlockState state;
	private @MonotonicNonNull BlockStateExtension stateExtension;
	
	public BasicCustomBlockType(final CustomBlockTypeProperties propeties) {
		this.propeties = Objects.requireNonNull(propeties, "propeties");
	}
	
	public BasicCustomBlockType(final CustomBlockTypeBuilder<?> builder) {
		this(Objects.requireNonNull(builder, "builder").buildProperties());
	}
	
	@Override
	public CustomBlockTypeProperties properties() {
		return this.propeties;
	}
	
	@Override
	public BlockState state() {
		if (this.state == null) {
			throw new IllegalStateException("State is not yet bound");
		}
		
		return this.state;
	}
	
	@Override
	public void bind(final BlockState state) {
		if (this.state != null) {
			throw new IllegalStateException("State is already bound");
		}
		
		this.state = Objects.requireNonNull(state, "state");
		this.stateExtension = BlockStateExtension.getFor(state);
	}
	
	public BehaviourHolder stateExtension() {
		return this.stateExtension;
	}
}
