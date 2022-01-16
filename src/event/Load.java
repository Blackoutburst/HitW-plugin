package event;

import java.io.File;

import api.CoreAPI;
import utils.HGameLoader;
import utils.NPCUtils;
import utils.SkullOwner;

public class Load {
	
	public void execute() {
		new File("./plugins/HitW").mkdir();
		new File("./plugins/HitW/player data").mkdir();
		new File("./plugins/HitW/songs").mkdir();
		
		NPCUtils.loadSkins();
		HGameLoader.loadHGames();
		SkullOwner.loadSkullOwner();
		new CoreAPI().startServer();
	}
}
