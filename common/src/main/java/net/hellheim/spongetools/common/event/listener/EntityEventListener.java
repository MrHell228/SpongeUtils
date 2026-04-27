package net.hellheim.spongetools.common.event.listener;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Listener;

import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.common.event.ModifyRegistryValueEventImpl;
import net.hellheim.spongetools.common.event.RegisterEntityDisplayEventImpl;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.type.entity.ModeledEntity;
import net.hellheim.spongetools.event.RegisterEntityDisplayEvent;
import net.hellheim.spongetools.mixin.world.item.alchemy.PotionAccessor;

public final class EntityEventListener {
	
	public static void fireEvents(final Game game, final Cause cause) {
		final RegisterEntityDisplayEventImpl displayEvent = new RegisterEntityDisplayEventImpl(cause, game);
		Sponge.eventManager().post(displayEvent);
		displayEvent.display().forEach((type, display) ->
			((EntityTypeBridge) type).spongetools$bridge$setDisplay(display));
		
		final var effectEvent = new ModifyRegistryValueEventImpl.ModifyPotionEffectImpl(cause, game);
		Sponge.eventManager().post(effectEvent);
		effectEvent.steps.forEach((type, step) -> {
			((MobEffectBridge) type).spongetools$bridge$setModifierTemplates(step.attributes);
			((MobEffectBridge) type).spongetools$bridge$setSound(step.sound);
		});
		
		final var potionEvent = new ModifyRegistryValueEventImpl.ModifyPotionImpl(cause, game);
		Sponge.eventManager().post(potionEvent);
		potionEvent.steps.forEach((type, step) -> {
			((PotionAccessor) type).accessor$effects(step.effects.stream()
					.map(Converter::asVanilla)
					.toList());
		});
	}
	
	@Listener
	public void registerDisplay(final RegisterEntityDisplayEvent event) {
		ModeledEntity.registry().get().stream().forEach(model -> event.type(model.get()).add(model.display()));
	}
}
