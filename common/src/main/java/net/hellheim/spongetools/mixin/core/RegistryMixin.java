package net.hellheim.spongetools.mixin.core;

import net.hellheim.spongetools.bridge.RegistryBridge;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends RegistryBridge<T> {
}
