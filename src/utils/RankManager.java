package utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankManager {
	
	public static String loadRank(Player player) {
		String[] value;
		String rank = "";
		int qualification = 0;
		int finals = 0;
		
		if (player.getUniqueId().toString().replace("-", "").toString().equals("9293868b414c42b2bd8e3bcb791247b9")) {
			rank = ChatColor.DARK_GRAY+"["+ChatColor.DARK_RED+"Yaku"+ChatColor.DARK_GRAY+"] "+ChatColor.DARK_RED;
			setDisplayName(player, rank + player.getName() + ChatColor.RESET);
			return (rank);
		}
		
		if (player.getUniqueId().toString().replace("-", "").toString().equals("6e8e9eb5f45e4e058362d02f92c1da72")) {
			rank = ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"0"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY;
			setDisplayName(player, rank + player.getName() + ChatColor.RESET);
			return (rank);
		}
		
		if (player.getUniqueId().toString().replace("-", "").toString().equals("5c8e599738764d8081cc4f1b81e16209")) {
			rank = ChatColor.DARK_GRAY+"["+ChatColor.GRAY+"0"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY;
			setDisplayName(player, rank + player.getName() + ChatColor.RESET);
			return (rank);
		}
		
		String output = Request.getPlayerInfo(player.getUniqueId().toString().replace("-", "").toString());
		
		if (output == null) {
			
			rank = ChatColor.GRAY+"["+ChatColor.RED+"API"+ChatColor.GRAY+"]"+ChatColor.ITALIC;
			
			setDisplayName(player, rank + player.getName() + ChatColor.RESET);
			return(rank);
		}
		
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
	
	private static String getRank(Player player, int qualification, int finals) {
		if (qualification >= 500 || finals >= 500) {
			return(ChatColor.DARK_GREEN + "[500+] ");
			
		} else if (qualification >= 475 || finals >= 475) {
			return(ChatColor.GREEN + "[450"+ ChatColor.DARK_GREEN +"+"+ChatColor.GREEN+"] ");
			
		} else if (qualification >= 450 || finals >= 450) {
			return(ChatColor.GREEN + "[450+] ");
			
		} else if (qualification >= 425 || finals >= 425) {
			return(ChatColor.DARK_AQUA + "[400"+ ChatColor.GREEN +"+"+ChatColor.DARK_AQUA+"] ");
			
		} else if (qualification >= 400 || finals >= 400) {
			return(ChatColor.DARK_AQUA + "[400+] ");
			
		} else if (qualification >= 375 || finals >= 375) {
			return(ChatColor.BLUE + "[350"+ ChatColor.DARK_AQUA +"+"+ChatColor.BLUE+"] ");
			
		} else if (qualification >= 350 || finals >= 350) {
			return(ChatColor.BLUE + "[350+] ");
			
		} else if (qualification >= 325 || finals >= 325) {
			return(ChatColor.DARK_PURPLE + "[300"+ ChatColor.BLUE +"+"+ChatColor.DARK_PURPLE+"] ");
			
		} else if (qualification >= 300 || finals >= 300) {
			return(ChatColor.DARK_PURPLE + "[300+] ");
			
		} else if (qualification >= 275 || finals >= 275) {
			return(ChatColor.LIGHT_PURPLE + "[250"+ ChatColor.DARK_PURPLE +"+"+ChatColor.LIGHT_PURPLE+"] ");
			
		} else if (qualification >= 250 || finals >= 250) {
			return(ChatColor.LIGHT_PURPLE + "[250+] ");
			
		} else if (qualification >= 225 || finals >= 225) {
			return(ChatColor.DARK_RED + "[200"+ ChatColor.LIGHT_PURPLE +"+"+ChatColor.DARK_RED+"] ");
			
		} else if (qualification >= 200 || finals >= 200) {
			return(ChatColor.DARK_RED + "[200+] ");
			
		} else if (qualification >= 175 || finals >= 175) {
			return(ChatColor.RED + "[150"+ ChatColor.DARK_RED +"+"+ChatColor.RED+"] ");
			
		} else if (qualification >= 150 || finals >= 150) {
			return(ChatColor.RED + "[150+] ");
			
		} else if (qualification >= 125 || finals >= 125) {
			return(ChatColor.GOLD + "[100"+ ChatColor.RED +"+"+ChatColor.GOLD+"] ");
			
		} else if (qualification >= 100 || finals >= 100) {
			return(ChatColor.GOLD + "[100+] ");
			
		} else if (qualification >= 75 || finals >= 75) {
			return(ChatColor.YELLOW + "[50"+ ChatColor.GOLD +"+"+ChatColor.YELLOW+"] ");
			
		} else if (qualification >= 50 || finals >= 50) {
			return(ChatColor.YELLOW + "[50+] ");
			
		} else {
			return(ChatColor.GRAY+"");
		}
	}
	
	private static void setDisplayName(Player player, String str) {
		player.setDisplayName(str);
		player.setPlayerListName(str);
	}
}