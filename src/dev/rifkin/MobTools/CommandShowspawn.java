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
	private static HashMap<UUID, Pair<BukkitTask, SpawnGenerator>> generator_tasks;
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
			// accept one positional argument
			int light_level = -1; // -1 = default light-level based off of the environment
			if(args.length > 0) {
				if(args[0].toLowerCase().equals("any")) {
					light_level = 15;
				} else {
					try {
						light_level = Integer.parseInt(args[0]);
					} catch(NumberFormatException e) {
						Utils.SendErrorMsg(player, "Invalid light-level (must be integer or \"any\")");
						return false;
					}
					if(light_level > 15 || light_level < 0) {
						Utils.SendErrorMsg(player, "Invalid light-level (out of bounds 0-15)");
						return false;
					}
				}
			}
			// create new task, cancel current visualizer task, or update current task
			if(playerIsCurrentlyVisualizing(player)) {
				// update if light level provided, otherwise cancel
				if(light_level == -1) {
					cancelPlayersVisualizationTask(player);
				} else {
					generator_tasks.get(player.getUniqueId()).b.updateLightLevel(light_level);
				}
			} else {
				// setup task
				SpawnGenerator generator = new SpawnGenerator(player, light_level);
				BukkitTask task = generator.runTaskTimer(MobTools.getInstance(), 0, 10);
				registerTask(task, generator, player);
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
	private static void registerTask(BukkitTask task, SpawnGenerator generator, Player player) {
		// this method is prone to overwriting a running task
		// the way this method is invoked in onCommand ensures that doesn't happen
		generator_tasks.put(player.getUniqueId(), new Pair<>(task, generator));
	}
	private static void cancelPlayersVisualizationTask(Player player) {
		generator_tasks.get(player.getUniqueId()).a.cancel();
		generator_tasks.remove(player.getUniqueId());
	}
	public static void cancelPlayersVisualizationTaskIf(Player player) {
		if(playerIsCurrentlyVisualizing(player)) {
			cancelPlayersVisualizationTask(player);
		}
	}
}
