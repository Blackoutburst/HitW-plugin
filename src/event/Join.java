package event;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.blackout.npcapi.core.PacketInteractListener;

import core.Board;
import core.HPlayer;
import main.Main;
import utils.NPCListener;
import utils.NPCUtils;
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
		
		PacketInteractListener.init(event.getPlayer(), new NPCListener());
		NPCUtils.spawnNPC(event);
		
		afkTimer(p);
	}
	
	private void afkTimer(HPlayer p) {
		new BukkitRunnable(){
			@Override
			public void run(){
				if (p == null) {
            		this.cancel();
            		return;
            	}
				p.setAfkValue(p.getAfkValue() - 1);
				
				if (p.getAfkValue() <= 0) {
					p.getPlayer().setPlayerListName(p.getRank()+p.getPlayer().getName()+" §4§lAFK§r");
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
		HPlayer newHp = null;
		
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
			newHp = new HPlayer(p, wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, rightSided, oldAnimation, blind, destroy, autoLeave, board, rank, songName);
		} else {
			Bukkit.broadcastMessage("§dWelcome "+p.getDisplayName()+" §dto the server!");
			newHp = new HPlayer(p, wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, rightSided, oldAnimation, blind, destroy, autoLeave, board, rank, songName);
			HPlayer.updatePlayerData(newHp);
		}
		Main.hPlayers.add(newHp);
		ScoreboardManager.setDefaultScoreboard(board);
		
		for (HPlayer hp : Main.hPlayers) {
			hp.getBoard().addTeam(hp, newHp);
			newHp.getBoard().addTeam(newHp, hp);
		}
	}
}
