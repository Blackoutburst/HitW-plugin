package com.blackoutburst.simplenpc;

import java.util.UUID;

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
		
		SkinLoader.loadSkinFromName(Main.skinId, args[1]);
		
		NPC npc = new NPC(UUID.randomUUID(), args[0])
		.setLocation(player.getLocation())
		.setSkin(SkinLoader.getSkinById(Main.skinId++))
		.setCapeVisible(false);
		
		for (SimpleNPCPlayer p : Main.npcplayers) {
			NPCManager.spawnNPC(npc, p.player);
			p.npcs.add(npc);
		}
		NPCFile.saveSeat(npc, args[1]);
		System.out.println("§aCreated a new npc: "+args[0]+" with the skin of: "+args[1]);
		
		return (true);
	}
}
