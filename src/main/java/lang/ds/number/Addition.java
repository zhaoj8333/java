package lang.ds.number;

public class Addition
{
    public static void main(String[] args)
    {
        add1();
    }

    public static void add1()
    {

        // System.out.println(Byte.MAX_VALUE);
        byte b = 127;
        byte c = 12;
        
        // // System.out.println(b + 1); // 128
        // System.out.println(b += 1); // -128
        // System.out.println(b + c);
        // System.out.println(b += c);
        
        // byte d = 0;
        // d = b + c;
        // System.out.println(d); //  compile error: incompatible types
        
        // short d = 0;
        // d = b + c;// compile error: incompatible error
        // System.out.println(d);
        
        System.out.println((short)b + (short)c); // it works

    }
}
