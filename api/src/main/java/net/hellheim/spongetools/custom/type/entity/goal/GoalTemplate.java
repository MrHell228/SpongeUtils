package net.hellheim.spongetools.custom.type.entity.goal;

import java.util.Objects;
import java.util.function.Function;

import org.spongepowered.api.entity.ai.goal.Goal;
import org.spongepowered.api.entity.ai.goal.GoalBuilder;
import org.spongepowered.api.entity.living.Agent;

public record GoalTemplate<O extends Agent, G extends Goal<O>>(int priority, Function<O, G> goalProvider) {
	
	/*
	TODO this should probably be switched to Encoder to allow making goals from configs
	
	Codec<GoalTemplate<?, ?>> CODEC = GoalTemplate.registryCodec().dispatch(GoalTemplate::mapCodec, Function.identity());
	
	static DefaultedRegistryType<MapCodec<? extends GoalTemplate<?, ?>>> registry() {
		return SpongeTools.Registries.GOAL_TEMPLATE_TYPE;
	}
	
	static Codec<MapCodec<? extends GoalTemplate<?, ?>>> registryCodec() {
		return RegistryCodecs.GOAL_TEMPLATE_TYPE;
	}
	*/
	
	public GoalTemplate(final int priority, final Function<O, G> goalProvider) {
		this.priority = priority;
		this.goalProvider = Objects.requireNonNull(goalProvider, "goalProvider");
	}
	
	public static <O extends Agent, G extends Goal<O>> GoalTemplate<O, G> of(
		final int priority, final Function<O, G> goalProvider
	) {
		return new GoalTemplate<>(priority, goalProvider);
	}
	
	public static <O extends Agent, G extends Goal<O>> GoalTemplate<O, G> of(
		final int priority, final GoalBuilder<O, G, ?> goalBuilder
	) {
		return new GoalTemplate<>(priority, goalBuilder::build);
	}
	
	public G create(final O owner) {
		return this.goalProvider.apply(owner);
	}
	
	@SuppressWarnings("unchecked")
	public G createUnsafe(final Agent owner) {
		return this.create((O) owner);
	}
}
