package hxf.ws.adapters;

import hxf.ws.runtime.RuntimeConfiguration;
import org.junit.Test;

public class OutputAdapterFactoryTest {

    @Test
    public void testGetClassName() throws Exception {
        RuntimeConfiguration runtimeConfiguration = RuntimeConfiguration.createDefault();
        runtimeConfiguration.getOutputConfiguration().setCustomOutput(false);
        runtimeConfiguration.getOutputConfiguration().setDynamic(false);
        runtimeConfiguration.getOutputConfiguration().setCustomAdapterClass("ZZZ");
        OutputAdapter outputAdapter = null;

        System.out.print(outputAdapter);

    }
}