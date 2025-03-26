package net.hellheim.spongetools.custom.metadata;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.proxy.solid.codec.CodecProxy;

public record AnimationFrame(int index, Optional<Integer> time) implements CodecProxy<AnimationFrame> {
	
	public static final Codec<AnimationFrame> FULL_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("index").forGetter(AnimationFrame::index),
			ExtraCodecs.POSITIVE_INT.optionalFieldOf("time").forGetter(AnimationFrame::time)
			).apply(instance, AnimationFrame::new));
	
	public static final Codec<AnimationFrame> CODEC = Codec.either(ExtraCodecs.NON_NEGATIVE_INT, FULL_CODEC)
		.xmap(
			either -> either.map(AnimationFrame::of, Function.identity()),
			frame -> frame.time().isPresent() ? Either.right(frame) : Either.left(frame.index())
			);
	
	public AnimationFrame(final int index, final Optional<Integer> time) {
		Preconditions.checkArgument(index >= 0, "index must not be negative: " + index);
		Objects.requireNonNull(time, "time");
		time.ifPresent(t -> Preconditions.checkArgument(t > 0, "time must be positive: " + t));
		this.index = index;
		this.time = time;
	}
	
	@Override
	public Codec<AnimationFrame> codec() {
		return CODEC;
	}
	
	public static AnimationFrame of(int index) {
		return AnimationFrame.of(index, Optional.empty());
	}
	
	public static AnimationFrame of(int index, int time) {
		return AnimationFrame.of(index, Optional.of(time));
	}
	
	public static AnimationFrame of(int index, Optional<Integer> time) {
		return new AnimationFrame(index, time);
	}
}
