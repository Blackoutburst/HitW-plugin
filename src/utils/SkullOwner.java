package utils;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class SkullOwner {

	public static String Arcxire;
	public static String Puffleman;
	public static String Astele;
	public static String Khantrast;
	public static String Shingblad;
	public static String Teddy;
	public static String Sparklizm;
	public static String Mason;
	public static String Jazmin;
	public static String FrogOp;
	
	public static void loadSkullOwner() {
		Arcxire = getNameFromUUID("c46f6438006049d4830ca6fa732303fc");
		Puffleman = getNameFromUUID("2f066b0e58fd4db6a24bfbce5ab5bdb4");
		Astele = getNameFromUUID("6b8b2b824d7b423287617ee286d8bce0");
		Khantrast = getNameFromUUID("e19a51763749493f98071e31deecdd4f");
		Shingblad = getNameFromUUID("3c1737382f624be3aa68bb0c915d7f03");
		Teddy = getNameFromUUID("082e28e178724b21ba12b3231f7a872c");
		Sparklizm = getNameFromUUID("8b8eb178c169486c97924255c26c0967");
		Mason = getNameFromUUID("bedb53e2dd754786a2ac1ec80023aabe");
		Jazmin = getNameFromUUID("640459a7a5c8417bbb06f6e6d28614a1");
		FrogOp = getNameFromUUID("2931cb0ba0104d9b97730d22a61ddc10");
	}
	
	private static String getNameFromUUID(String uuid) {
		String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
		try {
		    String nameJson = IOUtils.toString(new URL(url));           
		    JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
		    String playerSlot = nameValue.get(nameValue.size()-1).toString();
		    JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
		    return nameObject.get("name").toString();
		} catch (IOException | ParseException e) {
		    e.printStackTrace();
		}
		return ("notch");
	}
	
}
