package utils;

import java.time.Instant;

import core.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import main.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class GameUtils {

	private static void prepareGameStartData(HPlayer p, HGame game) {
		p.getPlayer().getInventory().clear();
		ItemStack stack;
		if (p.isInvisibleGlass()) {
			stack = new ItemStack(Material.BARRIER, 50);
		} else {
			stack = new ItemStack(Material.STAINED_GLASS, 50, p.getGlassColor());
		}
		p.getPlayer().getInventory().setItem(0, stack);
		p.setWallBegin(Instant.now());
		p.setInGame(true, game);
	}
	
	public static void prepareGameStart(HPlayer p, HGame game) {
		if (p.isInParty())
			for (HPlayer hp : p.getParty())
				prepareGameStartData(hp, game);
		else
			prepareGameStartData(p, game);
	}
	
	public static void gameOptions(HPlayer leader, HPlayer p, HGame game) {
		if (game.isCustomGame()) {
			CustomWallManager.resetPlayField(game, p);
		} else {
			WallManager.resetPlayField(game, p, true);
		}
		
		if (leader.isDestroy() && !leader.isBlind()) {
			if (game.isCustomGame()) {
				CustomWallManager.fillPlayField(game, p);
			} else {
				WallManager.fillPlayField(game, p);
			}
		}
		
		if (leader.isBlind()) {
			if (game.isCustomGame()) {
				CustomWallManager.hideWall(p, game);
			} else {
				WallManager.hideWall(p, game);
			}
		}
		
		if (game.isCustomGame()) {
			CustomWallManager.generateWall(p, game, false);
		} else {
			WallManager.generateWall(p, game, false);
		}
	}
	public static void setCountdown(HPlayer p, HGame game, int sec) {
		if (p.isInParty())
			for (HPlayer hp : p.getParty())
				GameUtils.displayCountdown(hp, sec, game);
		else
			GameUtils.displayCountdown(p, sec, game);
	}
	
	private static void preparePlayersData(HPlayer p) {
		p.setPerfectWall(0);
		p.setWall(1);
		p.setChoke(0);
		p.setMisplaced(0);
		p.setMissed(0);
		p.setScore(0);
		p.setWallBegin(Instant.now());
		p.getWallTime().clear();
	}
	
	public static void preparePlayers(HPlayer p) {
		if (p.isInParty())
			for (HPlayer hp : p.getParty())
				preparePlayersData(hp);
		else
			preparePlayersData(p);
	}
	
	public static String getCountdownNumber(int index) {
		switch(index) {
			case 10: return "§c10";
			case 9: return "§c9";
			case 8: return "§c8";
			case 7: return "§c7";
			case 6: return "§c6";
			case 5: return "§c5";
			case 4: return "§c4";
			case 3: return "§63";
			case 2: return "§62";
			case 1: return "§a1";
			case 0: return "§cGo!";
			default: return "";
		}
	}
	
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle timePacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(timePacket);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
	
	public static void displayCountdown(HPlayer p, int seconds, HGame game) {
		sendTitle(p.getPlayer(), "", "§6The game will start in §a"+seconds+" §6seconds!", 0, 20, 0);
		
		seconds++;
		for (int i = seconds - 1; i >= 0; i--) {
			final int number = i;
			new BukkitRunnable(){
				@Override
				public void run(){
					if (game.getOwner() == null) {
						this.cancel();
						return;
					}
					sendTitle(p.getPlayer(), getCountdownNumber(number), "", 0, 20, 0);
				}
			}.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 20L * (seconds - i));
		}
	}
	
	public static boolean inCustomPlayArea(Location loc, HGame game) {
		boolean inArea = false;
		
		final int bx = loc.getBlockX();
		final int by =  loc.getBlockY();
		final int bz = loc.getBlockZ();
		
		for(Location l : game.getCustomPlayfield().locations) {
			final int x = l.getBlockX();
			final int y = l.getBlockY();
			final int z = l.getBlockZ();
			
			if (x == bx && y == by && z == bz)
				inArea = true;
		}
		
		return (inArea);
	}
	
	public static boolean inPlayArea(Location loc, HGame game) {
		boolean inArea = false;
		
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		
		float xS = (game.getPlayfield().x0 < game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		float xL = (game.getPlayfield().x0 > game.getPlayfield().x1 ? game.getPlayfield().x0 : game.getPlayfield().x1);
		float yS = (game.getPlayfield().y0 < game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		float yL = (game.getPlayfield().y0 > game.getPlayfield().y1 ? game.getPlayfield().y0 : game.getPlayfield().y1);
		float zS = (game.getPlayfield().z0 < game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		float zL = (game.getPlayfield().z0 > game.getPlayfield().z1 ? game.getPlayfield().z0 : game.getPlayfield().z1);
		
		if (x >= xS && x <= xL && y >= yS && y <= yL && z >= zS && z <= zL) {
			inArea = true;
		}
		return (inArea);
	}
	
	public static boolean inGameArea(Player p) {
		boolean inArea = false;
		
		HPlayer player = HPlayer.getHPlayer(p);
		float x = (float) player.getPlayer().getLocation().getX();
		float y = (float) player.getPlayer().getLocation().getY();
		float z = (float) player.getPlayer().getLocation().getZ();
		
		for (HGame game : Main.hGames) {
			float xS = (game.getArea().x0 < game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float xL = (game.getArea().x0 > game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float yS = (game.getArea().y0 < game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float yL = (game.getArea().y0 > game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float zS = (game.getArea().z0 < game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			float zL = (game.getArea().z0 > game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			
			if (x > xS && x < xL && y > yS && y < yL && z > zS && z < zL) {
				ScoreboardManager.updateStage(player.getBoard(), game.getName());
				inArea = true;
			} else {
				if (game.getOwner() == null) continue;
				if (!game.getOwner().isAutoLeave()) continue;
				if (game.getOwner().getPlayer() == p) {
					if (game.getOwner().isInParty()) {
						for (HPlayer hp : game.getOwner().getParty())  {
							hp.setInGame(false, game);
						}
					} else {
						game.getOwner().setInGame(false, game);
					}
					game.setOwner(null, true);
				}
				if (game.getOwner() == null) continue;
				if (!game.getOwner().getPlayer().isOnline()) {game.setOwner(null, true);}
			}
		}
		if (!inArea) ScoreboardManager.updateStage(player.getBoard(), "none");
		return (inArea);
	}
	
	public static boolean leaveGameArea(Player p) {
		boolean inArea = false;
		
		HPlayer player = HPlayer.getHPlayer(p);
		float x = (float) player.getPlayer().getLocation().getX();
		float y = (float) player.getPlayer().getLocation().getY();
		float z = (float) player.getPlayer().getLocation().getZ();
		
		for (HGame game : Main.hGames) {
			float xS = (game.getArea().x0 < game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float xL = (game.getArea().x0 > game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float yS = (game.getArea().y0 < game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float yL = (game.getArea().y0 > game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float zS = (game.getArea().z0 < game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			float zL = (game.getArea().z0 > game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			
			if (x > xS && x < xL && y > yS && y < yL && z > zS && z < zL) {
				ScoreboardManager.updateStage(player.getBoard(), game.getName());
				inArea = true;
			} else {
				if (game.getOwner() == null) continue;
				if (game.getOwner().getPlayer() == p) {
					if (game.getOwner().isInParty()) {
						for (HPlayer hp : game.getOwner().getParty())  {
							hp.setInGame(false, game);
						}
					} else {
						game.getOwner().setInGame(false, game);
					}
					game.setOwner(null, true);
				}
				if (game.getOwner() == null) continue;
				if (!game.getOwner().getPlayer().isOnline()) {game.setOwner(null, true);}
			}
		}
		if (!inArea) ScoreboardManager.updateStage(player.getBoard(), "none");
		return (inArea);
	}
	
	public static HGame getGameArea(Player p) {
		HPlayer player = HPlayer.getHPlayer(p);
		float x = (float) player.getPlayer().getLocation().getX();
		float y = (float) player.getPlayer().getLocation().getY();
		float z = (float) player.getPlayer().getLocation().getZ();
		
		for (HGame game : Main.hGames) {
			float xS = (game.getArea().x0 < game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float xL = (game.getArea().x0 > game.getArea().x1 ? game.getArea().x0 : game.getArea().x1);
			float yS = (game.getArea().y0 < game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float yL = (game.getArea().y0 > game.getArea().y1 ? game.getArea().y0 : game.getArea().y1);
			float zS = (game.getArea().z0 < game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			float zL = (game.getArea().z0 > game.getArea().z1 ? game.getArea().z0 : game.getArea().z1);
			
			if (x > xS && x < xL && y > yS && y < yL && z > zS && z < zL) {
				return (game);
			}
		}
		return (null);
	}
	
	public static boolean ownGame(HPlayer p) {
		for (HGame game : Main.hGames) {
			if (game.getOwner() == p) {
				return (true);
			}
		}
		return (false);
	}
	
	public static void stopGames(HPlayer p) {
		p.setUsePlay(false);
		for (HGame game : Main.hGames) {
			if (game.getOwner() == p) {
				game.getOwner().setInGame(false, game);
				game.setOwner(null, true);
			}
		}
		new BukkitRunnable(){
			@Override
			public void run(){
				p.setUsePlay(true);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20L);
	}
}
