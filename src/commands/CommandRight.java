package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandRight {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setRightSided(p.isRightSided() ? false : true);
		HPlayer.updatePlayerData(p);
		
		if (p.isRightSided()) {
			sender.sendMessage("§eRight sided walls are now enabled");
		} else {
			sender.sendMessage("§eright sided walls are now disabled");
		}
	}
}
