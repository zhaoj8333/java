package lang.reflection.d1;

import java.lang.reflect.Constructor;

public class Demo1 {

    public static void main(String[] args) {
        Class<Student> clazz = Student.class;
//        System.out.println(clazz);

        Constructor<Student> constructor = null;
        try {
            constructor = clazz.getConstructor();
            System.out.println("constructor: " + constructor);
            Constructor<Student> declaredConstructor = clazz.getDeclaredConstructor();
            System.out.println("declared: " + declaredConstructor);
            // 暴力访问
            constructor.setAccessible(true);
            declaredConstructor.setAccessible(true);
            Student student = declaredConstructor.newInstance();
            System.out.println(student);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Student {
    private Integer id;
    private String username;
    private String password;
    private Integer age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    private Student(Integer id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Student(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    private Student() {
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