package net.hellheim.spongetools.common.util;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.checkerframework.checker.nullness.qual.PolyNull;
import org.spongepowered.common.SpongeCommon;

import com.google.common.base.Suppliers;

import io.netty.buffer.Unpooled;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;

// If only there were proper StreamCodec for Component...
public final class NetworkComponentSanitizer {
	
	private static final Supplier<RegistryFriendlyByteBuf> BYTE_BUF = Suppliers.memoize(
			() -> new RegistryFriendlyByteBuf(Unpooled.buffer(), SpongeCommon.server().registryAccess()));
	
	public static Component component(final Component component) {
		final MutableComponent result = MutableComponent.create(NetworkComponentSanitizer.componentContents(component.getContents()));
		result.setStyle(NetworkComponentSanitizer.style(component.getStyle()));
		component.getSiblings().forEach(sibling -> result.append(NetworkComponentSanitizer.component(sibling)));
		return result;
	}

	public static Style style(final Style style) {
		return style.withHoverEvent(NetworkComponentSanitizer.hoverEvent(style.getHoverEvent()));
	}
	
	private static ComponentContents componentContents(final ComponentContents contents) {
		if (contents instanceof final TranslatableContents translatable) {
			final Object[] oldArgs = translatable.getArgs();
			final Object[] newArgs = new Object[oldArgs.length];
			boolean containsComponent = false;
			for (int i = 0; i < oldArgs.length; ++i) {
				Object arg = oldArgs[i];
				if (arg instanceof final Component componentArg) {
					containsComponent = true;
					arg = NetworkComponentSanitizer.component(componentArg);
				}
				newArgs[i] = arg;
			}
			
			if (containsComponent) {
				return new TranslatableContents(
						translatable.getKey(),
						translatable.getFallback(),
						newArgs);
			}
		} else if (contents instanceof final SelectorContents selector) {
			final Optional<Component> separator = selector.separator();
			if (separator.isPresent()) {
				return new SelectorContents(
						selector.selector(),
						separator.map(NetworkComponentSanitizer::component));
			}
		} else if (contents instanceof final NbtContents nbt) {
			final Optional<Component> separator = nbt.getSeparator();
			if (separator.isPresent()) {
				return new NbtContents(
						nbt.getNbtPath(),
						nbt.isInterpreting(),
						separator.map(NetworkComponentSanitizer::component),
						nbt.getDataSource());
			}
		}
		
		return contents;
	}
	
	private static @PolyNull HoverEvent hoverEvent(final @PolyNull HoverEvent event) {
		if (event == null) {
			return null;
		}
		
		final HoverEvent.Action<?> action = event.getAction();
		if (action == HoverEvent.Action.SHOW_ITEM) {
			return NetworkComponentSanitizer.hoverEventWithInfo(event, HoverEvent.Action.SHOW_ITEM, info -> {
				final ItemStack originalStack = info.getItemStack();
				final RegistryFriendlyByteBuf byteBuf = NetworkComponentSanitizer.BYTE_BUF.get();
				ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, originalStack);
				final ItemStack sanitizedStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
				byteBuf.clear();
				return new HoverEvent.ItemStackInfo(sanitizedStack);
			});
		} else if (action == HoverEvent.Action.SHOW_ENTITY) {
			return NetworkComponentSanitizer.hoverEventWithInfo(event, HoverEvent.Action.SHOW_ENTITY, info ->
				new HoverEvent.EntityTooltipInfo(
					FakeableNetworkValueBridge.asNetworkValue(info.type),
					info.id,
					info.name.map(NetworkComponentSanitizer::component)));
		} else if (action == HoverEvent.Action.SHOW_TEXT) {
			return NetworkComponentSanitizer.hoverEventWithInfo(event, HoverEvent.Action.SHOW_TEXT,
				NetworkComponentSanitizer::component);
		}
		
		throw new IllegalArgumentException("Unknown HoverEvent Action: " + action.getSerializedName());
	}
	
	private static <T> HoverEvent hoverEventWithInfo(
		final HoverEvent event, final HoverEvent.Action<T> action, final UnaryOperator<T> mapper
	) {
		return new HoverEvent(action, mapper.apply(event.getValue(action)));
	}
	
	private NetworkComponentSanitizer() {
	}
}
