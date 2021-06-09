package lang.ds;

public class TypeCasting {
    public static void main(String[] args) {
//        scalarType();
        refType();
    }

    /**
     * 超类所能做的, 子类也能做, 而子类能做的超类不一定能做
     *  1. 一个子类对象可以强制转化成其超类对象
     *  2. 一个超类对象如果强制转化为子类对象在运行时会抛出异常
     *  3. 如果不是同一继承树上的类强制转换, 将产生编译错误
     */
    private static void refType() {
        B b = new B();
        A a = (A)b;
        //
        a = new A();
        b = (B)a;
        // ClassCastException

        C c = new C();
//        a = (A)c;
        // 不是同一继承树产生编译错误
    }

    private static class A {}
    private static class B extends A {}
    private static class C {}


    private static void scalarType() {
        int a = 0;
//        float b = 8.2; // compiling error
        float b = 8.2f;
        float c = (float) 8.2;
    }
}
