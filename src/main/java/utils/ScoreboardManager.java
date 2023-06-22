package utils;

import core.Board;
import core.HPlayer;
import reflect.CreditImporter;

import java.text.NumberFormat;
import java.util.Locale;

public class ScoreboardManager {
	
	public static void updateStage(Board board, String name) {
		if (board.get(11).contains(name)) return;
		board.set(11, "Stage: §a" + name);
	}
	
	public static void updateScoreboard(HPlayer p) {
		if (p.isInDuel()) {updateDuelScoreboard(p);return;}
		
		int minutes = p.getTime() / 60;
		int seconds = (p.getTime()) % 60;
		String str = String.format("%d:%02d", minutes, seconds);

		if (p.isInParty()) {
			for (HPlayer hp : p.getParty()) {
				hp.getBoard().set(10, "Play Time: §a" + str);
				hp.getBoard().set(8, "Perfect Walls: §a" + hp.getPerfectWall());
				hp.getBoard().set(7, "Wall: §a" + hp.getWall());
				hp.getBoard().set(6, "Score: §a" + hp.getScore());
				hp.getBoard().set(4, "Missing block: §4" + hp.getMissed());
				hp.getBoard().set(3, "Misplaced block: §4" + hp.getMisplaced());
				hp.getBoard().set(2, "Choke: §4" + hp.getChoke());
			}
		} else {
			p.getBoard().set(10, "Play Time: §a" + str);
			p.getBoard().set(8, "Perfect Walls: §a" + p.getPerfectWall());
			p.getBoard().set(7, "Wall: §a" + p.getWall());
			p.getBoard().set(6, "Score: §a" + p.getScore());
			p.getBoard().set(4, "Missing block: §4" + p.getMissed());
			p.getBoard().set(3, "Misplaced block: §4" + p.getMisplaced());
			p.getBoard().set(2, "Choke: §4" + p.getChoke());
		}
	}
	
	
	public static void updateDuelScoreboard(HPlayer p) {
		int minutes = p.getTime() / 60;
		int seconds = (p.getTime()) % 60;
		String str = String.format("%d:%02d", minutes, seconds);

		if (p.isInParty()) {
			HPlayer leader = p.getParty().get(0);
			
			for (HPlayer hp : p.getParty()) {
				String player1 = leader.getDisplayName().substring(0, 2) + leader.getPlayer().getName() + "§r: " + leader.getScore();
				String player2 = leader.getOpponent().getDisplayName().substring(0, 2) + leader.getOpponent().getPlayer().getName() + "§r: " + leader.getOpponent().getScore();
				
				hp.getBoard().set(8, "Play Time: §a" + str);
				hp.getBoard().set(7, " ");
				hp.getBoard().set(6, leader.getScore() >= leader.getOpponent().getScore() ? player1 : player2);
				hp.getBoard().set(5, leader.getScore() < leader.getOpponent().getScore() ? player1 : player2);
				hp.getBoard().set(4, "  ");
				hp.getBoard().set(3, "Perfect Walls: §a" + leader.getPerfectWall());
				hp.getBoard().set(2, "Wall: §a" + leader.getWall());
			}
		} else {
			String player1 = p.getDisplayName().substring(0, 2) + p.getPlayer().getName() + "§r: " + p.getScore();
			String player2 = p.getOpponent().getDisplayName().substring(0, 2) + p.getOpponent().getPlayer().getName() + "§r: " + p.getOpponent().getScore();
			
			p.getBoard().set(8, "Play Time: §a" + str);
			p.getBoard().set(7, " ");
			p.getBoard().set(6, p.getScore() >= p.getOpponent().getScore() ? player1 : player2);
			p.getBoard().set(5, p.getScore() < p.getOpponent().getScore() ? player1 : player2);
			p.getBoard().set(4, "  ");
			p.getBoard().set(3, "Perfect Walls: §a" + p.getPerfectWall());
			p.getBoard().set(2, "Wall: §a" + p.getWall());
		}
	}
	
	public static void setDuelScoreboard(String stage, HPlayer p) {
		int minutes = 0 / 60;
		int seconds = (0) % 60;
		String str = String.format("%d:%02d", minutes, seconds);

		p.getBoard().remove(11);
		p.getBoard().set(10, "§a§m-----------------");
		p.getBoard().set(9, "Stage: §a" + stage);
		p.getBoard().set(8, "Play Time: §a" + str);
		p.getBoard().set(7, " ");
		p.getBoard().set(6, p.getDisplayName().substring(0, 2) + p.getPlayer().getName() + "§r: " + 0);
		p.getBoard().set(5, p.getOpponent().getDisplayName().substring(0, 2) + p.getOpponent().getPlayer().getName() + "§r: " + 0);
		p.getBoard().set(4, "  ");
		p.getBoard().set(3, "Perfect Walls: §a" + 0);
		p.getBoard().set(2, "Wall: §a" + 0);
		p.getBoard().set(1, "§a§m-----------------§r");
	}

	public static void updateCredit(Board board, HPlayer player) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		String formattedCredit = numberFormat.format(CreditImporter.getCredits(player));


		board.set(13, "Credit: §6§l" + formattedCredit);
	}

	public static void setDefaultScoreboard(Board board, HPlayer player) {
		int minutes = 0 / 60;
		int seconds = (0) % 60;
		String str = String.format("%d:%02d", minutes, seconds);


		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		String formattedCredit = numberFormat.format(CreditImporter.getCredits(player));

		board.set(14, "§a§m-----------------");
		board.set(13, "Credit: §6§l" + formattedCredit);
		board.set(12, "     ");
		board.set(11, "Stage: §anone");
		board.set(10, "Play Time: §a" + str);
		board.set(9, "  ");
		board.set(8, "Perfect Walls: §a" + 0);
		board.set(7, "Wall: §a" + 0);
		board.set(6, "Score: §a" + 0);
		board.set(5, "   ");
		board.set(4, "Missing block: §4" + 0);
		board.set(3, "Misplaced block: §4" + 0);
		board.set(2, "Choke: §4" + 0);
		board.set(1, "§a§m-----------------§r");
	}
}
