package commands;

import org.bukkit.scoreboard.DisplaySlot;

import main.GamePlayer;
import utils.ScoreboardManager;
import utils.Values;

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
		player.getPlayer().teleport(Values.spawn);
    	player.setInTourney(false);
		player.setTourneyRole("none");
		player.getPlayer().getScoreboard().getObjective(player.getPlayer().getName()).setDisplaySlot(DisplaySlot.SIDEBAR);
		ScoreboardManager.update(player);
		return true;
	}
}
