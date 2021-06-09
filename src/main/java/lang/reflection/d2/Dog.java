package lang.reflection.d2;

public class Dog {

    public String name;
    public int age;

    public Dog() {
    }

    private Dog(String name) {
        this.name = name;
    }

    public Dog(String name, int age) {
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
