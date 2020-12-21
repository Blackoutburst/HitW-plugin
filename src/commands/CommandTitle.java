package commands;

import java.io.File;

import main.GamePlayer;
import utils.Tools;

/**
 * Manage /title command
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
				player.getLeverDelay(), player.showTitle(), player.getMemtime());
		
		if (show) {
			player.getPlayer().sendMessage("§bTitle will now be displayed instead of chat message");
		} else {
			player.getPlayer().sendMessage("§bChat message will now be displayed instead of title");
		}
		return true;
	}
}
