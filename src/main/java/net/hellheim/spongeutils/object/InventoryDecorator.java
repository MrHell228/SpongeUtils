package net.hellheim.spongeutils.object;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.math.vector.Vector2i;

import net.hellheim.spongeutils.source.solid.item.IItemSource;

public interface InventoryDecorator<T extends Inventory> extends InventoryOperator {
	
	static <T extends Inventory> InventoryDecorator<T> of(final T inventory) {
		return new InventoryDecoratorImpl<>(inventory);
	}
	
	@Override
	Sized<T> defineGrid(int height, int width);
	
	@Override
	Sized<T> defineGrid(Vector2i size);
	
	/**
	 * Sets the item for slot at the given index. <br>
	 * Negative index counts as index from the end of the inventory.
	 * 
	 * @param index The index
	 * @param item  The item
	 * 
	 * @return The decoration step
	 */
	InventoryDecorator<T> set(int index, IItemSource item);
	
	/**
	 * Sets the item for slot at the given index. <br>
	 * Negative index counts as index from the end of the inventory.
	 * 
	 * @param index The index
	 * @param item  The item
	 * 
	 * @return The decoration step
	 */
	InventoryDecorator<T> set(int index, ItemStackLike item);
	
	/**
	 * Sets {@link ItemStackSnapshot#empty()} for slot at the gievn index.
	 * Negative index counts as index from the end of the inventory.
	 * 
	 * @param index The index
	 * 
	 * @return The decoration step
	 */
	InventoryDecorator<T> setEmpty(int index);
	
	T get();
	
	
	static interface Sized<T extends Inventory> extends InventoryDecorator<T> {
		
		@Override
		Sized<T> set(int index, IItemSource item);
		
		@Override
		Sized<T> set(int index, ItemStackLike item);
		
		@Override
		Sized<T> setEmpty(int index);
		
		/**
		 * Sets the item for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param row    The row of position
		 * @param column The column of position
		 * @param item   The item
		 * 
		 * @return The decoration step
		 */
		Sized<T> set(int row, int column, IItemSource item);
		
		/**
		 * Sets the item for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param row    The row of position
		 * @param column The column of position
		 * @param item   The item
		 * 
		 * @return The decoration step
		 */
		Sized<T> set(int row, int column, ItemStackLike item);
		
		/**
		 * Sets {@link ItemStackSnapshot#empty()} for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param row    The row of position
		 * @param column The column of position
		 * 
		 * @return The decoration step
		 */
		Sized<T> setEmpty(int row, int column);
		
		/**
		 * Sets the item for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param pos  The position
		 * @param item The item
		 * 
		 * @return The decoration step
		 */
		Sized<T> set(Vector2i pos, IItemSource item);
		
		/**
		 * Sets the item for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param pos  The position
		 * @param item The item
		 * 
		 * @return The decoration step
		 */
		Sized<T> set(Vector2i pos, ItemStackLike item);
		
		/**
		 * Sets {@link ItemStackSnapshot#empty()} for slot at the given position. <br>
		 * Negative row counts as row from the end of the inventory. <br>
		 * Negative column counts as column from the end of the inventory.
		 * 
		 * @param pos The position
		 * 
		 * @return The decoration step
		 */
		Sized<T> setEmpty(Vector2i pos);
	}
}
