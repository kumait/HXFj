package hxf.ws.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import hxf.ws.messaging.CallMessage;
import hxf.ws.runtime.HXFRuntimeException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class QueryStringInputAdapter implements InputAdapter {
    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> map = new HashMap<String, String>();
        String[] params = queryString.split("&");
        for(String p : params) {
            String[] kv = p.split("=");
            String key = kv[0];
            String val = kv[1];
            map.put(key, val);
        }
        return map;
    }

    @Override
    public CallMessage getInput(String input) throws HXFRuntimeException {
        String interfaceName = null;
        String methodName = null;
        JsonArray parameters = null;

        Map<String, String> map = parseQueryString(input);

        if (map.containsKey(CallMessage.INTERFACE_LONG)) {
            interfaceName = map.get(CallMessage.INTERFACE_LONG);
        } else {
            interfaceName = map.get(CallMessage.INTERFACE_SHORT);
        }

        if (map.containsKey(CallMessage.METHOD_LONG)) {
            methodName = map.get(CallMessage.METHOD_LONG);
        } else {
            methodName = map.get(CallMessage.METHOD_SHORT);
        }

        String jParams = null;
        if (map.containsKey(CallMessage.PARAMETERS_LONG)) {
            jParams = map.get(CallMessage.PARAMETERS_LONG);
        } else {
            jParams = map.get(CallMessage.PARAMETERS_SHORT);
        }

        if (jParams != null) {
            JsonParser jsonParser = new JsonParser();
            parameters = jsonParser.parse(jParams).getAsJsonArray();
        }

        CallMessage callMessage = new CallMessage(interfaceName, methodName, parameters);
        return callMessage;
    }
}
