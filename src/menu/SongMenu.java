package menu;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import core.HPlayer;
import main.Main;

public class SongMenu {

	public static void menu(HPlayer p, String name) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + name);
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.BARRIER, 1, (short) 0);
        meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§bNo song will be played");
        meta.setLore(lore);
        meta.setDisplayName("§r§aNone");
        item.setItemMeta(meta);
        inv.setItem(10, item);
        p.getPlayer().openInventory(inv);
        
        item = new ItemStack(Material.GOLD_RECORD, 1, (short) 0);
        meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        lore = new ArrayList<String>();
        lore.add("§bInter-Dimensional Existence Kontrol");
        meta.setLore(lore);
        meta.setDisplayName("§r§aHyperdron");
        item.setItemMeta(meta);
        inv.setItem(11, item);
        p.getPlayer().openInventory(inv);
        
        item = new ItemStack(Material.RECORD_12, 1, (short) 0);
        meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        lore = new ArrayList<String>();
        lore.add("§bVoting");
        meta.setLore(lore);
        meta.setDisplayName("§r§aShinkoNet");
        item.setItemMeta(meta);
        inv.setItem(12, item);
        p.getPlayer().openInventory(inv);
	}
	
	public static void use(Inventory inv, int slot, HPlayer p) {
		String songName = "none";
		
		switch (slot) {
			case 10: songName = "none"; break; 
			case 11: songName = "Hyperdron - Inter-Dimensional Existence Kontrol"; break; 
			case 12: songName = "ShinkoNet - Voting"; break; 
			default: songName = null; break;
		}
		
		if (songName == null) {
			return;
		}
		
		p.setSongName(songName);
		HPlayer.updatePlayerData(p);
		ConfigurationMenu.generate(p);
	}
}
