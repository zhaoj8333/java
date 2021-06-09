package jvm.clazz.loading;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ThreadClassLoader {
    public static void main(String[] args) {

//        testThreadContextClassloader1();
//        testThreadContextClassloader2();
        testJdbcLoader();

    }

    private static void testJdbcLoader() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(
                    "jdbc:mysql://192.168.33.10:3306/test",
                    "root",
                    "zhaoj833"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程上下文加载的的使用形式(获取 - 使用 - 还原)
     *
     * ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
     * try {
     *     Thread.currentThread.setContextClassLoader(targetTccl);
     *     myThread();
     * } finally {
     *     Thread.currentThread().setContextClassLoader(classLoader)
     * }
     * myThread()中会调用Thread.currentThread().getContextClassLoader()获取当前线程的上下文加载器做某些事情
     * 如果类由加载器A加载, 它的依赖类而是由A加载
     *
     * ContextClassLoader就是为了破坏java的类加载机制
     *
     * 当高层提供了统一的接口让低层区实现, 同时又要在高层加载或实例化低层的类时, 就必须要在线程上下文类加载器来帮助高层的ClassLoader
     * 找到并加载该类
     *
     */
    private static void testThreadContextClassloader2() {
//        Thread.currentThread().setContextClassLoader(ThreadClassLoader.class.getClassLoader().getParent());

        ServiceLoader<Driver> sl  = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = sl.iterator();

        while (iterator.hasNext()) {
            Driver driver = iterator.next();
            System.out.println(driver.getClass() + " ---> " + driver.getClass().getClassLoader());
        }

        System.out.println();

        System.out.println("currentthread: " + Thread.currentThread().getContextClassLoader());
        System.out.println("serviceloader: " + ServiceLoader.class.getClassLoader());
    }

    /**
     * 当前类的加载类:
     *     每一个类都会尝试使用其自身的类加载器来去加载它所依赖的其他的类
     *
     * 线程上下文类加载器:
     *     从JDK1.2开始引入
     *     如果没有通过setContextClassLoader(ClassLoader cl)进行设置的话,线程将继承其父线程的上下文类加载器
     *     java应用运行时的初始线程上下文加载器就是系统类加载器
     *     线程中运行的代码可以通过该类加载器加载类与资源
     *
     *     重要性:
     *         Class.forName("com.mysql.driver.Driver");
     *         Connection conn = Driver.getConnection();
     *         Statement stmt  = conn.getStatement();
     *
     *         具体厂商对Connection等驱动的实现, 必须由系统类加载器加载,扫描相关的CLASSPATH
     *         如果基于双亲委派原则父加载器看不到子加载器的加载的类,子加载器可以访问父加载器加载的类或接口
     *         如果父类(由启动类加载, 如Connection)要访问具体实现类(由应用加载器加载), 是无法加载到具体实现类的
     *
     *    SPI(Service Provider Interface)
     *         其实只需要实现的jar包扔到CLASSPATH下即可, 但是基于双亲委派原则是无法加载的
     *         父ClassLoader可以使用当前线程Thread.currentThread().getContextClassLoader()所指定的classloader加载的类
     *         这就改变了父ClassLoader不能使用子ClassLoader或是其他没有父子关系的ClassLoader加载类的情况, 即改变了双亲委派模型
     *
     *         线程上下文加载器就是当前线程的Current ClassLoader
     *         双亲委派模型下, 类加载是由下至上的, 即下层委托上层进行加载
     *         但是对于SPI有些是Java核心库提供, 而Java核心库由Bootstrap加载器加载, 这些接口的实现却来自于不同的jar包(厂商提供)
     *         ,java的启动类加载器是不会加载其他来源的jar包,这样传统的双亲委派模型无法满足SPI要求.
     *         而通过给当前线程设置上下文类加载器, 就可以自由设置的上下文加载器来实现对于接口实现的加载
     *
     *
     */
    private static void testThreadContextClassloader1() {
        System.out.println(ThreadClassLoader.class.getClassLoader());

        System.out.println(Thread.currentThread().getContextClassLoader());
        // AppClassLoader
        System.out.println(Thread.class.getClassLoader());
        // null
    }
}
