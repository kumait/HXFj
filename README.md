# HXF WebServices

*Simplicity is a prerequisite for reliability*

*Edsger W. Dijkstra*

---


**HXF Webservices** or **HXF-WS** is part of **HXF** which stands for *High productivity Cross-Platform Framework*.

The idea behind HXF is to create a framework that boosts developer's productivity while persevering efficiency and performance features, the whole framework tries to implement advanced features while keeping the framework simple and easy to be understood.

HXF, at this stage, covers two main areas which are _persistence_ and _web services_, they are covered by two sub-frameworks which are HXF-WS and HXF-P correspondingly.

HXF-WS is built on top of bare HTTP as a messaging protocol and JSON as a message format, messages exchanged with the server are very compact and easy to be serialized/de-serialized, based on this, HXF-WS is very efficient when compared to other frameworks that depend on heavier and more complex standards such as XML, SOAP and WSDL.

Simplicity does not mean that developers can't do everything using HXF-WS, on the contrary, they can create webservices that have all the required features while being light-weight, efficient and easy to be maintained.

Portability is the second design factor that was taken into consideration. While simplicity ensures reliability, it is also easier to build portable designs when the used standards are easy to be implemented, based on this, HXF-WS web services can easily be consumed from a wide range of clients.

For more information about HXF-WS and HXF please refer to documentation.

##Quick Example##

Using HXF-WS it is possible to expose any plain old java interface (POJI) as a web service, below is an example.

Let's have a plain java interface:

~~~~~~~~~~~~~~ java
public interface ITestService {
    String sayHello(String s);
    String getStudentInfo(Student student);
    int getStudentCount(List<Student> studentList);
    int sum(int x, int y);
} 
~~~~~~~~~~~~~~


The above interface is implemented using the code below:

~~~~~~~~~~~~~~ java
public class TestService implements ITestService {

    @Override
    public String sayHello(String s1) {
        return "Hello, " + s1;
    }

    @Override
    public String getStudentInfo(Student student) {
        return student.toString();
    }

    @Override
    public int getStudentCount(List<Student> studentList) {
        return studentList.size();
    }

    @Override
    public int sum(int x, int y) {
        return x + y;
    }
}
~~~~~~~~~~~~~~

~~~~~~~~~~~~~~ java
public class Student {
    private int id;
    private String name;
    private float grade;
    private boolean graduated;

    public int getId() {
        return id;
    }

    // other getters and setters...
}
~~~~~~~~~~~~~~

To expose the interface as a web service, the code below is used.

~~~~~~~~~~~~~~ java
public class TestServlet extends HXFServlet {
    public TestServlet() {
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration("TestService");
        serviceConfiguration.addInterfaceConfig("test", ITestService.class, TestService.class);
        this.runtimeConfiguration.setServiceConfiguration(serviceConfiguration);
    }
}
~~~~~~~~~~~~~~

The above code exposes the `ITest` interface as a web service that can be accessed either using HTTP POST or HTTP GET. In the case of using GET, the call message is delivered as a query string. However, if POST is used, the call message is delivered using JSON. The output of the service is JSON by default. However, it is possible to extend the framework to include custom formats.
