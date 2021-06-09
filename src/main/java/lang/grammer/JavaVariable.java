package lang.grammer;

import java.util.Arrays;

public class JavaVariable
{
    private static int init;

    public static void main(String[] args)
    {
        problem();
        //
        // p1();
        // p2();
        // p3();
        p4();
    }

    public static void p4()
    {
        
    }

    public static void p3()
    {
        /*
         * class member variable behaves different from local variables?
         *
         * static context is init when your app starts
         * int 0
         * string ""
         * boolean false
         *
         */

        // System.out.println(init); // 0
        
        /**
         * int array initialization
         *
         * 栈中主要存放一些基本类 型的变量（,int, short, long, byte, float, double, boolean, char）和对象句柄。
         *
         * local varialbles are stored in stack which are not initialized explicitly with default values
         * while instance variables are stored in heap, they are initilized with their default values
         *
         * also objects are also created in heap
         *
         * when you declare your array reference like this as local variable, and init it
         */

        int[] arr = new int[5];
        System.out.println(Arrays.toString(arr)); // [0, 0, 0, 0, 0]
        /**
         * the arr reference is stored on stack , and memory is allocated for array capable of holding
         * 5 integer elements on heap
         * 
         * then, 5 contiguous memory location(size = 5) for storing integer value are allocated on heap;
         * and each index on array object holds a reference to those memory location in sequence
         *
         * then the array reference points to that array
         * so, since memory for 5 integer values are allocated on heap, they are inited to default value
         * 
         * instance vairable: int[] arr: initialized with null, the arr object lives in heap space
         * local variable   : int[] arr; lives in stack
         */
        
        int[] i;
        /**
         *
         */

    }

    public static void problem()
    {
        int[] a = new int[10];
        // int[] a = [];  //报错
        for (int i = 0; i < 10; i ++) {
            a[i] = i * i; // a might not have been intialized
        }
        System.out.println(Arrays.toString(a));

    }

    public static void p2()
    {
        int a = 0; // 如果没有输出化，编译器会报错 compile-time error
        int[] arr = {1, 2, 3, 4, 5};

        for (int i = 0; i < arr.length; i ++) {
            a = arr[i];
        }
        System.out.println(a);
    }

    public static void p1()
    {
        int a = 2;
        /**
         * int a; this is a declaration
         * a = 0; this is initialization
         *
         * int b = 1; this is declaration and initialization
         *
         * primitives have default values but:
         *
         *  default value is zero when declared as class members
         *  local variables don't have default values
         * 
         * local vairiables do not get default values.
         * Their default values is undefined without assigning values by some means. 
         * Before being used, they must be initialized
         */
        // int b;    // variable b might not have been initialized
        int b = 12;
        int c = a + b;
    }
}
