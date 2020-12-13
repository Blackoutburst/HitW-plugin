package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import main.GamePlayer;

/**
 * Manage /delay command
 * @author Blackoutburst
 */
public class CommandDelay {
	
	/**
	 * Set player lever / wall delay to the specified value
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
			player.getPlayer().sendMessage("§cInvalid argument");
			return true;
		}
		if (Float.valueOf(args[0]) < 0) {
			player.getPlayer().sendMessage("§cValue can not be negative");
			return true;
		}
		player.setLeverDelay(Float.valueOf(args[0]));
		File f = new File("./plugins/HitW/player data/"+player.getPlayer().getUniqueId().toString().replace("-", ""));

		PrintWriter writer;
		try {
			writer = new PrintWriter(f+"/Lever");
			writer.write(String.valueOf(player.getLeverDelay()));
			writer.close();
		} catch (FileNotFoundException e) {
		}
		player.getPlayer().sendMessage("§aYour lever delay is now set to "+player.getLeverDelay()+" seconds");
		return true;
	}
}
