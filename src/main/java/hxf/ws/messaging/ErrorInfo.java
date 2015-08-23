package hxf.ws.messaging;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class ErrorInfo {
    private int code;
    private String message;

    public ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorInfo() {
        this(0, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
