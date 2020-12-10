package commands;

import game.TourneyManager;
import main.GamePlayer;

/**
 * Manage /spectate command
 * @author Blackoutburst
 */
public class CommandSpectate {
	
	/**
	 * Toggle spectator mode
	 * @param player command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		TourneyManager.spectate(player, args);
		return true;
	}
}
