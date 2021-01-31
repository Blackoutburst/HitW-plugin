package event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import main.GamePlayer;
import utils.InsideArea;
import utils.ScoreboardManager;
import utils.Tools;
import utils.Values;

/**
 * Manage every action when the onBlockPlace event is called
 * @author Blackoutburst
 */
public class BlockPlace {
	
	/**
	 * Removed every block placed outside the play area
	 * and increase choke counter is the player is in game
	 * operator player can still place block outside if they are not in game
	 * @param event Block place event
	 * @author Blackoutburst
	 */
	public void removeMisplacedBlock(BlockPlaceEvent event) {
		GamePlayer player = Tools.getPlayerFromName(event.getPlayer().getName());
		
		if (player.isInGame()) {
			endlessBlock(event, player);
			if (!InsideArea.inPlayArea(event.getBlock().getLocation(), Values.games)) {
	    		event.getBlock().setType(Material.AIR);
	    		endlessBlock(event, player);
	    		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1f, 1f);
    			player.setChoke(player.getChoke() + 1);
    			ScoreboardManager.update(player);
			}
		} else if (!player.getPlayer().isOp()) {
			event.getBlock().setType(Material.AIR);
		}
	}
	
	
	/**
	 * Make player have infinite amount of building block
	 * @param event 
	 * @param player
	 * @author Blackoutburst
	 */
	@SuppressWarnings("deprecation")
	private void endlessBlock(BlockPlaceEvent event, GamePlayer player) {
		ItemStack stack = new ItemStack(event.getBlock().getType(), 5, event.getBlock().getData());
        stack.setAmount(1);
        
        if (!player.getPlayer().getInventory().containsAtLeast(stack, 6)) {
			player.getPlayer().getInventory().addItem(stack);
        }
	}
}
