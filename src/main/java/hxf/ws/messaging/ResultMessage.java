package hxf.ws.messaging;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public class ResultMessage {
    private boolean successful;
    private ErrorInfo error;
    private Object value;

    public ResultMessage(boolean successful, ErrorInfo error, Object value) {
        this.successful = successful;
        this.error = error;
        this.value = value;
    }

    public ResultMessage() {
        this(false, null, null);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(JsonObject value) {
        this.value = value;
    }
}
