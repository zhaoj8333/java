package lang.anno;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
    String[] value() default "unknown";
}

class Person {
    @MyAnno
    @Deprecated
    public void empty() {
        System.out.println("empty");
    }

    @MyAnno(value = {"girl", "boy"})
    public void somebody(String name, int age) {
        System.out.println(name + ", " + age);
    }
}

public class AnnoReflect {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        Class<Person> personClass = Person.class;
        Method somebody = null;
        try {
            somebody = personClass.getMethod("somebody", String.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (somebody != null) {
            Object invoke = somebody.invoke(person, new Object[]{"lily", 18});
            itr(somebody);
        }

        System.out.println("=========================");

        Method empty = null;
        try {
            empty = personClass.getMethod("empty", new Class[] {});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (empty != null) {
            empty.invoke(person, new Object[]{});
            itr(empty);
        }
    }

    public static void itr(Method method) {
        if (method.isAnnotationPresent(MyAnno.class)) {
            MyAnno annotation = method.getAnnotation(MyAnno.class);
            System.out.println("======: " + annotation);
            String[] value = annotation.value();
            for (String a : value) {
                System.out.println(a);
            }
        }
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("-----: " + annotation);
        }
    }
}
