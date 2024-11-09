package net.hellheim.spongeutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * {@link Component}s utilities
 */
public final class CompUtil {
	
	public static String toPlain(final Component comp) {
		return toString(Types.plain(), comp);
	}
	public static List<String> toPlains(final Component... comps) {
		return toStrings(Types.plain(), comps);
	}
	public static List<String> toPlains(final Iterable<Component> comps) {
		return toStrings(Types.plain(), comps);
	}
	
	public static TextComponent fromPlain(final String str) {
		return fromString(Types.plain(), str);
	}
	public static List<TextComponent> fromPlains(final String... strs) {
		return fromStrings(Types.plain(), strs);
	}
	public static List<TextComponent> fromPlains(final Iterable<String> strs) {
		return fromStrings(Types.plain(), strs);
	}
	
	
	public static String toMini(final Component comp) {
		return toString(Types.mini(), comp);
	}
	public static List<String> toMinis(final Component... comps) {
		return toStrings(Types.mini(), comps);
	}
	public static List<String> toMinis(final Iterable<Component> comps) {
		return toStrings(Types.mini(), comps);
	}
	
	public static Component fromMini(final String str) {
		return fromString(Types.mini(), str);
	}
	public static List<Component> fromMinis(final String... strs) {
		return fromStrings(Types.mini(), strs);
	}
	public static List<Component> fromMinis(final Iterable<String> strs) {
		return fromStrings(Types.mini(), strs);
	}
	
	
	public static String toGson(final Component comp) {
		return toString(Types.gson(), comp);
	}
	public static List<String> toGsons(final Component... comps) {
		return toStrings(Types.gson(), comps);
	}
	public static List<String> toGsons(final Iterable<Component> comps) {
		return toStrings(Types.gson(), comps);
	}
	
	public static Component fromGson(final String str) {
		return fromString(Types.gson(), str);
	}
	public static List<Component> fromGsons(final String... strs) {
		return fromStrings(Types.gson(), strs);
	}
	public static List<Component> fromGsons(final Iterable<String> strs) {
		return fromStrings(Types.gson(), strs);
	}
	
	
	
	public static <I extends Component, O extends Component> String toString(
		final ComponentSerializer<I, O, String> serializer, final I comp
	) {
		return serializer.serialize(comp);
	}
	
	@SafeVarargs
	public static <I extends Component, O extends Component> List<String> toStrings(
		final ComponentSerializer<I, O, String> serializer, final I... comps
	) {
		final List<String> result = new ArrayList<>();
		for (final I comp : comps) {
			result.add(toString(serializer, comp));
		}
		return result;
	}
	
	public static <I extends Component, O extends Component> List<String> toStrings(
		final ComponentSerializer<I, O, String> serializer, final Iterable<? extends I> comps
	) {
		final List<String> result = new ArrayList<>();
		for (final Iterator<? extends I> iter = comps.iterator(); iter.hasNext();) {
			result.add(toString(serializer, iter.next()));
		}
		return result;
	}
	
	
	public static <I extends Component, O extends Component> O fromString(
		final ComponentSerializer<I, O, String> serializer, final String str
	) {
		return serializer.deserialize(str);
	}
	
	public static <I extends Component, O extends Component> List<O> fromStrings(
		final ComponentSerializer<I, O, String> serializer, final String... strs
	) {
		final List<O> result = new ArrayList<>();
		for (final String str : strs) {
			result.add(fromString(serializer, str));
		}
		return result;
	}
	
	public static <I extends Component, O extends Component> List<O> fromStrings(
		final ComponentSerializer<I, O, String> serializer, final Iterable<String> strs
	) {
		final List<O> result = new ArrayList<>();
		for (final Iterator<String> iter = strs.iterator(); iter.hasNext();) {
			result.add(fromString(serializer, iter.next()));
		}
		return result;
	}
	
	
	public static class Types {
		
		private Types() {
		}
		
		public static PlainTextComponentSerializer plain() {
			return PlainTextComponentSerializer.plainText();
		}
		
		public static MiniMessage mini() {
			return MiniMessage.miniMessage();
		}
		
