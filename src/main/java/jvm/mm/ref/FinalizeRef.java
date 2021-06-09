package jvm.mm.ref;

import java.io.IOException;

/**
 * 终接器引用
 *     没有强引用时, 由jvm创建终接器引用,调用finalize方法, 将终接器引用放入队列, 由FinalizeHandller线程回收
 */
public class FinalizeRef {
    public static void main(String[] args) {
        testFinalize();
    }

    /**
     * testFinalize执行完毕, finalize栈帧直接被弹出去
     *
     * 先输出finalize还是null不一定:
     *     main线程和gc线程谁先执行,依赖于线程调度和其他多种因素
     *     甚至虚拟机退出时, finalize都不一定会输出
     *
     * 调用finalize的线程优先级很低, 所以尽量不要使用finalize方法释放对象
     */
    private static void testFinalize() {
        FinalizeRef finalize = new FinalizeRef();
        finalize = null;
        System.gc();

        System.out.println(finalize);
        // null

        try {
            // 阻塞main线程, 给垃圾回收时间执行
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  finalize: 该对象被回收时, finalize会被调用
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize()");
    }
}
