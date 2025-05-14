package net.hellheim.spongetools.custom.type;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Either;

public interface EitherType<COMMON, CUSTOM, EITHER extends EitherType<COMMON, CUSTOM, EITHER>> {
	
	boolean isCommon();
	
	boolean isCustom();
	
	Optional<COMMON> common();
	
	Optional<CUSTOM> custom();
	
	Either<COMMON, CUSTOM> either();
	
	default EITHER mapCommon(final Function<? super COMMON, ? extends COMMON> commonMapper) {
		return this.map(commonMapper, Function.identity());
	}
	
	default EITHER mapCustom(final Function<? super CUSTOM, ? extends CUSTOM> customMapper) {
		return this.map(Function.identity(), customMapper);
	}
	
	EITHER map(Function<? super COMMON, ? extends COMMON> commonMapper, Function<? super CUSTOM, ? extends CUSTOM> customMapper);
	
	<T> T apply(Function<? super COMMON, ? extends T> commonMapper, Function<? super CUSTOM, ? extends T> customMapper);
	
	void accept(Consumer<? super COMMON> commonAction, Consumer<? super CUSTOM> customAction);
	
	boolean test(Predicate<? super COMMON> commonTest, Predicate<? super CUSTOM> customTest);
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object obj);
	
	interface Common<COMMON, CUSTOM, EITHER extends EitherType<COMMON, CUSTOM, EITHER>> extends EitherType<COMMON, CUSTOM, EITHER> {
		
		COMMON get();
		
		@Override
		default boolean isCommon() {
			return true;
		}
		
		@Override
		default boolean isCustom() {
			return false;
		}
		
		@Override
		default Optional<COMMON> common() {
			return Optional.of(this.get());
		}
		
		@Override
		default Optional<CUSTOM> custom() {
			return Optional.empty();
		}
		
		@Override
		default Either<COMMON, CUSTOM> either() {
			return Either.left(this.get());
		}
		
		@Override
		default <T> T apply(
			final Function<? super COMMON, ? extends T> commonMapper,
			final Function<? super CUSTOM, ? extends T> customMapper
		) {
			return commonMapper.apply(this.get());
		}
		
		@Override
		default void accept(
			final Consumer<? super COMMON> commonAction,
			final Consumer<? super CUSTOM> customAction
		) {
			commonAction.accept(this.get());
		}
		
		@Override
		default boolean test(
			final Predicate<? super COMMON> commonTest,
			final Predicate<? super CUSTOM> customTest
		) {
			return commonTest.test(this.get());
		}
	}
	
	interface Custom<COMMON, CUSTOM, EITHER extends EitherType<COMMON, CUSTOM, EITHER>> extends EitherType<COMMON, CUSTOM, EITHER> {
		
		CUSTOM get();
		
		@Override
		default boolean isCommon() {
			return false;
		}
		
		@Override
		default boolean isCustom() {
			return true;
		}
		
		@Override
		default Optional<COMMON> common() {
			return Optional.empty();
		}
		
		@Override
		default Optional<CUSTOM> custom() {
			return Optional.of(this.get());
		}
		
		@Override
		default Either<COMMON, CUSTOM> either() {
			return Either.right(this.get());
		}
		
		@Override
		default <T> T apply(
			final Function<? super COMMON, ? extends T> commonMapper,
			final Function<? super CUSTOM, ? extends T> customMapper
		) {
			return customMapper.apply(this.get());
		}
		
		@Override
		default void accept(
			final Consumer<? super COMMON> commonAction,
			final Consumer<? super CUSTOM> customAction
		) {
			customAction.accept(this.get());
		}
		
		@Override
		default boolean test(
			final Predicate<? super COMMON> commonTest,
			final Predicate<? super CUSTOM> customTest
		) {
			return customTest.test(this.get());
		}
	}
}
