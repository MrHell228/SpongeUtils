package net.hellheim.spongetools.custom.type.entity;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;

public final class EntityBehaviours {
	
	public static final BehaviourType<Behaviour.SimpleBoolean> JUMP_READY = BehaviourType.of(SpongeTools.key("jump_ready"));
	
	public static final BehaviourType<EntityBehaviour.JumpStart> JUMP_START = BehaviourType.of(SpongeTools.key("jump_start"));
	
	public static final BehaviourType<EntityBehaviour.OpenInventory> OPEN_INVENTORY = BehaviourType.of(SpongeTools.key("open_inventory"));
	
	public static final BehaviourType<EntityBehaviour.PerformRangedAttack> PERFORM_RANGED_ATTACK = BehaviourType.of(SpongeTools.key("perform_ranged_attack"));
	
	public static final BehaviourType<Behaviour.SimpleAction> REGISTER_GOALS = BehaviourType.of(SpongeTools.key("register_goals"));
	
	private EntityBehaviours() {
	}
}
