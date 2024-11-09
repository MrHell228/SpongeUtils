package net.hellheim.spongeutils.menu.pagination;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.item.inventory.ItemStack;

/**
 * The configuration for button in {@link Pagination}.
 */
public class PaginationButton {
	
	private int index;
	private Supplier<ItemStack> buttonProvider;
	private Predicate<ItemStack> isButton;
	
	protected PaginationButton(
		final int index, final Supplier<ItemStack> buttonProvider, final Predicate<ItemStack> isButton
	) {
		this.index = index;
		this.buttonProvider = buttonProvider;
		this.isButton = isButton;
	}
	
	/**
	 * Constructs and returns {@link PaginationButton} with the given parameters.
	 * 
	 * @param index The index of the button in inventory
	 * @param buttonProvider The provider of the button's {@link ItemStack}
	 * @param isButton The predicate to check if {@link ItemStack} represents the button
	 * @return The constructed {@link PaginationButton}
	 */
	public static PaginationButton of(
			final int index, final Supplier<ItemStack> buttonProvider, final Predicate<ItemStack> isButton
	) {
		return new PaginationButton(index, buttonProvider, isButton);
	}
	
	/**
	 * Constructs and returns {@link PaginationButton} represented by {@link ItemStack}.
	 * 
	 * @param index The index of the button in inventory
	 * @param button The ItemStack
	 * @return The constructed {@link PaginationButton}
	 */
	public static PaginationButton of(final int index, final ItemStack button) {
		final ItemStack buttonCopy = button.copy();
		return new PaginationButton(index, buttonCopy::copy, buttonCopy::equalTo);
	}
	
	/**
	 * Returns the index of this button in inventory.
	 * 
	 * @return The index of this button in inventory
	 */
	public int index() {
		return this.index;
	}
	
	/**
	 * Returns the {@link ItemStack} that represents this button.
	 * 
	 * @return The ItemStack that represents this button.
	 */
	public ItemStack button() {
		return this.buttonProvider.get();
	}
	
	/**
	 * Returns whether the given stack represents this button.
	 * 
	 * @param stack The stack to test
	 * @return True if the given stack represents this button
	 */
	public boolean test(final ItemStack stack) {
		return this.isButton.test(stack);
	}
}
