package dev.rifkin.MobTools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCancelSpheres implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			MobTools.getInstance().cancelTasks((Player) sender);
			return true;
		}
		return false;
	}
}
