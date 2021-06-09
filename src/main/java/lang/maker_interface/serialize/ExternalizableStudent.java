package lang.maker_interface.serialize;

import java.io.*;
import java.util.Objects;

/**
 * 可序列化类实现Externalizable接口之后，基于Serializable接口的默认序列化机制会失效
 */
public class ExternalizableStudent extends Person implements Externalizable {

    private static final long serialVersionUID = 4945452581122892110L;

    private ExternalizableStudent() {
        super();
    }

    public ExternalizableStudent(int age) {
        super(age);
        this.age = age;
    }

    public ExternalizableStudent(String name, int age) {
        this.name  = name;
        this.age   = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExternalizableStudent student = (ExternalizableStudent) o;
        return name == student.name && age == student.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "ExternalizableStudent{" +
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeObject((ObjectOutputStream) out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        readObject((ObjectInputStream) in);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
        out.writeObject(name);
        out.writeInt(age);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        try {
            in.defaultReadObject();
            name = (String) in.readObject();
            age = in.readInt();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
