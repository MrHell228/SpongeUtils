package net.hellheim.spongetools.custom.type.block;

import java.util.function.Consumer;

import org.spongepowered.api.fluid.FluidTypes;

import net.hellheim.spongetools.custom.behaviour.type.BlockStateBehaviours;

public final class CustomBlockTypePresets {
	
	public static final CustomBlockTypeProperties
	
	SLAB = create(b -> b.state(BlockStateProvider.slab())
			.set(BlockStateBehaviours.FLUID, () -> FluidTypes.EMPTY.get().defaultState())
			.offer);
	
	private static final CustomBlockTypeProperties create(
		final Consumer<CustomBlockTypeBuilder<?>> configurator
	) {
		final var builder = builder();
		configurator.accept(builder);
		return builder.buildProperties();
	}
	
	private static final CustomBlockTypeBuilder<?> builder() {
		return new CustomBlockTypeBuilder<>();
	}
	
	private CustomBlockTypePresets() {
	}
}
