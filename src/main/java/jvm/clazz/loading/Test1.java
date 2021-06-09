package jvm.clazz.loading;

public class Test1 {
    public static void main(String[] args) {
        System.out.println(Child1.a);
        Child1.doSomething();
        /**
         * parent1 static block
         * 3
         * do something
         */
    }
}

class Parent1 {
    static int a = 3;
    static {
        System.out.println("parent1 static block");
    }

    static void doSomething() {
        System.out.println("do something");
    }
}

class Child1 extends Parent1 {
    static {
        System.out.println("child1 static block");
    }
}
