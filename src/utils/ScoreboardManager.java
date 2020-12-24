package utils;

import main.GamePlayer;
import main.Main;

/**
 * Manage Scorebaord
 * @author black
 *
 */
public class ScoreboardManager {
	/**
	 * Update Scorebaord on demand
	 * @param player player Scoreboard who need update
	 * @author Blackoutburst
	 */
	public static void update(GamePlayer player) {
		if (player.isInTourney()) {
			tournamentBoard(player);
    	} else if (player.isInCoop()) {
    		coopBoard(player);
    	}else {
    		trainingBoard(player);
    	}
	}
	
	/**
	 * Update coop Scoreboard
	 * @param player player Scoreboard who need update
	 * @author Blackoutburst
	 */
	private static void coopBoard(GamePlayer player) {
		GamePlayer leader = player.getCoop().getPlayers().get(0);
		int minutes = leader.getTime() / 60;
 		int seconds = (leader.getTime()) % 60;
 		String str = String.format("%d:%02d", minutes, seconds);
 		
 		if (!player.getBoard().getTitle().equals("§6§l--= Co-op =--")) {
 			player.getBoard().setTitle("§6§l--= Co-op =--");
 		}
 		player.getBoard().set(15, " ");
 		player.getBoard().set(14, "Stage: §a"+leader.getStage());
 		player.getBoard().set(13, "Play Time: §a"+str);
 		player.getBoard().set(12, "  ");
 		if (player.getCoop().getPlayers().size() >= 1) {
 			player.getBoard().set(11, player.getCoop().getPlayers().get(0).getPlayer().getName());
 		}
 		if (player.getCoop().getPlayers().size() >= 2) {
 			player.getBoard().set(10, player.getCoop().getPlayers().get(1).getPlayer().getName());
 		}
 		if (player.getCoop().getPlayers().size() >= 3) {
 			player.getBoard().set(9, player.getCoop().getPlayers().get(2).getPlayer().getName());
 		}
 		if (player.getCoop().getPlayers().size() >= 4) {
 			player.getBoard().set(8, player.getCoop().getPlayers().get(3).getPlayer().getName());
 		}
 		player.getBoard().set(7, "   ");
 		player.getBoard().set(6, "Perfect Walls: §a"+leader.getPerfectwalls());
 		player.getBoard().set(5, "Wall: §a"+leader.getWalls());
 		player.getBoard().set(4, "Score: §a"+player.getScore());
 		player.getBoard().set(3, "    ");
 		if (player.getPlayer().getAllowFlight()) {
 			player.getBoard().set(2, "Fly: §bOn");
 		} else {
 			player.getBoard().set(2, "Fly: §cOff");
 		}
	}
	
	/**
	 * Update Tournament Scoreboard
	 * @param player player Scoreboard who need update
	 * @author Blackoutburst
	 */
	private static void tournamentBoard(GamePlayer player) {
		int minutes = Main.stageTime / 60;
 		int seconds = (Main.stageTime) % 60;
 		String str = String.format("%d:%02d", minutes, seconds);
 		
 		if (!player.getBoard().getTitle().equals("§6§lTournament")) {
 			player.getBoard().setTitle("§6§lTournament");
 		}
 		player.getBoard().set(15, " ");
 		player.getBoard().set(14, "§6=================");
 		player.getBoard().set(13, "Stage: §a"+Main.tourneyStage);
 		player.getBoard().set(12, "Stage End: §a"+str);
 		player.getBoard().set(11, "  ");
 		if (Main.player1.getScore() >= Main.player2.getScore()) {
 			player.getBoard().set(10, Main.player1.getPlayer().getName()+": §a"+Main.player1.getScore());
	 		player.getBoard().set(9, "Wall: §a"+Main.player1.getWalls()+" ");
	 		player.getBoard().set(8, "Perfect Wall: §a"+Main.player1.getPerfectwalls()+" ");
	 		player.getBoard().set(7, "§6-----------------");
	 		player.getBoard().set(6, Main.player2.getPlayer().getName()+": §a"+Main.player2.getScore());
	 		player.getBoard().set(5, "Wall: §a"+Main.player2.getWalls());
	 		player.getBoard().set(4, "Perfect Wall: §a"+Main.player2.getPerfectwalls());
 		} else {
 			player.getBoard().set(10, Main.player2.getPlayer().getName()+": §a"+Main.player2.getScore());
	 		player.getBoard().set(9, "Wall: §a"+Main.player2.getWalls()+" ");
	 		player.getBoard().set(8, "Perfect Wall: §a"+Main.player2.getPerfectwalls()+" ");
	 		player.getBoard().set(7, "§6-----------------");
	 		player.getBoard().set(6, Main.player1.getPlayer().getName()+": §a"+Main.player1.getScore());
	 		player.getBoard().set(5, "Wall: §a"+Main.player1.getWalls());
	 		player.getBoard().set(4, "Perfect Wall: §a"+Main.player1.getPerfectwalls());
 		}
 		player.getBoard().set(3, "    ");
 		player.getBoard().set(2, "§6hitw.minesr.com");
	}
	
	/**
	 * Update training Scoreboard
	 * @param player player Scoreboard who need update
	 * @author Blackoutburst
	 */
	private static void trainingBoard(GamePlayer player) {
		int minutes = player.getTime() / 60;
 		int seconds = (player.getTime()) % 60;
 		String str = String.format("%d:%02d", minutes, seconds);
 		
 		if (!player.getBoard().getTitle().equals(player.getPlayer().getName())) {
 			player.getBoard().setTitle(player.getPlayer().getName());
 		}
 		player.getBoard().set(15, " ");
 		player.getBoard().set(14, "Stage: §a"+player.getStage());
 		player.getBoard().set(13, "Play Time: §a"+str);
 		player.getBoard().set(12, "  ");
 		player.getBoard().set(11, "Perfect Walls: §a"+player.getPerfectwalls());
 		player.getBoard().set(10, "Wall: §a"+player.getWalls());
 		player.getBoard().set(9, "Score: §a"+player.getScore());
 		player.getBoard().set(8, "   ");
 		player.getBoard().set(7, "Missing block: §4"+player.getMissing());
 		player.getBoard().set(6, "Misplaced block: §4"+player.getMisplaced());
 		player.getBoard().set(5, "Choke: §4"+player.getChoke());
 		player.getBoard().set(4, "    ");
 		if (player.getPlayer().getAllowFlight()) {
 			player.getBoard().set(3, "Fly: §bOn");
 		} else {
 			player.getBoard().set(3, "Fly: §cOff");
 		}
 		player.getBoard().set(2, "     ");
	}
}
