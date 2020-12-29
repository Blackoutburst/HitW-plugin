package utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Manage in game ranks
 * @author Blackoutburst
 */
public class RankManager {
	
	/**
	 * Load ranks from Hypixel API
	 * @param player player who will get the rank
	 * @return the rank
	 * @author Blackoutburst
	 */
	public static String loadRank(Player player) {
		String[] value;
		String rank = "";
		int qualification = 0;
		int finals = 0;
		String output = Request.getPlayerInfo(player.getUniqueId().toString().replace("-", "").toString());
		
		value = output.split(",");
		for (int i = 0; i < value.length; i++) {
			if (value[i].contains("hitw_record_q")) {
				qualification = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
			if (value[i].contains("hitw_record_f")) {
				finals = Integer.valueOf(value[i].replaceAll("[^0-9]", ""));
			}
		}
		rank = getRank(player, qualification, finals);
		setDisplayName(player, rank + player.getName() + ChatColor.RESET);
		return (rank);
	}
	
	/**
	 * Get rank from hitW score
	 * @param player player who will get a rank
	 * @param qualification qualification score
	 * @param finals finals score
	 * @return rank name
	 * @author Blackoutburst
	 */
	private static String getRank(Player player, int qualification, int finals) {
		if (qualification >= 400 || finals >= 400) {
			return(ChatColor.DARK_AQUA + "[400+] ");
		} else if (qualification >= 375 || finals >= 375) {
			return(ChatColor.YELLOW + "[350"+ ChatColor.DARK_AQUA +"+"+ChatColor.YELLOW+"] ");
		} else if (qualification >= 350 || finals >= 350) {
			return(ChatColor.YELLOW + "[350+] ");
		} else if (qualification >= 325 || finals >= 325) {
			return(ChatColor.GOLD + "[300"+ ChatColor.YELLOW +"+"+ChatColor.GOLD+"] ");
		} else if (qualification >= 300 || finals >= 300) {
			return(ChatColor.GOLD + "[300+] ");
		} else if (qualification >= 275 || finals >= 275) {
			return(ChatColor.LIGHT_PURPLE + "[250"+ ChatColor.GOLD +"+"+ChatColor.LIGHT_PURPLE+"] ");
		} else if (qualification >= 250 || finals >= 250) {
			return(ChatColor.LIGHT_PURPLE + "[250+] ");
		} else if (qualification >= 225 || finals >= 225) {
			return(ChatColor.DARK_PURPLE + "[200"+ ChatColor.LIGHT_PURPLE +"+"+ChatColor.DARK_PURPLE+"] ");
		} else if (qualification >= 200 || finals >= 200) {
			return(ChatColor.DARK_PURPLE + "[200+] ");
		} else if (qualification >= 175 || finals >= 175) {
			return(ChatColor.RED + "[150"+ ChatColor.DARK_PURPLE +"+"+ChatColor.RED+"] ");
		} else if (qualification >= 150 || finals >= 150) {
			return(ChatColor.RED + "[150+] ");
		} else if (qualification >= 125 || finals >= 125) {
			return(ChatColor.DARK_RED + "[100"+ ChatColor.RED +"+"+ChatColor.DARK_RED+"] ");
		} else if (qualification >= 100 || finals >= 100) {
			return(ChatColor.DARK_RED + "[100+] ");
		} else if (qualification >= 75 || finals >= 75) {
			return(ChatColor.GREEN + "[50"+ ChatColor.DARK_RED +"+"+ChatColor.GREEN+"] ");
		} else if (qualification >= 50 || finals >= 50) {
			return(ChatColor.GREEN + "[50+] ");
		} else {
			return(ChatColor.DARK_GREEN+"");
		}
	}
	
	/**
	 * Change user name in chat and tab
	 * @param player player who get the rank
	 * @param str rank name
	 * @author Blackoutburst
	 */
	private static void setDisplayName(Player player, String str) {
		player.setDisplayName(str);
		player.setPlayerListName(str);
	}
}
