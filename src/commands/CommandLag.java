package commands;

import java.io.File;

import main.GamePlayer;
import utils.Tools;

/**
 * Manage /memtime command
 * @author Blackoutburst
 */
public class CommandLag {
	
	/**
	 * Set player memory time to the specified value
	 * Save this value inside a file
	 * @param player command sender
	 * @param sender command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		try {
			Float.valueOf(args[0]);
		} catch (Exception e) {
			player.getPlayer().sendMessage("�cInvalid argument");
			return true;
		}
		if (Float.valueOf(args[0]) < 0) {
			player.getPlayer().sendMessage("�cValue can not be negative!");
			return true;
		}
		player.setBrushLag(Float.valueOf(args[0]));
		
		Tools.writePlayerData(new File(Tools.getPlayerFolder(player.getPlayer())), 
				player.getWallColor(), player.getGlassColor(), 
				player.getLeverDelay(), player.showTitle(), player.getMemtime(), player.getBrushLag());
		
		player.getPlayer().sendMessage("�bYour brush lag is now set to "+player.getBrushLag()+" ms �a(default 100)");
		return true;
	}
}