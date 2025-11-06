package net.hellheim.spongetools.common.util;

import java.util.Optional;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.PolyNull;

import net.hellheim.spongetools.bridge.FakeableNetworkValueBridge;
import net.hellheim.spongetools.mixin.network.chat.HoverEvent_ItemStackInfoAccessor;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.TranslatableContents;

// If only there were proper StreamCodec for Component...
public final class NetworkComponentSanitizer {
	
	// Used to filter component patch in HoverEvent
	// It's all exists because of this...
	private static final Set<DataComponentType<?>> BADLY_NETWORKABLE_COMPONENTS = Set.of(
			// Contains Block
			DataComponents.TOOL,
			DataComponents.CAN_BREAK,
			DataComponents.CAN_PLACE_ON,
			// Contains ItemStack
			DataComponents.BUNDLE_CONTENTS,
			DataComponents.CHARGED_PROJECTILES,
			DataComponents.CONTAINER,
			DataComponents.USE_REMAINDER
			);

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
						Optional.of(NetworkComponentSanitizer.component(separator.get())));
			}
		} else if (contents instanceof final NbtContents nbt) {
			final Optional<Component> separator = nbt.getSeparator();
			if (separator.isPresent()) {
				return new NbtContents(
						nbt.getNbtPath(),
						nbt.isInterpreting(),
						Optional.of(NetworkComponentSanitizer.component(separator.get())),
						nbt.getDataSource());
			}
		}
		
		return contents;
	}
	
	private static @PolyNull HoverEvent hoverEvent(final @PolyNull HoverEvent event) {
		if (event == null) {
			return null;
		} else if (event.getAction() == HoverEvent.Action.SHOW_ITEM) {
			final HoverEvent_ItemStackInfoAccessor info = (HoverEvent_ItemStackInfoAccessor) event.getValue(HoverEvent.Action.SHOW_ITEM);
			return new HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent_ItemStackInfoAccessor.invoker$init(
					FakeableNetworkValueBridge.asNetworkItemHolder(info.accessor$item()),
					info.accessor$count(),
					NetworkComponentSanitizer.patch(info.accessor$patch())));
		} else {
			return event;
		}
	}
	
	private static DataComponentPatch patch(final DataComponentPatch patch) {
		return patch.forget(NetworkComponentSanitizer.BADLY_NETWORKABLE_COMPONENTS::contains);
	}
	
	private NetworkComponentSanitizer() {
	}
}
