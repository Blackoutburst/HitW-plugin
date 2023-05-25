package commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn {
	
	public void run(CommandSender sender) {
		((Player)sender).teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1054.5f, 0, 0));
	}
}
