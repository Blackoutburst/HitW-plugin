package commands;

import main.GamePlayer;
import utils.Values;

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
    	player.getPlayer().teleport(Values.color);
		return true;
	}
}
