package core;

import org.bukkit.scheduler.BukkitRunnable;

import main.Main;
import utils.GameUtils;
import utils.ScoreboardManager;

public class GameUpdater {

	private static void showLastSecond(HPlayer p) {
    	if (p.getTime() <= 5 && p.getTime() > 0) {
    		if (p.isTitle())
    			GameUtils.sendTitle(p.getPlayer(), "", "§6The game end in §a"+p.getTime()+" §6seconds!", 0, 20, 0);
    		else
    			p.getPlayer().sendMessage("§6The game end in §a"+p.getTime()+" §6seconds!");
    	}
	}
	
	public static void stopGame(HPlayer p, HGame game) {
		if (p.isInParty()) {
			for (HPlayer hp : p.getParty()) {
				hp.setInGame(false, game);
				hp.restorePlayerData();
			}
		} else {
			p.setInGame(false, game);
			p.restorePlayerData();
		}
	}
	
	public static void updateEndless(HPlayer p, HGame game) {
		new BukkitRunnable(){
            @Override
            public void run(){
            	if (game.getOwner() == null) {
            		this.cancel();
            		return;
            	}
            	if (p.isInParty()) {
            		for (HPlayer hp : p.getParty()) {
            			hp.setTime(hp.getTime() + 1);
            		}
            	} else {
            		p.setTime(p.getTime() + 1);
            	}
            	ScoreboardManager.updateScoreboard(p);
            }
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
	}

	public static void updateTime(HPlayer p, HGame game) {
		new BukkitRunnable(){
            @Override
            public void run(){
            	if (game.getOwner() == null) {
            		this.cancel();
            		return;
            	}
            	if (p.isInParty()) {
            		for (HPlayer hp : p.getParty()) {
            			hp.setTime(hp.getTime() - 1);
            		}
            	} else {
            		p.setTime(p.getTime() - 1);
            	}
            	ScoreboardManager.updateScoreboard(p);
            	showLastSecond(p);
            	if (p.getTime() <= 0) {
            		this.cancel();
            		game.setOwner(null);
            		return;
            	}
            }
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);	
	}

	public static void updateScore(HPlayer p, HGame game) {
		new BukkitRunnable(){
            @Override
            public void run(){
            	if (game.getOwner() == null) {
            		this.cancel();
            		return;
            	}
            	if (p.isInParty()) {
            		for (HPlayer hp : p.getParty()) {
            			hp.setTime(hp.getTime() + 1);
            		}
            	} else {
            		p.setTime(p.getTime() + 1);
            	}
            	ScoreboardManager.updateScoreboard(p);
            	
            	if (p.getScore() >= p.getScoreLimit()) {
            		this.cancel();
            		game.setOwner(null);
            		return;
            	}
            }
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
	}

	public static void updateClassic(HPlayer p, HGame game) {
		new BukkitRunnable(){
            @Override
            public void run(){
            	if (game.getOwner() == null) {
            		this.cancel();
            		return;
            	}
            	if (p.isInParty()) {
            		for (HPlayer hp : p.getParty()) {
            			hp.setTime(hp.getTime() - 1);
            		}
            	} else {
            		p.setTime(p.getTime() - 1);
            	}
            	ScoreboardManager.updateScoreboard(p);
            	showLastSecond(p);
            	if (p.getTime() <= 0) {
            		game.setOwner(null);
            		this.cancel();
            		return;
            	}
            }
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
	}
}
