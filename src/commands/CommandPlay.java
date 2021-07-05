package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import core.GameUpdater;
import core.HGame;
import core.HPlayer;
import core.WallManager;
import main.Main;
import utils.GameUtils;
import utils.ScoreboardManager;

public class CommandPlay {
	
	private void preStart(HPlayer p, HGame game) {
		WallManager.resetWall(game);
		WallManager.resetPlayField(game, p, true);
		
		game.setWallPulled(false);
		game.setOwner(p);

		GameUtils.preparePlayers(p);
		GameUtils.setCountdown(p, game, 5);
		
		final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;
		
		new BukkitRunnable(){
			@Override
			public void run(){
				if (game.getOwner() == null) {
					this.cancel();
					return;
				}
				GameUtils.gameOptions(leader, p, game);
				GameUtils.prepareGameStart(p, game);
			}
		}.runTaskLater(Main.getPlugin(Main.class),  20L * 6);	
		
		ScoreboardManager.updateScoreboard(p);
	}
	
	private void classic(HPlayer p, HGame game) {
		preStart(p, game);
		if (p.isInParty()) {
    		for (HPlayer hp : p.getParty()) {
    			hp.setTime(120);
    		}
    	} else {
    		p.setTime(120);
    	}
		game.setIncrementingHoles(true);
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateTime(p, game);
				game.setLeverBusy(true);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game.setLeverBusy(false);
						game.setType("Classic");
					}
				}, (30L));
			}
		}, (20L * 6));
	}
	
	private void score(HPlayer p, String[] args, HGame game) {
		if (args.length < 2) {p.getPlayer().sendMessage("§cInvalid usage try §b/play score §r[value] §c!");return;}
		try {Integer.valueOf(args[1]);} catch(Exception e) {p.getPlayer().sendMessage("§cThe score limit must be a valid number");return;}
		
		preStart(p, game);
		p.setScoreLimit(Integer.valueOf(args[1]));
		if (p.isInParty()) {
    		for (HPlayer hp : p.getParty()) {
    			hp.setTime(0);
    		}
    	} else {
    		p.setTime(0);
    	}
		game.setIncrementingHoles(true);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateScore(p, game);
				game.setLeverBusy(true);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game.setLeverBusy(false);
						game.setType("Score");
					}
				}, (30L));
			}
		}, (20L * 6));
	}
	
	private void time(HPlayer p, String[] args, HGame game) {
		if (args.length < 2) {p.getPlayer().sendMessage("§cInvalid usage try §b/play time §r[value] §c!");return;}
		try {Integer.valueOf(args[1]);} catch(Exception e) {p.getPlayer().sendMessage("§cThe time limit must be a valid number");return;}
		
		preStart(p, game);
		if (p.isInParty()) {
    		for (HPlayer hp : p.getParty()) {
    			hp.setTime(Integer.valueOf(args[1]));
    		}
    	} else {
    		p.setTime(Integer.valueOf(args[1]));
    	}
		
		game.setIncrementingHoles(true);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateTime(p, game);
				game.setLeverBusy(true);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game.setLeverBusy(false);
						game.setType("Time");
					}
				}, (30L));
			}
		}, (20L * 6));
	}
	
	private void endless(HPlayer p, HGame game) {
		preStart(p, game);
		if (p.isInParty()) {
    		for (HPlayer hp : p.getParty()) {
    			hp.setTime(0);
    		}
    	} else {
    		p.setTime(0);
    	}
		game.setIncrementingHoles(false);
		
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				GameUpdater.updateEndless(p, game);
				game.setLeverBusy(true);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game.setType("Endless");
						game.setLeverBusy(false);
					}
				}, (long)(20L * p.getLeverDelay()));
			}
		}, (20L * 6));
	}
	
	public void run(CommandSender sender, String[] args) {
		HPlayer p = HPlayer.getHPlayer((Player) sender);
		HGame game = GameUtils.getGameArea(p.getPlayer());
		
		if (p.isInGame()) {sender.sendMessage("§cLeave your previous game before starting a new one !");return;}
		if (p.isInParty() && !p.isPartyLeader()) {sender.sendMessage("§cOnly the party leader can start a game !");return;}
		if (game == null) {sender.sendMessage("§cPlease enter any game area before using this command !");return;}
		if (game.getOwner() != null) {sender.sendMessage("§cThis game is already used by " + game.getOwner().getDisplayName() + " §c!");return;}
		
		if (args.length >= 1) {
			switch(args[0]) {
				case "classic": classic(p, game); break;
				case "score": score(p, args, game); break;
				case "time": time(p, args, game); break;
				default: sender.sendMessage("§cUnknown parrameter : " + args[0] + " !"); break;
			}
		} else {
			endless(p, game);
		}
	}
}
