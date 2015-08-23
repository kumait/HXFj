package hxf.ws.adapters;

import hxf.ws.runtime.ConfigurationReader;
import hxf.ws.runtime.HXFRuntimeException;
import hxf.ws.runtime.OutputConfiguration;
import hxf.ws.runtime.RuntimeConfiguration;

import java.util.Locale;

/**
 * Created by kumuhammad on 3/1/2015.
 */
public class OutputAdapterFactory {
    public static OutputAdapter create(RuntimeConfiguration runtimeConfiguration, String customFormat) throws Exception {
        OutputAdapter outputAdapter = null;
        OutputConfiguration outConf = runtimeConfiguration.getOutputConfiguration();
        String customClassName = null;
        
        if (outConf.isDynamic() && customFormat != null) {
            customClassName = getClassName(customFormat);
            if (customClassName == null) {
                String message = String.format(Locale.US, "Configuration Error, Format '%s' not found in configuration file", customFormat);
                HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(HXFRuntimeException.CONFIGURATION_ERROR, message);
                throw hxfRuntimeException;
            }
        } else {
            if (outConf.isCustomOutput()) {
                customClassName = outConf.getCustomAdapterClass();
                if (customClassName == null) {
                    String message = "Output configuration is set to custom. However, customAdapterClass is not set.";
                    HXFRuntimeException hxfRuntimeException = new HXFRuntimeException(HXFRuntimeException.GENERAL, message);
                    throw hxfRuntimeException;
                }
            }
        }

        if (customClassName != null) {
            outputAdapter = (OutputAdapter) Class.forName(customClassName).newInstance();
        } else {
            outputAdapter = new JsonOutputAdapter(outConf.isPrettyOutput());
        }

        return outputAdapter;
    }

    public static String getClassName(String format) throws Exception {
        String className = null;
        try {
            className = ConfigurationReader.getConfiguration().getAsJsonObject("output-adapters").get(format).getAsString();
        } catch (Exception ex) {
            ex.printStackTrace();   
        }
        return className;
    }
}
