package net.hellheim.spongetools.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.entity.Angerable;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.RangedAgent;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.DefaultMethod;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import net.hellheim.spongetools.common.behaviour.CommonArgs;
import net.hellheim.spongetools.common.behaviour.EntityArgs;
import net.hellheim.spongetools.custom.behaviour.Behaviour;
import net.hellheim.spongetools.custom.behaviour.BehaviourArgs;
import net.hellheim.spongetools.custom.behaviour.BehaviourCallbackHolderLogic;
import net.hellheim.spongetools.custom.behaviour.BehaviourLayer;
import net.hellheim.spongetools.custom.behaviour.BehaviourType;
import net.hellheim.spongetools.custom.type.entity.EntityBehaviours;
import net.hellheim.spongetools.custom.type.entity.EntityFlags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * Some possible EntityFlags were not added:
 * <li> {@link Shearable} - in vanilla, it's not used by Items.SHEARS what makes it useless
 * <li> {@link FlyingAnimal} (slightly adjusts Y-movement in air) - used by 2 mobs, no idea why it exists
 */
final class EntityFactoryUtil {
	
	// TODO EntityFlags to implement
	@SuppressWarnings("unused")
	private static final Map<?, ?> GROUPS = Map.of(
			org.spongepowered.api.entity.Leashable.class, Leashable.class, // A lot of behaviour
			org.spongepowered.api.entity.Saddleable.class, Saddleable.class, // Some behaviour
			Angerable.class, NeutralMob.class, // A lot of behaviour
			null, Bucketable.class,
			null, ItemSteerable.class // #boost
			);
	
