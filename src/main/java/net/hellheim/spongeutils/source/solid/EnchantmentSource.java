package net.hellheim.spongeutils.source.solid;

import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;

public interface EnchantmentSource extends EnchantmentTypeSource {
	
	Enchantment getAsEnchantment();
	
	@Override
	default EnchantmentType getAsEnchantmentType() {
		return this.getAsEnchantment().type();
	}
}
