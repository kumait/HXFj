package supporting;

import hxf.ws.runtime.IntendedException;

import java.util.List;

/**
 * Created by kumait on 12/3/14.
 */
public interface ITestService {
    String sayHello(String s);
    int sum(int x, int y);
    String getStudentInfo(Student student);
    int getStudentCount(List<Student> studentList);
    List<Student>getStudents(int count);

    void intendedException(int code, String message) throws IntendedException;
}
