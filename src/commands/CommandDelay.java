package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandDelay {
	
	public void run(CommandSender sender, String[] args) {
		float value = 0;
		
		if (args.length == 0) {
			sender.sendMessage("§cPlease enter a value after the command !");
			return;
		}
		
		try {
			value = Float.valueOf(args[0]);
		} catch (Exception e) {
			sender.sendMessage("§cInvalid value format, it must be a number or a decimal number !");
			return;
		}
		
		if (value < 0) {value = 0;}
		if (value > 10000) {value = 10000;}
		
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setLeverDelay(value);
		HPlayer.updatePlayerData(p);
		sender.sendMessage("§eYour lever delay is now set " + value + "s");
	}
}
