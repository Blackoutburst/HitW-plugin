package event;

import java.io.File;

import analytics.AnalyticsActions;
import analytics.AnalyticsWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.npcapi.core.PacketInteractListener;
import com.blackoutburst.simplenpc.NPCFile;
import com.blackoutburst.simplenpc.SimpleNPCPlayer;

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
		AnalyticsWatcher.appendLine(
				System.currentTimeMillis()+ "," +
					AnalyticsActions.PLAYER_JOIN.data + "," +
					event.getPlayer().getUniqueId().toString().replace("-", "")
		);

		SimpleNPCPlayer pnpc = new SimpleNPCPlayer(event.getPlayer());
		Main.npcplayers.add(pnpc);
		NPCFile.loadNPC(pnpc);
		
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1054.5f, 0, 0));
		Utils.giveConfigItem(event.getPlayer());
		addHPlayer(event.getPlayer());
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		event.getPlayer().setAllowFlight(p.isFly());
		
		PacketInteractListener.init(event.getPlayer(), new NPCListener());
		NPCUtils.spawnNPC(event);
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
		boolean perfectOnly = false;
		boolean invisibleGlass = false;
		String songName = "Hyperdron - Inter-Dimensional Existence Kontrol";
		HPlayer newHp = null;
		
		int scoreFinals = 0;
		int scoreQualification = 0;
		int scoreWideQualification = 0;
		int scoreLobby = 0;
		int scoreWideFinals = 0;
		
		if (new File("./plugins/HitW/player data/"+p.getUniqueId().toString().replace("-", "")+".yml").exists()) {
			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/player data/"+p.getUniqueId().toString().replace("-", "")+".yml"));
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
			perfectOnly = playerData.getBoolean("perfectOnly");
			invisibleGlass = playerData.getBoolean("invisibleGlass");
			songName = playerData.getString("songName");
			scoreQualification = playerData.getInt("score.Q");
			scoreFinals = playerData.getInt("score.F");
			scoreWideQualification = playerData.getInt("score.WQ");
			scoreLobby = playerData.getInt("score.L");
			scoreWideFinals = playerData.getInt("score.WF");
			
			newHp = new HPlayer(p, wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, 
					rightSided, oldAnimation, blind, destroy, autoLeave, board, rank, songName,
					scoreQualification, scoreFinals, scoreWideQualification, scoreLobby, scoreWideFinals, perfectOnly, invisibleGlass);
			HPlayer.updatePlayerData(newHp);
		} else {
			Bukkit.broadcastMessage("§dWelcome "+p.getDisplayName()+" §dto the server!");
			newHp = new HPlayer(p, wallColor, glassColor, leverDelay, memTime, brushLag, fly, title, 
					rightSided, oldAnimation, blind, destroy, autoLeave, board, rank, songName,
					scoreQualification, scoreFinals, scoreWideQualification, scoreLobby, scoreWideFinals, perfectOnly, invisibleGlass);
			HPlayer.updatePlayerData(newHp);
		}
		Main.hPlayers.add(newHp);
		ScoreboardManager.setDefaultScoreboard(board, newHp);
		
		for (HPlayer hp : Main.hPlayers) {
			hp.getBoard().addTeam(hp, newHp);
			newHp.getBoard().addTeam(newHp, hp);
		}
	}
}
