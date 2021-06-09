package lang.dt;

import java.lang.reflect.AccessibleObject;
import java.util.HashMap;

/**
 * Java反射机制：
 *     获取Class对象方法：
 *      {@link Class#forName(String)}
 *      类名.class
 *      对象名.getClass()
 *
 * 优点：
 *     动态获取类实例，灵活性高
 *     与动态编译组合
 * 缺点：
 *     反射性能较低，需要解析字节码，将内存对象进行解析
 * 解决：
 *     {@link java.lang.reflect.Field#setAccessible(boolean)}关闭jdk的安全检查
 *     多次创建时，缓存起来
 *     ReflectASM工具类，通过字节码生成加快反射速度
 *     不安全，会破坏封装性
 */
public class JavaReflection {
    public static void main(String[] args) {
        Class<HashMap> hashMapClass = HashMap.class;
        System.out.println(hashMapClass);

        HashMap<Object, Object> hm = new HashMap<>();
        System.out.println(hm.getClass());

        try {
            Class<?> aClass = Class.forName("java.util.HashMap");
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class<Integer> type = Integer.TYPE;
        System.out.println(type);   // 基本类型的包装类，可以调用包装类的Type属性来获得该包装类的Class对象

    }
}
