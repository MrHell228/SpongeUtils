package net.hellheim.spongeutils.proxy.solid;

import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;

public interface EnchantmentProxy extends EnchantmentTypeProxy {
	
	Enchantment getAsEnchantment();
	
	@Override
	default EnchantmentType getAsEnchantmentType() {
		return this.getAsEnchantment().type();
	}
}
