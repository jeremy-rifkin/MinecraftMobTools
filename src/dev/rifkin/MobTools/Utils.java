package dev.rifkin.MobTools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
	public static void SendErrorMsg(Player p, String s) {
		p.sendMessage("[" + ChatColor.RED + "ORB ERROR" + ChatColor.RESET + "]: " + s);
	}
}
