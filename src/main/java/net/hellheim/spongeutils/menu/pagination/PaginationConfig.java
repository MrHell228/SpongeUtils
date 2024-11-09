package net.hellheim.spongeutils.menu.pagination;

import java.util.function.IntUnaryOperator;

/**
 * The configuration of {@link SinglePagination}.
 */
public class PaginationConfig {
	
	private final int pageSize;
	private final IntUnaryOperator indexMapper;
	
	private final PaginationButton back;
	private final PaginationButton next;
	
	public PaginationConfig(
		final int pageSize, final IntUnaryOperator indexMapper,
		final PaginationButton back, final PaginationButton next
	) {
		this.pageSize = pageSize;
		this.indexMapper = indexMapper;
		
		this.back = back;
		this.next = next;
	}
	
	/**
	 * Returns the size of the page.
	 * 
	 * @return The size of the page.
	 */
	public int pageSize() {
		return this.pageSize;
	}
	
	/**
	 * Returns the index of the element in inventory for provided index of the element on page.
	 * 
	 * @param index The index of the element on page
	 * @return The index of the element in inventory
	 * @throws IllegalArgumentException If index is not in [0, {@link #pageSize()}) range
	 */
	public int mapIndex(final int index) {
		if (index < 0 || index >= this.pageSize) {
			throw new IllegalArgumentException(String.format("", this.pageSize, index));
		}
		
		return this.indexMapper.applyAsInt(index);
	}
	
	/**
	 * Returns the "back" button configuration.
	 * 
	 * @return The "back" button configuration
	 */
	public PaginationButton backButton() {
		return this.back;
	}
	
	/**
	 * Returns the "next" button configuration.
	 * 
	 * @return The "next" button configuration
	 */
	public PaginationButton nextButton() {
		return this.next;
	}
}
