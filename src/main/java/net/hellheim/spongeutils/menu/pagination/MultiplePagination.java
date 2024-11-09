package net.hellheim.spongeutils.menu.pagination;

import java.util.List;
import java.util.function.Consumer;

import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.menu.Menu;

/**
 * Represents {@link Pagination} in {@link Menu} containing other paginations, usually {@link SinglePagination}s.
 */
public class MultiplePagination implements Pagination {
	
	private final List<Pagination> paginations;
	
	protected MultiplePagination(final List<Pagination> paginations) {
		this.paginations = paginations;
	}
	
	/**
	 * Returns the amount of paginations.
	 * 
	 * @return The amount of paginations
	 */
	public int size() {
		return this.paginations.size();
	}
	
	/**
	 * Returns the pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 * @return The pagination
	 */
	public Pagination get(final int ordinal) {
		return this.paginations.get(ordinal);
	}
	
	/**
	 * Returns the pagination at the given ordinal of the given {@link PaginationType}.
	 * 
	 * @param <P> The type of pagination to return
	 * @param ordinal The ordinal of pagination
	 * @param type The pagination type
	 * @return The pagination
	 * @throws ClassCastException If pagination cannot be casted to provided type
	 */
	public <P extends Pagination> P get(final int ordinal, final PaginationType<P> type) {
		@SuppressWarnings("unchecked")
		final P castedPagination = (P) this.get(ordinal);
		return castedPagination;
	}
	
	/**
	 * Performs the given action for each pagination.
	 * 
	 * @param action The action to be performed
	 */
	public void forEach(final Consumer<? super Pagination> action) {
		this.paginations.forEach(action);
	}
	
	/**
	 * Calls {@link Pagination#back()} on pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 */
	public void back(final int ordinal) {
		this.get(ordinal).back();
	}
	
	/**
	 * Calls {@link Pagination#next()} on pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 */
	public void next(final int ordinal) {
		this.get(ordinal).next();
	}
	
	/**
	 * Calls {@link Pagination#page(int)} on pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 */
	public void page(final int ordinal, final int page) {
		this.get(ordinal).page(page);
	}
	
	/**
	 * Calls {@link Pagination#update()} on pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 */
	public void update(final int ordinal) {
		this.get(ordinal).update();
	}
	
	/**
	 * Calls {@link Pagination#refresh()} on pagination at the given ordinal.
	 * 
	 * @param ordinal The ordinal of pagination
	 */
	public void refresh(final int ordinal) {
		this.get(ordinal).refresh();
	}
	
	@Override
	public void back() {
		this.forEach(Pagination::back);
	}
	
	@Override
	public void next() {
		this.forEach(Pagination::next);
	}
	
	@Override
	public void page(final int page) {
		this.forEach(p -> p.page(page));
	}
	
	@Override
	public void update() {
		this.forEach(Pagination::update);
	}
	
	@Override
	public void refresh() {
		this.forEach(Pagination::refresh);
	}
	
	@Override
	public boolean handleButtonClick(final ItemStack stack) {
		for (final Pagination p : this.paginations) {
			if (p.handleButtonClick(stack)) {
				return true;
			}
		}
		
		return false;
	}
}
