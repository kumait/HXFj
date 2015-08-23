package hxf.ws.runtime;

import hxf.ws.adapters.InputAdapter;
import hxf.ws.adapters.OutputAdapter;
import hxf.ws.messaging.CallMessage;
import hxf.ws.messaging.ErrorInfo;
import hxf.ws.messaging.ResultMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by kumuhammad on 2/19/2015.
 */
public class ServiceRuntime {

    private RuntimeConfiguration runtimeConfiguration;
    private InputAdapter inputAdapter;
    private OutputAdapter outputAdapter;

    public ServiceRuntime(RuntimeConfiguration runtimeConfiguration, InputAdapter inputAdapter, OutputAdapter outputAdapter) {
        this.runtimeConfiguration = runtimeConfiguration;
        this.inputAdapter = inputAdapter;
        this.outputAdapter = outputAdapter;
    }

    private ErrorInfo getErrorInfo(Exception exception) {

        // service exceptions are returned to the client all the time
        if (exception instanceof IntendedException) {
            return new ErrorInfo(((IntendedException) exception).getCode(), exception.getMessage());
        }

        String errorMessage = "";

        if (runtimeConfiguration.getErrorLevel() == ErrorLevel.NONE) {
            errorMessage = "HXF ERROR";
        } else if (runtimeConfiguration.getErrorLevel() == ErrorLevel.MESSAGE) {
            errorMessage = exception.getMessage();
            if (exception.getCause() != null) {
                errorMessage = errorMessage + "::cause: " + exception.getCause().getMessage();
            }
        } else if (runtimeConfiguration.getErrorLevel() == ErrorLevel.STACK_TRACE) {
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter));
            errorMessage = stringWriter.toString();
        }

        ErrorInfo errorInfo = new ErrorInfo(0, errorMessage);

        if (runtimeConfiguration.getErrorLevel() != ErrorLevel.NONE) {
            if (exception instanceof HXFRuntimeException) {
                HXFRuntimeException HXFRuntimeException = (HXFRuntimeException)exception;
                errorInfo.setCode(HXFRuntimeException.getCode());
            }
        }
        return errorInfo;
    }

    public String execute(String message) {
        ResultMessage resultMessage = null;
        String executionResult = null;
        try {
            if (runtimeConfiguration.getServiceConfiguration() == null) {
                throw new HXFRuntimeException(HXFRuntimeException.SERVICE_CONFIGURATION_MISSING, "No service configuration is defined");
            }

            if (runtimeConfiguration.getLatency() > 0) {
                try {
                    Thread.sleep(runtimeConfiguration.getLatency());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            CallMessage callMessage = null;
            try {
                callMessage = inputAdapter.getInput(message);
            } catch (Exception ex) {
                HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(200, "Invalid input");
                hxfRuntimeException.initCause(ex);
                throw hxfRuntimeException;
            }


            MethodInvoker methodInvoker = new MethodInvoker(runtimeConfiguration.getServiceConfiguration());
            
            Object result = null;
            try {
                result = methodInvoker.invoke(callMessage);
            } catch (HXFRuntimeException runtimeException) {
                throw runtimeException;
            } catch (InvocationTargetException iex) {
                if (iex.getTargetException() != null && iex.getTargetException() instanceof IntendedException) {
                    throw (IntendedException) iex.getTargetException();
                } else {
                    HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(HXFRuntimeException.EXECUTION_ERROR, "Execution Error occured in method: '" + callMessage.getMethodName() + "'");
                    hxfRuntimeException.initCause(iex.getTargetException());
                    throw hxfRuntimeException;
                }
            } catch (Exception ex) {
                HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(HXFRuntimeException.EXECUTION_ERROR, "Error occured while calling: '" + callMessage.getMethodName() + "'");
                hxfRuntimeException.initCause(ex);
                throw hxfRuntimeException;
            }
            resultMessage = new ResultMessage(true, null, result);
        } catch (Exception ex) {
            ErrorInfo errorInfo = getErrorInfo(ex);
            resultMessage = new ResultMessage(false, errorInfo, null);
        }

        executionResult = outputAdapter.getOutput(resultMessage);
        return executionResult;
    }
}
