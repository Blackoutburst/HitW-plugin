package event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockDamageEvent;

import core.HGame;
import core.HPlayer;
import main.Main;
import utils.GameUtils;

public class BlockDamage {
	
	public void execute(BlockDamageEvent event) {
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		if (!p.isInGame()) return;
		
		HGame game = null;
		
		if (p.isInParty()) {
			game = GameUtils.getGameArea(p.getParty().get(0).getPlayer());
		} else {
			game = GameUtils.getGameArea(p.getPlayer());
		}
		
		if (game == null) {
			event.setCancelled(true);
			return;
		}
		
		if (GameUtils.inPlayArea(event.getBlock().getLocation(), game) && event.getBlock().getType().equals(Material.STAINED_GLASS)) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				@Override
				public void run(){
            		event.getBlock().setType(Material.AIR);
            		Bukkit.getWorld("world").playSound(event.getPlayer().getLocation(), Sound.GLASS, 1f, 1f);
				}
			}, (long)((p.getBrushLag() * 20) / 1000));
		}
	}
}
