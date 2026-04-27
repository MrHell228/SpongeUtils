package net.hellheim.spongetools.bridge;

import java.util.Map;
import java.util.Optional;
import java.util.function.IntToDoubleFunction;

import net.hellheim.spongetools.object.AttributeModifierTemplate;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.attribute.AttributeOperation;
import org.spongepowered.api.entity.attribute.type.AttributeType;

public interface MobEffectBridge {
	
	Map<AttributeType, AttributeModifierTemplate> spongetools$bridge$modifierTemplates();

    void spongetools$bridge$setModifierTemplates(Map<AttributeType, AttributeModifierTemplate> attributes);

    Optional<SoundType> spongetools$bridge$sound();

    void spongetools$bridge$setSound(Optional<SoundType> sound);

    AttributeModifierTemplate spongetools$bridge$assembleTemplate(ResourceKey key, AttributeOperation operation, IntToDoubleFunction curve);
}
