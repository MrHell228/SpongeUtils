package net.hellheim.spongetools.mixin.world.item.context;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.behaviour.util.UseContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import org.spongepowered.api.util.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DirectionalPlaceContext.class)
public abstract class DirectionalPlaceContextMixin implements UseContext.DirectionaBlockPlace {

    @Shadow @Final private net.minecraft.core.Direction direction;

    @Override
    public Direction baseDirection() {
        return Converter.asSponge(this.direction);
    }
}
