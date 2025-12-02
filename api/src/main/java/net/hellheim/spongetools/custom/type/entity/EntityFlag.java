package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.entity.Aerial;
import org.spongepowered.api.entity.Angerable;
import org.spongepowered.api.entity.Leashable;
import org.spongepowered.api.entity.Ranger;
import org.spongepowered.api.entity.Saddleable;
import org.spongepowered.api.entity.ai.goal.Goal;
import org.spongepowered.api.entity.living.Hostile;

/**
 * <li> {@link Hostile} (Enemy) - targeted by snow golem, iron golem, conduit (and maybe shulker); by default cannot be leashed 
 * <li> {@link Ranger} (RangedAttackMob + (?) CrossbowAttackMob) - allows using some related {@link Goal}s
 * <li> {@link Leashable} - allows A LOT
 * <li> {@link Saddleable} - hand or dispenser interaction with saddle
 * <li> Shearable - hand or dispenser interaction with shears
 * <li> {@link Angerable} (NeutralMob) ? - used in some {@link Goal}s, gets notification when player dies
 * <li> {@link Aerial} (FlyingAnimal) ? - used to adjust movement in air
 * <li> Bucketable ? - allows using some bucket-pickup logic, probably not needed
 * <li> ItemSteerable - allows using FoonOnAStick item to perform boost while riding the entity
 * <li> PlayerRideableJumping - can listen to jump-control packets and adds jump strength bar to client, but
 * 				player MUST ride vehicle that implements it client-side (horses)
 * <li> HasCustomInventoryScreen - can open inventory to player when it press 'E', but
 * 				player MUST ride vehicle that implements it client-side (boats/horses)
 * 
 * 
 */
public interface EntityFlag {
	
}
