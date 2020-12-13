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
import utils.GetGamePlayer;
import utils.ScoreboardManager;
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
		final GamePlayer player = GetGamePlayer.getPlayerFromName(event.getPlayer().getName());
		
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
		
		player.setLeverPulled(true);
		player.getPlayer().getPlayer().getInventory().clear();
		player.setWalls(player.getWalls() + 1);
		setScore(player);
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
		if (score < 0) {
			score = 0;
		}
		player.setScore(player.getScore() + score);
		player.setMisplaced(player.getMisplaced() + misplaced);
		player.setMissing(player.getMissing() + missing);
		checkPerfectWall(player, missing, misplaced);
		ScoreboardManager.update(player);
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
		if (player.isInClassicGame()) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
	            @Override
	            public void run(){
	            	if (player.isInGame()) {
	            		player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 64, (short)(player.getGlassColor())));
	            	}
            		WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
	            }
	        }, 15L);
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	if (!player.isInClassicGame()) {
            		player.getPlayer().getInventory().addItem(new ItemStack(Material.STAINED_GLASS, 64, (short)(player.getGlassColor())));
            		WallsManager.clearPlayField(Values.games.get(player.getGameID()).getPlay(), Values.games.get(player.getGameID()).getWall());
            	}
            	player.setLeverPulled(false);
            }
        }, (long)(20 * player.getLeverDelay()));
	}
	
	/**
	 * Check if a wall is perfectly filled or not
	 * play a sound of correct and play a different sound if not
	 * @param player the GamePlayer who triggered the event
	 * @param missing number of hole remaining in the wall
	 * @param misplaced number of block placer in wrong location
	 * @author Blackoutburst
	 */
	private void checkPerfectWall(GamePlayer player, int missing, int misplaced) {
		if (missing == 0 && misplaced == 0) {
			player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
			player.setPerfectwalls(player.getPerfectwalls() + 1);
		} else {
			player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.NOTE_BASS_GUITAR, 1f, 1f);
		}
	}
}
