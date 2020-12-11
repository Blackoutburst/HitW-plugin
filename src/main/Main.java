package main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import commands.CommandColor;
import commands.CommandDelay;
import commands.CommandDiscord;
import commands.CommandFly;
import commands.CommandLeave;
import commands.CommandPlay;
import commands.CommandSpawn;
import commands.CommandSpectate;
import commands.CommandTourney;
import event.BlockDamage;
import event.BlockPlace;
import event.Enable;
import event.Join;
import event.Leave;
import event.PlayerInteract;
import utils.GetGamePlayer;

/**
 * Main class manage Bukkit / Spigot event
 * @author Blackoutburst
 */
public class Main extends JavaPlugin implements Listener {

	public static List<GamePlayer> players = new ArrayList<GamePlayer>();
	public static boolean InTourney = false;
	public static String tourneyStage = "none";
	public static int stageTime = 0;
	public static int tourneyClock = 0;
	public static GamePlayer player1 = null;
	public static GamePlayer player2 = null;
	
	@Override
    public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new Enable().enable();
	}
	
    @Override
    public void onDisable() {}
	
	@EventHandler
    public void onBlockDamageEvent(BlockDamageEvent event) {
		BlockDamage blockDamage = new BlockDamage();
		blockDamage.brushing(event);
		blockDamage.selectColor(event);
    }
    
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
		PlayerInteract interact = new PlayerInteract();
		interact.hitLever(event);
    }
    
	@EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
		BlockPlace blockPlace = new BlockPlace();
		blockPlace.removeMisplacedBlock(event);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	GamePlayer player = GetGamePlayer.getPlayerFromName(sender.getName());
    	
    	switch (command.getName().toLowerCase()) {
    		case "fly" : return CommandFly.onUse(player);
    		case "tourney" : return CommandTourney.onUse(player, args);
    		case "spectate" : return CommandSpectate.onUse(player, args);
    		case "play" : return CommandPlay.onUse(player, args);
    		case "leave" : return CommandLeave.onUse(player);
    		case "spawn" : return CommandSpawn.onUse(player);
    		case "color" : return CommandColor.onUse(player);
    		case "discord" : return CommandDiscord.onUse(sender);
    		case "delay" : return CommandDelay.onUse(player, args);
    		default : return false;
    	}
    }
    
    @EventHandler
   	public void onPlayerChat(AsyncPlayerChatEvent event) {
    	event.setFormat("%s: %s");
   	}
   
	@EventHandler
 	public void onPlayerJoin(PlayerJoinEvent event) {
		Join join = new Join();
		join.onJoin(event);
	}
	
	@EventHandler
 	public void onPlayerLeave(PlayerQuitEvent event) {
		Leave leave = new Leave();
		leave.onLeave(event);
	}
}
