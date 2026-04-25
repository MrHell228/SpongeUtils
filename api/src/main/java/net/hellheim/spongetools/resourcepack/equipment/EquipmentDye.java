package net.hellheim.spongetools.resourcepack.equipment;

import java.util.Objects;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.list.ExtraCodecs;

/**
 * The dyeable data of the {@link EquipmentLayer}.
 */
public record EquipmentDye(Optional<Integer> colorWhenUndyed) {
	
	private static final EquipmentDye EMPTY = EquipmentDye.of(Optional.empty());
	private static final EquipmentDye LEATHER = EquipmentDye.of(-6265536);
	
	public static final Codec<EquipmentDye> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					ExtraCodecs.RGB.optionalFieldOf("color_when_undyed").forGetter(EquipmentDye::colorWhenUndyed))
			.apply(instance, EquipmentDye::new));
	
	public EquipmentDye(final Optional<Integer> colorWhenUndyed) {
		this.colorWhenUndyed = Objects.requireNonNull(colorWhenUndyed, "colorWhenUndyed");
	}
	
	/**
	 * Returns the new {@link EquipmentDye}.
	 * 
	 * @param colorWhenUndyed The color when undyed
	 * @return The equipment dye
	 */
	public static EquipmentDye of(final Optional<Integer> colorWhenUndyed) {
		return new EquipmentDye(colorWhenUndyed);
	}
	
	/**
	 * Returns the new {@link EquipmentDye}.
	 * 
	 * @param colorWhenUndyed The color when undyed
	 * @return The equipment dye
	 */
	public static EquipmentDye of(final int colorWhenUndyed) {
		return EquipmentDye.of(Optional.of(colorWhenUndyed));
	}
	
	/**
	 * Returns the {@link EquipmentDye} that vanilla uses for leather equipment.
	 * 
	 * @return The equipment dye
	 */
	public static EquipmentDye leather() {
		return EquipmentDye.LEATHER;
	}
	
	/**
	 * Returns the {@link EquipmentDye} that hides the equipment if it's not dyed.
	 * 
	 * @return The equipment dye
	 */
	public static EquipmentDye empty() {
		return EquipmentDye.EMPTY;
	}
}
