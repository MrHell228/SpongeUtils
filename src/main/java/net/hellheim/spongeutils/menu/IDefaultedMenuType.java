package net.hellheim.spongeutils.menu;

import java.util.function.Predicate;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ContainerType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.util.Tuple;

import net.hellheim.spongeutils.menu.handler.MenuInventoryCallbackHandler;
import net.hellheim.spongeutils.menu.pagination.Pagination;
import net.hellheim.spongeutils.menu.pagination.PaginationInitStage;
import net.kyori.adventure.text.Component;

/**
 * {@link IMenuType} which configurations are represented by {@link MenuTypeProperties}. <br>
 * <br>
 * For default implementation, see {@link DefaultedMenuType}.
 * 
 * @param <M> The {@link Menu}
 */
public interface IDefaultedMenuType<M extends Menu<M>> extends IMenuType<M> {
	
	/**
	 * Returns {@link MenuTypeProperties} of this {@link IDefaultedMenuType}
	 * that are used to define all of {@link IMenuType} methods.
	 * 
	 * @return The {@link MenuTypeProperties}
	 */
	MenuTypeProperties<M> properties();
	
	@Override
	default @Nullable IMenuType<M> defaultPrevious() {
		return this.properties().defaultPrevious();
	}
	
	@Override
	default boolean test(final M menu) {
		for (final Tuple<Predicate<M>, MenuAction<M>> test : this.properties().tests()) {
			if (!test.first().test(menu)) {
				test.second().accept(menu);
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	default @Nullable ContainerType inventoryType() {
		return this.properties().inventoryType();
	}
	
	@Override
	default ViewableInventory inventory(final M menu) {
		return this.properties().inventoryProvider().apply(menu);
	}
	
	@Override
	default boolean isReadOnly(final M menu) {
		return this.properties().readOnlyProvider().test(menu);
	}
	
	@Override
	default Iterable<MenuInventoryCallbackHandler<M>> handlers() {
		return this.properties().handlers();
	}
	
	@Override
	default Component title(final M menu) {
		return this.properties().titleProvider().apply(menu);
	}
	
	@Override
	default void finalize(final M menu, final Container container) {
		this.properties().finalizer().accept(menu, container);
	}
	
	@Override
	default PaginationInitStage paginationStage() {
		return this.properties().paginationStage();
	}
	
	@Override
	default Pagination pagination(final M menu, final Inventory inventory) {
		if (this.paginationStage() == PaginationInitStage.NONE) {
			throw new UnsupportedOperationException("This menu type does not support pagination.");
		}
		
		return this.properties().paginationProvider().apply(menu, inventory);
	}
}
