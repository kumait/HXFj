package hxf.ws.messaging;

import com.google.gson.JsonArray;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class CallMessage {
    public static final String INTERFACE_LONG = "interface";
    public static final String INTERFACE_SHORT = "i";
    public static final String METHOD_LONG = "method";
    public static final String METHOD_SHORT = "m";
    public static final String PARAMETERS_LONG = "parameters";
    public static final String PARAMETERS_SHORT = "p";

    private String interfaceName;
    private String methodName;
    private JsonArray parameters;

    public CallMessage(String interfaceName, String methodName, JsonArray parameters) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public CallMessage() {
        this(null, null, null);
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public JsonArray getParameters() {
        return parameters;
    }

    public void setParameters(JsonArray parameters) {
        this.parameters = parameters;
    }
}
