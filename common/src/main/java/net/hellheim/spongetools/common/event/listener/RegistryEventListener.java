package net.hellheim.spongetools.common.event.listener;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSoundGroup;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.type.ItemActionType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterBuilderEvent;
import org.spongepowered.api.event.lifecycle.RegisterDataEvent;
import org.spongepowered.api.event.lifecycle.RegisterFactoryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.common.data.provider.DataProviderRegistratorBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.StringRepresentableCodecs;
import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.hellheim.spongetools.common.behaviour.BehaviourManagerImpl;
import net.hellheim.spongetools.common.behaviour.BlockStateDispatcherImpl;
import net.hellheim.spongetools.common.builder.BlockHitResultBuilder;
import net.hellheim.spongetools.common.builder.BlockSoundGroupBuilderImpl;
import net.hellheim.spongetools.common.builder.BlockTypeBuilderImpl;
import net.hellheim.spongetools.common.builder.ItemTypeBuilderImpl;
import net.hellheim.spongetools.common.codec.AdventureCodecsFactory;
import net.hellheim.spongetools.common.codec.ExtraCodecsFactory;
import net.hellheim.spongetools.common.codec.SpongeToolsCodecs;
import net.hellheim.spongetools.common.codec.StringRepresentableCodecsFactory;
import net.hellheim.spongetools.common.factory.EffectUtilFactory;
import net.hellheim.spongetools.common.factory.HitResultFactory;
import net.hellheim.spongetools.common.factory.InteractionResultFactory;
import net.hellheim.spongetools.common.factory.SignalOrientationFactory;
import net.hellheim.spongetools.common.factory.StatePropertiesFactory;
import net.hellheim.spongetools.common.factory.StatePropertyValueFactory;
import net.hellheim.spongetools.common.factory.SwingTypeFactory;
import net.hellheim.spongetools.common.util.BlockTypeUtil;
import net.hellheim.spongetools.common.util.Converter;
import net.hellheim.spongetools.common.util.CustomConsumeEffect;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.hellheim.spongetools.custom.type.block.BlockArchetype;
import net.hellheim.spongetools.custom.type.block.BlockArchetypes;
import net.hellheim.spongetools.custom.type.block.BlockSoundGroupBuilder;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.BlockTypeBuilder;
import net.hellheim.spongetools.custom.type.block.BlockTypeKeys;
import net.hellheim.spongetools.custom.type.block.ModeledBlock;
import net.hellheim.spongetools.custom.type.block.StateProperties;
import net.hellheim.spongetools.custom.type.item.CustomItemAction;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.ItemArchetypes;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;
import net.hellheim.spongetools.custom.type.item.LoreProcessor;
import net.hellheim.spongetools.custom.type.item.LoreProvider;
import net.hellheim.spongetools.custom.type.item.ModeledItem;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.block.StateOps;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.util.EffectUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class RegistryEventListener {
	
	private final Logger logger;
	private final File assetsToLoad;
	
	public RegistryEventListener(final Logger logger, final File assetsToLoad) {
		this.logger = logger;
		this.assetsToLoad = assetsToLoad;
	}
	
	@Listener
	public void registerFactories(final RegisterFactoryEvent event) {
		event.register(StatePropertyValue.Factory.class, new StatePropertyValueFactory());
		event.register(StateProperties.Factory.class, new StatePropertiesFactory());
		event.register(CustomItemAction.Factory.class, new CustomConsumeEffect.FactoryImpl());
		event.register(SwingType.Factory.class, new SwingTypeFactory());
		event.register(InteractionResult.Factory.class, new InteractionResultFactory());
		event.register(HitResult.Factory.class, new HitResultFactory());
		event.register(SignalOrientation.Factory.class, new SignalOrientationFactory());
		event.register(SignalBias.Factory.class, new SignalOrientationFactory.BiasFactory());
		event.register(EffectUtil.Factory.class, new EffectUtilFactory());
		event.register(BehaviourManager.class, new BehaviourManagerImpl());
		event.register(BlockStateDispatcher.class, new BlockStateDispatcherImpl());
		event.register(ExtraCodecs.Factory.class, new ExtraCodecsFactory());
		event.register(AdventureCodecs.Factory.class, new AdventureCodecsFactory());
		event.register(StringRepresentableCodecs.Factory.class, new StringRepresentableCodecsFactory());
	}
	
	@Listener
	public void registerBuilders(final RegisterBuilderEvent event) {
		event.register(HitResult.BlockHitResult.Builder.class, BlockHitResultBuilder::new);
		event.register(BlockSoundGroupBuilder.class, BlockSoundGroupBuilderImpl::new);
		event.register(ItemTypeBuilder.class, ItemTypeBuilderImpl::new);
		event.register(BlockTypeBuilder.class, BlockTypeBuilderImpl::new);
	}
	
	@Listener
	public void registerData(final RegisterDataEvent event) {
		event.register(DataRegistration.of(LoreProcessor.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(LoreProvider.dataKey(), ItemStack.class));
		
		Sponge.dataManager().registerBuilder(LoreProcessor.class, LoreProcessor.dataBuilder());
		Sponge.dataManager().registerBuilder(LoreProvider.class, LoreProvider.dataBuilder());
		
		SpongeToolsCodecs.bootstrap();
		
		new DataProviderRegistratorBuilder() {
			@Override
			protected void registerProviders() {
				this.registrator
					.asImmutable(Block.class)
						.create(BlockTypeKeys.LOOT_TABLE)
							.get(v -> v.getLootTable()
								.map(net.minecraft.resources.ResourceKey::location)
								.map(Converter::asSponge)
								.orElse(null))
						.create(BlockTypeKeys.MAP_COLOR)
							.get(v -> Converter.asSponge(v.defaultMapColor()))
						.create(BlockTypeKeys.SPEED_FACTOR)
							.get(v -> (double) v.getSpeedFactor())
						.create(BlockTypeKeys.JUMP_FACTOR)
							.get(v -> (double) v.getJumpFactor())
						.create(BlockTypeKeys.FRICTION_FACTOR)
							.get(v -> (double) v.getFriction())
					.asImmutable(BlockState.class)
						.create(BlockTypeKeys.REQUIRE_TOOL)
							.get(v -> v.requiresCorrectToolForDrops())
						.create(BlockTypeKeys.SOUND_GROUP)
							.get(v -> (BlockSoundGroup) v.getSoundType())
						;
			}
		}.register();;
	}
	
	@Listener
	public void registerRegistries(final RegisterRegistryEvent.GameScoped event) {
		
		// Resourcepack-based registries
		
		event.register(ModeledBlock.registry().location(), true);
		event.register(ModeledItem.registry().location(), true);
		event.register(Model.registry().location(), true);
		event.register(BlockDefinition.registry().location(), true);
		event.register(ItemDefinition.registry().location(), true);
		
		// Other registries
		
		event.register(LoreProcessor.registry().location(), true, () -> Map.of(
				SpongeTools.key("plain"), LoreProcessor.Plain.CODEC,
				SpongeTools.key("apply_fallback_style"), LoreProcessor.ApplyFallbackStyle.CODEC,
				SpongeTools.key("separated"), LoreProcessor.Separated.CODEC
				));
		
		event.register(LoreProvider.registry().location(), true, () -> Map.of(
				SpongeTools.key("plain"), LoreProvider.Plain.CODEC
				));
		
		event.register(CustomItemAction.registry().location(), true);
		
		// Archetypes
		
		event.register(BlockArchetype.registry().location(), true, $ -> {
			final Map<ResourceKey, BlockArchetype> map = new HashMap<>();
			map.put(BlockArchetypes.DEFAULT.location(), BlockTypeUtil.Archetypes.DEFAULT);
			map.put(BlockArchetypes.SCAFFOLDING.location(), BlockTypeUtil.Archetypes.SCAFFOLDING);
			return map;
		});
		
		event.register(ItemArchetype.registry().location(), true, $ -> {
			final Map<ResourceKey, ItemArchetype> map = new HashMap<>();
			map.put(ItemArchetypes.BLOCK.location(), ItemTypeUtil.Archetypes.BLOCK);
			map.put(ItemArchetypes.DEFAULT.location(), ItemTypeUtil.Archetypes.DEFAULT);
			map.put(ItemArchetypes.FISHING_ROD.location(), ItemTypeUtil.Archetypes.FISHING_ROD);
			map.put(ItemArchetypes.SCAFFOLDING.location(), ItemTypeUtil.Archetypes.SCAFFOLDING);
			return map;
		});
	}
	
	@Listener
	public void registerRegistryValues(final RegisterRegistryValueEvent.GameScoped event) {
		
		// TODO Load & Register custom ItemTypes from configs
		
		event.registry(RegistryTypes.ITEM_ACTION_TYPE, ($, step) ->
			step.register(SpongeTools.key("custom"), (ItemActionType) (Object) CustomConsumeEffect.TYPE));
		
		final var modeledBlocks = ModeledBlock.registry();
		final var modeledItems = ModeledItem.registry();
		
		event.registry(Model.registry(), ($, step) -> {
			modeledBlocks.get().stream().forEach(block ->
				block.models().forEach((key, model) ->
					step.register(key, model)));
			
			modeledItems.get().stream().forEach(item ->
				item.models().forEach((key, model) ->
					step.register(key, model)));
		}, modeledBlocks, modeledItems);
		
		event.registry(RegistryTypes.BLOCK_TYPE, ($, step) ->
			modeledBlocks.get().streamEntries().forEach(e -> step.register(e.key(), e.value().type())),
			modeledBlocks);
		
		event.registry(RegistryTypes.ITEM_TYPE, ($, step) ->
			modeledItems.get().streamEntries().forEach(e -> step.register(e.key(), e.value().type())),
			modeledItems);
		
		event.registry(BlockDefinition.registry(), ($, step) ->
			BlockStateEventListener.fireEvents(event.game(), event.cause(), this.logger)
					.entrySet().stream()
					.collect(Collectors.groupingBy(e -> e.getKey().type()))
					.forEach((blockType, entries) -> {
						final ResourceKey blockKey = blockType.key(RegistryTypes.BLOCK_TYPE);
						BlockDefinition model = this.decode(
								ops -> StateOps.of(ops, blockType),
								BlockDefinition.CODEC,
								BlockDefinition.registry(),
								blockKey
								).getOrThrow(RuntimeException::new);
						
						for (final var entry : entries) {
							model = model.expandWith(entry.getKey(), entry.getValue());
						}
						
						step.register(blockKey, model);
					}),
			modeledBlocks, RegistryTypes.BLOCK_TYPE);
		
		event.registry(ItemDefinition.registry(), ($, step) ->
			modeledItems.get().streamEntries().forEach(e -> step.register(e.key(), e.value().definition())),
			modeledItems, RegistryTypes.ITEM_TYPE);
	}
	
	/* TODO Is this needed?
	private <T> DataResult<T> decode(
		final Codec<T> codec, final RegistryType<T> registry, final ResourceKey key
	) {
		return this.decode(UnaryOperator.identity(), codec, registry, key);
	}
	*/
	
	private <T> DataResult<T> decode(
		final UnaryOperator<DynamicOps<JsonElement>> opsModifier,
		final Codec<T> codec, final RegistryType<T> registry, final ResourceKey key
	) {
		File file = this.file(registry, key, ".json");
		if (file.exists() && file.isFile()) {
			try {
				final JsonReader reader = new JsonReader(new FileReader(file));
				final JsonObject json = SpongeToolsPlugin.GSON.fromJson(reader, JsonObject.class);
				return codec.decode(opsModifier.apply(JsonOps.INSTANCE), json).map(Pair::getFirst);
			} catch (final Exception e) {
				return DataResult.error(e::getMessage);
			}
		}
		
		return DataResult.error(() -> "Could not find asset file " + file.getPath());
	}
	
	private File file(
		final RegistryType<?> registry, final ResourceKey key, final String suffix
	) {
		final char separator = File.separatorChar;
		return new File(this.assetsToLoad,
				key.namespace() + separator +
				registry.location().value().replace('/', separator) + separator + 
				key.value().replace('/', separator) + suffix
				);
	}
}
