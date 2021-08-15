package utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;

import core.HPlayer;
import main.Main;

public class NPCListener implements NPCPacket {

	@Override
	public void onLeftClick(Player player, int npcId) {
		APlayer ap = APlayer.get(player);
		for (NPC npc : ap.npcs)
			if (npcId == npc.getEntityId())
				teleportToLocation(npc.getName(), player);
	}

	@Override
	public void onRightClick(Player player, int npcId) {
		APlayer ap = APlayer.get(player);
		for (NPC npc : ap.npcs)
			if (npcId == npc.getEntityId())
				teleportToLocation(npc.getName(), player);
	}

		
	private void teleportToLocation(String npcName, Player p) {
		switch (npcName) {
			case "spawn": p.teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0)); break;
			case "finals": p.teleport(new Location(Bukkit.getWorld("world"), -60.5f, 55, -1038.5f, 90, 0)); break;
			case "qualification": p.teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1013.5f, 0, 0)); break;
			case "wideQ": 
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				    @Override
				    public void run(){
				    	openCustomGUI(HPlayer.getHPlayer(p)); 
				    }
				}, (5L));
				break;
				
			case "original": 
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				    @Override
				    public void run(){
				    	openGUI(HPlayer.getHPlayer(p)); 
				    }
				}, (5L));
				break;
			default: return;
		}
	}
	
	
	private void openCustomGUI(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 27, ChatColor.BLACK + "Custom walls locations");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 27; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aLong Qualification");
        skull.setOwner("Fuby");
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aLobby walls");
        skull.setOwner("Shingblad");
        item.setItemMeta(skull);
        inv.setItem(15, item);
        
        p.getPlayer().openInventory(inv);
	}
	
	public static void GUICustomAction(Inventory inv, int slot, HPlayer p) {
		switch(slot) {
			case 11: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -13.5f, 55, -940.5f, 0, 0)); break;
			case 15: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 74.5f, 55, -944.5f, 0, 0)); break;
			default: break;
		}
	}
	
	public static void GUIAction(Inventory inv, int slot, HPlayer p) {
		switch(slot) {
			case 11: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -225.5f, 84, -955.5f, 0, 0)); break;
			case 13: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -189.5f, 79, -861.5f, -135, 0)); break;
			case 15: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -199.5f, 83, -945.5f, -90, 0)); break;
			default: break;
		}
	}
	
	private void openGUI(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 27, ChatColor.BLACK + "Hypixel map locations");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 27; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aQualification");
        skull.setOwner("NamelessTeddy");
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aFinals");
        skull.setOwner("Sparklizm");
        item.setItemMeta(skull);
        inv.setItem(13, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aLobby");
        skull.setOwner("Shingblad");
        item.setItemMeta(skull);
        inv.setItem(15, item);
        
        p.getPlayer().openInventory(inv);
	}
}
