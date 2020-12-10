package event;

import java.io.File;

import utils.Values;

/**
 * Manage every action when onEnable event is called
 * @author Blackoutburst
 */
public class Enable {
	
	/**
	 * Initialize games value
	 * and create player data folder
	 * @author Blackoutburst
	 */
	public void enable() {
		Values.initValue();
		File f = new File("player data");
		
		if (!f.exists()) {
			f.mkdir();
		}
	}
}
