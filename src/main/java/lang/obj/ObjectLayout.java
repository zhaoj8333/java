package lang.obj;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author allen
 */
public class ObjectLayout {

    /**
     * java.lang.Object object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       // 以上为markword： 8字节
     *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     *      // 以上为classpointer: 4字节，没有压缩的话8字节
     *      12     4        (loss due to the next object alignment)
     *      // 以上为对齐, 前面为12字节，不能被8整除，所以另补充4字节作为补充对其
     * Instance size: 16 bytes (如果开启压缩： 对象本身16字节； 未开启压缩则不会有对其，仍是16个字节)
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 对象：
     *     普通对象  数组
     *
     * 普通对象构成：
     *     对象头(包含： markword和类型指针): 8个字节
     *         markword:  关于锁，synchronized信息都在里边
     *         类型指针(class pointer)：  属于那个类，默认情况下4个字节
     *     实例数据(instance data)： 成员变量
     *     对齐(padding)： 当整体的字节数不能被8整除的时候，会补充为 以被8整除,cpu读取内存时，会按照总线宽度读取，提高性能
     *
     * 内存对齐：
     *     编译器对内存进行自动对齐
     *     为了访问未对齐的内存，cpu需要做两次内存访问，而对齐的内存只需要一次访问。
     * struct test {
     *     char c1;
     *     short s1;
     *     char c1;
     *     int i;
     * }
     * 对于字，双字，四字数据来说，在自然边界上时则不需要对齐。
     * （它们的自然边界分别是 偶数地址，可以被4整除的地址，可以被8整除的地址）
     *
     * java -XX:+PrintCommandLineFlags -version
     * -XX:InitialHeapSize=259982592  起始堆大小
     * -XX:MaxHeapSize=4159721472     最大堆大小
     * -XX:+PrintCommandLineFlags
     * -XX:+UseCompressedClassPointers  压缩指针： 64位下指针应该是64位的(指针为8字节)，但是启用了压缩指针，意味着会把这8个字节压缩为4个字节
     * -XX:+UseCompressedOops(Ordinary Object Pointer普通对象指针) 也是压缩后的4个字节
     * -XX:+UseParallelGC
     * java version "1.8.0_181"
     *
     * Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
     * Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
     *
     *
     * lang.obj.ObjectLayout$User object internals:
     *  OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
     *       0     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       // markword: 8 byte
     *       8     4                    (object header)                           43 c1 00 f8 (01000011 11000001 00000000 11111000) (-134168253)
     *      // classpointer: 4 byte
     *      12     4                int User.id                                   0
     *      // int id:      4 byte
     *      16     4   java.lang.String User.name                                 null
     *      // String name: 4 byte
     *      20     4                    (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     */

    /**
     * java.lang.Object object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 以上调用hashcode后的布局：
     * java.lang.Object object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           01 4d cf 67 (00000001 01001101 11001111 01100111) (1741638913)
     *       4     4        (object header)                           4b 00 00 00 (01001011 00000000 00000000 00000000) (75)
     *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * ======================================使用synchronized之后===========================================
     *
     * java.lang.Object object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           10 29 72 25 (00010000 00101001 01110010 00100101) (628238608)
     *                                                                // 有关锁的信息记录在了markword里边
     *
     *       4     4        (object header)                           79 7f 00 00 (01111001 01111111 00000000 00000000) (32633)
     *       8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     *      12     4        (loss due to the next object alignment)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * new - 偏向锁 - 轻量级锁（无锁，自旋锁，自适应自旋） - 重量级锁
     * synchronized的优化过程与markword息息相关
     *
     * new： 偏向锁,使用当前线程指针所谓锁
     * 有人使用，有竞争时： 升级为轻量级锁
     * 竞争状态变得激烈时，升级为重量级锁
     *
     * markword 最低三位表示锁状态，其中1 是偏向锁位，两位是普通锁位
     *
     */
    public static void main(String[] args) {
        Object o = new Object();
        o.hashCode();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        /**
         * 执行这段代码时锁定该对象（门）：此处充当这个门的角色就是对象 o
         *     不能锁定某段代码
         */
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

//        User u = new User();
//        System.out.println(ClassLayout.parseInstance(u).toPrintable());


    }

    static class User {
        private int id;
        private String name;
    }
}
