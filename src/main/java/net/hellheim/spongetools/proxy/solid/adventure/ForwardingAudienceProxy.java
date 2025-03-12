package net.hellheim.spongetools.proxy.solid.adventure;

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
public interface ForwardingAudienceProxy extends AudienceProxy, ForwardingAudience {
	
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
		AudienceProxy.super.sendMessage(message);
	}
	
	@Override
	default void sendMessage(final Component message) {
		AudienceProxy.super.sendMessage(message);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final MessageType type) {
		AudienceProxy.super.sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final MessageType type) {
		AudienceProxy.super.sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message) {
		AudienceProxy.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message) {
		AudienceProxy.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message) {
		AudienceProxy.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message) {
		AudienceProxy.super.sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message, final MessageType type) {
		AudienceProxy.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message, final MessageType type) {
		AudienceProxy.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message, final MessageType type) {
		AudienceProxy.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message, final MessageType type) {
		AudienceProxy.super.sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final ChatType.Bound boundChatType) {
		AudienceProxy.super.sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final ChatType.Bound boundChatType) {
		AudienceProxy.super.sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final SignedMessage signedMessage, final ChatType.Bound boundChatType) {
		AudienceProxy.super.sendMessage(signedMessage, boundChatType);
	}
	
	@Override
	default void deleteMessage(final SignedMessage signedMessage) {
		AudienceProxy.super.deleteMessage(signedMessage);
	}
	
	@Override
	default void deleteMessage(final SignedMessage.Signature signature) {
		AudienceProxy.super.deleteMessage(signature);
	}
	
	@Override
	default void sendActionBar(final ComponentLike message) {
		AudienceProxy.super.sendActionBar(message);
	}
	
	@Override
	default void sendActionBar(final Component message) {
		AudienceProxy.super.sendActionBar(message);
	}
	
	@Override
	default void sendPlayerListHeader(final ComponentLike header) {
		AudienceProxy.super.sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListHeader(final Component header) {
		AudienceProxy.super.sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListFooter(final ComponentLike footer) {
		AudienceProxy.super.sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListFooter(final Component footer) {
		AudienceProxy.super.sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final ComponentLike header, final ComponentLike footer) {
		AudienceProxy.super.sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final Component header, final Component footer) {
		AudienceProxy.super.sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void showTitle(final Title title) {
		AudienceProxy.super.showTitle(title);
	}
	
	@Override
	default <T> void sendTitlePart(final TitlePart<T> part, final T value) {
		AudienceProxy.super.sendTitlePart(part, value);
	}
	
	@Override
	default void clearTitle() {
		AudienceProxy.super.clearTitle();
	}
	
	@Override
	default void resetTitle() {
		AudienceProxy.super.resetTitle();
	}
	
	@Override
	default void showBossBar(final BossBar bar) {
		AudienceProxy.super.showBossBar(bar);
	}
	
	@Override
	default void hideBossBar(final BossBar bar) {
		AudienceProxy.super.hideBossBar(bar);
	}
	
	@Override
	default void playSound(final Sound sound) {
		AudienceProxy.super.playSound(sound);
	}
	
	@Override
	default void playSound(final Sound sound, final double x, final double y, final double z) {
		AudienceProxy.super.playSound(sound, x, y, z);
	}
	
	@Override
	default void playSound(final Sound sound, final Sound.Emitter emitter) {
		AudienceProxy.super.playSound(sound, emitter);
	}
	
	@Override
	default void stopSound(final Sound sound) {
		AudienceProxy.super.stopSound(sound);
	}
	
	@Override
	default void stopSound(final SoundStop stop) {
		AudienceProxy.super.stopSound(stop);
	}
	
	@Override
	default void openBook(final Book.Builder book) {
		AudienceProxy.super.openBook(book);
	}
	
	@Override
	default void openBook(final Book book) {
		AudienceProxy.super.openBook(book);
	}
}
