package event;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import core.Board;
import core.HPlayer;
import main.Main;
import utils.NPC;
import utils.RankManager;
import utils.ScoreboardManager;
import utils.Utils;

public class Join {
	
	public void execute(PlayerJoinEvent event) {
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0));
		Utils.giveConfigItem(event.getPlayer());
		addHPlayer(event.getPlayer());
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		event.getPlayer().setAllowFlight(p.isFly());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
			@Override
			public void run() {
				spawnHallOfFame(event);
				spawnTournamentWinner(event);
				spawnTeleportNPC(event);
			}
		}, 10L);
		
		AFKTimer(p);
	}
	
	private void AFKTimer(HPlayer p) {
		new BukkitRunnable(){
			@Override
			public void run(){
				if (p == null) {
            		this.cancel();
            		return;
            	}
				p.setAfkValue(p.getAfkValue() - 1);
				
				if (p.getAfkValue() <= 0 && !p.isAfk()) {
					p.setAfk(true);
					p.getPlayer().setPlayerListName(p.getRank()+p.getPlayer().getName()+" §4§lAFK§r");
				} else if (p.getAfkValue() > 0 && p.isAfk()) {
					p.setAfk(false);
					p.getPlayer().setPlayerListName(p.getRank()+p.getPlayer().getName()+"§r");
				}
			}
		}.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
	}
	
	private void addHPlayer(Player p) {
		String rank = RankManager.loadRank(p);
		Board board = new Board(p, rank, "§r");
		
		short wallColor = 9;
		short glassColor = 5;
		float leverDelay = 0.5f;
		float memTime = 1.0f;
		float brushLag = 100.0f;
		boolean fly = true;
		boolean title = true;
		boolean rightSided = false;
		boolean oldAnimation = false;
		boolean blind = false;
		boolean destroy = false;
		boolean autoLeave = true;
		String songName = "Hyperdron - Inter-Dimensional Existence Kontrol";
		
		if (new File("./plugins/HitW/player data/"+p.getUniqueId()+".yml").exists()) {
			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/player data/"+p.getUniqueId()+".yml"));
			wallColor = (short) playerData.getInt("colors.wall");
			glassColor = (short) playerData.getInt("colors.glass");
			leverDelay = (float) playerData.getDouble("leverDelay");
			memTime = (float) playerData.getDouble("memtime");
			brushLag = (float) playerData.getDouble("brushLag");
			fly = playerData.getBoolean("fly");
			title = playerData.getBoolean("title");
			rightSided = playerData.getBoolean("rightSided");
			oldAnimation = playerData.getBoolean("oldAnimation");
			blind = playerData.getBoolean("blind");
			destroy = playerData.getBoolean("destroy");
			autoLeave = playerData.getBoolean("autoLeave");
			songName = playerData.getString("songName");
		} else {
			Bukkit.broadcastMessage("§dWelcome "+p.getDisplayName()+" §dto the server!");
			HPlayer.writePlayerData(new File("./plugins/HitW/player data/"+p.getUniqueId()+".yml"), wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, rightSided, oldAnimation, blind, destroy, autoLeave, songName);
		}
		HPlayer newHp = new HPlayer(p, wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, rightSided, oldAnimation, blind, destroy, autoLeave, board, rank, songName);
		Main.hPlayers.add(newHp);
		ScoreboardManager.setDefaultScoreboard(board);
		
		for (HPlayer hp : Main.hPlayers) {
			Board.addTeam(hp, newHp);
			Board.addTeam(newHp, hp);
		}
	}
	
	private void spawnTeleportNPC(PlayerJoinEvent event) {
		NPC.spawn(event.getPlayer(), "paffol3", -2.5f, 55.0f, -1031.5f, 180, 0, "qualification");
		NPC.spawn(event.getPlayer(), "Arcire", -12.5f, 55.0f, -1031.5f, 180, 0, "finals");
		NPC.spawn(event.getPlayer(), "Blackuwu", -5.5f, 54.0f, -1007.5f, 135, 0, "spawn");
		NPC.spawn(event.getPlayer(), "Blackuwu", -66.5f, 54.0f, -1036.5f, -135, 0, "spawn");
	}
	
	private void spawnHallOfFame(PlayerJoinEvent event) {
		NPC.spawn(event.getPlayer(), "Somi", 15.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "paffol3", 15.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "Arcire", 19.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "catfury400", 19.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "Ted", 23.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "Spar", 23.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "Tasted", 27.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "NoobLMason", 27.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "HamsterWall", 31.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "Blackuwu", 31.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "alo", 35.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "DOG", 35.5f, 55.0f, -1034.5f, 180, 0, "");
	}
	
	private void spawnTournamentWinner(PlayerJoinEvent event) {
		NPC.spawn(event.getPlayer(), "Somi", -30.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "NoobLMason", -30.5f, 55.0f, -1034.5f, 180, 0, "");
		NPC.spawn(event.getPlayer(), "STEK", -34.5f, 55.0f, -1042.5f, 0, 0, "");
		NPC.spawn(event.getPlayer(), "DOG", -34.5f, 55.0f, -1034.5f, 180, 0, "");
	}
}
