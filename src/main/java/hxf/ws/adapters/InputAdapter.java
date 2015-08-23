package hxf.ws.adapters;

import hxf.ws.runtime.HXFRuntimeException;
import hxf.ws.messaging.CallMessage;

/**
 * Created by kumuhammad on 2/18/2015.
 */
public interface InputAdapter {
    CallMessage getInput(String input) throws HXFRuntimeException;
}
