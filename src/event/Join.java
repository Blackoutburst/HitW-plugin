package event;

import java.io.File;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import game.TourneyManager;
import main.GamePlayer;
import main.Main;
import main.NMS;
import utils.Board;
import utils.RankManager;
import utils.Tools;
import utils.Values;

/**
 * Manage every action when onPlayerJoin event is called
 * 
 * @author Blackoutburst
 */
public class Join {

	/**
	 * When a player join create a new Scoreboard and load player rank from Hypixel
	 * Hole in the Wall data if the player is new create configuration file for him or load
	 * current data Create a new GamePlayer object
	 * 
	 * @param event Player Join event
	 * @author Blackoutburst
	 */
	public void onJoin(PlayerJoinEvent event) {
		String rank = RankManager.loadRank(event.getPlayer());
		Board board = new Board(event.getPlayer(), rank, "§r");
		int wallColor = 4;
		int glassColor = 3;
		float leverDelay = 0.5f;
		boolean showTitle = true;
		float memtime = 3f;
		float brushLag = 100;

		File f = new File(Tools.getPlayerFolder(event.getPlayer()));
		if (!f.exists()) {
			f.mkdir();
			Tools.writePlayerData(f, wallColor, glassColor, leverDelay, showTitle, memtime, brushLag);
		} else {
			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(f+"/config.yml"));
			wallColor = playerData.getInt("colors.wall");
			glassColor = playerData.getInt("colors.glass");
			leverDelay = (float) playerData.getDouble("delay");
			showTitle = playerData.getBoolean("title");
			memtime = (float) playerData.getDouble("memtime");
			brushLag = (float) playerData.getDouble("brushLag");
		}

