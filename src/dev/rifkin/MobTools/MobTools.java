package dev.rifkin.MobTools;

import org.bukkit.plugin.java.JavaPlugin;

public class MobTools extends JavaPlugin {
	private static MobTools pluginInstance;
	/*
	TODO: Better particles
	TODO: Blocks above?
	 */
	@Override
	public void onEnable() {
		// set pluginInstance pointer
		pluginInstance = this;
		// register commands
		this.getCommand("mksphere").setExecutor(new CommandMksphere());
		this.getCommand("cancelspheres").setExecutor(new CommandCancelSpheres());
		this.getCommand("showspawn").setExecutor(new CommandShowspawn());
		this.getCommand("locatebedrockcage").setExecutor(new CommandBedrockCageFinder());
		// setup the solid blocks memoizer
		SolidBlocks.setup();
		// setup the hash map for player spheres
		SphereManager.setup();
		// spawn visualizer keeps track of its own tasks too...
		CommandShowspawn.setup();
	}
	@Override
	public void onDisable() {
		pluginInstance = null;
	}
	public static MobTools getInstance() {
		return pluginInstance;
	}
}
