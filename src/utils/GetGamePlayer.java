package utils;

import main.GamePlayer;
import main.Main;

/**
 * This Class contains function to return GamePlayer objects
 * @author Blackoutburst
 */
public class GetGamePlayer {
	
	/**
	 * Get a GamePlayer object from his player name
	 * @param name player name
	 * @return The GamePlayer object with the specified name
	 * @author Blackoutburst
	 */
	public static GamePlayer getPlayerFromName(String name) {
    	for (GamePlayer p : Main.players) {
			if (p.getPlayer().getName().equals(name)) {
				return (p);
			}
		}
    	return null;
	}
}
