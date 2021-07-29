package utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

public class NPCUtils {

	public static void loadSkins() {
		SkinLoader.loadSkinFromUUID(0, "16d455475ddf42d48e9ffdb45cd69fa5"); //Simon
		SkinLoader.loadSkinFromUUID(1, "2f066b0e58fd4db6a24bfbce5ab5bdb4"); //Puffleman
		SkinLoader.loadSkinFromUUID(2, "c46f6438006049d4830ca6fa732303fc"); //Arcxire
		SkinLoader.loadSkinFromUUID(3, "bb88e17c171f4f36b4ff6e056abe4fff"); //Cosmic
		SkinLoader.loadSkinFromUUID(4, "082e28e178724b21ba12b3231f7a872c"); //Teddy
		SkinLoader.loadSkinFromUUID(5, "8b8eb178c169486c97924255c26c0967"); //Sparklizm
		SkinLoader.loadSkinFromUUID(6, "983b1593a9a443ab8f2a15e0a65f720f"); //Distasted
		SkinLoader.loadSkinFromUUID(7, "bedb53e2dd754786a2ac1ec80023aabe"); //Mason
		SkinLoader.loadSkinFromUUID(8, "b00d07b27984424db8f2d96c1e4aace5"); //Hammy
		SkinLoader.loadSkinFromUUID(9, "b8ef1c7615e04b958d474ca133561f5a"); //Blackout
		SkinLoader.loadSkinFromUUID(10, "9477ef812e1b4345be40b1529f22c6a6"); //Steak
		SkinLoader.loadSkinFromUUID(11, "75203801f5a54ba6baa691523aa15848"); //Dogette
		SkinLoader.loadSkinFromUUID(12, "fb278a5613644c728076efa043204989"); //Alon
	}
	
	public static void spawnNPC(PlayerJoinEvent event) {
		spawnTeleportNPC(event);
		spawnHallOfFame(event);
		spawnTournamentWinner(event);
	}
	
	private static void spawnTeleportNPC(PlayerJoinEvent event) {
		NPC npc = new NPC(UUID.randomUUID(), "qualification").setLocation(new Location(Bukkit.getWorld("world"), -2.5f, 55.0f, -1031.5f, 180, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(1));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "finals").setLocation(new Location(Bukkit.getWorld("world"), -12.5f, 55.0f, -1031.5f, 180, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(2));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -5.5f, 54.0f, -1007.5f, 135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -66.5f, 54.0f, -1036.5f, -135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
	
	private static void spawnHallOfFame(PlayerJoinEvent event) {
		NPC npc = new NPC(UUID.randomUUID(), "§2Simon").setLocation(new Location(Bukkit.getWorld("world"), 15.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(0));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§9Puffleman").setLocation(new Location(Bukkit.getWorld("world"), 15.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(1));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§3Arcxire").setLocation(new Location(Bukkit.getWorld("world"), 19.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(2));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§6Cosmic").setLocation(new Location(Bukkit.getWorld("world"), 19.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(3));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§6Teddy").setLocation(new Location(Bukkit.getWorld("world"), 23.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(4));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§eSparklizm").setLocation(new Location(Bukkit.getWorld("world"), 23.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(5));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§dDistasted").setLocation(new Location(Bukkit.getWorld("world"), 27.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(6));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§4Mason").setLocation(new Location(Bukkit.getWorld("world"), 27.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(7));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§6Hammy").setLocation(new Location(Bukkit.getWorld("world"), 31.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(8));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§5Blackout").setLocation(new Location(Bukkit.getWorld("world"), 31.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§7Alon").setLocation(new Location(Bukkit.getWorld("world"), 35.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(12));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
	
	private static void spawnTournamentWinner(PlayerJoinEvent event) {
		NPC npc = new NPC(UUID.randomUUID(), "§2Simon").setLocation(new Location(Bukkit.getWorld("world"), -30.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(0));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§4Mason").setLocation(new Location(Bukkit.getWorld("world"), -30.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(7));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§6Steak").setLocation(new Location(Bukkit.getWorld("world"), -34.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(10));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§fDogette").setLocation(new Location(Bukkit.getWorld("world"), -34.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(11));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
}
