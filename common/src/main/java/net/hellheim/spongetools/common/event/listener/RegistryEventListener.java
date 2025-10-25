package net.hellheim.spongetools.common.event.listener;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterBuilderEvent;
import org.spongepowered.api.event.lifecycle.RegisterDataEvent;
import org.spongepowered.api.event.lifecycle.RegisterFactoryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.registry.RegistryTypes;

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
import net.hellheim.spongetools.common.codec.AdventureCodecsFactory;
import net.hellheim.spongetools.common.codec.ExtraCodecsFactory;
import net.hellheim.spongetools.common.codec.SpongeToolsCodecs;
import net.hellheim.spongetools.common.codec.StringRepresentableCodecsFactory;
import net.hellheim.spongetools.common.util.BlockHitResultBuilder;
import net.hellheim.spongetools.common.util.EffectUtilFactory;
import net.hellheim.spongetools.common.util.HitResultFactory;
import net.hellheim.spongetools.common.util.InteractionResultFactory;
import net.hellheim.spongetools.common.util.ItemTypeBuilderImpl;
import net.hellheim.spongetools.common.util.ItemTypeUtil;
import net.hellheim.spongetools.common.util.SignalOrientationFactory;
import net.hellheim.spongetools.common.util.StatePropertyValueFactory;
import net.hellheim.spongetools.common.util.SwingTypeFactory;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.CustomBlockType;
import net.hellheim.spongetools.custom.type.block.EitherBlockType;
import net.hellheim.spongetools.custom.type.item.ItemArchetype;
import net.hellheim.spongetools.custom.type.item.ItemArchetypes;
import net.hellheim.spongetools.custom.type.item.ItemTypeBuilder;
import net.hellheim.spongetools.custom.type.item.LoreProcessor;
import net.hellheim.spongetools.custom.type.item.LoreProvider;
import net.hellheim.spongetools.custom.type.item.data.CustomConsumeEffect;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.block.StateOps;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.hellheim.spongetools.resourcepack.block.Variant;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.util.EffectUtil;

public final class RegistryEventListener {
	
	@SuppressWarnings("unused")
	private final Logger logger;
	private final File assetsToLoad;
	
	public RegistryEventListener(final Logger logger, final File assetsToLoad) {
		this.logger = logger;
		this.assetsToLoad = assetsToLoad;
	}
	
	@Listener
	public void registerFactories(final RegisterFactoryEvent event) {
		event.register(StatePropertyValue.Factory.class, new StatePropertyValueFactory());
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
		event.register(ItemTypeBuilder.class, ItemTypeBuilderImpl::new);
	}
	
	@Listener
	public void registerData(final RegisterDataEvent event) {
		event.register(DataRegistration.of(LoreProcessor.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(LoreProvider.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(CustomConsumeEffect.dataKey(), ItemStack.class));
		
		Sponge.dataManager().registerBuilder(LoreProcessor.class, LoreProcessor.dataBuilder());
		Sponge.dataManager().registerBuilder(LoreProvider.class, LoreProvider.dataBuilder());
		Sponge.dataManager().registerBuilder(CustomConsumeEffect.class, CustomConsumeEffect.dataBuilder());
		
		SpongeToolsCodecs.bootstrap();
	}
	
	@Listener
	public void registerRegistries(final RegisterRegistryEvent.GameScoped event) {
		
		// CustomType registries
		
		final var customBlocks = CustomBlockType.registry();
		event.register(customBlocks.location(), true);
		
		// EitherType registries
		
		event.register(EitherBlockType.registry().location(), false, $ -> {
			final Map<ResourceKey, EitherBlockType> map = new HashMap<>();
			RegistryTypes.BLOCK_TYPE.get().streamEntries().forEach(e -> map.put(e.key(), EitherBlockType.common(e.value())));
			customBlocks.get().streamEntries().forEach(e -> map.put(e.key(), EitherBlockType.custom(e.value())));
			return map;
		}, customBlocks);
		
		// Resourcepack-based registries
		
		event.register(Model.registry().location(), true, $ -> {
			final Map<ResourceKey, Model> map = new HashMap<>();
			
			// TODO add items too
			
			customBlocks.get().streamEntries().forEach(e -> {
				e.value().companions().forEach((keyTransformer, model) -> {
					map.put(keyTransformer.apply(e.key()), model);
				});
			});
			return map;
		}, customBlocks);
		
		event.register(ItemDefinition.registry().location(), true);
		
		event.register(BlockDefinition.registry().location(), true, $ -> {
			// TODO Can this be in some nicer place?
			BlockStateDispatcherEventListener.fireEvent(event);
			
			final Map<ResourceKey, BlockDefinition> map = new HashMap<>();
			customBlocks.get().stream()
					.filter(block -> !block.model().isEmpty())
					.collect(Collectors.groupingBy(block -> block.state().type()))
					.forEach((blockType, customBlockTypes) -> {
						final ResourceKey blockKey = blockType.key(RegistryTypes.BLOCK_TYPE);
						BlockDefinition model = this.decode(
								ops -> StateOps.of(ops, blockType),
								BlockDefinition.CODEC,
								BlockDefinition.registry(),
								blockKey
								).getOrThrow(RuntimeException::new);
						
						for (final CustomBlockType customBlockType : customBlockTypes) {
							final @Nullable Variant modelToAdd = customBlockType.model().orElse(null);
							if (modelToAdd != null) {
								model = model.expandWith(customBlockType.state(), modelToAdd);
							}
						}
						
						map.put(blockKey, model);
					});
			
			return map;
		}, customBlocks);
		
		// Other registries
		
		event.register(LoreProcessor.registry().location(), true, () -> Map.of(
				SpongeTools.key("plain"), LoreProcessor.Plain.CODEC,
				SpongeTools.key("apply_fallback_style"), LoreProcessor.ApplyFallbackStyle.CODEC,
				SpongeTools.key("separated"), LoreProcessor.Separated.CODEC
				));
		
		event.register(LoreProvider.registry().location(), true, () -> Map.of(
				SpongeTools.key("plain"), LoreProvider.Plain.CODEC
				));
		
		event.register(CustomConsumeEffect.registry().location(), true);
		
		event.register(ItemArchetype.registry().location(), true, $ -> {
			final Map<ResourceKey, ItemArchetype> map = new HashMap<>();
			map.put(ItemArchetypes.BLOCK.location(), ItemTypeUtil.Archetypes.BLOCK);
			map.put(ItemArchetypes.FISHING_ROD.location(), ItemTypeUtil.Archetypes.FISHING_ROD);
			map.put(ItemArchetypes.PLAIN.location(), ItemTypeUtil.Archetypes.PLAIN);
			return map;
		});
	}
	
	@Listener
	public void registerServerRegistryValues(final RegisterRegistryValueEvent.GameScoped event) {
		// TODO Load & Register custom ItemTypes from configs
	}
	
	/* Is this needed?
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
		
		return DataResult.error(() -> "Unknown file extension");
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
