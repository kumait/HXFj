package hxf.ws.runtime;

import com.google.gson.*;
import hxf.ws.adapters.*;

import org.junit.Before;
import org.junit.Test;
import supporting.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServiceRuntimeTest {
    private RuntimeConfiguration runtimeConfiguration;
    private ServiceRuntime jsonServiceRuntime;
    private ServiceRuntime qsServiceRuntime;


    @Before
    public void setup() {
        runtimeConfiguration = RuntimeConfiguration.createDefault();
        runtimeConfiguration.setErrorLevel(ErrorLevel.MESSAGE);
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration("test");
        serviceConfiguration.addInterfaceConfig("test", ITestService.class, TestService.class);
        runtimeConfiguration.setServiceConfiguration(serviceConfiguration);
        runtimeConfiguration.setErrorLevel(ErrorLevel.STACK_TRACE);

        InputAdapter jsonInputAdapter = new JsonInputAdapter();
        InputAdapter qsInputAdapter = new QueryStringInputAdapter();
        OutputAdapter outputAdapter = new JsonOutputAdapter(false);
        jsonServiceRuntime = new ServiceRuntime(runtimeConfiguration, jsonInputAdapter, outputAdapter);
        qsServiceRuntime = new ServiceRuntime(runtimeConfiguration, qsInputAdapter, outputAdapter);
    }


    @Test
    public void testSayHelloJSON() {
        String name = "Kumait";
        Object[] p = {name};
        Object o = callMethodJson("test", "sayHello", p, String.class);
        String s = (String)o;
        ITestService testService = new TestService();
        assertEquals(s, testService.sayHello(name));
    }

    @Test
    public void testSayHelloQS() {
        String qs = "i=test&m=sayHello&p=['كميت محمد']";
        String result = qsServiceRuntime.execute(qs);
        JsonParser jsonParser = new JsonParser();
        JsonObject jresult = jsonParser.parse(result).getAsJsonObject();
        ITestService testService = new TestService();
        System.out.print(jresult.get("value").getAsString());
        assertEquals(jresult.get("value").getAsString(), testService.sayHello("كميت محمد"));
    }
    
    private Object callMethodJson(String inter, String method, Object[] parameters, Class<?> cls) {
        JsonObject jo = new JsonObject();
        jo.addProperty("i", inter);
        jo.addProperty("m", method);
        JsonArray ja = new JsonArray();
        Gson gson = new Gson();
        if (parameters != null) {
            for (Object p: parameters) {
                JsonElement jsonElement = gson.toJsonTree(p);
                ja.add(jsonElement);

            }
            jo.add("p", ja);
        }

        
        String json = new Gson().toJson(jo);
        String str = jsonServiceRuntime.execute(json);
        JsonParser jsonParser = new JsonParser();
        JsonObject jresult = jsonParser.parse(str).getAsJsonObject();
        System.out.printf("%25s => %s\n", method, jresult);

        JsonElement jval = jresult.get("value");
        Object result = gson.fromJson(jval, cls);
        return result;
    }
    
    @Test
    public void testSum() {
        int x = 16;
        int y = 18;
        Object[] p = {x, y};
        Object r = callMethodJson("test", "sum", p, Integer.class);
        int z = (Integer)r;
        assertEquals(x + y, z);
    }
    
    @Test
    public void testGetStudentInfo() {
        Student student = new Student();
        student.setId(15);
        student.setName("Kumait");
        student.setGrade(72.0f);
        student.setGraduated(true);
        Object[] p = {student};
        
        Object r = callMethodJson("test", "getStudentInfo", p, String.class);
        assertEquals(r, student.toString());
    }
    
    @Test
    public void testGetStudentCount() {
        Student student = new Student();
        student.setId(15);
        student.setName("Kumait");
        student.setGrade(72.0f);
        student.setGraduated(true);
        Object[] students = {student, student, student};
        Object[] p = {students};
        Object r = callMethodJson("test", "getStudentCount", p, Integer.class);
        int c = (Integer)r;
        assertEquals(c, students.length);
    }
    
    @Test
    public void testGetStudents() {
        int count = 3;
        Object[] p = {count};
        Object r = callMethodJson("test", "getStudents", p, Object.class);
        ArrayList arrayList = (ArrayList)r;
        assertEquals(arrayList.size(), count);
    }

    @Test
    public void testServiceException() {
        JsonObject jo = new JsonObject();
        jo.addProperty("i", "test");
        jo.addProperty("m", "intendedException");
        JsonArray ja = new JsonArray();
        Gson gson = new Gson();
        int inputCode = 53;
        String inputMessage = "M123";
        Object[] parameters = new Object[]{inputCode, inputMessage};
        if (parameters != null) {
            for (Object p : parameters) {
                JsonElement jsonElement = gson.toJsonTree(p);
                ja.add(jsonElement);

            }
            jo.add("p", ja);
        }

        String json = new Gson().toJson(jo);
        String str = jsonServiceRuntime.execute(json);
        JsonParser jsonParser = new JsonParser();
        JsonObject jresult = jsonParser.parse(str).getAsJsonObject();
        System.out.printf("%25s => %s\n", "intendedException", jresult);
        JsonObject error = jresult.getAsJsonObject("error");
        int returnedCode = error.get("code").getAsInt();
        String returnedMessage = error.get("message").getAsString();
        assertEquals(inputCode, returnedCode);
        assertEquals(inputMessage, returnedMessage);
    }
    
}