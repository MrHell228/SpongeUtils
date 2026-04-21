package net.hellheim.spongetools.common.behaviour;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import net.hellheim.spongetools.custom.type.entity.EntityBehaviour;

public final class EntityArgs {
	
	public record JumpStart(int power)
			implements EntityBehaviour.JumpStart.Args {
		
		@Override
		public JumpStart withPower(final int power) {
			return new JumpStart(power);
		}
	}
	
	public record OpenInventory(ServerPlayer player)
			implements EntityBehaviour.OpenInventory.Args {
		
		@Override
		public OpenInventory withPlayer(final ServerPlayer player) {
			return new OpenInventory(player);
		}
	}
	
	public record PerformRangedAttack(Living target, double velocity)
			implements EntityBehaviour.PerformRangedAttack.Args {
		
		@Override
		public PerformRangedAttack withTarget(final Living target) {
			return new PerformRangedAttack(target, this.velocity);
		}
		
		@Override
		public PerformRangedAttack withVelocity(final double velocity) {
			return new PerformRangedAttack(this.target, velocity);
		}
	}
	
	private EntityArgs() {
	}
}
