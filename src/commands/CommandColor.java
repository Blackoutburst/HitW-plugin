package commands;

import org.bukkit.Bukkit;

import org.bukkit.Location;

import main.GamePlayer;

/**
 * Manage /color commands
 * @author Blackoutburst
 */
public class CommandColor {
	
	/**
	 * Teleport the player to the color location
	 * if he is not in a tournament
	 * @param player the command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		if (player.isInTourney()) {
			player.getPlayer().sendMessage("§cYou can't do that while in tournament!");
			return true;
		}
    	player.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -583.5f, 7, 692.5f,  0, 0));
		return true;
	}
}
