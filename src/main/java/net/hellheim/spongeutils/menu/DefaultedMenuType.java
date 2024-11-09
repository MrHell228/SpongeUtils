package net.hellheim.spongeutils.menu;

/**
 * Class-based {@link IDefaultedMenuType} implementation.
 * 
 * @param <M>
 */
public class DefaultedMenuType<M extends Menu<M>> implements IDefaultedMenuType<M> {
	
	private final MenuTypeProperties<M> properties;
	
	protected DefaultedMenuType(final MenuTypeProperties<M> properties) {
		this.properties = properties.asImmutable();
	}
	
	public static <M extends Menu<M>> DefaultedMenuType<M> of(final MenuTypeProperties<M> properties) {
		return new DefaultedMenuType<>(properties);
	}
	
	@Override
	public MenuTypeProperties<M> properties() {
		return this.properties;
	}
}
