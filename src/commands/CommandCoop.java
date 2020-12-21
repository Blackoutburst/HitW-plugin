package commands;

import java.util.ArrayList;

import game.StageManager;
import main.Coop;
import main.GamePlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.InsideArea;
import utils.ScoreboardManager;
import utils.Tools;
import utils.Values;

/**
 * Manage /coop command
 * @author Blackoutburst
 */
public class CommandCoop {
	
	/**
	 * Display coop command help
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean showHelp(GamePlayer player) {
		player.getPlayer().sendMessage("§9§m------------------------------");
		player.getPlayer().sendMessage("§6Co-op Commands");
		player.getPlayer().sendMessage("§e/coop <player> §8§l-§r §7§oInvite another player to your co-op");
		player.getPlayer().sendMessage("§e/coop accept <player> §8§l-§r §7§oAccept a co-op invite from a player");
		player.getPlayer().sendMessage("§e/coop list §8§l-§r §7§oLists the player in your current co-op");
		player.getPlayer().sendMessage("§e/coop kick <player> §8§l-§r §7§oRemove a player from your co-op");
		player.getPlayer().sendMessage("§e/coop leave §8§l-§r §7§oLeave your current co-op");
		player.getPlayer().sendMessage("§e/coop disband §8§l-§r §7§oDisbands the co-op");
		player.getPlayer().sendMessage("§e/coop warp §8§l-§r §7§oWarps the members of a co-op to your current game area");
		player.getPlayer().sendMessage("§9§m------------------------------");
		return true;
	}
	
	/**
	 * Update coop status for every coop members
	 * @param leader
	 * @author Blackoutburst
	 */
	private static void updateCoop(GamePlayer leader) {
		for (GamePlayer p : leader.getCoop().getPlayers()) {
			p.setCoop(leader.getCoop());
			ScoreboardManager.update(p);
		}
	}
	
	/**
	 * List coop members
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean list(GamePlayer player) {
		if (player.isInCoop()) {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§6Co-op Members ("+player.getCoop().getPlayers().size()+")");
			player.getPlayer().sendMessage(" ");
			for (GamePlayer p : player.getCoop().getPlayers()) {
				player.getPlayer().sendMessage(p.getPlayer().getDisplayName());
			}
			player.getPlayer().sendMessage("§9§m------------------------------");
		} else {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cYou are not currently in a co-op.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Create a new coop
	 * @param player
	 * @author Blackoutburst
	 */
	private static void createNewCoop(GamePlayer player) {
		Coop coop = new Coop(new ArrayList<GamePlayer>());
		coop.getPlayers().add(player);
		player.setCoop(coop);
		player.setInCoop(true);
		player.setCoopLeader(true);
	}
	
