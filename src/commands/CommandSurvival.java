package commands;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;

import main.GamePlayer;

/**
 * Manage /spawn command
 * @author Blackoutburst
 */
public class CommandSurvival {
	
	/**
	 * Teleport the player to the survival world
	 * @param player command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mv tp "+player.getPlayer().getName()+" survival");
		player.getPlayer().setAllowFlight(false);
		player.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		return true;
	}
}
