package lang.obj.hierarchy.innerclass;

public class Outer {
    /**
     * 1. Nested Inner class
     *    Interface can also be nested and can have access modifiers
     * 2. Method Local inner class
     * 3. Anonymous inner class
     * 4. Static nested class
     */
    class Inner {
        public void work() {
            System.out.println("I am inner");
        }

        /**
         * Compile error: Illegal static declaration in inner class
         *
         * we can’t have static method in a nested inner class
         * an inner class is implicitly associated with an object of its outer class so it cannot define any
         * static method for itself
         */
//        public static void inStatic() {
//            System.out.println("inner static");
//        }
    }

    public void outerMethod() {
        final int x = 3821;
        // the main reason we need to declare a local variable as a final is
        // local variables lives on stack till method is on the stack
        // but there might be a case the object of inner class still lives on the heap

        // method local inner class cannot be marked as private, protected, public and transient but can be abstract and final
        System.out.println("outer method");
        // private class Inner {
        // protected class Inner {
        class Inner {
//            also cannot contain static methods
//            public static void a() {
//
//            }
            void innerMethod() {
                // cannot assign a value to final variable
                // x = 1;
                System.out.println("int x of outerMethod: " + x);
                // System.out.println("inside inner method");
            }
        }
        Inner y = new Inner();
        y.innerMethod();
    }

    // static nested class is not technically an inner class, it's like a static member of the outer class
    // 如果一个类要被声明为static的，只有一种情况，就是静态内部类。
    static class InnerStatic {
        public InnerStatic() {
            System.out.println("inner static / inner static");
        }
    }

    // anonymous inner classes
    static Anonymous a = new Anonymous() {
        void show() {
            super.show();
            System.out.println("aaaaaaaaaaaaa");
        }
    };

    void showAnony() {
        a.show();
    }

    static Hello h = new Hello() {
        @Override
        public void show() {
            System.out.println("interface hello");
        }
    };

    void showHello () {
        h.show();
    }

}

class Anonymous {
    void show() {
        System.out.println("anonymous");
    }
}

interface HelloParent {

}

interface Hello {
    void show();
}


class Main {
    public static void main(String[] args) {
        // test2();
        // test3();
        // test4();
        test5();
    }

    private static void test5() {
        Outer o = new Outer();
        o.showAnony();
        o.showHello();
    }

    private static void test4() {
        Outer.InnerStatic ins = new Outer.InnerStatic();
    }

    private static void test3() {
        Outer x = new Outer();
        x.outerMethod();
    }

    private static void test2() {
        test3();
    }

    public static void test1() {
        Outer.Inner in = new Outer().new Inner();
        in.work();
    }
}