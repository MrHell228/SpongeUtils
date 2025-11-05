package net.hellheim.spongetools.common.builder;

import java.util.Objects;

import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;

import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.custom.type.block.BlockSoundGroupBuilder;

public final class BlockSoundGroupBuilderImpl implements BlockSoundGroupBuilder {
	
	private double volume;
	private double pitch;
	private SoundType breakSound;
	private SoundType stepSound;
	private SoundType placeSound;
	private SoundType hitSound;
	private SoundType fallSound;
	
	public BlockSoundGroupBuilderImpl() {
		this.reset();
	}
	
	@Override
	public BlockSoundGroupBuilder volume(final double volume) {
		this.volume = volume;
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder pitch(final double pitch) {
		this.pitch = pitch;
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder breakSound(final SoundType sound) {
		this.breakSound = Objects.requireNonNull(sound, "sound");
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder stepSound(final SoundType sound) {
		this.stepSound = Objects.requireNonNull(sound, "sound");
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder placeSound(final SoundType sound) {
		this.placeSound = Objects.requireNonNull(sound, "sound");
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder hitSound(final SoundType sound) {
		this.hitSound = Objects.requireNonNull(sound, "sound");
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder fallSound(final SoundType sound) {
		this.fallSound = Objects.requireNonNull(sound, "sound");
		return this;
	}
	
	@Override
	public BlockSoundGroupBuilder from(final BlockSoundGroup value) {
		return this
				.volume(value.volume())
				.pitch(value.pitch())
				.breakSound(value.breakSound())
				.stepSound(value.stepSound())
				.placeSound(value.placeSound())
				.hitSound(value.hitSound())
				.fallSound(value.fallSound());
	}
	
	@Override
	public BlockSoundGroupBuilder reset() {
		this.volume = 1.0D;
		this.pitch = 1.0D;
		this.breakSound = SoundTypes.INTENTIONALLY_EMPTY.get();
		this.stepSound = SoundTypes.INTENTIONALLY_EMPTY.get();
		this.placeSound = SoundTypes.INTENTIONALLY_EMPTY.get();
		this.hitSound = SoundTypes.INTENTIONALLY_EMPTY.get();
		this.fallSound = SoundTypes.INTENTIONALLY_EMPTY.get();
		return this;
	}
	
	@Override
	public BlockSoundGroup build() {
		return (BlockSoundGroup) new net.minecraft.world.level.block.SoundType(
				(float) this.volume,
				(float) this.pitch,
				Converter.asVanilla(this.breakSound),
				Converter.asVanilla(this.stepSound),
				Converter.asVanilla(this.placeSound),
				Converter.asVanilla(this.hitSound),
				Converter.asVanilla(this.fallSound));
	}
}
