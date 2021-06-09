package lang.anno;

import java.lang.annotation.*;

/*
    定义一个Annotation
    @interface: 意味着实现java.lang.annotation.Annotation接口
        注解的实现细节都由编译器完成
 */
@Documented
// @Documented: 类和方法的注解默认情况下不会出现在javadoc中的, 如果用@Documented修饰,则会出现在javadoc中
@Target(ElementType.TYPE)
// 定义注解的类型属性, @Target可有可无,没有时可以用在任何地方
@Retention(RetentionPolicy.RUNTIME)
// 策略属性, @Retention, 默认为CLASS
public @interface Anno1 {

}
