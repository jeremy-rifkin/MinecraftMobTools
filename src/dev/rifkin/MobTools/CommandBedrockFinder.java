package dev.rifkin.MobTools;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CommandBedrockFinder implements CommandExecutor {
	private class UniqueHistoryQueue<T> {
		private final LinkedList<T> queue = new LinkedList<T>();
		private final HashSet<T> set = new HashSet<T>();
		public void push(T item) {
			if(set.add(item)) {
				queue.add(item);
			}
		}
		public T pop() throws NoSuchElementException {
			return queue.remove();
		}
		public void clear() {
			queue.clear();
			set.clear();
		}
		public boolean empty() {
			return queue.size() == 0;
		}
	}
	private World w;
	private UniqueHistoryQueue<Location> queue;
	private int found;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			w = player.getWorld();
			if(w.getEnvironment() != World.Environment.NETHER) {
				player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Must be in the nether");
			}
			queue = new UniqueHistoryQueue<>();
			Location player_location = player.getLocation();
			player_location.setX(player_location.getBlockX());
			player_location.setZ(player_location.getBlockZ());
			player_location.setY(127);
			queue.push(player_location);
			//player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Conducting bedrock search. This will lag tf out of the server and may never end. Enjoy!");
			Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Conducting bedrock search. This will lag tf out of the server and possibly never end. Enjoy!");
			int checks = 0;
			found = 0;
			// Traverse area around the player breadth-first
			while(!queue.empty()) {
				Location l = queue.pop();
				if(l.getY() != 127) {
					Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: ERROR. Initial location Y=" + l.getY());
					break;
				}
				check_location(l.clone());
				checks++;
				queue_adjacent(l);
				//if(checks % 100 == 0)
				//	Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: " + checks);
				if(checks > 10000)
					break;
				if(found >= 40)
					break;
			}
			Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Bedrock search ended");
		}
		return true;
	}
	private void check_location(Location location) {
		for(int i = 0; i < 5; i++) {
			check_block(location);
			location.subtract(0, -1, 0);
		}
	}
	private void check_block(Location location) {
		// top - just check center block
		if(w.getBlockAt(location).getType() != Material.BEDROCK)
			return;
		boolean is_full_top = is_full_3x3(location);
		// go down two levels
		location.add(0, -1, 0);
		boolean is_full_middle = is_full_3x3(location);
		location.add(0, -1, 0);
		boolean is_full_bottom = is_full_3x3(location);
		// reset
		location.add(0, 2, 0);
		// result
		if(is_full_middle) {
			if(is_full_bottom && is_full_top) {
				Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Instance: " + location.getX() + ", " + location.getZ() + " (3x3x3)");
				found++;
			} else {
				//Bukkit.broadcastMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Instance: " + location.getX() + ", " + location.getZ());
			}

		}
	}
	private boolean is_full_3x3(Location location) {
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				location.add(x, 0, z);
				if(w.getBlockAt(location).getType() != Material.BEDROCK) {
					// reset
					location.subtract(x, 0, z);
					return false;
				}
				location.subtract(x, 0, z);
			}
		}
		return true;
	}
	private void queue_adjacent(Location location) {
		// left
		location.add(-1, 0, 0);
		queue.push(location.clone());
		// right
		location.add(2, 0, 0);
		queue.push(location.clone());
		location.add(-1, 0, 0);
		// forward
		location.add(0, 0, 1);
		queue.push(location.clone());
		// back
		location.add(0, 0, -2);
		queue.push(location.clone());
		location.add(0, 0, 1);
	}
}
