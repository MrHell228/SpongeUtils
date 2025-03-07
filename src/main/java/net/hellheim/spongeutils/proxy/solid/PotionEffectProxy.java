package net.hellheim.spongeutils.proxy.solid;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;

public interface PotionEffectProxy extends PotionEffectTypeProxy {
	
	PotionEffect getAsPotionEffect();
	
	@Override
	default PotionEffectType getAsPotionEffectType() {
		return this.getAsPotionEffect().type();
	}
}
