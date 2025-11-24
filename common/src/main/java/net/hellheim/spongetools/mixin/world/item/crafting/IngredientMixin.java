package net.hellheim.spongetools.mixin.world.item.crafting;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.util.SpongeTypeItemList;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Ingredient.class)
public abstract class IngredientMixin {

    @WrapOperation(
            method = {
                    "of(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/crafting/Ingredient;",
                    "of(Ljava/util/stream/Stream;)Lnet/minecraft/world/item/crafting/Ingredient;",
                    "of(Lnet/minecraft/core/HolderSet;)Lnet/minecraft/world/item/crafting/Ingredient;"
            },
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/core/HolderSet;)Lnet/minecraft/world/item/crafting/Ingredient;"
            )
    )
    private static Ingredient spongetools$properlyDisplayCustomItems(
            final HolderSet<Item> set, final Operation<Ingredient> original
    ) {
        if (set.stream()
                .map(holder -> holder.unwrapKey().orElseThrow().location().getNamespace())
                .allMatch(ResourceKey.MINECRAFT_NAMESPACE::equals)) {
            return original.call(set);
        }

        final ItemStack[] display = set.stream().map(ItemStack::new).toArray(ItemStack[]::new);
        return SpongeTypeItemList.ingredient(set, display);
    }
}
