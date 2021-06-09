package jvm.bc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

@SuppressWarnings("all")
public class ByteCodeGuide {
    private int a = 1;
    private String name = "non-static";
    private static String hello = "static";

    /**
     * 构造方法的字节码:
     * 0 aload_0
     *  1 invokespecial #1 <java/lang/Object.<init>>
     *  4 aload_0
     *  5 iconst_1
     *  6 putfield #2 <jvm/bc/ByteCodeDemo.a>
     *  9 aload_0
     * 10 ldc #3 <non-static>
     * 12 putfield #4 <jvm/bc/ByteCodeDemo.name>
     * 15 return
     *
     * 所有 非静态变量 的 初始赋值 是在构造方法(init)中进行的
     * 静态变量的赋值是在 (clinit)中进行的
     *
     */
    public ByteCodeGuide() { }

    public ByteCodeGuide(String hello) {
        System.out.println("aaaa");
    }

    public ByteCodeGuide(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void update() {
        synchronized (ByteCodeGuide.class) {
            System.out.println("syn method");
        }
    }

    /**
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=4, args_size=1
      stack:  操作数栈深度, 对应于code中的max_stack
      locals: 局部变量数量, 包含了this, fis, serverSocket, e
      args_size: 可接受参数, 实例方法的第一个参数都是this, 该操作在编译期间完成, 在运行期间jvm会将this自动传递进去,
                 把对this的访问转换成了对传参的访问
                 非实例方法则不会传递this参数

         0: new           #10                 // class java/io/FileInputStream
         3: dup                               // 复制栈顶的操作数, 把复制结果推给栈
         4: ldc           #11                 // String text.txt 从常量池的变量push入栈
         6: invokespecial #12                 // Method java/io/FileInputStream."<init>":(Ljava/lang/String;)V 调用父类方法
         9: astore_1                          // 将引用存储到局部变量中
        10: new           #13                 // class java/net/ServerSocket
        13: dup
        14: sipush        9999                // push short 短整形
        17: invokespecial #14                 // Method java/net/ServerSocket."<init>":(I)V
        20: astore_2
        21: aload_2
        22: invokevirtual #15                 // Method java/net/ServerSocket.accept:()Ljava/net/Socket;
        25: pop
        26: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
        29: ldc           #16                 // String finally
        31: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        34: goto          64
        37: astore_1
        38: aload_1
        39: invokevirtual #18                 // Method java/io/IOException.printStackTrace:()V
        42: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
        45: ldc           #16                 // String finally
        47: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        50: goto          64
        53: astore_3
        54: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
        57: ldc           #16                 // String finally
        59: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        62: aload_3
        63: athrow
        64: return

     异常处理机制:
        采用同一个异常表对异常进行处理
        存在finally语句块时,现代jvm都是将finally语句块的字节码拼接到每一个catch块中

     方法声明抛出异常 与 方法内部catch异常的区别:
        jclasslib code平级的一行会多出一行Exceptions
        任何异常都会展示出
        声明异常与内部捕获异常

     Exception table:
     from    to  target type
     0    26    37   Class java/io/FileNotFoundException
     0    26    49   Class java/io/IOException
     0    26    61   Class java/lang/Exception
     0    26    73   any
     LineNumberTable:
     line 131: 0
     line 132: 10
      ... ...

     本地变量表:
        LocalVariableTable:
        Start  Length  Slot  Name           Signature
        10      16     1    fis             Ljava/io/FileInputStream;
        21       5     2    serverSocket    Ljava/net/ServerSocket;
        0      85     0     this            Ljvm/bc/ByteCodeDemo;

     frame_type = 101 /* same_locals_1_stack_item
     stack = [ class java/io/IOException ]
     frame_type = 79 /* same_locals_1_stack_item
     stack = [ class java/lang/Throwable ]
     frame_type = 10 /* same
     */
    public void testException() throws FileNotFoundException, IOException, Exception, NullPointerException {
        try {
            final FileInputStream fis = new FileInputStream("text.txt");
            final ServerSocket serverSocket = new ServerSocket(9999);
            serverSocket.accept();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {

            // any
        } finally {
            System.out.println("finally");
        }
    }

    public static void main(String[] args) {
        final ByteCodeGuide byteCodeDemo = new ByteCodeGuide();
        byteCodeDemo.setA(2);
        hello = "hello";
    }
}
