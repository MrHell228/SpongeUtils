package net.hellheim.spongetools.resourcepack.block;

import java.util.Objects;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateContainer;

import com.mojang.serialization.DynamicOps;

import net.hellheim.spongetools.proxy.solid.block.StateContainerProxy;
import net.hellheim.spongetools.proxy.solid.codec.DynamicOpsProxy;

public final class StateOps<T, S extends State<S>> implements DynamicOpsProxy<T>, StateContainerProxy<S> {
	
	private final DynamicOps<T> ops;
	private final StateContainer<S> container;
	
	private StateOps(final DynamicOps<T> ops, final StateContainer<S> container) {
		this.ops = Objects.requireNonNull(ops, "ops");
		this.container = Objects.requireNonNull(container, "contaier");
	}
	
	public static <T, S extends State<S>> StateOps<T, S> of(
		final DynamicOps<T> ops, final StateContainer<S> container
	) {
		return new StateOps<>(ops, container);
	}
	
	@Override
	public DynamicOps<T> getAsOps() {
		return this.ops;
	}
	
	@Override
	public StateContainer<S> getAsStateContainer() {
		return this.container;
	}
}
