package net.hellheim.spongetools.common.codec;

import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.HandPreference;
import org.spongepowered.api.entity.display.ItemDisplayType;

import com.mojang.serialization.Codec;

import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.StringRepresentableCodecs;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

public final class StringRepresentableCodecsFactory implements StringRepresentableCodecs.Factory {
	
	@Override
	public Codec<DyeColor> dyeColor() {
		return ExtraCodecs.casted(net.minecraft.world.item.DyeColor.CODEC);
	}
	
	@Override
	public Codec<HandPreference> handPreference() {
		return ExtraCodecs.casted(HumanoidArm.CODEC);
	}
	
	@Override
	public Codec<ItemDisplayType> itemDisplayType() {
		return ExtraCodecs.casted(ItemDisplayContext.CODEC);
	}
}
