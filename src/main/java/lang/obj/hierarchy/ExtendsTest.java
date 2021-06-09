package lang.obj.hierarchy;

public class ExtendsTest {
    public static void main(String[] args) {
        // test1();

        downwardConversion();
        upwardConversion();

    }

    private static void upwardConversion() {

    }

    private static void downwardConversion() {
        // 子类可以非常自然地转换成父类，但是父类转换成子类则需要强制转换。因为子类拥有比父类更多的属性、更强的功能，所以父类转换为子类需要强制。
        // 在这里Son 对象实例被向上转型为father了，
        // 但是请注意这个Son对象实例在内存中的本质还是Son类型的，只不过它的能力临时被消弱了而已
//        Parent p = new Parent();
//        Daughter d = (Daughter)p;
        // runtime lang.exception
        // xxx can not convert ...

        Parent parent = new Parent();
        Son son = (Son)parent;
        // runtime error:
        // cannot case to ...

        // Parent d1 = new Daughter();
        // d.learn();
        // compile time error: cannot find symbol method learn()
        // if (d1 instanceof Daughter) {
        //    Daughter d2 = (Daughter)d;
//            d2.learn();
//        }
    }

    private static void test1() {
        Son c = new Son();
        c.say();
        c.walk();
        c.learn();
        System.out.println(c.hashCode());
        System.out.println(c.toString());

        /*
        final修饰变量时，必须被赋值
        但是不能被二次赋值

        静态方法
         */
        final Parent c1 = new Son();
        System.out.println(c1);
    }
}
