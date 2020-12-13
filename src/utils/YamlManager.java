package utils;

import java.io.File;

public class YamlManager {
	
	/**
	 * Create default yaml file
	 * @author Blackoutburst
	 */
	public static void createFiles() {
		try {
    		if (!new File("./plugins/HitW/walls.yml").exists()) {
				new File("./plugins/HitW/walls.yml").createNewFile();
    		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
