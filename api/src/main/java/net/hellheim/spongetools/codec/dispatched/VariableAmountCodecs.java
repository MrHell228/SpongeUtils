package net.hellheim.spongetools.codec.dispatched;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.weighted.VariableAmount;
import org.spongepowered.api.util.weighted.VariableAmount.Fixed;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.hellheim.spongetools.codec.CodecDispatcher;
import net.hellheim.spongetools.codec.list.SpongeCodecs;

public class VariableAmountCodecs {
	
	public static final CodecDispatcher<ResourceKey, VariableAmount> DISPATCHER = new CodecDispatcher<>();
	
	private static final Codec<VariableAmount> FULL = VariableAmountCodecs.DISPATCHER.codec(SpongeCodecs.SPONGE_RESOURCE_KEY);
	
	private static final Codec<VariableAmount> INLINE = Codec.DOUBLE.xmap(
			amount -> (Fixed) VariableAmount.fixed(amount),
			fixed -> fixed.amount(null));
	
	public static final Codec<VariableAmount> CODEC = Codec.withAlternative(VariableAmountCodecs.INLINE, VariableAmountCodecs.FULL);
	
	private static final MapCodec<Fixed> FIXED = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.DOUBLE.fieldOf("amount").forGetter(fixed -> fixed.amount(null))
					)
			.apply(instance, amount -> (Fixed) VariableAmount.fixed(amount)));
	
	static {
		DISPATCHER.put(ResourceKey.sponge("fixed"), FIXED, VariableAmount.Fixed.class);
	}
}
