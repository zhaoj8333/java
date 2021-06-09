package lang.maker_interface.serialize;

import lombok.SneakyThrows;

import java.io.*;

/**
 * 1. 标记接口： 标签接口，接口不包含任何方法
 *    标记接口是计算机科学中通用的设计理念
 *
 *    The tag/marker interface pattern is a design pattern in computer science, used with languages that provide run-time type information
 *    about objects. It provides a means to associate metadata with a class where the language does not have explicit support for such meta data;
 *
 * 编程语言本身不支持为类维护元数据，而标记接口则弥补了该缺失，一个类实现某个一个没有任何方法的标记接口，实际上该标记接口就成这个类的元数据之一，运行时通过类的反射机制可以在代码里
 * 找到这种元数据
 *
 *
 * 序列化接口仅仅标记实现Serializable类的可序列化语义
 *
 * java.io.{@link ObjectOutputStream}
 *
 * if (obj instanceof String) {
 *     writeString((String) obj, unshared);
 * } else if (cl.isArray()) {
 *     writeArray(obj, desc, unshared);
 * } else if (obj instanceof Enum) {
 *     writeEnum((Enum) obj, desc, unshared);
 * } else if (obj instanceof Serializable) {
 *     writeOrdinaryObject(obj, desc, unshared);
 * } else {
 *     if (extendedDebugInfo) {
 *         throw new NotSerializableException(cl.getName() + " "
 *         + debugInfoStack.toString());
 *     } else {
 *         throw new NotSerializableException(cl.getName());
 *     }
 * }
 * java中的序列号： 字符串，数组，枚举和普通类的序列化是分开进行的，如果当前类不是字符串，数组，枚举类，就会检测该类是否实现了serializable接口,如果没有则会
 * 抛出 {@link java.io.NotSerializableException}
 * java.io.{@link Serializable}
 *
 *
 * Array classes cannot declare an explicit serialVersionUID, so they always have the default computed value,
 * but the requirement for matching serialVersionUID values is waived for array classes.
 *
 * 2. 元数据 与 注解
 *  {@link java.lang.annotation.Annotation} 声明在java包，类，方法，参数，成员变量，局部变量，用以维护元数据，但是只有在
 *  jdk1.5以后使用，1.5之前维护元数据是通过标记接口进行的
 */
public class JavaSerializableInterface {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        serialize();
//        deserialize();
//        nonArgConstructorDetect();
//        defaultSerializeMechanism();
//        externalize();
//        singletonAndSerialize();
//        singletonAndDeserialize();
        summary();
    }

    /**
     * 父类是serializable，所有子类都可以被序列化
     * 子类是serializable，父类不是，子类可以正确序列化，但是父类属性不会被序列化（不报错，数据丢失）
     * 如果序列化的属性是对象，该对象也必须是seserializable
     *
     * 反序列化时，如果对象属性有增减，则修改的部分会丢失
     * 反序列化时，如果serialVersionUID被修改，则反序列化失败
     *
     * java序列化缺陷：
     * 无法跨语言: 无法在不同编程语言构筑的服务之间通信
     * 容易被攻击: {@link ObjectInputStream} readObject进行反序列化，可以将路径上所有的sh实现了serialize的接口都实例化，该方法会执行任意代码
     *    如果一旦执行循环对象链条，导致hashCode无线调用,引发栈异常
     * 序列化后的流太大: 通过 {@link ObjectOutputStream}实现二进制编码,编码后的数组很大,影响存储和传输效率
     * 序列化性能差:
     * 序列化编程限制:
     *
     * 其他序列化技术：
     * thrift, protobuf, hessian, jack, fastjson
     */
    private static void summary() {

    }

    private static void singletonAndSerialize() {
        try {
            SingletonSerializableStudent instance = SingletonSerializableStudent.instance;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singletonallen.serialize"));
            oos.writeObject(instance);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件获取的SingletonSerializableStudent对象与SingletonSerializableStudent.instance()并不是同一个对象，为了保持单例,可以使用
     *
     * {@link SingletonSerializableStudent} readResolve方法
     */
    private static void singletonAndDeserialize() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("singletonallen.serialize"));
            SingletonSerializableStudent o = (SingletonSerializableStudent) ois.readObject();
            System.out.println(o == SingletonSerializableStudent.instance);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link ExternalizableStudent} 对象中会调用父类Person的无参构造方法
     * {@link Externalizable} 接口的序列化细节需要由程序员去完成，
     * {@link Externalizable} 接口类必须要提供一个午餐的构造方法，且权限为public
     */
    @SneakyThrows
    private static void externalize() {
        ExternalizableStudent a = new ExternalizableStudent("allen", 1);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("allen.externalize"));
        oos.writeObject(a);
    }

    /**
     * 如果仅仅是r让某个类实现 java.io.{@link Serializable} 接口，没有其他处理的话，那么使用的就是默认的序列化机制
     * 默认机制：不仅序列化当前对象本身，还会对父类字段以及该对象的其他对象也进行序列化，同样这些引用的其他对象也会被序列化,以此类推。
     *
     * 所以，如果是容器类对象，该容器所有的元素也会被序列化,改序列化过程开销就会很大。
     *
     * 不能使用默认序列化机制时，序列化过程中简化序列化过程或忽略敏感数据可以使用 transient
     */
    private static void defaultSerializeMechanism() {

    }

    private static void nonArgConstructorDetect() {
        try {
            String t = "1";
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("t.ser"));
            objectOutputStream.writeObject(t);
            objectOutputStream.close();

            // read
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("t.ser"));
            Object o = objectInputStream.readObject();
            System.out.println(o);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化的使用场景：
     *     1. 把内存中的对象状态进行持久化时（保存到文件或数据库）；
     *     2. 用套接字在网络上传输对象时；
     *     3. 通过RMI传输时
     *
     */
    private static void serialize() {
        SerializableStudent allen = new SerializableStudent("Allen", 20, "CD", 1);
//        Class<Student> studentClass = Student.class;
//        Class<?>[] interfaces = studentClass.getInterfaces();
//        System.out.println(Arrays.toString(interfaces));
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("allen.serialize"));
            oos.writeObject(allen);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream((new FileInputStream("allen.serialize")));
        Object o = ois.readObject();
        System.out.println("deserialize: ");
        System.out.println(o);
        ois.close();
    }
}
