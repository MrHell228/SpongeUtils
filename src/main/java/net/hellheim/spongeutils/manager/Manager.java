package net.hellheim.spongeutils.manager;

import org.spongepowered.plugin.PluginContainer;

import net.hellheim.spongeutils.source.solid.PluginSource;

public abstract class Manager implements PluginSource {
	
	protected final PluginContainer plugin;
	
	public Manager(final PluginContainer plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public PluginContainer plugin() {
		return this.plugin;
	}
}
