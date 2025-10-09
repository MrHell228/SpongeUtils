package net.hellheim.spongetools.common.event.listener;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import net.hellheim.spongetools.custom.type.item.data.CustomConsumeEffect;

public final class ItemEventListener {
	
	@Listener(order = Order.POST)
	public void onUseFinish(
		final UseItemStackEvent event,
		final @First Living entity,
		final @Getter("itemStackInUse") ItemStackSnapshot stack
	) {
		stack.get(CustomConsumeEffect.dataKey()).ifPresent(
				effects -> effects.forEach(
						effect -> effect.apply(entity, stack)));
	}
}
