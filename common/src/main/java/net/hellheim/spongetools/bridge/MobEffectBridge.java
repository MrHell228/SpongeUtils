package net.hellheim.spongetools.bridge;

import java.util.Map;

import net.hellheim.spongetools.object.AttributeModifierTemplate;
import org.spongepowered.api.entity.attribute.type.AttributeType;

public interface MobEffectBridge {
	
	Map<AttributeType, AttributeModifierTemplate> spongetools$bridge$modifierTemplates();
}
