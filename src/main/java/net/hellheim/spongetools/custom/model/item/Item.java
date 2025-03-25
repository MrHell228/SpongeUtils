package net.hellheim.spongetools.custom.model.item;

import java.util.Objects;

import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;

public record Item(ItemDefinition definition, boolean handAnimationOnSwap) {
	
	public static final boolean DEFAULT_HAND_ANIMATION_ON_SWAP = true;
	
	public static final Codec<Item> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ItemDefinition.CODEC.fieldOf("model").forGetter(Item::definition),
					Codec.BOOL.optionalFieldOf("hand_animation_on_swap", Item.DEFAULT_HAND_ANIMATION_ON_SWAP).forGetter(Item::handAnimationOnSwap)
					).apply(instance, Item::new));
	
	public Item(final ItemDefinition definition, final boolean handAnimationOnSwap) {
		this.definition = Objects.requireNonNull(definition, "definition");
		this.handAnimationOnSwap = handAnimationOnSwap;
	}
	
	public static DefaultedRegistryType<Item> registry() {
		return SpongeTools.Registries.ITEM;
	}
	
	public static Item of(final ItemDefinition definition, final boolean handAnimationOnSwap) {
		return new Item(definition, handAnimationOnSwap);
	}
	
	public static Item of(final ItemDefinition definition) {
		return Item.of(definition, Item.DEFAULT_HAND_ANIMATION_ON_SWAP);
	}
}
