package event;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;

import core.HPlayer;
import utils.GameUtils;

public class Move {
	
	public void execute(PlayerMoveEvent event) {
		if (!HPlayer.getHPlayer(Bukkit.getPlayer(event.getPlayer().getName())).isInDuel()) {
			GameUtils.inGameArea(event.getPlayer());
		}
	}
}
