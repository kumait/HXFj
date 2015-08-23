package hxf.ws.runtime;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Created by Kumait on 3/1/2015.
 */
public class ConfigurationReader {
    public static final String FILE_NAME = "hxf.json";
    private static JsonObject conf;

    public static void init(File configFile) {
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
                JsonParser parser = new JsonParser();
                conf = parser.parse(reader).getAsJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static JsonObject getConfiguration() {
        return conf;
    }
}
