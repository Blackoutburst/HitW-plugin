package game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import event.PlayerInteract;
import main.GamePlayer;
import main.Main;
import utils.InsideArea;
import utils.ScoreboardManager;
import utils.Tools;
import utils.Values;

/**
 * Manage stage states
 * @author Blackoutburst
 */
public class StageManager {
	
	/**
	 * Pull the last wall when the time is over
	 * @param player player playing
	 * @author Blackoutburst
	 */
	private static void pullLastWallTime(GamePlayer player) {
		PlayerInteract interaction = new PlayerInteract();
		interaction.pullWall(player);
	}
	
	/**
	 * Set player current stage
	 * @param player player starting the game
	 * @author Blackoutburst
	 */
	private static void setStage(GamePlayer player) {
		player.setGameID(InsideArea.inGameAreaID(player.getPlayer().getLocation(), Values.gamesQ));
		player.setStage("Finals");
		if (player.getGameID() == -1) {
			player.setGameID(InsideArea.inGameAreaID(player.getPlayer().getLocation(), Values.gamesF));
		} else {
			player.setStage("Qualification");
		}
	}
	
	/**
	 * Set player score or time limit
	 * if player choose this option
	 * @param player player starting a game
	 * @param args command arguments
	 * @return if the limit was a success
	 * @author Blackoutburst
	 */
	private static boolean setLimit(GamePlayer player, String[] args) {
		if (args.length >= 2) {
			if (args[0].equals("time".toLowerCase())) {
				try {
					player.setGoalTime(Integer.valueOf(args[1]));
				} catch(Exception e) {
					player.getPlayer().sendMessage("§cInvalid number!");
					return false;
				}
			}
			if (args[0].equals("score".toLowerCase())) {
				try {
					player.setGoalScore(Integer.valueOf(args[1]));
				} catch(Exception e) {
					player.getPlayer().sendMessage("§cInvalid number!");
					return false;
				}
			}
			if (!args[0].equals("score".toLowerCase()) && !args[0].equals("time".toLowerCase())) {
				player.getPlayer().sendMessage("§cInvalid parameter: try /play {score|time} [value]");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Set stage data like time and lever delay
	 * Define id the game is classic or not
	 * @param player player starting a game
	 * @author Blackoutburst
	 */
	private static void setStageData(GamePlayer player) {
		if (player.getStage().equals("Qualification")) {
			Values.gamesQ.get(player.getGameID()).setRunning(true);
		} else {
			Values.gamesF.get(player.getGameID()).setRunning(true);
		}
		if (player.isInClassicGame()) {
			player.setTime(120);
			player.setInClassicGame(true);
			player.setLeverDelay(1.5f);
		} else {
			player.setTime(0);
			player.setInClassicGame(false);
		}
	}
	
	/**
	 * Reset player game data before starting the game
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	private static void resetPlayerStats(GamePlayer player) {
		player.setWalls(0);
		player.setPerfectwalls(0);
		player.setMissing(0);
		player.setMisplaced(0);
		player.setChoke(0);
		player.setScore(0);
		ScoreboardManager.update(player);
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
    	player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 64, (short)(player.getGlassColor())));
    	if (player.getStage().equals("Qualification")) {
        	WallsManager.clearPlayField(Values.gamesQ.get(player.getGameID()).getPlay(), Values.gamesQ.get(player.getGameID()).getWall());
			if (player.isInClassicGame()) {
				WallsManager.genWall(Values.gamesQ.get(player.getGameID()).getPlay(), Values.gamesQ.get(player.getGameID()).getWall(), 3, player);
			} else {
				WallsManager.genWall(Values.gamesQ.get(player.getGameID()).getPlay(), Values.gamesQ.get(player.getGameID()).getWall(), 8, player);
			}
    	} else if (player.getStage().equals("Finals")) {
    		WallsManager.clearPlayField(Values.gamesF.get(player.getGameID()).getPlay(), Values.gamesF.get(player.getGameID()).getWall());
			if (player.isInClassicGame()) {
				WallsManager.genWall(Values.gamesF.get(player.getGameID()).getPlay(), Values.gamesF.get(player.getGameID()).getWall(), 10, player);
			} else {
				WallsManager.genWall(Values.gamesF.get(player.getGameID()).getPlay(), Values.gamesF.get(player.getGameID()).getWall(), 22, player);
			}
    	}
	}
	
	/**
	 * Update the game every seconds
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	private static void updateGame(GamePlayer player) {
		if (player.isInClassicGame()) {
			player.setTime(player.getTime() - 1);
		} else {
			player.setTime(player.getTime() + 1);
		}
		ScoreboardManager.update(player);
		if (!InsideArea.inGameArea(player.getPlayer().getLocation(), Values.gamesQ) && !InsideArea.inGameArea(player.getPlayer().getLocation(), Values.gamesF)) {
			autostop(player);
		}
		if (player.getScore() >= player.getGoalScore()) {
			autostop(player);
		}
		if (player.isInClassicGame()) {
			if (player.getTime() <= 0) {
				pullLastWallTime(player);
				autostop(player);
			}
		} else {
			if (player.getTime() >= player.getGoalTime()) {
				pullLastWallTime(player);
				autostop(player);
			}
		}
	}
	
	/**
	 * Start a game
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	private static void startGame(GamePlayer player) {
		player.getPlayer().sendMessage("§a The game will start in 3 seconds!");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	setupGame(player);
            	if (player.getStage().equals("Qualification")) {
					Values.gamesQ.get(player.getGameID()).setClock(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
						@Override
						public void run() {
							updateGame(player);
						}
					}, 0L,  20L));	
	            } else if (player.getStage().equals("Finals")) {
	            	Values.gamesF.get(player.getGameID()).setClock(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
						@Override
						public void run() {
							updateGame(player);
						}
					}, 0L,  20L));	
	            }
            }
		}, 60L);
	}
	
	/**
	 * Define is the game is in classic mode or not
	 * @param player player starting a new game
	 * @param args command arguments
	 * @return if this is a success or not
	 * @author Blackoutburst
	 */
	private static boolean setClassicGame(GamePlayer player, String[] args) {
		if (args.length == 1) {
			if (args[0].equals("classic".toLowerCase())) {
				player.setInClassicGame(true);
				return true;
			} else {
				player.getPlayer().sendMessage("§cInvalid parameter: try /play classic");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Call everything needed to start a new game
	 * @param player player starting a new game
	 * @param args command arguments
	 * @author Blackoutburst
	 */
	public static void start(GamePlayer player, String[] args) {
		boolean correctArgs = false;

		setStage(player);
		if (player.getGameID() == -1) {player.setStage("none");player.getPlayer().sendMessage("§cPlease enter a game area before running this command!");return;}
		if (player.isInGame()) {player.getPlayer().sendMessage("§cYou can not start a game while you are in game!");return;}
		
		if (player.getStage().equals("Qualification")) {
			if (Values.gamesQ.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game already started!");return;}
		} else {
			if (Values.gamesF.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game already started!");return;}
		}
		player.setGoalScore(Integer.MAX_VALUE);
		player.setGoalTime(Integer.MAX_VALUE);
		correctArgs = setLimit(player, args);
		if (!correctArgs) {return;}
		correctArgs = setClassicGame(player, args);
		if (!correctArgs) {return;}
		setStageData(player);
		resetPlayerStats(player);
		startGame(player);
	}
	
	/**
	 * Clean wall and play field
	 * Clean game data
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	private static void cleanGame(GamePlayer player) {
		if (player.getStage().equals("Qualification")) {
			Bukkit.getScheduler().cancelTask(Values.gamesQ.get(player.getGameID()).getClock());
			WallsManager.resetWall(Values.gamesQ.get(player.getGameID()).getWall());
			WallsManager.clearPlayField(Values.gamesQ.get(player.getGameID()).getPlay(), Values.gamesQ.get(player.getGameID()).getWall());
			Values.gamesQ.get(player.getGameID()).setRunning(false);
		} else {
			Bukkit.getScheduler().cancelTask(Values.gamesF.get(player.getGameID()).getClock());
			WallsManager.resetWall(Values.gamesF.get(player.getGameID()).getWall());
			WallsManager.clearPlayField(Values.gamesF.get(player.getGameID()).getPlay(), Values.gamesF.get(player.getGameID()).getWall());
			Values.gamesF.get(player.getGameID()).setRunning(false);
		}
	}
	
	/**
	 * Restore player status
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	private static void resetPlayerData(GamePlayer player) {
		player.setInClassicGame(false);
		player.setInGame(false);
		player.setStage("none");
		player.getPlayer().getInventory().clear();
		ScoreboardManager.update(player);
		player.setLeverDelay(Float.valueOf(Tools.readValue("player data/"+player.getPlayer().getUniqueId().toString().replace("-", "")+"/Lever")));
	}
	
	/**
	 * Call everything needed to stop a game
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	public static void stop(GamePlayer player) {
		if (!player.isInGame()) {player.getPlayer().sendMessage("§cYou can not stop a game if you are not inside!");return;}
		if (player.getStage().equals("Qualification")) {
			if (!Values.gamesQ.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game is not running!");return;}
		} else {
			if (!Values.gamesF.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game is not running!");return;}
		}
		cleanGame(player);
		resetPlayerData(player);
	}
	
	/**
	 * Automatically stop a game when the player leave
	 * or the time run out
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	public static void autostop(GamePlayer player) {
		cleanGame(player);
		resetPlayerData(player);
	}
}
