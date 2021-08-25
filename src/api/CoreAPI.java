package api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.configuration.file.YamlConfiguration;

public class CoreAPI {

	private final int PORT = 30100;
	private ServerSocket server = null;
	
	public void startServer() {
		try {
			server = new ServerSocket(PORT);
			System.out.println("API server running on port : "+PORT);
			
			Thread serverRequestThread = new Thread(new Runnable(){
				public void run(){
					try {
						handleRequest();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			serverRequestThread.setDaemon(true);
			serverRequestThread.setName("Server Request Thread");
			serverRequestThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleRequest() throws IOException {
		while (true) {
	  		Socket client = server.accept();
	  		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
	  		OutputStream out = client.getOutputStream();
	  		
	  		String uuid = in.readLine();
	  		File playerFile = new File("./plugins/HitW/player data/"+uuid+".yml");
	  		
	  		
	  		String message = "";
	  		if (!playerFile.exists()) {
	  			message = "Unknown player\n";
	  			out.write(message.getBytes("UTF-8"));
	  		}
	  		
	  		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
			int Q = playerData.getInt("score.Q", 0);
			int F = playerData.getInt("score.F", 0);
			int WQ = playerData.getInt("score.WQ", 0);
			int L = playerData.getInt("score.L", 0);
			message = "{"
					+ "   \"scores\":{"
					+ "      \"qualification\":"+Q+","
					+ "      \"finals\":"+F+","
					+ "      \"wide_qualification\":"+WQ+","
					+ "      \"lobby\":"+L
					+ "   }"
					+ "}";
	  		out.write(message.getBytes("UTF-8"));
	  		
	  		client.close();
	  		in.close();
	  		out.close();
		}
	}
	
}
