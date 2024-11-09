package net.hellheim.spongeutils.object;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.spongepowered.math.vector.Vector2i;

public class InventoryOperatorImpl implements InventoryOperator {
	
	protected int height = -1;
	protected int width = -1;
	
	protected InventoryOperatorImpl() {
	}
	
	// InventoryOperator
	
	@Override
	public InventoryOperator defineGrid(final int height, final int width) {
		Validate.isTrue(height >= 0, "Height must not be negative");
		Validate.isTrue(width >= 0, "Width must not be negative");
		this.height = height;
		this.width = width;
		return this;
	}
	
	@Override
	public InventoryOperator defineGrid(final Vector2i size) {
		Objects.requireNonNull(size, "size");
		return this.defineGrid(size.x(), size.y());
	}
	
	protected int posToIndex(final Vector2i pos) {
		return this.posToIndex(pos.x(), pos.y());
	}
	
	protected int posToIndex(final int x, final int y) {
        Validate.isTrue(x <= this.height, "Target inventory height is too small: " + this.height + " < " + x);
        Validate.isTrue(y <= this.width, "Target inventory width is too small: " + this.width + " < " + y);
		return x * this.width + y;
	}
	
	protected Vector2i indexToPos(final int index) {
		Validate.isTrue(index <= this.width * this.height, "Target inventory is too small: " + this.width * this.height + " < " + index);
		final int x = index / this.width;
		final int y = index % this.width;
		return new Vector2i(x, y);
	}
	
	protected boolean isSizeSet() {
		return this.height != -1 || this.width != -1;
	}
}
