package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandFly {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setFly(p.getPlayer().getAllowFlight() ? false : true);
		p.getPlayer().setAllowFlight(p.isFly());
		HPlayer.updatePlayerData(p);
		
		if (p.isFly()) {
			sender.sendMessage("§eYou can now fly");
		} else {
			sender.sendMessage("§eYou can no longer fly");
		}
	}
}
