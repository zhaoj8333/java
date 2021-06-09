package lang.reflection.d1;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassReflection {
    public static void main(String[] args) {
//        getclass();

//        lang.obj();

        try {
            Class<?> aClass = Class.forName("java.lang.Integer");
            Object o = Array.newInstance(aClass, 10);
            for (int i = 0; i < 10; i++) {
                Array.set(o, i, i * 10);
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Array.get(o, i));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void obj() {
        Field[] fields = User.class.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        Method[] methods = User.class.getMethods();
        for (Method method : methods) {
//            System.out.println(method);
        }

        try {
//            Constructor<User> constructor = User.class.getConstructor();
            Constructor<?>[] constructors = User.class.getConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getclass() {
        try {
            Class<?> aClass = Class.forName("lang.reflection.d1.IUesr");
            System.out.println(aClass.getCanonicalName());
//            System.out.println(aClass);
            Class<?> user = Class.forName("lang.reflection.d1.User");
            System.out.println(user.getCanonicalName());
//            System.out.println(user);
//            System.out.println(user.toGenericString());
//            Class<?> aClass1 = Class.forName("[[Ljava.lang.String");
//            System.out.println(aClass1);
//            System.out.println(IUesr.class);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

interface IUesr {
    void show();
}

class User {
    public Integer id;
    public String username;
    public String password;
    public Integer age;

    public User(Integer id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User (Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
