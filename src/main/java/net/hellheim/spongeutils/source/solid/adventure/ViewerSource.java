package net.hellheim.spongeutils.source.solid.adventure;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.sound.music.MusicDisc;
import org.spongepowered.api.world.WorldType;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.kyori.adventure.sound.Sound;

public interface ViewerSource extends AudienceSource, Viewer {
	
	@Override
	Viewer getAsAudience();
	
	@Override
	default void sendWorldType(final WorldType worldType) {
		this.getAsAudience().sendWorldType(worldType);
	}
	
	@Override
	default void spawnParticles(final ParticleEffect particleEffect, final Vector3d position) {
		this.getAsAudience().spawnParticles(particleEffect, position);
	}
	
	@Override
	default void spawnParticles(final ParticleEffect particleEffect, final double x, final double y, final double z) {
		this.getAsAudience().spawnParticles(particleEffect, x, y, z);
	}
	
	@Override
	default void playSound(final Sound sound, final Vector3d pos) {
		this.getAsAudience().playSound(sound, pos);
	}
	
	@Override
	default void playMusicDisc(final Vector3i position, final MusicDisc musicDiscType) {
		this.getAsAudience().playMusicDisc(position, musicDiscType);
	}
	
	@Override
	default void playMusicDisc(final int x, final int y, final int z, final MusicDisc musicDisc) {
		this.getAsAudience().playMusicDisc(x, y, z, musicDisc);
	}
	
	@Override
	default void stopMusicDisc(final Vector3i position) {
		this.getAsAudience().stopMusicDisc(position);
	}
	
	@Override
	default void stopMusicDisc(final int x, final int y, final int z) {
		this.getAsAudience().stopMusicDisc(x, y, z);
	}
	
	@Override
	default void sendBlockChange(final Vector3i position, final BlockState state) {
		this.getAsAudience().sendBlockChange(position, state);
	}
	
	@Override
	default void sendBlockChange(final int x, final int y, final int z, final BlockState state) {
		this.getAsAudience().sendBlockChange(x, y, z, state);
	}
	
	@Override
	default void resetBlockChange(final Vector3i position) {
		this.getAsAudience().resetBlockChange(position);
	}
	
	@Override
	default void resetBlockChange(final int x, final int y, final int z) {
		this.getAsAudience().resetBlockChange(x, y, z);
	}
	
	@Override
	default void sendBlockProgress(final Vector3i position, double progress) {
		this.getAsAudience().sendBlockProgress(position, progress);
	}
	
	@Override
	default void sendBlockProgress(final int x, final int y, final int z, final double progress) {
		this.getAsAudience().sendBlockProgress(x, y, z, progress);
	}
	
	@Override
	default void resetBlockProgress(final Vector3i position) {
		this.getAsAudience().resetBlockProgress(position);
	}
	
	@Override
	default void resetBlockProgress(final int x, final int y, final int z) {
		this.getAsAudience().resetBlockProgress(x, y, z);
	}
}
