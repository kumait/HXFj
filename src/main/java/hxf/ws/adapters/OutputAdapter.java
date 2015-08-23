package hxf.ws.adapters;

import hxf.ws.messaging.ResultMessage;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public interface OutputAdapter {
    String getOutput(ResultMessage resultMessage);
}
