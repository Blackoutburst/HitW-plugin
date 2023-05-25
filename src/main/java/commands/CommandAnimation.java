package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandAnimation {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setOldAnimation(p.isOldAnimation() ? false : true);
		HPlayer.updatePlayerData(p);
		
		if (p.isOldAnimation()) {
			sender.sendMessage("§eOld walls animations are now enabled");
		} else {
			sender.sendMessage("§eOld walls animations are now disabled");
		}
	}
}