	protected static <E extends Entity> EntityType.EntityFactory<E> create(
		final Class<E> baseClass,
		final Collection<org.spongepowered.api.ResourceKey> flags,
		final BehaviourCallbackHolderLogic<Entity> behaviour
	) {
		final DynamicType.Builder<E> builder = new ByteBuddy()
				.subclass(baseClass)
				.defineConstructor(Visibility.PUBLIC)
				.withParameters(EntityType.class, Level.class)
				.intercept(SuperMethodCall.INSTANCE);
		
		final Class<? extends E> entity = EntityFactoryUtil.decorate(builder, baseClass, flags, behaviour)
				.make()
				.load(Entity.class.getClassLoader())
				.getLoaded();
		
		try {
			final var constructor = entity.getConstructor(EntityType.class, Level.class);
			return (type, world) -> {
				try {
					return constructor.newInstance(type, world);
				} catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException("Could not create new custom entity", e);
				}
			};
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException("Could not find custom entity constructor (this should not happen)", e);
		}
	}
	
	private static <E extends Entity> DynamicType.Builder<E> decorate(
		DynamicType.Builder<E> builder,
		final Class<E> baseClass,
		final Collection<org.spongepowered.api.ResourceKey> flags,
		final BehaviourCallbackHolderLogic<Entity> behaviour
	) {
		// Archetypes
		
		if (Mob.class.isAssignableFrom(baseClass)) {
			builder = EntityFactoryUtil.methodWithCallbackNoArgs(builder, behaviour,
					named("registerGoals"),
					EntityBehaviours.REGISTER_GOALS,
					e -> () -> {}
					);
		}
		
		// Flags
		
		if (flags.contains(EntityFlags.HOSTILE)) {
			builder = builder.implement(Enemy.class);
		}
		
		if (flags.contains(EntityFlags.RANGER) || RangedAttackMob.class.isAssignableFrom(baseClass)) {
			builder = builder.implement(RangedAttackMob.class);
			builder = EntityFactoryUtil.methodWithCallback(builder, behaviour,
					named("performRangedAttack").and(takesArguments(LivingEntity.class, float.class)),
					EntityBehaviours.PERFORM_RANGED_ATTACK,
					e -> (target, velocity) -> {},
					args -> new EntityArgs.PerformRangedAttack((Living) args[0], ((Number) args[1]).doubleValue()),
					args -> new Object[] {(LivingEntity) args.target(), (float) args.velocity()}
					);
			
			if (Agent.class.isAssignableFrom(baseClass)) {
				builder = builder.implement(RangedAgent.class);
			}
		}
		
		if (flags.contains(EntityFlags.RIDEABLE_INVENTORY) || HasCustomInventoryScreen.class.isAssignableFrom(baseClass)) {
			builder = builder.implement(HasCustomInventoryScreen.class);
			builder = EntityFactoryUtil.methodWithCallback(builder, behaviour,
					named("openCustomInventoryScreen").and(takesArguments(Player.class)),
					EntityBehaviours.OPEN_INVENTORY,
					e -> (player) -> {},
					args -> new EntityArgs.OpenInventory((ServerPlayer) args[0]),
					args -> new Object[] {args.player()}
					);
		}
		
		if (flags.contains(EntityFlags.RIDEABLE_JUMPER) || PlayerRideableJumping.class.isAssignableFrom(baseClass)) {
			builder = builder.implement(PlayerRideableJumping.class);
			builder = EntityFactoryUtil.methodWithCallbackNoArgs(builder, behaviour,
					named("canJump"),
					EntityBehaviours.JUMP_READY,
					e -> () -> true
					);
			builder = EntityFactoryUtil.methodWithCallback(builder, behaviour,
					named("handleStartJump").and(takesArguments(int.class)),
					EntityBehaviours.JUMP_START,
					e -> (power) -> {},
					args -> new EntityArgs.JumpStart((int) args[0]),
					args -> new Object[] {args.power()}
					);
			// There is also stop jump packet but in vanilla it's never sent by client so no need to support it yet
		}
		
		return builder;
	}
	
	private static <E, R, B extends Behaviour<R, BehaviourArgs>> DynamicType.Builder<E> methodWithCallbackNoArgs(
		final DynamicType.Builder<E> builder,
		final BehaviourCallbackHolderLogic<Entity> callbacks,
		final Junction<? super MethodDescription> description,
		final BehaviourType<B> behaviourType,
		final Function<Entity, B> fallbackBehaviour
	) {
		return EntityFactoryUtil.methodWithCallback(
				builder,
				callbacks,
				description.and(takesNoArguments()),
				behaviourType,
				fallbackBehaviour,
				args -> CommonArgs.empty(),
				args -> new Object[0]);
	}
	
	@SuppressWarnings("unchecked")
	private static <E, R, A extends BehaviourArgs, B extends Behaviour<R, A>> DynamicType.Builder<E> methodWithCallback(
		final DynamicType.Builder<E> builder,
		final BehaviourCallbackHolderLogic<Entity> callbacks,
		final ElementMatcher<? super MethodDescription> description,
		final BehaviourType<B> behaviourType,
		final Function<Entity, B> fallbackBehaviour,
		final Function<Object[], A> toSpongeArgs,
		final Function<A, Object[]> toVanillaArgs
	) {
		return EntityFactoryUtil.methodWithCallback(
				builder,
				callbacks,
				description,
				behaviourType,
				fallbackBehaviour,
				toSpongeArgs,
				toVanillaArgs,
				obj -> (R) obj,
				r -> r);
	}
	
	private static <E, R, A extends BehaviourArgs, B extends Behaviour<R, A>> DynamicType.Builder<E> methodWithCallback(
		final DynamicType.Builder<E> builder,
		final BehaviourCallbackHolderLogic<Entity> callbacks,
		final ElementMatcher<? super MethodDescription> description,
		final BehaviourType<B> behaviourType,
		final Function<Entity, B> fallbackBehaviour,
		final Function<Object[], A> toSpongeArgs,
		final Function<A, Object[]> toVanillaArgs,
		final Function<Object, R> toSpongeResult,
		final Function<R, Object> toVanillaResult
	) {
		final var callback = callbacks.callbackOrNull(BehaviourLayer.TOP, behaviourType);
		return builder
				.method(description)
				.intercept(MethodDelegation.to(new Object() {
					@SuppressWarnings("unused")
					public Object handle(
						final @SuperMethod(nullIfImpossible = true) @Nullable Method $super,
						final @DefaultMethod(nullIfImpossible = true) @Nullable Method $default,
						final @This Entity $this,
						final @AllArguments Object[] objects
					) {
						final var original = $super != null
								? $super
								: $default != null
								? $default
								: null;
						if (callback == null) {
							if (original != null) {
								try {
									return original.invoke($this, objects);
								} catch (final IllegalAccessException | InvocationTargetException e) {
									throw EntityFactoryUtil.failInvoke(description, e);
								}
							} else {
								return toVanillaResult.apply(fallbackBehaviour.apply($this).call(toSpongeArgs.apply(objects)));
							}
						} else {
							if (original != null) {
								return toVanillaResult.apply(callback.call(
										$this,
										args -> {
											try {
												return toSpongeResult.apply(original.invoke($this, toVanillaArgs.apply(args)));
											} catch (final IllegalAccessException | InvocationTargetException e) {
												throw EntityFactoryUtil.failInvoke(description, e);
											}
										},
										toSpongeArgs.apply(objects)
										));
							} else {
								return toVanillaResult.apply(callback.call(
										$this,
										fallbackBehaviour.apply($this),
										toSpongeArgs.apply(objects)
										));
							}
						}
					}
				}));
	}
	
	private static RuntimeException failInvoke(final ElementMatcher<? super MethodDescription> description, final Exception e) {
		return new RuntimeException(String.format("Failed to invoke custom entity method (%s)", description.toString()), e);
	}
	
	private EntityFactoryUtil() {
	}
}
