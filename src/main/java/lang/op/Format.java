package lang.op;

import java.lang.Integer;
import java.lang.Double;

public class Format
{
    public static void main(String[] args)
    {
        if (args.length == 0 ) {
            System.out.println("请输入!");
            System.exit(1);
        }

        String arg = args[0];

        //printInt(arg);
        // printDouble(arg);
        printStr(arg);
    }
    
    public static void printInt(String arg)
    {
        int number = Integer.parseInt(arg);

        System.out.printf("%14d", number);
    }

    public static void printDouble(String arg)
    {
        double flo = Double.parseDouble(arg);

        System.out.printf("%14.2f", flo);
    }

    public static void printStr(String arg)
    {
        String str = arg;

        System.out.printf("%14s", str);
    }
}
