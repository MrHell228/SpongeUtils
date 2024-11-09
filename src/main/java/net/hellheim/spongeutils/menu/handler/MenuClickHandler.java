package net.hellheim.spongeutils.menu.handler;

import java.util.Objects;

import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.item.inventory.menu.handler.ClickHandler;

import net.hellheim.spongeutils.function.BooleanBinaryOperator;
import net.hellheim.spongeutils.menu.Menu;

@FunctionalInterface
public interface MenuClickHandler<M extends Menu<M>> extends MenuInventoryCallbackHandler<M> {
	
	/**
	 * See {@link ClickHandler#handle(Cause, Container, ClickType)}.
	 */
	boolean handle(M menu, Cause cause, Container container, ClickType<?> clickType);
	
	default MenuClickHandler<M> andThen(final MenuClickHandler<M> after, final BooleanBinaryOperator combiner) {
		Objects.requireNonNull(after);
		Objects.requireNonNull(combiner);
		return (menu, cause, container, clickType) -> {
			final boolean b1 = this.handle(menu, cause, container, clickType);
			final boolean b2 = after.handle(menu, cause, container, clickType);
			return combiner.applyAsBoolean(b1, b2);
		};
	}
	
	@Override
	default ClickHandler asDefaultHandler(final M menu) {
		return (cause, container, clickType)
				-> this.handle(menu, cause, container, clickType);
	}
	
	static <M extends Menu<M>> MenuClickHandler<M> asCustomHandler(final ClickHandler handler) {
		Objects.requireNonNull(handler);
		return (menu, cause, container, clickType)
				-> handler.handle(cause, container, clickType);
	}
}
