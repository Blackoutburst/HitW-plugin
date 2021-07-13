package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import core.HPlayer;

public class CommandAFK {
	
	public void run(CommandSender sender) {
		sender.sendMessage("§eYou are now AFK");
		HPlayer hp = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		
		hp.setAfkValue(0);
		hp.getPlayer().setPlayerListName(hp.getRank()+hp.getPlayer().getName()+" §4§lAFK§r");
	}
}
