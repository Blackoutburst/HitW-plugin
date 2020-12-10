package utils;

import java.util.List;

import org.bukkit.Location;

import main.Game;

/**
 * This class contains every function needed
 * to check if a player is inside a specific area
 * @author Blackoutburst
 */
public class InsideArea {

	/**
	 * Get the area ID from every possible game area
	 * @param location player location in the world
	 * @param games all games existing
	 * @return game area ID or -1 if the player is not inside any game area
	 * @author Blackoutburst
	 */
	public static int inGameAreaID(Location location, List<Game> games) {
		int id = 0;
		
		for (Game game : games) {
			int x1 = 0;int x2 = 3;if (game.getArea()[3] < game.getArea()[0]) {x1 = 3;x2 = 0;}
			int y1 = 1;int y2 = 4;if (game.getArea()[4] < game.getArea()[1]) {y1 = 4;y2 = 1;}
			int z1 = 2;int z2 = 5;if (game.getArea()[5] < game.getArea()[2]) {z1 = 5;z2 = 2;}
			
			if (location.getX() >= game.getArea()[x1]  && location.getX() <= game.getArea()[x2] &&
				location.getY() >= game.getArea()[y1]  && location.getY() <= game.getArea()[y2] &&
				location.getZ() >= game.getArea()[z1]  && location.getZ() <= game.getArea()[z2]) {
				return id;
			}
			id++;
		}
		return -1;
	}
	
	/**
	 * Check if a player is inside a specific area or not
	 * @param location the player location in the world
	 * @param area the current area
	 * @return if the player is inside the area or not
	 * @author Blackoutburst
	 */
	public static boolean playerInside(Location location, int[] area) {
		int x1 = 0;int x2 = 3;if (area[3] < area[0]) {x1 = 3;x2 = 0;}
		int y1 = 1;int y2 = 4;if (area[4] < area[1]) {y1 = 4;y2 = 1;}
		int z1 = 2;int z2 = 5;if (area[5] < area[2]) {z1 = 5;z2 = 2;}
		
		if (location.getX() >= area[x1]  && location.getX() <= area[x2] &&
			location.getY() >= area[y1]  && location.getY() <= area[y2] &&
			location.getZ() >= area[z1]  && location.getZ() <= area[z2]) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the player is inside every game area
	 * @param location the player location in the world
	 * @param games all possible games
	 * @return if the player is inside a game area or not
	 * @author Blackoutburst
	 */
	public static boolean inGameArea(Location location, List<Game> games) {
		for (Game game : games) {
			
			int x1 = 0;int x2 = 3;if (game.getArea()[3] < game.getArea()[0]) {x1 = 3;x2 = 0;}
			int y1 = 1;int y2 = 4;if (game.getArea()[4] < game.getArea()[1]) {y1 = 4;y2 = 1;}
			int z1 = 2;int z2 = 5;if (game.getArea()[5] < game.getArea()[2]) {z1 = 5;z2 = 2;}
			
			if (location.getX() >= game.getArea()[x1]  && location.getX() <= game.getArea()[x2] &&
				location.getY() >= game.getArea()[y1]  && location.getY() <= game.getArea()[y2] &&
				location.getZ() >= game.getArea()[z1]  && location.getZ() <= game.getArea()[z2]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the block is inside the play field
	 * @param location block location in the world
	 * @param games all possible games play field
	 * @return if the block is inside the play field or not
	 * @author Blackoutburst
	 */
	public static boolean inPlayArea(Location location, List<Game> games) {
		for (Game game : games) {
			
			int x1 = 0;int x2 = 3;if (game.getPlay()[3] < game.getPlay()[0]) {x1 = 3;x2 = 0;}
			int y1 = 1;int y2 = 4;if (game.getPlay()[4] < game.getPlay()[1]) {y1 = 4;y2 = 1;}
			int z1 = 2;int z2 = 5;if (game.getPlay()[5] < game.getPlay()[2]) {z1 = 5;z2 = 2;}
			
			if (location.getX() >= game.getPlay()[x1]  && location.getX() <= game.getPlay()[x2] &&
				location.getY() >= game.getPlay()[y1]  && location.getY() <= game.getPlay()[y2] &&
				location.getZ() >= game.getPlay()[z1]  && location.getZ() <= game.getPlay()[z2]) {
				return true;
			}
		}
		return false;
	}
	
}
