package lang.maker_interface.serialize;

import java.io.Serializable;
import java.util.Objects;

public class SerializableStudent extends Person implements Serializable {

    /**
     * 如果没有serialVersionUID, jvm会自动根据属性计算该值
     * 计算默认的serialVersionUID根据编译器的实现千差万别，这样反序列化过程中可能会导致以外的 java.io.{@link java.io.InvalidClassException}
     * 所以，为了保证serialVersionUID跨编译器的一致性，序列化类必须要有一个明确的serailVersionUID
     *
     * 数组类没有明确的serialVersionUID, 但是数组类没有匹配serialVersionUID的需求
     */
    private static final long serialVersionUID = 4245452581122892110L;

    private SerializableStudent() {
        super();
    }

    public SerializableStudent(int age) {
        super(age);
        this.age = age;
    }

    public SerializableStudent(String name, int age, String addr, int grade) {
        this.name  = name;
        this.age   = age;
        this.addr  = addr;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SerializableStudent student = (SerializableStudent) o;
        return name == student.name &&
                age == student.age &&
                addr == student.addr &&
                grade == student.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, addr, grade);
    }

    @Override
    public String toString() {
        return "SerializablStudent{" +
                "name = " + name +
                ", age = " + age +
                ", addr = " + addr +
                ", grade = " + grade +
                ", desc = " + desc +
                '}';
    }

    private String name;
    private int age;
    private String addr;
    private int grade;
    // 静态变量不会被序列化, 除了serialVersionUID属性
    private static String desc = "===============";

    /**
     * 明确给serialVersionUID赋值后，去掉hobbys序列化后，再恢复后反序列化不会报错
     * 但是没有给serialVersionUID赋值会抛出 java.io.{@link java.io.InvalidClassException}异常
     * : lang.maker_interface.serialize.Student; local class incompatible: stream classdesc serialVersionUID = 495682861967746322,
     * local class serialVersionUID = 6415782351502132760
     *
     * 使用场景：
     *     如果将对象序列化存入库，没有写serializeVersionUID时，可能会出现不认识旧数据的bug
     */
    private int hobbys = 1;
//
    public int getHobbys() {
        return hobbys;
    }

    public void setHobbys(int hobbys) {
        this.hobbys = hobbys;
    }

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
