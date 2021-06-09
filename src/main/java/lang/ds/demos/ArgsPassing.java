package lang.ds.demos;

public class ArgsPassing {
    public static void main(String[] args) {
        int a = 100;
        changeInt(a);
        System.out.println("Integer: " + a);

        String b = "aaaaaa";
        changeStr(b);
        System.out.println("String:  " + b);

        StringBuilder sb = new StringBuilder("iphone");
        changeStringBuilder(sb);
        System.out.println("StringBuilder: " + sb);
    }

    public static void changeInt(int number) {
        number  = 200;
    }

    public static void changeStr(String a) {
        a = "bbbbbb";
    }

    public static void changeStringBuilder(StringBuilder sb) {
        sb.append("cccccc");
    }
}
