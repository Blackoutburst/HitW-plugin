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
		SkinLoader.loadSkinFromUUID(0, "ab0ce1c342e847f18212e159fdbc2576"); //Simon
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
		SkinLoader.loadSkinFromUUID(13, "527f18702ca942258a8f928c0077ab80"); //Fuby
		SkinLoader.loadSkinFromUUID(14, "f7c77d999f154a66a87dc4a51ef30d19"); //Hypixel
		SkinLoader.loadSkinFromUUID(15, "638bd94631a0487783b3e24e0581d695"); //DeprecatedNether
		SkinLoader.loadSkinFromUUID(16, "3bd62e7edecd4baaa402ac84712e32cb"); //Ugy
		SkinLoader.loadSkinFromUUID(17, "9293868b414c42b2bd8e3bcb791247b9"); //Yaku
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
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), 4.5f, 54.0f, -811.5f, 135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "wideQ").setLocation(new Location(Bukkit.getWorld("world"), 4.5f, 55.0f, -1032.5f, 180, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(13));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "original").setLocation(new Location(Bukkit.getWorld("world"), -19.5f, 55.0f, -1032.5f, 180, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(14));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -222.5f, 83.0f, -952.5f, 135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -194.5f, 82.0f, -949.5f, 45, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -186.5f, 79.0f, -862.5f, 45, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), 76.5f, 54.0f, -938.5f, 135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -400.5f, 54.0f, -842.5f, 135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "spawn").setLocation(new Location(Bukkit.getWorld("world"), -355.5f, 55.0f, -1095.5f, -135, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "old_spawn").setLocation(new Location(Bukkit.getWorld("world"), -7.5f, 54.0f, -1036.5f, 180, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(17));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "new_spawn").setLocation(new Location(Bukkit.getWorld("old_map"), -565.5f, 6.0f, 665.5f, 90, 0)).setCapeVisible(false).setNameVisible(false).setSkin(SkinLoader.getSkinById(9));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
	
	private static void spawnHallOfFame(PlayerJoinEvent event) {
		NPC npc = new NPC(UUID.randomUUID(), "§4Winday").setLocation(new Location(Bukkit.getWorld("world"), 15.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(0));
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
		npc = new NPC(UUID.randomUUID(), "§eUgy").setLocation(new Location(Bukkit.getWorld("world"), 35.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(16));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§r").setLocation(new Location(Bukkit.getWorld("world"), 37.5f, 55.0f, -1038.5f, 90, 0)).setNameVisible(false).setCapeVisible(false).setSkin(SkinLoader.getSkinById(15));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
	
	private static void spawnTournamentWinner(PlayerJoinEvent event) {
		NPC npc = new NPC(UUID.randomUUID(), "§9Puffleman").setLocation(new Location(Bukkit.getWorld("world"), -30.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(1));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§4Mason").setLocation(new Location(Bukkit.getWorld("world"), -30.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(7));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§6Steak").setLocation(new Location(Bukkit.getWorld("world"), -34.5f, 55.0f, -1042.5f, 0, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(10));
		NPCManager.spawnNPC(npc, event.getPlayer());
		npc = new NPC(UUID.randomUUID(), "§fDogette").setLocation(new Location(Bukkit.getWorld("world"), -34.5f, 55.0f, -1034.5f, 180, 0)).setCapeVisible(false).setSkin(SkinLoader.getSkinById(11));
		NPCManager.spawnNPC(npc, event.getPlayer());
	}
}
