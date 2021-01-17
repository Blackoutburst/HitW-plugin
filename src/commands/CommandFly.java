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
			player.getPlayer().sendMessage("§bYou can no longer fly");
			player.getPlayer().setAllowFlight(false);
		} else {
			player.getPlayer().setAllowFlight(true);
			player.getPlayer().sendMessage("§bYou can now fly");
		}
		ScoreboardManager.update(player);
		return true;
	}
}
