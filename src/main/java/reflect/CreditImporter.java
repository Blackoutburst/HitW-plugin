package reflect;

import core.HPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CreditImporter {

    public static int getCredits(HPlayer player) {
        try {
            Class<?> economyEPlayerClass = Class.forName("com.hitc.hitweconomyplugin.main.core.EPlayer");
            Class<?> economyEPlayerClassCompanion = Class.forName("com.hitc.hitweconomyplugin.main.core.EPlayer$Companion");
            Object companionObject = economyEPlayerClass.getField("Companion").get(null);
            Method getFromPlayer = economyEPlayerClassCompanion.getDeclaredMethod("getFromPlayer", HPlayer.class);

            Object ePlayer = getFromPlayer.invoke(companionObject, player);

            Field playerDataField = economyEPlayerClass.getDeclaredField("playerData");
            playerDataField.setAccessible(true);

            Object playerData = playerDataField.get(ePlayer);
            Method getCredits = playerData.getClass().getMethod("getCredits");

            return (int) getCredits.invoke(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int getCreditsEarned(HPlayer player) {
        try {
            Class<?> economyEPlayerClass = Class.forName("com.hitc.hitweconomyplugin.main.core.EPlayer");
            Class<?> economyEPlayerClassCompanion = Class.forName("com.hitc.hitweconomyplugin.main.core.EPlayer$Companion");
            Object companionObject = economyEPlayerClass.getField("Companion").get(null);
            Method getFromPlayer = economyEPlayerClassCompanion.getDeclaredMethod("getFromPlayer", HPlayer.class);

            Object ePlayer = getFromPlayer.invoke(companionObject, player);

            Field playerDataField = economyEPlayerClass.getDeclaredField("playerData");
            playerDataField.setAccessible(true);

            Object playerData = playerDataField.get(ePlayer);
            Method getCreditsEarned = playerData.getClass().getMethod("getCreditsEarned");

            return (int) getCreditsEarned.invoke(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
