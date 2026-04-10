package net.hellheim.spongetools.mixin.world.entity;

import net.hellheim.spongetools.custom.behaviour.BehaviourHolder;
import net.hellheim.spongetools.custom.type.entity.EntityDisplay;
import net.hellheim.spongetools.custom.type.entity.EntityExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtension, BehaviourHolder.Defaulted {

    @Shadow
    private Level level;
    @Unique private List<EntityDisplay> spongetools$display;

    @Override
    public Object getAsActualBehaviourHolder() {
        return this;
    }

    @Override
    public org.spongepowered.api.entity.Entity owner() {
        return (org.spongepowered.api.entity.Entity) this;
    }

    @Override
    public List<EntityDisplay> display() {
        return this.spongetools$display;
    }

    @Override
    public void act(final byte action) {
        if (this.level instanceof final ServerLevel serverLevel) {
            //serverLevel.broadcastEntityEvent((Entity) this, action).getChunkSource().broadcast();
        }
        //this.level.getChunkSource().
    	// TODO Auto-generated method stub
    	
    }

    // TODO redirect Block methods in getBlockSpeedFactor & getBlockJumpFactor to BlockStateBaseBridge
}
