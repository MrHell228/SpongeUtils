package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;

@FunctionalInterface
public interface EntityBehaviour<R, A extends BehaviourArgs> extends Behaviour<R, A> {
	
	interface JumpStart extends EntityBehaviour<Void, JumpStart.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.power());
			return null;
		}
		
		void call(int power);
		
		interface Args extends BehaviourArgs {
			
			int power();
			
			Args withPower(int power);
		}
	}
	
	interface OpenInventory extends EntityBehaviour<Void, OpenInventory.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.player());
			return null;
		}
		
		void call(ServerPlayer player);
		
		interface Args extends BehaviourArgs {
			
			ServerPlayer player();
			
			Args withPlayer(ServerPlayer player);
		}
	}
	
	interface PerformRangedAttack extends EntityBehaviour<Void, PerformRangedAttack.Args> {
		
		@Override
		default Void call(final Args args) {
			this.call(args.target(), args.velocity());
			return null;
		}
		
		void call(Living target, double velocity);
		
		interface Args extends BehaviourArgs {
			
			Living target();
			
			double velocity();
			
			Args withTarget(Living target);
			
			Args withVelocity(double velocity);
		}
	}
}
