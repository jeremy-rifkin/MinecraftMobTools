package dev.rifkin.MobTools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class CommandShowspawn implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			SpawnGenerator generator = new SpawnGenerator(player);
			BukkitTask task = generator.runTaskTimer(MobTools.getInstance(), 0, 10);
			// temporary...
			SphereManager.registerTask(task, player);
		}
		return true;
	}
}
