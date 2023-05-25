package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HPlayer;
import utils.GameUtils;

public class CommandLeave {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		if (!p.isInGame()) {sender.sendMessage("§cYou are not in game right now !");return;}
		if (p.isInParty() && !p.isPartyLeader()) {sender.sendMessage("§cOnly the party leader stop start a game !");return;}
		if (p.isInDuel()) {sender.sendMessage("§cYou can not leave a duel !");return;}
		
		GameUtils.stopGames(p);
	}
}
