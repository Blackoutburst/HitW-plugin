package commands;

import game.StageManager;
import main.GamePlayer;

/**
 * Manage /play command
 * @author Blackoutburst
 */
public class CommandPlay {
	/**
	 * Start a game
	 * @param player command sender
	 * @param sender command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		if (player.isInTourney()) {
			player.getPlayer().sendMessage("§cYou can't do that while in tournament!");
		}
		StageManager.start(player, args);
		return true;
	}
}
