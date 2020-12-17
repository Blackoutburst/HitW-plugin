package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * Small function with huge utility
 * @author Blackoutburst
 */
public class Tools {
	
	/**
	 * Read a file first line content and return it
	 * @param file file path you want to read
	 * @return file content
	 * @author Blackoutburst
	 */
	public static String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
		}
		return str;
	}
	
	/**
	 * Write new player data file
	 * 
	 * @param f new player folder
	 * @author Blackoutburst
	 */
	public static void writePlayerData(File f, int wall, int glass, float delay, boolean title) {
		File data = new File(f+"/config.yml");
		try {
			if (!data.exists()) {
				data.createNewFile();
			}
				YamlConfiguration config = YamlConfiguration.loadConfiguration(data);
				config.set("colors", null);
				config.set("colors.wall", wall);
				config.set("colors.glass", glass);
				config.set("delay", delay);
				config.set("title", title);
				config.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return player data folder path
	 * @param player
	 * @return player data folder path
	 */
	public static String getPlayerFolder(Player player) {
		return ("./plugins/HitW/player data/"+player.getUniqueId().toString().replace("-", ""));
	}
}
