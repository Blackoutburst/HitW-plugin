package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import core.HGame;
import core.HPlayer;
import utils.GameUtils;

public class CommandLeave {
	
	public void run(CommandSender sender) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		if (!p.isInGame()) {sender.sendMessage("§cYou are not in game right now !");return;}
		if (p.isInParty() && !p.isPartyLeader()) {sender.sendMessage("§cOnly the party leader stop start a game !");return;}
		if (p.isInDuel()) {sender.sendMessage("§cYou can not leave a duel !");return;}
		
		HGame game = GameUtils.getGameArea(p.getPlayer());

		if (game == null) {GameUtils.leaveGameArea(p.getPlayer());return;}
		
		if (p.isInParty()) {
			for (HPlayer hp : p.getParty())  {
				hp.setInGame(false, null);
			}
		} else {
			p.setInGame(false, null);
		}
		game.setOwner(null);
	}
}
