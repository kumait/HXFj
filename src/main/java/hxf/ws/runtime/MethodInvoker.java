package hxf.ws.runtime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import hxf.ws.messaging.CallMessage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kumait on 12/3/14 (first vesion).
 */
public class MethodInvoker {
    private ServiceConfiguration serviceConfiguration;

    public MethodInvoker(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }

    private List<Method> findMethods(String name, Class cls) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : cls.getMethods()) {
            if (method.getName().equals(name)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private RuntimeInfo getRuntimeInfo(String interfaceName) throws HXFRuntimeException {
        RuntimeInfo runtimeInfo;
        InterfaceConfiguration ic = serviceConfiguration.getInterfaceConfig(interfaceName);
        if (ic == null) {
            String errorMessage = String.format(Locale.US, "Interface '%s' was not found in service configuration", interfaceName);
            throw new HXFRuntimeException(HXFRuntimeException.INTERFACE_NOT_FOUND, errorMessage);
        }
        runtimeInfo = ic.getRuntimeInfo();
        return runtimeInfo;
    }

    private Method getMethod(RuntimeInfo runtimeInfo, String methodName) throws HXFRuntimeException {
        // should we use the interface cls or class cls? interface cls is more sensible
        List<Method> interfaceMethods = findMethods(methodName, runtimeInfo.getInterfaceCls());
        //List<Method> classMethods = findMethods(methodName, runtimeInfo.getClassCls());

        if (interfaceMethods.size() == 0) {
            String errorMessage = String.format(Locale.US, "Method '%s' was not found in interface '%s'", methodName, runtimeInfo.getInterfaceCls().getName());
            throw new HXFRuntimeException(HXFRuntimeException.METHOD_NOT_FOUND, errorMessage);
        } else if (interfaceMethods.size() > 1) {
            String errorMessage = String.format(Locale.US, "Method overloading is not supported, method '%s' was found more than once in interface '%s'", methodName, runtimeInfo.getInterfaceCls().getName());
            throw new HXFRuntimeException(HXFRuntimeException.OVERLOADED_METHOD, errorMessage);
        }

        /*
        if (classMethods.size() > 1) {
            String errorMessage = String.format(Locale.US, "Method overloading is not supported, method '%s' was found more than once in class '%s'", methodName, runtimeInfo.getClassCls().getName());
            throw new HXFRuntimeException(errorMessage);
        }
        */

        //Method method = classMethods.get(0);
        Method method = interfaceMethods.get(0);
        return method;
    }

    public Object invoke(CallMessage request) throws Exception {
        return invoke(request.getInterfaceName(), request.getMethodName(), request.getParameters());
    }

    private Object invoke(String interfaceName, String methodName, JsonArray jparameters) throws Exception {
        if (interfaceName == null) {
            throw new HXFRuntimeException(HXFRuntimeException.INTERFACE_NOT_DEFINED, "Invalid input, Interface not defined");
        }

        if (methodName == null) {
            throw new HXFRuntimeException(HXFRuntimeException.METHOD_NOT_DEFINED, "Invalid input, Method not defined");
        }

        if (jparameters == null) {
            jparameters = new JsonArray();
        }

        Object result = null;
        Gson gson = new GsonBuilder().create();
        RuntimeInfo runtimeInfo = getRuntimeInfo(interfaceName);
        Method method = getMethod(runtimeInfo, methodName);
        Class[] parameterTypes = method.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];

        // this implies that var args is not supported now
        if (arguments.length != jparameters.size()) {
            String errorMessage = String.format(Locale.US, "Parameter count mismatch for method '%s'", methodName);
            throw new HXFRuntimeException(HXFRuntimeException.PARAMETER_COUNT_MISMATCH, errorMessage);
        }

        for (int i = 0; i < jparameters.size(); i++) {
            arguments[i] = gson.fromJson(jparameters.get(i), parameterTypes[i]);
        }

        Object classInstance = runtimeInfo.getClassCls().newInstance();

        try {
            if (method.getReturnType() == void.class) {
                method.invoke(classInstance, arguments);
            } else {
                result = method.invoke(classInstance, arguments);
            }
        } finally {
            if (classInstance instanceof Cleanable) {
                ((Cleanable) classInstance).clean();
            }
        }
        return result;
    }
}
