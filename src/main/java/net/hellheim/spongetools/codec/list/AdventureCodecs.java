package net.hellheim.spongetools.codec.list;

import org.spongepowered.api.Sponge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
import net.kyori.adventure.text.event.HoverEvent.ShowItem;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

/**
 * Codecs related to kyori Adventure
 */
public final class AdventureCodecs {
	
	public static final Codec<Key> KEY = AdventureCodecs.key(Key.MINECRAFT_NAMESPACE);
	
	public static final Codec<NamedTextColor> NAMED_TEXT_COLOR = AdventureCodecs.factory().namedTextColor();
	
	public static final Codec<TextColor> TEXT_COLOR = AdventureCodecs.factory().textColor();
	
	public static final Codec<Component> SHOW_TEXT = AdventureCodecs.factory().showText();
	
	public static final Codec<ShowItem> SHOW_ITEM = AdventureCodecs.factory().showItem();
	
	public static final Codec<ShowEntity> SHOW_ENTITY = AdventureCodecs.factory().showEntity();
	
	public static final Codec<HoverEvent.Action<?>> HOVER_EVENT_ACTION = AdventureCodecs.factory().hoverEventAction();
	
	public static final Codec<HoverEvent<?>> HOVER_EVENT = AdventureCodecs.factory().hoverEvent();
	
	public static final Codec<ClickEvent.Action> CLICK_EVENT_ACTION = AdventureCodecs.factory().clickEventAction();
	
	public static final Codec<ClickEvent> CLICK_EVENT = AdventureCodecs.factory().clickEvent();
	
	public static final Codec<Style> STYLE = AdventureCodecs.factory().style();
	
	public static final Codec<Component> COMPONENT = AdventureCodecs.factory().component();
	
	public static final Codec<Component> FLAT_COMPONENT = AdventureCodecs.factory().flatComponent();
	
	public static final Codec<BossBar.Color> BOSS_BAR_COLOR = AdventureCodecs.factory().bossBarColor();
	
	public static final Codec<BossBar.Flag> BOSS_BAR_FLAG = AdventureCodecs.factory().bossBarFlag();
	
	public static final Codec<BossBar.Overlay> BOSS_BAR_OVERLAY = AdventureCodecs.factory().bossBarOverlay();
	
	public static final Codec<BossBar> BOSS_BAR = AdventureCodecs.factory().bossBar();
	
	public static final Codec<Book> BOOK = AdventureCodecs.factory().book();
	
	public static final Codec<ChatType> CHAT_TYPE = AdventureCodecs.factory().chatType();
	
	public static Codec<Key> key(final String defaultNamespace) {
		if (Key.parseableNamespace(defaultNamespace)) {
			throw new IllegalArgumentException("Invalid namespace: " + defaultNamespace);
		}
		
		return Codec.STRING.comapFlatMap(
				str -> {
					if (str.contains(":")) {
						try {
							return DataResult.success(Key.key(str));
						} catch (final InvalidKeyException e) {
							return DataResult.error(e::toString);
						}
					} else {
						try {
							return DataResult.success(Key.key(defaultNamespace, str));
						} catch (final InvalidKeyException e) {
							return DataResult.error(e::toString);
						}
					}
				},
				key -> key.namespace().equals(defaultNamespace) ? key.value() : key.asString()
				);
	}
	
	public static Codec<Component> flatComponent(final int maxSize) {
		return AdventureCodecs.factory().flatComponent(maxSize);
	}
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	public static interface Factory {
		
		Codec<NamedTextColor> namedTextColor();
		
		Codec<TextColor> textColor();
		
		Codec<Component> showText();
		
		Codec<ShowItem> showItem();
		
		Codec<ShowEntity> showEntity();
		
		Codec<HoverEvent.Action<?>> hoverEventAction();
		
		Codec<HoverEvent<?>> hoverEvent();
		
		Codec<ClickEvent.Action> clickEventAction();
		
		Codec<ClickEvent> clickEvent();
		
		Codec<Style> style();
		
		Codec<Component> component();
		
		Codec<Component> flatComponent();
		
		Codec<Component> flatComponent(int maxSize);
		
		Codec<BossBar.Color> bossBarColor();
		
		Codec<BossBar.Flag> bossBarFlag();
		
		Codec<BossBar.Overlay> bossBarOverlay();
		
		Codec<BossBar> bossBar();
		
		Codec<Book> book();
		
		Codec<ChatType> chatType();
	}
	
	private AdventureCodecs() {
	}
}
