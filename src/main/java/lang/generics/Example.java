package lang.generics;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) {
//        exp1();
//        exp2();
        exp3();

    }

    /**
     * 泛型只在编译阶段有效，编译之后会采取去泛型化
     * 编译中，正确验证泛型结果后，在对象进入或离开方法的边界处添加类型检查和类型转换方法。
     * 泛型信息不会进入运行时阶段
     *
     * 泛型在逻辑上看似是多个不同类型，实际上是相同的基本类型
     */
    public static void exp3() {
        List<String> strL  = new ArrayList<String>();
        List<Integer> intL = new ArrayList<Integer>();

        Class cstrL = strL.getClass();
        Class citrL = intL.getClass();
        System.out.println(cstrL.equals(citrL));
        // true
        System.out.println(citrL.equals(cstrL));
        // true
    }

    public static void exp2() {
        List<String> arrl = new ArrayList<String>();
        arrl.add("aaaaaaaaaaa");
//        arrl.add(1111);  // 编译时报错
    }

    public static void exp1() {
        /**
         * ArrayList可以放置任意类型
         */
        List arrl = new ArrayList();
        arrl.add("aaaaaaaaa");
        arrl.add(100);

        for (int i = 0; i < arrl.size(); i++) {
            String item = (String)arrl.get(i);
            System.out.println(item);
            // 运行时错误 java.lang.Integer cannot be cast to java.lang.String
        }
    }
}
