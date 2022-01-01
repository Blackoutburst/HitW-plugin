package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Request {
	
	private static final String API = "a6426792-10f4-476c-b67d-7634d3b42ddc";
	
	public static String getPlayerInfo(String uuid) {
		try {
			URL url = new URL("https://api.hypixel.net/player?uuid="+uuid+"&key="+API);
		    URLConnection con = url.openConnection();
		    InputStream is =con.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = br.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (Exception e) {
			System.err.println("Hypixel api issue");
			return null;
		}
	}
}
