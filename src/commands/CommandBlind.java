package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandBlind {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		p.setBlind(p.isBlind() ? false : true);

		HPlayer.updatePlayerData(p);
		
		if (p.isBlind()) {
			sender.sendMessage("§eBlind mode is now enabled");
		} else {
			sender.sendMessage("§eBlind mode is now disabled");
		}
	}
}
