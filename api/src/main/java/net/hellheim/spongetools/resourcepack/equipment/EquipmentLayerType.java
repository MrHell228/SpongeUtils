package net.hellheim.spongetools.resourcepack.equipment;

import org.spongepowered.api.data.type.StringRepresentable;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.StringRepresentableCodec;

/**
 * The type of the {@link EquipmentLayer} in the {@link EquipmentAsset}.
 * 
 * @see <a href=https://minecraft.wiki/w/Equipment#Equipment_Layer_Types> Minecraft Wiki </a>
 */
public enum EquipmentLayerType implements StringRepresentable {
	HUMANOID,
	HUMANOID_LEGGINGS,
	HUMANOID_BABY,
	WINGS,
	WOLF_BODY,
	HORSE_BODY,
	LLAMA_BODY,
	PIG_SADDLE,
	STRIDER_SADDLE,
	CAMEL_SADDLE,
	CAMEL_HUSK_SADDLE,
	HORSE_SADDLE,
	DONKEY_SADDLE,
	MULE_SADDLE,
	ZOMBIE_HORSE_SADDLE,
	SKELETON_HORSE_SADDLE,
	HAPPY_GHAST_BODY,
	NAUTILUS_SADDLE,
	NAUTILUS_BODY;
	
	public static final Codec<EquipmentLayerType> CODEC = StringRepresentableCodec.fromValues(EquipmentLayerType::values);
	
	@Override
	public String serializationString() {
		return this.name().toLowerCase();
	}
}
