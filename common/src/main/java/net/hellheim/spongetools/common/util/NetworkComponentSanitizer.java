package net.hellheim.spongetools.common.util;

import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.PolyNull;
import org.spongepowered.common.SpongeCommon;

import com.google.common.base.Suppliers;

import io.netty.buffer.Unpooled;
import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.ObjectContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

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
		switch (contents) {
			case final TranslatableContents translatable -> {
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
			}
			case SelectorContents(final var selector, final var separator) -> {
				if (separator.isPresent()) {
					return new SelectorContents(
							selector,
							separator.map(NetworkComponentSanitizer::component));
				}
			}
			case NbtContents(var nbtPath, var interpreting, var plain, var separator, var dataSource) -> {
				if (separator.isPresent()) {
					return new NbtContents(
							nbtPath,
							interpreting,
							plain,
							separator.map(NetworkComponentSanitizer::component),
							dataSource);
				}
			}
			case ObjectContents(var objectInfo, var fallback) -> {
				if (fallback.isPresent()) {
					return new ObjectContents(objectInfo, fallback.map(NetworkComponentSanitizer::component));
				}
			}
			default -> {}
		}
		
		return contents;
	}
	
	private static @PolyNull HoverEvent hoverEvent(final @PolyNull HoverEvent event) {
		return switch (event) {
			case null -> null;
			case HoverEvent.ShowItem(final ItemStackTemplate info) -> {
				final ItemStack originalStack = info.create();
				final RegistryFriendlyByteBuf byteBuf = NetworkComponentSanitizer.BYTE_BUF.get();
				ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, originalStack);
				final ItemStack sanitizedStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
				byteBuf.clear();
				yield new HoverEvent.ShowItem(ItemStackTemplate.fromNonEmptyStack(sanitizedStack));
			}
			case HoverEvent.ShowEntity(final EntityTooltipInfo info) ->
				new HoverEvent.ShowEntity(new EntityTooltipInfo(
						FakeableNetworkValueBridge.asNetworkValue(info.type),
						info.uuid,
						info.name.map(NetworkComponentSanitizer::component)));
			case HoverEvent.ShowText(final Component info) ->
				new HoverEvent.ShowText(NetworkComponentSanitizer.component(info));
			default -> throw new IllegalArgumentException(String.format(
					"Unknown HoverEvent with action %s: %s", event.action().getSerializedName(), event));
		};
	}
	
	private NetworkComponentSanitizer() {
	}
}
