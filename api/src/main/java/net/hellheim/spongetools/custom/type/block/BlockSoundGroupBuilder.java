package net.hellheim.spongetools.custom.type.block;

import java.util.Objects;
import java.util.function.Supplier;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.util.Builder;
import org.spongepowered.api.util.CopyableBuilder;

public interface BlockSoundGroupBuilder extends
		Builder<BlockSoundGroup, BlockSoundGroupBuilder>,
		CopyableBuilder<BlockSoundGroup, BlockSoundGroupBuilder> {
	
	static BlockSoundGroupBuilder create() {
		return Sponge.game().builderProvider().provide(BlockSoundGroupBuilder.class);
	}
	
	BlockSoundGroupBuilder volume(double volume);
	
	BlockSoundGroupBuilder pitch(double pitch);
	
	BlockSoundGroupBuilder breakSound(SoundType sound);
	
	BlockSoundGroupBuilder stepSound(SoundType sound);
	
	BlockSoundGroupBuilder placeSound(SoundType sound);
	
	BlockSoundGroupBuilder hitSound(SoundType sound);
	
	BlockSoundGroupBuilder fallSound(SoundType sound);
	
	default BlockSoundGroupBuilder breakSound(Supplier<? extends SoundType> sound) {
		return this.breakSound(Objects.requireNonNull(sound, "sound").get());
	}
	
	default BlockSoundGroupBuilder stepSound(Supplier<? extends SoundType> sound) {
		return this.stepSound(Objects.requireNonNull(sound, "sound").get());
	}
	
	default BlockSoundGroupBuilder placeSound(Supplier<? extends SoundType> sound) {
		return this.placeSound(Objects.requireNonNull(sound, "sound").get());
	}
	
	default BlockSoundGroupBuilder hitSound(Supplier<? extends SoundType> sound) {
		return this.hitSound(Objects.requireNonNull(sound, "sound").get());
	}
	
	default BlockSoundGroupBuilder fallSound(Supplier<? extends SoundType> sound) {
		return this.fallSound(Objects.requireNonNull(sound, "sound").get());
	}
}
