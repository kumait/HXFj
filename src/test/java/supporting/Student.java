package supporting;

/**
 * Created by kumait on 12/3/14.
 */
public class Student {
    private int id;
    private String name;
    private float grade;
    private boolean graduated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("supporting.Student[");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", grade=").append(grade);
        sb.append(", graduated=").append(graduated);
        sb.append(']');
        return sb.toString();
    }
}
