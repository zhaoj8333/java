package lang.ds.chars;

public class LowerFirstCase
{
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        String s   = "";

        for (int i = 1000000; i > 0; i--) {
            s = func1("abcd");
        }
        System.out.println(s + " 1 : " + (System.currentTimeMillis() - start));
    

        start = System.currentTimeMillis();
        for (int i = 1000000; i > 0; i--) {
            // func1("abcd");
            // func2("abcd");
            s = func2("abcd");
        }
        System.out.println(s + " 2 : " + (System.currentTimeMillis() - start));
        
        start = System.currentTimeMillis();
        for (int i = 1000000; i > 0; i--) {
            // func1("abcd");
            // func2("abcd");
            s = func3("abcd");
        }
        System.out.println(s + " 3 : " + (System.currentTimeMillis() - start));

    }

    public static String func1(String str)
    {
        char[] chs = str.toCharArray();
        chs[0] -= 32;

        return String.valueOf(chs);
    }

    public static String func2(String str)
    {
        char[] chs = str.toCharArray();
        chs[0] |= 32;
    
        return String.valueOf(chs);
    }

    public static String func3(String str)
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
