package dev.rifkin.MobTools;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobTools extends JavaPlugin {
	private static MobTools pluginInstance;
	/*
	TODO: Better particles
	TODO: Blocks above?
	TODO: Player disconnect
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
		// setup disconnect listener
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			private void onPlayerQuit(final PlayerQuitEvent e) {
				handlePlayerDC(e);
			}
			@EventHandler
			private void onPlayerKicked(final PlayerKickEvent e) {
				handlePlayerDC(e);
			}
		}, this);
	}
	@Override
	public void onDisable() {
		pluginInstance = null;
	}
	public static MobTools getInstance() {
		return pluginInstance;
	}
	private void handlePlayerDC(PlayerEvent e) {
		SphereManager.cancelTasks(e.getPlayer());
		CommandShowspawn.cancelPlayersVisualizationTaskIf(e.getPlayer());
	}
}
