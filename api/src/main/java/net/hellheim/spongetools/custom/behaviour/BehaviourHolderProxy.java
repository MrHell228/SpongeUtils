package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;

public interface BehaviourHolderProxy extends BehaviourHolder {
	
	BehaviourHolder getAsBehaviourHolder();
	
	@Override
	default boolean supports(final BehaviourType<?> type) {
		return this.getAsBehaviourHolder().supports(type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> Optional<B> get(final BehaviourType<B> type) {
		return this.getAsBehaviourHolder().get(type);
	}
	
	@Override
	default <B extends Behaviour<?, ?>> B require(final BehaviourType<B> type) {
		return this.getAsBehaviourHolder().require(type);
	}
}
