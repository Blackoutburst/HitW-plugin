package menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import core.HPlayer;
import main.Main;

public class ColorMenu {

	public static void menu(HPlayer p, String name, Material mat) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + name);
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(mat, 1, (short) 0);
        inv.setItem(11, item);
        item = new ItemStack(mat, 1, (short) 1);
        inv.setItem(12, item);
        item = new ItemStack(mat, 1, (short) 2);
        inv.setItem(13, item);
        item = new ItemStack(mat, 1, (short) 3);
        inv.setItem(14, item);
        item = new ItemStack(mat, 1, (short) 4);
        inv.setItem(15, item);
        item = new ItemStack(mat, 1, (short) 5);
        inv.setItem(20, item);
        item = new ItemStack(mat, 1, (short) 6);
        inv.setItem(21, item);
        item = new ItemStack(mat, 1, (short) 7);
        inv.setItem(22, item);
        item = new ItemStack(mat, 1, (short) 8);
        inv.setItem(23, item);
        item = new ItemStack(mat, 1, (short) 9);
        inv.setItem(24, item);
        item = new ItemStack(mat, 1, (short) 10);
        inv.setItem(29, item);
        item = new ItemStack(mat, 1, (short) 11);
        inv.setItem(30, item);
        item = new ItemStack(mat, 1, (short) 12);
        inv.setItem(31, item);
        item = new ItemStack(mat, 1, (short) 13);
        inv.setItem(32, item);
        item = new ItemStack(mat, 1, (short) 14);
        inv.setItem(33, item);
        item = new ItemStack(mat, 1, (short) 15);
        inv.setItem(40, item);
        p.getPlayer().openInventory(inv);
	}
	
	public static void use(Inventory inv, int slot, HPlayer p) {
		short color = 0;
		
		switch (slot) {
			case 11: color = 0;break; 
			case 12: color = 1;break; 
			case 13: color = 2;break; 
			case 14: color = 3;break; 
			case 15: color = 4;break; 
			case 20: color = 5;break; 
			case 21: color = 6;break; 
			case 22: color = 7;break; 
			case 23: color = 8;break; 
			case 24: color = 9;break; 
			case 29: color = 10;break; 
			case 30: color = 11;break; 
			case 31: color = 12;break; 
			case 32: color = 13;break; 
			case 33: color = 14;break; 
			case 40: color = 15;break; 
			default: color = 0;break;
		}
		
		if (inv.getName().equals(ChatColor.BLACK + "Wall Color")) {
			p.setWallColor(color);
		} else if (inv.getName().equals(ChatColor.BLACK + "Glass Color")) {
			p.setGlassColor(color);
		}
		HPlayer.updatePlayerData(p);
		ConfigurationMenu.generate(p);
	}
}
