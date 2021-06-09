package lang.maker_interface.serialize;

import java.io.Serializable;
import java.util.Objects;

public class SingletonSerializableStudent extends Person implements Serializable {

    private static final long serialVersionUID = 424545581122892110L;

    public static final SingletonSerializableStudent instance = new SingletonSerializableStudent("allen", 1);

    private Object readResolve() {
        return instance;
    }

    private SingletonSerializableStudent() {
        super();
    }

    private SingletonSerializableStudent(int age) {
        super(age);
        this.age = age;
    }

    private SingletonSerializableStudent(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SingletonSerializableStudent student = (SingletonSerializableStudent) o;
        return name == student.name &&
                age == student.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "SingletonSerializablStudent{" +
                "name = " + name +
                ", age = " + age +
                '}';
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}
