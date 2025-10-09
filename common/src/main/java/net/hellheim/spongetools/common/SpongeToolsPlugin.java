package net.hellheim.spongetools.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.ConfigRoot;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.FreezeRegistryEvent;
import org.spongepowered.api.event.lifecycle.RegisterBuilderEvent;
import org.spongepowered.api.event.lifecycle.RegisterDataEvent;
import org.spongepowered.api.event.lifecycle.RegisterFactoryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryEvent;
import org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.registry.RegistryEntry;
import org.spongepowered.api.registry.RegistryHolder;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.codec.list.AdventureCodecs;
import net.hellheim.spongetools.codec.list.ExtraCodecs;
import net.hellheim.spongetools.codec.list.StringRepresentableCodecs;
import net.hellheim.spongetools.common.behaviour.BehaviourManagerImpl;
import net.hellheim.spongetools.common.behaviour.BlockStateDispatcherImpl;
import net.hellheim.spongetools.common.codec.AdventureCodecsFactory;
import net.hellheim.spongetools.common.codec.ExtraCodecsFactory;
import net.hellheim.spongetools.common.codec.SpongeToolsCodecs;
import net.hellheim.spongetools.common.codec.StringRepresentableCodecsFactory;
import net.hellheim.spongetools.common.event.listener.BehaviourEventListener;
import net.hellheim.spongetools.common.event.listener.BlockStateDispatcherEventListener;
import net.hellheim.spongetools.common.event.listener.ItemEventListener;
import net.hellheim.spongetools.common.util.BlockHitResultBuilder;
import net.hellheim.spongetools.common.util.EffectUtilFactory;
import net.hellheim.spongetools.common.util.HitResultFactory;
import net.hellheim.spongetools.common.util.InteractionResultFactory;
import net.hellheim.spongetools.common.util.SignalOrientationFactory;
import net.hellheim.spongetools.common.util.StatePropertyValueFactory;
import net.hellheim.spongetools.common.util.SwingTypeFactory;
import net.hellheim.spongetools.common.web.MineHttpd;
import net.hellheim.spongetools.custom.behaviour.BehaviourManager;
import net.hellheim.spongetools.custom.behaviour.util.HitResult;
import net.hellheim.spongetools.custom.behaviour.util.InteractionResult;
import net.hellheim.spongetools.custom.behaviour.util.SignalBias;
import net.hellheim.spongetools.custom.behaviour.util.SignalOrientation;
import net.hellheim.spongetools.custom.behaviour.util.SwingType;
import net.hellheim.spongetools.custom.type.block.BlockStateDispatcher;
import net.hellheim.spongetools.custom.type.block.CustomBlockType;
import net.hellheim.spongetools.custom.type.block.EitherBlockType;
import net.hellheim.spongetools.custom.type.item.CustomItemType;
import net.hellheim.spongetools.custom.type.item.EitherItemType;
import net.hellheim.spongetools.custom.type.item.LoreProcessor;
import net.hellheim.spongetools.custom.type.item.LoreProvider;
import net.hellheim.spongetools.custom.type.item.data.CustomConsumeEffect;
import net.hellheim.spongetools.proxy.solid.PluginProxy;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.block.StatePropertyValue;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.resourcepack.meta.Metadata;
import net.hellheim.spongetools.resourcepack.meta.MetadataSection;
import net.hellheim.spongetools.util.EffectUtil;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;

@Plugin(SpongeTools.NAMESPACE)
public final class SpongeToolsPlugin implements PluginProxy {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final ResourcePackInfo.Builder INFO_BUILDER = ResourcePackInfo.resourcePackInfo();
	private static final ResourcePackRequest.Builder REQUEST_BUILDER = ResourcePackRequest.resourcePackRequest()
			.required(true)
			.replace(false);
	
	private final PluginContainer plugin;
	private final Logger logger;
	private final Optional<MineHttpd> web;
	
	private Optional<ResourcePackRequest> pack = Optional.empty();
	
	// TODO should be configurable
	private File packResult = new File("config/spongetools/pack.zip");
	private File assetsToCopy = new File("config/spongetools/copy_assets");
	private File assetsToLoad = new File("config/spongetools/load_assets");
	private int port = 8073;
	
	@Inject
	public SpongeToolsPlugin(final PluginContainer plugin, final Logger logger) {
		this.plugin = plugin;
		this.logger = logger;
		
		final ConfigRoot root = Sponge.configManager().pluginConfig(plugin);
		final ConfigurationLoader<CommentedConfigurationNode> loader = root.config();
		final ConfigurationOptions options = loader.defaultOptions();
		
		this.web = MineHttpd.tryCreate(this.logger, this.packResult, this.port);
		
		final var eventManager = Sponge.game().eventManager();
		final var lookup = MethodHandles.lookup();
		eventManager.registerListeners(this.plugin, new ItemEventListener(), lookup);
		eventManager.registerListeners(this.plugin, new BehaviourEventListener(), lookup);
		eventManager.registerListeners(this.plugin, new BlockStateDispatcherEventListener(), lookup);
		this.web.ifPresent(web -> eventManager.registerListeners(this.plugin, web.eventListener(), lookup));
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
	}
	
