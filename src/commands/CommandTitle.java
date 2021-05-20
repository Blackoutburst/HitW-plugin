package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandTitle {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setTitle(p.isTitle() ? false : true);
		HPlayer.updatePlayerData(p);
		
		if (p.isTitle()) {
			sender.sendMessage("§eTitle are now enabled");
		} else {
			sender.sendMessage("§eTitle are now disabled");
		}
	}
}
