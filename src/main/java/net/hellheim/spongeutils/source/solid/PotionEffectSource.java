package net.hellheim.spongeutils.source.solid;

import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;

public interface PotionEffectSource extends PotionEffectTypeSource {
	
	PotionEffect getAsPotionEffect();
	
	@Override
	default PotionEffectType getAsPotionEffectType() {
		return this.getAsPotionEffect().type();
	}
}
