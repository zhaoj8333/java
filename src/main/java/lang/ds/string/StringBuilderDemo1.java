package lang.ds.string;

public class StringBuilderDemo1 {
    public static void main(String[] args) {
//        demo1();
        demo2();
    }

    public static void demo2() {
        Character c = 'A';
        System.out.println(c.toString());
    }

    /**
     * StringBuilder:
     * 字符串缓冲区（字符串容器），可变的字符序列
     *
     */
    public static void demo1() {
        StringBuilder sb = new StringBuilder();
        // 动态初始化
        // 空的字符串缓冲区，初始容量为16
        System.out.println(sb);
        sb.append("123456789123456789");
        sb.append(" + aaaaaaa");
        sb.append(" + bbbbbbb");
        System.out.println(sb);
        // 但是超过16个，会自动扩容

        StringBuilder sb1 = new StringBuilder("aaaa");
        // 静态初始化
        System.out.println(sb1);
    }


}
