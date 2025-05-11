package net.hellheim.spongetools.proxy.solid.block;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.state.State;
import org.spongepowered.api.state.StateContainer;
import org.spongepowered.api.state.StateProperty;

public interface StateContainerProxy<S extends State<S>> extends StateContainer<S> {
	
	StateContainer<S> getAsStateContainer();
	
	@Override
	default List<S> validStates() {
		return this.getAsStateContainer().validStates();
	}
	
	@Override
	default S defaultState() {
		return this.getAsStateContainer().defaultState();
	}
	
	@Override
	default Collection<StateProperty<?>> stateProperties() {
		return this.getAsStateContainer().stateProperties();
	}
	
	@Override
	default Optional<StateProperty<?>> findStateProperty(final String name) {
		return this.getAsStateContainer().findStateProperty(name);
	}
}
