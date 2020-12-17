package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import main.GamePlayer;

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
		boolean show = player.isShowTitle() ? false : true;
		
		
		player.setShowTitle(show);
		File f = new File("./plugins/HitW/player data/"+player.getPlayer().getUniqueId().toString().replace("-", ""));

		PrintWriter writer;
		try {
			writer = new PrintWriter(f+"/Title");
			writer.write(String.valueOf(show));
			writer.close();
		} catch (FileNotFoundException e) {
		}
		if (show) {
			player.getPlayer().sendMessage("§bPerfect walls title will now be displayed");
		} else {
			player.getPlayer().sendMessage("§bPerfect walls title will now be hidden");
		}
		return true;
	}
}
