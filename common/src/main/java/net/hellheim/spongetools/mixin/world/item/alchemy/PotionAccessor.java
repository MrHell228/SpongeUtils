package net.hellheim.spongetools.mixin.world.item.alchemy;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Potion.class)
public interface PotionAccessor {

    @Accessor("effects") List<MobEffectInstance> accessor$effects();

    @Accessor("effects") void accessor$effects(List<MobEffectInstance> effects);
}
