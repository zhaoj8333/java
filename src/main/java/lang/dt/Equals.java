package lang.dt;

/**
 * {@link Object#equals(Object)}
 *     比较两个对象内容是否相等，使用于所有对象
 *
 * ==:
 *     比较的是变量（栈）内存中存放对象的（堆）内存地址，用来判断两个对象的地址是否相同，即是否是指向同一个对象
 *     是真正意义上的指针操作
 *
 *     * 比较操作符两端操作数是否是同一对象
 *     * 两边操作数必须是同一类型（可以是父子之间）
 *     * 比较的是地址，如果是Number, 则比较值
 *
 *
 */
public class Equals {
    public static void main(String[] args) {
        System.out.println(10 == 10L);
        System.out.println(10 == 10.0F);
    }
}