	/**
	 * Invite player to a coop
	 * @param player
	 * @param args
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean invite(GamePlayer player, String[] args) {
		GamePlayer receiver = Tools.getPlayerFromName(args[0]);
		if (receiver == null) {
			player.getPlayer().sendMessage("§cUnknow player "+args[0]);
			return true;
		}
		
		if (receiver.getPlayer().getName().equals(player.getPlayer().getName())) {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cYou cannot co-op yourself!");
			player.getPlayer().sendMessage("§9§m------------------------------");
			return true;
		}
		
		if (!player.isInCoop()) {
			createNewCoop(player);
		}
		if (player.isCoopLeader()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				p.getPlayer().sendMessage("§9§m------------------------------");
				p.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §einvited "+receiver.getPlayer().getDisplayName()+" §eto the co-op!");
				p.getPlayer().sendMessage("§9§m------------------------------");
			}
			receiver.getPlayer().sendMessage("§9§m------------------------------");
			receiver.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §ehas invited you to join their co-op!");
			
			TextComponent message = new TextComponent("§eDo §6/coop accept "+player.getPlayer().getName()+" §eor §6Click here to join!");
			message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coop accept "+player.getPlayer().getName()));
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to run\n/coop accept "+player.getPlayer().getName()).create()));
			receiver.getPlayer().spigot().sendMessage(message);
			receiver.getPlayer().sendMessage("§9§m------------------------------");
		} else {
			receiver.getPlayer().sendMessage("§9§m------------------------------");
			receiver.getPlayer().sendMessage("§cYou are not allowed to invite payers.");
			receiver.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Accept coop invitation
	 * @param player
	 * @param name
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean accept(GamePlayer player, String name) {
		GamePlayer leader = Tools.getPlayerFromName(name);
		if (leader.getCoop().getPlayers().size() < 4) {
			for (GamePlayer p : leader.getCoop().getPlayers()) {
				p.getPlayer().sendMessage("§9§m------------------------------");
				p.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §ejoined the co-op.");
				p.getPlayer().sendMessage("§9§m------------------------------");
			}
			leader.getCoop().getPlayers().add(player);
			player.setInCoop(true);
			updateCoop(leader);
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§eYou have joined "+leader.getPlayer().getDisplayName()+" §eco-op!");
			player.getPlayer().sendMessage("§9§m------------------------------");
		} else {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cThis co-op is actually full.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Kick a player from the coop
	 * @param player
	 * @param name
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean kick(GamePlayer player, String name) {
		if (player.isCoopLeader()) {
			GamePlayer kicked = null;
			for (GamePlayer p : player.getCoop().getPlayers()) {
				if (p.getPlayer().getName().toLowerCase().equals(name)) {
					p.setCoop(null);
					StageManager.resetPlayerStats(p);
					p.setInBlindGame(false);
					p.setInClassicGame(false);
					p.setInGame(false);
					p.setInCoop(false);
					kicked = p;
					ScoreboardManager.update(p);
					updateCoop(player);
				}
				kicked.getPlayer().sendMessage("§9§m------------------------------");
				kicked.getPlayer().sendMessage("§eYou got kicked from the co-op.");
				kicked.getPlayer().sendMessage("§9§m------------------------------");
				player.getCoop().getPlayers().remove(kicked);
				updateCoop(player);

			}
		} else {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cYou are not this co-op's leader.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Leave your current coop
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean leave(GamePlayer player) {
		GamePlayer leader = player.getCoop().getPlayers().get(0);
		
		if (player.getPlayer().getName().equals(leader.getPlayer().getName())) {
			disband(player);
		} else {
			leader.getCoop().getPlayers().remove(player);
			for (GamePlayer p : leader.getCoop().getPlayers()) {
				ScoreboardManager.update(p);
				StageManager.resetPlayerStats(p);
				p.setInBlindGame(false);
				p.setInClassicGame(false);
				p.setInGame(false);
				p.getPlayer().sendMessage("§9§m------------------------------");
				p.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §eleaved the co-op.");
				p.getPlayer().sendMessage("§9§m------------------------------");
				ScoreboardManager.update(p);
			}
			updateCoop(leader);
		}
		if (leader.getCoop().getPlayers().size() == 1) {
			autoDisband(leader);
		}
		player.setInCoop(false);
		player.setCoop(null);
		ScoreboardManager.update(player);
		return true;
	}
	
	/**
	 * Automatically disband the coop when everyone leave
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	private static void autoDisband(GamePlayer player) {
			player.setCoop(null);
			player.setInCoop(false);
			player.setCoopLeader(false);
			ScoreboardManager.update(player);
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§eThe co-op got disbanded becasue everyone leaved");
			player.getPlayer().sendMessage("§9§m------------------------------");
	}
	
	/**
	 * Disband the current coop
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean disband(GamePlayer player) {
		if (player.isCoopLeader()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				if (player.getPlayer().getName().equals(p.getPlayer().getName())) {
					continue;
				}
				StageManager.resetPlayerStats(p);
				p.setInBlindGame(false);
				p.setInClassicGame(false);
				p.setInGame(false);
				p.setCoop(null);
				p.setInCoop(false);
				ScoreboardManager.update(p);
				p.getPlayer().sendMessage("§9§m------------------------------");
				p.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §edisbanded the coop.");
				p.getPlayer().sendMessage("§9§m------------------------------");
			}
			player.setCoop(null);
			player.setInCoop(false);
			player.setCoopLeader(false);
			ScoreboardManager.update(player);
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§eYou disbanded the co-op.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		} else {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cYou are not this co-op's leader.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Teleport every player to your game area
	 * @param player
	 * @return true
	 * @author Blackoutburst
	 */
	private static boolean warp(GamePlayer player) {
		if (player.isCoopLeader()) {
			if (InsideArea.inGameArea(player.getPlayer().getLocation(), Values.games)) {
				for (GamePlayer p : player.getCoop().getPlayers()) {
					if (player.getPlayer().getName().equals(p.getPlayer().getName())) {
						continue;
					}
					p.getPlayer().teleport(player.getPlayer().getLocation());
					p.getPlayer().sendMessage("§9§m------------------------------");
					p.getPlayer().sendMessage(player.getPlayer().getDisplayName()+" §ewarped you inside his game area.");
					p.getPlayer().sendMessage("§9§m------------------------------");
				}
				player.getPlayer().sendMessage("§9§m------------------------------");
				player.getPlayer().sendMessage("§eYou warped everyone to your game area");
				player.getPlayer().sendMessage("§9§m------------------------------");
			} else {
				player.getPlayer().sendMessage("§cPlease enter a game area before running this command!");
			}
		} else {
			player.getPlayer().sendMessage("§9§m------------------------------");
			player.getPlayer().sendMessage("§cYou are not this co-op's leader.");
			player.getPlayer().sendMessage("§9§m------------------------------");
		}
		return true;
	}
	
	/**
	 * Call coop function
	 * @param sender command sender
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean onUse(GamePlayer player, String[] args) {
		if (player.isInTourney()) {
			player.getPlayer().sendMessage("§cYou can not do that while in tournament!");
			return true;
		}
		
		if (args.length == 0) {
			return showHelp(player);
		}
		
		if (args.length == 1) {
			if (args[0].toLowerCase().equals("list")) {return list(player);}
			if (args[0].toLowerCase().equals("help")) {return showHelp(player);}
			if (args[0].toLowerCase().equals("leave")) {return leave(player);}
			if (args[0].toLowerCase().equals("disband")) {return disband(player);}
			if (args[0].toLowerCase().equals("warp")) {return warp(player);}
		}
		
		if (args.length == 2) {
			if (args[0].toLowerCase().equals("accept")) {return accept(player, args[1]);}
			if (args[0].toLowerCase().equals("kick")) {return kick(player, args[1]);}
		}
		return invite(player, args);
	}
}
