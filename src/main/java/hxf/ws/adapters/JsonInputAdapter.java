package hxf.ws.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hxf.ws.messaging.CallMessage;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class JsonInputAdapter implements InputAdapter {
    @Override
    public CallMessage getInput(String input) {
        JsonParser jsonParser = new JsonParser();
        JsonObject requestObject = jsonParser.parse(input).getAsJsonObject();

        String interfaceName = null;
        String methodName = null;
        JsonArray parameters = null;

        if (requestObject.has(CallMessage.INTERFACE_LONG)) {
            interfaceName = requestObject.get(CallMessage.INTERFACE_LONG).getAsString();
        } else if (requestObject.has(CallMessage.INTERFACE_SHORT)) {
            interfaceName = requestObject.get(CallMessage.INTERFACE_SHORT).getAsString();
        }

        if (requestObject.has(CallMessage.METHOD_LONG)) {
            methodName = requestObject.get(CallMessage.METHOD_LONG).getAsString();
        } else if (requestObject.has(CallMessage.METHOD_SHORT)) {
            methodName = requestObject.get(CallMessage.METHOD_SHORT).getAsString();
        }

        if (requestObject.has(CallMessage.PARAMETERS_LONG)) {
            parameters = requestObject.get(CallMessage.PARAMETERS_LONG).getAsJsonArray();
        } else if (requestObject.has(CallMessage.PARAMETERS_SHORT)) {
            parameters = requestObject.get(CallMessage.PARAMETERS_SHORT).getAsJsonArray();
        }

        CallMessage callMessage = new CallMessage(interfaceName, methodName, parameters);
        return callMessage;
    }
}
