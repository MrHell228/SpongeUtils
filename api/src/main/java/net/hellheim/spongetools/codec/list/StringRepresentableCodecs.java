package net.hellheim.spongetools.codec.list;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.data.type.HandPreference;
import org.spongepowered.api.entity.display.ItemDisplayType;

import com.mojang.serialization.Codec;

public final class StringRepresentableCodecs {
	
	public static final Codec<DyeColor> DYE_COLOR = StringRepresentableCodecs.factory().dyeColor();
	
	public static final Codec<HandPreference> HAND_PREFERENCE = StringRepresentableCodecs.factory().handPreference();
	
	public static final Codec<ItemDisplayType> ITEM_DISPLAY_TYPE = StringRepresentableCodecs.factory().itemDisplayType();
	
	private static Factory factory() {
		return Sponge.game().factoryProvider().provide(Factory.class);
	}
	
	public static interface Factory {
		
		Codec<DyeColor> dyeColor();
		
		Codec<HandPreference> handPreference();
		
		Codec<ItemDisplayType> itemDisplayType();
	}
	
	private StringRepresentableCodecs() {
	}
}
