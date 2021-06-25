package core;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.Utils;

public class ConfigurationMenu {
	
	private static void colorMenu(HPlayer p, String name, Material mat) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + name);
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("�r");
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
	
	public static void useColor(Inventory inv, int slot, HPlayer p) {
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
			default: color = -1;
		}
		if (color == -1) {
			return;
		}
		
		if (inv.getName().equals(ChatColor.BLACK + "Wall Color")) {
			p.setWallColor(color);
		} else if (inv.getName().equals(ChatColor.BLACK + "Glass Color")) {
			p.setGlassColor(color);
		}
		HPlayer.updatePlayerData(p);
		ConfigurationMenu.generate(p);
	}
	
	public static void use(Inventory inv, int slot, HPlayer p) {
		switch (slot) {
			case 19: case 10:
				p.setOldAnimation(p.isOldAnimation() ? false : true);
				ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) (p.isOldAnimation() ? 10 : 8));
				ItemMeta meta = item.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
		        lore.add("�6Old walls animations");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(19, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 20: case 11:
				p.setRightSided(p.isRightSided() ? false : true);
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.isRightSided() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Right sided walls");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(20, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 21: case 12:
				p.setBlind(p.isBlind() ? false : true);
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.isBlind() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Blind mode");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(21, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 22: case 13:
				p.setDestroy(p.isDestroy() ? false : true);
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.isDestroy() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Break mode");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(22, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 23: case 14:
				p.setAutoLeave(p.isAutoLeave() ? false : true);
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.isAutoLeave() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Auto leave");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(23, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 24: case 15:
				p.setTitle(p.isTitle() ? false : true);
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.isTitle() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Title");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(24, item);
		        HPlayer.updatePlayerData(p);
		        break;		
			case 25: case 16:
				p.setFly(p.getPlayer().getAllowFlight() ? false : true);
				p.getPlayer().setAllowFlight(p.isFly());
				item = new ItemStack(Material.INK_SACK, 1, (short) (p.getPlayer().getAllowFlight() ? 10 : 8));
				meta = item.getItemMeta();
				lore = new ArrayList<String>();
		        lore.add("�6Fly");
		        meta.setLore(lore);
		        meta.setDisplayName("�r�aClick to Toggle");
		        item.setItemMeta(meta);
		        inv.setItem(25, item);
		        HPlayer.updatePlayerData(p);
		        break;
			case 37: 
				songMenu(p, "Song played");
				break;
			case 38: 
				colorMenu(p, "Glass Color", Material.STAINED_GLASS);
				break;
			case 39:
				colorMenu(p, "Wall Color", Material.STAINED_CLAY);
				break;
			case 40:
				TextComponent msg = new TextComponent(Utils.centerText("�6Use �b/lag �r[value]"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/lag "+p.getBrushLag()));
				p.getPlayer().closeInventory();
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				p.getPlayer().sendMessage(" ");
				p.getPlayer().spigot().sendMessage(msg);
				msg = new TextComponent(Utils.centerText("�e(Click me to copy the command)"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/lag "+p.getBrushLag()));
				p.getPlayer().spigot().sendMessage(msg);
				p.getPlayer().sendMessage(" ");
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				break;
			case 41:
				msg = new TextComponent(Utils.centerText("�6Use �b/memtime �r[value]"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/memtime "+p.getMemTime()));
				p.getPlayer().closeInventory();
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				p.getPlayer().sendMessage(" ");
				p.getPlayer().spigot().sendMessage(msg);
				msg = new TextComponent(Utils.centerText("�e(Click me to copy the command)"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/memtime "+p.getMemTime()));
				p.getPlayer().spigot().sendMessage(msg);
				p.getPlayer().sendMessage(" ");
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				break;
			case 42:
				msg = new TextComponent(Utils.centerText("�6Use �b/delay �r[value]"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/delay "+p.getLeverDelay()));
				p.getPlayer().closeInventory();
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				p.getPlayer().sendMessage(" ");
				p.getPlayer().spigot().sendMessage(msg);
				msg = new TextComponent(Utils.centerText("�e(Click me to copy the command)"));
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/delay "+p.getLeverDelay()));
				p.getPlayer().spigot().sendMessage(msg);
				p.getPlayer().sendMessage(" ");
				p.getPlayer().sendMessage("�a�l�m---------------------------------------------");
				break;
		}
	}
	
	public static void useSong(Inventory inv, int slot, HPlayer p) {
		String songName = "none";
		
		switch (slot) {
			case 11: songName = "none";break; 
			case 12: songName = "Hyperdron - Inter-Dimensional Existence Kontrol";break; 
			default: songName = null;
		}
		if (songName == null) {
			return;
		}
		
		p.setSongName(songName);
		HPlayer.updatePlayerData(p);
		ConfigurationMenu.generate(p);
	}
	
	private static void songMenu(HPlayer p, String name) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + name);
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("�r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.BARRIER, 1, (short) 0);
        meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("�bNo song will be played");
        meta.setLore(lore);
        meta.setDisplayName("�r�aNone");
        item.setItemMeta(meta);
        inv.setItem(11, item);
        p.getPlayer().openInventory(inv);
        
        item = new ItemStack(Material.GOLD_RECORD, 1, (short) 0);
        meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        lore = new ArrayList<String>();
        lore.add("�bInter-Dimensional Existence Kontrol");
        meta.setLore(lore);
        meta.setDisplayName("�r�aHyperdron");
        item.setItemMeta(meta);
        inv.setItem(12, item);
        p.getPlayer().openInventory(inv);
	}
	
	public static void generate(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + "Configuration Menu");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("�r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++)
        	inv.setItem(i, item);
		
		item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore = new ArrayList<String>();
        lore.add("�6Toggle old walls animations");
        lore.add("�6Like how Hitw used to look");
        skull.setLore(lore);
        skull.setDisplayName("�r�aOld walls animations");
        skull.setOwner("NamelessTeddy");
        item.setItemMeta(skull);
        inv.setItem(10, item);
        
		item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle right sided walls");
        lore.add("�6Every walls you play will be flipped");
        lore.add("");
        lore.add("�6Good for right to left player");
        skull.setLore(lore);
        skull.setDisplayName("�r�aRight sided walls");
        skull.setOwner("MasonMC");
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.WOOL, 1, (short) 15);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle Blind mode");
        lore.add("�6You only have a certain time");
        lore.add("�6to memorize the wall");
        lore.add("");
        lore.add("�6Then build it from memory");
        meta.setLore(lore);
        meta.setDisplayName("�r�aBlind mode");
        item.setItemMeta(meta);
        inv.setItem(12, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle Break mode");
        lore.add("�6Your play field is filled with glass");
        lore.add("�6You have to break block to match the wall pattern");
        lore.add("");
        skull.setLore(lore);
        skull.setDisplayName("�r�aBreak mode");
        skull.setOwner("jazmout");
        item.setItemMeta(skull);
        inv.setItem(13, item);
        
        item = new ItemStack(Material.WOOD_DOOR, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle Auto leave");
        lore.add("�6Automatically end the game");
        lore.add("�6when you step out of the play area");
        meta.setLore(lore);
        meta.setDisplayName("�r�aAuto leave");
        item.setItemMeta(meta);
        inv.setItem(14, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isOldAnimation() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Old walls animations");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(19, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isRightSided() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Right sided walls");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(20, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isBlind() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Blind mode");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(21, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isDestroy() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Break mode");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(22, item);
        
        item = new ItemStack(Material.SIGN, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle title");
        lore.add("�6such as perfect walls title");
        meta.setLore(lore);
        meta.setDisplayName("�r�aTitle");
        item.setItemMeta(meta);
        inv.setItem(15, item);

        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isAutoLeave() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Auto leave");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(23, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.isTitle() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Title");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(24, item);
        
        item = new ItemStack(Material.FIREWORK, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Toggle Fly");
        lore.add("�6Make you able to fly in survival");
        meta.setLore(lore);
        meta.setDisplayName("�r�aFly");
        item.setItemMeta(meta);
        inv.setItem(16, item);
        
        item = new ItemStack(Material.INK_SACK, 1, (short) (p.getPlayer().getAllowFlight() ? 10 : 8));
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Fly");
        meta.setLore(lore);
        meta.setDisplayName("�r�aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(25, item);
        
        item = new ItemStack(Material.JUKEBOX, 1, p.getGlassColor());
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change song");
        lore.add("");
        lore.add("�eCurrent song �7: �b"+p.getSongName());
        meta.setLore(lore);
        meta.setDisplayName("�r�aSong");
        item.setItemMeta(meta);
        inv.setItem(37, item);
        
        item = new ItemStack(Material.STAINED_GLASS, 1, p.getGlassColor());
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change the glass color");
        lore.add("�6used when you play");
        meta.setLore(lore);
        meta.setDisplayName("�r�aGlass color");
        item.setItemMeta(meta);
        inv.setItem(38, item);
        
        item = new ItemStack(Material.STAINED_CLAY, 1, p.getWallColor());
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change the wall color");
        lore.add("�6used when you play");
        meta.setLore(lore);
        meta.setDisplayName("�r�aWall color");
        item.setItemMeta(meta);
        inv.setItem(39, item);
        
        item = new ItemStack(Material.PAPER, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change brushing lag");
        lore.add("�6How long it take to break block");
        lore.add("�6after clicking them");
        lore.add("�3(current "+p.getBrushLag()+")");
        lore.add("�e(default 100.0)");
        meta.setLore(lore);
        meta.setDisplayName("�r�aBrushing lag");
        item.setItemMeta(meta);
        inv.setItem(40, item);
        
        item = new ItemStack(Material.EMPTY_MAP, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change memory time");
        lore.add("�6How long you can look at the wall");
        lore.add("�6in blind mode");
        lore.add("�3(current "+p.getMemTime()+")");
        lore.add("�e(default 1.0)");
        meta.setLore(lore);
        meta.setDisplayName("�r�aMemory time");
        item.setItemMeta(meta);
        inv.setItem(41, item);
        
        item = new ItemStack(Material.LEVER, 1);
        meta = item.getItemMeta();
        lore = new ArrayList<String>();
        lore.add("�6Click to change lever delay");
        lore.add("�6How long the lever take to reset");
        lore.add("�3(current "+p.getLeverDelay()+")");
        lore.add("�e(default 0.5)");
        meta.setLore(lore);
        meta.setDisplayName("�r�aLever delay");
        item.setItemMeta(meta);
        inv.setItem(42, item);
        
        p.getPlayer().openInventory(inv);
	}
}