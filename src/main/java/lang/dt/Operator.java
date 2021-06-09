package lang.dt;

public class Operator {
    public static void main(String[] args) {
//        System.out.println(3 * 0.1);    // 30000000000000004
        add();

    }

    /**
     * byte a = 127;
     * byte b = 127;
     *
     * b = a + b;  // java: incompatible types: possible lossy conversion from int to byte
     *
     *
     * b += a;     // += 操作符会进行隐式自动类型转换，此处会将相加操作的结果强制转化为持有结果的类型
     * System.out.println(b);  // -2
     *
     * += 操作会进行隐式转化
     */
    private static void add() {
        byte a = 127;
        byte b = 127;

        b = (byte) (a + b);  // java: incompatible types: possible lossy conversion from int to byte

        b += a;     // += 操作符会进行隐式自动类型转换，此处会将相加操作的结果强制转化为持有结果的类型
        System.out.println(b);  // -2

        short s1 = 1;
//        s1 = s1 + 1;    // short运算时会自动提升为int，此时s1是short，会报编译错误
        s1 += 1;
        System.out.println(s1);
    }

}
