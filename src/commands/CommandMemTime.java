package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandMemtime {
	
	public void run(CommandSender sender, String[] args) {
		float value = 0;
		
		if (args.length == 0) {
			sender.sendMessage("§eYour memory time is now set to the default value §b(1.0s)");
			value = 1.0f;
		} else {
			try {
				value = Float.valueOf(args[0]);
			} catch (Exception e) {
				sender.sendMessage("§cInvalid value format, it must be a number or a decimal number !");
				return;
			}
		}
		
		if (value < 0) {value = 0;}
		if (value > 10000) {value = 10000;}
		
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		
		p.setMemTime(value);
		HPlayer.updatePlayerData(p);
		sender.sendMessage("§eYour memory time is now set " + value + "s");
	}
}
