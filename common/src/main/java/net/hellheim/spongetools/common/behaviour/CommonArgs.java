package net.hellheim.spongetools.common.behaviour;

import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;

public final class CommonArgs {
	
	public static BehaviourArgs empty() {
		return Empty.INSTANCE;
	}
	
	private record Empty() implements BehaviourArgs {
		public static final Empty INSTANCE = new Empty();
	}
	
	private CommonArgs() {
	}
}
