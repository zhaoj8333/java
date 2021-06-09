package lang.dt;

/**
 * final修饰的类不可被继承
 * final方法不可被重写
 * final变量不可被改变，如果是引用表示引用不可变，所指向的内容依赖于具体的类是否为final
 * 被final修饰的方法，jvm会尝试做内联
 * final变量在编译阶段会被放入常量池
 *
 * 编译器对final的重排序规则：
 *     构造函数内对final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，两个操作之间不能重排序
 *         构造对象内有final域，必须先构造对象，再赋值给其他引用
 *
 *     初次读取包含final域对象的引用，与随后初次读取这个final域，两个操作之间不能重排序
 *         如果对象有final域，必须先读取对象引用，再读final域
 *
 * final域的重排序规则确保：
 *     引用变量为任意线程可见之前，改引用变量指向的对象的final域已经在构造函数中被正确初始化过了
 *
 *     JSR内存屏障：
 *      LOADLOAD:   load1;loadload;load2，load2以及后续读取操作要读取的数据被访问前，保障load1要读取的数据被读取完毕
 *      STORESTORE: store1;storestore;store2，store2以及后续写操作执行前，保障store1的写入操作对其他处理器可见
 *      LOADSTORE:  load1;loadstore;store2,store2以及后续写入操作被刷出前，保证load1要读取的数据被读取完毕
 *      STORELOAD:  store1;storeload;load2,load2以后续操作执行前，保障store1的写入对所有处理器可见
 *
 */
public class Final {
    private final String a;
    private String b;
    static Final instance;

    public Final(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public static void write() {
        instance = new Final("A", "B");
    }

    public static void read() {
        Final obj = Final.instance;
        String a = obj.a;
        String b = obj.b;
    }

    private static void finalEscapeFromConstruct() {

    }

    public static void main(String[] args) {

    }
}
