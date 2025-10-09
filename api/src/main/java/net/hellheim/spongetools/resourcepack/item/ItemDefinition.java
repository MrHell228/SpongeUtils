package net.hellheim.spongetools.resourcepack.item;

import java.util.Objects;

import org.spongepowered.api.registry.DefaultedRegistryType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.RegistryCodecs;

/**
 * @see <a href=https://minecraft.wiki/w/Items_model_definition> Minecraft Wiki </a>
 */
public record ItemDefinition(ItemModel model, boolean handAnimationOnSwap) {
	
	public static final boolean DEFAULT_HAND_ANIMATION_ON_SWAP = true;
	
	public static final Codec<ItemDefinition> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ItemModel.CODEC.fieldOf("model").forGetter(ItemDefinition::model),
					Codec.BOOL.optionalFieldOf("hand_animation_on_swap", ItemDefinition.DEFAULT_HAND_ANIMATION_ON_SWAP).forGetter(ItemDefinition::handAnimationOnSwap)
					).apply(instance, ItemDefinition::new));
	
	public ItemDefinition(final ItemModel model, final boolean handAnimationOnSwap) {
		this.model = Objects.requireNonNull(model, "model");
		this.handAnimationOnSwap = handAnimationOnSwap;
	}
	
	public static DefaultedRegistryType<ItemDefinition> registry() {
		return SpongeTools.Registries.ITEM_DEFINITION;
	}
	
	public static Codec<ItemDefinition> registryCodec() {
		return RegistryCodecs.ITEM_DEFINITION;
	}
	
	public static ItemDefinition of(final ItemModel model, final boolean handAnimationOnSwap) {
		return new ItemDefinition(model, handAnimationOnSwap);
	}
	
	public static ItemDefinition of(final ItemModel model) {
		return ItemDefinition.of(model, ItemDefinition.DEFAULT_HAND_ANIMATION_ON_SWAP);
	}
}
