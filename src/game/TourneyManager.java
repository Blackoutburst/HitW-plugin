package game;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.GamePlayer;
import main.Main;
import utils.ScoreboardManager;

/**
 * Manage every tournament function
 * @author Blackoutburst
 */
public class TourneyManager {
	
	/**
	 * Toggle tournament spectator mode
	 * @param player command sender
	 * @param args command argument
	 * @author Blackoutburst
	 */
	public static void spectate (GamePlayer player, String[] args) {
		if (player.isInTourney() && !player.getTourneyRole().equals("spectator")) {
			player.getPlayer().sendMessage("§cYou are already participating in this tournament!");
			return;
		}
		if (!player.isInTourney()) {
			player.setInTourney(true);
			player.setTourneyRole("spectator");
			if (Main.tourneyStage.equals("Qualification")) {
				player.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -641.5f, 8, 665.5f, 90, 0));
			} else if (Main.tourneyStage.equals("Finals")) {
				player.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -699.5f, 8, 665.5f, 90, 0));
			} else {
				player.getPlayer().sendMessage("§aNo tournament is running right now, you will be teleported when the tournament start");
			}
			return;
		}
		if (player.isInTourney() && player.getTourneyRole().equals("spectator")) {
			player.setInTourney(false);
			player.setTourneyRole("none");
			player.getPlayer().sendMessage("§aYou will no longer follow the tournament §b/spawn if you are in the tournament arena");
			return;
		}
		
	}
	
	/**
	 * Redirect to the correct function
	 * depending on command arguments
	 * @param player command sender
	 * @param args command arguments
	 * @return true
	 * @author Blackoutburst
	 */
	public static boolean manageOption(GamePlayer player, String[] args) {
		if (args.length == 0) {
			player.getPlayer().sendMessage("§cMissing argument after /tourney try /tourney {manager | player1 | player2 | cam1 | cam2 | stage | end}");
			return true;
		}
		switch(args[0].toLowerCase()) {
			case "manager" :  setRole(player, args); return true;
			case "player1" :  setRole(player, args); return true;
			case "player2" :  setRole(player, args); return true;
			case "cam1" :  setRole(player, args); return true;
			case "cam2" :  setRole(player, args); return true;
			case "list" : showPlayerList(player, args); return true;
			case "end" : endTourney(); return true;
			case "stage" : switchStage(player, args); return true;
			case "start" : startStage(player, args); return true;
			default :  player.getPlayer().sendMessage("§cInvalid argument : "+args[0]+" !"); return true;
		}
	}
	
	/**
	 * Start the tournament stage
	 * @param player commands sender
	 * @param args commands arguments
	 * @author Blackoutburst
	 */
	private static void startStage(GamePlayer player, String[] args) {
		//Later
	}
	
	/**
	 * Generate every wall before the game
	 * @param player command sender
	 * @param stage current stage
	 * @author Blackoutburst
	 */
	private static void genWalls(GamePlayer player, String stage) {
		if (stage.equals("Qualification")) {
			int[] playField = new int[] {-617, 7, 639, -617, 10, 633};
			int[] Wall = new int[] {-617, 7, 639, -617, 10, 633};
			for (int i = 1; i < 100; i++) {
				Wall[0] --;
				Wall[3] --;
				WallsManager.genWall(playField, Wall, getHoleCount(stage,i), player);
			}
		} else {
			int[] playField = new int[] {-717, 7, 639, -717, 11, 629};
			int[] Wall = new int[] {-717, 7, 639, -717, 11, 629};
			for (int i = 1; i < 100; i++) {
				Wall[0] --;
				Wall[3] --;
				WallsManager.genWall(playField, Wall, getHoleCount(stage,i), player);
			}
		}
	}
	
	/**
	 * Get next wall hole counts based on player current wall
	 * @param player the GamePlayer who triggered the event
	 * @return hole count for the next wall
	 * @author Blackoutburst
	 */
	private static int getHoleCount(String stage, int wall) {
		if (stage.equals("Qualification")) {
			switch (wall) {
				case 1 : return (4);
				case 2 : return (4);
				case 3 : return (5);
				case 4 : return (5);
				case 5 : return (6);
				case 6 : return (6);
				case 7 : return (7);
				default : return (8);
			} 
		} else if (stage.equals("Finals")) {
			switch (wall) {
				case 1 : return (11);
				case 2 : return (12);
				case 3 : return (14);
				case 4 : return (15);
				case 5 : return (16);
				case 6 : return (18);
				case 7 : return (20);
				default : return (22);
			} 
		}
		return (0);
	}
	
	/**
	 * Switch tournament stage and generate games wall
	 * Broadcast everyone the tournament stage
	 * @param player command sender
	 * @param args command arguments
	 * @author Blackoutburst
	 */
	private static void switchStage(GamePlayer player, String[] args) {
		if (args.length < 2) {
			player.getPlayer().sendMessage("§cMissing parameter try /tourney stage {Q | F}");
			return;
		}
		if (args[1].toLowerCase().equals("q")) {
			Main.tourneyStage = "Qualification";
			Main.stageTime = 120;
			genWalls(player, "Qualification");
			for (GamePlayer p : Main.players) {
				if (p.isInTourney()) {
					p.getBoard().setTitle("§6§lTournament");
					ScoreboardManager.update(p);
					p.getPlayer().sendMessage("§6§lWelcome to the Qualification stage!");
				}
				if (p.isInTourney() && p.getTourneyRole().equals("spectator")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -641.5f, 8, 665.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("manager")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -641.5f, 8, 665.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("player1")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -652.5f, 7, 673.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("player2")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -652.5f, 7, 657.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("cam1")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -650.5f, 9, 673.5f, 90, 15));}
				if (p.isInTourney() && p.getTourneyRole().equals("cam2")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -650.5f, 9, 657.5f, 90, 15));}
			}
			
		} else if (args[1].toLowerCase().equals("f")) {
			Main.tourneyStage = "Finals";
			Main.stageTime = 120;
			genWalls(player, "Finals");
			for (GamePlayer p : Main.players) {
				if (p.isInTourney()) {
					p.getBoard().setTitle("§6§lTournament");
					ScoreboardManager.update(p);
					p.getPlayer().sendMessage("§6§lWelcome to the Finals stage!");
				}
				if (p.isInTourney() && p.getTourneyRole().equals("spectator")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -699.5f, 8, 665.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("manager")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -699.5f, 8, 665.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("player1")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -712.5f, 7, 675.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("player2")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -712.5f, 7, 655.5f, 90, 0));}
				if (p.isInTourney() && p.getTourneyRole().equals("cam1")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -708.5f, 9, 675.5f, 90, 15));}
				if (p.isInTourney() && p.getTourneyRole().equals("cam2")) {p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -708.5f, 9, 655.5f, 90, 15));}
			}
		} else {
			player.getPlayer().sendMessage("§cInvalid stage: "+args[1]+" try /tourney stage {Q | F}");
		}
	}
	
	/**
	 * End the tournament
	 * Reset every player status
	 * @author Blackoutburst
	 */
	private static void endTourney() {
		Main.InTourney = false;
		Main.tourneyStage = "none";
		Main.stageTime = 0;
		Main.player1 = null;
		Main.player2 = null;
		for (GamePlayer p : Main.players) {
			if (p.isInTourney()) {
				p.getBoard().setTitle(p.getPlayer().getName());
				p.getPlayer().sendMessage("§6§lTournament ended!");
		    	p.getPlayer().teleport(new Location(Bukkit.getWorld("World"), -574.5f, 7, 665.5f, -90, 0));
			}
			p.setInTourney(false);
			p.setTourneyRole("none");
			ScoreboardManager.update(p);
		}
	}
	
	/**
	 * Show every people participating in the tournament
	 * @param player player with tournament roles
	 * @param args command arguments
	 * @author Blackoutburst
	 */
	private static void showPlayerList(GamePlayer player, String[] args) {
		for (GamePlayer p : Main.players) {
			if (p.isInTourney() && !p.getTourneyRole().equals("spectator")) {
				player.getPlayer().sendMessage(p.getTourneyRole()+" | "+p.getPlayer().getName());
			}
		}
		
	}
	
	/**
	 * Set a player tournament role
	 * @param player player who get the role
	 * @param args command arguments
	 * @author Blackoutburst
	 */
	private static void setRole(GamePlayer player, String[] args) {
		if (args.length < 2) {
			player.getPlayer().sendMessage("§cMissing parameter try /tourney {manager | player1 | player2 | cam1 | cam2} {add | remove} [Player]");
			return;
		}
		if (args[1].toLowerCase().equals("add")) {
			for (GamePlayer p : Main.players) {
				if (p.getPlayer().getName().toLowerCase().contains(args[2].toLowerCase())) {
					p.setInTourney(true);
					p.setTourneyRole(args[0]);
					p.getPlayer().sendMessage("§aYou were assigned to "+args[0]+ " by "+player.getPlayer().getDisplayName());
					player.getPlayer().sendMessage("§a"+args[2]+" is now a Tourney "+args[0]);
					if (args[0].equals("player1")) {Main.player1 = p;}
					if (args[0].equals("player2")) {Main.player2 = p;}
					return;
				}
			}
			player.getPlayer().sendMessage("§cUnknow player: "+args[2]+" !");
		} else if (args[1].toLowerCase().equals("remove")) {
			for (GamePlayer p : Main.players) {
				if (p.getPlayer().getName().toLowerCase().contains(args[2].toLowerCase())) {
					p.setInTourney(false);
					p.setTourneyRole("none");
					p.getPlayer().sendMessage("§aYou were unassigned from "+args[0]+ " by "+player.getPlayer().getDisplayName());
					player.getPlayer().sendMessage("§a"+args[2]+" is no longer a Tourney "+args[0]);
					if (args[0].equals("player1")) {Main.player1 = null;}
					if (args[0].equals("player2")) {Main.player2 = null;}
					return;
				}
			}
			player.getPlayer().sendMessage("§cUnknow player: "+args[2]+" !");
		} else {
			player.getPlayer().sendMessage("§cUnknow parameter : "+args[1]+" try /tourney {manager | player1 | player2 | cam1 | cam2} {add | remove} [Player]");
		}
	}
}
