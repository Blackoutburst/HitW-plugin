package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import main.Game;

/**
 * Add game value
 * will be replaced with .yml files later
 * @author Blackoutburst
 */
public class Values {
	private static YamlConfiguration locationFile;
	private static YamlConfiguration wallsFile;
	public static List<Game> games = new ArrayList<Game>();
	public static int[] colors = new int[] {-588, 6, 690, -580, 11, 715};
	public static Location spawn = new Location(null, 0, 0, 0);
	public static Location color = new Location(null, 0, 0, 0);
	
	public static Location specQ = new Location(null, 0, 0, 0);
	public static Location player1Q = new Location(null, 0, 0, 0);
	public static Location player2Q = new Location(null, 0, 0, 0);
	public static Location cam1Q = new Location(null, 0, 0, 0);
	public static Location cam2Q = new Location(null, 0, 0, 0);
	
	public static Location specF = new Location(null, 0, 0, 0);
	public static Location player1F = new Location(null, 0, 0, 0);
	public static Location player2F = new Location(null, 0, 0, 0);
	public static Location cam1F = new Location(null, 0, 0, 0);
	public static Location cam2F = new Location(null, 0, 0, 0);
	
	/**
	 * Initialize / reload all plugins value
	 * @author Blackoutburst
	 */
	public static void initValue() {
		games.clear();
		locationFile = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/locations.yml"));
		wallsFile = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/walls.yml"));
		loadLocation();
		int walls = Integer.valueOf(wallsFile.getString("count"));
		
		
		for (int i = 0; i < walls; i++) {
			String[] strArea = wallsFile.getString(i+".area").split(", ");
			String[] strPlay = wallsFile.getString(i+".play").split(", ");
			String[] strWall = wallsFile.getString(i+".wall").split(", ");
			String[] strHoles = wallsFile.getString(i+".holes").split(", ");
			int[] area = new int[] {Integer.valueOf(strArea[0]), Integer.valueOf(strArea[1]), 
									Integer.valueOf(strArea[2]), Integer.valueOf(strArea[3]), 
									Integer.valueOf(strArea[4]), Integer.valueOf(strArea[5])};
			int[] play = new int[] {Integer.valueOf(strPlay[0]), Integer.valueOf(strPlay[1]), 
									Integer.valueOf(strPlay[2]), Integer.valueOf(strPlay[3]), 
									Integer.valueOf(strPlay[4]), Integer.valueOf(strPlay[5])};
			int[] wall = new int[] {Integer.valueOf(strWall[0]), Integer.valueOf(strWall[1]), 
									Integer.valueOf(strWall[2]), Integer.valueOf(strWall[3]), 
									Integer.valueOf(strWall[4]), Integer.valueOf(strWall[5])};
			int[] holes = new int[] {Integer.valueOf(strHoles[0]), Integer.valueOf(strHoles[1]), 
									Integer.valueOf(strHoles[2]), Integer.valueOf(strHoles[3]), 
									Integer.valueOf(strHoles[4]), Integer.valueOf(strHoles[5]),
									Integer.valueOf(strHoles[6]), Integer.valueOf(strHoles[7]), Integer.valueOf(strHoles[8])};
			games.add(new Game(area, play, wall, holes, false, false, 0, wallsFile.getString(i+".type")));
		}
	}
	
