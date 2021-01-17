package event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import game.WallsManager;
import main.GamePlayer;
import main.Main;
import utils.ScoreboardManager;
import utils.TitleManager;
import utils.Tools;
import utils.Values;

/**
 * Manager block interaction event
 * mainly lever event
 * @author Blackoutburst
 */
public class PlayerInteract {
	
	/**
	 * When a a player flick a lever
	 * @param event interaction event
	 * @author Blackoutburst
	 */
	public void hitLever(PlayerInteractEvent event) {
		final GamePlayer player = Tools.getPlayerFromName(event.getPlayer().getName());
		
		if (player.isLeverPulled() || !player.isInGame()) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.LEVER) {
				pullWall(player);
			}
		}
	}
	
	/**
	 * Pull the current wall
	 * and call sub function to manage player score
	 * and manage the next wall
	 * @param player the GamePlayer who triggered the event
	 * @author Blackoutburst
	 */
	public void pullWall(GamePlayer player) {
		if (player.isInBlindGame()) {
			Values.games.get(player.getGameID()).setTimeFreeze(true);
		}
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				p.setLeverPulled(true);
				resetLever(p);
				p.getPlayer().getPlayer().getInventory().clear();
				p.setWalls(p.getWalls() + 1);
			}
			setScore(player.getCoop().getPlayers().get(0));
		} else {
			player.setLeverPulled(true);
			resetLever(player);
			player.getPlayer().getPlayer().getInventory().clear();
			player.setWalls(player.getWalls() + 1);
			setScore(player);
		}
		if (player.isInTourney()) {
			if (player.getStage().equals("Qualification")) {
				WallsManager.copyWall(player, Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getPlay(), new int[] {-617 - player.getWalls(), 7, 639, -617 - player.getWalls(), 10, 633});
				delayedAction(player);
			} else {
				WallsManager.copyWall(player, Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getPlay(), new int[] {-717 - player.getWalls(), 7, 639, -717 - player.getWalls(), 11, 629});
				delayedAction(player);
			}
		} else {
			genNextWall(player);
		}
	}
	
	/**
	 * Set player score based on missing / misplaced and correct block
	 * @param player the GamePlayer who triggered the event
	 * @author Blackoutburst
	 */
	private void setScore(GamePlayer player) {
		int misplaced = 0;
		int missing = 0;
		int score = 0;
		
		misplaced = WallsManager.pullWall(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
		missing = WallsManager.checkHole(Values.games.get(player.getGameID()).getPlay());
		score = WallsManager.checkScore(Values.games.get(player.getGameID()).getPlay()) - misplaced;
		player.setMisplaced(player.getMisplaced() + misplaced);
		player.setMissing(player.getMissing() + missing);
		
		if (player.isInCoop()) {
			for (GamePlayer p : player.getCoop().getPlayers()) {
				checkPerfectWall(p, missing, misplaced, score);
				ScoreboardManager.update(p);
			}
		} else {
			checkPerfectWall(player, missing, misplaced, score);
			ScoreboardManager.update(player);
		}
	}
	
	/**
	 * Generate the next wall using current player stage and classic mode
	 * @param player the GamePlayer who triggered the event
	 * @author Blackoutburst
	 */
	private void genNextWall(GamePlayer player) {
		WallsManager.genWall(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall(), Values.games.get(player.getGameID()).getHoles(), player);
		delayedAction(player);
	}
	
	/**
	 * Delay action for play field clear and lever input delay
	 * @param player the GamePlayer who triggered the event
	 * @author Blackoutburst
	 */
	private void delayedAction(GamePlayer player) {
		long delay = (long)(20 * player.getLeverDelay());
		
		if (player.isInClassicGame()) {
			delay = 10L;
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	if (player.isInGame()) {
            		if (!player.isInBlindGame()) {
            			if (player.isInCoop()) {
            				for (GamePlayer p : player.getCoop().getPlayers()) {
            					p.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(p.getGlassColor())));
            				}
            			} else {
            				player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 5, (short)(player.getGlassColor())));
            			}
            		} else {
            			hideWall(player);
            		}
            	}
            	WallsManager.clearHider(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
        		WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
            }
        }, delay);
	}
	
	/**
	 * Make lever usable again
	 * @param player
	 * @author Blackoutburst
	 */
	public static void resetLever(GamePlayer player) {
		long delay = (long)(20 * player.getLeverDelay());
		
		if (player.isInClassicGame()) {
			delay = 30L;
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	player.setLeverPulled(false);
            }
        }, delay);
	}
	
	/**
	 * Hide the wall and give block to the player with some delay
	 * @param player
	 * @author Blackoutburst
	 */
	private static void hideWall(GamePlayer player) {
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
	
	/**
	 * Check if a wall is perfectly filled or not
	 * play a sound of correct and play a different sound if not
	 * @param player the GamePlayer who triggered the event
	 * @param missing number of hole remaining in the wall
	 * @param misplaced number of block placer in wrong location
	 * @author Blackoutburst
	 */
	private void checkPerfectWall(GamePlayer player, int missing, int misplaced, int score) {
		if (missing == 0 && misplaced == 0) {
			if (player.showTitle()) {
				new TitleManager().send(player.getPlayer(), "§aPerfect", "", 0, 10, 10);
			}
			player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
			player.setPerfectwalls(player.getPerfectwalls() + 1);
			player.getPlayer().sendMessage("§aPerfect wall §e+"+score+" points");
		} else {
			score -= 1;
			if (score < 0) {
				score = 0;
			}
			player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.NOTE_BASS_GUITAR, 1f, 1f);
			player.getPlayer().sendMessage("§c"+missing+" missing block | "+misplaced+" misplaced block §e+"+score+" points");
		}
		if (score < 0) {
			score = 0;
		}
		player.setScore(player.getScore() + score);
	}
}
