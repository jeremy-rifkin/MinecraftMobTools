package dev.rifkin.MobTools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CommandShowspawn implements CommandExecutor {
	private static HashMap<UUID, BukkitTask> generator_tasks;
	private static boolean did_setup = false;
	public static void setup() {
		if(did_setup)
			throw new Error("setup called more than once");
		generator_tasks = new HashMap<>();
		did_setup = true;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(playerIsCurrentlyVisualizing(player)) {
				cancelPlayersVisualizationTask(player);
			} else {
				SpawnGenerator generator = new SpawnGenerator(player);
				BukkitTask task = generator.runTaskTimer(MobTools.getInstance(), 0, 10);
				registerTask(task, player);
				// user info
				player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: visualizing spawnable spaces around the user");
				player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: use \"/showspawn\" again to stop showing spawnable spaces");
			}
		}
		return true;
	}
	private static boolean playerIsCurrentlyVisualizing(Player player) {
		return generator_tasks.containsKey(player.getUniqueId());
	}
	private static void registerTask(BukkitTask task, Player player) {
		// this method is prone to overwriting a running task
		// the way this method is invoked in onCommand ensures that doesn't happen
		generator_tasks.put(player.getUniqueId(), task);
	}
	private static void cancelPlayersVisualizationTask(Player player) {
		generator_tasks.get(player.getUniqueId()).cancel();
		generator_tasks.remove(player.getUniqueId());
	}
}
