package com.blackoutburst.simplenpc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;

import main.Main;

public class CreateNPC {

	public boolean run(CommandSender sender, String[] args) {
		if (args.length != 2) {
			System.out.println("§cInvalid usage! Try §e/createnpc §6[name] [skinname]");
			return (true);
		}
		
		if (!sender.isOp()) {
			System.out.println("§cMissing permission!");
			return (true);
		}
		
		Player player;
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			System.out.println("§cOnly players can run this command!");
			return (true);
		}

		String uuid;

		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + args[1]);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			String line;

			while((line = br.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}

			is.close();
			br.close();
			JsonObject json = (new JsonParser()).parse(builder.toString()).getAsJsonObject();
			uuid = json.get("id").getAsString();
		} catch (Exception e) {
			System.err.println("Could not get player uuid!");
			return true;
		}

		SkinLoader.loadSkinFromUUID(Main.skinId, uuid);
		
		NPC npc = new NPC(UUID.randomUUID(), args[0])
		.setLocation(player.getLocation())
		.setSkin(SkinLoader.getSkinById(Main.skinId++))
		.setCapeVisible(false);
		
		for (SimpleNPCPlayer p : Main.npcplayers) {
			NPCManager.spawnNPC(npc, p.player);
			p.npcs.add(npc);
		}
		NPCFile.saveSeat(npc, uuid);
		System.out.println("§aCreated a new npc: "+args[0]+" with the skin of: "+args[1]);
		
		return (true);
	}
}
