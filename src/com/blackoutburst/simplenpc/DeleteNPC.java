package com.blackoutburst.simplenpc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;

import main.Main;

public class DeleteNPC {

	public boolean run(CommandSender sender, String[] args) {
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
		
		SimpleNPCPlayer snp = SimpleNPCPlayer.getFromPlayer(player);
		
		if (snp.selectedID == -1) {
			System.out.println("§cYou must select a NPC before runnnig this command");
			return (true);
		}
		
		for (SimpleNPCPlayer p : Main.npcplayers) {
			int j = p.npcs.size();
			for (int i = 0; i < j; i++) {
				NPC npc = p.npcs.get(i);
				if (npc.getEntityId() == snp.selectedID) {
					p.npcs.remove(npc);
					NPCManager.deleteNPC(player, npc);
					NPCFile.deleteSeat(npc);
					j--;
				}
			}
		}
		snp.selectedID = -1;
		
		return (true);
	}
}
