package core;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import main.Main;
import utils.GameUtils;
import utils.Utils;

public class WallManager {
	
	private static final Random RNG = new Random();
	
	private static void clearHider(HGame game) {
		int north = game.direction == Direction.NORTH ? 1 : 0;
		int south = game.direction == Direction.SOUTH ? 1 : 0;
		int west = game.direction == Direction.WEST ? 1 : 0;
		int east = game.direction == Direction.EAST ? 1 : 0;
		
		int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);

		for (int x = xS+north-south; x <= xL+north-south; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS-west+east; z <= zL-west+east; z++) {
					game.world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
	}
	
	public static void hideWall(HPlayer p, HGame game) {
		long delay = 0;
		
		if (p.isInParty()) {
			delay = (long) (20L * p.getParty().get(0).getMemTime());
		} else {
			delay = (long) (20L * p.getMemTime());
		}
		
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		
		if (p.isOldAnimation()) {
			resetPlayField(game, p, false);
			delay = 5L * (yL-yS + 1);
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@SuppressWarnings("deprecation")
			@Override
			public void run(){
				int north = game.direction == Direction.NORTH ? 1 : 0;
				int south = game.direction == Direction.SOUTH ? 1 : 0;
				int west = game.direction == Direction.WEST ? 1 : 0;
				int east = game.direction == Direction.EAST ? 1 : 0;
				
				int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
				int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
				int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
				int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
				int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
				int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		
				for (int x = xS+north-south; x <= xL+north-south; x++) {
					for (int y = yS; y <= yL; y++) {
						for (int z = zS-west+east; z <= zL-west+east; z++) {
							game.world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
							game.world.getBlockAt(x, y, z).setData((byte)(p.getWallColor()));
						}
					}
				}
				
				final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;
			
				if (p.isInParty()) {
					for (HPlayer hp : p.getParty()) {
						ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, hp.getGlassColor());
						hp.getPlayer().getInventory().setItem(0, stack);
						hp.setWallBegin(Instant.now());
					}
				} else {
					ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
					p.getPlayer().getInventory().setItem(0, stack);
					p.setWallBegin(Instant.now());
				}
				if (leader.isDestroy()) {
					if (!leader.isOldAnimation()) {
						resetPlayField(game, p, false);
					}
					fillPlayField(game, p);
				}
			}
		}, (delay));
	}
	
	private static void delayedAction(HPlayer p, HGame game, boolean endGame) {
		if (endGame) return;
		
		long delay = 10L;
		
		if (!game.isIncrementingHoles()) {
			if (p.isInParty()) {
				delay = (long) (20 * p.getParty().get(0).getLeverDelay());
			} else {
				delay = (long) (20 * p.getLeverDelay());
			}
		}
		
		final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;
		
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		
		if (leader.isOldAnimation()) {
			resetPlayField(game, p, false);
			delay = 5L * (yL-yS + 1);
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				if (game.owner == null)
					return;
				if (leader.isDestroy()) {
					if (!leader.isOldAnimation()) {
						resetPlayField(game, p, false);
					}
					fillPlayField(game, p);
				} else {
					if (!leader.isOldAnimation()) {
						resetPlayField(game, p, false);
					}
				}
				if (leader.isBlind()) {
					hideWall(p, game);
				} else {
					if (p.isInParty()) {
						for (HPlayer hp : p.getParty()) {
							ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, hp.getGlassColor());
							hp.getPlayer().getInventory().setItem(0, stack);
							hp.setWallBegin(Instant.now());
						}
					} else {
						ItemStack stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
						p.getPlayer().getInventory().setItem(0, stack);
						p.setWallBegin(Instant.now());
					}
				}
			}
		}, (delay));
		
		delay = game.getName().equals("Lobby Wall") ? 15L : 30L;
		
		if (!game.isIncrementingHoles()) {
			if (p.isInParty()) {
				delay = (long) (20 * p.getParty().get(0).getLeverDelay());
			} else {
				delay = (long) (20 * p.getLeverDelay());
			}
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				game.setLeverBusy(false);
			}
		}, (delay));
	}
	
	private static void calulateScore(HPlayer p, HGame game, boolean endGame, boolean leverTrigger) {
		int missed = 0;
		int misplaced = 0;
		
		int north = game.direction == Direction.NORTH ? 1 : 0;
		int south = game.direction == Direction.SOUTH ? 1 : 0;
		int west = game.direction == Direction.WEST ? 1 : 0;
		int east = game.direction == Direction.EAST ? 1 : 0;
		
		int xS = (int) (game.getPlayfield().x0 < game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int xL = (int) (game.getPlayfield().x0 > game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int zS = (int) (game.getPlayfield().z0 < game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		int zL = (int) (game.getPlayfield().z0 > game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		
		for (int x = xS; x <= xL; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS; z <= zL; z++) {
					if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
						missed++;
					}
					if ((west == 1 || east == 1) && game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)+east-west).getType().equals(Material.STAINED_GLASS)) {
						misplaced++;
					}
					if ((south == 1 || north == 1) && game.world.getBlockAt((int)(game.getPlayfield().x0)-south+north, y, z).getType().equals(Material.STAINED_GLASS)) {
						misplaced++;
					}
				}
			}
		}
		
		int score = checkHole(game) - misplaced - missed;
		
		if (missed != 0)
			score--;
		
		if (score < 0)
			score = 0;
		
		if (missed == 0 && misplaced == 0 && score == 0) {
			resetPlayField(game, p, true);
			return;
		}
		
		final boolean bufferWall = missed / checkHole(game) > 0.4 && p.wall < 8;
		
		if (score >= 0) {
			if (missed == 0 && misplaced == 0) {
				if (p.isInParty()) {
					for (HPlayer hp : p.getParty()) {
						hp.setMisplaced(hp.getMisplaced() + misplaced);
						hp.setMissed(hp.getMissed() + missed);
						hp.setScore(hp.getScore() + score);
						hp.setWall(hp.getWall() + 1);
						hp.setPerfectWall(hp.getPerfectWall() + 1);
						
						if (hp.isTitle()) {
							GameUtils.sendTitle(hp.getPlayer(), "§aPERFECT!", "", 5, 15, 10);
						}
						hp.getPlayer().sendMessage("§aPerfect Wall §6+"+score+" §b("+Utils.ROUND.format((Float.valueOf(Duration.between(hp.getWallBegin(), hp.getWallEnd()).toMillis()) / 1000.0f))+"s)");
						hp.getPlayer().playSound(hp.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
						if (leverTrigger)
							hp.getWallTime().add(Float.valueOf(Duration.between(hp.getWallBegin(), hp.getWallEnd()).toMillis()) / 1000.0f);
					}
				} else {
					p.setMisplaced(p.getMisplaced() + misplaced);
					p.setMissed(p.getMissed() + missed);
					p.setScore(p.getScore() + score);
					p.setWall(p.getWall() + 1);
					p.setPerfectWall(p.getPerfectWall() + 1);
					if (p.isTitle()) {
						GameUtils.sendTitle(p.getPlayer(), "§aPERFECT!", "", 5, 15, 10);
					}
					p.getPlayer().sendMessage("§aPerfect Wall §6+"+score+" §r| §b("+Utils.ROUND.format((Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f))+"s)");
					p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1f);
					if (leverTrigger)
						p.getWallTime().add(Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f);
				}
			} else {
				if (p.isInParty()) {
					for (HPlayer hp : p.getParty()) {
						if (!bufferWall) {
							hp.setMisplaced(hp.getMisplaced() + misplaced);
							hp.setMissed(hp.getMissed() + missed);
						}
						hp.setScore(hp.getScore() + score);
						hp.setWall(hp.getWall() + 1);
						if (!bufferWall) {
							hp.getPlayer().sendMessage("§4Missing: §6"+missed+" §r| §4Misplaced: §6"+misplaced+" §r| §aScore: §6+"+score+" §r| §b("+Utils.ROUND.format((Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f))+"s)");
						} else {
							hp.getPlayer().sendMessage("§4Missing: §6"+missed+" §r| §4Misplaced: §6"+misplaced+" §r| §aScore: §6+"+score+" §r| §b("+Utils.ROUND.format((Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f))+"s) §d(Buffer Wall)");
						}
						hp.getPlayer().playSound(hp.getPlayer().getLocation(), Sound.NOTE_BASS_GUITAR, 1f, 1f);
						if (leverTrigger && !bufferWall)
							hp.getWallTime().add(Float.valueOf(Duration.between(hp.getWallBegin(), hp.getWallEnd()).toMillis()) / 1000.0f);
					}
				} else {
					if (!bufferWall) {
						p.setMisplaced(p.getMisplaced() + misplaced);
						p.setMissed(p.getMissed() + missed);
					}
					p.setScore(p.getScore() + score);
					p.setWall(p.getWall() + 1);
					if (!bufferWall) {
						p.getPlayer().sendMessage("§4Missing: §6"+missed+" §r| §4Misplaced: §6"+misplaced+" §r| §aScore: §6+"+score+" §r| §b("+Utils.ROUND.format((Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f))+"s)");
					} else {
						p.getPlayer().sendMessage("§4Missing: §6"+missed+" §r| §4Misplaced: §6"+misplaced+" §r| §aScore: §6+"+score+" §r| §b("+Utils.ROUND.format((Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f))+"s) §d(Buffer Wall)");
					}
					p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.NOTE_BASS_GUITAR, 1f, 1f);
					if (leverTrigger && !bufferWall)
						p.getWallTime().add(Float.valueOf(Duration.between(p.getWallBegin(), p.getWallEnd()).toMillis()) / 1000.0f);
				}
			}
		}
		generateWall(p, game, endGame);
		delayedAction(p, game, endGame);
	}
	
	@SuppressWarnings("deprecation")
	public static void pullWall(HPlayer p, HGame game, boolean endGame, boolean leverTrigger) {
		if (game.getType().equals("Score") || game.getType().equals("Endless")) {
			endGame = false;
		}
		if (game.isWallPulled()) return;
		game.setLeverBusy(true);
		game.setWallPulled(true);
		
		if (p.isInParty()) {
			for (HPlayer hp : p.getParty()) {
				hp.getPlayer().getInventory().clear();
				hp.setWallEnd(Instant.now());
			}
		} else {
			p.getPlayer().getInventory().clear();
			p.setWallEnd(Instant.now());
		}
		
		int north = game.direction == Direction.NORTH ? 1 : 0;
		int south = game.direction == Direction.SOUTH ? 1 : 0;
		int west = game.direction == Direction.WEST ? 1 : 0;
		int east = game.direction == Direction.EAST ? 1 : 0;
		
		int xSW = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xLW = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int ySW = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yLW = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int zSW = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zLW = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		
		int xS = (int) (game.getPlayfield().x0 < game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int xL = (int) (game.getPlayfield().x0 > game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int zS = (int) (game.getPlayfield().z0 < game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		int zL = (int) (game.getPlayfield().z0 > game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);

		for (int x = xSW; x <= xLW; x++) {
			for (int y = ySW; y <= yLW; y++) {
				for (int z = zSW; z <= zLW; z++) {
					if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) continue;
					if (west == 1 || east == 1) {
						if (game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)).getType().equals(Material.STAINED_GLASS)) {
							game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)+east-west).setType(Material.STAINED_GLASS);
							game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)+east-west).setData((byte)(14));
						}
						game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)).setType(game.world.getBlockAt(x, y, z).getType());
						game.world.getBlockAt(x, y, (int)(game.getPlayfield().z0)).setData(game.world.getBlockAt(x, y, z).getData());
					}
					if (south == 1 || north == 1) {
						if (game.world.getBlockAt((int)(game.getPlayfield().x0), y, z).getType().equals(Material.STAINED_GLASS)) {
							game.world.getBlockAt((int)(game.getPlayfield().x0)-south+north, y, z).setType(Material.STAINED_GLASS);
							game.world.getBlockAt((int)(game.getPlayfield().x0)-south+north, y, z).setData((byte)(14));
						}
						game.world.getBlockAt((int)(game.getPlayfield().x0), y, z).setType(game.world.getBlockAt(x, y, z).getType());
						game.world.getBlockAt((int)(game.getPlayfield().x0), y, z).setData(game.world.getBlockAt(x, y, z).getData());
					}
				}
			}
		}
		for (int x = xS; x <= xL; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS; z <= zL; z++) {
					if (game.world.getBlockAt(x, y, z).getType().equals(Material.STAINED_GLASS)) {
						game.world.getBlockAt(x, y, z).setData((byte)(13));
					}
				}
			}
		}
		calulateScore(p, game, endGame, leverTrigger);
	}
	
	private static int checkHole(HGame game) {
		int hole = 0;
		
		int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);

		for (int x = xS; x <= xL; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS; z <= zL; z++) {
					if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
						hole++;
					}
				}
			}
		}
		return hole;
	}
	
	@SuppressWarnings("deprecation")
	public static void fillPlayField(HGame game, HPlayer p) {
		int xS = (int) (game.getPlayfield().x0 < game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int xL = (int) (game.getPlayfield().x0 > game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int zS = (int) (game.getPlayfield().z0 < game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		int zL = (int) (game.getPlayfield().z0 > game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		
		if (p.isOldAnimation()) {
			long delay = 5L * ((yL - yS) + 2);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				@Override
				public void run(){
					for (int x = xS; x <= xL; x++) {
						for (int y = yS; y <= yL; y++) {
							for (int z = zS; z <= zL; z++) {
								game.world.getBlockAt(x, y, z).setType(Material.STAINED_GLASS);
								game.world.getBlockAt(x, y, z).setData((byte) p.getGlassColor());
							}
						}
					}
				}
			}, delay);
		} else {
			for (int x = xS; x <= xL; x++) {
				for (int y = yS; y <= yL; y++) {
					for (int z = zS; z <= zL; z++) {
						game.world.getBlockAt(x, y, z).setType(Material.STAINED_GLASS);
						game.world.getBlockAt(x, y, z).setData((byte) p.getGlassColor());
					}
				}
			}
		}
	}
	
	public static void resetPlayField(HGame game, HPlayer p, boolean bypasAnimation) {
		int north = game.direction == Direction.NORTH ? 1 : 0;
		int south = game.direction == Direction.SOUTH ? 1 : 0;
		int west = game.direction == Direction.WEST ? 1 : 0;
		int east = game.direction == Direction.EAST ? 1 : 0;
		
		int xS = (int) (game.getPlayfield().x0 < game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int xL = (int) (game.getPlayfield().x0 > game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		int yS = (int) (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int yL = (int) (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		int zS = (int) (game.getPlayfield().z0 < game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		int zL = (int) (game.getPlayfield().z0 > game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);

		final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;
		
		if (leader.isOldAnimation() && !bypasAnimation) {
			for (int i = 0; i <= yL-yS; i++) {
				long delay = 5L * (i + 1);
				final int y = yL - i;
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
					@Override
					public void run(){
						game.world.playSound(p.getPlayer().getLocation(), Sound.ORB_PICKUP, 1f, 1f);
						
						if (p.isInParty()) {
							for (HPlayer hp : p.getParty()) {
								hp.setWallBegin(Instant.now());
							}
						} else {
							p.setWallBegin(Instant.now());
						}
						
						for (int x = xS-south; x <= xL+north; x++) {
							for (int z = zS-west; z <= zL+east; z++) {
								game.world.getBlockAt(x, y, z).setType(Material.AIR);
							}
						}
					}
				}, delay);
			}
			
			long delay = 5L * (yL-yS + 1);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				@Override
				public void run(){
					game.setWallPulled(false);
				}
			}, delay);
		} else {
			for (int x = xS-south; x <= xL+north; x++) {
				for (int y = yS; y <= yL; y++) {
					for (int z = zS-west; z <= zL+east; z++) {
						game.world.getBlockAt(x, y, z).setType(Material.AIR);
					}
				}
			}
			game.setWallPulled(false);
		}
	}
	
	public static void resetWall(HGame game) {
		int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);

		for (int x = xS; x <= xL; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS; z <= zL; z++) {
					game.world.getBlockAt(x, y, z).setType(Material.QUARTZ_BLOCK);
				}
			}
		}
		clearHider(game);
	}
	
	@SuppressWarnings("deprecation")
	private static void cleanWall(HGame game, HPlayer p) {
		int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);

		for (int x = xS; x <= xL; x++) {
			for (int y = yS; y <= yL; y++) {
				for (int z = zS; z <= zL; z++) {
					game.world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
					game.world.getBlockAt(x, y, z).setData((byte)(p.getWallColor()));
				}
			}
		}
		clearHider(game);
	}
	
	@SuppressWarnings("deprecation")
	private static void generateXAxys(int y, HGame game, HPlayer p, int hole)  {
		int rightSided = p.isRightSided() ? 1 : 0;
		int xS = (int) (game.getWall().x0 < game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int xL = (int) (game.getWall().x0 > game.getWall().x1 ? game.getWall().x0 : game.getWall().x1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
	
			if (game.direction == Direction.EAST) {
				for (int x = xS + rightSided; x <= (xL-1) + rightSided; x++) {
					int rng = RNG.nextInt(8);
					
					if (rng == 1 && checkHole(game) < hole)
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setType(Material.AIR);
					if (x == xS && y == yS || x == xL && y == yS) {
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setType(Material.STAINED_CLAY);
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setData((byte)(p.getWallColor()));
					}
				}
			}
			if (game.direction == Direction.WEST) {
				for (int x = xL - rightSided; x >= (xS+1) - rightSided; x--) {
					int rng = RNG.nextInt(8);
					
					if (rng == 1 && checkHole(game) < hole)
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setType(Material.AIR);
					if (x == xS && y == yS || x == xL && y == yS) {
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setType(Material.STAINED_CLAY);
						game.world.getBlockAt(x, y, (int) game.getWall().z0).setData((byte)(p.getWallColor()));
					}
				}
			}
	}
	
	@SuppressWarnings("deprecation")
	private static void generateZAxys(int y, HGame game, HPlayer p, int hole)  {
		int rightSided = p.isRightSided() ? 1 : 0;
		int zS = (int) (game.getWall().z0 < game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int zL = (int) (game.getWall().z0 > game.getWall().z1 ? game.getWall().z0 : game.getWall().z1);
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		
		if (game.direction == Direction.SOUTH) {
			for (int z = zS + rightSided; z <= (zL-1) + rightSided; z++) {
				int rng = RNG.nextInt(8);

				if (rng == 1 && checkHole(game) < hole)
					game.world.getBlockAt((int) game.getWall().x0, y, z).setType(Material.AIR);
				if (z == zS && y == yS || z == zL && y == yS) {
					game.world.getBlockAt((int) game.getWall().x0, y, z).setType(Material.STAINED_CLAY);
					game.world.getBlockAt((int) game.getWall().x0, y, z).setData((byte)(p.getWallColor()));
				}
			}
		}
		if (game.direction == Direction.NORTH) {
			for (int z = zL - rightSided; z >= (zS+1) - rightSided; z--) {
				int rng = RNG.nextInt(8);
				
				if (rng == 1 && checkHole(game) < hole)
					game.world.getBlockAt((int) game.getWall().x0, y, z).setType(Material.AIR);
				if (z == zS && y == yS || z == zL && y == yS) {
					game.world.getBlockAt((int) game.getWall().x0, y, z).setType(Material.STAINED_CLAY);
					game.world.getBlockAt((int) game.getWall().x0, y, z).setData((byte)(p.getWallColor()));
				}
			}
		}
	}
	
	public static void generateWall(HPlayer p, HGame game, boolean endGame) {
		if (endGame) return;
		cleanWall(game, p);
		
		int wall2 = (p.wall == 2 && game.isIncrementingHoles()) ? 0 : 1;
		
		int yS = (int) (game.getWall().y0 < game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		int yL = (int) (game.getWall().y0 > game.getWall().y1 ? game.getWall().y0 : game.getWall().y1);
		
		int hole = game.holes[8];
		
		if (game.isIncrementingHoles()) {
			switch(p.wall) {
				case 1: hole = game.holes[0]; break;
				case 2: hole = game.holes[1]; break;
				case 3: hole = game.holes[2]; break;
				case 4: hole = game.holes[3]; break;
				case 5: hole = game.holes[4]; break;
				case 6: hole = game.holes[5]; break;
				case 7: hole = game.holes[6]; break;
				case 8: hole = game.holes[7]; break;
				default: hole = game.holes[8]; break;
			}
		}
		
		while (checkHole(game) != hole) {
			for (int y = yS; y <= yL - wall2; y++) {
				if (game.direction == Direction.EAST || game.direction == Direction.WEST) {
					generateXAxys(y, game, p, hole);
				}
				if (game.direction == Direction.NORTH || game.direction == Direction.SOUTH) {
					generateZAxys(y, game, p, hole);
				}
			}
		}
	}
}
