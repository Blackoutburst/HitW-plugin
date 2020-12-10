package commands;

import main.GamePlayer;
import utils.ScoreboardManager;

/**
 * Manage /fly command
 * @author Blackoutburst
 */
public class CommandFly {
	
	/**
	 * Toggle fly mode for the player
	 * @param player command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		if (player.getPlayer().getAllowFlight()) {
			player.getPlayer().setAllowFlight(false);
		} else {
			player.getPlayer().setAllowFlight(true);
		}
		ScoreboardManager.update(player);
		return true;
	}
}
