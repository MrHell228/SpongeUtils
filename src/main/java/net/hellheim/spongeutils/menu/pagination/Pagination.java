package net.hellheim.spongeutils.menu.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.menu.Menu;

/**
 * Represents abstract pagination in {@link Menu}.
 * 
 * @see SinglePagination
 * @see MultiplePagination
 */
public interface Pagination {
	
	/**
	 * Decreases the current page by one and calls {@link #update()}.
	 */
	void back();
	
	/**
	 * Increases the current page by one and calls {@link #update()}.
	 */
	void next();
	
	/**
	 * Sets the current page to the given page and calls {@link #update()}.
	 * 
	 * @param page Tha page to set
	 */
	void page(int page);
	
	/**
	 * Fills current page and adds buttons, is necessary.
	 */
	void update();
	
	/**
	 * Refreshes elements in this pagination, clamps the current page and calls {@link #update()}.
	 */
	void refresh();
	
	/**
	 * Handles button click and returns whether the given stack is button in this pagination.
	 * 
	 * @param stack The clicked ItemStack
	 * @return True if the given stack is button in this pagination
	 */
	boolean handleButtonClick(ItemStack stack);
	
	
	
	/**
	 * Constructs and returns {@link SinglePagination} with the given parameters.
	 * 
	 * @param <M> The {@link Menu}
	 * @param <T> The type of elements
	 * @param menu The menu
	 * @param inventory The inventory
	 * @param cfg The config
	 * @param content The content
	 * @return The constructed {@link SinglePagination}
	 */
	static <M extends Menu<M>, T> SinglePagination<M, T> single(
		final M menu, final Inventory inventory, final PaginationConfig cfg, final PageContentProvider<M, T> content
	) {
		return new SinglePagination<>(menu, inventory, cfg, content);
	}
	
	/**
	 * Constructs and returns {@link MultiplePagination} with the given {@link Pagination}s.
	 * 
	 * @param paginations The paginations
	 * @return The constructed {@link MultiplePagination}
	 */
	static MultiplePagination multiple(final List<Pagination> paginations) {
		return new MultiplePagination(paginations);
	}
	
	/**
	 * Constructs and returns {@link MultiplePagination} with
	 * {@link SinglePagination}s with the given parameters.
	 * 
	 * @param <M> The {@link Menu}
	 * @param menu The menu
	 * @param inventory The inventory
	 * @param size The amount of paginations
	 * @param cfg The config provider
	 * @param content The content provider
	 * @return The constructed {@link MultiplePagination}
	 */
	static <M extends Menu<M>> MultiplePagination multiple(
		final M menu, final Inventory inventory, final int size,
		final IntFunction<PaginationConfig> cfg, final IntFunction<PageContentProvider<M, ?>> content
	) {
		final List<Pagination> paginations = new ArrayList<>();
		for (int page = 0; page < size; ++page) {
			paginations.add(single(menu, inventory, cfg.apply(page), content.apply(page)));
		}
		return new MultiplePagination(paginations);
	}
	
	/**
	 * Constructs and returns {@link MultiplePagination} with
	 * {@link SinglePagination}s with the given parameters.
	 * 
	 * @param <M> The {@link Menu}
	 * @param menu The menu
	 * @param inventory The inventory
	 * @param cfgs The configs
	 * @param contents The content providers
	 * @return The constructed {@link MultiplePagination}
	 * @throws IllegalArgumentException If sizes of configs and contents are not equal
	 */
	static <M extends Menu<M>> MultiplePagination multiple(
		final M menu, final Inventory inventory,
		final List<PaginationConfig> cfgs, final List<? extends PageContentProvider<M, ?>> contents
	) {
		if (cfgs.size() != contents.size()) {
			throw new IllegalArgumentException(String.format(
					"Configs size does not match contents size (%s and %s)", cfgs.size(), contents.size()
					));
		}
		
		final int size = cfgs.size();
		return multiple(menu, inventory, size, i -> cfgs.get(i), i -> contents.get(i));
	}
}
