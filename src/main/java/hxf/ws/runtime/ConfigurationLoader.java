package hxf.ws.runtime;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Kumait on 3/2/2015.
 */
public class ConfigurationLoader {
    public static void applyConfiguration(String servletName, JsonObject conf, RuntimeConfiguration runtimeConfiguration) {
        try {
            if (conf == null) {
                return;
            }

            if (conf.get("servlets") != null) {
                JsonObject servlets = conf.getAsJsonObject("servlets");
                if (servlets.get(servletName) != null) {
                    JsonObject servlet = servlets.getAsJsonObject(servletName);

                    if (servlet.get("runtime") != null) {
                        JsonObject runtime = servlet.getAsJsonObject("runtime");

                        if (runtime.get("error-level") != null) {
                            String errorLevel = runtime.get("error-level").getAsString().toUpperCase();
                            if (errorLevel.equals("NONE")) {
                                runtimeConfiguration.setErrorLevel(ErrorLevel.NONE);
                            } else if (errorLevel.equals("MESSAGE")) {
                                runtimeConfiguration.setErrorLevel(ErrorLevel.MESSAGE);
                            } else if (errorLevel.equals("STACK_TRACE")) {
                                runtimeConfiguration.setErrorLevel(ErrorLevel.STACK_TRACE);
                            }
                        }

                        if (runtime.get("latency") != null) {
                            runtimeConfiguration.setLatency(runtime.get("latency").getAsInt());
                        }

                        if (runtime.get("allow-cross-domain") != null) {
                            runtimeConfiguration.setAllowCrossDomain(runtime.get("allow-cross-domain").getAsBoolean());
                        }

                        if (runtime.get("request-charset") != null) {
                            runtimeConfiguration.setRequestCharset(runtime.get("request-charset").getAsString());
                        }

                        if (runtime.get("response-charset") != null) {
                            runtimeConfiguration.setResponseCharset(runtime.get("response-charset").getAsString());
                        }

                        if (runtime.get("output") != null) {
                            JsonObject output = runtime.getAsJsonObject("output");

                            if (output.get("custom") != null) {
                                runtimeConfiguration.getOutputConfiguration().setCustomOutput(output.get("custom").getAsBoolean());
                            }

                            if (output.get("pretty-output") != null) {
                                runtimeConfiguration.getOutputConfiguration().setPrettyOutput(output.get("pretty-output").getAsBoolean());
                            }

                            if (output.get("dynamic") != null) {
                                runtimeConfiguration.getOutputConfiguration().setDynamic(output.get("dynamic").getAsBoolean());
                            }

                            if (output.get("adapter-class") != null) {
                                runtimeConfiguration.getOutputConfiguration().setCustomAdapterClass(output.get("adapter-class").getAsString());
                            }
                        }

                    }

                    if (servlet.get("service") != null) {
                        JsonObject service = servlet.getAsJsonObject("service");
                        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
                        if (service.get("name") != null) {
                            serviceConfiguration.setName(service.get("name").getAsString());
                        }

                        if (service.get("description") != null) {
                            serviceConfiguration.setDescription(service.get("description").getAsString());
                        }

                        if (service.get("interfaces") != null) {
                            JsonArray interfaces = service.getAsJsonArray("interfaces");
                            for (JsonElement element : interfaces) {
                                JsonObject inter = element.getAsJsonObject();
                                InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
                                if (service.get("name") != null) {
                                    interfaceConfiguration.setName(service.get("name").getAsString());
                                }

                                if (service.get("description") != null) {
                                    interfaceConfiguration.setDescription(service.get("description").getAsString());
                                }

                                String interfaceName = inter.get("interface").getAsString();
                                String className = inter.get("class").getAsString();
                                Class interfaceClass = Class.forName(interfaceName);
                                Class classClass = Class.forName(className);
                                RuntimeInfo runtimeInfo = new RuntimeInfo(interfaceClass, classClass);
                                interfaceConfiguration.setRuntimeInfo(runtimeInfo);
                                serviceConfiguration.getInterfaceConfigs().add(interfaceConfiguration);
                            }
                        }
                        runtimeConfiguration.setServiceConfiguration(serviceConfiguration);
                    }

                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            //HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(HXFRuntimeException.CONFIGURATION_ERROR, "Configuration Error");
            //hxfRuntimeException.initCause(ex);
            //throw hxfRuntimeException;
        }
           
    }
    
    
}
