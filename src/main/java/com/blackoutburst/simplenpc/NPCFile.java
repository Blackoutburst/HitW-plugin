package com.blackoutburst.simplenpc;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import main.Main;

public class NPCFile {

	public static void loadNPC(SimpleNPCPlayer p) {
		YamlConfiguration npcsFile = YamlConfiguration.loadConfiguration(new File("plugins/SimpleNPC/npc.yml"));
		
		if (npcsFile.getConfigurationSection("npc") == null) return;
		
		Set<String> seat = npcsFile.getConfigurationSection("npc").getKeys(false);
		
		for (String s : seat) {
			UUID uuid = UUID.fromString(s);
			World world = Bukkit.getWorld(npcsFile.getString("npc."+s+".location.world"));
			double x = npcsFile.getDouble("npc."+s+".location.x");
			double y = npcsFile.getDouble("npc."+s+".location.y");
			double z = npcsFile.getDouble("npc."+s+".location.z");
			float pitch = (float)(npcsFile.getDouble("npc."+s+".pitch"));
			float yaw = (float)(npcsFile.getDouble("npc."+s+".yaw"));
			String name = npcsFile.getString("npc."+s+".name");
			String skinUUID = npcsFile.getString("npc."+s+".skin");
			
			Location loc = new Location(world, x, y, z, yaw, pitch);
			
			SkinLoader.loadSkinFromUUID(Main.skinId, skinUUID);
			
			NPC npc = new NPC(uuid, name)
			.setLocation(loc)
			.setSkin(SkinLoader.getSkinById(Main.skinId++))
			.setCapeVisible(false);

			if (npc.getSkin() == null) continue;

			NPCManager.spawnNPC(npc, p.player);
			p.npcs.add(npc);
		}
	}
	
	public static void saveSeat(NPC s, String uuid) {
		YamlConfiguration npcsFile = YamlConfiguration.loadConfiguration(new File("plugins/SimpleNPC/npc.yml"));
		
		npcsFile.set("npc."+s.getUUID().toString()+".location.world", s.getLocation().getWorld().getName());
		npcsFile.set("npc."+s.getUUID().toString()+".location.x", s.getLocation().getX());
		npcsFile.set("npc."+s.getUUID().toString()+".location.y", s.getLocation().getBlockY());
		npcsFile.set("npc."+s.getUUID().toString()+".location.z", s.getLocation().getBlockZ());
		npcsFile.set("npc."+s.getUUID().toString()+".pitch", s.getLocation().getPitch());
		npcsFile.set("npc."+s.getUUID().toString()+".yaw", s.getLocation().getYaw());
		npcsFile.set("npc."+s.getUUID().toString()+".name", s.getName());
		npcsFile.set("npc."+s.getUUID().toString()+".skin", uuid);
		
		try {
			npcsFile.save(new File("plugins/SimpleNPC/npc.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteSeat(NPC s) {
		YamlConfiguration npcsFile = YamlConfiguration.loadConfiguration(new File("plugins/SimpleNPC/npc.yml"));
		
		npcsFile.set("npc."+s.getUUID().toString(), null);
		
		try {
			npcsFile.save(new File("plugins/SimpleNPC/npc.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
