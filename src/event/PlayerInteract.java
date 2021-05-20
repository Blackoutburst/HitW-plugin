package event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Stairs;

import core.ConfigurationMenu;
import core.HGame;
import core.HPlayer;
import core.WallManager;
import main.Main;
import utils.GameUtils;

public class PlayerInteract {
	
	private void seat(Player p, Location loc, Stairs stairs) {
		Location seatLoc = loc;
		final float pitch;
		
		switch (stairs.getFacing()) {
			case SOUTH: pitch = 0; break;
			case WEST: pitch = 90; break;
			case NORTH: pitch = 180; break;
			case EAST: pitch = -90; break;
			default: pitch = 0; break;
		}
		
		seatLoc.setX(seatLoc.getX() + 0.5f);
		seatLoc.setY(seatLoc.getY() - 1.1f);
		seatLoc.setZ(seatLoc.getZ() + 0.5f);
		
		ArmorStand a = p.getWorld().spawn(seatLoc, ArmorStand.class);   
		a.teleport(seatLoc);
		a.setGravity(false);
		a.setVisible(false);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				a.teleport(new Location(p.getWorld(), seatLoc.getX(), seatLoc.getY(), seatLoc.getZ(), pitch, 0));
				a.setPassenger(p);
			}
		}, (3L));
	}
	
	public void execute(PlayerInteractEvent event) {
		HPlayer p = HPlayer.getHPlayer(event.getPlayer());
		
		if(event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR)) {
			ConfigurationMenu.generate(HPlayer.getHPlayer(event.getPlayer()));
		}
		
		if (event.getClickedBlock() == null) return;
		if(event.getClickedBlock().getType().equals(Material.BIRCH_WOOD_STAIRS) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Stairs stairs = (Stairs) event.getClickedBlock().getState().getData();
			seat(event.getPlayer(), event.getClickedBlock().getLocation(), stairs);
		}
		
		if (!p.isInGame()) return;
		if(event.getClickedBlock().getType().equals(Material.LEVER) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			HGame game = null;
			
			if (p.isInParty()) {
				game = GameUtils.getGameArea(p.getParty().get(0).getPlayer());
			} else {
				game = GameUtils.getGameArea(p.getPlayer());
			}
			if (game.isLeverBusy()) {
				event.setCancelled(true);
				return;
			}
			pullWall(p, game);
		}
	}
	
	public void pullWall(HPlayer p, HGame game) {
		if (!game.isWallPulled()) {
			WallManager.pullWall(p, game, p.getTime() < 3 ? true : false, true);
		}
	}
}
