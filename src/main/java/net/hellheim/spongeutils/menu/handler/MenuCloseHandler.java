package net.hellheim.spongeutils.menu.handler;

import java.util.Objects;

import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.menu.handler.CloseHandler;

import net.hellheim.spongeutils.menu.Menu;

@FunctionalInterface
public interface MenuCloseHandler<M extends Menu<M>> extends MenuInventoryCallbackHandler<M> {
	
	/**
	 * See {@link CloseHandler#handle(Cause, Container)}.
	 */
	void handle(M menu, Cause cause, Container container);
	
	default MenuCloseHandler<M> andThen(final MenuCloseHandler<M> after) {
		Objects.requireNonNull(after);
		return (menu, cause, container) -> {
			this.handle(menu, cause, container);
			after.handle(menu, cause, container);
		};
	}
	
	@Override
	default CloseHandler asDefaultHandler(final M menu) {
		return (cause, container) -> this.handle(menu, cause, container);
	}
	
	static <M extends Menu<M>> MenuCloseHandler<M> asCustomHandler(final CloseHandler handler) {
		Objects.requireNonNull(handler);
		return (menu, cause, container)
				-> handler.handle(cause, container);
	}
}
