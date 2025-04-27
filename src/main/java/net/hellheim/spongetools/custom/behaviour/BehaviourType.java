package net.hellheim.spongetools.custom.behaviour;

public interface BehaviourType<B extends Behaviour<?, ?>> {
	
	static <B extends Behaviour<?, ?>> BehaviourType<B> create() {
		return new BehaviourType<>() {};
	}
}
