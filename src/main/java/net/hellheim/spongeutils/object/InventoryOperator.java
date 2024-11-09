package net.hellheim.spongeutils.object;

import org.spongepowered.math.vector.Vector2i;

public interface InventoryOperator {
	
	/**
	 * Defines size of inventory as if it was grid-like inventory, allowing to use grid-based methods.
	 * 
	 * @param height The height of inventory
	 * @param width The width of inventory
	 * 
	 * @return Grid-based inventory operator
	 */
	InventoryOperator defineGrid(int height, int width);
	
	/**
	 * Defines size of inventory as if it was grid-like inventory, allowing to use grid-based methods.
	 * 
	 * <p>The format of the size is represented by:<br>
	 * {@code x -> height}, {@code y -> width}</p>
	 * 
	 * @param size The size of inventory
	 * 
	 * @return Grid-based inventory operator
	 */
	InventoryOperator defineGrid(Vector2i size);
}
