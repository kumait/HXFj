package hxf.ws.runtime;

/**
 * Created by Kumait on 4/14/2015.
 */
public class IntendedException extends CodedException {
    public IntendedException(int code, String message) {
        super(code, message);
    }

    public IntendedException(String message) {
        super(message);
    }
}
