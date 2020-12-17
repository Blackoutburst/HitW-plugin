package event;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import game.TourneyManager;
import main.GamePlayer;
import main.Main;
import utils.Board;
import utils.RankManager;
import utils.Tools;
import utils.Values;

/**
 * Manage every action when onPlayerJoin event is called
 * 
 * @author Blackoutburst
 */
public class Join {

	/**
	 * When a player join create a new Scoreboard and load player rank from Hypixel
	 * Hole in the Wall data if the player is new create config file for him or load
	 * current data Create a new GamePlayer object
	 * 
	 * @param event Player Join event
	 * @author Blackoutburst
	 */
	public void onJoin(PlayerJoinEvent event) {
		String rank = RankManager.loadRank(event.getPlayer());
		Board board = new Board(event.getPlayer(), rank, "§r");
		int wallColor = 4;
		int glassColor = 3;
		float leverDelay = 0.5f;
		boolean showTitle = true;

		File f = new File(Tools.getPlayerFolder(event.getPlayer()));
		if (!f.exists()) {
			f.mkdir();
			Tools.writePlayerData(f, 4, 3, 0.5f, true);
		} else {
			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(f+"/config.yml"));
			wallColor = playerData.getInt("colors.wall");
			glassColor = playerData.getInt("colors.glass");
			leverDelay = (float) playerData.getDouble("delay");
			showTitle = playerData.getBoolean("title");
		}

		setToSpawn(event.getPlayer());
		setScoreBoard(board);
		GamePlayer newPlayer = new GamePlayer(event.getPlayer(), false, 0, 0, 0, board, "none", 0, 0, 0, 0, false, rank,
				0, 0, 0, wallColor, glassColor, leverDelay, false, "none", false, showTitle);
		addTeam(newPlayer);
		event.getPlayer().setAllowFlight(true);
		Main.players.add(newPlayer);
		if (!Main.tourneyStage.equals("none")) {
			TourneyManager.displaySpectateMessage();
		}
	}

	/**
	 * Add the new player to everyone Add all player to the new player This is
	 * important to display rank on player nametag
	 * 
	 * @param newPlayer newest player
	 * @author Blackoutburst
	 */
	private void addTeam(GamePlayer newPlayer) {
		for (GamePlayer p : Main.players) {
			Board.addTeam(p, newPlayer);
			Board.addTeam(newPlayer, p);
		}
	}

	/**
	 * Create a new Scoreboard with default value
	 * 
	 * @param board Scoreboard object
	 * @author Blackoutburst
	 */
	private void setScoreBoard(Board board) {
		int minutes = 0 / 60;
		int seconds = (0) % 60;
		String str = String.format("%d:%02d", minutes, seconds);

		board.set(15, " ");
		board.set(14, "Stage: §anone");
		board.set(13, "Play Time: §a" + str);
		board.set(12, "  ");
		board.set(11, "Perfect Walls: §a" + 0);
		board.set(10, "Wall: §a" + 0);
		board.set(9, "Score: §a" + 0);
		board.set(8, "   ");
		board.set(7, "Missing block: §4" + 0);
		board.set(6, "Misplaced block: §4" + 0);
		board.set(5, "Choke: §4" + 0);
		board.set(4, "    ");
		board.set(3, "Fly: §bOn");
		board.set(4, "     ");
	}

	/**
	 * Teleport the player to the spawn location
	 * 
	 * @param player newest player
	 * @author Blackoutburst
	 */
	private void setToSpawn(Player player) {
		player.teleport(Values.spawn);
	}
}
