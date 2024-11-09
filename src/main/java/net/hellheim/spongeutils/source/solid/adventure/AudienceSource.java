package net.hellheim.spongeutils.source.solid.adventure;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.hellheim.spongeutils.CompUtil;
import net.kyori.adventure.audience.Audience;
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
public interface AudienceSource extends Audience {
	
	Audience getAsAudience();
	
	@Override
	default Audience filterAudience(final Predicate<? super Audience> filter) {
		return this.getAsAudience().filterAudience(filter);
	}
	
	@Override
	default void forEachAudience(final Consumer<? super Audience> action) {
		this.getAsAudience().forEachAudience(action);
	}
	
	
	
	default void sendPlainMessages(final String... plainMessages) {
		this.sendMessages(CompUtil.fromPlains(plainMessages));
	}
	
	default void sendPlainMessages(final Iterable<String> plainMessages) {
		this.sendMessages(CompUtil.fromPlains(plainMessages));
	}
	
	default void sendPlainMessage(final String plainMessage) {
		this.sendMessage(CompUtil.fromPlain(plainMessage));
	}
	
	default void sendMiniMessages(final String... miniMessages) {
		this.sendMessages(CompUtil.fromMinis(miniMessages));
	}
	
	default void sendMiniMessages(final Iterable<String> miniMessages) {
		this.sendMessages(CompUtil.fromMinis(miniMessages));
	}
	
	default void sendMiniMessage(final String miniMessage) {
		this.sendMessage(CompUtil.fromMini(miniMessage));
	}
	
	default void sendMessages(final ComponentLike... messages) {
		final Audience audience = this.getAsAudience();
		for (final ComponentLike message : messages) {
			audience.sendMessage(message);
		}
	}
	
	default void sendMessages(final Component... messages) {
		final Audience audience = this.getAsAudience();
		for (final Component message : messages) {
			audience.sendMessage(message);
		}
	}
	
	default void sendMessages(final Iterable<? extends Component> messages) {
		final Audience audience = this.getAsAudience();
		for (final Component message : messages) {
			audience.sendMessage(message);
		}
	}
	
	
	
	@Override
	default void sendMessage(final ComponentLike message) {
		this.getAsAudience().sendMessage(message);
	}
	
	@Override
	default void sendMessage(final Component message) {
		this.getAsAudience().sendMessage(message);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final MessageType type) {
		this.getAsAudience().sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final MessageType type) {
		this.getAsAudience().sendMessage(message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message) {
		this.getAsAudience().sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message) {
		this.getAsAudience().sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message) {
		this.getAsAudience().sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message) {
		this.getAsAudience().sendMessage(source, message);
	}
	
	@Override
	default void sendMessage(final Identified source, final ComponentLike message, final MessageType type) {
		this.getAsAudience().sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final ComponentLike message, final MessageType type) {
		this.getAsAudience().sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identified source, final Component message, final MessageType type) {
		this.getAsAudience().sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Identity source, final Component message, final MessageType type) {
		this.getAsAudience().sendMessage(source, message, type);
	}
	
	@Override
	default void sendMessage(final Component message, final ChatType.Bound boundChatType) {
		this.getAsAudience().sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final ComponentLike message, final ChatType.Bound boundChatType) {
		this.getAsAudience().sendMessage(message, boundChatType);
	}
	
	@Override
	default void sendMessage(final SignedMessage signedMessage, final ChatType.Bound boundChatType) {
		this.getAsAudience().sendMessage(signedMessage, boundChatType);
	}
	
	@Override
	default void deleteMessage(final SignedMessage signedMessage) {
		this.getAsAudience().deleteMessage(signedMessage);
	}
	
	@Override
	default void deleteMessage(final SignedMessage.Signature signature) {
		this.getAsAudience().deleteMessage(signature);
	}
	
	@Override
	default void sendActionBar(final ComponentLike message) {
		this.getAsAudience().sendActionBar(message);
	}
	
	@Override
	default void sendActionBar(final Component message) {
		this.getAsAudience().sendActionBar(message);
	}
	
	@Override
	default void sendPlayerListHeader(final ComponentLike header) {
		this.getAsAudience().sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListHeader(final Component header) {
		this.getAsAudience().sendPlayerListHeader(header);
	}
	
	@Override
	default void sendPlayerListFooter(final ComponentLike footer) {
		this.getAsAudience().sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListFooter(final Component footer) {
		this.getAsAudience().sendPlayerListFooter(footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final ComponentLike header, final ComponentLike footer) {
		this.getAsAudience().sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void sendPlayerListHeaderAndFooter(final Component header, final Component footer) {
		this.getAsAudience().sendPlayerListHeaderAndFooter(header, footer);
	}
	
	@Override
	default void showTitle(final Title title) {
		this.getAsAudience().showTitle(title);
	}
	
	@Override
	default <T> void sendTitlePart(final TitlePart<T> part, final T value) {
		this.getAsAudience().sendTitlePart(part, value);
	}
	
	@Override
	default void clearTitle() {
		this.getAsAudience().clearTitle();
	}
	
	@Override
	default void resetTitle() {
		this.getAsAudience().resetTitle();
	}
	
	@Override
	default void showBossBar(final BossBar bar) {
		this.getAsAudience().showBossBar(bar);
	}
	
	@Override
	default void hideBossBar(final BossBar bar) {
		this.getAsAudience().hideBossBar(bar);
	}
	
	@Override
	default void playSound(final Sound sound) {
		this.getAsAudience().playSound(sound);
	}
	
	@Override
	default void playSound(final Sound sound, final double x, final double y, final double z) {
		this.getAsAudience().playSound(sound, x, y, z);
	}
	
	@Override
	default void playSound(final Sound sound, final Sound.Emitter emitter) {
		this.getAsAudience().playSound(sound, emitter);
	}
	
	@Override
	default void stopSound(final Sound sound) {
		this.getAsAudience().stopSound(sound);
	}
	
	@Override
	default void stopSound(final SoundStop stop) {
		this.getAsAudience().stopSound(stop);
	}
	
	@Override
	default void openBook(final Book.Builder book) {
		this.getAsAudience().openBook(book);
	}
	
	@Override
	default void openBook(final Book book) {
		this.getAsAudience().openBook(book);
	}
}
