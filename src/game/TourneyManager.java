package game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import main.GamePlayer;
import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.ScoreboardManager;
import utils.Tools;
import utils.Values;

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
				player.getPlayer().teleport(Values.specQ);
			} else if (Main.tourneyStage.equals("Finals")) {
				player.getPlayer().teleport(Values.specF);
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
	 * Send final result message to every player in the tournament
	 * @author Blackoutburst
	 */
	private static void broadcastEndMessage() {
		for (GamePlayer p : Main.players) {
			if (p.isInTourney()) {
				if (Main.player1.getScore() > Main.player2.getScore()) {
					p.getPlayer().sendMessage("§6§l========================================");
					p.getPlayer().sendMessage("§6§l#1 :"+Main.player1.getPlayer().getDisplayName()+" §6§l: §r§a"+Main.player1.getScore());
					p.getPlayer().sendMessage("§l#2 : "+Main.player2.getPlayer().getDisplayName()+" §l: §r§a"+Main.player2.getScore());
					p.getPlayer().sendMessage("§6§l========================================");
				}
				if (Main.player1.getScore() < Main.player2.getScore()) {
					p.getPlayer().sendMessage("§6§l========================================");
					p.getPlayer().sendMessage("§6§l#1 :"+Main.player2.getPlayer().getDisplayName()+" §6§l: §r§a"+Main.player2.getScore());
					p.getPlayer().sendMessage("§l#2 : "+Main.player1.getPlayer().getDisplayName()+" §l: §r§a"+Main.player1.getScore());
					p.getPlayer().sendMessage("§6§l========================================");
				}
				if (Main.player1.getScore() == Main.player2.getScore()) {
					p.getPlayer().sendMessage("§6§l========================================");
					p.getPlayer().sendMessage("§lTie : "+Main.player1.getPlayer().getDisplayName()+" §l: §r§a"+Main.player1.getScore());
					p.getPlayer().sendMessage("§lTie : "+Main.player2.getPlayer().getDisplayName()+" §l: §r§a"+Main.player2.getScore());
					p.getPlayer().sendMessage("§6§l========================================");
				}
			}
		}
	}
	
	/**
	 * Start the tournament stage
	 * @param player commands sender
	 * @param args commands arguments
	 * @author Blackoutburst
	 */
	private static void startStage(GamePlayer player, String[] args) {
		StageManager.setStage(Main.player1);
		if (Main.player1.getGameID() == -1) {Main.player1.setStage("none");player.getPlayer().sendMessage("§cPlayer1 is missing start canceled!");return;}
		StageManager.setStage(Main.player2);
		if (Main.player2.getGameID() == -1) {Main.player2.setStage("none");player.getPlayer().sendMessage("§cPlayer2 is missing start canceled!");return;}
		Main.player1.setInClassicGame(true);
		Main.player2.setInClassicGame(true);
		Main.player1.setPerfectwalls(0);
		Main.player2.setPerfectwalls(0);
		StageManager.setStageData(Main.player1);
		StageManager.setStageData(Main.player2);
		for (GamePlayer p : Main.players) {
			if (p.isInTourney()) {
				Tools.showCountDown(p, 5);
			}
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	setupGame(Main.player1);
            	setupGame(Main.player2);
				Main.tourneyClock = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run() {
						Main.stageTime--;
						for (GamePlayer p : Main.players) {
							if (p.isInTourney()) {
								ScoreboardManager.update(p);
							}
						}
						if (Main.stageTime <= 0) {
							StageManager.pullLastWallTime(Main.player1);
							StageManager.pullLastWallTime(Main.player2);
							StageManager.autostop(Main.player1);
							StageManager.autostop(Main.player2);
							broadcastEndMessage();
							for (GamePlayer p : Main.players) {
								if (p.isInTourney()) {
									ScoreboardManager.update(p);
								}
							}
							Bukkit.getScheduler().cancelTask(Main.tourneyClock);
						}
					}
				}, 0L,  20L);	
            }
		}, 100L);
	}
	
	/**
	 * Setup the game
	 * clear play field and generate walls
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	private static void setupGame(GamePlayer player) {
		player.setInGame(true);
		player.setWalls(1);
    	player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(player.getGlassColor())));
    	WallsManager.clearHider(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
    	WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
    	if (player.getStage().equals("Qualification")) {
        	WallsManager.copyWall(player, Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getPlay(), new int[] {-617 - player.getWalls(), 7, 639, -617 - player.getWalls(), 10, 633});
    	} else if (player.getStage().equals("Finals")) {
    		WallsManager.copyWall(player, Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getPlay(), new int[] {-717 - player.getWalls(), 7, 639, -717 - player.getWalls(), 11, 629});
    		
    	}
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
				if (i == 2) {player.setWalls(2);player.setInClassicGame(true);} else {player.setWalls(0);player.setInClassicGame(false);}
				WallsManager.genWallTournament(playField, Wall, getHoleCount(stage,i), player);
			}
		} else {
			int[] playField = new int[] {-717, 7, 639, -717, 11, 629};
			int[] Wall = new int[] {-717, 7, 639, -717, 11, 629};
			for (int i = 1; i < 100; i++) {
				Wall[0] --;
				Wall[3] --;
				if (i == 2) {player.setWalls(2);player.setInClassicGame(true);} else {player.setWalls(0);player.setInClassicGame(false);}
				WallsManager.genWallTournament(playField, Wall, getHoleCount(stage,i), player);
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
	 * Broadcast clickable message for every player 
	 * not spectating nor affected to the tournament  
	 */
	public static void displaySpectateMessage() {
		TextComponent message = new TextComponent("§aA tournament is currently running do §b/spectate §aor click §bhere §ato §aspectate");
		
		message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spectate"));
		if (Main.player1 != null && Main.player2 != null) {
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Main.player1.getPlayer().getDisplayName()+" VS "+Main.player2.getPlayer().getDisplayName()).create()));
		}
		for (GamePlayer p : Main.players) {
			if (!p.isInTourney()) {
				p.getPlayer().spigot().sendMessage(message);
			}
		}
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
			displaySpectateMessage();
			StageManager.resetPlayerStats(Main.player1);
			StageManager.resetPlayerStats(Main.player2);
			for (GamePlayer p : Main.players) {
				if (p.isInTourney()) {
					p.getBoard().setTitle("§6§lTournament");
					ScoreboardManager.update(p);
					p.getPlayer().sendMessage("§6§lWelcome to the Qualification stage!");
				}
				if (p.isInTourney() && p.getTourneyRole().equals("spectator")) {p.getPlayer().teleport(Values.specQ);}
				if (p.isInTourney() && p.getTourneyRole().equals("manager")) {p.getPlayer().teleport(Values.specQ);}
				if (p.isInTourney() && p.getTourneyRole().equals("player1")) {p.getPlayer().teleport(Values.player1Q);}
				if (p.isInTourney() && p.getTourneyRole().equals("player2")) {p.getPlayer().teleport(Values.player2Q);}
				if (p.isInTourney() && p.getTourneyRole().equals("cam1")) {
					p.getPlayer().teleport(Values.cam1Q);
					p.getPlayer().setGameMode(GameMode.SPECTATOR);
				}
				if (p.isInTourney() && p.getTourneyRole().equals("cam2")) {
					p.getPlayer().teleport(Values.cam2Q);
					p.getPlayer().setGameMode(GameMode.SPECTATOR);
				}
			}
			
		} else if (args[1].toLowerCase().equals("f")) {
			Main.tourneyStage = "Finals";
			Main.stageTime = 120;
			genWalls(player, "Finals");
			displaySpectateMessage();
			for (GamePlayer p : Main.players) {
				if (p.isInTourney()) {
					p.getBoard().setTitle("§6§lTournament");
					ScoreboardManager.update(p);
					p.getPlayer().sendMessage("§6§lWelcome to the Finals stage!");
				}
				if (p.isInTourney() && p.getTourneyRole().equals("spectator")) {p.getPlayer().teleport(Values.specF);}
				if (p.isInTourney() && p.getTourneyRole().equals("manager")) {p.getPlayer().teleport(Values.specF);}
				if (p.isInTourney() && p.getTourneyRole().equals("player1")) {p.getPlayer().teleport(Values.player1F);}
				if (p.isInTourney() && p.getTourneyRole().equals("player2")) {p.getPlayer().teleport(Values.player2F);}
				if (p.isInTourney() && p.getTourneyRole().equals("cam1")) {
					p.getPlayer().teleport(Values.cam1F);
					p.getPlayer().setGameMode(GameMode.SPECTATOR);
				}
				if (p.isInTourney() && p.getTourneyRole().equals("cam2")) {
					p.getPlayer().teleport(Values.cam2Q);
					p.getPlayer().setGameMode(GameMode.SPECTATOR);
				}
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
		StageManager.resetPlayerStats(Main.player1);
		StageManager.resetPlayerStats(Main.player2);
		Main.player1 = null;
		Main.player2 = null;
		for (GamePlayer p : Main.players) {
			if (p.isInTourney()) {
				p.getBoard().setTitle(p.getPlayer().getName());
				p.getPlayer().sendMessage("§6§lTournament ended!");
		    	p.getPlayer().teleport(Values.spawn);
			}
			if (p.getTourneyRole().contains("cam")) {
				p.getPlayer().setGameMode(GameMode.SURVIVAL);
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
				player.getPlayer().sendMessage(p.getTourneyRole()+" | "+p.getPlayer().getDisplayName());
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
