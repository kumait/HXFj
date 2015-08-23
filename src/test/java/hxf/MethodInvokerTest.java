package hxf;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import hxf.ws.messaging.CallMessage;
import hxf.ws.runtime.ServiceConfiguration;
import hxf.ws.runtime.MethodInvoker;
import org.junit.Before;
import org.junit.Test;
import supporting.ITestService;
import supporting.TestService;



public class MethodInvokerTest {
    private MethodInvoker methodInvoker;

    @Before
    public void setUp() throws Exception {
        ServiceConfiguration sc = new ServiceConfiguration("Test", "Test");
        sc.addInterfaceConfig("ITest", "ITest", ITestService.class, TestService.class);
        methodInvoker = new MethodInvoker(sc);
    }

    @Test
    public void testInvoke() throws Exception {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive("Kumait"));
        CallMessage message = new CallMessage("ITest", "sayHello", jsonArray);
        Object result = methodInvoker.invoke(message);
        assertEquals(result, "Hello, Kumait");
    }
}