		setToSpawn(event.getPlayer());
		setScoreBoard(board);
		GamePlayer newPlayer = new GamePlayer(event.getPlayer(), board, rank, wallColor, glassColor, leverDelay, showTitle, memtime, brushLag);
		addTeam(newPlayer);
		event.getPlayer().setAllowFlight(true);
		Main.players.add(newPlayer);
		if (!Main.tourneyStage.equals("none")) {
			TourneyManager.displaySpectateMessage();
		}
	}

	
	/*
    	private int a;
	    private UUID b;
	    private int c;
	    private int d;
	    private int e;
	    private byte f;
	    private byte g;
	    private int h;
	    private DataWatcher i;
	    private List<DataWatcher.WatchableObject> j;
	    
	    
	    
	    this.a = entityhuman.getId();
        this.b = entityhuman.getProfile().getId();
        this.c = MathHelper.floor(entityhuman.locX * 32.0D);
        this.d = MathHelper.floor(entityhuman.locY * 32.0D);
        this.e = MathHelper.floor(entityhuman.locZ * 32.0D);
        this.f = (byte) ((int) (entityhuman.yaw * 256.0F / 360.0F));
        this.g = (byte) ((int) (entityhuman.pitch * 256.0F / 360.0F));
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        this.h = itemstack == null ? 0 : Item.getId(itemstack.getItem());
        this.i = entityhuman.getDataWatcher();
	    
	 */
	
	
	
	
	private static final String SKIN = "ewogICJ0aW1lc3RhbXAiIDogMTYxMjkwMDQ1NDQ4OSwKICAicHJvZmlsZUlkIiA6ICI5MjkzODY4YjQxNGM0MmIyYmQ4ZTNiY2I3OTEyNDdiOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJZYWt1c3VuYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MzhiYmI5MTI0ZjM3NGM5N2Y0MTEyODEyMWZmMDczYmZiZTZiYjRjM2VmMmZjNWE1ODZlODY5ZDY2MDZkNWJjIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
    private static final String SIGNATURE = "q+GfLIBrKDF+qUyv9ykF3ZTnHfbOZRox3/ql2liOUGgTCgWJF/lIBcWaqp9HOz3cKk94148/fV3Q8NPx0S29AfpGKZgrVz8AL0csmkBGo66V5G+ovA0zeu4I7dQnpNpqhiv0Rq1cy8uceheGMuUkglQCnfJ0ay/yE1lUfuTAw++w2e+Hl3dWWZaejNZpNNMDJFABMsuAl3TEuX2Qg0ZeLHh9820EhQLVX6v4ZEb1fAnl+0/f9IyWO+mc5KZ9nRVkvpRPtCqpfSnrBbxdMjcEUzlR4dYF1Mkd6wZWUhpIlZziOFAj+HjNlQmQhDSi0z/LyXk22Qk9ErYsDnM25P/TVzucRenu8IifyTunJZ0D6uvA/9rOlu+1yjRqjlREYrJC8PL+cw/do1q2Vb5XIdIb+4nIYUrYZPDpZNg+kMCl64JkSSqRwtceTgEjfCLGSEPFhYYOUiyTBFLnM5J8K409L7TWOOUWURnizFblxNsM/UzMR7TQ2iXW8om2qyPdoKKJSqr7xjuiaMvJm/UW9VgUOwPTH3qGC033vu9cD2nzqf3QtW0EqqSrisUx6XerHIUndexIrO5+K+cR89ntlke6RZRHbv+/Wk7ELB4IMe3zSUm5L/QqY9LOFR2gsCHbWZPgjnqK9ZBSDPHmzx3XJVrMeK1FzBz0VKG6QyNJw9OqZ8Y=";
    private static final Random RANDOM = new Random();

	public static void spawnHOL(Player player) {
		int entityId = RANDOM.nextInt(1000) + 2000;
		GameProfile profile = new GameProfile(UUID.fromString("9293868b-414c-42b2-bd8e-3bcb791247b9"), "Yakusuna");
		profile.getProperties().put("textures", new Property("textures", SKIN, SIGNATURE));
		Location location = player.getLocation();
		
		
		
		
		Object packet = null;
		try {
			packet = NMS.getNMSClass("PacketPlayOutNamedEntitySpawn").getDeclaredConstructor().newInstance();
			NMS.setField(packet, "a", entityId);
		    NMS.setField(packet, "b", profile.getId());
		    NMS.setField(packet, "c", (int)(Math.floor(location.getX() * 32.0D)));
		    NMS.setField(packet, "d", (int)(Math.floor(location.getX() * 32.0D)));
		    NMS.setField(packet, "e", (int)(Math.floor(location.getX() * 32.0D)));
		    NMS.setField(packet, "f", (byte)((int)(location.getYaw() * 256.0F / 360.0F)));
		    NMS.setField(packet, "g", (byte)((int)(location.getYaw() * 256.0F / 360.0F)));
		    NMS.setField(packet, "h", 0);
		    NMS.sendPacket(player, packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Add the new player to everyone Add all player to the new player This is
	 * important to display rank on player nametag
	 * 
	 * @param newPlayer newest player
	 * @author Blackoutburst
	 */
	private void addTeam(GamePlayer newPlayer) {
		for (GamePlayer p : Main.players) {
			Board.addTeam(p, newPlayer);
			Board.addTeam(newPlayer, p);
		}
	}

	/**
	 * Create a new Scoreboard with default value
	 * 
	 * @param board Scoreboard object
	 * @author Blackoutburst
	 */
	private void setScoreBoard(Board board) {
		int minutes = 0 / 60;
		int seconds = (0) % 60;
		String str = String.format("%d:%02d", minutes, seconds);

		board.set(15, " ");
		board.set(14, "Stage: §anone");
		board.set(13, "Play Time: §a" + str);
		board.set(12, "  ");
		board.set(11, "Perfect Walls: §a" + 0);
		board.set(10, "Wall: §a" + 0);
		board.set(9, "Score: §a" + 0);
		board.set(8, "   ");
		board.set(7, "Missing block: §4" + 0);
		board.set(6, "Misplaced block: §4" + 0);
		board.set(5, "Choke: §4" + 0);
		board.set(4, "    ");
		board.set(3, "Fly: §bOn");
		board.set(4, "     ");
	}

	/**
	 * Teleport the player to the spawn location
	 * 
	 * @param player newest player
	 * @author Blackoutburst
	 */
	private void setToSpawn(Player player) {
		player.teleport(Values.spawn);
	}
}
