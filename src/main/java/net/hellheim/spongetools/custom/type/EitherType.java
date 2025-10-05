package net.hellheim.spongetools.custom.type;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Either;

@SuppressWarnings("unchecked")
public interface EitherType<COMMON, CUSTOM, EITHER extends EitherType<COMMON, CUSTOM, EITHER>> {
	
	Optional<COMMON> common();
	
	Optional<CUSTOM> custom();
	
	Either<COMMON, CUSTOM> either();
	
	boolean isCommon();
	
	boolean isCommon(COMMON common);
	
	boolean isCommon(Supplier<? extends COMMON> commonSupplier);
	
	boolean isAnyCommon(final COMMON... commons);
	
	boolean isAnyCommon(final Supplier<? extends COMMON>... commonSuppliers);
	
	boolean isAnyCommon(final Iterable<? extends COMMON> commonIterable);
	
	boolean isCustom();
	
	boolean isCustom(CUSTOM custom);
	
	boolean isCustom(Supplier<? extends CUSTOM> customSupplier);
	
	boolean isAnyCustom(final CUSTOM... customs);
	
	boolean isAnyCustom(final Supplier<? extends CUSTOM>... customSuppliers);
	
	boolean isAnyCustom(final Iterable<? extends CUSTOM> customIterable);
	
	boolean is(EITHER either);
	
	default boolean isAny(final EITHER... eithers) {
		return Arrays.stream(eithers).anyMatch(this::is);
	}
	
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
		default boolean isCommon() {
			return true;
		}
		
		@Override
		default boolean isCommon(final COMMON common) {
			return Objects.equals(this.get(), common);
		}
		
		@Override
		default boolean isCommon(final Supplier<? extends COMMON> commonSupplier) {
			return this.isCommon(commonSupplier.get());
		}
		
		@Override
		default boolean isAnyCommon(final COMMON... commons) {
			return Arrays.stream(commons).anyMatch(this::isCommon);
		}
		
		@Override
		default boolean isAnyCommon(final Supplier<? extends COMMON>... commonSuppliers) {
			return Arrays.stream(commonSuppliers).map(Supplier::get).anyMatch(this::isCommon);
		}
		
		@Override
		default boolean isAnyCommon(final Iterable<? extends COMMON> commonIterable) {
			return Streams.stream(commonIterable).anyMatch(this::isCommon);
		}
		
		@Override
		default boolean isCustom() {
			return false;
		}
		
		@Override
		default boolean isCustom(final CUSTOM custom) {
			return false;
		}
		
		@Override
		default boolean isCustom(final Supplier<? extends CUSTOM> customSupplier) {
			return false;
		}
		
		@Override
		default boolean isAnyCustom(final CUSTOM... customs) {
			return false;
		}
		
		@Override
		default boolean isAnyCustom(final Supplier<? extends CUSTOM>... customSuppliers) {
			return false;
		}
		
		@Override
		default boolean isAnyCustom(final Iterable<? extends CUSTOM> customIterable) {
			return false;
		}
		
		@Override
		default boolean is(final EITHER either) {
			return either.isCommon(this.get());
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
		default boolean isCommon() {
			return false;
		}
		
		@Override
		default boolean isCommon(final COMMON common) {
			return false;
		}
		
		@Override
		default boolean isCommon(final Supplier<? extends COMMON> commonSupplier) {
			return false;
		}
		
		@Override
		default boolean isAnyCommon(final COMMON... commons) {
			return false;
		}
		
		@Override
		default boolean isAnyCommon(final Supplier<? extends COMMON>... commonSuppliers) {
			return false;
		}
		
		@Override
		default boolean isAnyCommon(final Iterable<? extends COMMON> commonIterable) {
			return false;
		}
		
		@Override
		default boolean isCustom() {
			return true;
		}
		
		@Override
		default boolean isCustom(final CUSTOM custom) {
			return Objects.equals(this.get(), custom);
		}
		
		@Override
		default boolean isCustom(final Supplier<? extends CUSTOM> customSupplier) {
			return this.isCustom(customSupplier.get());
		}
		
		@Override
		default boolean isAnyCustom(final CUSTOM... customs) {
			return Arrays.stream(customs).anyMatch(this::isCustom);
		}
		
		@Override
		default boolean isAnyCustom(final Supplier<? extends CUSTOM>... customSuppliers) {
			return Arrays.stream(customSuppliers).map(Supplier::get).anyMatch(this::isCustom);
		}
		
		@Override
		default boolean isAnyCustom(final Iterable<? extends CUSTOM> customIterable) {
			return Streams.stream(customIterable).anyMatch(this::isCustom);
		}
		
		@Override
		default boolean is(final EITHER either) {
			return either.isCustom(this.get());
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
