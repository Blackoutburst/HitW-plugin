package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Small function with huge utility
 * @author Blackoutburst
 */
public class Tools {
	
	/**
	 * Read a file first line content and return it
	 * @param file file path you want to read
	 * @return file content
	 * @author Blackoutburst
	 */
	public static String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
		}
		return str;
	}
}
