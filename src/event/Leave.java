package event;

import org.bukkit.event.player.PlayerQuitEvent;

import commands.CommandCoop;
import game.StageManager;
import main.GamePlayer;
import main.Main;
import utils.Tools;

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
		GamePlayer player = Tools.getPlayerFromName(event.getPlayer().getName());
	
		if (player.isInGame()) {
			StageManager.autostop(player);
		}
		if (player.isInCoop()) {
			CommandCoop.leave(player);
		}
		Main.players.remove(player);
	}
}
