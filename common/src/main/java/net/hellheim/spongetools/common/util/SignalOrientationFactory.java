package net.hellheim.spongetools.common.util;

import java.util.Optional;

import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.World;

import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;

public final class SignalOrientationFactory implements SignalOrientation.Factory {
	
	@Override
	public SignalOrientation of(final Direction up, final Direction front, final SignalBias bias) {
		return Converter.asSponge(Orientation.of(Converter.asVanilla(up), Converter.asVanilla(front), Converter.asVanilla(bias)));
	}
	
	@Override
	public SignalOrientation random(final RandomProvider.Source random) {
		return Converter.asSponge(Orientation.random(Converter.asVanilla(random)));
	}
	
	@Override
	public SignalOrientation randomWith(final World<?, ?> world, final Optional<Direction> up, final Optional<Direction> front) {
		return Converter.asSponge(ExperimentalRedstoneUtils.initialOrientation(
				Converter.asVanilla(world),
				front.map(Converter::asVanilla).orElse(null),
				up.map(Converter::asVanilla).orElse(null)
				));
	}
	
	public static final class BiasFactory implements SignalBias.Factory {
		
		@Override
		public SignalBias left() {
			return Converter.asSponge(Orientation.SideBias.LEFT);
		}
		
		@Override
		public SignalBias right() {
			return Converter.asSponge(Orientation.SideBias.RIGHT);
		}
	}
}
