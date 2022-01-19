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
import com.blackoutburst.simplenpc.SimpleNPCPlayer;

import core.HPlayer;
import main.Main;

public class NPCListener implements NPCPacket {

	@Override
	public void onLeftClick(Player player, int npcId) {
		APlayer ap = APlayer.get(player);
		for (NPC npc : ap.npcs)
			if (npcId == npc.getEntityId()) {
				teleportToLocation(npc.getName(), player);
				
				if (ap.player.isOp()) {
					player.sendMessage("§bYou selected §a"+npc.getName()+" §bid: §a"+npc.getEntityId());
					SimpleNPCPlayer snp = SimpleNPCPlayer.getFromPlayer(player);
					snp.selectedID = npc.getEntityId();
				}
			}
	}

	@Override
	public void onRightClick(Player player, int npcId) {
		APlayer ap = APlayer.get(player);
		for (NPC npc : ap.npcs)
			if (npcId == npc.getEntityId()) {
				teleportToLocation(npc.getName(), player);
				if (ap.player.isOp()) {
					player.sendMessage("§bYou selected §a"+npc.getName()+" §bid: §a"+npc.getEntityId());
					SimpleNPCPlayer snp = SimpleNPCPlayer.getFromPlayer(player);
					snp.selectedID = npc.getEntityId();
				}
			}
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
			case "old_spawn": 
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				    @Override
				    public void run(){
				    	p.teleport(new Location(Bukkit.getWorld("old_map"), -574.5f, 7, 665.5f, -90, 0));
				    }
				}, (5L));
			break;
			case "new_spawn": 
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				    @Override
				    public void run(){
				    	p.teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0));
				    }
				}, (5L));
			break;
			default: return;
		}
	}
	
	
	private void openCustomGUI(HPlayer p) {
		Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 45, ChatColor.BLACK + "Custom walls locations");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 45; i++)
        	inv.setItem(i, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aWide Qualification");
        skull.setOwner(SkullOwner.Astele);
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aWide Finals");
        skull.setOwner(SkullOwner.Khantrast);
        item.setItemMeta(skull);
        inv.setItem(13, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aLobby walls");
        skull.setOwner(SkullOwner.Shingblad);
        item.setItemMeta(skull);
        inv.setItem(15, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aPrototype walls");
        skull.setOwner(SkullOwner.FrogOp);
        item.setItemMeta(skull);
        inv.setItem(31, item);
        
        p.getPlayer().openInventory(inv);
	}
	
	public static void GUICustomAction(Inventory inv, int slot, HPlayer p) {
		switch(slot) {
			case 11: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 2.5f, 55, -817.5f, 0, 0)); break;
			case 13: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -402.5f, 55, -848.5f, 0, 0)); break;
			case 15: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 74.5f, 55, -944.5f, 0, 0)); break;
			case 31: p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 352.5f, 55, -1097.5f, 90, 0)); break;
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
        skull.setOwner(SkullOwner.Teddy);
        item.setItemMeta(skull);
        inv.setItem(11, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aFinals");
        skull.setOwner(SkullOwner.Sparklizm);
        item.setItemMeta(skull);
        inv.setItem(13, item);
        
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("§r§aLobby");
        skull.setOwner(SkullOwner.Shingblad);
        item.setItemMeta(skull);
        inv.setItem(15, item);
        
        p.getPlayer().openInventory(inv);
	}
}
