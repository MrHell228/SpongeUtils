package net.hellheim.spongetools.mixin.network.protocol.game;

import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.hellheim.spongetools.common.util.NetworkUtil;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientboundUpdateAttributesPacket.class)
public abstract class ClientboundUpdateAttributesPacketMixin {

    @Shadow @Final private List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes;

    @Inject(method = "<init>(ILjava/util/Collection;)V", at = @At("RETURN"))
    private void spongetools$addBreakSpeedAttribute(final CallbackInfo ci) {
        if (SpongeToolsPlugin.customMiningEnabled()) {
            this.attributes.add(NetworkUtil.miningSpeedAttribute());
        }
    }
}
