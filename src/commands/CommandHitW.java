package commands;

import main.GamePlayer;
import utils.Values;

/**
 * Manage /discord command
 * @author Blackoutburst
 */
public class CommandHitW {
	
	/**
	 * Reload configuration file
	 * @param player player using the commands
	 * @param args commands arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		if (player.getPlayer().isOp()) {
			if (args[0].toLowerCase().equals("reload")) {
				Values.initValue();
				player.getPlayer().sendMessage("§bConfiguration files reloaded!");
			}
		} else {
			player.getPlayer().sendMessage("§cSorry but you don't have acces to this command!");
		}
		return true;
	}
}
