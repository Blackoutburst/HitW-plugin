package commands;

import org.bukkit.command.CommandSender;

/**
 * Manage /discord command
 * @author Blackoutburst
 */
public class CommandDiscord {
	
	/**
	 * Send the discord server link
	 * @param sender command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(CommandSender sender) {
		sender.sendMessage("§bhttps://discord.gg/Gh24vw5b54");
		return true;
	}
}
