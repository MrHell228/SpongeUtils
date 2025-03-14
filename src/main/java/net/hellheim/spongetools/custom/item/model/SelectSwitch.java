package net.hellheim.spongetools.custom.item.model;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.mojang.serialization.MapCodec;

public record SelectSwitch<T, P extends SelectProperty<T>>(P property, List<SelectSwitchCase<T>> cases) {
	public static final MapCodec<SelectSwitch<?, ?>> CODEC = SelectProperty.CODEC
            .dispatchMap("property", s -> s.property().codec(), Function.identity());
	
	public SelectSwitch(final P property, final List<SelectSwitchCase<T>> cases) {
		this.property = Objects.requireNonNull(property, "property");
		this.cases = Objects.requireNonNull(cases, "cases");
	}
	
	public static <T, P extends SelectProperty<T>> SelectSwitch<T, P> of(final P property, final List<SelectSwitchCase<T>> cases) {
		return new SelectSwitch<>(property, cases);
	}
}
