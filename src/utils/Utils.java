package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	
	public static final DecimalFormat ROUND = new DecimalFormat("0.00");
	
	public static int[] stringArrToIntArr(String[] arr) {
		int[] iarr = new int[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			iarr[i] = Integer.valueOf(arr[i]);
		}
		return iarr;
	}
	
	public static Float[] stringArrToFloatArr(String[] arr) {
		Float[] farr = new Float[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			farr[i] = Float.valueOf(arr[i]);
		}
		return farr;
	}
	
	public static String centerText(String text) {
		int maxWidth = 80;
		int spaces = (int) Math.round((maxWidth - 1.4 * ChatColor.stripColor(text).length()) / 2);
		
		return StringUtils.repeat(" ", spaces) + text;
	}
	
	public static void giveConfigItem(Player p) {
		ItemStack menu = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta menuMeta = menu.getItemMeta();
		
		menuMeta.setDisplayName("§aConfiguration Menu");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§6Show the configuration menu");
		menuMeta.setLore(lore);
		menu.setItemMeta(menuMeta);
		p.getInventory().setItem(8, menu);
	}
}
