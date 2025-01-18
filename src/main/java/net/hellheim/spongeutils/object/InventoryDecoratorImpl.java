package net.hellheim.spongeutils.object;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackLike;
import org.spongepowered.math.vector.Vector2i;

import net.hellheim.spongeutils.object.InventoryDecorator.Sized;
import net.hellheim.spongeutils.source.solid.item.IItemSource;

public class InventoryDecoratorImpl<T extends Inventory> extends InventoryOperatorImpl implements Sized<T> {
	
	private static final ItemStack EMPTY_STACK = ItemStack.empty();
	
	protected final T inventory;
	
	protected InventoryDecoratorImpl(final T inventory) {
		this.inventory = inventory;
	}
	
	// InventoryOperator
	
	@Override
	public InventoryDecoratorImpl<T> defineGrid(final int height, final int width) {
		super.defineGrid(height, width);
		return this;
	}
	
	@Override
	public InventoryDecoratorImpl<T> defineGrid(final Vector2i size) {
		super.defineGrid(size);
		return this;
	}
	
	@Override
	public T get() {
		return this.inventory;
	}
	
	// InventoryDecorator
	
	@Override
	public Sized<T> set(final int index, final IItemSource item) {
		return this.set(index, item.getAsItemStack());
	}
	
	@Override
	public Sized<T> set(int index, final ItemStackLike item) {
		if (index < 0) {
			index += this.inventory.capacity();
		}
		this.inventory.set(index, item);
		return this;
	}
	
	@Override
	public Sized<T> setEmpty(final int index) {
		return this.set(index, EMPTY_STACK);
	}
	
	// SizedInventoryDecorator
	
	@Override
	public Sized<T> set(final int row, final int column, final IItemSource item) {
		return this.set(row, column, item.getAsItemStack());
	}
	
	@Override
	public Sized<T> set(int row, int column, final ItemStackLike item) {
		if (row < 0) {
			row += this.height;
		}
		if (column < 0) {
			column += this.width;
		}
		return this.set(this.posToIndex(row, column), item);
	}
	
	@Override
	public Sized<T> setEmpty(final int row, final int column) {
		return this.set(row, column, EMPTY_STACK);
	}
	
	@Override
	public Sized<T> set(final Vector2i pos, final IItemSource item) {
		return this.set(pos.x(), pos.y(), item);
	}
	
	@Override
	public Sized<T> set(final Vector2i pos, final ItemStackLike item) {
		return this.set(pos.x(), pos.y(), item);
	}
	
	@Override
	public Sized<T> setEmpty(final Vector2i pos) {
		return this.setEmpty(pos.x(), pos.y());
	}
}
