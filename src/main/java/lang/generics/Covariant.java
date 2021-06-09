package lang.generics;

import java.util.ArrayList;
import java.util.List;

public class Covariant {
    public static void main(String[] args) {
//        intro();
//        typeErasure();
        whyNoGenericArray();
    }

    static class Pair<E> {
        public void info() {
            System.out.println("pair");
        }
    }

    static class One<E> extends Pair<E> {
        @Override
        public void info() {
            System.out.println("one");
        }
    }

    private static void whyNoGenericArray() {
        ArrayList<Pair> list = new ArrayList<>();
        list.add(new Pair());

//        list.add(2); // compile error:
//        list.add((Pair)2);
//        泛型保证类型统一
//        argument mismatch; int cannot be converted to lang.generics.Covariant.Pair

//        System.out.println(list);
//        /////////////////////////////////////
        Pair[] pairs = new Pair[10];
        // 数组允许 把一个子类数组  赋给一个父类数组
//        Pair<String>[] pairs1 = new Pair<String>[10];
//        Object[Object] oj = pairs1;
        // compile error: generic array creation


    }

    private static void typeErasure() {
        /*
        类型擦除：
            数组具体化： 数组在运行时才判断数组类型约束
            泛型相反，运行时泛型类型信息被擦除，编译时对类型进行强化
         */
    }

    private static void intro() {
        /*
        协变性： 如果Base是Sub的基类，则Base[] 就是 Sub[]的基类

        泛型是不可变的(invariant):
            List<Base> 不会是 List<Sub>的基类，更不是其子类
         */

        Object[] array = new String[10];
        array[0] = 10; // 编译通过，因为数组是协变的
        // 运行时异常: ArrayStoreException
        System.out.println(array);

        // 编译不通过
//        List<Object> list = new ArrayList<String>();
//        list.add(10);
        /*
        理由：
            破坏安全泛型
         */
        List<Integer> li = new ArrayList<>();
//        List<Number> li1 = li;
//        li1.add(new Float(3.14));  // 破坏了li中定义时的类型安全承诺
    }
}
