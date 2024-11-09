package net.hellheim.spongeutils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.sound.music.MusicDisc;
import org.spongepowered.api.world.WorldType;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

@SuppressWarnings("deprecation")
public final class ViewerUtil {
	
	public static Viewer empty() {
		return EmptyViewer.INSTANCE;
	}
	
	private static class EmptyViewer implements Viewer {
		
		static final EmptyViewer INSTANCE = new EmptyViewer();
		
		private EmptyViewer() {
		}
		
		@Override
		public <T> Optional<T> get(final Pointer<T> pointer) {
			return Optional.empty();
		}
		
		@Override
		public <T> T getOrDefault(final Pointer<T> pointer, final T defaultValue) {
			return defaultValue;
		}
		
		@Override
		public <T> T getOrDefaultFrom(final Pointer<T> pointer, final Supplier<? extends T> defaultValue) {
			return defaultValue.get();
		}
		
		@Override
		public Audience filterAudience(final Predicate<? super Audience> filter) {
			return this;
		}
		
		@Override
		public void forEachAudience(final Consumer<? super Audience> action) {
		}
		
		@Override
		public void sendMessage(final ComponentLike message) {
		}
		
		@Override
		public void sendMessage(final Component message) {
		}
		
		@Override
		public void sendMessage(final Identified source, final Component message, final MessageType type) {
		}
		
		@Override
		public void sendMessage(final Identity source, final Component message, final MessageType type) {
		}
		
		@Override
		public void sendMessage(final Component message, final ChatType.Bound boundChatType) {
		}
		
		@Override
		public void sendMessage(final SignedMessage signedMessage, final ChatType.Bound boundChatType) {
		}
		
		@Override
		public void deleteMessage(final SignedMessage.Signature signature) {
		}
		
		@Override
		public void sendActionBar(final ComponentLike message) {
		}
		
		@Override
		public void sendPlayerListHeader(final ComponentLike header) {
		}
		
		@Override
		public void sendPlayerListFooter(final ComponentLike footer) {
		}
		
		@Override
		public void sendPlayerListHeaderAndFooter(final ComponentLike header, final ComponentLike footer) {
		}
		
		@Override
		public void openBook(final Book.Builder book) {
		}
		
		@Override
		public void sendWorldType(final WorldType worldType) {
		}
		
		@Override
		public void spawnParticles(final ParticleEffect particleEffect, final Vector3d position) {
		}
		
		@Override
		public void spawnParticles(final ParticleEffect particleEffect, final Vector3d position, final int radius) {
		}
		
		@Override
		public void playSound(final Sound sound, final Vector3d pos) {
		}
		
		@Override
		public void playMusicDisc(final Vector3i position, final MusicDisc musicDiscType) {
		}
		
		@Override
		public void stopMusicDisc(final Vector3i position) {
		}
		
		@Override
		public void sendBlockChange(final Vector3i position, final BlockState state) {
		}
		
		@Override
		public void sendBlockChange(final int x, final int y, final int z, final BlockState state) {
		}
		
		@Override
		public void resetBlockChange(final Vector3i position) {
		}
		
		@Override
		public void resetBlockChange(final int x, final int y, final int z) {
		}
		
		@Override
		public boolean equals(final Object that) {
			return this == that;
		}
		
		@Override
		public int hashCode() {
			return 0;
		}
		
		@Override
		public String toString() {
			return "EmptyViewer";
		}
	}
	
	private ViewerUtil() {
	}
}
