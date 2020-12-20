package dev.rifkin.MobTools;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

public class CommandCancelSpheres implements CommandExecutor {
	/*private boolean isNonPlayerCommandSender(CommandSender sender) {
		return sender instanceof BlockCommandSender
		    || sender instanceof CommandMinecart
		    || sender instanceof ConsoleCommandSender
		    || sender instanceof RemoteConsoleCommandSender
		    || sender instanceof ProxiedCommandSender;
	}*/
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
		/*if(sender instanceof Player) {
			SphereManager.cancelTasks((Player) sender);
			return true;
		} else if(sender instanceof BlockCommandSender) {
			if(args.length != 1) {
				sender.sendMessage("Expected 1 argument for /cancelspheres issued by a command block, got " + args.length);
				return false;
			}
		}*/
		return false;
	}
}
