package event;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;

import main.GamePlayer;
import main.Main;
import utils.InsideArea;
import utils.Tools;
import utils.Values;

/**
 * Manage every action when the event onBlockDamage event is called
 * @author Blackoutburst
 */
public class BlockDamage {
	
	/**
	 * This function create the brushing mechanic
	 * Brushing make the player able to instantly break
	 * the block he is looking at if he is holding the left mouse button
	 * @param event Block damage event
	 * @author Blackoutburst
	 */
	public void brushing(BlockDamageEvent event) {
		GamePlayer player = Tools.getPlayerFromName(event.getPlayer().getName());
		
		if (player.isInGame() && InsideArea.inPlayArea(event.getBlock().getLocation(), Values.games)) {
			if (event.getBlock().getType().equals(Material.STAINED_GLASS)) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
		            @Override
		            public void run(){
	            		event.getBlock().setType(Material.AIR);
	            		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.GLASS, 1f, 1f);
		            }
		        }, (long)((player.getBrushLag() * 20) / 1000));
			}
		}
	}
	
	/**
	 * Change player color upon hitting
	 * the block with the color he want
	 * @param event Block damage event
	 * @author Blackoutburst
	 */
	@SuppressWarnings("deprecation")
	public void selectColor(BlockDamageEvent event) {
		GamePlayer player = Tools.getPlayerFromName(event.getPlayer().getName());
		String type = "none";
		int data = 0;
		
		if (!InsideArea.playerInside(player.getPlayer().getLocation(), Values.colors)) {return;}
		if (event.getBlock().getType() == Material.STAINED_GLASS) {
			type = "Glass";
			player.setGlassColor(event.getBlock().getData());
		} else if (event.getBlock().getType() == Material.STAINED_CLAY) {
			type = "Wall";
			player.setWallColor(event.getBlock().getData());
		}
		Tools.writePlayerData(new File(Tools.getPlayerFolder(player.getPlayer())), 
				player.getWallColor(), player.getGlassColor(), 
				player.getLeverDelay(), player.showTitle(), player.getMemtime(), player.getBrushLag());
		data = event.getBlock().getData();
		displayColorMessage(data, player.getPlayer(), type);
	}
	
	/**
	 * Send a message to the player who changed his color
	 * to confirm the color change was a success
	 * @param data event block data
	 * @param player player who triggered the event
	 * @param type Wall or Glass depend on the block type
	 * @author Blackoutburst
	 */
	private void displayColorMessage(int data, Player player, String type) {
		switch(data) {
			case 0: player.sendMessage("§aYou selected the White " + type); break;
			case 1: player.sendMessage("§aYou selected the Orange " + type); break;
			case 2: player.sendMessage("§aYou selected the Magenta " + type); break;
			case 3: player.sendMessage("§aYou selected the Light Blue " + type); break;
			case 4: player.sendMessage("§aYou selected the Yellow " + type); break;
			case 5: player.sendMessage("§aYou selected the Lime " + type); break;
			case 6: player.sendMessage("§aYou selected the Pink " + type); break;
			case 7: player.sendMessage("§aYou selected the Gray " + type); break;
			case 8: player.sendMessage("§aYou selected the Light Gray " + type); break;
			case 9: player.sendMessage("§aYou selected the Cyan " + type); break;
			case 10: player.sendMessage("§aYou selected the Purple " + type); break;
			case 11: player.sendMessage("§aYou selected the Blue " + type); break;
			case 12: player.sendMessage("§aYou selected the Brown " + type); break;
			case 13: player.sendMessage("§aYou selected the Green " + type); break;
			case 14: player.sendMessage("§aYou selected the Red " + type); break;
			case 15: player.sendMessage("§aYou selected the Black " + type); break;
			default: player.sendMessage("§cSomething went wrong!"); break;
		}
	}
}
