package net.hellheim.spongetools.custom.behaviour;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 
 */
public record BehaviourGroupType<G extends BehaviourGroup>(Set<BehaviourType<?>> required, Set<BehaviourType<?>> allowed) {
	
	public BehaviourGroupType(final Set<BehaviourType<?>> required, final Set<BehaviourType<?>> allowed) {
		Objects.requireNonNull(required, "required");
		Objects.requireNonNull(allowed, "allowed");
		final Set<BehaviourType<?>> common = Sets.intersection(required, allowed);
		if (!common.isEmpty()) {
			throw new IllegalArgumentException("Required and allowed behaviour must not contain common elements");
		}
		
		this.required = Set.copyOf(required);
		this.allowed = Set.copyOf(allowed);
	}
	
	public static <G extends BehaviourGroup> Builder<G> builder() {
		return new Builder<>();
	}
	
	public Set<BehaviourType<?>> all() {
		return Sets.union(this.required, this.allowed);
	}
	
	public static final class Builder<G extends BehaviourGroup>
			implements org.spongepowered.api.util.Builder<BehaviourGroupType<G>, Builder<G>> {
		
		private final Set<BehaviourType<?>> required = new HashSet<>();
		private final Set<BehaviourType<?>> allowed = new HashSet<>();
		
		public Builder<G> require(final Iterable<? extends BehaviourType<?>> types) {
			for (final BehaviourType<?> type : Objects.requireNonNull(types, "types")) {
				this.required.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
		
		public Builder<G> require(final BehaviourType<?>... types) {
			for (final BehaviourType<?> type : Objects.requireNonNull(types, "types")) {
				this.required.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
		
		public Builder<G> allow(final Iterable<? extends BehaviourType<?>> types) {
			for (final BehaviourType<?> type : Objects.requireNonNull(types, "types")) {
				this.allowed.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
		
		public Builder<G> allow(final BehaviourType<?>... types) {
			for (final BehaviourType<?> type : Objects.requireNonNull(types, "types")) {
				this.allowed.add(Objects.requireNonNull(type, "type"));
			}
			return this;
		}
		
		@Override
		public BehaviourGroupType<G> build() {
			return new BehaviourGroupType<>(this.required, this.allowed);
		}
	}
}
