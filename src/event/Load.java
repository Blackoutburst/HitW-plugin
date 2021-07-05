package event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.HGameLoader;
import utils.NPC;

public class Load {
	public static List<NPC> NPCS = new ArrayList<NPC>();
	public static List<String[]> skins = new ArrayList<String[]>();
	
	public void execute() {
		new File("./plugins/HitW").mkdir();
		new File("./plugins/HitW/player data").mkdir();
		new File("./plugins/HitW/songs").mkdir();
		
		NPC.loadNPCData();
		NPC.npcListener();
		HGameLoader.loadHGames();
	}
}
