package net.hellheim.spongetools.mixin.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.hellheim.spongetools.bridge.EntityTypeBridge;
import net.hellheim.spongetools.common.event.AttributeEventImpl;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.common.util.EntityTypeUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.EventContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DefaultAttributes.class)
public abstract class DefaultAttributesMixin {

    @Unique private static Map<EntityType<?>, AttributeEventImpl.RegisterToEntityImpl.EntityStepImpl> SPONGETOOLS$ENTITIES;

    @Inject(
            method = "<clinit>",
            at = @At("HEAD")
    )
    private static void spongetools$fireAttributeEvents(final CallbackInfo ci) {
        final Game game = Sponge.game();
        final Cause cause = Cause.of(EventContext.empty(), game);
        game.eventManager().post(new AttributeEventImpl.ModifyImpl(cause, game));

        final var event = new AttributeEventImpl.RegisterToEntityImpl(cause, game);
        game.eventManager().post(event);
        SPONGETOOLS$ENTITIES = (Map<EntityType<?>, AttributeEventImpl.RegisterToEntityImpl.EntityStepImpl>) (Object) event.entities;
    }

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;"
            )
    )
    private static ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier> spongetools$registerCustomEntitiesAttributes(
            final Operation<ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier>> original) {
        final var builder = original.call();
        BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
            final EntityTypeUtil.@Nullable AdditionalData data = ((EntityTypeBridge) entityType).spongetools$bridge$getData();
            if (data != null && data.attributes() != null) {
                final AttributeSupplier.Builder supplier = AttributeSupplier.builder();
                data.attributes().attributes().forEach((key, modifier) -> {
                    BuiltInRegistries.ATTRIBUTE.get(Converter.asVanilla(key)).ifPresent(attribute -> {
                        modifier.ifPresentOrElse(
                                value -> supplier.add(attribute, value),
                                () -> supplier.add(attribute)
                        );
                    });
                });
                spongetools$impl$appendAttributes(entityType, supplier);
                builder.put((EntityType<? extends LivingEntity>) entityType, supplier.build());
            }
        });
        return builder;
    }

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableMap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;"
            )
    )
    private static ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier> spongetools$appendAttributesToVanillaEntities(
            final ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier> instance,
            final Object entityType, final Object supplier,
            final Operation<ImmutableMap.Builder<EntityType<? extends LivingEntity>, AttributeSupplier>> original
    ) {
        if (!SPONGETOOLS$ENTITIES.containsKey(entityType)) {
            return original.call(instance, entityType, supplier);
        } else {
            final var instances = ((AttributeSupplierAccessor) supplier).accessor$instances();
            final AttributeSupplier.Builder builder = AttributeSupplier.builder();
            instances.forEach((attribute, modifier) ->
                    builder.add(attribute, modifier.getBaseValue()));
            spongetools$impl$appendAttributes((EntityType<?>) entityType, builder);
            return original.call(instance, entityType, builder.build());
        }
    }

    private static void spongetools$impl$appendAttributes(
            final EntityType<?> entityType,
            final AttributeSupplier.Builder builder
    ) {
        SPONGETOOLS$ENTITIES.get(entityType).values.forEach((attribute, modifier) -> {
            final Holder<Attribute> holder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder((Attribute) attribute);
            modifier.ifPresentOrElse(
                    value -> builder.add(holder, value),
                    () -> builder.add(holder));
        });
    }
}