	@Listener
	public void registerData(final RegisterDataEvent event) {
		event.register(DataRegistration.of(CustomItemType.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(LoreProcessor.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(LoreProvider.dataKey(), ItemStack.class));
		event.register(DataRegistration.of(CustomConsumeEffect.dataKey(), ItemStack.class));
		
		Sponge.dataManager().registerBuilder(CustomItemType.class, CustomItemType.dataBuilder());
		Sponge.dataManager().registerBuilder(LoreProcessor.class, LoreProcessor.dataBuilder());
		Sponge.dataManager().registerBuilder(LoreProvider.class, LoreProvider.dataBuilder());
		Sponge.dataManager().registerBuilder(CustomConsumeEffect.class, CustomConsumeEffect.dataBuilder());
		
		SpongeToolsCodecs.bootstrap();
	}
	
	// Registry events
	
	@Listener
	public void registerServerRegistries(final RegisterRegistryEvent.EngineScoped<Server> event) {
		
		// CustomType registries
		
		final var customItems = CustomItemType.registry();
		event.register(customItems.location(), true);
		
		final var customBlocks = CustomBlockType.registry();
		event.register(customBlocks.location(), true);
		
		// EitherType registries
		
		event.register(EitherItemType.registry().location(), false, $ -> {
			final Map<ResourceKey, EitherItemType> map = new HashMap<>();
			RegistryTypes.ITEM_TYPE.get().streamEntries().forEach(e -> map.put(e.key(), EitherItemType.common(e.value())));
			customItems.get().streamEntries().forEach(e -> map.put(e.key(), EitherItemType.custom(e.value())));
			return map;
		}, customItems);
		
		event.register(EitherBlockType.registry().location(), false, $ -> {
			final Map<ResourceKey, EitherBlockType> map = new HashMap<>();
			RegistryTypes.BLOCK_TYPE.get().streamEntries().forEach(e -> map.put(e.key(), EitherBlockType.common(e.value())));
			customBlocks.get().streamEntries().forEach(e -> map.put(e.key(), EitherBlockType.custom(e.value())));
			return map;
		}, customBlocks);
		
		// Model registries
		
		event.register(Model.registry().location(), true);
		
		event.register(ItemDefinition.registry().location(), true, $ -> {
			this.logger.info(customItems.defaultHolder().get());
			this.logger.info($);
			this.logger.info($ == customItems.defaultHolder().get());
			final Map<ResourceKey, ItemDefinition> map = new HashMap<>();
			customItems.get().streamEntries().forEach(e -> {
				e.value().model().ifPresent(model -> {
					map.put(e.key(), model);
				});
			});
			
			return map;
		}, customItems);
		
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
								BlockDefinition.CODEC,
								BlockDefinition.registry(),
								blockKey
								).getOrThrow(RuntimeException::new);
						
						for (final CustomBlockType customBlockType : customBlockTypes) {
							model = model.expandWith(customBlockType.state(), customBlockType.model());
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
	}
	
	@Listener
	public void registerServerRegistryValues(final RegisterRegistryValueEvent.EngineScoped<Server> event) {
		// TODO Load & Register CustomItemTypes from configs
	}
	
	@Listener
	public void assembleResourcePack(final FreezeRegistryEvent.Post.EngineScoped<Server> event) throws IOException {
		final RegistryHolder holder = event.holder();
		this.logger.info(RegistryTypes.ENCHANTMENT_TYPE.defaultHolder().get());
		this.logger.info(holder);
		this.logger.info(holder == RegistryTypes.ENCHANTMENT_TYPE.defaultHolder().get());
		final Map<ResourceKey, ItemDefinition> items = holder.registry(ItemDefinition.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		final Map<ResourceKey, BlockDefinition> blocks = holder.registry(BlockDefinition.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		final Map<ResourceKey, Model> models = holder.registry(Model.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		// TODO
		try (final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(this.packResult))) {
			final MetadataSection packmeta = MetadataSection.pack(
					Component.text("Resource pack made with SpongeTools"), 46);
			this.writeEntry(out, Metadata.CODEC, "pack.mcmeta", "pack.mcmeta", packmeta.asMetadata());
			
			final File copy = this.assetsToCopy;
			if (copy.exists() && copy.isDirectory()) {
				final String pattern = Pattern.quote(copy.getPath());
				this.writeFile(out, copy, path -> path.replaceFirst(pattern, "assets"));
			}
			
			for (final Map.Entry<ResourceKey, ItemDefinition> e : items.entrySet()) {
				this.writeEntry(out, ItemDefinition.CODEC, "ItemDefinition", "items", e.getKey(), e.getValue());
			}
			
			for (final Map.Entry<ResourceKey, BlockDefinition> e : blocks.entrySet()) {
				this.writeEntry(out, BlockDefinition.CODEC, "BlockDefinition", "blockstates", e.getKey(), e.getValue());
			}
			
			for (final Map.Entry<ResourceKey, Model> e : models.entrySet()) {
				this.writeEntry(out, Model.CODEC, "Model", "models", e.getKey(), e.getValue());
			}
		}
		
		if (this.web.isEmpty()) {
			return;
		}
		
		if (Sponge.isServerAvailable()) {
			this.assembleResourcePackRequest(Sponge.server());
		}
	}
	
	@Listener
	public void assembleResourcePackRequestOnServerStart(final StartedEngineEvent<Server> event) {
		this.assembleResourcePackRequest(event.engine());
	}
	
	private void assembleResourcePackRequest(final Server server) {
		server.boundAddress().ifPresentOrElse(
				address -> {
					INFO_BUILDER.uri(URI.create("http://" + address.getHostString() + ":" + this.port + "/resourcepack_id"))
							.computeHashAndBuild()
							.handle((info, ex) -> info != null
									? REQUEST_BUILDER.packs(info).build()
									: null)
							.thenApply(Optional::ofNullable)
							.thenAccept(pack -> {
								this.pack = pack;
								pack.ifPresent(request ->
										server.streamOnlinePlayers().forEach(player ->
												player.sendResourcePacks(request)));
							});
				},
				() -> {
					this.logger.error("Server does not have bound address");
				});
	}
	
	private void writeFile(
		final ZipOutputStream out, final File file, final UnaryOperator<String> pathToName
	) throws IOException {
		String entryName = pathToName.apply(file.getPath()).replace(File.separatorChar, '/');
		if (file.isFile()) {
			final ZipEntry entry = new ZipEntry(entryName);
			entry.setTime(file.lastModified());
			out.putNextEntry(entry);
			Files.copy(file.toPath(), out);
			out.closeEntry();
		} else if (file.isDirectory()) {
			if (!entryName.endsWith("/")) {
				entryName += "/";
			}
			
			final ZipEntry dirEntry = new ZipEntry(entryName);
			dirEntry.setTime(file.lastModified());
			out.putNextEntry(dirEntry);
			out.closeEntry();
			
			for (final File subfile : file.listFiles()) {
				writeFile(out, subfile, pathToName);
			}
		} else {
			this.logger.warn("Weird file found while zipping pack: " + file.getAbsolutePath());
		}
	}
	
	private <T> void writeEntry(
		final ZipOutputStream out, final Codec<T> codec,
		final String name, final String prefix,
		final ResourceKey key, final T value
	) throws IOException {
		this.writeEntry(out, codec, name,
				"assets/" + key.namespace() + "/" + prefix + "/" + key.value() + ".json", value);
	}
	
	private <T> void writeEntry(
		final ZipOutputStream out, final Codec<T> codec,
		final String name, final String entry, final T value
	) throws IOException {
		final DataResult<JsonElement> encoded = codec.encodeStart(JsonOps.INSTANCE, value);
		if (encoded.isError()) {
			this.logger.warn("Failed to encode " + name + ": " + encoded.error().get().message());
			return;
		}
		
		out.putNextEntry(new ZipEntry(entry));
		out.write(GSON.toJson(encoded.result().get()).getBytes());
		out.closeEntry();
	}
	
	@Listener
	public void sendResourcePack(final ServerSideConnectionEvent.Join event) {
		this.logger.info("Player joined");
		this.pack.ifPresent(pack -> {
			this.logger.info("Sending SpongeTools pack " + pack);
			event.player().sendResourcePacks(pack);
		});
	}
	
	public <T> DataResult<T> decode(
		final Codec<T> codec, final RegistryType<T> registry, final ResourceKey key
	) {
		File file = this.file(registry, key, ".json");
		if (file.exists() && file.isFile()) {
			try {
				final JsonReader reader = new JsonReader(new FileReader(file));
				final JsonObject json = GSON.fromJson(reader, JsonObject.class);
				return codec.decode(JsonOps.INSTANCE, json).map(Pair::getFirst);
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
	
	@Override
	public PluginContainer plugin() {
		return this.plugin;
	}
}
