package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandPerfect {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setPerfectOnly(p.isPerfectOnly() ? false : true);
		HPlayer.updatePlayerData(p);
		
		if (p.isPerfectOnly()) {
			sender.sendMessage("§ePerfect mode is now enabled");
		} else {
			sender.sendMessage("§ePerfect mode is now disabled");
		}
	}
}
