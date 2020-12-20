package dev.rifkin.MobTools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class CommandMksphere implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			// argument parsing
			Player player = (Player) sender;
			double radius;
			boolean semisphere = false;
			boolean force_vis = false;
			if(args.length < 1) {
				Utils.SendErrorMsg(player, "Invalid arguments");
				return false;
			}
			try {
				radius = Double.parseDouble(args[0]);
			} catch(NumberFormatException e) {
				Utils.SendErrorMsg(player, "Invalid radius");
				return false;
			}
			if(args.length > 1) {
				semisphere = Boolean.parseBoolean(args[1]);
			}
			if(args.length > 2) {
				force_vis = Boolean.parseBoolean(args[2]);
			}
			// setup runnable
			SphereGenerator generator = new SphereGenerator(player.getLocation(), radius, semisphere, force_vis);
			BukkitTask task = generator.runTaskTimer(MobTools.getInstance(), 0, 20);
			SphereManager.registerTask(task, player);
			// user info
			player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: created a sphere r = " + radius);
			player.sendMessage("[" + ChatColor.GREEN + "ORB" + ChatColor.RESET + "]: use \"/cancelspheres\" to remove all your spheres");
		}
		return true;
	}
}
