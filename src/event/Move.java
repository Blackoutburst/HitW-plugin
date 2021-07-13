package event;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;

import core.HPlayer;
import utils.GameUtils;

public class Move {
	
	public void execute(PlayerMoveEvent event) {
		HPlayer hp = HPlayer.getHPlayer(Bukkit.getPlayer(event.getPlayer().getName()));
		
		hp.setAfkValue(60);
		hp.getPlayer().setPlayerListName(hp.getRank()+hp.getPlayer().getName()+"§r");
		
		if (!hp.isInDuel()) {
			GameUtils.inGameArea(event.getPlayer());
		}
	}
}
