package event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import core.HGame;
import core.HPlayer;
import utils.GameUtils;
import utils.ScoreboardManager;

public class BlockPlace {
	
	public void execute(BlockPlaceEvent event) {
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		
		if (!p.isInGame()) return;
		
		HGame game = null;
		
		if (p.isInParty()) {
			game = GameUtils.getGameArea(p.getParty().get(0).getPlayer());
		} else {
			game = GameUtils.getGameArea(p.getPlayer());
		}
		
		if (game == null) {
			event.getBlock().setType(Material.AIR);
			p.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1f, 1f);
			p.setChoke(p.getChoke() + 1);
			ScoreboardManager.updateScoreboard(p);
			event.setCancelled(true);
			return;
		}
		
		if (game.isCustomGame() && !GameUtils.inCustomPlayArea(event.getBlock().getLocation(), game)) {
			event.getBlock().setType(Material.AIR);
			p.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1f, 1f);
			p.setChoke(p.getChoke() + 1);
			ScoreboardManager.updateScoreboard(p);
		} else if (!GameUtils.inPlayArea(event.getBlock().getLocation(), game)) {
			event.getBlock().setType(Material.AIR);
			p.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1f, 1f);
			p.setChoke(p.getChoke() + 1);
			ScoreboardManager.updateScoreboard(p);
		}
		
		ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
		p.getPlayer().getInventory().setItem(0, stack);
	}
}
