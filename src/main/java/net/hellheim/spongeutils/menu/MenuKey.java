package net.hellheim.spongeutils.menu;

/**
 * Used to store data within {@link Menu}.
 * 
 * @param <T> The type of key
 */
public final class MenuKey<T> {
	
	private MenuKey() {
	}
	
	public static <T> MenuKey<T> key() {
		return new MenuKey<>();
	}
}
