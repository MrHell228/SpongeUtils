package net.hellheim.spongetools.resourcepack.block;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Encoder;

import net.hellheim.spongetools.proxy.solid.codec.EncoderProxy;

public interface StateCondition extends EncoderProxy<StateCondition> {
	
	record AndCondition(List<StateCondition> conditions) implements StateCondition {
		
		public static final Encoder<AnyCondition> ENCODER = Codec
		
		public AndCondition(final List<StateCondition> conditions) {
			this.conditions = List.copyOf(conditions);
		}
		
		@Override
		public Encoder<? extends StateCondition> codec() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	record OrCondition(List<StateCondition> conditions) implements StateCondition {
		
		public OrCondition(final List<StateCondition> conditions) {
			this.conditions = List.copyOf(conditions);
		}
		
		@Override
		public Encoder<? extends StateCondition> codec() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	record PropertyCondition<T>() implements StateCondition {
		
		@Override
		public Encoder<? extends StateCondition> codec() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
