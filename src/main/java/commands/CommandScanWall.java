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

public class CommandScanWall {
	
	private List<Location> spawnpoints = new ArrayList<>();
	
    private void saveLocation(String worldName) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(new File("plugins/HitW/TempWall.yml"));

        for (int i = 0; i < spawnpoints.size(); i++) {
	        file.set("wall."+i+".world", worldName);
	        file.set("wall."+i+".x", spawnpoints.get(i).getBlockX());
	        file.set("wall."+i+".y", spawnpoints.get(i).getBlockY());
	        file.set("wall."+i+".z", spawnpoints.get(i).getBlockZ());
        }
        try {
            file.save(new File("plugins/HitW/TempWall.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean run(CommandSender sender, String[] args) {
        if (args.length < 6) {
        	sender.sendMessage("§cInvalid usage try §e/scanwall x1 y1 z1 x2 y2 z2");
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
                    if (b.getType().equals(Material.QUARTZ_BLOCK)) {
                    	spawnpoints.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        saveLocation(world.getName());
        sender.sendMessage("§bWall scan complete found §6"+spawnpoints.size()+" §blocation");

        return (true);
    }
	
}
