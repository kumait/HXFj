package hxf.ws.runtime;

import com.google.gson.JsonObject;
import org.junit.Test;
import supporting.ITestService;
import supporting.TestService;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ConfigurationLoaderTest {
    @Test
    public void testApplyConfiguration() throws Exception {
        ConfigurationReader.init(new File("src/test/java/supporting/hxf2.json"));
        JsonObject conf = ConfigurationReader.getConfiguration();
        RuntimeConfiguration runtimeConfiguration = RuntimeConfiguration.createDefault();
        ConfigurationLoader.applyConfiguration("Test", conf, runtimeConfiguration);
        
        assertEquals(runtimeConfiguration.getErrorLevel(), ErrorLevel.STACK_TRACE);
        assertEquals(runtimeConfiguration.getLatency(), 1500);
        assertEquals(runtimeConfiguration.isAllowCrossDomain(), false);
        assertEquals(runtimeConfiguration.getRequestCharset(), "iso1");
        assertEquals(runtimeConfiguration.getResponseCharset(), "iso2");
        assertEquals(runtimeConfiguration.getOutputConfiguration().isCustomOutput(), true);
        assertEquals(runtimeConfiguration.getOutputConfiguration().isDynamic(), true);
        assertEquals(runtimeConfiguration.getOutputConfiguration().isPrettyOutput(), true);
        assertEquals(runtimeConfiguration.getOutputConfiguration().getCustomAdapterClass(), "ZZZ");
        
        ServiceConfiguration serviceConfiguration = runtimeConfiguration.getServiceConfiguration();
        assertEquals(serviceConfiguration.getName(), "N");
        assertEquals(serviceConfiguration.getDescription(), "D");
        
        InterfaceConfiguration interfaceConfiguration = serviceConfiguration.getInterfaceConfigs().get(0);
        assertEquals(interfaceConfiguration.getName(), "N");
        assertEquals(interfaceConfiguration.getDescription(), "D");
        assertEquals(interfaceConfiguration.getRuntimeInfo().getInterfaceCls(), ITestService.class);
        assertEquals(interfaceConfiguration.getRuntimeInfo().getClassCls(), TestService.class);
    }
}