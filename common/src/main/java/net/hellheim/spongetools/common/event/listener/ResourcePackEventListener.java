package net.hellheim.spongetools.common.event.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
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
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.FreezeRegistryEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.registry.RegistryEntry;
import org.spongepowered.api.registry.RegistryHolder;
import org.spongepowered.api.registry.RegistryTypes;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import net.hellheim.spongetools.common.SpongeToolsPlugin;
import net.hellheim.spongetools.resourcepack.Model;
import net.hellheim.spongetools.resourcepack.block.BlockDefinition;
import net.hellheim.spongetools.resourcepack.equipment.EquipmentAsset;
import net.hellheim.spongetools.resourcepack.item.ItemDefinition;
import net.hellheim.spongetools.resourcepack.meta.Metadata;
import net.hellheim.spongetools.resourcepack.meta.MetadataSection;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.minecraft.SharedConstants;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;

public final class ResourcePackEventListener {
	
	private static final ResourcePackInfo.Builder INFO_BUILDER = ResourcePackInfo.resourcePackInfo();
	private static final ResourcePackRequest.Builder REQUEST_BUILDER = ResourcePackRequest.resourcePackRequest()
			.required(true)
			.replace(false);
	
	private final Logger logger;
	private final int port;
	private final File packResult;
	private final File assetsToCopy;
	
	private Optional<ResourcePackRequest> pack = Optional.empty();
	
	public ResourcePackEventListener(
		final Logger logger, final int port, final File packResult, final File assetsToCopy
	) {
		this.logger = logger;
		this.port = port;
		this.packResult = packResult;
		this.assetsToCopy = assetsToCopy;
	}
	
	@Listener
	public void assembleResourcePack(final FreezeRegistryEvent.Post.GameScoped event) throws IOException {
		// TODO replace holder(type) with just type.get()
		final RegistryHolder holder = event.holder();
		this.logger.info(RegistryTypes.ENCHANTMENT_TYPE.defaultHolder().get());
		this.logger.info(holder);
		final Map<ResourceKey, ItemDefinition> items = holder.registry(ItemDefinition.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		final Map<ResourceKey, BlockDefinition> blocks = holder.registry(BlockDefinition.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		final Map<ResourceKey, Model> models = holder.registry(Model.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		final Map<ResourceKey, EquipmentAsset> equipment = holder.registry(EquipmentAsset.registry())
				.streamEntries()
				.collect(Collectors.toMap(RegistryEntry::key, RegistryEntry::value));
		
		// TODO figure out what this TODO means
		try (final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(this.packResult))) {
			final PackFormat format = SharedConstants.getCurrentVersion().packVersion(PackType.CLIENT_RESOURCES);
			final MetadataSection packmeta = MetadataSection.pack(
					org.spongepowered.api.resource.pack.PackType.client(),
					Component.text("Resource pack made with SpongeTools"),
					new net.hellheim.spongetools.resourcepack.meta.PackFormat(format.major(), format.minor()));
			this.writeEntry(out, Metadata.CODEC_CLIENT, "pack.mcmeta", "pack.mcmeta", packmeta.asMetadata());
			
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
			
			for (final Map.Entry<ResourceKey, EquipmentAsset> e : equipment.entrySet()) {
				this.writeEntry(out, EquipmentAsset.CODEC, "EquipmentAsset", "equipment", e.getKey(), e.getValue());
			}
		}
	}
	
	@Listener
	public void assembleResourcePackRequest(final StartedEngineEvent<Server> event) {
		final Server server = event.engine();
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
		out.write(SpongeToolsPlugin.GSON.toJson(encoded.result().get()).getBytes());
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
}
