package lang.dt;

import java.util.Hashtable;

/**
 * {@link java.util.HashMap}:
 *     实现了 {@link java.util.Map}
 *     key值可以为null，但是只能有一个
 *     线程不安全
 *     容量初始值为 16
 *
 * {@link java.util.Hashtable}:
 *     实现了 {@link java.util.Map},但也继承了{@link java.util.Dictionary}
 *     多出了 {@link Hashtable#elements()}和 {@link Hashtable#contains(Object)}
 *
 *     元素都不能为null
 *     线程安全
 *     使用 {@link java.util.concurrent.ConcurrentHashMap}替代
 *     容量初始值为11
 *     hash方法为 (hashcode() & 01111111 11111111 11111111 11111111) % size
 *
 *
 * Hash Collision:
 *    拉链法：
 *    开放定址法：发生冲突寻找下一个空的散列地址，只要散列表足够大，空的散列地址总能找到
 *    rehash：双hash，多个hash函数
 *
 */
public class Hash {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(0x7FFFFFFF));
    }
}
