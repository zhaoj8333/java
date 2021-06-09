package lang.dt;

/**
 * {@link String}: 只读字符串，不是基本数据类型，而是对象，已经定义，无法再修改，任何操作都会生成新的String对象
 * {@link StringBuilder}
 * {@link StringBuffer}
 *      以上两者都继承了 {@link AbstractStringBuilder}，调用append进行字符串拼接
 *      底层都是可变字符 数组，{@link StringBuffer}具有同步功能，线程安全，前者线程不安全
 */
public class Strings {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("a");
        sb.append("b");
        String a = sb.toString();
        a = a.intern();
        System.out.println("ab" == "ab");   // true
        System.out.println("ab" == a);
    }
}
