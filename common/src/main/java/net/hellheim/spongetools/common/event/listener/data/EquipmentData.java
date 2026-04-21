package net.hellheim.spongetools.common.event.listener.data;

import java.util.Optional;
import java.util.function.Supplier;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.common.SpongeCommon;
import org.spongepowered.common.adventure.SpongeAdventure;
import org.spongepowered.common.data.provider.DataProviderRegistrator;

import com.google.common.base.Suppliers;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.common.util.Converter;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public final class EquipmentData {
	
	private static final Supplier<Equippable> DEFAULT = Suppliers.memoize(() -> Equippable.builder(EquipmentSlot.MAINHAND).build());
	
	public static final void register(final DataProviderRegistrator registrator) {
		registrator		
			.asMutable(net.minecraft.world.item.ItemStack.class)
				.create(SpongeTools.Keys.EQUIPMENT_TYPE)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null ? null : Converter.asSponge(equip.slot());
					})
					.set((h, v) -> {
						if (v == null) {
							h.remove(DataComponents.EQUIPPABLE);
						} else {
							h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
									Converter.asVanilla(v),
									eq.equipSound(),
									eq.assetId(),
									eq.cameraOverlay(),
									eq.allowedEntities(),
									eq.dispensable(),
									eq.swappable(),
									eq.damageOnHurt()));
						}
					})
					.resetOnDelete((EquipmentType) null)
				.create(SpongeTools.Keys.EQUIPMENT_SOUND)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null
								? null
								: Converter.asSponge(equip.equipSound().value());
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								Optional.ofNullable(v).map(sound -> resolveSoundEvent(sound)).orElseGet(() -> DEFAULT.get().equipSound()),
								eq.assetId(),
								eq.cameraOverlay(),
								eq.allowedEntities(),
								eq.dispensable(),
								eq.swappable(),
								eq.damageOnHurt()));
					})
					.resetOnDelete((SoundType) null)
				.create(SpongeTools.Keys.EQUIPMENT_ASSET)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null
								? null
								: equip.assetId().map(key -> Converter.asSponge(key.location())).orElse(null);
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								eq.equipSound(),
								Optional.ofNullable(v).map(key -> net.minecraft.resources.ResourceKey.create(
										EquipmentAssets.ROOT_ID,
										Converter.asVanilla(key))),
								eq.cameraOverlay(),
								eq.allowedEntities(),
								eq.dispensable(),
								eq.swappable(),
								eq.damageOnHurt()));
					})
					.resetOnDelete((ResourceKey) null)
				.create(SpongeTools.Keys.EQUIPMENT_CAMERA_OVERLAY)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null
								? null
								: equip.cameraOverlay().map(key -> Converter.asSponge(key)).orElse(null);
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								eq.equipSound(),
								eq.assetId(),
								Optional.ofNullable(v).map(Converter::asVanilla),
								eq.allowedEntities(),
								eq.dispensable(),
								eq.swappable(),
								eq.damageOnHurt()));
					})
					.resetOnDelete((ResourceKey) null)
				.create(SpongeTools.Keys.EQUIPMENT_DISPENSABLE)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null ? null : equip.dispensable();
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								eq.equipSound(),
								eq.assetId(),
								eq.cameraOverlay(),
								eq.allowedEntities(),
								Optional.ofNullable(v).orElseGet(() -> DEFAULT.get().dispensable()),
								eq.swappable(),
								eq.damageOnHurt()));
					})
					.resetOnDelete((Boolean) null)
				.create(SpongeTools.Keys.EQUIPMENT_SWAPPABLE)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null ? null : equip.swappable();
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								eq.equipSound(),
								eq.assetId(),
								eq.cameraOverlay(),
								eq.allowedEntities(),
								eq.dispensable(),
								Optional.ofNullable(v).orElseGet(() -> DEFAULT.get().swappable()),
								eq.damageOnHurt()));
					})
					.resetOnDelete((Boolean) null)
				.create(SpongeTools.Keys.EQUIPMENT_DAMAGEABLE)
					.get(h -> {
						final @Nullable Equippable equip = h.get(DataComponents.EQUIPPABLE);
						return equip == null ? null : equip.damageOnHurt();
					})
					.set((h, v) -> {
						h.update(DataComponents.EQUIPPABLE, DEFAULT.get(), eq -> new Equippable(
								eq.slot(),
								eq.equipSound(),
								eq.assetId(),
								eq.cameraOverlay(),
								eq.allowedEntities(),
								eq.dispensable(),
								eq.swappable(),
								Optional.ofNullable(v).orElseGet(() -> DEFAULT.get().damageOnHurt())));
					})
					.resetOnDelete((Boolean) null);
	}
	
	private static Holder<SoundEvent> resolveSoundEvent(final SoundType sound) {
		final ResourceLocation soundKey = SpongeAdventure.asVanilla(sound.key());
		final var registry = SpongeCommon.vanillaRegistry(Registries.SOUND_EVENT);
		final SoundEvent event = registry.getOptional(soundKey)
				.orElseGet(() -> SoundEvent.createVariableRangeEvent(soundKey));
		return registry.wrapAsHolder(event);
	}
	
	private EquipmentData() {
	}
}
