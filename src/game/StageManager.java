package game;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
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
	public static void pullLastWallTime(GamePlayer player) {
		PlayerInteract interaction = new PlayerInteract();
		player.setInGame(false);
		interaction.pullWall(player);
		ScoreboardManager.update(player);
	}
	
	/**
	 * Set player current stage
	 * @param player player starting the game
	 * @author Blackoutburst
	 */
	public static void setStage(GamePlayer player) {
		player.setGameID(InsideArea.inGameAreaID(player.getPlayer().getLocation(), Values.games));
		if (player.getGameID() != -1) {
			player.setStage(Values.games.get(player.getGameID()).getType());
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
		int index = 0;
		for (String arg : args) {
			if (arg.equals("classic")) {
				player.setInClassicGame(true);
				player.setTime(Values.games.get(player.getGameID()).getStageTime());
			}
			if (arg.equals("blind")) {
				player.setInBlindGame(true);
			}
			if (arg.equals("easy")) {
				player.setEasyMode(true);
			}
			if (arg.equals("old")) {
				player.setOldAnimation(true);
			}
			if (arg.equals("time")) {
				try {
					if (Integer.valueOf(args[index+1]) < 0) {
						player.getPlayer().sendMessage("§cValue can not be negative!");
						return false;
					}
					player.setInClassicGame(true);
					player.setTime(Integer.valueOf(args[index+1]));
				} catch(Exception e) {
					player.getPlayer().sendMessage("§cInvalid number!");
					return false;
				}
			}
			if (arg.equals("score")) {
				try {
					if (Integer.valueOf(args[index+1]) < 0) {
						player.getPlayer().sendMessage("§cValue can not be negative!");
						return false;
					}
					player.setInClassicGame(true);
					player.setGoalScore(Integer.valueOf(args[index+1]));
					player.setTime(0);
				} catch(Exception e) {
					player.getPlayer().sendMessage("§cInvalid number!");
					return false;
				}
			}
			index ++;
		}
		return true;
	}
	
	/**
	 * Set stage data like time and lever delay
	 * Define id the game is classic or not
	 * @param player player starting a game
	 * @author Blackoutburst
	 */
	public static void setStageData(GamePlayer player) {
		Values.games.get(player.getGameID()).setRunning(true);
		if (player.isInClassicGame()) {
			player.setLeverDelay(1.5f);
		} else {
			player.setTime(0);
		}
	}
	
	/**
	 * Reset player game data before starting the game
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	public static void resetPlayerStats(GamePlayer player) {
		if (player == null) {return;}
		player.getPlayer().getPlayer().getInventory().clear();
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
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				p.setInGame(true);
				p.setLeverPulled(true);
				PlayerInteract.resetLever(p);
			}
		} else {
			player.setInGame(true);
			player.setWalls(1);
			player.setLeverPulled(true);
			PlayerInteract.resetLever(player);
		}
		
		WallsManager.clearHider(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
		WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
		WallsManager.genWall(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getHoles(), player);
		
		if (!player.isInBlindGame()) {
			if (player.isInCoop()) {
				for (GamePlayer p : player.getCoop().getPlayers()) {
					p.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(p.getGlassColor())));
				}
			} else {
				player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(player.getGlassColor())));
			}
		} else {
			Values.games.get(player.getGameID()).setTimeFreeze(true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
	            @Override
	            public void run(){
	            	if (player.isInGame()) {
	            		if (player.isInCoop()) {
	        				for (GamePlayer p : player.getCoop().getPlayers()) {
	        					p.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(p.getGlassColor())));
	        				}
	        			} else {
	        				player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(player.getGlassColor())));
	        			}
	            		Values.games.get(player.getGameID()).setTimeFreeze(false);
	            		WallsManager.hideWall(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall(), player);
	            	}
	            }
			}, (long) (20L * player.getMemtime()));
		}
	}
	
	/**
	 * Update the game every seconds
	 * @param player player starting a new game
	 * @author Blackoutburst
	 */
	private static void updateGame(GamePlayer player) {
		if (!Values.games.get(player.getGameID()).isTimeFreeze()) {
			if (player.isInClassicGame() && player.getGoalScore() == Integer.MAX_VALUE) {
				player.setTime(player.getTime() - 1);
			} else {
				player.setTime(player.getTime() + 1);
			}
		}
		
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				ScoreboardManager.update(p);
			}
		} else {
			ScoreboardManager.update(player);
		}
		if (!InsideArea.inGameArea(player.getPlayer().getLocation(), Values.games)) {
			autostop(player);
		}
		if (player.getScore() >= player.getGoalScore()) {
			autostop(player);
		}
		if (player.isInClassicGame() && player.getGoalScore() == Integer.MAX_VALUE) {
			if (player.getTime() <= 0) {
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

		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				Tools.showCountDown(p, 3);
			}
		} else {
			Tools.showCountDown(player, 3);
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	setupGame(player);
				Values.games.get(player.getGameID()).setClock(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run() {
						updateGame(player);
					}
				}, 0L,  20L));	
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
	private static boolean setGameType(GamePlayer player, String[] args) {
		if (args.length == 1) {
			if (args[0].toLowerCase().contains("classic")) {
				player.setInClassicGame(true);
				player.setTime(Values.games.get(player.getGameID()).getStageTime());
				return true;
			} else if (args[0].toLowerCase().contains("blind")) {
				player.setInBlindGame(true);
				return true;
			} else if (args[0].toLowerCase().contains("easy")) {
				player.setEasyMode(true);
				return true;
			} else if (args[0].toLowerCase().contains("old")) {
				player.setOldAnimation(true);
				return true;
			} else {
				player.getPlayer().sendMessage("§cInvalid parameter: try /play {classic|blind|easy|old}");
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

		if (player.isInCoop() && !player.isCoopLeader()) {player.getPlayer().sendMessage("§cYou need to be the co-op leader to use this command");return;}
		setStage(player);
		if (player.getGameID() == -1) {player.setStage("none");player.getPlayer().sendMessage("§cPlease enter a game area before running this command!");return;}
		if (player.isInGame()) {player.getPlayer().sendMessage("§cYou can not start a game while you are in game!");return;}
		if (Values.games.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game already started!");return;}
		player.setGoalScore(Integer.MAX_VALUE);
		correctArgs = setLimit(player, args);
		if (!correctArgs) {return;}
		correctArgs = setGameType(player, args);
		if (!correctArgs) {return;}
		setStageData(player);
		resetPlayerStats(player);
		
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				setLimit(p, args);
				setGameType(p, args);
				setStageData(p);
				resetPlayerStats(p);
				p.setLeverDelay(player.getLeverDelay());
				p.setMemtime(player.getMemtime());
				p.setStage(player.getStage());
				p.setGameID(player.getGameID());
			}
		}
		startGame(player);
	}
	
	/**
	 * Clean wall and play field
	 * Clean game data
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	private static void cleanGame(GamePlayer player) {
		Bukkit.getScheduler().cancelTask(Values.games.get(player.getGameID()).getClock());
		WallsManager.resetWall(Values.games.get(player.getGameID()).getWall());
		WallsManager.clearHider(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
		WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
		Values.games.get(player.getGameID()).setRunning(false);
		Values.games.get(player.getGameID()).setTimeFreeze(false);
		
	}
	
	/**
	 * Restore player status
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	private static void resetPlayerData(GamePlayer player) {
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(Tools.getPlayerFolder(player.getPlayer())+"/config.yml"));
		
		player.setInBlindGame(false);
		player.setInClassicGame(false);
		player.setEasyMode(false);
		player.setOldAnimation(false);
		player.setInGame(false);
		player.setStage("none");
		player.getPlayer().getInventory().clear();
		ScoreboardManager.update(player);
		player.setLeverDelay((float) playerData.getDouble("delay"));
		player.setMemtime((float) playerData.getDouble("memtime"));
	}
	
	/**
	 * Call everything needed to stop a game
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	public static void stop(GamePlayer player) {
		if (player.isInCoop() && !player.isCoopLeader()) {player.getPlayer().sendMessage("§cYou need to be the co-op leader to use this command");return;}
		if (!player.isInGame()) {player.getPlayer().sendMessage("§cYou can not stop a game if you are not inside!");return;}
		if (!Values.games.get(player.getGameID()).isRunning()) {player.getPlayer().sendMessage("§cThis game is not running!");return;}
		
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				cleanGame(p);
				resetPlayerData(p);
			}
		} else {
			cleanGame(player);
			resetPlayerData(player);
		}
	}
	
	/**
	 * Automatically stop a game when the player leave
	 * or the time run out
	 * @param player player stopping a game
	 * @author Blackoutburst
	 */
	public static void autostop(GamePlayer player) {
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				cleanGame(p);
				resetPlayerData(p);
			}
		} else {
			cleanGame(player);
			resetPlayerData(player);
		}
	}
}
