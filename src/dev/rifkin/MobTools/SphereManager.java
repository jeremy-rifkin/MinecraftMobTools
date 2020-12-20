package dev.rifkin.MobTools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class SphereManager {
	private static HashMap<UUID, ArrayList<BukkitTask>> spheres;
	private static boolean did_setup = false;
	public static void setup() {
		if(did_setup)
			throw new Error("setup called more than once");
		spheres = new HashMap<>();
		did_setup = true;
	}
	public static void registerTask(BukkitTask task, Player player) {
		UUID uuid = player.getUniqueId();
		if(!spheres.containsKey(uuid)) {
			spheres.put(uuid, new ArrayList<>(Collections.singletonList(task)));
		} else {
			spheres.get(uuid).add(task);
		}
	}
	public static void cancelTasks(Player player) {
		ArrayList<BukkitTask> tasks = spheres.get(player.getUniqueId());
		// No action needed if tasks is null
		if(tasks != null) {
			for(BukkitTask task : tasks) {
				task.cancel();
			}
			tasks.clear();
			spheres.remove(player.getUniqueId());
		}
		player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: removed spheres");
	}
	public static void cancelAllTasks() {
		for(ArrayList<BukkitTask> tasks : spheres.values()) {
			for(BukkitTask task : tasks) {
				task.cancel();
			}
			tasks.clear();
		}
		spheres.clear();
	}
}
