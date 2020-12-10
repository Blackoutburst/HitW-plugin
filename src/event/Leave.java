package event;

import org.bukkit.event.player.PlayerQuitEvent;

import game.StageManager;
import main.GamePlayer;
import main.Main;
import utils.GetGamePlayer;

/**
 * Manage every action when onPlayerQuit event is called 
 * @author Blackoutburst
 */
public class Leave {
	/**
	 * Stop the game player was inside
	 * Removed him from player list
	 * @param event Player quit event
	 * @author Blackoutburst
	 */
	public void onLeave(PlayerQuitEvent event) {
		GamePlayer player = GetGamePlayer.getPlayerFromName(event.getPlayer().getName());
	
		if (player.isInGame()) {
			StageManager.autostop(player);
		}
		Main.players.remove(player);
	}
}
