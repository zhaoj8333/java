package lang.obj.hierarchy;


/*
     Java 不支持多继承，只允许一个类直接继承另一个类，
     即子类只能有一个直接父类，extends 关键字后面只能有一个类名。

     一个类只能有一个直接父类，但是它可以有多个间接的父类。

 */

/**
 * super 关键字:
 *
 * super() 必须是在子类构造方法的方法体的第一行。
 * super() 必须是在子类构造方法的方法体的第一行。
 *
 */
public class Son extends Parent{
    public Son(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Son() {
        super();
    }

    protected String name;
    protected int age;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected void say() {
        System.out.println("I am child");
        super.say();
    }

    public void walk() {
        System.out.println("child walk");
    }

    public void eat() {
        super.walk(); // 如果子类中重写walk，调用的是被重写的方法
        System.out.println("eat");
    }

//    public final void smoke() {
//        System.out.println("儿子抽烟");
//    }

    public final void learn() {
        final String task = "学习";
        // task = "抽烟";
        // cannot assign a value to final variable
        System.out.println(task);
    }
}
