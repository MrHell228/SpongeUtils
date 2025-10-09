package net.hellheim.spongetools.custom.behaviour.util;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3i;

/**
 * Represents the context of the item usage.
 */
public interface UseContext {
	
	/**
	 * Returns the player that used item, if present.
	 * 
	 * @return The player that used item, if present
	 */
	Optional<Player> player();
	
	/**
	 * Returns the used hand.
	 * 
	 * @return The used hand
	 */
	HandType hand();
	
	/**
	 * Returns the hit result of the usage.
	 * 
	 * @return The hit result of the usage
	 */
	HitResult.BlockHitResult hit();
	
	/**
	 * Returns the world the item is used in.
	 * 
	 * @return The world the item is used in
	 */
	World<?, ?> world();
	
	/**
	 * Returns the used item. This item can be mutated.
	 * 
	 * @return The used item
	 */
	ItemStack item();
	
	/**
	 * Returns the position that is considered as true clicked position. <br>
	 * This may differ from the position of the {@link #hit()}.
	 * 
	 * @return The true clicked block position
	 */
	Vector3i clickedPosition();
	
	/**
	 * Returns the direction that is considered as true clicked direction. <br>
	 * This may differ from the direction of the {@link #hit()}.
	 * 
	 * @return The true clicked direction
	 */
	Direction clickedDirection();
	
	/**
	 * Returns the yaw rotation of the usage. <br>
	 * This may differ from the yaw of the {@link #player()}.
	 * 
	 * @return The true yaw of the usage
	 */
	double rotation();
	
	/**
	 * Returns the horizontal direction of the usage.
	 * 
	 * @return The horizontal direction of the usage
	 */
	Direction horizontalDirection();
	
	/**
	 * Returns whether the usage has active secondary use.
	 * 
	 * @return True if the usage has active secondary use
	 */
	boolean isSecondary();
	
	interface BlockPlace extends UseContext {
		
		boolean canPlace();
		
		boolean canReplaceClickedPosition();
		
		Direction nearestDirection();
		
		Direction nearestVerticalDirection();
		
		List<Direction> nearestDirections();
	}
	
	interface DirectionaBlockPlace extends BlockPlace {
		
		Direction baseDirection();
	}
}
