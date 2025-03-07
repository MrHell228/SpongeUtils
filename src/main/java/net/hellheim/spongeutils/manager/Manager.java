package net.hellheim.spongeutils.manager;

import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.proxy.solid.PluginProxy;

public abstract class Manager implements PluginProxy {
	
	protected final PluginContainer plugin;
	
	public Manager(final PluginContainer plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public PluginContainer plugin() {
		return this.plugin;
	}
}
