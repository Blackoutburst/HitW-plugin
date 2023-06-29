package analytics;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class AnalyticsWatcher {

    private static final String FILE_PATH = "plugins/HitW/analytics.gz";

    public static void init() {
        try {
            new File(FILE_PATH).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendLine(String data) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH, true);
             GZIPOutputStream gos = new GZIPOutputStream(fos) {
                 {
                     def.setLevel(Deflater.BEST_COMPRESSION);
                 }
             };
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(gos))) {

            writer.write(data+";");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
