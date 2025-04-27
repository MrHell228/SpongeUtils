package net.hellheim.spongetools.custom.behaviour.world;

import java.util.Optional;
import java.util.stream.Stream;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.RandomProvider;
import org.spongepowered.api.world.World;

public interface SignalOrientation {
	
	static SignalOrientation of(final Direction up, final Direction front, final SignalBias bias) {
		return SignalOrientation.factory().of(up, front, bias);
	}
	
	static SignalOrientation random(final RandomProvider.Source random) {
		return SignalOrientation.factory().random(random);
	}
	
	static SignalOrientation random(final RandomProvider provider) {
		return SignalOrientation.random(provider.random());
	}
	
	static SignalOrientation randomWith(final World<?, ?> world, final Optional<Direction> up, final Optional<Direction> front) {
		return SignalOrientation.factory().randomWith(world, up, front);
	}
	
	static SignalOrientation randomWith(final World<?, ?> world, final Direction up, final Direction front) {
		return SignalOrientation.randomWith(world, Optional.of(up), Optional.of(front));
	}
	
	static SignalOrientation randomWithUp(final World<?, ?> world, final Direction up) {
		return SignalOrientation.randomWith(world, Optional.of(up), Optional.empty());
	}
	
	static SignalOrientation randomWithFront(final World<?, ?> world, final Direction front) {
		return SignalOrientation.randomWith(world, Optional.empty(), Optional.of(front));
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	Direction up();
	
	Direction front();
	
	Direction side();
	
	SignalBias bias();
	
	SignalOrientation withUp(Direction direction);
	
	SignalOrientation withFront(Direction direction);
	
	SignalOrientation withBias(SignalBias bias);
	
	Stream<Direction> allDirections();
	
	Stream<Direction> horizontalDirections();
	
	Stream<Direction> verticalDirections();
	
	interface Factory {
		
		SignalOrientation of(Direction up, Direction front, SignalBias bias);
		
		SignalOrientation random(RandomProvider.Source random);
		
		SignalOrientation randomWith(World<?, ?> world, Optional<Direction> up, Optional<Direction> front);
	}
}
