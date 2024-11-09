package net.hellheim.spongeutils.menu.handler;

import java.util.Objects;

import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.item.inventory.menu.handler.SlotClickHandler;

import net.hellheim.spongeutils.function.BooleanBinaryOperator;
import net.hellheim.spongeutils.menu.Menu;

@FunctionalInterface
public interface MenuSlotClickHandler<M extends Menu<M>> extends MenuInventoryCallbackHandler<M> {
	
	/**
	 * See {@link SlotClickHandler#handle(Cause, Container, Slot, int, ClickType)}.
	 */
	boolean handle(M menu, Cause cause, Container container, Slot slot, int slotIndex, ClickType<?> clickType);
	
	default MenuSlotClickHandler<M> andThen(final MenuSlotClickHandler<M> after, final BooleanBinaryOperator combiner) {
		Objects.requireNonNull(after);
		Objects.requireNonNull(combiner);
		return (menu, cause, container, slot, slotIndex, clickType) -> {
			final boolean b1 = this.handle(menu, cause, container, slot, slotIndex, clickType);
			final boolean b2 = after.handle(menu, cause, container, slot, slotIndex, clickType);
			return combiner.applyAsBoolean(b1, b2);
		};
	}
	
	@Override
	default SlotClickHandler asDefaultHandler(final M menu) {
		return (cause, container, slot, slotIndex, clickType)
				-> this.handle(menu, cause, container, slot, slotIndex, clickType);
	}
	
	static <M extends Menu<M>> MenuSlotClickHandler<M> asCustomHandler(final SlotClickHandler handler) {
		Objects.requireNonNull(handler);
		return (menu, cause, container, slot, slotIndex, clickType)
				-> handler.handle(cause, container, slot, slotIndex, clickType);
	}
}
