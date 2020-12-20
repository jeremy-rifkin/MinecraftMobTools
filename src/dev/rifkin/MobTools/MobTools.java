package dev.rifkin.MobTools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class MobTools extends JavaPlugin {
	private static MobTools pluginInstance;
	private ArrayList<BukkitTask> tasks = new ArrayList<>();
	/*
	TODO: Player-owned spheres / spawn visualizers so player can just cancel their's? OP needs to be able to cancel all.
	TODO: Command to stop showing spawns
	TODO: Better particles
	TODO: Blocks above?
	 */
	@Override
	public void onEnable() {
		pluginInstance = this;
		this.getCommand("mksphere").setExecutor(new CommandMksphere());
		this.getCommand("dspheres").setExecutor(new CommandCancel());
		this.getCommand("showspawn").setExecutor(new CommandShowspawn());
		this.getCommand("bedrocklocate").setExecutor(new CommandBedrockFinder());
		SolidBlocks.setup();
	}
	@Override
	public void onDisable() {
		pluginInstance = null;
	}
	public static MobTools getInstance() {
		return pluginInstance;
	}
	public void registerTask(BukkitTask task) {
		tasks.add(task);
	}
	public void cancelTasks(Player player) {
		for(BukkitTask task : tasks) {
			task.cancel();
		}
		tasks.clear();
		player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: removed spheres");
	}
}
