package lang.maker_interface.serialize;

public class Person implements Cloneable {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(final int age) {
        this.age = age;
    }

    public Person() {
        System.out.println("public person without arg");
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
