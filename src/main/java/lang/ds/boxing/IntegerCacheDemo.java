package lang.ds.boxing;

public class IntegerCacheDemo {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 1;
        System.out.println(a == b);

        Integer c = 128;
        Integer d = 128;
        System.out.println(c == d);   // false
        System.out.println(c.equals(d));
    }
}
