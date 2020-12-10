package commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.GamePlayer;
import utils.ScoreboardManager;

/**
 * Manage /spawn command
 * @author Blackoutburst
 */
public class CommandSpawn {
	
	/**
	 * Teleport the player to the spawn location
	 * @param player command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		player.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -574.5f, 7, 665.5f, -90, 0));
		player.getBoard().setTitle(player.getPlayer().getName());
    	player.setInTourney(false);
		player.setTourneyRole("none");
		ScoreboardManager.update(player);
		return true;
	}
}
