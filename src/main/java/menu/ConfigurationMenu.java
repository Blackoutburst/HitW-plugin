package menu;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import core.HPlayer;
import main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import reflect.CreditImporter;
import utils.SkullOwner;
import utils.Utils;

public class ConfigurationMenu {
	
	private static void toggle(boolean isEnabled, Inventory inv, HPlayer p, String lore, int slot) {
		ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) (isEnabled ? 10 : 8));
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> l = new ArrayList<String>();
		
        l.add(lore);
        meta.setLore(l);
        meta.setDisplayName("§r§aClick to Toggle");
        item.setItemMeta(meta);
        inv.setItem(slot, item);
        HPlayer.updatePlayerData(p);
	}
	
	public static void use(Inventory inv, int slot, HPlayer p) {
		switch (slot) {
			case 19: case 10:
				p.setOldAnimation(p.isOldAnimation() ? false : true);
				toggle(p.isOldAnimation(), inv, p, "§6Old walls animations", 19);
		        break;
			case 20: case 11:
				p.setRightSided(p.isRightSided() ? false : true);
				toggle(p.isRightSided(), inv, p, "§6Right sided walls", 20);
		        break;
			case 21: case 12:
				p.setBlind(p.isBlind() ? false : true);
				toggle(p.isBlind(), inv, p, "§6Blind mode", 21);
		        break;
			case 22: case 13:
				p.setDestroy(p.isDestroy() ? false : true);
				toggle(p.isDestroy(), inv, p, "§6Break mode", 22);
		        break;
			case 23: case 14:
				p.setAutoLeave(p.isAutoLeave() ? false : true);
				toggle(p.isAutoLeave(), inv, p, "§6Auto leave", 23);
		        break;
			case 24: case 15:
				p.setTitle(p.isTitle() ? false : true);
				toggle(p.isTitle(), inv, p, "§6Title", 24);
		        break;		
			case 25: case 16:
				p.getPlayer().setAllowFlight(p.getPlayer().getAllowFlight() ? false : true);
				p.setFly(p.getPlayer().getAllowFlight() ? false : true);
				toggle(p.getPlayer().getAllowFlight(), inv, p, "§6Fly", 25);
		        break;
			case 28: case 37:
				p.setPerfectOnly(p.isPerfectOnly() ? false : true);
				toggle(p.isPerfectOnly(), inv, p, "§6Perfect mode", 37);
		        break;
			case 29: case 38:
				p.setInvisibleGlass(p.isInvisibleGlass() ? false : true);
				toggle(p.isInvisibleGlass(), inv, p, "§6Invisible glass", 38);
		        break;
			case 46: 
				SongMenu.menu(p, "Song played");
				break;
			case 47: 
				ColorMenu.menu(p, "Glass Color", Material.STAINED_GLASS);
				break;
			case 48:
				ColorMenu.menu(p, "Wall Color", Material.STAINED_CLAY);
				break;
			case 49:
				commandMessage("lag", p.getBrushLag(), p.getPlayer());
				break;
			case 50:
				commandMessage("memtime", p.getMemTime(), p.getPlayer());
				break;
			case 51:
				commandMessage("delay", p.getLeverDelay(), p.getPlayer());
				break;
			default: return;
		}
	}
	
	private static void commandMessage(String commandName, float value, Player p) {
		TextComponent msg = new TextComponent(Utils.centerText("§6Use §b/" + commandName + " §r[value]"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + commandName + " " + value));
		
		p.closeInventory();
		p.sendMessage("§a§l§m---------------------------------------------");
		p.sendMessage(" ");
		p.spigot().sendMessage(msg);
		msg = new TextComponent(Utils.centerText("§e(Click me to copy the command)"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + commandName + " " + value));
		p.spigot().sendMessage(msg);
		p.sendMessage(" ");
		p.sendMessage("§a§l§m---------------------------------------------");
	}
	
	public static void generate(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, ChatColor.BLACK + "Configuration Menu");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++)
        	inv.setItem(i, item);
		
        ArrayList<String> lore = new ArrayList<String>();
        
        setFirstLine(lore, inv);
        setSecondLine(lore, inv, p);
        setThirdLine(lore, inv, p);
        setFourthLine(lore, inv, p);
        
        p.getPlayer().openInventory(inv);
	}
	
	private static void setThirdLine(ArrayList<String> lore, Inventory inv, HPlayer p) {
        lore.clear();
        lore.add("§6Toggle Perfect mode");
        lore.add("§6Only perfect wall give score");
        setItem(Material.GOLD_BLOCK, 1, (short) 0, "§r§aPerfect mode", lore, 28, inv);
        
		lore.clear();
        lore.add("§6Perfect mode");
        setItem(Material.INK_SACK, 1, (short) (p.isPerfectOnly() ? 10 : 8), "§r§aClick to Toggle", lore, 37, inv);
        
        
        lore.clear();
        lore.add("§6Toggle Invisible glass");
        setItem(Material.BARRIER, 1, (short) 0, "§r§aInvisible glass", lore, 29, inv);
        
		lore.clear();
        lore.add("§6Invisible glass");
        setItem(Material.INK_SACK, 1, (short) (p.isInvisibleGlass() ? 10 : 8), "§r§aClick to Toggle", lore, 38, inv);
	}
	
	private static void setFourthLine(ArrayList<String> lore, Inventory inv, HPlayer p) {
        lore.clear();
        lore.add("§6Click to change song");
        lore.add("");
        lore.add("§eCurrent song §7: §b"+p.getSongName());
        setItem(Material.JUKEBOX, 1, (short) 0, "§r§aSong", lore, 46, inv);
        
        lore.clear();
        lore.add("§6Click to change the glass color");
        lore.add("§6used when you play");
        setItem(Material.STAINED_GLASS, 1, p.getGlassColor(), "§r§aGlass color", lore, 47, inv);
        
        lore.clear();
        lore.add("§6Click to change the wall color");
        lore.add("§6used when you play");
        setItem(Material.STAINED_CLAY, 1, p.getWallColor(), "§r§aWall color", lore, 48, inv);
        
        lore.clear();
        lore.add("§6Click to change brushing lag");
        lore.add("§6How long it take to break block");
        lore.add("§6after clicking them");
        lore.add("§3(current "+p.getBrushLag()+"ms)");
        lore.add("§e(default 100.0ms)");
        setItem(Material.EMPTY_MAP, 1, (short) 0, "§r§aBrushing lag", lore, 49, inv);
        
        lore.clear();
        lore.add("§6Click to change memory time");
        lore.add("§6How long you can look at the wall");
        lore.add("§6in blind mode");
        lore.add("§3(current "+p.getMemTime()+"s)");
        lore.add("§e(default 1.0s)");
        setItem(Material.EMPTY_MAP, 1, (short) 0, "§r§aMemory time", lore, 50, inv);
        
        lore.clear();
        lore.add("§6Click to change lever delay");
        lore.add("§6How long the lever take to reset");
        lore.add("§3(current "+p.getLeverDelay()+"s)");
        lore.add("§e(default 0.5s)");
        setItem(Material.LEVER, 1, (short) 0, "§r§aLever delay", lore, 51, inv);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedCredit = numberFormat.format(CreditImporter.getCredits(p));

        lore.clear();
        lore.add("§e§l§m--------------------");
		lore.add("§6Qualification score§r:§b "+p.getScoreQualification());
        lore.add("§6Finals score§r:§b "+p.getScoreFinals());
        lore.add("§6Wide Qualification score§r:§b "+p.getScoreWideQualification());
        lore.add("§6Wide Finals score§r:§b "+p.getScoreWideFinals());
        lore.add("§6Lobby score§r:§b "+p.getScoreLobby());
        lore.add("§6Credits:§b " + formattedCredit);
        lore.add("§e§l§m--------------------");
        setSkull(Material.SKULL_ITEM, 1, (short) 3, "§r§aYour stats", lore, 52, inv, p.getPlayer().getName());
	}
	
	private static void setSecondLine(ArrayList<String> lore, Inventory inv, HPlayer p) {
		lore.clear();
        lore.add("§6Old walls animations");
        setItem(Material.INK_SACK, 1, (short) (p.isOldAnimation() ? 10 : 8), "§r§aClick to Toggle", lore, 19, inv);
        
        lore.clear();
        lore.add("§6Right sided walls");
        setItem(Material.INK_SACK, 1, (short) (p.isRightSided() ? 10 : 8), "§r§aClick to Toggle", lore, 20, inv);
        
        lore.clear();
        lore.add("§6Blind mode");
        setItem(Material.INK_SACK, 1, (short) (p.isBlind() ? 10 : 8), "§r§aClick to Toggle", lore, 21, inv);
        
        lore.clear();
        lore.add("§6Break mode");
        setItem(Material.INK_SACK, 1, (short) (p.isDestroy() ? 10 : 8), "§r§aClick to Toggle", lore, 22, inv);
        
        lore.clear();
        lore.add("§6Auto leave");
        setItem(Material.INK_SACK, 1, (short) (p.isAutoLeave() ? 10 : 8), "§r§aClick to Toggle", lore, 23, inv);
        
        lore.clear();
        lore.add("§6Title");
        setItem(Material.INK_SACK, 1, (short) (p.isTitle() ? 10 : 8), "§r§aClick to Toggle", lore, 24, inv);
        
        lore.clear();
        lore.add("§6Fly");
        setItem(Material.INK_SACK, 1, (short) (p.getPlayer().getAllowFlight() ? 10 : 8), "§r§aClick to Toggle", lore, 25, inv);
	}
	
	private static void setFirstLine(ArrayList<String> lore, Inventory inv) {
		lore.add("§6Toggle old walls animations");
        lore.add("§6Like how Hitw used to look");
        setSkull(Material.SKULL_ITEM, 1, (short) 3, "§r§aOld walls animations", lore, 10, inv, SkullOwner.Teddy);
        
        lore.clear();
        lore.add("§6Toggle right sided walls");
        lore.add("§6Every walls you play will be flipped");
        lore.add("");
        lore.add("§6Good for right to left player");
        setSkull(Material.SKULL_ITEM, 1, (short) 3, "§r§aRight sided walls", lore, 11, inv, SkullOwner.Mason);
        
        lore.clear();
        lore.add("§6Toggle Blind mode");
        lore.add("§6You only have a certain time");
        lore.add("§6to memorize the wall");
        lore.add("");
        lore.add("§6Then build it from memory");
        setItem(Material.WOOL, 1, (short) 15, "§r§aBlind mode", lore, 12, inv);
        
        lore.clear();
        lore.add("§6Toggle Break mode");
        lore.add("§6Your play field is filled with glass");
        lore.add("§6You have to break block to match the wall pattern");
        lore.add("");
        setItem(Material.GLASS, 1, (short) 0, "§r§aBreak mode", lore, 13, inv);
        
        lore.clear();
        lore.add("§6Toggle Auto leave");
        lore.add("§6Automatically end the game");
        lore.add("§6when you step out of the play area");
        setItem(Material.WOOD_DOOR, 1, (short) 0, "§r§aAuto leave", lore, 14, inv);
        
        lore.clear();
        lore.add("§6Toggle title");
        lore.add("§6such as perfect walls title");
        setItem(Material.SIGN, 1, (short) 0, "§r§aTitle", lore, 15, inv);
        
        lore.clear();
        lore.add("§6Toggle Fly");
        lore.add("§6Make you able to fly in survival");
        setItem(Material.FIREWORK, 1, (short) 0, "§r§aFly", lore, 16, inv);
	}
	
	private static void setItem(Material itemType, int quantity, short data, String name, ArrayList<String> lore, int slot,  Inventory inv) {
		ItemStack item = new ItemStack(itemType, quantity, data);
		ItemMeta meta = item.getItemMeta();

		meta.setLore(lore);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		inv.setItem(slot, item);
	}
	
	private static void setSkull(Material itemType, int quantity, short data, String name, ArrayList<String> lore, int slot,  Inventory inv, String owner) {
		ItemStack item = new ItemStack(itemType, quantity, data);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		meta.setOwner(owner);
		meta.setLore(lore);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		inv.setItem(slot, item);
	}
}
