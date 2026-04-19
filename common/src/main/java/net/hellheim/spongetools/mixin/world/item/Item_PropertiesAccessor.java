package net.hellheim.spongetools.mixin.world.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Item.Properties.class)
public interface Item_PropertiesAccessor {

    @Invoker("itemIdOrThrow") ResourceKey<Item> invoker$itemIdOrThrow();

    @Invoker("effectiveDescriptionId") String invoker$effectiveDescriptionId();
}
