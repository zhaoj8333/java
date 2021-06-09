package lang.maker_interface.clone;

import lang.maker_interface.serialize.Person;

import java.util.ArrayList;

/**
 * 继承 java.lang.{@link Cloneable} 接口的类，意思是告知 java.lang.{@link Object} 的clone()方法，
 * 可以将该类进行字段到字段的对象拷贝，如果不继承该接口，则会抛出 java.lang.{@link CloneNotSupportedException}异常
 *
 * 继承 java.lang.{@link Cloneable} 接口的类应该复写public clone方法
 *
 * java.lang.{@link Cloneable} 并不包含clone方法，所以，
 */
public class JavaClone {
    public static void main(String[] args) {
//        arraylistClone();
        cloneObject();
    }

    /**
     * 浅拷贝： 对于基本数据类型，可以完全拷贝其值
     *        但是对于对象，则只能拷贝引用
     *
     * 深拷贝： 要实现深拷贝，所有要拷贝的对象类都必须实现 java.lang.{@link Cloneable}接口
     */
    private static void cloneObject() {
        Person p1 = new Person(1);
        Person p1Clone = null;
        try {
            p1Clone = (Person) p1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        p1Clone.setAge(3);
        System.out.println(p1.toString());
        System.out.println(p1Clone);
        System.out.println(p1 == p1Clone);
    }

    /**
     * ArrayList的clone实行的是浅拷贝
     */
    private static void arraylistClone() {
        ArrayList<Person> list = new ArrayList<>();
        Person p1 = new Person(1);
        Person p2 = new Person(2);
        list.add(p1);
        list.add(p2);
        // System.out.println(list);

        ArrayList<Person> clone = (ArrayList<Person>) list.clone();
        Person pClone = clone.get(1);

        System.out.println(pClone == list.get(1));
        list.get(1).setAge(2);
        System.out.println(list.get(1));
        System.out.println(clone.get(1));
    }
}
