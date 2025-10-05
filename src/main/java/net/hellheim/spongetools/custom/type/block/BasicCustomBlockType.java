package net.hellheim.spongetools.custom.type.block;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.block.BlockState;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.custom.behaviour.type.BlockStateExtension;

public class BasicCustomBlockType implements DefaultedCustomBlockType {
	
	private final CustomBlockTypeProperties properties;
	private @MonotonicNonNull BlockState state;
	private @MonotonicNonNull BlockStateExtension stateExtension;
	
	public BasicCustomBlockType(final CustomBlockTypeProperties properties) {
		this.properties = Objects.requireNonNull(properties, "properties");
	}
	
	public BasicCustomBlockType(final CustomBlockTypeBuilder<?> builder) {
		this(Objects.requireNonNull(builder, "builder").buildProperties());
	}
	
	@Override
	public CustomBlockTypeProperties properties() {
		return this.properties;
	}
	
	@Override
	public BlockState state() {
		if (this.state == null) {
			throw new IllegalStateException("State is not yet bound for " + this.toString());
		}
		
		return this.state;
	}
	
	@Override
	public void bind(final BlockState state) {
		if (this.state != null) {
			throw new IllegalStateException("State is already bound for " + this.toString());
		}
		
		this.state = Objects.requireNonNull(state, "state");
		this.stateExtension = BlockStateExtension.getFor(state);
	}
	
	public BehaviourHolder stateExtension() {
		return this.stateExtension;
	}
}
