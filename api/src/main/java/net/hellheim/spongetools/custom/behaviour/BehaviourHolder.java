package net.hellheim.spongetools.custom.behaviour;

import java.util.Optional;

/**
 * {@link BehaviourGroup} that consists of other {@link BehaviourGroup}s.
 */
public interface BehaviourHolder extends BehaviourGroup {
	
	boolean supports(BehaviourGroupType<?> type);
	
	<G extends BehaviourGroup> Optional<G> get(BehaviourGroupType<G> type);
	
	<G extends BehaviourGroup> G require(BehaviourGroupType<G> type);
}
