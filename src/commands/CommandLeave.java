package commands;

import game.StageManager;
import main.GamePlayer;

/**
 * Manage /leave command
 * @author Blackoutburst
 */
public class CommandLeave {
	
	/**
	 * Stop the game
	 * @param player command sender
	 * @param sender command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		if (player.isInTourney()) {
			player.getPlayer().sendMessage("§cYou can't do that while in tournament!");
			return true;
		}
		StageManager.stop(player);
		return true;
	}
}
