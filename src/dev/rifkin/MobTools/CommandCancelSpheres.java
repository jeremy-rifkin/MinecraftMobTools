package dev.rifkin.MobTools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCancelSpheres implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0 && sender instanceof Player) {
			SphereManager.cancelTasks((Player) sender);
			return true;
		} else if(args.length == 1) {
			// command blocks and other non-player command senders implement .isOp to return true
			if(sender.isOp()) {
				String arg = args[0];
				if(arg.equals("all")) {
					SphereManager.cancelAllTasks();
				} else {
					sender.sendMessage("unknown argument for /cancelspheres");
					return false;
				}
				return true;
			}
		} else {
			sender.sendMessage("Unexpected number of arguments for /cancelspheres");
			return false;
		}
		return false;
	}
}
