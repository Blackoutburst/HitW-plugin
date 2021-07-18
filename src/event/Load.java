package event;

import java.io.File;

import org.bukkit.scheduler.BukkitRunnable;

import core.HPlayer;
import main.Main;
import utils.HGameLoader;
import utils.NPCUtils;

public class Load {
	
	public void execute() {
		new File("./plugins/HitW").mkdir();
		new File("./plugins/HitW/player data").mkdir();
		new File("./plugins/HitW/songs").mkdir();
		
		NPCUtils.loadSkins();
		HGameLoader.loadHGames();
		afkTimer();
	}
	
	private void afkTimer() {
		new BukkitRunnable(){
			@Override
			public void run(){
				for (HPlayer p : Main.hPlayers) {
					p.setAfkValue(p.getAfkValue() - 1);
					
					if (p.getAfkValue() <= 0) {
						p.getPlayer().setPlayerListName(p.getRank()+p.getPlayer().getName()+" §4§lAFK§r");
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
	}
}
