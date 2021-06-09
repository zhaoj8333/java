package lang.reflection.d2;

public class Student {

    private String name;
    private int age;

    public Student() {
    }

    private Student(String name) {
        this.name = name;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private String show(String message) {
        System.out.println("show: " + name + "," + age + "," + message);
        return "testReturnValue";
    }
}
