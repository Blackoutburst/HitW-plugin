package commands;

import java.io.File;

import main.GamePlayer;
import utils.Tools;

/**
 * Manage /delay command
 * @author Blackoutburst
 */
public class CommandTitle {
	
	/**
	 * Toggle title
	 * Save this value inside a file
	 * @param player command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		boolean show = player.showTitle() ? false : true;
		
		player.setShowTitle(show);
		
		Tools.writePlayerData(new File(Tools.getPlayerFolder(player.getPlayer())), 
				player.getWallColor(), player.getGlassColor(), 
				player.getLeverDelay(), player.showTitle());
		
		if (show) {
			player.getPlayer().sendMessage("§bPerfect walls title will now be displayed");
		} else {
			player.getPlayer().sendMessage("§bPerfect walls title will now be hidden");
		}
		return true;
	}
}
