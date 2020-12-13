package event;

import java.io.File;

import utils.Values;
import utils.YamlManager;

/**
 * Manage every action when onEnable event is called
 * @author Blackoutburst
 */
public class Enable {
	
	/**
	 * Initialize games value
	 * and create data folder
	 * @author Blackoutburst
	 */
	public void enable() {
		new File("./plugins/HitW").mkdir();
		new File("./plugins/HitW/player data").mkdir();
		YamlManager.createFiles();
		Values.initValue();
	}
}
