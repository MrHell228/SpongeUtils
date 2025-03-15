package net.hellheim.spongetools.custom.item.model;

import java.util.Objects;

import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;

public record Item(ItemModelDefinition definition, boolean handAnimationOnSwap) {
	
	public static final boolean DEFAULT_HAND_ANIMATION_ON_SWAP = true;
	
	public static final Codec<Item> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ItemModelDefinition.CODEC.fieldOf("model").forGetter(Item::definition),
					Codec.BOOL.optionalFieldOf("hand_animation_on_swap", DEFAULT_HAND_ANIMATION_ON_SWAP).forGetter(Item::handAnimationOnSwap)
					).apply(instance, Item::new));
	
	public Item(final ItemModelDefinition definition, final boolean handAnimationOnSwap) {
		this.definition = Objects.requireNonNull(definition, "definition");
		this.handAnimationOnSwap = handAnimationOnSwap;
	}
	
	public static DefaultedRegistryType<Item> registry() {
		return SpongeTools.Registries.ITEM;
	}
	
	public static Item of(final ItemModelDefinition definition, final boolean handAnimationOnSwap) {
		return new Item(definition, handAnimationOnSwap);
	}
	
	public static Item of(final ItemModelDefinition definition) {
		return Item.of(definition, DEFAULT_HAND_ANIMATION_ON_SWAP);
	}
}
