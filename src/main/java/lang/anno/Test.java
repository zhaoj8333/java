package lang.anno;

import java.lang.annotation.*;
import java.util.Date;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface Inheritable {

}

@Inheritable
class TestFather {

    public void testInheritaed() {
        System.out.println("father inheritable: " + Test.class.isAnnotationPresent(Inheritable.class));
//        如果使用了@Inherited, 则子类也会继承该注解
    }
}


public class Test extends TestFather {

    @Deprecated
    private static void testDeprecated1() {
        System.out.println("test Deprecated 1");
    }

    private void testDeprecated2() {
        System.out.println("test deprecated 2");
    }

    public void testInheritaed() {
        System.out.println("son inheritable: " + Test.class.isAnnotationPresent(Inheritable.class));
//        如果使用了@Inherited, 则子类也会继承该注解: 继承性
    }

    public static void main(String[] args) {
//        testInheritable();
        testSupressWarning();
    }

    @SuppressWarnings(value = {"deprecation"})
    /*
        SupressWarnings:
            deprecation: 使用了不赞成使用的类或方法的警告
            unchecked:   执行了未检查的转换时的警告, 如使用集合时未用泛型
            fallthrough: switch程序块直接通往下一个路径时的警告
            path: 类路径,源文件另等中有不存在的路径时警告
            serial: 可序列化类上缺少serialVersionUID警告
            finally: 任何finally子句不能正常完成
            allji 以上所有警告

     */
    private static void testSupressWarning() {
        Date date = new Date(13, 8, 26);
        System.out.println(date);
    }

    private static void testInheritable() {
        TestFather testFather = new TestFather();
        Test test = new Test();
//        test.testDeprecated2();
//        testDeprecated1();
        testFather.testInheritaed();
        test.testInheritaed();
    }
}
