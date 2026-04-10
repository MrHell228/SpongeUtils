package net.hellheim.spongetools.common.event.listener;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Listener;

import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.common.event.RegisterEntityDisplayEventImpl;
import net.hellheim.spongetools.custom.type.entity.ModeledEntity;
import net.hellheim.spongetools.event.RegisterEntityDisplayEvent;

public final class EntityEventListener {
	
	public static void fireEvents(final Game game, final Cause cause) {
		final RegisterEntityDisplayEventImpl event = new RegisterEntityDisplayEventImpl(cause, game);
		Sponge.eventManager().post(event);
		event.display().forEach((type, display) -> ((EntityTypeBridge) type).spongetools$bridge$setDisplay(display));
	}
	
	@Listener
	public void registerDisplay(final RegisterEntityDisplayEvent event) {
		ModeledEntity.registry().get().stream().forEach(model -> event.type(model.get()).add(model.display()));
	}
}
