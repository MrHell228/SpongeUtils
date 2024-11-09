package net.hellheim.spongeutils.menu.handler;

import java.util.Objects;

import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.menu.handler.SlotChangeHandler;

import net.hellheim.spongeutils.function.BooleanBinaryOperator;
import net.hellheim.spongeutils.menu.Menu;

@FunctionalInterface
public interface MenuSlotChangeHandler<M extends Menu<M>> extends MenuInventoryCallbackHandler<M> {
	
	/**
	 * See {@link SlotChangeHandler#handle(Cause, Container, Slot, int, ItemStackSnapshot, ItemStackSnapshot)}.
	 */
	boolean handle(M menu, Cause cause, Container container, Slot slot, int slotIndex, ItemStackSnapshot oldStack, ItemStackSnapshot newStack);
	
	default MenuSlotChangeHandler<M> andThen(final MenuSlotChangeHandler<M> after, final BooleanBinaryOperator combiner) {
		Objects.requireNonNull(after);
		Objects.requireNonNull(combiner);
		return (menu, cause, container, slot, slotIndex, oldStack, newStack) -> {
			final boolean b1 = this.handle(menu, cause, container, slot, slotIndex, oldStack, newStack);
			final boolean b2 = after.handle(menu, cause, container, slot, slotIndex, oldStack, newStack);
			return combiner.applyAsBoolean(b1, b2);
		};
	}
	
	@Override
	default SlotChangeHandler asDefaultHandler(final M menu) {
		return (cause, container, slot, slotIndex, oldStack, newStack)
				-> this.handle(menu, cause, container, slot, slotIndex, oldStack, newStack);
	}
	
	static <M extends Menu<M>> MenuSlotChangeHandler<M> asCustomHandler(final SlotChangeHandler handler) {
		Objects.requireNonNull(handler);
		return (menu, cause, container, slot, slotIndex, oldStack, newStack)
				-> handler.handle(cause, container, slot, slotIndex, oldStack, newStack);
	}
}
