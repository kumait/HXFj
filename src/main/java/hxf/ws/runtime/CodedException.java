package hxf.ws.runtime;

/**
 * Created by Kumait on 4/16/2015.
 */
public class CodedException extends Exception {
    protected int code;


    public CodedException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CodedException(String message) {
        super(message);
        this.code = 0;
    }

    public int getCode() {
        return code;
    }
}
