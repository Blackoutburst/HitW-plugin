package commands;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import core.GameUpdater;
import core.HGame;
import core.HPlayer;
import core.WallManager;
import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.GameUtils;
import utils.ScoreboardManager;
import utils.Utils;

public class CommandDuel {
	
	private static void duelQualification(HPlayer p) {
		p.setDuelType("Qualification");
		p.getPlayer().sendMessage("§eDuel request send to " + p.getOpponent().getDisplayName());
		
		TextComponent msg = new TextComponent(Utils.centerText("§eClick §bhere §eto accept"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + p.getPlayer().getName()));
		p.getOpponent().getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getOpponent().getPlayer().sendMessage(" ");
		p.getOpponent().getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(p.getPlayer().getName()).getDisplayName() + " §einvited you to a §bQualification §eduel !"));
		p.getOpponent().getPlayer().spigot().sendMessage(msg);
		p.getOpponent().getPlayer().sendMessage(" ");
		p.getOpponent().getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getOpponent().setDuelInvite(true);
		
		new BukkitRunnable(){
			@Override
			public void run(){
				p.getOpponent().setDuelInvite(false);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L * 30);
	}
	
	private static void duelFinals(HPlayer p) {
		p.setDuelType("Finals");
		p.getPlayer().sendMessage("§eDuel request send to " + p.getOpponent().getDisplayName());
		
		TextComponent msg = new TextComponent(Utils.centerText("§eClick §bhere §eto accept"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + p.getPlayer().getName()));
		p.getOpponent().getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getOpponent().getPlayer().sendMessage(" ");
		p.getOpponent().getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(p.getPlayer().getName()).getDisplayName() + " §einvited you to a §bFinals §eduel !"));
		p.getOpponent().getPlayer().spigot().sendMessage(msg);
		p.getOpponent().getPlayer().sendMessage(" ");
		p.getOpponent().getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getOpponent().setDuelInvite(true);
		
		new BukkitRunnable(){
			@Override
			public void run(){
				p.getOpponent().setDuelInvite(false);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L * 30);
	}
	
	public static void duelGUIAction(Inventory inv, int slot, HPlayer p) {
		switch(slot) {
			case 11:
				if (!Main.QDuelBusy) {
					duelQualification(p);
				} else {
					p.getPlayer().sendMessage("§cThis duel arena is busy right now, try again in a few minute !");
				}
				p.getPlayer().closeInventory();
			break;
			case 15:
				if (!Main.FDuelBusy) {
					duelFinals(p);
				} else {
					p.getPlayer().sendMessage("§cThis duel arena is busy right now, try again in a few minute !");
				}
				p.getPlayer().closeInventory();
			break;
			default:break;
		}
	}
	
	private void openDuelGUI(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 27, ChatColor.BLACK + "Duel Game");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 27; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("§6Duel you opponent in a");
        lore.add("§6Qualification game");
        skull.setLore(lore);
        skull.setDisplayName("§r§aQualification");
        skull.setOwner("puffleman3");
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("§6Duel you opponent in a");
        lore.add("§6Finals game");
        skull.setLore(lore);
        skull.setDisplayName("§r§aFinals");
        skull.setOwner("Arcxire");
        item.setItemMeta(skull);
        inv.setItem(15, item);
        
        p.getPlayer().openInventory(inv);
	}
	
	private void preStart(HPlayer p, HGame game) {
		WallManager.resetWall(game);
		WallManager.resetPlayField(game, p, true);
		
		game.setWallPulled(false);
		game.setOwner(p);

		if (p.isInParty()) {
			for (HPlayer hp : p.getParty())  {
				GameUtils.displayCountdown(hp, 10, game);
			}
		} else {
			GameUtils.displayCountdown(p, 10, game);
		}
		
		if (p.isInParty()) {
			for (HPlayer hp : p.getParty()) {
				hp.setPerfectWall(0);
				hp.setWall(1);
				hp.setChoke(0);
				hp.setMisplaced(0);
				hp.setMissed(0);
				hp.setScore(0);
				hp.setWallBegin(Instant.now());
				hp.getWallTime().clear();
			}
		} else {
			p.setPerfectWall(0);
			p.setWall(1);
			p.setChoke(0);
			p.setMisplaced(0);
			p.setMissed(0);
			p.setScore(0);
			p.setWallBegin(Instant.now());
			p.getWallTime().clear();
		}
		
		final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;
		
		new BukkitRunnable(){
			@Override
			public void run(){
				if (game.getOwner() == null) {
					this.cancel();
					return;
				}
				WallManager.resetPlayField(game, p, true);
				if (leader.isDestroy() && !leader.isBlind()) {
					WallManager.fillPlayField(game, p);
				}
				if (leader.isBlind()) {
					WallManager.hideWall(p, game);
				} 
				if (p.isInParty()) {
					for (HPlayer hp : p.getParty()) {
						hp.getPlayer().getInventory().clear();
						ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, hp.getGlassColor());
						hp.getPlayer().getInventory().setItem(0, stack);
						hp.setWallBegin(Instant.now());
					}
				} else {
					p.getPlayer().getInventory().clear();
					ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
					p.getPlayer().getInventory().setItem(0, stack);
					p.setWallBegin(Instant.now());
				}
				WallManager.generateWall(p, game, false);
				if (p.isInParty()) {
					for (HPlayer hp : p.getParty())  {
						hp.setInGame(true, game);
					}
				} else {
					p.setInGame(true, game);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class),  20L * 11);	
		
		ScoreboardManager.updateDuelScoreboard(p);
	}
	
	
	private void preGame(HPlayer p2, String stage) {
		HPlayer p1 = p2.getOpponent();
		
		Main.QDuelBusy = stage.equals("Qualification") ? true : false;
		Main.FDuelBusy = stage.equals("Finals") ? true : false;
		
		setPlayers(p1, p2, stage);
		
		HGame game1 = GameUtils.getGameArea(p1.getPlayer());
		HGame game2 = GameUtils.getGameArea(p2.getPlayer());
		
		game1.setIncrementingHoles(true);
		game2.setIncrementingHoles(true);
		preStart(p1, game1);
		preStart(p2, game2);
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateClassic(p1, game1);
				game1.setLeverBusy(true);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game1.setLeverBusy(false);
						game1.setType("Classic");
					}
				}, (30L));
			}
		}, (20L * 11));
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateClassic(p2, game2);
				game2.setLeverBusy(true);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game2.setLeverBusy(false);
						game2.setType("Classic");
					}
				}, (30L));
			}
		}, (20L * 11));
		
	}
	
	private void setPlayers(HPlayer p1, HPlayer p2, String stage) {
		Location A = stage.equals("Qualification") ? new Location(Bukkit.getWorld("world"), 79.5f, 55, -1057.5f, -90, 0) : new Location(Bukkit.getWorld("world"), 78.5f, 55, -996.5f, -90, 0);
		Location B = stage.equals("Qualification") ? new Location(Bukkit.getWorld("world"), 79.5f, 55, -1043.5f, -90, 0) : new Location(Bukkit.getWorld("world"), 78.5f, 55, -978.5f, -90, 0);

		p1.setAutoLeave(false);
		p1.setInDuel(true);
		if (p1.isInParty()) {
			for (HPlayer hp : p1.getParty()) {
				hp.setMemTime(p1.getMemTime());
				hp.setBlind(p1.isBlind());
				hp.setOldAnimation(p1.isOldAnimation());
				hp.setDestroy(p1.isDestroy());
				hp.setAutoLeave(false);
				hp.setInDuel(true);
				hp.setTime(120);
				hp.getPlayer().teleport(A);
				hp.getBoard().setTitle("§6-= Duel =-");
				ScoreboardManager.setDuelScoreboard(stage, hp);
			}
		} else {
			p1.setTime(120);
			p1.getPlayer().teleport(A);
			p1.getBoard().setTitle("§6-= Duel =-");
			ScoreboardManager.setDuelScoreboard(stage, p1);
		}
		
		p2.setMemTime(p1.getMemTime());
		p2.setBlind(p1.isBlind());
		p2.setOldAnimation(p1.isOldAnimation());
		p2.setDestroy(p1.isDestroy());
		p2.setAutoLeave(false);
		p2.setInDuel(true);
		if (p2.isInParty()) {
			for (HPlayer hp : p2.getParty()) {
				hp.setMemTime(p1.getMemTime());
				hp.setBlind(p1.isBlind());
				hp.setOldAnimation(p1.isOldAnimation());
				hp.setDestroy(p1.isDestroy());
				hp.setAutoLeave(false);
				hp.setInDuel(true);
				hp.setTime(120);
				hp.getPlayer().teleport(B);
				hp.getBoard().setTitle("§6-= Duel =-");
				ScoreboardManager.setDuelScoreboard(stage, hp);
			}
		} else {
			p2.setTime(120);
			p2.getPlayer().teleport(B);
			p2.getBoard().setTitle("§6-= Duel =-");
			ScoreboardManager.setDuelScoreboard(stage, p2);
		}
	}
	
	private void accept(HPlayer p, String[] args) {
		HPlayer opponent = HPlayer.getHPlayer(Bukkit.getPlayer(args[1]));
		if (opponent == null) {p.getPlayer().sendMessage("§cUnknow player §r" + args[0] + " §c!"); return;}
		
		p.setDuelInvite(false);
		p.setInDuel(true);
		p.setOpponent(opponent);
		p.getOpponent().setInDuel(true);
		opponent.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		opponent.getPlayer().sendMessage("");
		opponent.getPlayer().sendMessage(Utils.centerText(Bukkit.getPlayer(p.getPlayer().getName()).getDisplayName() + " §eaccepted your duel invitation !"));
		opponent.getPlayer().sendMessage("");
		opponent.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		preGame(p, opponent.getDuelType());
	}
	
	public void run(CommandSender sender, String[] args) {
		HPlayer hsender = HPlayer.getHPlayer((Player) sender);
		if (args.length == 0) {sender.sendMessage("§cMissing arguments, try §r/duel [player] §c!"); return;}
		HPlayer opponent = HPlayer.getHPlayer(Bukkit.getPlayer(args[0]));
		if (args.length == 1) {
			if (opponent == null) {sender.sendMessage("§cUnknow player §r" + args[0] + " §c!"); return;}
			if (opponent == hsender) {sender.sendMessage("§cYou can't duel yourself !"); return;}
			
			if (opponent.isInParty() && !opponent.isPartyLeader()) {sender.sendMessage("§cYou need to duel the party leader (" + opponent.getParty().get(0).getDisplayName() + "§c) !"); return;}
			
			hsender.setOpponent(opponent);
			openDuelGUI(hsender);
		}
		
		if (args.length == 2 && args[0].equalsIgnoreCase("accept")) {
			if (!hsender.isDuelInvite()) {sender.sendMessage("§cNobody invited you tu a duel !"); return;}
			accept(hsender, args);
		}
	}
}
