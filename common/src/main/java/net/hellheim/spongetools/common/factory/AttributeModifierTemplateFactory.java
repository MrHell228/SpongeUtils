package net.hellheim.spongetools.common.factory;

import java.util.Objects;
import java.util.function.IntToDoubleFunction;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.entity.attribute.AttributeOperation;

import net.hellheim.spongetools.bridge.MobEffectBridge;
import net.hellheim.spongetools.object.AttributeModifierTemplate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class AttributeModifierTemplateFactory implements AttributeModifierTemplate.Factory {
	
	public static IntToDoubleFunction vanillaCurve(final double amountPerLevel) {
		return amplifier -> (amplifier + 1) * amountPerLevel;
	}
	
	@Override
	public AttributeModifierTemplate of(
		final ResourceKey key, final AttributeOperation operation, final double amountPerLevel
	) {
		return this.of(key, operation, AttributeModifierTemplateFactory.vanillaCurve(amountPerLevel));
	}
	
	@Override
	public AttributeModifierTemplate of(
		final ResourceKey key, final AttributeOperation operation, final IntToDoubleFunction curve
	) {
		return DummyMobEffect.TEMPLATE_ASSEMBLER.spongetools$bridge$assembleTemplate(
				Objects.requireNonNull(key, "key"),
				Objects.requireNonNull(operation, "operation"),
				Objects.requireNonNull(curve, "curve")
				);
	}
	
	private static final class DummyMobEffect extends MobEffect {
		
		private static final MobEffectBridge TEMPLATE_ASSEMBLER = (MobEffectBridge) (Object) new DummyMobEffect();
		
		protected DummyMobEffect() {
			super(MobEffectCategory.NEUTRAL, 0);
		}
	}
}
