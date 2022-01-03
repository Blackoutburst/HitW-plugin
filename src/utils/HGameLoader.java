package utils;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import core.Area;
import core.Direction;
import core.HGame;
import main.Main;

public class HGameLoader {
	
	public static void loadHGames() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/walls.yml"));
		Set<String> walls = config.getConfigurationSection("games").getKeys(false);
		
		for (String index : walls) {
			World world = Bukkit.getWorld(config.getString("games."+index+".world"));
			Float posA[] = Utils.stringArrToFloatArr(config.getString("games."+index+".area").split(", "));
			Area area = new Area(posA[0], posA[1], posA[2], posA[3], posA[4], posA[5]);
			Float posP[] = Utils.stringArrToFloatArr(config.getString("games."+index+".play").split(", "));
			Area playfield = new Area(posP[0], posP[1], posP[2], posP[3], posP[4], posP[5]);
			Float posW[] = Utils.stringArrToFloatArr(config.getString("games."+index+".wall").split(", "));
			Area wall = new Area(posW[0], posW[1], posW[2], posW[3], posW[4], posW[5]);
			int holes[] = Utils.stringArrToIntArr(config.getString("games."+index+".holes").split(", "));
			String name = config.getString("games."+index+".name");
			Direction direction = null;
			switch(config.getString("games."+index+".direction")) {
				case "north": direction = Direction.NORTH; break;
				case "east": direction = Direction.EAST; break;
				case "south": direction = Direction.SOUTH; break;
				case "west": direction = Direction.WEST; break;
				default: continue;
			}
			Main.hGames.add(new HGame(world, direction, wall, area, playfield, name, holes, false));
		}
	}
}
