package event;

import org.bukkit.event.player.PlayerQuitEvent;

import commands.CommandParty;
import core.HPlayer;
import main.Main;

public class Leave {
	public void execute(PlayerQuitEvent event) {
		CommandParty.autoLeave(event.getPlayer());
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		Main.hPlayers.remove(p);
	}
}
