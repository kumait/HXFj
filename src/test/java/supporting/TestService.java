package supporting;

import hxf.ws.runtime.IntendedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kumait on 12/3/14.
 */
public class TestService implements ITestService {

    public String sayHello(String s1) {
        return "Hello, " + s1;
    }

    public int sum(int x, int y) {
        return x + y;
    }

    public String getStudentInfo(Student student) {
        return student.toString();
    }

    public int getStudentCount(List<Student> studentList) {
        return studentList.size();
    }

    public List<Student> getStudents(int count) {
        Random r = new Random();
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < count; i++) {
            Student student = new Student();
            student.setGraduated(r.nextBoolean());
            student.setGrade(r.nextFloat());
            student.setId(r.nextInt());
            student.setName("student" + r.nextLong());
            students.add(student);
        }
        return students;
    }

    public void intendedException(int code, String message) throws IntendedException {
        throw new IntendedException(code, message);
    }


}
