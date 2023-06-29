package event;

import java.io.File;

import analytics.AnalyticsActions;
import analytics.AnalyticsWatcher;
import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
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
		HPlayer p = HPlayer.addHPlayer(event.getPlayer());
		
		event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1054.5f, 0, 0));
		Utils.giveConfigItem(event.getPlayer());
		event.getPlayer().setAllowFlight(p.isFly());
		NoteBlockAPI.getSongPlayersByPlayer(event.getPlayer()).forEach(rsp -> {
			rsp.setPlaying(false);
			rsp.destroy();
		});

		PacketInteractListener.init(event.getPlayer(), new NPCListener());
		NPCUtils.spawnNPC(event);

		SimpleNPCPlayer pnpc = new SimpleNPCPlayer(event.getPlayer());
		Main.npcplayers.add(pnpc);
		NPCFile.loadNPC(pnpc);
	}
}
