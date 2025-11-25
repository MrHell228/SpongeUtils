package net.hellheim.spongetools.common.util;

import java.util.Optional;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.PolyNull;
import org.spongepowered.common.SpongeCommon;

import com.google.common.base.Suppliers;

import io.netty.buffer.Unpooled;
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
		} else if (event.getAction() == HoverEvent.Action.SHOW_ITEM) {
			final ItemStack originalStack = event.getValue(HoverEvent.Action.SHOW_ITEM).getItemStack();
			final RegistryFriendlyByteBuf byteBuf = NetworkComponentSanitizer.BYTE_BUF.get();
			ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, originalStack);
			final ItemStack sanitizedStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
			byteBuf.clear();
			return new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(sanitizedStack));
		} else {
			return event;
		}
	}
	
	private NetworkComponentSanitizer() {
	}
}
