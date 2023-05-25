package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import core.HPlayer;
import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.Utils;

public class CommandParty {
	
	private void help(CommandSender sender) {
		sender.sendMessage("§a§l§m---------------------------------------------");
		sender.sendMessage(Utils.centerText("§6Party commands"));
		sender.sendMessage("");
		sender.sendMessage("§b/p §r[player] §8- §7Invite another player to your party");
		sender.sendMessage("§b/p accept §r[player] §8- §7Accept a party invitation");
		sender.sendMessage("§b/p list §8- §7List the player in your current party");
		sender.sendMessage("§b/p kick §r[player] §8- §7Remove a player from your party");
		sender.sendMessage("§b/p leave §8- §7Leave your current party");
		sender.sendMessage("§b/p disband §8- §7Disband the party");
		sender.sendMessage("§b/p warp §8- §7Teleport your party member to your position");
		sender.sendMessage("§a§l§m---------------------------------------------");
	}
	
	private void invite(CommandSender sender, String[] args) {
		if (args.length < 1) {sender.sendMessage("§cInvalid usage, try /part help");return;}
		HPlayer p = HPlayer.getHPlayer(Bukkit.getPlayer(args[0]));
		if (p == null) {sender.sendMessage("§cUnknown player " + args[0] + " !");return;}
		
		if (p.getPlayer().getName() == sender.getName()) {sender.sendMessage("§cYou can't party yourself");return;}
		
		TextComponent msg = new TextComponent(Utils.centerText("§eClick §bhere §eto accept"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p accept " + sender.getName()));
		p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getPlayer().sendMessage(" ");
		p.getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(sender.getName()).getDisplayName() + " §einvited you to a party !"));
		p.getPlayer().spigot().sendMessage(msg);
		p.getPlayer().sendMessage(" ");
		p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.setPartyInvite(true);
		
		new BukkitRunnable(){
			@Override
			public void run(){
				p.setPartyInvite(false);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L * 30);
		
		sender.sendMessage("§eYou invited "+ p.getDisplayName() + " §eto the party");
		HPlayer hp = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		
		if (hp.isInParty()) {
			for (HPlayer hps : hp.getParty()) {
				if (hps.isPartyLeader()) continue;
				hps.getPlayer().sendMessage("§e" + sender.getName() + " §einvited "+ p.getDisplayName() + " §eto the party !");
			}
		}
	}
	
	private void accept(CommandSender sender, String[] args) {
		if (args.length < 2) {sender.sendMessage("§cInvalid usage, try /part help");return;}
		Player p = Bukkit.getPlayer(args[1]);
		if (p == null) {sender.sendMessage("§cUnknown player " + args[1] + " !");return;}
		
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		
		if (!hsender.isPartyInvite()) {sender.sendMessage("§cYou don't have any pending invitation !");return;}
		
		if (hsender.isInParty()) {sender.sendMessage("§cYou must leave your current party before joining one !");return;}
		
		hsender.setPartyInvite(false);
		HPlayer hp = HPlayer.getHPlayer(p);
		
		if (hp.isInParty()) {
			for (HPlayer hps : hp.getParty()) {
				hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
				hps.getPlayer().sendMessage(" ");
				hps.getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(sender.getName()).getDisplayName() + " §ejoined the party !"));
				hps.getPlayer().sendMessage(" ");
				hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			}
		} else {
			p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			p.getPlayer().sendMessage(" ");
			p.getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(sender.getName()).getDisplayName() + " §ejoined the party !"));
			p.getPlayer().sendMessage(" ");
			p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hp.setInParty(true);
			hp.setPartyLeader(true);
			hp.getParty().add(hp);
			hp.getBoard().setTitle("§6-= Coop =-");
		}
		hp.getParty().add(hsender);
		hsender.setInParty(true);
		hsender.getBoard().setTitle("§6-= Coop =-");
		sender.sendMessage("§eYou joined "+ p.getDisplayName() +" §eparty !");
		
		for (HPlayer hps : hp.getParty()) {
			hps.setParty(new ArrayList<>(hp.getParty()));
		}
	}
	
	private void list(CommandSender sender) {
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		
		if (!hsender.isInParty()) {sender.sendMessage("§cYou are not in a party right now !");return;}
		
		sender.sendMessage("§a§l§m---------------------------------------------");
		for (HPlayer hps : hsender.getParty()) {
			if (hps.isPartyLeader()) {
				sender.sendMessage(Utils.centerText("§6Party leader"));
				sender.sendMessage(hps.getDisplayName());
				sender.sendMessage("");
				sender.sendMessage(Utils.centerText("§6Party members (" + (hsender.getParty().size() - 1) + ")"));
			} else {
				sender.sendMessage(hps.getDisplayName());
			}
		}
		sender.sendMessage("§a§l§m---------------------------------------------");
	}
	
	private void kick(CommandSender sender, String[] args) {
		if (args.length < 2) {sender.sendMessage("§cInvalid usage, try /part help");return;}
		
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		if (!hsender.isInParty()) {sender.sendMessage("§cYou are not in a party right now !");return;}
		if (!hsender.isPartyLeader()) {sender.sendMessage("§cOnly the party leader can do that !");return;}
		
		Player p = Bukkit.getPlayer(args[1]);
		HPlayer hp = HPlayer.getHPlayer(p);
		if (p == null) {sender.sendMessage("§cUnknown player " + args[1] + " !");return;}
		
		if (p.getName() == sender.getName()) {disband(sender);return;}
		
		p.sendMessage("§eYou got kicked from the party !");
		hp.getParty().clear();
		hp.setInParty(false);
		hp.setInGame(false, null);
		hp.getBoard().setTitle(hp.getDisplayName());
		
		hsender.getParty().remove(hp);
		
		for (HPlayer hps : hsender.getParty()) {
			hps.setParty(new ArrayList<>(hsender.getParty()));
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage(" ");
			hps.getPlayer().sendMessage(Utils.centerText(p.getDisplayName() + " §egot kicked from the party !"));
			hps.getPlayer().sendMessage(" ");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		}
		
		if (hsender.getParty().size() == 1) {
			disband(sender);
		}
	}
	
	private void leave(CommandSender sender) {
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		if (!hsender.isInParty()) {sender.sendMessage("§cYou are not in a party right now !");return;}
		
		if (hsender.isPartyLeader()) {disband(sender);return;}
		
		HPlayer leader = hsender.getParty().get(0);
		
		hsender.getPlayer().sendMessage("§eYou leaved the party");
		hsender.getParty().clear();
		hsender.setInParty(false);
		hsender.setInGame(false, null);
		hsender.getBoard().setTitle(hsender.getDisplayName());
		
		leader.getParty().remove(hsender);
		
		for (HPlayer hps : leader.getParty()) {
			hps.setParty(new ArrayList<>(leader.getParty()));
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage(Utils.centerText(hsender.getDisplayName()+" §eleft the party"));
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		}
		if (leader.getParty().size() == 1) {
			disband(leader.getPlayer());
		}
	}
	
	private void disband(CommandSender sender) {
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		if (!hsender.isInParty()) {sender.sendMessage("§cYou are not in a party right now !");return;}
		if (!hsender.isPartyLeader()) {sender.sendMessage("§cOnly the party leader can do that !");return;}
		
		List<HPlayer> party = new ArrayList<>(hsender.getParty());
		
		for (HPlayer hps : party) {
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage(Utils.centerText("§eThe party got disbanded"));
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.setInParty(false);
			hps.setInGame(false, null);
			hps.getBoard().setTitle(hps.getDisplayName());
			hps.setPartyLeader(false);
			hps.getParty().clear();
		}
	}
	
	private void warp(CommandSender sender) {
		HPlayer hsender = HPlayer.getHPlayer(Bukkit.getPlayer(sender.getName()));
		if (!hsender.isInParty()) {sender.sendMessage("§cYou are not in a party right now !");return;}
		if (!hsender.isPartyLeader()) {sender.sendMessage("§cOnly the party leader can do that !");return;}
		
		sender.sendMessage("§eYou warped everyone to your position");
		
		for (HPlayer hps : hsender.getParty()) {
			if (hps.isPartyLeader()) continue;
			hps.getPlayer().teleport(hsender.getPlayer().getLocation());
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage(Utils.centerText(hsender.getDisplayName()+" §ewarped you"));
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		}
 	}
	
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			help(sender);
			return;
		}
		
		switch (args[0]) {
			case "help": case "h": help(sender);break;
			case "accept": accept(sender, args); break;
			case "list": list(sender);break;
			case "kick": kick(sender, args); break;
			case "leave": leave(sender); break;
			case "disband": disband(sender); break;
			case "warp": warp(sender); break;
			default: invite(sender, args); break;
		}
	}
	
	public static void autoLeave(Player p) {
		HPlayer hsender = HPlayer.getHPlayer(p);
		if (!hsender.isInParty()) {return;}
		
		if (hsender.isPartyLeader()) {autodisband(p);return;}
		
		HPlayer leader = hsender.getParty().get(0);
		
		hsender.getParty().clear();
		hsender.setInParty(false);
		hsender.setInGame(false, null);
		hsender.getBoard().setTitle(hsender.getDisplayName());
		
		leader.getParty().remove(hsender);
		
		for (HPlayer hps : leader.getParty()) {
			hps.setParty(new ArrayList<>(leader.getParty()));
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage(Utils.centerText(hsender.getDisplayName()+" §eleft the party"));
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		}
		if (leader.getParty().size() == 1) {
			autodisband(leader.getPlayer());
		}
	}
	
	private static void autodisband(Player p) {
		HPlayer hsender = HPlayer.getHPlayer(p);
		if (!hsender.isInParty()) {return;}
		if (!hsender.isPartyLeader()) {return;}
		
		List<HPlayer> party = new ArrayList<>(hsender.getParty());
		
		for (HPlayer hps : party) {
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage(Utils.centerText("§eThe party got disbanded"));
			hps.getPlayer().sendMessage("");
			hps.getPlayer().sendMessage("§a§l§m---------------------------------------------");
			hps.setInParty(false);
			hps.setInGame(false, null);
			hps.getBoard().setTitle(hps.getDisplayName());
			hps.setPartyLeader(false);
			hps.getParty().clear();
		}
	}
}
