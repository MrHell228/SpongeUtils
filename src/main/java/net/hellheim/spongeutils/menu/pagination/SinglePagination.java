package net.hellheim.spongeutils.menu.pagination;

import java.util.List;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import net.hellheim.spongeutils.menu.Menu;

/**
 * Represents simple {@link Pagination} in {@link Menu} that contains elements of one type.
 * 
 * @param <M> The {@link Menu}
 * @param <T> The type of elements
 */
public class SinglePagination<M extends Menu<M>, T> implements Pagination {
	
	private final M menu;
	private final Inventory inventory;
	
	private final PaginationConfig cfg;
	private final PageContentProvider<M, T> content;
	
	private List<T> elements;
	private int page = 0;
	//private Map<Integer, List<ItemStack>> filledPages = new TreeMap<>();
	
	private @MonotonicNonNull ItemStack originalPrevStack = null;
	private @MonotonicNonNull ItemStack originalNextStack = null;
	
	protected SinglePagination(
		final M menu, final Inventory inventory, final PaginationConfig cfg, final PageContentProvider<M, T> content
	) {
		this.menu = menu;
		this.inventory = inventory;
		
		this.cfg = cfg;
		this.content = content;
		
		this.elements = this.content.elements(this.menu);
	}
	
	/**
	 * Returns whether "back" button should be displayed.
	 * 
	 * @return True if "back" button should be displayed
	 */
	public boolean shouldShowBack() {
		return this.page > 0;
	}
	
	/**
	 * Returns whether "next" button should be displayed.
	 * 
	 * @return True if "next" button should be displayed
	 */
	public boolean shouldShowNext() {
		return this.page < 0 || this.elements.size() > (this.cfg.pageSize() * (this.page + 1));
	}
	
	/**
	 * Returns the current page.
	 * 
	 * @return The current page
	 */
	public int page() {
		return this.page;
	}
	
	@Override
	public void back() {
		this.page(this.page - 1);
	}
	
	@Override
	public void next() {
		this.page(this.page + 1);
	}
	
	@Override
	public void page(final int page) {
		this.page = page;
		this.update();
	}
	
	protected int[] getIndizes() {
		final int size = this.elements.size(), pgSize = this.cfg.pageSize();
		
		final int[] indexes = new int[Math.min(pgSize, Math.max(size - (pgSize * this.page), 0))];
		for (int j = 0, i = pgSize * this.page, end = i + pgSize; (i < end) && (i < size); ++j, ++i) {
			indexes[j] = i;
		}
		return indexes;
	}
	
	@Override
	public void update() {
		// Sets elements on page
		final int[] indizes = this.getIndizes();
		final Function<T, ItemStack> elementStack = this.content.elementStackProvider(this.menu);
		for (int i = 0; i < indizes.length; ++i) {
			this.inventory.set(this.cfg.mapIndex(i), elementStack.apply(this.elements.get(indizes[i])));
		}
		
		// Sets empty stacks on page
		if (indizes.length < this.cfg.pageSize()) {
			final ItemStack emptyStack = this.content.emptyStack(this.menu);
			for (int i = indizes.length; i < this.cfg.pageSize(); ++i) {
				this.inventory.set(this.cfg.mapIndex(i), emptyStack);
			}
		}
		
		// Sets PREVIOUS button if needed
		final int backIndex = this.cfg.backButton().index();
		if (this.shouldShowBack()) {
			if (this.originalPrevStack == null) {
				this.originalPrevStack = this.inventory.peekAt(backIndex).get();
			}
			
			this.inventory.set(backIndex, this.cfg.backButton().button());
		} else if (this.originalPrevStack != null) {
			this.inventory.set(backIndex, this.originalPrevStack);
		} else {
			// TODO throw? set empty stack?
		}
		
		// Sets NEXT button if needed
		final int nextIndex = this.cfg.nextButton().index();
		if (this.shouldShowNext()) {
			if (this.originalNextStack == null) {
				this.originalNextStack = this.inventory.peekAt(nextIndex).get();
			}
			
			this.inventory.set(nextIndex, this.cfg.nextButton().button());
		} else if (this.originalNextStack != null) {
			this.inventory.set(nextIndex, this.originalNextStack);
		} else {
			// TODO throw? set empty stack?
		}
	}
	
	@Override
	public void refresh() {
		this.elements = this.content.elements(this.menu);
		this.page = Math.min(this.page, (this.elements.size()-1) / this.cfg.pageSize());
		this.update();
	}
	
	@Override
	public boolean handleButtonClick(final ItemStack stack) {
		if (this.cfg.backButton().test(stack)) {
			this.back();
			return true;
		} else if (this.cfg.nextButton().test(stack)) {
			this.next();
			return true;
		} else {
			return false;
		}
	}
}
