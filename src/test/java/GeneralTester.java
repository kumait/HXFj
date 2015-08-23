import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hxf.ws.runtime.RuntimeConfiguration;
import hxf.ws.runtime.ServiceConfiguration;
import supporting.ITestService;
import supporting.TestService;

/**
 * Created by kumuhammad on 3/1/2015.
 */
public class GeneralTester {
    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("k", null);
        
        JsonElement e = jsonObject.get("k");
        boolean b = jsonObject.has("k");

    }
}
