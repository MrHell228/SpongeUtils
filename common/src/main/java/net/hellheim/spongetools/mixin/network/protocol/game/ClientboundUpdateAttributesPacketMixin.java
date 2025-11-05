package net.hellheim.spongetools.mixin.network.protocol.game;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.hellheim.spongetools.common.util.NetworkUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@Mixin(ClientboundUpdateAttributesPacket.class)
public abstract class ClientboundUpdateAttributesPacketMixin {

    @Shadow @Final private List<ClientboundUpdateAttributesPacket.AttributeSnapshot> attributes;

    private @Unique boolean spongetools$fakeSpeedAdded = false;

    @WrapOperation(
            method = "<init>(ILjava/util/Collection;)V",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/core/Holder;DLjava/util/Collection;)Lnet/minecraft/network/protocol/game/ClientboundUpdateAttributesPacket$AttributeSnapshot;"
            )
    )
    private ClientboundUpdateAttributesPacket.AttributeSnapshot spongetools$replaceBreakSpeedAttribute(
            final Holder<Attribute> attribute, final double base, final Collection<AttributeModifier> modifiers,
            final Operation<ClientboundUpdateAttributesPacket.AttributeSnapshot> original
    ) {
        if (attribute.is(NetworkUtil.MINING_SPEED_ATTRIBUTE.attribute()) && SpongeToolsPlugin.customMiningEnabled()) {
            this.spongetools$fakeSpeedAdded = true;
            return NetworkUtil.MINING_SPEED_ATTRIBUTE;
        }

        return original.call(attribute, base, modifiers);
    }

    @Inject(method = "<init>(ILjava/util/Collection;)V", at = @At("RETURN"))
    private void spongetools$addBreakSpeedAttribute(final CallbackInfo ci) {
        if (!this.spongetools$fakeSpeedAdded && SpongeToolsPlugin.customMiningEnabled()) {
            this.attributes.add(NetworkUtil.MINING_SPEED_ATTRIBUTE);
        }
    }
}
