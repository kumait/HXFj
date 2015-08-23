package hxf.ws.adapters;

import com.google.gson.*;
import hxf.ws.messaging.ResultMessage;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class JsonOutputAdapter implements OutputAdapter {
    private boolean prettyJson;

    public JsonOutputAdapter(boolean prettyJson) {
        this.prettyJson = prettyJson;
    }

    public JsonOutputAdapter() {
        this(false);
    }

    public boolean isPrettyJson() {
        return prettyJson;
    }

    public void setPrettyJson(boolean prettyJson) {
        this.prettyJson = prettyJson;
    }

    public String getOutput(ResultMessage resultMessage) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(date.getTime());
            }
        });


        if (prettyJson) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        String jsonResult = gson.toJson(resultMessage);
        return jsonResult;
    }
}
