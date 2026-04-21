package net.hellheim.spongetools.common.codec;

import java.util.List;
import java.util.Set;

import org.spongepowered.common.SpongeCommon;
import org.spongepowered.common.adventure.SpongeAdventure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
import net.kyori.adventure.text.event.HoverEvent.ShowItem;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo;
import net.minecraft.network.chat.HoverEvent.ItemStackInfo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class AdventureCodecsFactory implements AdventureCodecs.Factory {
	
	public static final Codec<Component> COMPONENT = ComponentSerialization.CODEC.xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanilla);
	
	public static final Codec<BossBar.Color> BOSS_BAR_COLOR = ExtraCodecs.idResolver(Codec.STRING, BossBar.Color.NAMES);
	
	public static final Codec<BossBar.Flag> BOSS_BAR_FLAG = ExtraCodecs.idResolver(Codec.STRING, BossBar.Flag.NAMES);
	
	public static final Codec<BossBar.Overlay> BOSS_BAR_OVERLAY = ExtraCodecs.idResolver(Codec.STRING, BossBar.Overlay.NAMES);
	
	public static final Codec<BossBar> BOSS_BAR = RecordCodecBuilder.create(
			instance -> instance.group(
					AdventureCodecsFactory.COMPONENT.fieldOf("name").forGetter(BossBar::name),
					Codec.floatRange(0, 1).optionalFieldOf("progress", 1.0F).forGetter(BossBar::progress),
					AdventureCodecsFactory.BOSS_BAR_COLOR.fieldOf("color").forGetter(BossBar::color),
					AdventureCodecsFactory.BOSS_BAR_OVERLAY.fieldOf("overlay").forGetter(BossBar::overlay),
					ExtraCodecs.setOf(AdventureCodecsFactory.BOSS_BAR_FLAG).optionalFieldOf("flags", Set.of()).forGetter(BossBar::flags)
					).apply(instance, BossBar::bossBar));
	
	public static final Codec<Book> BOOK = RecordCodecBuilder.create(
			instance -> instance.group(
					AdventureCodecsFactory.COMPONENT.fieldOf("title").forGetter(Book::title),
					AdventureCodecsFactory.COMPONENT.fieldOf("author").forGetter(Book::author),
					AdventureCodecsFactory.COMPONENT.listOf().optionalFieldOf("pages", List.of()).forGetter(Book::pages)
					).apply(instance, Book::book));
	
	@Override
	public Codec<NamedTextColor> namedTextColor() {
		return ExtraCodecs.idResolver(Codec.STRING, NamedTextColor.NAMES);
	}
	
	@Override
	public Codec<TextColor> textColor() {
		return net.minecraft.network.chat.TextColor.CODEC.xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanillaNullable);
	}
	
	@Override
	public Codec<Component> showText() {
		return this.component();
	}
	
	@Override
	public Codec<ShowItem> showItem() {
		return ItemStackInfo.CODEC.xmap(AdventureCodecsFactory::asAdventure, AdventureCodecsFactory::asVanilla);
	}
	
	@Override
	public Codec<ShowEntity> showEntity() {
		return EntityTooltipInfo.CODEC.xmap(AdventureCodecsFactory::asAdventure, AdventureCodecsFactory::asVanilla);
	}
	
	@Override
	public Codec<HoverEvent.Action<?>> hoverEventAction() {
		return net.minecraft.network.chat.HoverEvent.Action.CODEC.xmap(AdventureCodecsFactory::asAdventure, SpongeAdventure::asVanilla);
	}
	
	@Override
	public Codec<HoverEvent<?>> hoverEvent() {
		return net.minecraft.network.chat.HoverEvent.CODEC.xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanillaNullable);
	}
	
	@Override
	public Codec<ClickEvent.Action> clickEventAction() {
		return net.minecraft.network.chat.ClickEvent.Action.CODEC.codec().xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanilla);
	}
	
	@Override
	public Codec<ClickEvent> clickEvent() {
		return net.minecraft.network.chat.ClickEvent.CODEC.xmap(AdventureCodecsFactory::asAdventure, SpongeAdventure::asVanillaNullable);
	}
	
	@Override
	public Codec<Style> style() {
		return net.minecraft.network.chat.Style.Serializer.CODEC.xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanilla);
	}
	
	@Override
	public Codec<Component> component() {
		return AdventureCodecsFactory.COMPONENT;
	}
	
	@Override
	public Codec<Component> flatComponent() {
		return ComponentSerialization.FLAT_CODEC.xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanilla);
	}
	
	@Override
	public Codec<Component> flatComponent(final int maxSize) {
		return ComponentSerialization.flatCodec(maxSize).xmap(SpongeAdventure::asAdventure, SpongeAdventure::asVanilla);
	}
	
	@Override
	public Codec<BossBar.Color> bossBarColor() {
		return AdventureCodecsFactory.BOSS_BAR_COLOR;
	}
	
	@Override
	public Codec<BossBar.Flag> bossBarFlag() {
		return AdventureCodecsFactory.BOSS_BAR_FLAG;
	}
	
	@Override
	public Codec<BossBar.Overlay> bossBarOverlay() {
		return AdventureCodecsFactory.BOSS_BAR_OVERLAY;
	}
	
	@Override
	public Codec<BossBar> bossBar() {
		return AdventureCodecsFactory.BOSS_BAR;
	}
	
	@Override
	public Codec<Book> book() {
		return AdventureCodecsFactory.BOOK;
	}
	
	@Override
	public Codec<ChatType> chatType() {
		return AdventureCodecs.KEY.xmap(ChatType::chatType, ChatType::key);
	}
	
	// This should really be separated into specific methods in SpongeAdventure :<
	
	private static ShowItem asAdventure(final ItemStackInfo info) {
		final Registry<Item> itemRegistry = SpongeCommon.vanillaRegistry(Registries.ITEM);
		final ItemStack itemStack = info.getItemStack();
		return ShowItem.showItem(
				SpongeAdventure.asAdventure(itemRegistry.getKey(itemStack.getItem())),
				itemStack.getCount(),
				SpongeAdventure.asAdventure(itemStack.getComponentsPatch())
				);
	}
	
	private static ShowEntity asAdventure(final EntityTooltipInfo info) {
		final Registry<EntityType<?>> entityTypeRegistry = SpongeCommon.vanillaRegistry(Registries.ENTITY_TYPE);
		return ShowEntity.showEntity(
				SpongeAdventure.asAdventure(entityTypeRegistry.getKey(info.type)),
				info.id,
				SpongeAdventure.asAdventure(info.name)
				);
	}
	
	private static HoverEvent.Action<?> asAdventure(final net.minecraft.network.chat.HoverEvent.Action<?> action) {
		if (action == net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT) {
			return HoverEvent.Action.SHOW_TEXT;
		} else if (action == net.minecraft.network.chat.HoverEvent.Action.SHOW_ITEM) {
			return HoverEvent.Action.SHOW_ITEM;
		} else if (action == net.minecraft.network.chat.HoverEvent.Action.SHOW_ENTITY) {
			return HoverEvent.Action.SHOW_ENTITY;
		}
		
        throw new IllegalArgumentException(action.toString());
	}
	
	private static ClickEvent asAdventure(final net.minecraft.network.chat.ClickEvent event) {
		return ClickEvent.clickEvent(SpongeAdventure.asAdventure(event.getAction()), event.getValue());
	}
	
	private static ItemStackInfo asVanilla(final ShowItem info) {
		// TODO make mixin
		return (ItemStackInfo) null;
		/*final Registry<Item> itemRegistry = SpongeCommon.vanillaRegistry(Registries.ITEM);
		return HoverEvent_ItemStackInfoAccessor.invoker$new(
				itemRegistry.getValue(SpongeAdventure.asVanilla(adventure.item())).builtInRegistryHolder(),
				adventure.count(),
				SpongeAdventure.asVanilla(adventure.dataComponents())
				);*/
	}
	
	private static EntityTooltipInfo asVanilla(final ShowEntity info) {
		final Registry<EntityType<?>> entityTypeRegistry = SpongeCommon.vanillaRegistry(Registries.ENTITY_TYPE);
		return new EntityTooltipInfo(
				entityTypeRegistry.getValue(SpongeAdventure.asVanilla(info.type())),
				info.id(),
				SpongeAdventure.asVanillaNullable(info.name())
				);
	}
}
