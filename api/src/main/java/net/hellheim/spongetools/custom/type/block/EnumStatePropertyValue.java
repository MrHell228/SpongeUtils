package net.hellheim.spongetools.custom.type.block;

import org.spongepowered.api.data.type.StringRepresentable;
import org.spongepowered.api.state.EnumStateProperty;

/**
 * This interface is supposed to be implemented by classes used to create
 * {@link EnumStateProperty} via {@link StateProperties#enumProperty}.
 */
public interface EnumStatePropertyValue extends StringRepresentable {
}
