package net.hellheim.spongetools.custom.type.entity;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.Angerable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Leashable;
import org.spongepowered.api.entity.Ranger;
import org.spongepowered.api.entity.Saddleable;
import org.spongepowered.api.entity.ai.goal.Goal;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.item.ItemTypes;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.custom.behaviour.type.EntityBehaviours;

public final class EntityFlags {
	
	/**
	 * Makes {@link Entity} an {@link Angerable}. <br>
	 * Gets notification when player dies, used in some {@link Goal}s.
	 */
	public static final ResourceKey ANGERABLE = SpongeTools.key("angerable");
	
	/**
	 * Makes {@link Entity} a Bucketable. TODO add interface <br>
	 * Allows using some bucket-pickup logic.
	 */
	public static final ResourceKey BUCKETABLE = SpongeTools.key("bucketable");
	
	/**
	 * Makes {@link Entity} a {@link Hostile}. <br>
	 * Makes entity targetable by golems, conduit (and sometimes shulker). Cannot be leashed by default.
	 */
	public static final ResourceKey HOSTILE = SpongeTools.key("hostile");
	
	/**
	 * Makes {@link Entity} an ItemSteerable. TODO add interface <br>
	 * Allows using FoonOnAStick item to perform boost while riding the entity.
	 */
	public static final ResourceKey ITEM_STEERABLE = SpongeTools.key("item_steerable");
	
	/**
	 * Makes {@link Entity} a {@link Leashable}. <br>
	 * Allows interaction with {@link ItemTypes#LEAD}.
	 */
	public static final ResourceKey LEASHABLE = SpongeTools.key("leashable");
	
	/**
	 * Makes {@link Entity} a {@link Ranger}. <br>
	 * Allows using some {@link Goal}s. <br.
	 * Supported behaviour: <br>
	 * - {@link EntityBehaviours#PERFORM_RANGED_ATTACK}
	 */
	public static final ResourceKey RANGER = SpongeTools.key("ranger");
	
	/**
	 * Makes {@link Entity} an RideableInventory. TODO add interface <br>
	 * Allows riding player to open entity inventory with 'E' button. <br>
	 * Supported behaviour: <br>
	 * - {@link EntityBehaviours#OPEN_INVENTORY}
	 * 
	 * @implNote player MUST ride vehicle that implements it client-side (boats/horses)
	 */
	public static final ResourceKey RIDEABLE_INVENTORY = SpongeTools.key("rideable_inventory");
	
	/**
	 * Makes {@link Entity} a RideableJumper. TODO add interface <br>
	 * Adds riding player jump strength bar on client. <br>
	 * Supported behaviour: <br>
	 * - {@link EntityBehaviours#JUMP_READY} <br>
	 * - {@link EntityBehaviours#JUMP_START}
	 * 
	 * @implNote player MUST ride vehicle that implements it client-side (horses)
	 */
	public static final ResourceKey RIDEABLE_JUMPER = SpongeTools.key("rideable_jumper");
	
	/**
	 * Makes {@link Entity} a {@link Saddleable}. <br>
	 * Handles hand and dispenser interactions with saddle.
	 */
	public static final ResourceKey SADDLEABLE = SpongeTools.key("saddleable");
	
	private EntityFlags() {
	}
}
