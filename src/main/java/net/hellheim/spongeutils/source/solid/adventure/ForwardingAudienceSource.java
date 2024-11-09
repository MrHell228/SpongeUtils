package net.hellheim.spongeutils.source.solid.adventure;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

@SuppressWarnings("deprecation")
public interface ForwardingAudienceSource extends AudienceSource, ForwardingAudience {
	
	@Override
	ForwardingAudience getAsAudience();
	
	@Override
	default Iterable<? extends Audience> audiences() {
		return this.getAsAudience().audiences();
	}
	
	@Override
	default Audience filterAudience(final Predicate<? super Audience> filter) {
		return this.getAsAudience().filterAudience(filter);
	}
	
	@Override
	default void forEachAudience(final Consumer<? super Audience> action) {
		this.getAsAudience().forEachAudience(action);
	}
	
	@Override
	default void sendMessage(final ComponentLike message) {
		AudienceSource.super.sendMessage(message);
	}
	
	@Override
	default void sendMessage(final Component message) {
		AudienceSource.super.sendMessage(message);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final MessageType type) {
		AudienceSource.super.sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final MessageType type) {
		AudienceSource.super.sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message) {
		AudienceSource.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message) {
		AudienceSource.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message) {
		AudienceSource.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message) {
		AudienceSource.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message, final MessageType type) {
		AudienceSource.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message, final MessageType type) {
		AudienceSource.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message, final MessageType type) {
		AudienceSource.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message, final MessageType type) {
		AudienceSource.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final ChatType.Bound boundChatType) {
		AudienceSource.super.sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final ChatType.Bound boundChatType) {
		AudienceSource.super.sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final SignedMessage signedMessage, final ChatType.Bound boundChatType) {
		AudienceSource.super.sendMessage(signedMessage, boundChatType);
	}
	
	@Override
	default void deleteMessage(final SignedMessage signedMessage) {
		AudienceSource.super.deleteMessage(signedMessage);
	}
	
	@Override
	default void deleteMessage(final SignedMessage.Signature signature) {
		AudienceSource.super.deleteMessage(signature);
	}
	
	@Override
	default void sendActionBar(final ComponentLike message) {
		AudienceSource.super.sendActionBar(message);
	}
	
	@Override
	default void sendActionBar(final Component message) {
		AudienceSource.super.sendActionBar(message);
	}
	
	@Override
	default void sendPlayerListHeader(final ComponentLike header) {
		AudienceSource.super.sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListHeader(final Component header) {
		AudienceSource.super.sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListFooter(final ComponentLike footer) {
		AudienceSource.super.sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListFooter(final Component footer) {
		AudienceSource.super.sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final ComponentLike header, final ComponentLike footer) {
		AudienceSource.super.sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final Component header, final Component footer) {
		AudienceSource.super.sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void showTitle(final Title title) {
		AudienceSource.super.showTitle(title);
	}
	
	@Override
	default <T> void sendTitlePart(final TitlePart<T> part, final T value) {
		AudienceSource.super.sendTitlePart(part, value);
	}
	
	@Override
	default void clearTitle() {
		AudienceSource.super.clearTitle();
	}
	
	@Override
	default void resetTitle() {
		AudienceSource.super.resetTitle();
	}
	
	@Override
	default void showBossBar(final BossBar bar) {
		AudienceSource.super.showBossBar(bar);
	}
	
	@Override
	default void hideBossBar(final BossBar bar) {
		AudienceSource.super.hideBossBar(bar);
	}
	
	@Override
	default void playSound(final Sound sound) {
		AudienceSource.super.playSound(sound);
	}
	
	@Override
	default void playSound(final Sound sound, final double x, final double y, final double z) {
		AudienceSource.super.playSound(sound, x, y, z);
	}
	
	@Override
	default void playSound(final Sound sound, final Sound.Emitter emitter) {
		AudienceSource.super.playSound(sound, emitter);
	}
	
	@Override
	default void stopSound(final Sound sound) {
		AudienceSource.super.stopSound(sound);
	}
	
	@Override
	default void stopSound(final SoundStop stop) {
		AudienceSource.super.stopSound(stop);
	}
	
	@Override
	default void openBook(final Book.Builder book) {
		AudienceSource.super.openBook(book);
	}
	
	@Override
	default void openBook(final Book book) {
		AudienceSource.super.openBook(book);
	}
}
