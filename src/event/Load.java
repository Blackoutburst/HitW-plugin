package event;

import java.io.File;

import utils.HGameLoader;
import utils.NPCUtils;

public class Load {
	
	public void execute() {
		new File("./plugins/HitW").mkdir();
		new File("./plugins/HitW/player data").mkdir();
		new File("./plugins/HitW/songs").mkdir();
		
		NPCUtils.loadSkins();
		HGameLoader.loadHGames();
	}
}
