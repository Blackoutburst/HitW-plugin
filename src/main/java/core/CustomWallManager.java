package core;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import main.Main;
import utils.GameUtils;
import utils.Utils;

public class CustomWallManager {

	private static final Random RNG = new Random();

	private static void clearHider(HGame game) {
		final int north = game.direction == Direction.NORTH ? 1 : 0;
		final int south = game.direction == Direction.SOUTH ? 1 : 0;
		final int west = game.direction == Direction.WEST ? 1 : 0;
		final int east = game.direction == Direction.EAST ? 1 : 0;

		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX() + north - south;
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ() - west + east;

			game.world.getBlockAt(x, y, z).setType(Material.AIR);
		}
	}

	public static void hideWall(HPlayer p, HGame game) {
		long delay = 0;

		if (p.isInParty()) {
			delay = (long) (20L * p.getParty().get(0).getMemTime());
		} else {
			delay = (long) (20L * p.getMemTime());
		}

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@SuppressWarnings("deprecation")
			@Override
			public void run(){
				final int north = game.direction == Direction.NORTH ? 1 : 0;
				final int south = game.direction == Direction.SOUTH ? 1 : 0;
				final int west = game.direction == Direction.WEST ? 1 : 0;
				final int east = game.direction == Direction.EAST ? 1 : 0;

				for(Location loc : game.getCustomWall().locations) {
					final int x = loc.getBlockX() + north - south;
					final int y = loc.getBlockY();
					final int z = loc.getBlockZ() - west + east;

					game.world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
					game.world.getBlockAt(x, y, z).setData((byte)(p.getWallColor()));
				}

				final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;

				if (p.isInParty()) {
					for (HPlayer hp : p.getParty()) {
						ItemStack stack;
						if (hp.isInvisibleGlass()) {
							stack = new ItemStack(Material.BARRIER, 50);
						} else {
							stack = new ItemStack(Material.STAINED_GLASS, 50, hp.getGlassColor());
						}
						hp.getPlayer().getInventory().setItem(0, stack);
						hp.setWallBegin(Instant.now());
					}
				} else {
					ItemStack stack;
					if (p.isInvisibleGlass()) {
						stack = new ItemStack(Material.BARRIER, 50);
					} else {
						stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
					}
					p.getPlayer().getInventory().setItem(0, stack);
					p.setWallBegin(Instant.now());
				}
				if (leader.isDestroy()) {
					resetPlayField(game, p);
					fillPlayField(game, p);
				}
			}
		}, (delay));
	}

	private static void delayedAction(HPlayer p, HGame game, boolean endGame) {
		if (endGame) return;

		long delay = 10L;

		if (game.getName().equals("1x1") || game.getName().equals("3x1") || game.getName().equals("3x3"))
			delay = 0L;

		if (!game.isIncrementingHoles()) {
			if (p.isInParty()) {
				delay = (long) (20 * p.getParty().get(0).getLeverDelay());
			} else {
				delay = (long) (20 * p.getLeverDelay());
			}
		}

		final HPlayer leader = p.isInParty() ? p.getParty().get(0) : p;

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				if (game.owner == null)
					return;
				if (leader.isDestroy()) {
					resetPlayField(game, p);
					fillPlayField(game, p);
				} else {
					resetPlayField(game, p);
				}
				if (leader.isBlind()) {
					hideWall(p, game);
				} else {
					if (p.isInParty()) {
						for (HPlayer hp : p.getParty()) {
							ItemStack stack;
							if (hp.isInvisibleGlass()) {
								stack = new ItemStack(Material.BARRIER, 50);
							} else {
								stack = new ItemStack(Material.STAINED_GLASS, 50, hp.getGlassColor());
							}
							hp.getPlayer().getInventory().setItem(0, stack);
							hp.setWallBegin(Instant.now());
						}
					} else {
						ItemStack stack;
						if (p.isInvisibleGlass()) {
							stack = new ItemStack(Material.BARRIER, 50);
						} else {
							stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
						}
						p.getPlayer().getInventory().setItem(0, stack);
						p.setWallBegin(Instant.now());
					}
				}
			}
		}, (delay));

		delay = 30L;

		if (game.getName().equals("1x1") || game.getName().equals("3x1") || game.getName().equals("3x3"))
			delay = 0L;

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

		for(Location loc : game.getCustomPlayfield().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
				missed++;
			}
			if ((west == 1 || east == 1) && game.world.getBlockAt(x, y, z+east-west).getType().equals(Material.STAINED_GLASS)) {
				misplaced++;
			}
			if ((south == 1 || north == 1) && game.world.getBlockAt(x-south+north, y, z).getType().equals(Material.STAINED_GLASS)) {
				misplaced++;
			}
		}

		int score = checkHole(game) - misplaced - missed;

		if (missed != 0)
			score--;

		if (score < 0)
			score = 0;

		if (missed == 0 && misplaced == 0 && score == 0) {
			resetPlayField(game, p);
			return;
		}

		final boolean bufferWall = Float.valueOf(missed) / Float.valueOf(checkHole(game)) > 0.4 && p.wall < 8;

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

		int index = 0;
		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			final int playFieldZ = game.getCustomPlayfield().locations.get(index).getBlockZ();
			final int playFieldX = game.getCustomPlayfield().locations.get(index).getBlockX();
			index++;

			if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) continue;

			if (west == 1 || east == 1) {
				if (game.world.getBlockAt(playFieldX, y, playFieldZ).getType().equals(Material.STAINED_GLASS)) {
					game.world.getBlockAt(playFieldX, y, playFieldZ+east-west).setType(Material.STAINED_GLASS);
					game.world.getBlockAt(playFieldX, y, playFieldZ+east-west).setData((byte)(14));
				}
				game.world.getBlockAt(playFieldX, y, playFieldZ).setType(game.world.getBlockAt(x, y, z).getType());
				game.world.getBlockAt(playFieldX, y, playFieldZ).setData(game.world.getBlockAt(x, y, z).getData());
			}
			if (south == 1 || north == 1) {
				if (game.world.getBlockAt(playFieldX, y, playFieldZ).getType().equals(Material.STAINED_GLASS)) {
					game.world.getBlockAt(playFieldX-south+north, y, playFieldZ).setType(Material.STAINED_GLASS);
					game.world.getBlockAt(playFieldX-south+north, y, playFieldZ).setData((byte)(14));
				}
				game.world.getBlockAt(playFieldX, y, playFieldZ).setType(game.world.getBlockAt(x, y, z).getType());
				game.world.getBlockAt(playFieldX, y, playFieldZ).setData(game.world.getBlockAt(x, y, z).getData());
			}
		}

		for(Location loc : game.getCustomPlayfield().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			if (game.world.getBlockAt(x, y, z).getType().equals(Material.STAINED_GLASS)) {
				game.world.getBlockAt(x, y, z).setData((byte)(13));
			}
		}

		calulateScore(p, game, endGame, leverTrigger);
	}

	private static int checkHole(HGame game) {
		int hole = 0;

		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			if (game.world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
				hole++;
			}
		}

		return hole;
	}

	@SuppressWarnings("deprecation")
	public static void fillPlayField(HGame game, HPlayer p) {
		for(Location loc : game.getCustomPlayfield().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			game.world.getBlockAt(x, y, z).setType(Material.STAINED_GLASS);
			game.world.getBlockAt(x, y, z).setData((byte) p.getGlassColor());
		}
	}

	public static void resetPlayField(HGame game, HPlayer p) {
		int north = game.direction == Direction.NORTH ? 1 : 0;
		int south = game.direction == Direction.SOUTH ? 1 : 0;
		int west = game.direction == Direction.WEST ? 1 : 0;
		int east = game.direction == Direction.EAST ? 1 : 0;

		for(Location loc : game.getCustomPlayfield().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			game.world.getBlockAt(x, y, z).setType(Material.AIR);
			game.world.getBlockAt(x + north - south, y, z - west + east).setType(Material.AIR);
		}

		game.setWallPulled(false);
	}

	public static void resetWall(HGame game) {
		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			game.world.getBlockAt(x, y, z).setType(Material.QUARTZ_BLOCK);
		}

		clearHider(game);
	}

	@SuppressWarnings("deprecation")
	private static void cleanWall(HGame game, HPlayer p) {
		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			game.world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
			game.world.getBlockAt(x, y, z).setData((byte)(p.getWallColor()));
		}

		clearHider(game);
	}

	private static void generate(HGame game, HPlayer p, int hole)  {
		for(Location loc : game.getCustomWall().locations) {
			final int x = loc.getBlockX();
			final int y = loc.getBlockY();
			final int z = loc.getBlockZ();

			int rng = RNG.nextInt(8);

			if (rng == 1 && checkHole(game) < hole)
				game.world.getBlockAt(x, y, z).setType(Material.AIR);
		}
	}


	public static void generateWall(HPlayer p, HGame game, boolean endGame) {
		if (endGame) return;
		cleanWall(game, p);

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
			generate(game, p, hole);
		}
	}
}