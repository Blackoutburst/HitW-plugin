package com.blackoutburst.simplenpc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.blackout.npcapi.core.NPC;

import main.Main;

public class SimpleNPCPlayer {

	public Player player;
	public int selectedID;
	public List<NPC> npcs;
	
	public SimpleNPCPlayer(Player player) {
		this.player = player;
		this.selectedID = -1;
		this.npcs = new ArrayList<>();
	}
	
	public static SimpleNPCPlayer getFromPlayer(Player p) {
		for (SimpleNPCPlayer ap : Main.npcplayers) {
			if (ap.player.getUniqueId().equals(p.getUniqueId())) {
				return (ap);
			}
		}
		return (null);
	}
	
}
