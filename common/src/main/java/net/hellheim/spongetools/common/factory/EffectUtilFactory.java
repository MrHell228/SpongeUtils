package net.hellheim.spongetools.common.factory;

import java.util.Map;

import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.attribute.type.AttributeType;

import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.hellheim.spongetools.util.EffectUtil;

public final class EffectUtilFactory implements EffectUtil.Factory {
	
	@Override
	public Map<AttributeType, AttributeModifierTemplate> modifierTemplates(final PotionEffectType type) {
		return ((MobEffectBridge) type).spongetools$bridge$modifierTemplates();
	}
}
