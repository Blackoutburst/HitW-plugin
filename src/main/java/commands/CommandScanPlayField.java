package commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CommandScanPlayField {
	
	private List<Location> spawnpoints = new ArrayList<>();
	
    private void saveLocation(String worldName) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(new File("plugins/HitW/TempPlayField.yml"));

        for (int i = 0; i < spawnpoints.size(); i++) {
	        file.set("play."+i+".world", worldName);
	        file.set("play."+i+".x", spawnpoints.get(i).getBlockX());
	        file.set("play."+i+".y", spawnpoints.get(i).getBlockY());
	        file.set("play."+i+".z", spawnpoints.get(i).getBlockZ());
        }
        try {
            file.save(new File("plugins/HitW/TempPlayField.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean run(CommandSender sender, String[] args) {
        if (args.length < 6) {
        	sender.sendMessage("§cInvalid usage try §e/scan x1 y1 z1 x2 y2 z2");
        	return false;
        }

        int x1 = Integer.min(Integer.parseInt(args[0]), Integer.parseInt(args[3]));
        int y1 = Integer.min(Integer.parseInt(args[1]), Integer.parseInt(args[4]));
        int z1 = Integer.min(Integer.parseInt(args[2]), Integer.parseInt(args[5]));

        int x2 = Integer.max(Integer.parseInt(args[0]), Integer.parseInt(args[3]));
        int y2 = Integer.max(Integer.parseInt(args[1]), Integer.parseInt(args[4]));
        int z2 = Integer.max(Integer.parseInt(args[2]), Integer.parseInt(args[5]));

        Player player = (Player) sender;
        World world = player.getWorld();

        sender.sendMessage("§bStarting scan");

        File tmp = new File("plugins/Quake/"+world.getName()+".yml");
        if (tmp.exists()) tmp.delete();
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Block b = world.getBlockAt(new Location(world, x, y, z));
                    if (b.getType().equals(Material.SPONGE)) {
                    	spawnpoints.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        saveLocation(world.getName());
        sender.sendMessage("§bPlay Field scan complete found §6"+spawnpoints.size()+" §blocation");

        return (true);
    }
	
}
