package event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import main.GamePlayer;
import utils.GetGamePlayer;
import utils.InsideArea;
import utils.ScoreboardManager;
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
		GamePlayer player = GetGamePlayer.getPlayerFromName(event.getPlayer().getName());
		
		if (player.getPlayer().getWorld().getName().toLowerCase().equals("world")) {
			if (player.isInGame()) {
				if (!InsideArea.inPlayArea(event.getBlock().getLocation(), Values.games)) {
		    		event.getBlock().setType(Material.AIR);
		    		endlessBlock(event, player);
		    		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1f, 1f);
	    			player.setChoke(player.getChoke() + 1);
	    			ScoreboardManager.update(player);
				} else {
					endlessBlock(event, player);
				}
			} else if (!player.getPlayer().isOp()) {
				event.getBlock().setType(Material.AIR);
			}
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
        stack.setAmount(Math.min(stack.getType().getMaxStackSize(), 5));
        
        if (!player.getPlayer().getInventory().containsAtLeast(stack, stack.getAmount())) {
			player.getPlayer().getInventory().addItem(stack);
        }
	}
}