	/**
	 * Load every location
	 * @author Blackoutburst
	 */
	private static void loadLocation() {
		spawn.setWorld(Bukkit.getWorld(locationFile.getString("spawn.world")));
		spawn.setX(Float.valueOf(locationFile.getString("spawn.x")));
		spawn.setY(Float.valueOf(locationFile.getString("spawn.y")));
		spawn.setZ(Float.valueOf(locationFile.getString("spawn.z")));
		spawn.setYaw(Float.valueOf(locationFile.getString("spawn.yaw")));
		spawn.setPitch(Float.valueOf(locationFile.getString("spawn.pitch")));
		
		color.setWorld(Bukkit.getWorld(locationFile.getString("color.world")));
		color.setX(Float.valueOf(locationFile.getString("color.x")));
		color.setY(Float.valueOf(locationFile.getString("color.y")));
		color.setZ(Float.valueOf(locationFile.getString("color.z")));
		color.setYaw(Float.valueOf(locationFile.getString("color.yaw")));
		color.setPitch(Float.valueOf(locationFile.getString("color.pitch")));
		
		specQ.setWorld(Bukkit.getWorld(locationFile.getString("specQ.world")));
		specQ.setX(Float.valueOf(locationFile.getString("specQ.x")));
		specQ.setY(Float.valueOf(locationFile.getString("specQ.y")));
		specQ.setZ(Float.valueOf(locationFile.getString("specQ.z")));
		specQ.setYaw(Float.valueOf(locationFile.getString("specQ.yaw")));
		specQ.setPitch(Float.valueOf(locationFile.getString("specQ.pitch")));
		
		player1Q.setWorld(Bukkit.getWorld(locationFile.getString("player1Q.world")));
		player1Q.setX(Float.valueOf(locationFile.getString("player1Q.x")));
		player1Q.setY(Float.valueOf(locationFile.getString("player1Q.y")));
		player1Q.setZ(Float.valueOf(locationFile.getString("player1Q.z")));
		player1Q.setYaw(Float.valueOf(locationFile.getString("player1Q.yaw")));
		player1Q.setPitch(Float.valueOf(locationFile.getString("player1Q.pitch")));
		
		player2Q.setWorld(Bukkit.getWorld(locationFile.getString("player2Q.world")));
		player2Q.setX(Float.valueOf(locationFile.getString("player2Q.x")));
		player2Q.setY(Float.valueOf(locationFile.getString("player2Q.y")));
		player2Q.setZ(Float.valueOf(locationFile.getString("player2Q.z")));
		player2Q.setYaw(Float.valueOf(locationFile.getString("player2Q.yaw")));
		player2Q.setPitch(Float.valueOf(locationFile.getString("player2Q.pitch")));
		
		cam1Q.setWorld(Bukkit.getWorld(locationFile.getString("cam1Q.world")));
		cam1Q.setX(Float.valueOf(locationFile.getString("cam1Q.x")));
		cam1Q.setY(Float.valueOf(locationFile.getString("cam1Q.y")));
		cam1Q.setZ(Float.valueOf(locationFile.getString("cam1Q.z")));
		cam1Q.setYaw(Float.valueOf(locationFile.getString("cam1Q.yaw")));
		cam1Q.setPitch(Float.valueOf(locationFile.getString("cam1Q.pitch")));
		
		cam2Q.setWorld(Bukkit.getWorld(locationFile.getString("cam2Q.world")));
		cam2Q.setX(Float.valueOf(locationFile.getString("cam2Q.x")));
		cam2Q.setY(Float.valueOf(locationFile.getString("cam2Q.y")));
		cam2Q.setZ(Float.valueOf(locationFile.getString("cam2Q.z")));
		cam2Q.setYaw(Float.valueOf(locationFile.getString("cam2Q.yaw")));
		cam2Q.setPitch(Float.valueOf(locationFile.getString("cam2Q.pitch")));
		
		specF.setWorld(Bukkit.getWorld(locationFile.getString("specF.world")));
		specF.setX(Float.valueOf(locationFile.getString("specF.x")));
		specF.setY(Float.valueOf(locationFile.getString("specF.y")));
		specF.setZ(Float.valueOf(locationFile.getString("specF.z")));
		specF.setYaw(Float.valueOf(locationFile.getString("specF.yaw")));
		specF.setPitch(Float.valueOf(locationFile.getString("specF.pitch")));
		
		player1F.setWorld(Bukkit.getWorld(locationFile.getString("player1F.world")));
		player1F.setX(Float.valueOf(locationFile.getString("player1F.x")));
		player1F.setY(Float.valueOf(locationFile.getString("player1F.y")));
		player1F.setZ(Float.valueOf(locationFile.getString("player1F.z")));
		player1F.setYaw(Float.valueOf(locationFile.getString("player1F.yaw")));
		player1F.setPitch(Float.valueOf(locationFile.getString("player1F.pitch")));
		
		player2F.setWorld(Bukkit.getWorld(locationFile.getString("player2F.world")));
		player2F.setX(Float.valueOf(locationFile.getString("player2F.x")));
		player2F.setY(Float.valueOf(locationFile.getString("player2F.y")));
		player2F.setZ(Float.valueOf(locationFile.getString("player2F.z")));
		player2F.setYaw(Float.valueOf(locationFile.getString("player2F.yaw")));
		player2F.setPitch(Float.valueOf(locationFile.getString("player2F.pitch")));
		
		cam1F.setWorld(Bukkit.getWorld(locationFile.getString("cam1F.world")));
		cam1F.setX(Float.valueOf(locationFile.getString("cam1F.x")));
		cam1F.setY(Float.valueOf(locationFile.getString("cam1F.y")));
		cam1F.setZ(Float.valueOf(locationFile.getString("cam1F.z")));
		cam1F.setYaw(Float.valueOf(locationFile.getString("cam1F.yaw")));
		cam1F.setPitch(Float.valueOf(locationFile.getString("cam1F.pitch")));
		
		cam2F.setWorld(Bukkit.getWorld(locationFile.getString("cam2F.world")));
		cam2F.setX(Float.valueOf(locationFile.getString("cam2F.x")));
		cam2F.setY(Float.valueOf(locationFile.getString("cam2F.y")));
		cam2F.setZ(Float.valueOf(locationFile.getString("cam2F.z")));
		cam2F.setYaw(Float.valueOf(locationFile.getString("cam2F.yaw")));
		cam2F.setPitch(Float.valueOf(locationFile.getString("cam2F.pitch")));
	}
}
