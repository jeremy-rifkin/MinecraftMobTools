package dev.rifkin.MobTools;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBedrockCageFinder implements CommandExecutor {
	private World world;
	private UniqueHistoryQueue<Location> queue;
	private int found;
	private Player player;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			player = (Player) sender;
			world = player.getWorld();
			if(world.getEnvironment() != World.Environment.NETHER) {
				player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Must be in the nether");
				return false;
			}
			// setup traversal start block
			queue = new UniqueHistoryQueue<>();
			Location start_block = player.getLocation();
			start_block.setX(start_block.getBlockX());
			start_block.setZ(start_block.getBlockZ());
			start_block.setY(127);
			queue.push(start_block);
			// begin breadth-first traversal of the area around the player
			player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Conducting bedrock search...");
			int checks = 0;
			found = 0;
			while(!queue.empty()) {
				Location l = queue.pop();
				if(l.getY() != 127) {
					player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Internal Error: Initial location Y=" + l.getY());
					return false;
				}
				check_location(l.clone());
				checks++;
				queue_adjacent(l);
				// avoid infinite search
				if(checks > 10000)
					break;
				// should be plenty of instances
				if(found >= 15)
					break;
			}
			player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Bedrock search ended");
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
		boolean is_full_top = is_full_3x3(location);
		location.add(0, -1, 0);
		boolean is_full_middle = is_full_3x3(location);
		location.add(0, -1, 0);
		boolean is_full_bottom = is_full_3x3(location);
		// reset
		location.add(0, 2, 0);
		// result
		if(is_full_middle && is_full_bottom && is_full_top) {
			player.sendMessage("[" + ChatColor.RED + "ORB" + ChatColor.RESET + "]: Instance: " + location.getX() + ", " + location.getZ());
			found++;
		}
	}
	private boolean is_full_3x3(Location location) {
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				location.add(x, 0, z);
				if(world.getBlockAt(location).getType() != Material.BEDROCK) {
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
