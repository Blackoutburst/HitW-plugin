package commands;

import game.TourneyManager;
import main.GamePlayer;

/**
 * Manage /tourney command
 * @author Blackoutburst
 */
public class CommandTourney {
	
	/**
	 * Call tournament manager to perform various option
	 * if the player have operator status
	 * @param player command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		if (player.getPlayer().isOp()) {
			TourneyManager.manageOption(player, args);
		} else {
			player.getPlayer().sendMessage("§cSorry but you don't have acces to this command!");
		}
		return true;
	}
}
