package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.GamePlayer;
import main.Main;

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
	public static void writePlayerData(File f, int wall, int glass, float delay, boolean title, float memtime) {
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
				config.set("memtime", memtime);
				config.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Show count down before a game
	 * @param player
	 * @param seconds
	 * @author Blackoutburst
	 */
	public static void showCountDown(GamePlayer player, int seconds) {
		if (player.showTitle()) {
			new TitleManager().send(player.getPlayer(), "", "§aThe game will start in "+seconds+" seconds!", 0, 20, 0);
		} else {
			player.getPlayer().sendMessage("§aThe game will start in "+seconds+" seconds!");
		}
			
		for (int i = seconds - 1; i > 0; i--) {
			final int index = i;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				@Override
				public void run(){
					if (player.showTitle()) {
						new TitleManager().send(player.getPlayer(), getCountdownNumber(index), "", 0, 20, 0);
					} else {
						player.getPlayer().sendMessage(getCountdownNumber(index));
					}
				}
			}, (20L * (seconds - i)));
		}
	}
	
	private static String getCountdownNumber(int index) {
		switch(index) {
		case 5: return "§a5";
		case 4: return "§24";
		case 3: return "§e3";
		case 2: return "§62";
		case 1: return "§c1";
		case 0: return "§40";
		default: return "§40";
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
