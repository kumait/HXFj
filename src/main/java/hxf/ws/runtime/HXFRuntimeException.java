package hxf.ws.runtime;

import javax.servlet.ServletException;
import java.util.HashMap;

/**
 * Created by kumait on 12/3/14.
 */
public class HXFRuntimeException extends CodedException {
    protected int code;

    public static final int GENERAL = 100;
    public static final int SERVICE_CONFIGURATION_MISSING = 101;
    public static final int CONFIGURATION_ERROR = 102;

    public static final int INVALID_CALL_MESSAGE = 200;
    public static final int INTERFACE_NOT_DEFINED = 201;
    public static final int METHOD_NOT_DEFINED = 202;
    public static final int PARAMETER_COUNT_MISMATCH = 203;

    public static final int EXECUTION_ERROR = 300;
    public static final int INTERFACE_NOT_FOUND = 301;
    public static final int METHOD_NOT_FOUND = 302;
    public static final int OVERLOADED_METHOD = 303;

    private static HashMap<Integer, String> errorMap;

    public HXFRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }
}
