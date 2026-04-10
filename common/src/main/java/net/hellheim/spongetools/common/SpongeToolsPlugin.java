package net.hellheim.spongetools.common;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.ConfigRoot;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

import net.hellheim.spongetools.SpongeTools;
import net.hellheim.spongetools.common.event.listener.BehaviourEventListener;
import net.hellheim.spongetools.common.event.listener.BlockStateEventListener;
import net.hellheim.spongetools.common.event.listener.EntityEventListener;
import net.hellheim.spongetools.common.event.listener.RegistryEventListener;
import net.hellheim.spongetools.common.event.listener.ResourcePackEventListener;
import net.hellheim.spongetools.common.web.MineHttpd;
import net.hellheim.spongetools.proxy.solid.PluginProxy;
import net.minecraft.resources.ResourceLocation;

@Plugin(SpongeTools.NAMESPACE)
public final class SpongeToolsPlugin implements PluginProxy {
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private final PluginContainer plugin;
	private final Logger logger;
	private final Optional<MineHttpd> web;
	
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
		eventManager.registerListeners(this.plugin, new BehaviourEventListener(), lookup);
		eventManager.registerListeners(this.plugin, new BlockStateEventListener(), lookup);
		eventManager.registerListeners(this.plugin, new EntityEventListener(), lookup);
		eventManager.registerListeners(this.plugin, new RegistryEventListener(this.logger, this.assetsToLoad), lookup);
		eventManager.registerListeners(this.plugin, new ResourcePackEventListener(this.logger, this.port, this.packResult, this.assetsToCopy), lookup);
		this.web.ifPresent(web -> eventManager.registerListeners(this.plugin, web.eventListener(), lookup));
	}
	
	@Override
	public PluginContainer plugin() {
		return this.plugin;
	}
	
	public static ResourceLocation location(final String key) {
		return ResourceLocation.fromNamespaceAndPath(SpongeTools.NAMESPACE, key);
	}
	
	public static boolean customMiningEnabled() {
		return true;
	}
}
