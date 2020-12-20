package dev.rifkin.MobTools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class MobTools extends JavaPlugin {
	private static MobTools pluginInstance;
	/*
	TODO: Player-owned spheres / spawn visualizers so player can just cancel their's? OP needs to be able to cancel all.
	TODO: Command to stop showing spawns
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
	}
	@Override
	public void onDisable() {
		pluginInstance = null;
	}
	public static MobTools getInstance() {
		return pluginInstance;
	}
}
