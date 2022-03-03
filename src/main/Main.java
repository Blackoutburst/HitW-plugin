package main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.entity.EntityDismountEvent;

import com.blackoutburst.simplenpc.SimpleNPCPlayer;

import commands.CommandsManager;
import core.HGame;
import core.HPlayer;
import event.BlockDamage;
import event.BlockPlace;
import event.Dismount;
import event.InventoryClick;
import event.Join;
import event.Leave;
import event.Load;
import event.Move;
import event.PlayerInteract;

public class Main extends JavaPlugin implements Listener {

	public static List<SimpleNPCPlayer> npcplayers = new ArrayList<SimpleNPCPlayer>();
	
	public static int skinId = 100;
	
	public static List<HPlayer> hPlayers = new ArrayList<HPlayer>();
	public static List<HGame> hGames = new ArrayList<HGame>();
	
	public static boolean QDuelBusy = false;
	public static boolean FDuelBusy = false;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		new Load().execute();
	}
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent event) {
		new Move().execute(event);
	}
	
	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		new BlockDamage().execute(event);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		new InventoryClick().execute(event);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		new PlayerInteract().execute(event);
	}
	
	@EventHandler
	public void onDismountEvent(EntityDismountEvent event) {
		new Dismount().execute(event);
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		new BlockPlace().execute(event);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		new CommandsManager().execute(sender, command, label, args);
		return true;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		new Join().execute(event);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		new Leave().execute(event);
	}
}