		public static GsonComponentSerializer gson() {
			return GsonComponentSerializer.gson();
		}
		
		public static GsonComponentSerializer colorDownsamplingGson() {
			return GsonComponentSerializer.colorDownsamplingGson();
		}
		
		public static LegacyComponentSerializer legacy(final char legacyCharacter) {
			return LegacyComponentSerializer.legacy(legacyCharacter);
		}
		
		public static LegacyComponentSerializer legacyAmpersand() {
			return LegacyComponentSerializer.legacyAmpersand();
		}
		
		public static LegacyComponentSerializer legacySection() {
			return LegacyComponentSerializer.legacySection();
		}
	}
	
	
	public static final class Mini {
		
		private static final String addColons(final Object... objects) {
			String s = "";
			for (Object object : objects)
				s += ":" + object.toString();
			return s;
		}
		private static final String colorsToString(final TextColor[] colors) {
			String s = "";
			for (TextColor color : colors)
				s += addColons(color);
			return s;
		}
		private static final float clamp(final float f1, final float f2, final float f3) {
			return f1 < f2
					? f2
					: f1 > f3
						? f3
						: f1;
		}
		
		private static final String closedTag(final String tag, final String content) {
			return closedTag(tag, "", content);
		}
		private static final String closedTag(final String tag, final String args, final String content) {
			return "<" + tag + args + ">" + content + "</" + tag + ">";
		}
		private static final String tag(final String tag) {
			return "<" + tag + ">";
		}
		private static final String tag(final String tag, final String args) {
			return "<" + tag + args + ">";
		}
		
		
		public static final String color(final TextColor color, final Object content) {
			return closedTag("color", addColons(color.asHexString()), content.toString());
		}
		public static final String decor(final TextDecoration decor, final String content) {
			return closedTag(decor.toString(), content);
		}
		
		public static final String click(final ClickEvent event, final String content) {
			return closedTag("click", addColons(event.action(), event.value()), content);
		}
		public static final String hover(final HoverEvent<?> event, final String content) {
			return closedTag("hover", addColons(event.action(), event.value()), content);
		}
		
		public static final String key(final String key) {
			return tag(key);
		}
		
		public static final String lang(final String key, final Object... values) {
			return tag("lang", addColons(key) + addColons(values));
		}
		
		public static final String insert(final String toInsert, final String content) {
			return closedTag("insert", toInsert, content);
		}
		
		private static final String rainbow(final String args, final String content) {
			return closedTag("rainbow", args, content);
		}
		public static final String rainbow(final String content) {
			return rainbow("", content);
		}
		public static final String rainbow(final int phase, final String content) {
			return rainbow(addColons(phase), content);
		}
		public static final String rainbowInv(final String content) {
			return rainbow(addColons("!"), content);
		}
		public static final String rainbowInv(final int phase, final String content) {
			return rainbow(addColons("!" + phase), content);
		}
		
		private static final String gradient(final String args, final String content) {
			return closedTag("gradient", args, content);
		}
		public static final String gradient(final String content) {
			return gradient("", content);
		}
		public static final String gradient(final TextColor[] colors, final String content) {
			return colors.length < 2 ? content : gradient(colorsToString(colors), content);
		}
		public static final String gradient(final TextColor[] colors, final float phase, final String content) {
			return colors.length < 2 ? content : gradient(colorsToString(colors) + addColons(clamp(phase, -1.0F, 1.0F)));
		}
		
		private static final String transition(final String args, final String content) {
			return closedTag("transition", args, content);
		}
		public static final String transition(final String content) {
			return transition("", content);
		}
		public static final String transition(final TextColor[] colors, final String content) {
			return colors.length < 2 ? content : transition(colorsToString(colors), content);
		}
		public static final String transition(final TextColor[] colors, final float phase, final String content) {
			return colors.length < 2 ? content : transition(colorsToString(colors) + addColons(clamp(phase, -1.0F, 1.0F)));
		}
		
		public static final String font(final String font, final String content) {
			return closedTag("font", addColons(font), content);
		}
		
		public static final String newLine() {
			return tag("newLine");
		}
	}
	
	private CompUtil() {
	}
}
