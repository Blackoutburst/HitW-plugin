package event;

import org.bukkit.event.player.PlayerQuitEvent;

import com.blackout.npcapi.core.PacketInteractListener;

import commands.CommandParty;
import core.HPlayer;
import main.Main;
import utils.GameUtils;

public class Leave {
	public void execute(PlayerQuitEvent event) {
		CommandParty.autoLeave(event.getPlayer());
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		Main.hPlayers.remove(p);
		GameUtils.stopGames(p);
		PacketInteractListener.remove(event.getPlayer());
	}
}
