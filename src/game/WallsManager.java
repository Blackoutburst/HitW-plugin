package game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import main.GamePlayer;

/**
 * Manage walls function
 * @author Blackoutburst
 */
public class WallsManager {
	
	private static Random rand = new Random();
	public static World world = Bukkit.getWorld("world");
	
	/**
	 * Check the play field for correct block
	 * @param play play field
	 * @return score
	 * @author Blackoutburst
	 */
	public static int checkScore(int[] play) {
		int score = 0;
		
		int x1 = 0;int x2 = 3;if (play[3] < play[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (play[4] < play[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (play[5] < play[2]) {z1 = 5;z2 = 2;}
		
		for (int x = play[x1]; x <= play[x2]; x++) {
			for (int y = play[y1]; y <= play[y2]; y++) {
				for (int z = play[z1]; z <= play[z2]; z++) {
					if (world.getBlockAt(x, y, z).getType().equals(Material.STAINED_GLASS)) {
						score++;
					}
				}
			}
		}
		return score;
	}
	
	/**
	 * Check the amount of missing block in the wall
	 * @param play play field
	 * @return number of holes remaining
	 * @author Blackoutburst
	 */
	public static int checkHole(int[] play) {
		int hole = 0;
		
		int x1 = 0;int x2 = 3;if (play[3] < play[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (play[4] < play[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (play[5] < play[2]) {z1 = 5;z2 = 2;}
		
		for (int x = play[x1]; x <= play[x2]; x++) {
			for (int y = play[y1]; y <= play[y2]; y++) {
				for (int z = play[z1]; z <= play[z2]; z++) {
					if (world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
						hole++;
					}
				}
			}
		}
		return hole;
	}
	
	/**
	 * Pull a wall to the pay field
	 * and check misplaced blocks
	 * @param play play field
	 * @param wall wall
	 * @return number of misplaced blocks
	 * @author Blackoutburst
	 */
	@SuppressWarnings("deprecation")
	public static int pullWall(int[] play , int[] wall) {
		int misplaced = 0;
		int zAxys = 0;
		int xAxys = 0;
		
		if (play[2] > wall[2]) {zAxys = 1;} else {zAxys = -1;}
		if (play[0] > wall[0]) {xAxys = 1;} else {xAxys = -1;}
		
		boolean onZ = false;
		if (play[0] == wall[0] || play[0] == wall[3]) {
			onZ = true;
		}
		
		int x1 = 0;int x2 = 3;if (wall[3] < wall[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (wall[4] < wall[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (wall[5] < wall[2]) {z1 = 5;z2 = 2;}
		
		if (onZ) {
			for (int x = wall[x1]; x <= wall[x2]; x++) {
				for (int y = wall[y1]; y <= wall[y2]; y++) {
					for (int z = wall[z1]; z <= wall[z2]; z++) {
						Block b = world.getBlockAt(x, y, z);
						if (!b.getType().equals(Material.AIR)) {
							if (world.getBlockAt(x, y, play[2]).getType().equals(Material.STAINED_GLASS)) {
								world.getBlockAt(x, y, play[2] + zAxys).setType(Material.STAINED_GLASS);
								world.getBlockAt(x, y, play[2] + zAxys).setData((byte)(14));
								misplaced++;
							}
							world.getBlockAt(x, y, play[2]).setType(b.getType());
							world.getBlockAt(x, y, play[2]).setData(b.getData());
						}
					}
				}
			}
		} else {
			for (int x = wall[x1]; x <= wall[x2]; x++) {
				for (int y = wall[y1]; y <= wall[y2]; y++) {
					for (int z = wall[z1]; z <= wall[z2]; z++) {
						Block b = world.getBlockAt(x, y, z);
						if (!b.getType().equals(Material.AIR)) {
							if (world.getBlockAt(play[0], y, z).getType().equals(Material.STAINED_GLASS)) {
								world.getBlockAt(play[0] + xAxys, y, z).setType(Material.STAINED_GLASS);
								world.getBlockAt(play[0] + xAxys, y, z).setData((byte)(14));
								misplaced++;
							}
							world.getBlockAt(play[0], y, z).setType(b.getType());
							world.getBlockAt(play[0], y, z).setData(b.getData());
						}
					}
				}
			}
		}
		return (misplaced);
	}
	
	/**
	 * Clear the play field
	 * @param play play field
	 * @param wall wall
	 * @author Blackoutburst
	 */
	public static void clearPlayField(int[] play, int[] wall) {
		int zAxys = 0;
		int xAxys = 0;
		
		if (play[2] > wall[2]) {zAxys = 1;} else {zAxys = -1;}
		if (play[0] > wall[0]) {xAxys = 1;} else {xAxys = -1;}
		
		boolean onZ = false;
		if (play[0] == wall[0] || play[0] == wall[3]) {
			onZ = true;
		}
		
		int x1 = 0;int x2 = 3;if (play[3] < play[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (play[4] < play[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (play[5] < play[2]) {z1 = 5;z2 = 2;}
		
		if (onZ) {
			for (int x = play[x1]; x <= play[x2]; x++) {
				for (int y = play[y1]; y <= play[y2]; y++) {
					for (int z = play[z1]; z <= play[z2]; z++) {
						world.getBlockAt(x, y, z).setType(Material.AIR);
						world.getBlockAt(x, y, z+ zAxys).setType(Material.AIR);
					}
				}
			}
		} else {
			for (int x = play[x1]; x <= play[x2]; x++) {
				for (int y = play[y1]; y <= play[y2]; y++) {
					for (int z = play[z1]; z <= play[z2]; z++) {
						world.getBlockAt(x, y, z).setType(Material.AIR);
						world.getBlockAt(x + xAxys, y, z).setType(Material.AIR);
					}
				}
			}
		}
	}
	
	/**
	 * Reset the wall with quartz block
	 * @param wall 
	 * @author Blackoutburst
	 */
	public static void resetWall(int[] wall) {
		
		int x1 = 0;int x2 = 3;if (wall[3] < wall[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (wall[4] < wall[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (wall[5] < wall[2]) {z1 = 5;z2 = 2;}
		
		for (int x = wall[x1]; x <= wall[x2]; x++) {
			for (int y = wall[y1]; y <= wall[y2]; y++) {
				for (int z = wall[z1]; z <= wall[z2]; z++) {
					world.getBlockAt(x, y, z).setType(Material.QUARTZ_BLOCK);
				}
			}
		}
	}
	
	/**
	 * Generate a new wall
	 * @param play play field
	 * @param wall wall
	 * @param hole number of hole to put in the wall
	 * @param player current player playing
	 * @author Blackoutburst
	 */
	@SuppressWarnings("deprecation")
	public static void genWall(int[] play, int[] wall, int hole, GamePlayer player) {
		int wall2 = 1;
		
		boolean onZ = false;
		if (play[0] == wall[0] || play[0] == wall[3]) {
			onZ = true;
		}
		
		int x1 = 0;int x2 = 3;if (wall[3] < wall[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (wall[4] < wall[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (wall[5] < wall[2]) {z1 = 5;z2 = 2;}
		
		if (player.getWalls() == 2) {
			if (player.isInClassicGame()) {
				wall2 = 0;
			}
		}
		
		for (int x = wall[x1]; x <= wall[x2]; x++) {
			for (int y = wall[y1]; y <= wall[y2]; y++) {
				for (int z = wall[z1]; z <= wall[z2]; z++) {
					world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
					world.getBlockAt(x, y, z).setData((byte)(player.getWallColor()));
				}
			}
		}
			
		if (onZ) {
			while (checkHole(wall) != hole) {
				for (int x = wall[x2]-1; x >= wall[x1]; x--) {
					for (int y = wall[y1]; y <= wall[y2]-wall2; y++) {
						for (int z = wall[z1]; z <= wall[z2]; z++) {
							int r = rand.nextInt(2);
							if (r == 1 && checkHole(wall) < hole) {
								world.getBlockAt(x, y, z).setType(Material.AIR);
							} else if (!world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
								world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
								world.getBlockAt(x, y, z).setData((byte)(player.getWallColor()));
							}
							if (world.getBlockAt(wall[0], wall[1], wall[2]).getType().equals(Material.AIR)) {
								world.getBlockAt(wall[0], wall[1], wall[2]).setType(Material.STAINED_CLAY);
								world.getBlockAt(wall[0], wall[1], wall[2]).setData((byte)(player.getWallColor()));
							}
							if (world.getBlockAt(wall[3], wall[1], wall[2]).getType().equals(Material.AIR)) {
								world.getBlockAt(wall[3], wall[1], wall[2]).setType(Material.STAINED_CLAY);
								world.getBlockAt(wall[3], wall[1], wall[2]).setData((byte)(player.getWallColor()));
							}
						}
					}
				}
			}
		} else {
			while (checkHole(wall) != hole) {
				for (int x = wall[x1]; x <= wall[x2]; x++) {
					for (int y = wall[y1]; y <= wall[y2]-wall2; y++) {
						for (int z = wall[z2]-1; z >= wall[z1]; z--) {
							int r = rand.nextInt(2);
							if (r == 1 && checkHole(wall) < hole) {
								world.getBlockAt(x, y, z).setType(Material.AIR);
							} else if (!world.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
								world.getBlockAt(x, y, z).setType(Material.STAINED_CLAY);
								world.getBlockAt(x, y, z).setData((byte)(player.getWallColor()));
							}
							if (world.getBlockAt(wall[0], wall[1], wall[2]).getType().equals(Material.AIR)) {
								world.getBlockAt(wall[0], wall[1], wall[2]).setType(Material.STAINED_CLAY);
								world.getBlockAt(wall[0], wall[1], wall[2]).setData((byte)(player.getWallColor()));
							}
							if (world.getBlockAt(wall[3], wall[1], wall[2]).getType().equals(Material.AIR)) {
								world.getBlockAt(wall[3], wall[1], wall[2]).setType(Material.STAINED_CLAY);
								world.getBlockAt(wall[3], wall[1], wall[2]).setData((byte)(player.getWallColor()));
							}
						}
					}
				}
			}
		}
	}
}
