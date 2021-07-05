package event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import commands.CommandDuel;
import core.HPlayer;
import menu.ColorMenu;
import menu.ConfigurationMenu;
import menu.SongMenu;

public class InventoryClick {
	
	public void execute(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(ChatColor.BLACK + "Configuration Menu")) {
			ConfigurationMenu.use(event.getInventory(), event.getSlot(), HPlayer.getHPlayer((Player) event.getWhoClicked()));
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals(ChatColor.BLACK + "Duel Game")) {
			CommandDuel.duelGUIAction(event.getInventory(), event.getSlot(), HPlayer.getHPlayer((Player) event.getWhoClicked()));
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals(ChatColor.BLACK + "Wall Color")) {
			ColorMenu.use(event.getInventory(), event.getSlot(), HPlayer.getHPlayer((Player) event.getWhoClicked()));
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals(ChatColor.BLACK + "Glass Color")) {
			ColorMenu.use(event.getInventory(), event.getSlot(), HPlayer.getHPlayer((Player) event.getWhoClicked()));
			event.setCancelled(true);
		}
		if (event.getInventory().getName().equals(ChatColor.BLACK + "Song played")) {
			SongMenu.use(event.getInventory(), event.getSlot(), HPlayer.getHPlayer((Player) event.getWhoClicked()));
			event.setCancelled(true);
		}
	}
}
