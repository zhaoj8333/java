package lang.grammer;

public class Tenary
{
    public static void main(String[] args)
    {
        // System.out.println(null); // reference to println is ambigious
        // System.exit(0);
        // tenary();
        tenaryReturn();
        // temp();
        // same();
        
        // d1();
    }
    
    /**
     * ====================================================
     * return type in tenary operator and if statement
     * 
     * the compiler interprets null as an Integer, applies the autoboxing/unboxing rules for the 
     * conditional operator.
     * a null pointer is a valid pointer to every possible object so nothing bad can happen there,
     * the compiler just assumes that null is an Integer which then can autobox to int
     *
     * null is the most specific reference type in common in the type hierarchy
     * null isn't boxed into an integer, it is interpreted as a reference to an Integer.
     * No Integer object is constructed from null, actually null can't construct a Integer object
     *
     *
     */
    private static int temp()
    {
        System.out.println(Integer.parseInt(null)); // NumberFormatExxception: null

        // int a = new Integer(1023);
        // System.out.println((int)a);

        // return true ? null : 0; // no compiler error but NullPointerException
        
        return true ? null : '0';
        /**
         * the java interprets true ? null : 0 as an Integer expression
         * 
         * As for the conditional operator, the JLS answers are:
         *  - if the second and third operands have the same type(which may be null type), then that
         *  is the type of the conditional expression
         *      return true ? 1 : 0;
         *  - if one of the second and third operands is of type boolean and the type of the other is
         *  of type Boolean, then the type of the conditional expression is boolean
         *      return true ? false : new Boolean
         *  - if one of the second and third operands is of the type null and the other is a reference
         *  type, then the type of the conditional expression is that reference type
         *  - if the second and third operands have types that are convertible to numeric types, then
         *  there are several cases
         *    null is treated as convertible to numeric type, and is defined in "Unboxing Conversion"
         *    to throw a NullPointerException
         *    
         *    Unboxing happends at runtime. The null is treated as though it had type int,but is 
         *    actually dquivalent to throw new NullPointerException
         *
         */
        
        /**
         * The first thing to keep in mind is that Java tenary operators  have a type, and that is what
         * the compiler will determine and consider no matter what the actual/real types of the second 
         * or third parameter are.
         *
         *
         *
         */
    }

    private static void tenaryReturn()
    {
        /**
         * ??????????????????????????????????????????????????????????????????????????????
         *
         * - X,Y??????????????????????????????????????????
         * - ????????????????????????????????????????????????????????????
         * - ???????????????????????????????????????X???byte???short???char??????Y?????????int????????????????????????????????????
         *   Y???X???????????????????????????X????????????????????????Y?????????,??????X????????????????????????????????????????????????
         *   ??????
         * - ??????????????????????????????????????????????????????????????????
         */
        int a = 1;
        int b = 90;
        System.out.println(a == b ? 9.9 : 9); // 9.0 ??????????????????
        System.out.println(a == b ? 'a' : 90); // Z  ???char???????????????
        System.out.println(a == b ? 'a' : Integer.MAX_VALUE); // 2147483647 ??????char??????
        System.out.println(a == b ? 'a' : b);  // 90 -
        System.out.println(a != b ? 'a' : b);  // 97 - ?????????????????????b?????????????????????????????????int
        
        System.out.println();


    }

    private static void tenary()
    {
        /**
         * Java???????????????
         * java????????????????????????????????????????????????????????????????????????????????????????????????
         *
         * ?????? ??? ?????? ????????????????????????????????????????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????????????????????????????????????????
         */
        
        int a = true ? 1 : 0;
        boolean b = true;
        boolean c = false;
        boolean d = true;
        boolean e = false;
        
        boolean res = b ? b : c ? d : e; // ?????????
        //                    ==========
        //                        false
        
        System.out.println("res: " + res);

    }

    private static void temp2()
    {
        return; // this is ok
    }

    private static int temp1()
    {
        
        return 1;
        // return true ? null : null; // incompatible types: <null> cannot be converted to int
    }

    private static int same()
    {
        if (true) {
            return 1;
            // return null; // compile error:  incompatible types: <null> cannot be converted to int
            /**
             * the expression null is of the special null type
             *
             * in this case of if statement, the null reference is not treated as an Integer reference
             * because it is not participating in an expression that forces it to be interpreted as such
             * therefore the error can be readily caught at compile-time it is more clearly a type error.
             *
             */
        } else {
            return 0;
        }
    }

    private static int other()
    {
        if (true) {
            return 1;
            // return new Integer(1);  // openjdk, deprecation warning
        }

        return 1;
    }

    public static int tmp()
    {
        return 1;
        // return (new Integer(1));
    }
    /**
     * =======================================================
     */
    public static void d1()
    {
        int[] a = new int[10];

        for (int i = 0; i < 10; i++) {
            a[i] = 9 - i;
        }
        System.out.println(java.util.Arrays.toString(a));
        for (int i = 0; i < 10; i++) {
            a[i] = a[a[i]];
        }
        System.out.println(java.util.Arrays.toString(a));
    }
}
