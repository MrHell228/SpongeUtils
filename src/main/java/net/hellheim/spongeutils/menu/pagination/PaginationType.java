package net.hellheim.spongeutils.menu.pagination;

import net.hellheim.spongeutils.menu.Menu;

/**
 * Used as a key to get the {@link Pagination} of the given type.
 * 
 * @see {@link Menu#pagination(PaginationType)}
 * @see {@link Menu#requirePagination(PaginationType)}
 * @see {@link MultiplePagination#get(int, PaginationType)}
 * 
 * @param <P> The type of pagination
 */
public final class PaginationType<P extends Pagination> {
	
	/**
	 * The type for abstract {@link SinglePagination}.
	 */
	public static final PaginationType<SinglePagination<?, ?>> SINGLE = create();
	
	/**
	 * The type for {@link MultiplePagination}.
	 */
	public static final PaginationType<MultiplePagination> MULTIPLE = create();
	
	/**
	 * Constructs and returns the {@link PaginationType}.
	 * 
	 * @param <P> The type of pagination
	 * @return The constructed {@link PaginationType}
	 */
	public static <P extends Pagination> PaginationType<P> create() {
		return new PaginationType<>();
	}
	
	private PaginationType() {
	}
}
