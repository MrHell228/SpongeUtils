package net.hellheim.spongeutils.menu.handler;

import java.util.Objects;

import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.item.inventory.menu.handler.KeySwapHandler;

import net.hellheim.spongeutils.function.BooleanBinaryOperator;
import net.hellheim.spongeutils.menu.Menu;

@FunctionalInterface
public interface MenuKeySwapHandler<M extends Menu<M>> extends MenuInventoryCallbackHandler<M> {
	
	/**
	 * See {@link KeySwapHandler#handle(Cause, Container, Slot, int, ClickType, Slot)}.
	 */
	boolean handle(M menu, Cause cause, Container container, Slot slot, int slotIndex, ClickType<?> clickType, Slot slot2);
	
	default MenuKeySwapHandler<M> andThen(final MenuKeySwapHandler<M> after, final BooleanBinaryOperator combiner) {
		Objects.requireNonNull(after);
		Objects.requireNonNull(combiner);
		return (menu, cause, container, slot, slotIndex, clickType, slot2) -> {
			final boolean b1 = this.handle(menu, cause, container, slot, slotIndex, clickType, slot2);
			final boolean b2 = after.handle(menu, cause, container, slot, slotIndex, clickType, slot2);
			return combiner.applyAsBoolean(b1, b2);
		};
	}
	
	@Override
	default KeySwapHandler asDefaultHandler(final M menu) {
		return (cause, container, slot, slotIndex, clickType, slot2)
				-> this.handle(menu, cause, container, slot, slotIndex, clickType, slot2);
	}
	
	static <M extends Menu<M>> MenuKeySwapHandler<M> asCustomHandler(final KeySwapHandler handler) {
		Objects.requireNonNull(handler);
		return (menu, cause, container, slot, slotIndex, clickType, slot2)
				-> handler.handle(cause, container, slot, slotIndex, clickType, slot2);
	}
}
