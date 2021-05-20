package utils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import event.Load;
import main.Main;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class NPC {
	
	private static ProtocolManager protocolManager;
	
	protected int id;
	protected String name;
	
	public NPC(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void clicked(Player p) {
		switch (name) {
			case "spawn": p.teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0)); break;
			case "finals": p.teleport(new Location(Bukkit.getWorld("world"), -60.5f, 55, -1038.5f, 90, 0)); break;
			case "qualification": p.teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1013.5f, 0, 0)); break;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static void NPCListner() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new PacketAdapter(Main.getPlugin(Main.class), ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
					PacketContainer packet = event.getPacket();
					for (NPC npc : Load.NPCS) {
						if (npc.getId() == packet.getIntegers().read(0)) {
							npc.clicked(event.getPlayer());
						}
					}
				}
			}
		});
	}
	
	public static void loadNPCData() {
		getFromUUID("16d455475ddf42d48e9ffdb45cd69fa5"); //Simon
		getFromUUID("2f066b0e58fd4db6a24bfbce5ab5bdb4"); //Puffleman
		getFromUUID("c46f6438006049d4830ca6fa732303fc"); //Arcxire
		getFromUUID("bb88e17c171f4f36b4ff6e056abe4fff"); //Cosmic
		getFromUUID("082e28e178724b21ba12b3231f7a872c"); //Teddy
		getFromUUID("8b8eb178c169486c97924255c26c0967"); //Sparklizm
		getFromUUID("983b1593a9a443ab8f2a15e0a65f720f"); //Distasted
		getFromUUID("bedb53e2dd754786a2ac1ec80023aabe"); //Mason
		getFromUUID("b00d07b27984424db8f2d96c1e4aace5"); //Hammy
		getFromUUID("b8ef1c7615e04b958d474ca133561f5a"); //Blackout
		getFromUUID("9477ef812e1b4345be40b1529f22c6a6"); //Steak
		getFromUUID("75203801f5a54ba6baa691523aa15848"); //Dogette
		getFromUUID("fb278a5613644c728076efa043204989"); //Alon
	}
	
	private static String[] getPlayerskin(String name) {
		switch(name) {
			case "Somi": return(Load.skins.get(0));
			case "paffol3": return(Load.skins.get(1));
			case "Arcire": return(Load.skins.get(2));
			case "catfury400": return(Load.skins.get(3));
			case "Ted": return(Load.skins.get(4));
			case "Spar": return(Load.skins.get(5));
			case "Tasted": return(Load.skins.get(6));
			case "NoobLMason": return(Load.skins.get(7));
			case "HamsterWall": return(Load.skins.get(8));
			case "Blackuwu": return(Load.skins.get(9));
			case "STEK": return(Load.skins.get(10));
			case "DOG": return(Load.skins.get(11));
			case "alo": return(Load.skins.get(12));
		}
		return null;
	}
	
	public static void spawn(Player p, String name, float x, float y, float z, float yaw, float pitch, String type) {
		String[] skin = getPlayerskin(name);
		
	    WorldServer s = ((CraftWorld) p.getLocation().getWorld()).getHandle();
	    World w = ((CraftWorld) p.getWorld()).getHandle();
	    
	    GameProfile gp = new GameProfile(UUID.randomUUID(), name);
	    
	    gp.getProperties().put("textures", new Property("textures", skin[0], skin[1]));
	    
	    EntityPlayer c = new EntityPlayer(MinecraftServer.getServer(), s, gp, new PlayerInteractManager(w));;
	    
        DataWatcher watcher = c.getDataWatcher();
        watcher.watch(10, (byte) 126);

        c.setLocation(-7.5, 54, -1017.5, yaw, pitch);
	    
	    PacketPlayOutEntityHeadRotation headRotation = new PacketPlayOutEntityHeadRotation(c, (byte) ((yaw * 256.0F) / 360.0F));
	    PacketPlayOutPlayerInfo pi = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, c);
	    PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(c);
	    PacketPlayOutEntityMetadata dataPacket = new PacketPlayOutEntityMetadata(c.getId(), watcher, true);
	    PacketPlayOutPlayerInfo po = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, c);
	    
	    PlayerConnection co = ((CraftPlayer) p).getHandle().playerConnection;
	    co.sendPacket(pi);
	    co.sendPacket(spawn);
	    co.sendPacket(dataPacket);
	    co.sendPacket(headRotation);
	    
	    Load.NPCS.add(new NPC(c.getId(), type));
	    
	    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {
            	co.sendPacket(po);
        		addTeam(p, c.getName());
        		c.setLocation(x, y, z, yaw, pitch);
        		co.sendPacket(new PacketPlayOutEntityTeleport(c));
            }
        }, 5L);
	}
	
    public static void getFromUUID(String uuid) {
        try {
            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();
     
            Load.skins.add(new String[] {texture, signature});
        } catch (Exception e) {
        	System.err.println("Could not get skin data!");
        }
    }
    
    private static void addTeam(Player player, String newPlayer) {
		Scoreboard scoreboard = player.getScoreboard();
		Team team = null;
		
		if (scoreboard.getTeam("NPC") == null) {
			scoreboard.registerNewTeam("NPC");
		}
		team = scoreboard.getTeam("NPC");

	    team.addEntry(newPlayer);
        team.setNameTagVisibility(NameTagVisibility.NEVER);
    }
}
