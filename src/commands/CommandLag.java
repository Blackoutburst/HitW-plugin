package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;

public class CommandLag {
	
	public void run(CommandSender sender, String[] args) {
		float value = 0;
		
		if (args.length == 0) {
			sender.sendMessage("§eYour brush lag is now set to the default value §b(100.0ms)");
			value = 100.0f;
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
		
		p.setBrushLag(value);
		HPlayer.updatePlayerData(p);
		sender.sendMessage("§eYour brush lag is now set " + value + "ms");
	}
}
