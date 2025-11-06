package net.hellheim.spongetools.mixin.network.chat;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HoverEvent.ItemStackInfo.class)
public interface HoverEvent_ItemStackInfoAccessor {

    @Invoker("<init>") static HoverEvent.ItemStackInfo invoker$init(Holder<Item> $$0, int $$1, DataComponentPatch $$2) {
        throw new IllegalStateException();
    }

    @Accessor("item") Holder<Item> accessor$item();

    @Accessor("count") int accessor$count();

    @Accessor("components") DataComponentPatch accessor$patch();
}
