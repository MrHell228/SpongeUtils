package net.hellheim.spongetools.mixin.world.item.crafting;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.type.item.CustomItemType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Ingredient.class)
public class IngredientMixin {

    @WrapMethod(method = "test(Lnet/minecraft/world/item/ItemStack;)Z")
    private boolean spongetools$failTestForCustomItems(final ItemStack stack, final Operation<Boolean> original) {
        return original.call(stack)
                && Converter.asSponge(stack).get(CustomItemType.dataKey()).isEmpty();
    }
}
