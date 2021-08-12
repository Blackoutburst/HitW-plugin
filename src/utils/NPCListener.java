package utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;

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
			case "wideQ": p.teleport(new Location(Bukkit.getWorld("world"), -13.5f, 55, -940.5f, 0, 0)); break;
			default: return;
		}
	}
}
