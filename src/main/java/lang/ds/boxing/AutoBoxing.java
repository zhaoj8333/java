package lang.ds.boxing;

public class AutoBoxing {
    public static void main(String[] args) {

        // Autoboxing:
        //  the automatic conversion that the Java Compiler makes between the primitive types and their corresponding wrapper class

        // Why autoboxing and unboxing
        // 1. for Generics

        /**
         * 1. Primitive variables in java contains values which have different lengths
         * But, class variables contain references to instances which are implemented by pointers, and pointers have the same size regardless of the sizes the instances they refer to
         *
         *  里氏替换原则(Liskov Substitution principle):
         *   派生类（子类）对象可以在程序中代替其基类（超类）对象
         * 2. This property of class variable makes the references they contain interchangeable, this allow us to do substitution
         *  But, Primitive types variables aren't interchangeable in the same way.
         */
    }


}
